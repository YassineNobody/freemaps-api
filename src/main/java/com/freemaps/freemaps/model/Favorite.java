package com.freemaps.freemaps.model;

import com.freemaps.freemaps.enums.FavoriteType;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "keycloak_user_id", nullable = false)
    private UUID keycloakUserId;

    @Column(name = "address_id", length = 100)
    private String addressId;

    @Column(name = "ban_id")
    private UUID banId;

    @Column(nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FavoriteType type = FavoriteType.OTHER;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String label;

    @Column(length = 255)
    private String street;

    @Column(length = 20)
    private String housenumber;

    @Column(length = 10)
    private String postcode;

    @Column(length = 255)
    private String city;

    @Column(length = 10)
    private String citycode;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
