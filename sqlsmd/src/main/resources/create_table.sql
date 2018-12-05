DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    c_id integer not null,
    c_name varchar(300),
    c_password varchar(300),
    PRIMARY KEY (c_id)
);