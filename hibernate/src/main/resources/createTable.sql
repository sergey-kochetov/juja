DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL
);