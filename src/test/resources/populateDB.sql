/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  KuroiTenshi
 * Created: Nov 15, 2015
 */
delete from user_roles;
delete from friendlist;
delete from images;
delete from usersdata;
delete from messages;

insert into usersdata  values (1,'milena','milena',true);
insert into usersdata values (2,'iwanna','iwanna',true);
insert into usersdata values (3,'mixalis','mixalis',true);

insert into messages  values (1, 1, 'Hi');
insert into messages values (2, 1, 'Hello, how are you');
insert into messages values (3, 2, 'I m fine thank you');

insert into friendlist  values (1,2);
insert into friendlist  values (1,3);

insert into user_info values (1,'milena@gmail.com','C:\FizData\DefaultImages\NoImage\noImg.jpg','Status','Azwidou','22/11/1994','kabala'); 
insert into user_info values(2,'iwanna@gmail.com','C:\FizData\DefaultImages\NoImage\noImg.jpg','Status','Fwtiadou','23/01/1994','serres');
insert into user_info values (3,'mixalis@gmail.com','C:\FizData\DefaultImages\NoImage\noImg.jpg','Status','mixailidis','17/10/1994','thessaloniki');
