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
delete from friendlist;
delete from usersdata;
delete from messages;

insert into usersdata  values (1,'milena','milena');
insert into usersdata values (2,'iwanna','iwanna');
insert into usersdata values (3,'mixalis','mixalis');

insert into messages  values (1, 1, 'Hi');
insert into messages values (2, 1, 'Hello, how are you');
insert into messages values (3, 2, 'I m fine thank you');