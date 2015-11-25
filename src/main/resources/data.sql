CREATE TABLE user_roles (
    user_role_id int(11) NOT NULL AUTO_INCREMENT,
    username varchar(45) NOT NULL,
    role varchar(45) NOT NULL,
    PRIMARY KEY (user_role_id),
    UNIQUE KEY uni_username_role (role,username),
    KEY fk_username_idx (username),
    CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES usersdata (username));