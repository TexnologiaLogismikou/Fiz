DROP TABLE "user_roles"; --table#2
DROP TABLE "chatroom_location";
DROP TABLE "user_info"; --table#3
DROP TABLE "images"; --table#4
DROP TABLE "friendlist"; --table#5
DROP TABLE "messages"; --table#7
DROP TABLE "chatroom_blacklist"; --table#6.4
DROP TABLE "chatroom_whitelist"; --table#6.5
DROP TABLE "chatrooms_members"; --table#6.2
DROP TABLE "chatroom_privileges"; --table#6.3
DROP TABLE "chatrooms_entities"; --table#6.1
DROP TABLE "usersdata"; --table#1

CREATE TABLE "usersdata" ( --table#1
    "id" bigint NOT NULL,
    "username" text NOT NULL,
    "password" text NOT NULL,
    "enabled" BOOLEAN NOT NULL,
    "hasroom" BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pk_usersdata PRIMARY KEY (id),
    CONSTRAINT uniq_username UNIQUE (username)    
);

CREATE TABLE "user_roles" ( --table#2
    "user_role_userid" bigint NOT NULL,
    "user_role_role" text NOT NULL,
    CONSTRAINT pk_userrole PRIMARY KEY (user_role_userid)
);

CREATE TABLE "user_info" ( --table#3
    "userid" bigint NOT NULL,
    "first_name" text NOT NULL,
    "last_name" text,
    "birthday" DATE NOT NULL,
    "email" text NOT NULL,
    "status" text,
    "profile_photo" text NOT NULL,
    "hometown" text,
    CONSTRAINT pk_userinfo PRIMARY KEY (userid),
    CONSTRAINT uniq_email UNIQUE (email)    
);

CREATE TABLE "images" ( --table#4
    "userid" bigint NOT NULL,
    "tmstamp" TIMESTAMP WITHOUT TIME ZONE NOT NULL, --tmstamp = timestamp . had to name it like this cause of defined word
    "hashtag" bigint NOT NULL,
    "images" text NOT NULL,
    CONSTRAINT pk_images PRIMARY KEY (userid,tmstamp)    
);

CREATE TABLE "friendlist" ( --table#5
    "userid" bigint NOT NULL,
    "friendid" bigint NOT NULL,
    "added_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_friendlist PRIMARY KEY (userid,friendid)    
);

CREATE TABLE "chatrooms_entities" ( --table#6.1
    "room_id" bigint NOT NULL UNIQUE,
    "room_creator" bigint NOT NULL,
    "room_name" text NOT NULL,
    "room_creation_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL, 
    "room_last_activity" TIMESTAMP WITHOUT TIME ZONE NOT NULL CHECK (room_creation_date < room_last_activity),
    CONSTRAINT pk_chatroom_entities PRIMARY KEY (room_id,room_creator),
    CONSTRAINT uniq_room_name UNIQUE (room_name)
);

CREATE TABLE "chatrooms_members" ( --table#6.2
    "room_id" bigint NOT NULL,
    "room_member" bigint NOT NULL,
    "room_joined_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chatroom_members PRIMARY KEY (room_id,room_member)
);

CREATE TABLE "chatroom_privileges" ( --table#6.3
    "room_id" bigint NOT NULL,
    "room_privileges" text NOT NULL,
    "room_password_protected" BOOLEAN NOT NULL,
    "room_password" text,
    "room_access_method" text NOT NULL CHECK(room_access_method IN('blacklist','whitelist')),
    CONSTRAINT pk_chatroom_priv PRIMARY KEY (room_id)
);

CREATE TABLE "chatroom_blacklist" ( --table#6.4
    "room_id" bigint NOT NULL,
    "room_member" bigint NOT NULL,
    "room_ban_time" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "room_expiration_time" TIMESTAMP WITHOUT TIME ZONE NOT NULL CHECK (room_ban_time < room_expiration_time),
    CONSTRAINT pk_chatroom_blacklist PRIMARY KEY (room_id,room_member)
);

CREATE TABLE "chatroom_whitelist" ( --table#6.5
    "room_id" bigint NOT NULL,
    "room_member" bigint NOT NULL,
    "room_invitation_time" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chatroom_whitelist PRIMARY KEY (room_id,room_member)
);

CREATE TABLE "messages" ( --table#7
    "id" bigint NOT NULL,
    "userid" bigint NOT NULL,
    "message" text NOT NULL,
    "datesent" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "chatroom_id" bigint NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
);

CREATE TABLE chatroom_location(
    room_id bigint NOT NULL,
    room_lat FLOAT NOT NULL,
    room_lng FLOAT NOT NULL,
    "room_max_range" INTEGER NOT NULL DEFAULT '20000',
    CONSTRAINT pk_location_id PRIMARY KEY (room_id)    
);

