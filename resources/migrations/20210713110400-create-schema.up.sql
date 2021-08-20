CREATE TABLE users (
    id           SMALLSERIAL,
    email        VARCHAR(64) NOT NULL,
    password     VARCHAR(1000) NOT NULL,
    username     VARCHAR(32) NOT NULL,
    is_active    BOOLEAN DEFAULT TRUE,
    is_deleted   BOOLEAN DEFAULT FALSE,
    time_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP::TIMESTAMP WITHOUT TIME ZONE,
    PRIMARY KEY (id)
);
--;;
CREATE TABLE tables (
    id                  SMALLSERIAL,
    description         VARCHAR(256),
    number              SMALLINT UNIQUE NOT NULL,
    capacity            SMALLINT NOT NULL,
    PRIMARY KEY (id)
);
--;;
CREATE TABLE guests (
    id                  SMALLSERIAL,
    first_name          VARCHAR(32) NOT NULL,
    middle_name         VARCHAR(32),
    last_name           VARCHAR(32) NOT NULL,
    email               VARCHAR(64),
    phone               VARCHAR(64),
    state               VARCHAR(64),
    city                VARCHAR(64),
    street              VARCHAR(64),
    descriptive_number  VARCHAR(32),
    orientation_number  VARCHAR(32),
    postal_code         VARCHAR(16),
    rsvp_answered       BOOLEAN NOT NULL DEFAULT FALSE,
    email_sent          BOOLEAN NOT NULL DEFAULT FALSE,
    table_id            SMALLINT,
    PRIMARY KEY (id),
    FOREIGN KEY (table_id) REFERENCES tables (id) ON DELETE SET NULL
);
--;;
