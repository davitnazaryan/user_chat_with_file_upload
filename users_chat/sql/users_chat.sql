drop database users_chat;
create database users_chat;

use users_chat;

create table users
(
    id        int primary key not null auto_increment,
    name      varchar(255)    not null,
    surname   varchar(255)    not null,
    email     varchar(255)    not null unique,
    password  varchar(255)    not null,
    image_url varchar(255)    null
);

create table messages
(
    id          int primary key not null auto_increment,
    sender_id   int             not null,
    receiver_id int             not null,
    message     text            not null,
    created_at  timestamp       not null default now(),
    constraint users_messages_sender_id_fk foreign key (sender_id) references users (id),
    constraint users_messages_receiver_id_fk foreign key (receiver_id) references users (id)
);