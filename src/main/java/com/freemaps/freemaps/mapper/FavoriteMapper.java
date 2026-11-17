
package com.freemaps.freemaps.mapper;

import com.freemaps.freemaps.dto.favorite.CreateFavoriteRequest;
import com.freemaps.freemaps.dto.favorite.FavoriteResponse;
import com.freemaps.freemaps.model.Favorite;

public final class FavoriteMapper {

    private FavoriteMapper() {}

    public static Favorite toEntity(CreateFavoriteRequest request) {
        Favorite favorite = new Favorite();

        favorite.setName(request.name());
        favorite.setType(request.type());
        favorite.setAddressId(request.addressId());
        favorite.setBanId(request.banId());
        favorite.setLabel(request.label());
        favorite.setStreet(request.street());
        favorite.setHousenumber(request.housenumber());
        favorite.setPostcode(request.postcode());
        favorite.setCity(request.city());
        favorite.setCitycode(request.citycode());
        favorite.setLongitude(request.longitude());
        favorite.setLatitude(request.latitude());

        return favorite;
    }

    public static FavoriteResponse toResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getName(),
                favorite.getType(),
                favorite.getAddressId(),
                favorite.getBanId(),
                favorite.getLabel(),
                favorite.getStreet(),
                favorite.getHousenumber(),
                favorite.getPostcode(),
                favorite.getCity(),
                favorite.getCitycode(),
                favorite.getLongitude(),
                favorite.getLatitude(),
                favorite.getCreatedAt()
        );
    }
}
