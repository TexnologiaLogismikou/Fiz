CREATE TABLE friendlist (
  userid bigint  NOT NULL,
  friendid bigint NOT NULL,
  CONSTRAINT user_friend PRIMARY KEY (userid,friendid),
  CONSTRAINT FK_FRIENDS_1 FOREIGN KEY (userid) REFERENCES usersdata (id),
  CONSTRAINT FK_FRIENDS_2 FOREIGN KEY (friendid) REFERENCES usersdata (id)
) 

