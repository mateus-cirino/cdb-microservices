ALTER TABLE wallet_cdb
ADD CONSTRAINT paper_id_customer_id_unique UNIQUE (paper_id, customer_id);