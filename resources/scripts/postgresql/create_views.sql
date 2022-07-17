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
        rooms.number AS room_number,
        REPLACE(
                CONCAT(
                        CONCAT(g.first_name, ' '),
                        CONCAT(g.middle_name, ' '),
                        g.last_name
                    ),
                '  ',
                ' '
        ) AS full_name,
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
    ON g.escort_id = escort.id
    LEFT JOIN accommodation_rooms rooms
    ON g.room_id = rooms.id;
--;;
CREATE VIEW v_guests_without_room AS
    SELECT g.*
    FROM v_guests g
    WHERE g.accommodation IS TRUE
    AND g.room_id IS NULL;
--;;
CREATE VIEW v_guests_with_room AS
    SELECT g.*
    FROM v_guests g
    WHERE g.accommodation IS TRUE
    AND g.room_id IS NOT NULL;
--;;
CREATE VIEW v_accommodation_rooms AS
    SELECT r.*,
           COALESCE(rooms.allocated_count, 0) AS allocated_count,
           COALESCE(rooms.available_beds_count, r.bed_count) AS available_beds_count,
           COALESCE(guests.guest, '[]') AS allocated_guests
    FROM accommodation_rooms r
    LEFT JOIN (
        SELECT
            r.id AS room_id,
            COUNT(r.id) AS allocated_count,
            r.bed_count - COUNT(r.id) AS available_beds_count
        FROM accommodation_rooms r
        INNER JOIN guests g
        ON r.id = g.room_id
        GROUP BY r.id
    ) rooms
    ON rooms.room_id = r.id
    LEFT JOIN (
        SELECT json_agg(g.*) AS guest, room_id
        FROM (
            SELECT
                g.id,
                g.room_id,
                REPLACE(
                    CONCAT(
                        CONCAT(first_name, ' '),
                        CONCAT(middle_name, ' '),
                        last_name
                    ),
                    '  ',
                    ' '
                ) AS full_name
            FROM v_guests g
            ) g
        GROUP BY room_id
    ) guests
    ON guests.room_id = r.id;
--;;
CREATE VIEW v_accommodation_rooms_available AS
    SELECT r.*
    FROM v_accommodation_rooms r
    WHERE r.available_beds_count > 0;
--;;
