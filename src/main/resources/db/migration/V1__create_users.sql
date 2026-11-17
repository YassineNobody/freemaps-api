CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    keycloak_id VARCHAR(255) NOT NULL UNIQUE,

    display_name VARCHAR(100),

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);