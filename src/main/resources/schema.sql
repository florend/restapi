create table if not exists users
(
    id       uuid primary key,
    username varchar(100) not null unique,
    email    varchar(255) not null unique,
    password varchar(255) not null
);

create table if not exists category
(
    id   serial primary key,
    name varchar(75) not null unique
);

create table if not exists post
(
    id          serial primary key,
    category_id serial references category (id),
    image       varchar(255) not null,
    title       varchar(255) not null,
    body        text,
    date        date         not null
);

