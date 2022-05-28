insert into roles (id, name, created_at, status_record, created_by, updated_at)
values ('manager', 'MANAGER', current_timestamp, 'ACTIVE', 'user', current_timestamp),
        ('super_admin', 'SUPER_ADMIN', current_timestamp, 'ACTIVE', 'user', current_timestamp),
        ('admin', 'ADMIN', current_timestamp, 'ACTIVE', 'user', current_timestamp),
        ('member', 'MEMBER', current_timestamp, 'ACTIVE', 'user', current_timestamp);