CREATE DATABASE store;
CREATE TABLE product
(
    id    bigserial PRIMARY KEY,
    name  varchar(50),
    price decimal
);
CREATE TABLE seller
(
    id   bigserial PRIMARY KEY,
    name varchar(50)
);
CREATE TABLE sale
(
    id      bigserial PRIMARY KEY,
    date    date,
    seller  bigint references seller (id),
    product bigint references product (id),
    count   integer,
    price   numeric,
    sum     numeric
);