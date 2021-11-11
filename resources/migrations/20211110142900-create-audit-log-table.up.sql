CREATE TABLE audit_log(
    id BIGSERIAL,
    event VARCHAR(32),
    payload JSONB,
    time_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
--;;