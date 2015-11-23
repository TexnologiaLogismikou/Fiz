Alter table "images2" add 
    constraint "fk3_userid"
    foreign key (userid) references  "usersdata" (id)
    ON DELETE CASCADE ON UPDATE CASCADE