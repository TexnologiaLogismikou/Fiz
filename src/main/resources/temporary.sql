/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KuroiTenshi
 * Created: Dec 12, 2015
 */

-- drop table chatroom_location;
-- 

-- INSERT INTO chatroom_location VALUES (1,-10,15);
-- INSERT INTO chatroom_location VALUES (2,56,142);
-- INSERT INTO chatroom_location VALUES (3,23,20);

-- INSERT INTO chatroom_location VALUES (1,ST_GeomFromEWKT('SRID=4326;POINT(-126.4 45.32)'));
-- INSERT INTO chatroom_location VALUES (2,ST_GeomFromEWKT('SRID=4326;POINT(10)'));
-- INSERT INTO chatroom_location VALUES (3,ST_GeomFromEWKT('SRID=4326;POINT(-123.47866 4.38672)'));

-- select chatroom_location.room_id from chatroom_location where earth_box(ll_to_earth(-120.4,40.2), 10000) @> ll_to_earth(-120,40);

-- SELECT chatroom_location.room_id, earth_distance(ll_to_earth( 12, 10 ), ll_to_earth(chatroom_location.lng, chatroom_location.lat)) as myData 
-- FROM chatroom_location group by chatroom_location.room_id 
-- HAVING 100 < earth_distance(ll_to_earth( 12, 10 ), ll_to_earth(chatroom_location.lng, chatroom_location.lat));

-- SELECT ST_Distance(geography(ST_MakePoint(chatroom_location.lng, chatroom_location.lat)) ,
--                    geography(ST_MakePoint(12, 10))) as d
-- FROM chatroom_location
-- having d>100;

-- SELECT * from chatroom_location 
-- where chatroom_location.room_id in ( select room_id from chatroom_location where ST_Distance(geography(ST_MakePoint(chatroom_location.lng, chatroom_location.lat)) ,
--                                             geography(ST_MakePoint(12, 10))) < 5000000  );
-- WHERE "chatroom_location"."username" = $3

-- SELECT * from chatroom_location 
-- where chatroom_location.room_id in ( select chatroom_location.room_id 
-- from chatroom_location where earth_distance(ll_to_earth( 12, 10 ), 
-- ll_to_earth(chatroom_location.lng, chatroom_location.lat)) < chatroom_location.room_max_range );

select * from chatroom_location where room_id=1 and earth_distance(ll_to_earth( 12, 10 ) ,ll_to_earth(chatroom_location.lng, chatroom_location.lat)) < 5000000 ;