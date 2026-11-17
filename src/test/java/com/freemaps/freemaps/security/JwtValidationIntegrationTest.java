
package com.freemaps.freemaps.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtValidationIntegrationTest {

    private static final String ISSUER =
            "http://localhost:8180/realms/freemaps";

    private KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    private NimbusJwtDecoder createDecoder(KeyPair pair) {
        var publicKey =
                (java.security.interfaces.RSAPublicKey) pair.getPublic();

        NimbusJwtDecoder decoder =
                NimbusJwtDecoder.withPublicKey(publicKey).build();

        OAuth2TokenValidator<Jwt> issuerValidator =
                JwtValidators.createDefaultWithIssuer(ISSUER);

        OAuth2TokenValidator<Jwt> audienceValidator = jwt ->
                jwt.getAudience().contains("freemaps-api")
                        ? OAuth2TokenValidatorResult.success()
                        : OAuth2TokenValidatorResult.failure(
                        new org.springframework.security.oauth2.core.OAuth2Error(
                                "invalid_token",
                                "Invalid audience",
                                null
                        )
                );

        decoder.setJwtValidator(
                new DelegatingOAuth2TokenValidator<>(
                        issuerValidator,
                        audienceValidator
                )
        );

        return decoder;
    }

    private String createToken(
            KeyPair pair,
            Instant expiration,
            String issuer,
            List<String> audience
    ) throws Exception {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(UUID.randomUUID().toString())
                .issuer(issuer)
                .audience(audience)
                .issueTime(new Date())
                .expirationTime(Date.from(expiration))
                .build();

        SignedJWT jwt = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256),
                claims
        );

        jwt.sign(new RSASSASigner(
                (java.security.interfaces.RSAPrivateKey) pair.getPrivate()
        ));

        return jwt.serialize();
    }

    @Test
    void shouldRejectExpiredToken() throws Exception {
        KeyPair pair = generateKeyPair();
        NimbusJwtDecoder decoder = createDecoder(pair);

        String token = createToken(
                pair,
                Instant.now().minusSeconds(600),
                ISSUER,
                List.of("freemaps-api")
        );

        assertThrows(JwtException.class, () -> decoder.decode(token));
    }

    @Test
    void shouldRejectInvalidSignature() throws Exception {
        KeyPair trustedPair = generateKeyPair();
        KeyPair untrustedPair = generateKeyPair();

        NimbusJwtDecoder decoder = createDecoder(trustedPair);

        String token = createToken(
                untrustedPair,
                Instant.now().plusSeconds(300),
                ISSUER,
                List.of("freemaps-api")
        );

        assertThrows(JwtException.class, () -> decoder.decode(token));
    }

    @Test
    void shouldRejectInvalidIssuer() throws Exception {
        KeyPair pair = generateKeyPair();
        NimbusJwtDecoder decoder = createDecoder(pair);

        String token = createToken(
                pair,
                Instant.now().plusSeconds(300),
                "http://localhost:8180/realms/other",
                List.of("freemaps-api")
        );

        assertThrows(JwtException.class, () -> decoder.decode(token));
    }

    @Test
    void shouldRejectInvalidAudience() throws Exception {
        KeyPair pair = generateKeyPair();
        NimbusJwtDecoder decoder = createDecoder(pair);

        String token = createToken(
                pair,
                Instant.now().plusSeconds(300),
                ISSUER,
                List.of("another-api")
        );

        assertThrows(JwtException.class, () -> decoder.decode(token));
    }
}
