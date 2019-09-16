DELETE
from VOTES;
DELETE
FROM MENU_DETAILS;
-- DELETE
-- FROM MENUS;
-- DELETE
-- FROM RESTAURANT_OWNER;
DELETE
FROM RESTAURANTS;
DELETE
FROM USER_ROLES;
DELETE
FROM USERS;


ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE extra_seq RESTART WITH 100;
INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),           --100000
       ('Admin', 'admin@gmail.com', '{noop}admin'),            --100001
       ('Manager', 'manager@yandex.ru', '{noop}manager'),      --100002
       ('User_1', 'user_1@yandex.ru', '{noop}password1'),      --100003
       ('User_2', 'user_2@yandex.ru', '{noop}password2'),      --100004
       ('User_3', 'user_3@yandex.ru', '{noop}password3'),      --100005
       ('Manager_1', 'manager_1@yandex.ru', '{noop}1password'),--100006
       ('Manager_2', 'manager_2@yandex.ru', '{noop}2password'),--100007
       ('Manager_3', 'manager_3@yandex.ru', '{noop}3password');--100008


INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', (select U.ID FROM USERS U WHERE U.NAME = 'User')),
       ('ROLE_ADMIN', (select U.ID FROM USERS U WHERE U.NAME = 'Admin')),
       ('ROLE_MANAGER', (select U.ID FROM USERS U WHERE U.NAME = 'Manager')),
       -- ('ROLE_MANADGER', (select U.ID FROM USERS U WHERE U.NAME = 'Admin')), -- у админа две роли,думаю не надо или как минимум он не можнт голосовать за свой ресторан
       ('ROLE_USER', (select U.ID FROM USERS U WHERE U.NAME = 'User_1')),
       ('ROLE_USER', (select U.ID FROM USERS U WHERE U.NAME = 'User_2')),
       ('ROLE_USER', (select U.ID FROM USERS U WHERE U.NAME = 'User_3')),
       ('ROLE_MANAGER', (select U.ID FROM USERS U WHERE U.NAME = 'Manager_1')),
       ('ROLE_MANAGER', (select U.ID FROM USERS U WHERE U.NAME = 'Manager_2')),
       ('ROLE_MANAGER', (select U.ID FROM USERS U WHERE U.NAME = 'Manager_3'))
;

INSERT INTO RESTAURANTS (MANAGER_ID, NAME, ADDRESS, OWNER, REGISTERED, DESCRIPTION)
VALUES ((select U.ID FROM USERS U WHERE U.NAME = 'Manager'), 'Star', 'Адресс 1, тел 111-111-111', 'Директор 1',
        CURRENT_DATE, 'Звезда'),
       ((select U.ID FROM USERS U WHERE U.NAME = 'Manager_1'), 'Pearl', 'Адресс 2, тел 222-222-222', 'Директор 2',
        CURRENT_DATE, 'Жемчужина'),
       ((select U.ID FROM USERS U WHERE U.NAME = 'Manager'), 'Super Star', 'Адресс 3, тел 333-333-333', 'Директор 3',
        CURRENT_DATE, 'Супер Звезда'),
       ((select U.ID FROM USERS U WHERE U.NAME = 'Manager_2'), 'Black Pearl', 'Адресс 4, тел 444-444-444', 'Директор 4',
        CURRENT_DATE, 'Черная Жемчужина');



insert INTO MENU_DETAILS(RESTAURANT_ID, TYPE_DISH, DESCRIPTION, QUANTITY, PRICE, DATE_TIME)
VALUES
--Today
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Первое блюдо', 'Борщ', '250 грамм', 10.00, CURRENT_DATE),--100
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Второе блюдо', 'Картошка с мясом', '200 грамм', 25.00,
 CURRENT_DATE),--101
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Третье блюдо', 'Салат овощной', '100 грамм', 5.50,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Четвертое блюдо', 'Компот', '250 грамм', 4.00, CURRENT_DATE),

((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Первое блюдо', 'Уха', '250 грамм', 12.00, CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Второе блюдо', 'рис  с рыбой', '200 грамм', 18.00,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Третье блюдо', 'Салат крабовый', '100 грамм', 7.50,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Четвертое блюдо', 'Сок', '250 грамм', 4.00, CURRENT_DATE),

((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Первое блюдо', 'Грибной суб', '250 грамм', 12.00,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Второе блюдо', 'Мясное рагу', '200 грамм', 18.00,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Третье блюдо', 'Кокот', '100 грамм', 7.50,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Четвертое блюдо', 'Чай', '250 грамм', 4.00,
 CURRENT_DATE),

((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Первое блюдо', 'Солянка', '250 грамм', 12.00,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Второе блюдо', 'Баранина', '200 грамм', 18.00,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Третье блюдо', 'Кокот', '100 грамм', 8.50,
 CURRENT_DATE),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Четвертое блюдо', 'Чай', '250 грамм', 2.00,
 CURRENT_DATE),
--yesterday
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Первое блюдо', 'Гороховый суп', '250 грамм', 6.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Второе блюдо', 'шашлык', '200 грамм', 28.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'), 'Третье блюдо', 'Вино красное', '100 грамм', 9.50,
 CURRENT_DATE - 1 day),

((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Первое блюдо', 'Суп с тефтелями', '250 грамм', 10.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Второе блюдо', 'Говядина', '200 грамм', 12.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'), 'Третье блюдо', 'Чай', '100 грамм', 1.50,
 CURRENT_DATE - 1 day),

((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Первое блюдо', 'Лапша', '250 грамм', 7.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Второе блюдо', 'Пельмени', '200 грамм', 10.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'), 'Третье блюдо', 'Сок', '100 грамм', 3.50,
 CURRENT_DATE - 1 day),


((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Первое блюдо', 'Суп', '250 грамм', 6.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Второе блюдо', 'Макароны', '200 грамм', 8.00,
 CURRENT_DATE - 1 day),
((SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'), 'Третье блюдо', 'Мороженое', '100 грамм', 6.50,
 CURRENT_DATE - 1 day);


INSERT INTO votes(USER_ID, RESTAURANT_ID, DATE_TIME) VALUES
        -- вчерашнее голосование  по одному голосу за ресторан
((select U.ID FROM USERS U WHERE U.NAME = 'User'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'),CURRENT_DATE - 1 day),
((select U.ID FROM USERS U WHERE U.NAME = 'User_1'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'),CURRENT_DATE - 1 day ),
((select U.ID FROM USERS U WHERE U.NAME = 'User_2'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'),CURRENT_DATE - 1 day),
((select U.ID FROM USERS U WHERE U.NAME = 'User_3'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Black Pearl'),CURRENT_DATE - 1 day ),
     -- сегодняшнее голосование 1 голоса Star, Pearl -2, Super Star -1
        ((select U.ID FROM USERS U WHERE U.NAME = 'User'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'),CURRENT_DATE),
        ((select U.ID FROM USERS U WHERE U.NAME = 'User_1'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Super Star'),CURRENT_DATE),
        ((select U.ID FROM USERS U WHERE U.NAME = 'User_2'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Pearl'),CURRENT_DATE);
      --  ((select U.ID FROM USERS U WHERE U.NAME = 'User_3'),(SELECT R.ID FROM RESTAURANTS R WHERE R.NAME = 'Star'),CURRENT_DATE);
