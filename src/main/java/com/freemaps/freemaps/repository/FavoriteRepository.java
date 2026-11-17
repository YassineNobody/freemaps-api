package com.freemaps.freemaps.repository;

import com.freemaps.freemaps.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    List<Favorite> findByKeycloakUserIdOrderByCreatedAtDesc(
            UUID keycloakUserId
    );

    Optional<Favorite> findByIdAndKeycloakUserId(
            UUID id,
            UUID keycloakUserId
    );

    boolean existsByKeycloakUserIdAndAddressId(
            UUID keycloakUserId,
            String addressId
    );

    long deleteByIdAndKeycloakUserId(
            UUID id,
            UUID keycloakUserId
    );
}
