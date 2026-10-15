alter table events add column booking_url varchar(500);

update events
set booking_url = 'https://www.helloasso.com/associations/groupe-vocal-harmony-s/evenements/gospel-en-fete-act-2'
where event_date = date '2026-11-14' and title like 'Gospel en Fête%ACT 2';
