
package com.freemaps.freemaps.dto.favorite;

import com.freemaps.freemaps.enums.FavoriteType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FavoriteResponse(
        UUID id,
        String name,
        FavoriteType type,
        String addressId,
        UUID banId,
        String label,
        String street,
        String housenumber,
        String postcode,
        String city,
        String citycode,
        Double longitude,
        Double latitude,
        OffsetDateTime createdAt
) {}
