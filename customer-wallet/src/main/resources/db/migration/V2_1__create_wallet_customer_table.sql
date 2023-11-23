CREATE TABLE IF NOT EXISTS wallet_customer (
    id BIGINT PRIMARY KEY,
    balance NUMERIC NOT NULL DEFAULT 0.0,
    customer_id BIGINT NOT NULL
);