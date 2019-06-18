DROP TABLE IF EXISTS users;

CREATE TABLE users(
    idProduct SERIAL NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL
);