-- Members now have an email (needed to send generated credentials, and to
-- migrate/import choristers as real accounts). Nullable: the two existing
-- demo accounts (marie/jean) predate this and have none.
alter table members add column email varchar(150);

-- Seeds the very first super admin account, for Axel (choir president /
-- chef de chœur). This has to happen in a migration rather than through the
-- admin API, since no ADMIN account exists yet to call it — every other
-- admin from here on is created through the "Ajouter un membre" flow, which
-- generates its own password the same way this one was generated.
insert into members (username, password_hash, full_name, role, email) values
    ('axel', '$2b$10$Qvtxjq9vowgrJ8IVHi9bY.qosoXPWQJyxaez/4vni7aNJGIttKyLO', 'Axel Mbina', 'ADMIN', 'groupeharmonys1@gmail.com');
