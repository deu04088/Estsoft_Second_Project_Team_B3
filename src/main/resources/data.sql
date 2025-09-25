-- data.sql 으로 더미데이터 삽입 --



-- users
INSERT INTO users (id, user_name, nick_name, password, address, created_at, role, state)
VALUES
    (1, 'test001', 'example001', 'pass001', '서울', CURRENT_TIMESTAMP, 'admin', 1),
    (2, 'test002', 'example002', 'pass002', '부산', CURRENT_TIMESTAMP, 'user', 1),
    (3, 'test003', 'example003', 'pass003', '인천', CURRENT_TIMESTAMP, 'user', 0),
    (4, 'test004', 'example004', 'pass004', '대구', CURRENT_TIMESTAMP, 'user', 1),
    (5, 'test005', 'example005', 'pass005', '대전', CURRENT_TIMESTAMP, 'user', 0),
    (6, 'test006', 'example006', 'pass006', '광주', CURRENT_TIMESTAMP, 'user', 1),
    (7, 'test007', 'example007', 'pass007', '울산', CURRENT_TIMESTAMP, 'user', 0),
    (8, 'test008', 'example008', 'pass008', '수원', CURRENT_TIMESTAMP, 'user', 1),
    (9, 'test009', 'example009', 'pass009', '전주', CURRENT_TIMESTAMP, 'user', 1),
    (10, 'test010', 'example010', 'pass010', '청주', CURRENT_TIMESTAMP, 'user', 0),
    (11, 'test011', 'example011', 'pass011', '김해', CURRENT_TIMESTAMP, 'user', 1),
    (12, 'test012', 'example012', 'pass012', '포항', CURRENT_TIMESTAMP, 'user', 1),
    (13, 'test013', 'example013', 'pass013', '창원', CURRENT_TIMESTAMP, 'user', 0),
    (14, 'test014', 'example014', 'pass014', '안양', CURRENT_TIMESTAMP, 'user', 1),
    (15, 'test015', 'example015', 'pass015', '의정부', CURRENT_TIMESTAMP, 'user', 1);