ALTER TABLE "chatroom_location" ADD --table#6.2
    CONSTRAINT "fk_chatroom_location_room_id" 
    FOREIGN KEY (room_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "user_roles" ADD --table#2
    CONSTRAINT "fk_userid_user_role" 
    FOREIGN KEY (user_role_userid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "user_info" ADD --table#3
    CONSTRAINT "fk_userid_user_info" 
    FOREIGN KEY (userid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "images" ADD --table#4
    CONSTRAINT "fk_userid_images" 
    FOREIGN KEY (userid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "friendlist" ADD --table#5.1
    CONSTRAINT "fk_userid_friendlist_userid" 
    FOREIGN KEY (userid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "friendlist" ADD --table#5.2
    CONSTRAINT "fk_userid_friendlist_friendid" 
    FOREIGN KEY (friendid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatrooms_entities" ADD --table#6.1
    CONSTRAINT "fk_chatroom_entities_creatorid" 
    FOREIGN KEY (room_creator) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatrooms_members" ADD --table#6.2
    CONSTRAINT "fk_chatroom_members_room_id" 
    FOREIGN KEY (room_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatrooms_members" ADD --table#6.2
    CONSTRAINT "fk_chatroom_member_memberid" 
    FOREIGN KEY (room_member) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatroom_privileges" ADD --table#6.3
    CONSTRAINT "fk_chatroom_privileges_room_id" 
    FOREIGN KEY (room_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatroom_blacklist" ADD --table#6.4
    CONSTRAINT "fk_room_blacklist_userid" 
    FOREIGN KEY (room_member) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatroom_blacklist" ADD --table#6.4
    CONSTRAINT "fk_room_blacklist_roomid" 
    FOREIGN KEY (room_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatroom_whitelist" ADD --table#6.5
    CONSTRAINT "fk_room_whitelist_userid" 
    FOREIGN KEY (room_member) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatroom_whitelist" ADD --table#6.5
    CONSTRAINT "fk_room_whitelist_roomid" 
    FOREIGN KEY (room_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "messages" ADD --table#7
    CONSTRAINT "fk_userid_messages" 
    FOREIGN KEY (userid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "messages" ADD --table#7
    CONSTRAINT "fk_userid_messages2" 
    FOREIGN KEY (chatroom_id) REFERENCES "chatrooms_entities" (room_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO usersdata  VALUES (1,'milenaAz','milena',TRUE,TRUE); --table#1
INSERT INTO usersdata VALUES (2,'iwannaFot','iwanna',TRUE,TRUE); --table#1
INSERT INTO usersdata VALUES (3,'mixalisMix','mixalis',TRUE,TRUE); --table#1

INSERT INTO user_roles VALUES(1,'ROLE_USER'); --table#2
INSERT INTO user_roles VALUES(2,'ROLE_USER'); --table#2
INSERT INTO user_roles VALUES(3,'ROLE_USER'); --table#2

INSERT INTO user_info VALUES (1,'milena','Azwidou','22/11/1994','milena@gmail.com','Status','C:\FizData\DefaultImages\NoImage\noImg.jpg','kabala');  --table#3
INSERT INTO user_info VALUES (2,'Iwanna','Fwtiadou','23/01/1994','iwanna@gmail.com','Status','C:\FizData\DefaultImages\NoImage\noImg.jpg','serres');  --table#3
INSERT INTO user_info VALUES (3,'Mixalis','Mixailidis','17/10/1994','mixalis@gmail.com','Status','C:\FizData\DefaultImages\NoImage\noImg.jpg','thessaloniki');  --table#3

INSERT INTO friendlist VALUES (1, 2,TO_TIMESTAMP('16-05-2011 12:00:00', 'dd-mm-yyyy hh24:mi:ss')); --table#5
INSERT INTO friendlist VALUES (1, 3,TO_TIMESTAMP('16-05-2011 13:00:00', 'dd-mm-yyyy hh24:mi:ss')); --table#5

INSERT INTO chatrooms_entities VALUES (1,1,'first testing room','20/1/2010','7/12/2015'); --table#6.1
INSERT INTO chatrooms_entities VALUES (2,1, 'second testing room','21/2/2011','7/12/2015'); --table#6.1
INSERT INTO chatrooms_entities VALUES (3,3, 'third testing room','22/3/2012','7/12/2015'); --table#6.1

INSERT INTO chatrooms_members VALUES (1,1,'15/12/2015'); --table#6.2
INSERT INTO chatrooms_members VALUES (1,2,'14/9/2015'); --table#6.2
INSERT INTO chatrooms_members VALUES (2,1,'14/9/2015'); --table#6.2
INSERT INTO chatrooms_members VALUES (3,3,'13/10/2015'); --table#6.2
INSERT INTO chatrooms_members VALUES (3,2,'12/11/2015'); --table#6.2

INSERT INTO chatroom_privileges VALUES (1,'PUBLIC',FALSE,NULL,'whitelist'); --table#6.3
INSERT INTO chatroom_privileges VALUES (2,'PUBLIC',FALSE,NULL,'blacklist'); --table#6.3
INSERT INTO chatroom_privileges VALUES (3,'PUBLIC',TRUE,'password','blacklist'); --table#6.3

INSERT INTO chatroom_blacklist VALUES (2,3,'10/10/2015','28/12/2015'); --table#6.4
INSERT INTO chatroom_blacklist VALUES (3,1,'11/10/2014','30/12/2015'); --table#6.4

INSERT INTO chatroom_whitelist VALUES (1,1,'1/1/2014'); --table#6.5
INSERT INTO chatroom_whitelist VALUES (1,2,'10/1/2014'); --table#6.5

INSERT INTO messages VALUES (1, 1,'initial message',TO_TIMESTAMP('16-05-2011 12:30:00', 'dd-mm-yyyy hh24:mi:ss'),'1'); --table#7
INSERT INTO messages VALUES (2, 2,'second message',TO_TIMESTAMP('16-05-2011 13:30:00', 'dd-mm-yyyy hh24:mi:ss'),'1'); --table#7
INSERT INTO messages VALUES (3, 2,'third message',TO_TIMESTAMP('16-05-2011 14:30:00', 'dd-mm-yyyy hh24:mi:ss'),'1'); --table#7
INSERT INTO messages VALUES (4, 3,'forth message',TO_TIMESTAMP('16-05-2011 13:30:00', 'dd-mm-yyyy hh24:mi:ss'),'2'); --table#7


