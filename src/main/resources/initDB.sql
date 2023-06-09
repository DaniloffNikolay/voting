DROP TABLE IF EXISTS Lunch_menu_item;
DROP TABLE IF EXISTS Lunch_menu;
DROP TABLE IF EXISTS Dish;
DROP TABLE IF EXISTS Vote;
DROP TABLE IF EXISTS Restaurant;
DROP TABLE IF EXISTS Person;

CREATE TABLE IF NOT EXISTS Person (
    id INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(100) NOT NULL UNIQUE,
    password varchar,
    role VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS Restaurant(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR UNIQUE
);

CREATE TABLE IF NOT EXISTS Vote(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    person_id INTEGER REFERENCES Person(id) ON DELETE SET NULL ,
    restaurant_id INTEGER REFERENCES Restaurant(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS Dish(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Lunch_menu(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    date DATE NOT NULL,
    restaurant_id INTEGER REFERENCES Restaurant(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS Lunch_menu_item(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    dish_id INTEGER REFERENCES Dish(id) NOT NULL,
    price DOUBLE PRECISION,
    lunch_menu_id INTEGER REFERENCES Lunch_menu(id) NOT NULL
);