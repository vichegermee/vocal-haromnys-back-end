-- Member accounts (auth). "role" is unused by the authorization rules today
-- (every protected endpoint just requires "logged in"), it's there so an
-- ADMIN role can be introduced later without a migration.
create table members (
    id            bigserial primary key,
    username      varchar(60) not null unique,
    password_hash varchar(255) not null,
    full_name     varchar(150) not null,
    role          varchar(20) not null default 'MEMBER',
    created_at    timestamp not null default now()
);

create table choristers (
    id             bigserial primary key,
    name           varchar(150) not null,
    voice_part     varchar(20) not null,
    description    text not null,
    image_url      varchar(255) not null,
    display_order  integer not null default 0
);

create table songs (
    id           bigserial primary key,
    title        varchar(200) not null,
    voicing      varchar(150) not null,
    musical_key  varchar(50) not null
);

create table audio_tracks (
    id          bigserial primary key,
    song_id     bigint not null references songs(id) on delete cascade,
    track_type  varchar(20) not null,
    file_url    varchar(255) not null
);
create index idx_audio_tracks_song_id on audio_tracks(song_id);

create table events (
    id           bigserial primary key,
    title        varchar(200) not null,
    event_date   date not null,
    location     varchar(200) not null,
    description  text
);

create table gallery_photos (
    id             bigserial primary key,
    label          varchar(200) not null,
    image_url      varchar(255) not null,
    display_order  integer not null default 0
);

create table gallery_videos (
    id             bigserial primary key,
    title          varchar(200) not null,
    youtube_id     varchar(50) not null,
    display_order  integer not null default 0
);

create table news_items (
    id         bigserial primary key,
    item_date  varchar(50) not null,
    title      varchar(300) not null
);

create table partners (
    id             bigserial primary key,
    label          varchar(200) not null,
    image_url      varchar(255) not null,
    display_order  integer not null default 0
);

create table cds (
    id            bigserial primary key,
    title         varchar(200) not null,
    release_year  integer not null,
    price         numeric(10,2) not null,
    description   text not null,
    image_url     varchar(255) not null
);

create table cd_orders (
    id                   bigserial primary key,
    cd_id                bigint references cds(id) on delete set null,
    -- snapshot of the CD at order time, so a later price/title change never
    -- rewrites history for an order that already happened
    cd_title_snapshot     varchar(200) not null,
    unit_price_snapshot   numeric(10,2) not null,
    customer_name        varchar(150) not null,
    customer_email       varchar(150) not null,
    quantity             integer not null default 1,
    message               text,
    status                varchar(20) not null default 'RECEIVED',
    created_at            timestamp not null default now()
);
create index idx_cd_orders_cd_id on cd_orders(cd_id);

create table donations (
    id           bigserial primary key,
    amount       numeric(10,2) not null,
    donor_name   varchar(150) not null,
    donor_email  varchar(150) not null,
    status       varchar(20) not null default 'RECEIVED',
    created_at   timestamp not null default now()
);

create table reservations (
    id                bigserial primary key,
    event_type        varchar(150) not null,
    desired_date      date not null,
    budget            varchar(100),
    location          varchar(200) not null,
    chorister_count   varchar(50),
    message           text,
    requester_name    varchar(150) not null,
    requester_email   varchar(150) not null,
    status            varchar(20) not null default 'RECEIVED',
    created_at        timestamp not null default now()
);

create table join_applications (
    id                 bigserial primary key,
    voice_part         varchar(20) not null,
    experience         varchar(255),
    availability       varchar(255),
    audio_link         varchar(255),
    motivation         text not null,
    applicant_name     varchar(150) not null,
    applicant_email    varchar(150) not null,
    status             varchar(20) not null default 'RECEIVED',
    created_at         timestamp not null default now()
);
