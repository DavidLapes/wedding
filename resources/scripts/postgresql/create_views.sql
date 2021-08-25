CREATE VIEW v_rsvp_guests AS
    SELECT
        id,
        REPLACE(
            CONCAT(
                    CONCAT(first_name, ' '),
                    CONCAT(middle_name, ' '),
                    last_name
            ),
            '  ',
            ' '
        ) AS full_name
    FROM guests
    WHERE rsvp_answered = false;
--;;