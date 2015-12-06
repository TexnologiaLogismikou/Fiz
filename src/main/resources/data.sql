DROP TABLE "user_roles"; --table#2
DROP TABLE "user_info"; --table#3
DROP TABLE "images"; --table#4
DROP TABLE "friendlist"; --table#5
DROP TABLE "messages"; --table#6
DROP TABLE "chatrooms"; --table#7
DROP TABLE "usersdata"; --table#1

CREATE TABLE "usersdata" ( --table#1
    "id" bigint NOT NULL,
    "username" text NOT NULL,
    "password" text NOT NULL,
    "enabled" boolean NOT NULL,
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
    "first_name" text,
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

CREATE TABLE "messages" ( --table#6
    "id" bigint NOT NULL,
    "userid" bigint NOT NULL,
    "message" text NOT NULL,
    "datesent" TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    "chatroom_id" bigint NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
);

CREATE TABLE "chatrooms" ( --table#7
    "room_id" bigint NOT NULL,
    "room_name" text NOT NULL,
    "room_creator" bigint NOT NULL,
    "room_member" bigint NOT NULL,
    CONSTRAINT pk_chatroom PRIMARY KEY (room_id,room_creator,room_member)
);

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

ALTER TABLE "messages" ADD --table#6
    CONSTRAINT "fk_userid_messages" 
    FOREIGN KEY (userid) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatrooms" ADD --table#7.1
    CONSTRAINT "fk_userid_chatrooms_creator" 
    FOREIGN KEY (room_creator) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE "chatrooms" ADD --table#7.2
    CONSTRAINT "fk_userid_chatrooms_member" 
    FOREIGN KEY (room_member) REFERENCES "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE;

INSERT INTO usersdata  VALUES (1,'milenaAz','milena',TRUE); --table#1
INSERT INTO usersdata VALUES (2,'iwannaFot','iwanna',TRUE); --table#1
INSERT INTO usersdata VALUES (3,'mixalisMix','mixalis',TRUE); --table#1

INSERT INTO user_roles VALUES(1,'ROLE_USER'); --table#2
INSERT INTO user_roles VALUES(2,'ROLE_USER'); --table#2
INSERT INTO user_roles VALUES(3,'ROLE_USER'); --table#2

INSERT INTO user_info VALUES (1,'milena','Azwidou','22/11/1994','milena@gmail.com','Status','C:\FizData\DefaultImages\NoImage\noImg.jpg','kabala');  --table#3
INSERT INTO user_info VALUES (2,'Iwanna','Fwtiadou','23/01/1994','iwanna@gmail.com','Status','C:\FizData\DefaultImages\NoImage\noImg.jpg','serres');  --table#3
INSERT INTO user_info VALUES (3,'Mixalis','Mixailidis','17/10/1994','mixalis@gmail.com','Status','C:\FizData\DefaultImages\NoImage\noImg.jpg','thessaloniki');  --table#3

INSERT INTO friendlist VALUES (1, 2,TO_TIMESTAMP('16-05-2011 12:00:00', 'dd-mm-yyyy hh24:mi:ss')); --table#5
INSERT INTO friendlist VALUES (1, 3,TO_TIMESTAMP('16-05-2011 13:00:00', 'dd-mm-yyyy hh24:mi:ss')); --table#5

INSERT INTO chatrooms VALUES (1,'testing room','1','1'); --table#7
INSERT INTO chatrooms VALUES (1,'testing room','1','2'); --table#7
INSERT INTO chatrooms VALUES (2,'second testing room','3','3'); --table#7
INSERT INTO chatrooms VALUES (2,'second testing room','3','2'); --table#7

INSERT INTO messages  VALUES (1, 1,'initial message',TO_TIMESTAMP('16-05-2011 12:30:00', 'dd-mm-yyyy hh24:mi:ss'),'1'); --table#6
INSERT INTO messages  VALUES (2, 2,'second message',TO_TIMESTAMP('16-05-2011 13:30:00', 'dd-mm-yyyy hh24:mi:ss'),'1'); --table#6
INSERT INTO messages  VALUES (3, 2,'third message',TO_TIMESTAMP('16-05-2011 14:30:00', 'dd-mm-yyyy hh24:mi:ss'),'1'); --table#6
INSERT INTO messages  VALUES (4, 3,'forth message',TO_TIMESTAMP('16-05-2011 13:30:00', 'dd-mm-yyyy hh24:mi:ss'),'2'); --table#6