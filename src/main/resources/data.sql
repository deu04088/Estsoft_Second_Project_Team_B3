-- data.sql 으로 더미데이터 삽입 --



-- users
INSERT INTO bosses (user_name, nick_name, password, address, si_do, gu_gun, dong_eup_myeon, created_at, role, state)
VALUES
    ('test001', 'example001', '$2b$10$TdkRWhKSkAhHRXabE.Ry9ulj/ROINqMFQDYHXVO467hTruipoSkCa','경기도 의정부시 용현동', '경기도', '의정부시', '용현동', CURRENT_TIMESTAMP, 'ROLE_ADMIN', 1),
    ('test002', 'example002', '$2b$10$KdniFkA6Bcp39p8OGIdoHuPyCUyLKBTHuaVK4gwGy9xSY2ukJkl5i','부산광역시 해운대구 좌동', '부산광역시', '해운대구', '좌동',  CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test003', 'example003', '$2b$10$r8TNFYz102zKrC9/8RVLj.a4HqYHhWpju4Fmy1uGFQZVztc6uh8AK','인천광역시 미추홀구 주안동', '인천광역시', '미추홀구', '주안동',  CURRENT_TIMESTAMP, 'ROLE_USER', 0),
    ('test004', 'example004', '$2b$10$Jm6DDoz8nt8yjGI8L1jI4./mifSCusZu7cy3hu/Xl/AS6peO22l6y','대구광역시 수성구 범어동', '대구광역시', '수성구', '범어동',  CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test005', 'example005', '$2b$10$C5ce9ckep9hhV7N3gU/pJ.ZQimnucR8AQc.PVcKUjjWBsWml400GO','대전광역시 서구 둔산동', '대전광역시', '서구', '둔산동', CURRENT_TIMESTAMP, 'ROLE_USER', 0),
    ('test006', 'example006', '$2b$10$YvToTedet9J2ehHkm3SYuufSwVR4ZItBi5m37edISFMCeLxdBeXZq', '광주광역시 북구 운암동', '광주광역시', '북구', '운암동', CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test007', 'example007', '$2b$10$8IiC36cLgDWurWL.UPkb6.bTw.qkY2R0x.29q6i4KF4Sic2nRA30C', '울산광역시 남구 삼산동', '울산광역시', '남구', '삼산동', CURRENT_TIMESTAMP, 'ROLE_USER', 0),
    ('test008', 'example008', '$2b$10$E5etMi7Eft3CPUnideXP5OKBBzI.OqBTBdOoJBykM/0lblA3PftQ6', '경기도 수원시 영통동', '경기도', '수원시', '영통동', CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test009', 'example009', '$2b$10$lnYdyKLAnh56t2VKBrF5Su.fnyV1fd31HKR7KW8bSPjv4xSnieJBG', '전라북도 전주시 덕진동', '전라북도', '전주시', '덕진동', CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test010', 'example010', '$2b$10$jQf5P3zqRtkdlcGc0/Y4meKUGgwh8663MEE8pC0a8LP0AW9T1Dym2', '충청북도 청주시 상당동', '충청북도', '청주시', '상당동', CURRENT_TIMESTAMP, 'ROLE_USER', 0),
    ('test011', 'example011', '$2b$10$cvBHPEsdlKlqIwXgdcm2Hez46iGSZehyWCh5pagpC.qOUXf4UXKJ6', '경상남도 김해시 삼계동', '경상남도', '김해시', '삼계동', CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test012', 'example012', '$2b$10$58bNno5YfnalhJ6ficiVTekZ3ldjsiN5HpOKMiQa8fvcJQAB/XGda', '경상북도 포항시 북구', '경상북도', '포항시', '북구', CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test013', 'example013', '$2b$10$cjRmbGzjS2uTyZ1OfTNzbOPVvVKTNNBBUQbXHHwEdJjfAc9uflyQ.', '경상남도 창원시 성산구', '경상남도', '창원시', '성산구', CURRENT_TIMESTAMP, 'ROLE_USER', 0),
    ('test014', 'example014', '$2b$10$a6/32ykgKdCWmFBa38MFfOCDTmr8gBnqxqdlq3JA5vWoq6MyNx.aW', '경기도 안양시 동안구', '경기도', '안양시', '동안구',CURRENT_TIMESTAMP, 'ROLE_USER', 1),
    ('test015', 'example015', '$2b$10$983JGu2BUW.XKnxPtJmBeOM7QgYtfYLZQryosgRuq8NdxEP.HXyCW', '경기도 의정부시 가능동', '경기도', '의정부시', '가능동', CURRENT_TIMESTAMP, 'ROLE_ADMIN', 1);


-- photo
INSERT INTO photos (s3key, s3url, original_filename, upload_date)
VALUES
    ('sample_001', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_002', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_003', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_004', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_005', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_006', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_007', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_008', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_009', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_010', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_011', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_012', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_013', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_014', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_015', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_016', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_017', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_018', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_019', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_020', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_021', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_022', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_023', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_024', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_025', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_026', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_027', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_028', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_029', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_030', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_031', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP),
    ('sample_032', '/images/default-restaurant.png', 'default-restaurant.png', CURRENT_TIMESTAMP),
    ('sample_033', '/images/restaurant-sample.jpg', 'restaurant-sample.jpg', CURRENT_TIMESTAMP),
    ('sample_034', '/images/restaurant-sample2.jpg', 'restaurant-sample2.jpg', CURRENT_TIMESTAMP),
    ('sample_035', '/images/restaurant-sample3.jpg', 'restaurant-sample3.jpg', CURRENT_TIMESTAMP);


-- restaurants
INSERT INTO restaurants (name, address, si_do, gu_gun, dong_eup_myeon, menu_name, price, description, created_at, updated_at, state, boss_id, photo_id)
VALUES
    ('example001', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '김치찌개', 8000, '김치랑 돼지고기 넣고 끓인 찌개', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 1),
    ('example002', '부산광역시 해운대구 좌동', '부산광역시', '해운대구', '좌동', '비빔밥', 9000, '다양한 채소와 고추장을 섞은 밥', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 2),
    ('example003', '인천광역시 미추홀구 주안동', '인천광역시', '미추홀구', '주안동', '불고기', 12000, '달콤한 간장 양념의 소고기 볶음', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 3, 3),
    ('example004', '대구광역시 수성구 범어동', '대구광역시', '수성구', '범어동', '떡볶이', 5000, '매콤한 고추장 소스의 떡 요리', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 4, 4),
    ('example005', '대전광역시 서구 둔산동', '대전광역시', '서구', '둔산동', '삼겹살', 13000, '구워 먹는 돼지고기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 5, 5),
    ('example006', '광주광역시 북구 운암동', '광주광역시', '북구', '운암동', '냉면', 7000, '시원한 면 요리', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 6, 6),
    ('example007', '울산광역시 남구 삼산동', '울산광역시', '남구', '삼산동', '순두부찌개', 8500, '순두부가 주재료인 찌개', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 7, 7),
    ('example008', '경기도 수원시 영통동', '경기도', '수원시', '영통동', '갈비탕', 15000, '소갈비가 들어간 맑은 국물 요리', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 8, 8),
    ('example009', '전라북도 전주시 덕진동', '전라북도', '전주시', '덕진동', '짜장면', 6000, '중국식 면 요리', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 9, 9),
    ('example010', '충청북도 청주시 상당동', '충청북도', '청주시', '상당동', '김밥', 4000, '밥과 재료를 김으로 말아 만든 음식', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 10, 10),
    ('example011', '경상남도 김해시 삼계동', '경상남도', '김해시', '삼계동', '닭갈비', 11000, '닭고기 볶음', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 11, 11),
    ('example012', '경상북도 포항시 북구', '경상북도', '포항시', '북구', '짬뽕', 9500, '얼큰한 중국식 면 요리', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 12, 12),
    ('example013', '경상남도 창원시 성산구', '경상남도', '창원시', '성산구', '된장찌개', 7500, '된장으로 끓인 찌개', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 13, 13),
    ('example014', '경기도 안양시 동안구', '경기도', '안양시', '동안구', '호떡', 3000, '한국식 길거리 팬케이크', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 14, 14),
    ('example015', '경기도 의정부시 가능동', '경기도', '의정부시', '가능동', '국밥', 8500, '국에 밥말아 먹는 요리', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 15, 15);
-- restaurants: 의정부시 용현동 추가 식당
INSERT INTO restaurants (name, address, si_do, gu_gun, dong_eup_myeon, menu_name, price, description, created_at, updated_at, state, boss_id, photo_id)
VALUES
    ('example016', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '돈까스', 9000, '바삭하게 튀긴 일본식 돈까스', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 16),
    ('example017', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '우동', 7000, '쫄깃한 면발의 일본식 국수', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 17),
    ('example018', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '오므라이스', 8000, '계란으로 감싼 볶음밥', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 18),
    ('example019', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '카레라이스', 8500, '매콤한 일본식 카레', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 19),
    ('example020', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '쌀국수', 9500, '베트남식 소고기 쌀국수', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 20),
    ('example021', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '해장국', 8500, '얼큰한 국물의 해장용 국밥', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 21),
    ('example022', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '제육볶음', 9000, '매콤한 돼지고기 볶음', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 22),
    ('example023', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '칼국수', 7000, '손으로 밀어 만든 국수', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 23),
    ('example024', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '부대찌개', 10000, '햄과 소시지가 들어간 찌개', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 24),
    ('example025', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '쭈꾸미볶음', 11000, '매콤한 해산물 볶음', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 25),
    ('example026', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '냉모밀', 8000, '차가운 메밀국수', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 26),
    ('example027', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '치킨', 16000, '바삭한 후라이드 치킨', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 27),
    ('example028', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '라멘', 9500, '진한 육수의 일본식 라멘', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 28),
    ('example029', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '소바', 8500, '일본식 메밀국수', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, 29),
    ('example030', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '볶음밥', 7000, '불맛 가득한 볶음밥', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 3, 30),
    ('example031', '경기도 의정부시 용현동', '경기도', '의정부시', '용현동', '오징어덮밥', 9500, '매콤한 오징어 볶음 덮밥', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 3, 31);


-- likes
INSERT INTO likes (boss_id, restaurant_id, liked_at)
VALUES
    (1, 5, CURRENT_TIMESTAMP),
    (2, 3, CURRENT_TIMESTAMP),
    (3, 1, CURRENT_TIMESTAMP),
    (4, 7, CURRENT_TIMESTAMP),
    (5, 2, CURRENT_TIMESTAMP),
    (6, 6, CURRENT_TIMESTAMP),
    (7, 4, CURRENT_TIMESTAMP),
    (8, 8, CURRENT_TIMESTAMP),
    (9, 9, CURRENT_TIMESTAMP),
    (10, 10, CURRENT_TIMESTAMP),
    (11, 11, CURRENT_TIMESTAMP),
    (12, 12, CURRENT_TIMESTAMP),
    (13, 13, CURRENT_TIMESTAMP),
    (14, 14, CURRENT_TIMESTAMP),
    (15, 15, CURRENT_TIMESTAMP);

-- chat_rooms
INSERT INTO chat_rooms (boss_id, boss_id2, created_at)
VALUES
    (1, 2, CURRENT_TIMESTAMP),
    (2, 3, CURRENT_TIMESTAMP),
    (3, 4, CURRENT_TIMESTAMP),
    (4, 5, CURRENT_TIMESTAMP),
    (5, 6, CURRENT_TIMESTAMP),
    (6, 7, CURRENT_TIMESTAMP),
    (7, 8, CURRENT_TIMESTAMP),
    (8, 9, CURRENT_TIMESTAMP),
    (9, 10, CURRENT_TIMESTAMP),
    (10, 11, CURRENT_TIMESTAMP),
    (11, 12, CURRENT_TIMESTAMP),
    (12, 13, CURRENT_TIMESTAMP),
    (13, 14, CURRENT_TIMESTAMP),
    (14, 15, CURRENT_TIMESTAMP),
    (15, 1, CURRENT_TIMESTAMP);

-- messages
INSERT INTO messages (chat_room_id, sender_id, content, created_at) VALUES
-- 유저 1과 2의 대화 (chat_room_id = 1)
(1, 1, 'send 1', CURRENT_TIMESTAMP),
(1, 2, 'response 1', CURRENT_TIMESTAMP),
(1, 1, 'send 2', CURRENT_TIMESTAMP),
(1, 2, 'response 2', CURRENT_TIMESTAMP),
(1, 1, 'transmission 1', CURRENT_TIMESTAMP),
(1, 2, 'response 3', CURRENT_TIMESTAMP),

-- 유저 3과 4의 대화 (chat_room_id = 3)
(3, 3, 'hi', CURRENT_TIMESTAMP),
(3, 4, 'hello', CURRENT_TIMESTAMP),
(3, 3, 'bye', CURRENT_TIMESTAMP),
(3, 4, 'see you', CURRENT_TIMESTAMP),

-- 유저 5와 6의 대화 (chat_room_id = 5)
(5, 5, 'hi', CURRENT_TIMESTAMP),
(5, 6, 'hello', CURRENT_TIMESTAMP),
(5, 5, 'bye', CURRENT_TIMESTAMP),

-- 유저 13과 14의 대화 (chat_room_id = 13)
(13, 13, '밥뭇나?', CURRENT_TIMESTAMP),
(13, 14, 'ㅇㅇ', CURRENT_TIMESTAMP),

-- 유저 15와 1의 대화 (chat_room_id = 15)
(15, 15, '파이팅', CURRENT_TIMESTAMP),
(15, 1, 'ㅍㅇㅌ', CURRENT_TIMESTAMP),

-- 채팅방 활성화용 메시지 (chat_room_id 2~12, 14)
(2, 2, '초기 메시지: 채팅방 2 활성화', CURRENT_TIMESTAMP),
(4, 4, '초기 메시지: 채팅방 4 활성화', CURRENT_TIMESTAMP),
(6, 6, '초기 메시지: 채팅방 6 활성화', CURRENT_TIMESTAMP),
(7, 7, '초기 메시지: 채팅방 7 활성화', CURRENT_TIMESTAMP),
(8, 8, '초기 메시지: 채팅방 8 활성화', CURRENT_TIMESTAMP),
(9, 10, '초기 메시지: 채팅방 9 활성화', CURRENT_TIMESTAMP),
(10, 10, '초기 메시지: 채팅방 10 활성화', CURRENT_TIMESTAMP),
(11, 11, '초기 메시지: 채팅방 11 활성화', CURRENT_TIMESTAMP),
(12, 12, '초기 메시지: 채팅방 12 활성화', CURRENT_TIMESTAMP),
(14, 14, '초기 메시지: 채팅방 14 활성화', CURRENT_TIMESTAMP);