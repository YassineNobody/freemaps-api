
package com.freemaps.freemaps.controller;

import com.freemaps.freemaps.dto.favorite.CreateFavoriteRequest;
import com.freemaps.freemaps.dto.favorite.FavoriteResponse;
import com.freemaps.freemaps.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    private UUID getUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }

    @GetMapping
    public List<FavoriteResponse> favorites(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return favoriteService.getFavorites(getUserId(jwt));
    }

    @GetMapping("/{favoriteId}")
    public FavoriteResponse favorite(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID favoriteId
    ) {
        return favoriteService.getFavorite(
                favoriteId,
                getUserId(jwt)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteResponse createFavorite(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid CreateFavoriteRequest request
    ) {
        return favoriteService.createFavorite(
                request,
                getUserId(jwt)
        );
    }

    @DeleteMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFavorite(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID favoriteId
    ) {
        favoriteService.deleteFavorite(
                favoriteId,
                getUserId(jwt)
        );
    }
}
