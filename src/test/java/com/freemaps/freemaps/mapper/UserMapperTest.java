
package com.freemaps.freemaps.mapper;

import com.freemaps.freemaps.dto.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void shouldMapJwtToUserResponse() {
        UUID id = UUID.randomUUID();

        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .subject(id.toString())
                .claim("preferred_username", "testuser")
                .claim("email", "test@example.com")
                .build();

        UserResponse response = UserMapper.toResponse(jwt);

        assertEquals(id, response.id());
        assertEquals("testuser", response.username());
        assertEquals("test@example.com", response.email());
    }

    @Test
    void shouldThrowWhenSubjectIsMissing() {
        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "RS256")
                .claim("preferred_username", "testuser")
                .build();

        assertThrows(
                NullPointerException.class,
                () -> UserMapper.toResponse(jwt)
        );
    }
}
