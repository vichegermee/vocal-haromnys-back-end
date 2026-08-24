-- "L'équipe d'accompagnement" section on the About page, right below
-- "Notre équipe". Same shape as admin_team, except photo_filename is
-- nullable: Nadine LEGOAS doesn't have a photo in the choristers folder
-- yet, and the frontend falls back to a placeholder when it's null.
create table support_team (
    id              bigserial primary key,
    first_name      varchar(100) not null,
    last_name       varchar(100) not null,
    title           varchar(300) not null,
    photo_filename  varchar(255),
    display_order   integer not null default 0
);

insert into support_team (first_name, last_name, title, photo_filename, display_order) values
    ('Catherine', 'MARESCAUX',
     'Référente bien-être & cohésion, à l''écoute des choristes, contribue à préserver un climat de confiance, de respect et de dialogue au sein du groupe.',
     'Catherine - Alto.jpg', 1),
    ('Nadine', 'LEGOAS',
     'En charge du suivi des participations aux activités, veille aux présences des choristes lors des prestations et événements.',
     null, 2),
    ('Pauline', 'RIGOBERT',
     'Référente Soprano – Suivi des répétitions. Veille au suivi des présences et accompagne l''assiduité du pupitre Soprano.',
     'Pauline - Soprano.jpg', 3),
    ('Carole', 'BOURGEOIS',
     'Référente Alto – Suivi des répétitions. Veille au suivi des présences et accompagne l''assiduité du pupitre Alto.',
     'Carole - Alto.jpg', 4);
