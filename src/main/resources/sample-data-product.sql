-- MACBOOK
insert into products (id, name, price, quantity, description, created_by, created_at, updated_at, status_record)
values ('macbook-air-m1-2020', 'Apple MacBook Air M1 2020', 12950000, 24, 'This is Apple MacBook Air M1 2020 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('macbook-m1-2020', 'Apple MacBook Pro M1 2020', 19140000, 35, 'This is Apple MacBook Pro M1 2020 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('macbook-air-2019', 'Apple MacBook Air 2019', 13490000, 35, 'This is Apple MacBook Air 2019 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('macbook-pro-14-2021', 'Apple MacBook Pro 14-inch 2021', 33390000, 20, 'This is Apple MacBook Pro 14-inch 2021 description', 'User', current_timestamp, current_timestamp, 'ACTIVE');

-- LENOVO
insert into products (id, name, price, quantity, description, created_by, created_at, updated_at, status_record)
values ('lenovo-legion-5', 'Lenovo Legion 5 Pro', 18199000, 27, 'This is Lenovo Legion 5 Pro description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('lenovo-thinkpad-t430', 'Lenovo ThinkPad T430', 6279000, 28, 'This is Lenovo ThinkPad T430 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('lenovo-thinkpad-t420', 'Lenovo ThinkPad T420', 16999000, 17, 'This is Lenovo ThinkPad T420 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('lenovo-thinkpad-x260', 'Lenovo ThinkPad X260', 15400000, 24, 'This is Lenovo ThinkPad X260 description', 'User', current_timestamp, current_timestamp, 'ACTIVE');

-- ACER
insert into products (id, name, price, quantity, description, created_by, created_at, updated_at, status_record)
values ('acer-nitro-5', 'Acer Nitro 5', 10701000, 30, 'This is Acer Nitro 5 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('acer-aspire-5', 'Acer Aspire 5 A514', 8750000, 35, 'This is Acer Aspire 5 A514 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('acer-aspire-e5', 'Acer Aspire E5-475G', 6100000, 37, 'This is Acer Aspire E5-475G description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('acer-aspire-3', 'Acer Aspire 3 a314-32', 4937500, 50, 'This is Acer Aspire 3 a314-32 description', 'User', current_timestamp, current_timestamp, 'ACTIVE');

-- HP
insert into products (id, name, price, quantity, description, created_by, created_at, updated_at, status_record)
values ('hp-pavilion-x360', 'HP Pavilion X360', 7599000, 5, 'This is HP Pavilion X360 description', 'User', current_timestamp, current_timestamp, 'ACTIVE'),
        ('hp-envy-x360', 'HP Envy X360', 10149000, 8, 'This is HP Envy X360 description', 'User', current_timestamp, current_timestamp, 'ACTIVE');

insert into products (id, name, price, quantity, description, created_by, updated_by, created_at, updated_at, status_record)
values ('acer-nitro-5', 'Acer Nitro 5', 10701000, 30, 'This is Acer Nitro 5 description', 'User', 'Bayu', current_timestamp, current_timestamp, 'ACTIVE');