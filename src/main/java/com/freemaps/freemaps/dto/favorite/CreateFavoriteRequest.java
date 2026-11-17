
package com.freemaps.freemaps.dto.favorite;

import com.freemaps.freemaps.enums.FavoriteType;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateFavoriteRequest(
        @NotBlank
        @Size(max = 255)
        String name,

        @NotNull
        FavoriteType type,

        @Size(max = 100)
        String addressId,

        UUID banId,

        @NotBlank
        String label,

        String street,
        String housenumber,
        String postcode,
        String city,
        String citycode,

        @NotNull
        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        Double longitude,

        @NotNull
        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        Double latitude
) {}
