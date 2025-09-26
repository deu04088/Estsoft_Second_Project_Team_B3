-- schema.sql 으로 DB 테이블 생성 --


CREATE TABLE bosses (
                       id BIGINT PRIMARY KEY,
                       boss_name VARCHAR(20),
                       nick_name VARCHAR(30),
                       password VARCHAR(60),
                       address VARCHAR(50),
                       created_at DATETIME,
                       role VARCHAR(10),
                       state INT
);
CREATE TABLE photos (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        s3key VARCHAR(255) NOT NULL,
                        s3url VARCHAR(255) NOT NULL,
                        upload_date TIMESTAMP NOT NULL
);

CREATE TABLE restaurants (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(30) NOT NULL,
                             address VARCHAR(50),
                             menu_name VARCHAR(30),
                             price INT,
                             description TEXT,
                             created_at TIMESTAMP,
                             updated_at TIMESTAMP,
                             state INT,
                             boss_id BIGINT,
                             photo_id BIGINT,
                             FOREIGN KEY (boss_id) REFERENCES bosses(id),
                             FOREIGN KEY (photo_id) REFERENCES photos(id)
);


CREATE TABLE likes (
                       id BIGINT PRIMARY KEY,
                       boss_id BIGINT,
                       restaurant_id BIGINT,
                       liked_at DATETIME,
                       FOREIGN KEY (boss_id) REFERENCES bosses(id),
                       FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE chat_rooms (
                            id BIGINT PRIMARY KEY,
                            boss_id BIGINT,
                            boss_id2 BIGINT,
                            created_at DATETIME,
                            FOREIGN KEY (boss_id) REFERENCES bosses(id),
                            FOREIGN KEY (boss_id2) REFERENCES bosses(id)
);

CREATE TABLE messages (
                          id BIGINT PRIMARY KEY,
                          chat_room_id BIGINT,
                          sender_id BIGINT,
                          content TEXT,
                          created_at DATETIME,
                          FOREIGN KEY (chat_room_id) REFERENCES chat_rooms(id),
                          FOREIGN KEY (sender_id) REFERENCES bosses(id)
);
