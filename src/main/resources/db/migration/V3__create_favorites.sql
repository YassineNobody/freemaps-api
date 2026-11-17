
CREATE TABLE favorites (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    keycloak_user_id UUID NOT NULL,
    address_id VARCHAR(100),
    ban_id UUID,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL DEFAULT 'OTHER',
    label TEXT NOT NULL,
    street VARCHAR(255),
    housenumber VARCHAR(20),
    postcode VARCHAR(10),
    city VARCHAR(255),
    citycode VARCHAR(10),
    longitude DOUBLE PRECISION NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_favorite_type
        CHECK (type IN (
            'HOME',
            'WORK',
            'SCHOOL',
            'RESTAURANT',
            'CAFE',
            'SHOPPING',
            'SPORT',
            'HEALTH',
            'ENTERTAINMENT',
            'TRANSPORT',
            'TRAVEL',
            'OTHER'
        )),
    CONSTRAINT chk_favorite_coordinates
        CHECK (
            longitude BETWEEN -180 AND 180
            AND latitude BETWEEN -90 AND 90
        )
);

CREATE INDEX idx_favorites_user
    ON favorites (keycloak_user_id);
