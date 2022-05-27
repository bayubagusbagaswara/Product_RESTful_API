-- REFRESH TOKEN
create table refresh_tokens (
    id bigint not null primary key,
    created_at timestamp without time zone not null default now(),
    status_record character varying(255) not null,
    updated_at timestamp without time zone not null,
    created_by character varying(255),
    updated_by character varying(255),
    expiry_date timestamp without time zone not null,
    refresh_token character varying(255) not null,
    id_user bigint
);

alter table refresh_tokens
    add constraint refresh_tokens_refresh_token_unique unique (refresh_token);

alter table refresh_tokens
    add constraint fk_refresh_token_user_id foreign key (id_user) references users(id);





