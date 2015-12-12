/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KuroiTenshi
 * Created: Dec 12, 2015
 */

drop table chatroom_location;

create table chatroom_location(
    room_id bigint NOT NULL,
    geom GEOMETRY(POINT,4326),
    CONSTRAINT pk_location_id PRIMARY KEY (room_id)    
);

ALTER TABLE "chatroom_location" ADD --table#6.2
    CONSTRAINT "fk_chatroom_location_room_id" 
    FOREIGN KEY (room_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO chatroom_location VALUES (1,ST_GeomFromEWKT('SRID=4326;POINT(-126.4 45.32)'));
INSERT INTO chatroom_location VALUES (2,ST_GeomFromEWKT('SRID=4326;POINT(-11.421515412 44.326546544)'));
INSERT INTO chatroom_location VALUES (3,ST_GeomFromEWKT('SRID=4326;POINT(-123.47866 4.38672)'));