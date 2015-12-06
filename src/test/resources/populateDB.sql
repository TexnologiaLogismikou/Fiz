/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KuroiTenshi
 * Created: Nov 15, 2015
 */
DELETE FROM user_roles;
DELETE FROM friendlist;
DELETE FROM chatrooms;
DELETE FROM images;
DELETE FROM usersdata;
DELETE FROM messages;
DELETE FROM user_info;

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