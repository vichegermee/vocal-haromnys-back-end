alter table events add column poster_url varchar(500);

update events
set poster_url = '/images/master-event.jpeg'
where event_date = date '2026-11-14' and title like 'Gospel en Fête%ACT 2';
