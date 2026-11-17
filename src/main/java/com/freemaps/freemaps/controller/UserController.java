package com.freemaps.freemaps.controller;

import com.freemaps.freemaps.dto.user.UserResponse;
import com.freemaps.freemaps.mapper.UserMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Jwt jwt){
        return UserMapper.toResponse(jwt);
    }
}
