create table products (
    id character varying(64) not null primary key default uuid_generate_v4(),
    created_at timestamp not null default now(),
    created_by character varying(255) not null,
    status_record character varying(255) not null,
    updated_at timestamp,
    updated_by character varying(255),
    name character varying(64) not null,
    price numeric(19,2) not null,
    quantity integer not null,
    description character varying(255) not null
);

