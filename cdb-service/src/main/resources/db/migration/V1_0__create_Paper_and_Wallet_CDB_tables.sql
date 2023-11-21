CREATE TABLE IF NOT EXISTS paper (
    id BIGINT PRIMARY KEY,
    value NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS wallet_cdb (
    id BIGINT PRIMARY KEY,
    paper_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    amount NUMERIC NOT NULL,
    FOREIGN KEY (paper_id) REFERENCES paper(id)
);