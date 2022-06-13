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
    WHERE rsvp_answered = false
    ORDER BY id;
--;;
CREATE VIEW v_guests AS
    SELECT
        g.*,
        CASE
            WHEN g.type = 'PRIMARY' THEN 'Primární'
            WHEN g.type = 'PLUS_ONE' THEN 'Host jako plus jedna'
            ELSE ''
        END type_name,
        CASE
            WHEN g.invitation_delivery_type = 'MAIL' THEN 'Pošta'
            WHEN g.invitation_delivery_type = 'IN_PERSON' THEN 'Osobně'
            ELSE ''
        END invitation_delivery_type_name,
        REPLACE(
            CONCAT(
                CONCAT(escort.first_name, ' '),
                CONCAT(escort.middle_name, ' '),
                escort.last_name
            ),
            '  ',
            ' '
        ) AS escort_name
    FROM guests g
    LEFT JOIN guests escort
    ON g.escort_id = escort.id;
--;;
