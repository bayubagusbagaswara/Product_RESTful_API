create table roles (
    id bigint not null primary key,
    created_at timestamp without time zone not null default now(),
    status_record character varying(255) not null,
    updated_at timestamp without time zone not null,
    created_by character varying(255),
    updated_by character varying(255),
    name character varying(20) not null
);

alter table roles
    add constraint roles_name_unique unique (name);
