-- The homepage hero and the About page "Notre histoire" photo were both
-- rotating carousels with their image list hardcoded in the frontend
-- (src/pages/Home.tsx, src/pages/About.tsx). This gives them the same
-- database-backed, CRUD-able model as the other showcase content (partners,
-- gallery, ...): one row per photo, ordered by display_order, served
-- publicly and editable by a logged-in member.
create table home_banners (
    id             bigserial primary key,
    image_url      varchar(255) not null,
    display_order  integer not null default 0
);

create table about_photos (
    id             bigserial primary key,
    image_url      varchar(255) not null,
    display_order  integer not null default 0
);

insert into home_banners (image_url, display_order) values
    ('/images/home/home-1.jpg', 1),
    ('/images/home/home-2.jpg', 2),
    ('/images/home/home-3.jpg', 3),
    ('/images/home/home-4.jpg', 4);

insert into about_photos (image_url, display_order) values
    ('/images/a-propos-1.jpg', 1),
    ('/images/a-propos-2.jpg', 2),
    ('/images/a-propos-3.jpg', 3);
