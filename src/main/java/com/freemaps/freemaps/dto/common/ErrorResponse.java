package com.freemaps.freemaps.dto.common;

public record ErrorResponse(
        String message,
        String code,
        String description
) {}
