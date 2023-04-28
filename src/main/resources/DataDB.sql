INSERT INTO Person(name, role) VALUES ('admin', 'admin');
INSERT INTO Person(name, role) VALUES ('Bob', 'guest');
INSERT INTO Person(name, role) VALUES ('Tom', 'guest');

INSERT INTO Restaurant(name) VALUES ('Heaven');
INSERT INTO Restaurant(name) VALUES ('Smile');

INSERT INTO Lunch_menu(date, restaurant_id) VALUES ('2023-04-27', 1);
INSERT INTO Lunch_menu(date, restaurant_id) VALUES ('2023-04-27', 2);
INSERT INTO Lunch_menu(date, restaurant_id) VALUES (CURRENT_DATE, 1);
INSERT INTO Lunch_menu(date, restaurant_id) VALUES (CURRENT_DATE, 2);

INSERT INTO Dish(name) VALUES ('Meatball soup');
INSERT INTO Dish(name) VALUES ('Borsch');
INSERT INTO Dish(name) VALUES ('Chicken noodle soup');
INSERT INTO Dish(name) VALUES ('Caesar salad');
INSERT INTO Dish(name) VALUES ('Mascarpone salad');
INSERT INTO Dish(name) VALUES ('Balanese pasta');
INSERT INTO Dish(name) VALUES ('Pasta Carbonara');

INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (1, 499.99, 1);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (2, 399.99, 1);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (5, 699.99, 1);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (6, 649.99, 1);

INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (2, 499.99, 2);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (3, 399.99, 2);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (6, 699.99, 2);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (7, 619.99, 2);

INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (1, 599.99, 3);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (3, 499.99, 3);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (4, 799.99, 3);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (7, 799.99, 3);

INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (2, 449.99, 4);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (4, 349.99, 4);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (5, 649.99, 4);
INSERT INTO Lunch_menu_item(dish_id, price, lunch_menu_id) VALUES (6, 649.99, 4);

INSERT INTO Vote(date_time, person_id, restaurant_id) VALUES ('2023-04-27 08:56:21', 3, 1);
INSERT INTO Vote(date_time, person_id, restaurant_id) VALUES ('2023-04-27 09:56:21', 2, 1);
INSERT INTO Vote(date_time, person_id, restaurant_id) VALUES (now(), 2, 1);
INSERT INTO Vote(date_time, person_id, restaurant_id) VALUES (now(), 3, 2);