-- All three albums move to a flat 10€ price.
update cds set price = 10.00;

-- Shipping details + Stripe payment tracking for CD orders, mirroring the
-- donations payment columns added in V4. customer_phone and the shipping_*
-- columns are added NOT NULL DEFAULT '' so existing rows (if any were
-- created through the old pay-later flow) stay valid, then the default is
-- dropped so new rows must supply a real value via the application.
alter table cd_orders
    add column customer_phone      varchar(30)   not null default '',
    add column shipping_street     varchar(255)  not null default '',
    add column shipping_postal_code varchar(20)  not null default '',
    add column shipping_city       varchar(150)  not null default '',
    add column shipping_country    varchar(100)  not null default '',
    add column shipping_option     varchar(20)   not null default 'STANDARD',
    add column shipping_cost       numeric(10,2) not null default 0,
    add column total_amount        numeric(10,2) not null default 0,
    add column payment_status      varchar(20)   not null default 'PENDING',
    add column stripe_checkout_session_id varchar(255),
    add column stripe_payment_intent_id   varchar(255),
    add column paid_at             timestamp;

alter table cd_orders
    alter column customer_phone       drop default,
    alter column shipping_street      drop default,
    alter column shipping_postal_code drop default,
    alter column shipping_city        drop default,
    alter column shipping_country     drop default,
    alter column shipping_option      drop default,
    alter column shipping_cost        drop default,
    alter column total_amount         drop default;

create unique index idx_cd_orders_stripe_checkout_session_id
    on cd_orders (stripe_checkout_session_id)
    where stripe_checkout_session_id is not null;
