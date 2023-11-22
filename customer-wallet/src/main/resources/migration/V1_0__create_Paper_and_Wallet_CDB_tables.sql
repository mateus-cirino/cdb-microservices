CREATE TABLE IF NOT EXISTS wallet_customer (
    id BIGINT PRIMARY KEY,
    balance NUMERIC NOT NULL,
    customer_id BIGINT NOT NULL
);
