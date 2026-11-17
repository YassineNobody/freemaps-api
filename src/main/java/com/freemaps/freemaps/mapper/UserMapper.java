package com.freemaps.freemaps.mapper;

import com.freemaps.freemaps.dto.user.UserResponse;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;
import java.util.UUID;

public class UserMapper {
    static public UserResponse toResponse(Jwt jwt){
        return new UserResponse(
                UUID.fromString(Objects.requireNonNull(jwt.getSubject(), "Jwt subject is missing")),
                jwt.getClaimAsString("preferred_username"),
                jwt.getClaimAsString("email")
        );
    }
}
