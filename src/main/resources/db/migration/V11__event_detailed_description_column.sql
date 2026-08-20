-- V3 lengthened "description" itself so the "En détail" section had enough
-- text to show, at the cost of "À venir" cards teasing a full paragraph via
-- firstSentence() truncation. This gives detail its own column instead:
-- description goes back to being the short teaser, detailed_description
-- holds the long write-up ("En détail" now reads from this new column).
alter table events add column detailed_description text;

update events set detailed_description = description
where title in ('Concert de rentrée', 'Soirée Gospel & Louange', 'Concert de Noël');

update events set description = 'Notre premier concert de la saison, ouvert à tous.'
where title = 'Concert de rentrée';

update events set description = 'Une soirée en co-plateau avec deux autres chorales franciliennes.'
where title = 'Soirée Gospel & Louange';

update events set description = 'Notre rendez-vous annuel, entre chants traditionnels et negro spirituals.'
where title = 'Concert de Noël';
