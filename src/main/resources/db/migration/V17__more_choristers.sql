-- 1 new chorister, photo already dropped into
-- public/images/choristers/ following the "Nom - Pupitre.ext" convention
-- (parsed the same way the original roster in V6 was). The Choristes page
-- groups by pupitre and sorts alphabetically within each group at render
-- time, so display_order here only needs to keep the table itself tidy.
insert into choristers (name, voice_part, description, image_url, display_order) values
    ('Coline', 'Soprano', '', '/images/choristers/Coline - Soprano.jpg', 48);
