/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KuroiTenshi
 * Created: Nov 15, 2015
 */
DELETE FROM "user_roles"; --table#2
DELETE FROM "user_info"; --table#3
DELETE FROM "images"; --table#4
DELETE FROM "friendlist"; --table#5
DELETE FROM "messages"; --table#7
DELETE FROM "chatroom_blacklist"; --table#6.4
DELETE FROM "chatroom_whitelist"; --table#6.5
DELETE FROM "chatrooms_members"; --table#6.2
DELETE FROM "chatroom_privileges"; --table#6.3
DELETE FROM "chatroom_location";
DELETE FROM "chatrooms_entities"; --table#6.1
DELETE FROM "usersdata"; --table#1

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


