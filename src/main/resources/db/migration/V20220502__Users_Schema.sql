-- TABLE USER
create table users (
    id bigint not null primary key,
    email character varying(40) not null,
    first_name character varying(40) not null,
    last_name character varying(40) not null,
    password character varying(255) not null,
    username character varying(15) not null
);

alter table users
    add constraint users_email_unique unique (email);

alter table users
    add constraint users_username_unique UNIQUE (username);