-- restaurants
INSERT INTO restaurants (id, name, address, menu_name, price, description, photo_url, created_at, updated_at, state, user_id)
VALUES
    (1, 'example001', '서울', '김치찌개', 8000, '김치랑 돼지고기 넣고 끓인 찌개', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
    (2, 'example002', '부산', '비빔밥', 9000, '다양한 채소와 고추장을 섞은 밥', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2),
    (3, 'example003', '인천', '불고기', 12000, '달콤한 간장 양념의 소고기 볶음', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 3),
    (4, 'example004', '대구', '떡볶이', 5000, '매콤한 고추장 소스의 떡 요리', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 4),
    (5, 'example005', '대전', '삼겹살', 13000, '구워 먹는 돼지고기', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 5),
    (6, 'example006', '광주', '냉면', 7000, '시원한 면 요리', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 6),
    (7, 'example007', '울산', '순두부찌개', 8500, '순두부가 주재료인 찌개', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 7),
    (8, 'example008', '수원', '갈비탕', 15000, '소갈비가 들어간 맑은 국물 요리', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 8),
    (9, 'example009', '전주', '짜장면', 6000, '중국식 면 요리', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 9),
    (10, 'example010', '청주', '김밥', 4000, '밥과 재료를 김으로 말아 만든 음식', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 10),
    (11, 'example011', '김해', '닭갈비', 11000, '닭고기 볶음', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 11),
    (12, 'example012', '포항', '짬뽕', 9500, '얼큰한 중국식 면 요리', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 12),
    (13, 'example013', '창원', '된장찌개', 7500, '된장으로 끓인 찌개', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 13),
    (14, 'example014', '안양', '호떡', 3000, '한국식 길거리 팬케이크', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 14),
    (15, 'example015', '의정부', '국밥', 8500, '국에 밥말아 먹는 요리', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 15);


-- likes
INSERT INTO likes (id, user_id, restaurant_id, liked_at)
VALUES
    (1, 1, 5, CURRENT_TIMESTAMP),
    (2, 2, 3, CURRENT_TIMESTAMP),
    (3, 3, 1, CURRENT_TIMESTAMP),
    (4, 4, 7, CURRENT_TIMESTAMP),
    (5, 5, 2, CURRENT_TIMESTAMP),
    (6, 6, 6, CURRENT_TIMESTAMP),
    (7, 7, 4, CURRENT_TIMESTAMP),
    (8, 8, 8, CURRENT_TIMESTAMP),
    (9, 9, 9, CURRENT_TIMESTAMP),
    (10, 10, 10, CURRENT_TIMESTAMP),
    (11, 11, 11, CURRENT_TIMESTAMP),
    (12, 12, 12, CURRENT_TIMESTAMP),
    (13, 13, 13, CURRENT_TIMESTAMP),
    (14, 14, 14, CURRENT_TIMESTAMP),
    (15, 15, 15, CURRENT_TIMESTAMP);


-- chat_rooms
INSERT INTO chat_rooms (id, user_id, user_id2, created_at)
VALUES
    (1, 1, 2, CURRENT_TIMESTAMP),
    (2, 2, 3, CURRENT_TIMESTAMP),
    (3, 3, 4, CURRENT_TIMESTAMP),
    (4, 4, 5, CURRENT_TIMESTAMP),
    (5, 5, 6, CURRENT_TIMESTAMP),
    (6, 6, 7, CURRENT_TIMESTAMP),
    (7, 7, 8, CURRENT_TIMESTAMP),
    (8, 8, 9, CURRENT_TIMESTAMP),
    (9, 9, 10, CURRENT_TIMESTAMP),
    (10, 10, 11, CURRENT_TIMESTAMP),
    (11, 11, 12, CURRENT_TIMESTAMP),
    (12, 12, 13, CURRENT_TIMESTAMP),
    (13, 13, 14, CURRENT_TIMESTAMP),
    (14, 14, 15, CURRENT_TIMESTAMP),
    (15, 15, 1, CURRENT_TIMESTAMP);

-- messages
INSERT INTO messages (id, chat_room_id, sender_id, content, created_at) VALUES
-- 유저 1과 2의 대화 (chat_room_id = 1)
(1, 1, 1, 'send 1', CURRENT_TIMESTAMP),
(2, 1, 2, 'response 1', CURRENT_TIMESTAMP),
(3, 1, 1, 'send 2', CURRENT_TIMESTAMP),
(4, 1, 2, 'response 2', CURRENT_TIMESTAMP),
(5, 1, 1, 'transmission 1', CURRENT_TIMESTAMP),
(6, 1, 2, 'response 3', CURRENT_TIMESTAMP),

-- 유저 3과 4의 대화 (chat_room_id = 3)
(7, 3, 3, 'hi', CURRENT_TIMESTAMP),
(8, 3, 4, 'hello', CURRENT_TIMESTAMP),
(9, 3, 3, 'bye', CURRENT_TIMESTAMP),
(10, 3, 4, 'see you', CURRENT_TIMESTAMP),

-- 유저 5와 6의 대화 (chat_room_id = 5)
(11, 5, 5, 'hi', CURRENT_TIMESTAMP),
(12, 5, 6, 'hello', CURRENT_TIMESTAMP),
(13, 5, 5, 'bye', CURRENT_TIMESTAMP),

-- 유저 13과 14의 대화 (chat_room_id = 13)
(14, 13, 13, '밥뭇나?', CURRENT_TIMESTAMP),
(15, 13, 14, 'ㅇㅇ', CURRENT_TIMESTAMP),

-- 유저 15와 1의 대화 (chat_room_id = 15)
(16, 15, 15, '파이팅', CURRENT_TIMESTAMP),
(17, 15, 1, 'ㅍㅇㅌ', CURRENT_TIMESTAMP),

-- 채팅방 활성화용 메시지 (chat_room_id 2~12, 14)
(18, 2, 2, '초기 메시지: 채팅방 2 활성화', CURRENT_TIMESTAMP),
(19, 4, 4, '초기 메시지: 채팅방 4 활성화', CURRENT_TIMESTAMP),
(20, 6, 6, '초기 메시지: 채팅방 6 활성화', CURRENT_TIMESTAMP),
(21, 7, 7, '초기 메시지: 채팅방 7 활성화', CURRENT_TIMESTAMP),
(22, 8, 8, '초기 메시지: 채팅방 8 활성화', CURRENT_TIMESTAMP),
(23, 9, 10, '초기 메시지: 채팅방 9 활성화', CURRENT_TIMESTAMP),
(24, 10, 10, '초기 메시지: 채팅방 10 활성화', CURRENT_TIMESTAMP),
(25, 11, 11, '초기 메시지: 채팅방 11 활성화', CURRENT_TIMESTAMP),
(26, 12, 12, '초기 메시지: 채팅방 12 활성화', CURRENT_TIMESTAMP),
(27, 14, 14, '초기 메시지: 채팅방 14 활성화', CURRENT_TIMESTAMP);
