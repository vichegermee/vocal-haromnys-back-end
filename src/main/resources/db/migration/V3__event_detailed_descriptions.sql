-- Lengthen the three upcoming events' descriptions so the Événements page has
-- enough to show in its "En détail" section, not just the one-line teaser.
update events
set description = 'Pour ouvrir la saison, Vocal Harmony''s vous invite à l''église Saint-Merri pour un concert '
    || 'gratuit et ouvert à tous. Au programme : les negro spirituals qui ont fait notre réputation, quelques '
    || 'nouveautés travaillées tout l''été, et la présentation des choristes qui nous ont rejoints cette année. '
    || 'Une belle occasion de (re)découvrir la chorale avant une saison chargée.'
where title = 'Concert de rentrée';

update events
set description = 'En co-plateau avec deux autres chorales franciliennes, cette soirée à la Salle Pleyel mêlera '
    || 'gospel traditionnel et louange contemporaine sur une scène partagée. Chaque chorale interprétera son '
    || 'propre répertoire avant un final à voix réunies — plus de soixante-dix choristes chantant ensemble. '
    || 'Billetterie ouverte sur place, entrée libre dans la limite des places disponibles.'
where title = 'Soirée Gospel & Louange';

update events
set description = 'Notre rendez-vous annuel prend cette année ses quartiers à la Cathédrale Notre-Dame-des-Champs. '
    || 'Un programme qui alterne chants traditionnels de Noël et negro spirituals, avec une première partie '
    || 'a cappella suivie d''un accompagnement piano et percussions. La soirée se conclut, comme chaque année, '
    || 'par un chant repris avec le public.'
where title = 'Concert de Noël';
