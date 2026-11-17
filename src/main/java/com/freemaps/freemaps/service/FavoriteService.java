
package com.freemaps.freemaps.service;

import com.freemaps.freemaps.dto.favorite.CreateFavoriteRequest;
import com.freemaps.freemaps.dto.favorite.FavoriteResponse;
import com.freemaps.freemaps.mapper.FavoriteMapper;
import com.freemaps.freemaps.model.Favorite;
import com.freemaps.freemaps.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavorites(UUID keycloakUserId) {
        return favoriteRepository
                .findByKeycloakUserIdOrderByCreatedAtDesc(keycloakUserId)
                .stream()
                .map(FavoriteMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FavoriteResponse getFavorite(UUID id, UUID keycloakUserId) {
        Favorite favorite = findFavorite(id, keycloakUserId);
        return FavoriteMapper.toResponse(favorite);
    }

    @Transactional
    public FavoriteResponse createFavorite(
            CreateFavoriteRequest request,
            UUID keycloakUserId
    ) {
        Favorite entity = FavoriteMapper.toEntity(request);
        entity.setKeycloakUserId(keycloakUserId);

        Favorite saved = favoriteRepository.save(entity);
        return FavoriteMapper.toResponse(saved);
    }

    @Transactional
    public void deleteFavorite(UUID id, UUID keycloakUserId) {
        Favorite favorite = findFavorite(id, keycloakUserId);
        favoriteRepository.delete(favorite);
    }

    private Favorite findFavorite(UUID id, UUID keycloakUserId) {
        return favoriteRepository
                .findByIdAndKeycloakUserId(id, keycloakUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Favorite not found"
                ));
    }
}
