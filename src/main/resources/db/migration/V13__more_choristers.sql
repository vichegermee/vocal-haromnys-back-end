-- 7 new choristers, photos already dropped into
-- public/images/choristers/ following the "Nom - Pupitre.ext" convention
-- (parsed the same way the original roster in V6 was). The Choristes page
-- groups by pupitre and sorts alphabetically within each group at render
-- time, so display_order here only needs to keep the table itself tidy.
insert into choristers (name, voice_part, description, image_url, display_order) values
    ('Artémise', 'Alto', '', '/images/choristers/Artémise - Alto.png', 33),
    ('Brigitte', 'Alto', '', '/images/choristers/Brigitte - Alto.png', 34),
    ('Dan', 'Basse', '', '/images/choristers/Dan - Basse.png', 35),
    ('Laurence', 'Alto', '', '/images/choristers/Laurence - Alto.png', 36),
    ('Loïc', 'Ténor', '', '/images/choristers/Loïc - Ténor.png', 37),
    ('Marie-Ange', 'Soprano', '', '/images/choristers/Marie_Ange - Soprano.png', 38),
    ('Nadine', 'Soprano', '', '/images/choristers/Nadine - Soprano.png', 39);

-- Nadine LEGOAS (support_team, added without a photo — see V12) now has a
-- real photo in the choristers folder, dropped in alongside the roster
-- above.
update support_team
set photo_filename = 'Nadine - Soprano.png'
where first_name = 'Nadine' and last_name = 'LEGOAS';
