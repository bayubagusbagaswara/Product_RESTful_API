-- TABLE ROLES
create table roles (
    id bigint not null primary key,
    name character varying(255)
);

alter table roles
    add constraint roles_name_unique unique (name);