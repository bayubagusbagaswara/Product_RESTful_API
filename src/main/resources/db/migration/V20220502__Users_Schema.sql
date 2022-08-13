-- TABLE USER
create table users (
    id bigserial not null primary key,
    created_at timestamp without time zone not null default now(),
    status_record character varying(255) not null,
    updated_at timestamp without time zone not null,
    user_active boolean not null,
    email character varying(40),
    first_name character varying(40) not null,
    last_name character varying(40) not null,
    username character varying(15) not null
);

alter table users
    add constraint users_email_unique unique (email);

alter table users
    add constraint users_username_unique unique (username);

-- TABLE USER PASSWORD

create table user_password(
    id_user bigserial,
    password character varying(255),
    primary key (id_user),
    foreign key (id_user) references users(id)
);

-- TABLE RESET PASSWORD
create table reset_password(
    id bigserial,
    generated timestamp not null,
    expired timestamp not null,
    id_user bigserial not null,
    unique_code character varying(50) not null,
    primary key (id),
    foreign key (id_user) references users(id),
    unique (unique_code)
);