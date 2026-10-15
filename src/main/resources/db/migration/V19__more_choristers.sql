-- 2 new choristers, photo already dropped into
-- public/images/choristers/ following the "Nom - Pupitre.ext" convention
-- (parsed the same way the original roster in V6 was). The Choristes page
-- groups by pupitre and sorts alphabetically within each group at render
-- time, so display_order here only needs to keep the table itself tidy.
insert into choristers (name, voice_part, description, image_url, display_order) values
    ('Vanessa', 'Soprano', '', '/images/choristers/Vanessa - Sopra.jpeg', 49);
insert into choristers (name, voice_part, description, image_url, display_order) values
    ('Alix', 'Alto', '', '/images/choristers/Alix - Alto.jpeg', 50);