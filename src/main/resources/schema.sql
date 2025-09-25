-- schema.sql 으로 DB 테이블 생성 --


CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       user_name VARCHAR(20),
                       nick_name VARCHAR(30),
                       password VARCHAR(60),
                       address VARCHAR(50),
                       created_at DATETIME,
                       role VARCHAR(10),
                       state INT
);

CREATE TABLE restaurants (
                             id BIGINT PRIMARY KEY,
                             name VARCHAR(30),
                             address VARCHAR(50),
                             menu_name VARCHAR(30),
                             price INT,
                             description TEXT,
                             photo_url VARCHAR(500),
                             created_at DATETIME,
                             updated_at DATETIME,
                             state INT,
                             user_id BIGINT,
                             FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE likes (
                       id BIGINT PRIMARY KEY,
                       user_id BIGINT,
                       restaurant_id BIGINT,
                       liked_at DATETIME,
                       FOREIGN KEY (user_id) REFERENCES users(id),
                       FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE chat_rooms (
                            id BIGINT PRIMARY KEY,
                            user_id BIGINT,
                            user_id2 BIGINT,
                            created_at DATETIME,
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (user_id2) REFERENCES users(id)
);

CREATE TABLE messages (
                          id BIGINT PRIMARY KEY,
                          chat_room_id BIGINT,
                          sender_id BIGINT,
                          content TEXT,
                          created_at DATETIME,
                          FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(id),
                          FOREIGN KEY (sender_id) REFERENCES users(id)
);
