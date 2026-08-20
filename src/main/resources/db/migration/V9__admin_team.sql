-- The "Notre équipe" section on the About page shows a handful of
-- choristers who also hold a coordination role (president, treasurer, ...).
-- photo_filename is just the file name (not a full path): these people
-- already have a photo in the choristers folder, so the frontend builds the
-- URL as /images/choristers/<photo_filename>, the same folder the
-- "choristers" table's real roster (V6) already points into.
create table admin_team (
    id              bigserial primary key,
    first_name      varchar(100) not null,
    last_name       varchar(100) not null,
    title           varchar(300) not null,
    photo_filename  varchar(255) not null,
    display_order   integer not null default 0
);

insert into admin_team (first_name, last_name, title, photo_filename, display_order) values
    ('Axel', 'MBINA',
     'Président de la chorale, responsable de son développement, de sa gestion et chef de chœur',
     'Axel - Manager.jpg', 1),
    ('Svetlana', 'ILEMBI',
     'Trésorière et Secrétaire générale, responsable du volet comptable et logistique, de sa gestion',
     'Svetlana - Soprano.jpg', 2),
    ('Audrey Denise', 'MEKAMNE',
     'Responsable de la communication du groupe, coordonne et développe la communication du chœur',
     'Audrey M. - Soprano.jpg', 3),
    ('Edna', 'YEMBI',
     'Responsable de la communication du groupe, développe le réseau de partenaires',
     'Edna - Alto.jpg', 5);
