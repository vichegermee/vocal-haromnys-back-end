-- gallery_photos (seeded in V2) only had 8 rows, but a 9th real photo
-- (public/images/gallery-photo-9.jpg on the frontend) was added since and
-- never made it into the database — the gallery page was quietly one photo
-- short of what's actually on disk. This replaces the seed with one row per
-- photo file actually present, same principle as V6 (choristers) and V7
-- (home/about carousels): the count in the database must match the count of
-- real photos.
delete from gallery_photos;

insert into gallery_photos (label, image_url, display_order) values
    ('Photo de concert 1', '/images/gallery-photo-1.jpg', 1),
    ('Photo de concert 2', '/images/gallery-photo-2.jpg', 2),
    ('Photo de concert 3', '/images/gallery-photo-3.jpg', 3),
    ('Photo de concert 4', '/images/gallery-photo-4.jpg', 4),
    ('Photo de concert 5', '/images/gallery-photo-5.jpg', 5),
    ('Photo de concert 6', '/images/gallery-photo-6.jpg', 6),
    ('Photo de concert 7', '/images/gallery-photo-7.jpg', 7),
    ('Photo de concert 8', '/images/gallery-photo-8.jpg', 8),
    ('Photo de concert 9', '/images/gallery-photo-9.jpg', 9);
