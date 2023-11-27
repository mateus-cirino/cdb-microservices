CREATE TABLE IF NOT EXISTS wallet_customer_log (
    id SERIAL PRIMARY KEY,
    wallet_customer_id BIGINT,
    old_balance NUMERIC,
    new_balance NUMERIC,
    change_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION log_wallet_customer_changes()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.balance != NEW.balance THEN
        INSERT INTO wallet_customer_log (wallet_customer_id, old_balance, new_balance)
        VALUES (NEW.id, OLD.balance, NEW.balance);
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER wallet_customer_trigger
AFTER UPDATE ON wallet_customer
FOR EACH ROW
EXECUTE FUNCTION log_wallet_customer_changes();