-- Demo member accounts. Password for both is "gospel2026" (bcrypt hash below),
-- same demo credentials the frontend already advertised on the login page.
insert into members (username, password_hash, full_name, role) values
    ('marie', '$2b$10$SyH/IbPZoAeG6tDn0NKgbugF4NmiXZ.q85.3ttST4cqI/2jkJb6lm', 'Marie Kouadio', 'MEMBER'),
    ('jean',  '$2b$10$SyH/IbPZoAeG6tDn0NKgbugF4NmiXZ.q85.3ttST4cqI/2jkJb6lm', 'Jean Mbala',    'MEMBER');

insert into choristers (name, voice_part, description, image_url, display_order) values
    ('Marie Kouadio',   'SOPRANO', 'Choriste depuis 2014, elle anime aussi les répétitions de pupitre soprano.', '/images/chorister-photo-1.jpg', 1),
    ('Jean Mbala',      'TENOR',   'Membre fondateur de la chorale, passionné de chant traditionnel congolais.', '/images/chorister-photo-2.jpg', 2),
    ('Aïcha Diallo',    'ALTO',    'Arrivée en 2019, elle partage son temps entre le chant et la formation vocale des nouveaux membres.', '/images/chorister-photo-3.jpg', 3),
    ('Emmanuel Touré',  'BASSE',   'Voix grave du groupe depuis 2016, également responsable du matériel de sonorisation.', '/images/chorister-photo-4.jpg', 4),
    ('Grace Ndiaye',    'SOPRANO', 'Chante avec la chorale depuis son adolescence, aujourd''hui soliste sur plusieurs titres.', '/images/chorister-photo-5.jpg', 5),
    ('David Kamga',     'TENOR',   'Musicien de formation, il accompagne aussi la chorale au clavier lors des répétitions.', '/images/chorister-photo-6.jpg', 6),
    ('Ruth Amina',      'ALTO',    'Elle rejoint la chorale en 2021 et coordonne aujourd''hui les tenues de scène.', '/images/chorister-photo-7.jpg', 7),
    ('Samuel Eboa',     'BASSE',   'Choriste et diacre, il assure aussi l''accueil lors des concerts et des cultes.', '/images/chorister-photo-8.jpg', 8);

insert into songs (id, title, voicing, musical_key) values
    (1, 'Oh Happy Day', 'Chorale complète', 'Sol majeur'),
    (2, 'Total Praise', 'SATB', 'Mi bémol majeur'),
    (3, 'Way Maker', 'Chorale complète', 'Do majeur'),
    (4, 'Nobody Greater', 'Soprano / Alto', 'Fa majeur'),
    (5, 'Ndinga Yo (chant traditionnel)', 'Chorale complète', 'Ré mineur');
select setval('songs_id_seq', (select max(id) from songs));

insert into audio_tracks (song_id, track_type, file_url) select s.id, t.track_type, '/audio/' || s.id || '-' || lower(t.track_type) || '.mp3'
from songs s cross join (values ('SOPRANO'), ('ALTO'), ('TENOR'), ('BASSE'), ('CHOEUR'), ('INSTRUMENTAL')) as t(track_type);

insert into events (title, event_date, location, description) values
    ('Concert de rentrée', date '2026-09-20', 'Église Saint-Merri, Paris', 'Notre premier concert de la saison, ouvert à tous.'),
    ('Soirée Gospel & Louange', date '2026-11-08', 'Salle Pleyel, Paris', 'Une soirée en co-plateau avec deux autres chorales franciliennes.'),
    ('Concert de Noël', date '2026-12-20', 'Cathédrale Notre-Dame-des-Champs', 'Notre rendez-vous annuel, entre chants traditionnels et negro spirituals.'),
    ('Festival Gospel sous les Étoiles', date '2025-06-14', 'Parc de la Villette, Paris', null),
    ('Veillée de Noël', date '2024-12-25', 'Église Saint-Merri, Paris', null),
    ('Mariage — prestation privée', date '2024-11-02', 'Château de Vincennes', null);

insert into gallery_photos (label, image_url, display_order) values
    ('Photo de concert 1', '/images/gallery-photo-1.jpg', 1),
    ('Photo de concert 2', '/images/gallery-photo-2.jpg', 2),
    ('Photo de concert 3', '/images/gallery-photo-3.jpg', 3),
    ('Photo de concert 4', '/images/gallery-photo-4.jpg', 4),
    ('Photo de concert 5', '/images/gallery-photo-5.jpg', 5),
    ('Photo de concert 6', '/images/gallery-photo-6.jpg', 6),
    ('Photo de concert 7', '/images/gallery-photo-7.jpg', 7),
    ('Photo de concert 8', '/images/gallery-photo-8.jpg', 8);

insert into gallery_videos (title, youtube_id, display_order) values
    ('Extrait live 1', 'GWDIwlpFIRU', 1),
    ('Extrait live 2', 'lxjpPjrkay4', 2),
    ('Extrait live 3', 'ZMsrcyvCJXM', 3);

insert into news_items (item_date, title) values
    ('2 août 2026', 'Ouverture des inscriptions pour la saison 2026-2027'),
    ('18 juil. 2026', 'Retour en images sur le Festival Gospel sous les Étoiles'),
    ('3 juil. 2026', 'Vocal Harmony''s rejoint le collectif Gospel Île-de-France'),
    ('20 juin 2026', 'Un nouvel album en préparation pour 2027');

insert into partners (label, image_url, display_order) values
    ('Logo partenaire 1', '/images/partner-1.jpg', 1),
    ('Logo partenaire 2', '/images/partner-2.jpg', 2),
    ('Logo partenaire 3', '/images/partner-3.jpg', 3),
    ('Logo partenaire 4', '/images/partner-4.jpg', 4),
    ('Logo partenaire 5', '/images/partner-5.jpg', 5);

insert into cds (id, title, release_year, price, description, image_url) values
    (1, 'Racines & Lumière', 2019, 15.00, 'Notre premier album, entre chants traditionnels africains et gospel contemporain.', '/images/cd-cover-1.jpg'),
    (2, 'Debout Nous Chantons', 2022, 15.00, 'Un hymne à l''unité et à la joie, enregistré en live lors de notre concert annuel.', '/images/cd-cover-2.jpg'),
    (3, 'Vers la Lumière', 2025, 18.00, 'Notre dernier opus, mêlant chorale, percussions et cuivres.', '/images/cd-cover-3.jpg');
select setval('cds_id_seq', (select max(id) from cds));
