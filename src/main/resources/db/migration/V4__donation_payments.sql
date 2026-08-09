-- Stripe payment tracking for donations. Kept separate from the existing
-- `status` (RequestStatus) column, which is a generic staff-workflow tracker
-- shared with reservations/join_applications/cd_orders and shouldn't gain
-- payment-specific semantics.
alter table donations
    add column payment_status varchar(20) not null default 'PENDING',
    add column stripe_checkout_session_id varchar(255),
    add column stripe_payment_intent_id varchar(255),
    add column paid_at timestamp;

create unique index idx_donations_stripe_checkout_session_id
    on donations (stripe_checkout_session_id)
    where stripe_checkout_session_id is not null;
