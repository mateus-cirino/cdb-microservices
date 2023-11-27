CREATE TABLE IF NOT EXISTS wallet_cdb_log (
    id SERIAL PRIMARY KEY,
    wallet_cdb_id BIGINT,
    old_amount NUMERIC,
    new_amount NUMERIC,
    change_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION log_wallet_cdb_changes()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.amount != NEW.amount THEN
        INSERT INTO wallet_cdb_log (wallet_cdb_id, old_amount, new_amount)
        VALUES (NEW.id, OLD.amount, NEW.amount);
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER wallet_cdb_trigger
AFTER UPDATE ON wallet_cdb
FOR EACH ROW
EXECUTE FUNCTION log_wallet_cdb_changes();