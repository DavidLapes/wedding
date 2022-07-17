CREATE TABLE accommodation_rooms (
    id SMALLSERIAL,
    number VARCHAR(16) NOT NULL,
    bed_count SMALLINT NOT NULL,
    PRIMARY KEY(id)
);
--;;
ALTER TABLE guests ADD COLUMN room_id SMALLINT;
--;;
ALTER TABLE guests ADD CONSTRAINT guests_room_id_fkey FOREIGN KEY (room_id) REFERENCES accommodation_rooms(id);
--;;
