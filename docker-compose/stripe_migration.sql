-- Stripe payment channel: audit columns on account_hist.
-- Optional: settlement works without them, but then the Stripe session id
-- is not kept against the payment, which makes reconciliation harder.
-- Idempotent on MySQL 8+ / MariaDB 10.5+.

ALTER TABLE account_hist
  ADD COLUMN IF NOT EXISTS PAY_CHANNEL varchar(16) NULL COMMENT 'stripe | legacy',
  ADD COLUMN IF NOT EXISTS PAY_REF     varchar(80) NULL COMMENT 'provider reference, e.g. Stripe Checkout Session id';

CREATE INDEX IF NOT EXISTS ix_account_hist_pay_ref ON account_hist (PAY_REF);

-- For MySQL < 8.0.29 without IF NOT EXISTS support, use:
-- ALTER TABLE account_hist ADD COLUMN PAY_CHANNEL varchar(16) NULL, ADD COLUMN PAY_REF varchar(80) NULL;
-- CREATE INDEX ix_account_hist_pay_ref ON account_hist (PAY_REF);
