-- The 8 choristers seeded in V2 were placeholders. This replaces them with
-- the real roster: one row per photo in the frontend's
-- public/images/choristers/ folder, named "Nom - Pupitre.ext". name and
-- voice_part are parsed from the file name (same rule the frontend used to
-- apply client-side before this migration existed); voice_part is free text
-- here (not the 4-value VoicePart enum used by join_applications) because
-- the real roster includes non-singing roles (Manager, Batterie, Guitare,
-- Pianiste) alongside the singing sections. Two files (EVA.png, lana 2.jpg)
-- don't follow the "Nom - Pupitre" naming convention, so voice_part is left
-- blank for them. description has no source data for any of these, so it's
-- left blank too (the column stays NOT NULL, hence '' rather than NULL).
delete from choristers;

insert into choristers (name, voice_part, description, image_url, display_order) values
    ('Audrey G.', 'Soprano', '', '/images/choristers/Audrey G. - Soprano.jpg', 1),
    ('Audrey M.', 'Soprano', '', '/images/choristers/Audrey M. - Soprano.jpg', 2),
    ('Axel', 'Manager', '', '/images/choristers/Axel - Manager.jpg', 3),
    ('Betty', 'Alto', '', '/images/choristers/Betty -Alto.png', 4),
    ('Carole', 'Alto', '', '/images/choristers/Carole - Alto.jpg', 5),
    ('Catherine', 'Alto', '', '/images/choristers/Catherine - Alto.jpg', 6),
    ('Céline L.', 'Alto', '', '/images/choristers/Céline L. - Alto.jpg', 7),
    ('Céline T.', 'Soprano', '', '/images/choristers/Céline T. - Soprano.jpg', 8),
    ('Christine', 'Alto', '', '/images/choristers/Christine - Alto.png', 9),
    ('Edna', 'Alto', '', '/images/choristers/Edna - Alto.jpg', 10),
    ('EVA', '', '', '/images/choristers/EVA.png', 11),
    ('Evora', 'Soprano', '', '/images/choristers/Evora - Soprano.png', 12),
    ('Fabien', 'Batterie', '', '/images/choristers/Fabien - Batterie.jpg', 13),
    ('Fayad', 'Ténor', '', '/images/choristers/Fayad - Ténor.jpg', 14),
    ('Françoise', 'Alto', '', '/images/choristers/Françoise - Alto.jpg', 15),
    ('Gérard', 'Guitare', '', '/images/choristers/Gérard - Guitare.jpg', 16),
    ('Glenn Horland', 'Basse', '', '/images/choristers/Glenn Horland - Basse.jpg', 17),
    ('Ingrid', 'Alto', '', '/images/choristers/Ingrid - Alto.jpg', 18),
    ('Ismael', 'Pianiste', '', '/images/choristers/Ismael - Pianiste.jpg', 19),
    ('Kendra', 'Soprano', '', '/images/choristers/Kendra - Soprano.jpg', 20),
    ('lana 2', '', '', '/images/choristers/lana 2.jpg', 21),
    ('Malika', 'Soprano', '', '/images/choristers/Malika - Soprano.jpg', 22),
    ('Marlène', 'Alto', '', '/images/choristers/Marlène - Alto.jpg', 23),
    ('Murielle', 'Soprano', '', '/images/choristers/Murielle - Soprano.jpg', 24),
    ('Pauline', 'Soprano', '', '/images/choristers/Pauline - Soprano.jpg', 25),
    ('Rita', 'Soprano', '', '/images/choristers/Rita - Soprano.jpg', 26),
    ('Sonia', 'Soprano', '', '/images/choristers/Sonia - Soprano.jpg', 27),
    ('Stéphanie', 'Alto', '', '/images/choristers/Stéphanie - Alto.jpg', 28),
    ('Svetlana', 'Soprano', '', '/images/choristers/Svetlana - Soprano.jpg', 29),
    ('Véronique', 'Alto', '', '/images/choristers/Véronique - Alto.jpg', 30),
    ('Yannh', 'Basse', '', '/images/choristers/Yannh - Basse.jpg', 31),
    ('Zaïda', 'Alto', '', '/images/choristers/Zaïda - Alto.jpg', 32);
