DROP SCHEMA IF EXISTS orden CASCADE;

CREATE SCHEMA orden;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS orden_status;
CREATE TYPE orden_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLED', 'CANCELLING');

DROP TABLE IF EXISTS orden.ordens CASCADE;

CREATE TABLE orden.ordens
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    restaurant_id uuid NOT NULL,
    tracking_id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    orden_status orden_status NOT NULL,
    failure_messages character varying COLLATE pg_catalog."default",
    CONSTRAINT ordens_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS orden.orden_items CASCADE;

CREATE TABLE orden.orden_items
(
    id bigint NOT NULL,
    orden_id uuid NOT NULL,
    product_id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    quantity integer NOT NULL,
    sub_total numeric(10,2) NOT NULL,
    CONSTRAINT orden_items_pkey PRIMARY KEY (id, orden_id)
);

ALTER TABLE orden.orden_items
    ADD CONSTRAINT "FK_orden_ID" FOREIGN KEY (orden_id)
    REFERENCES orden.ordens (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS orden.orden_address CASCADE;

CREATE TABLE orden.orden_address
(
    id uuid NOT NULL,
    orden_id uuid UNIQUE NOT NULL,
    street character varying COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying COLLATE pg_catalog."default" NOT NULL,
    city character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT orden_address_pkey PRIMARY KEY (id, orden_id)
);

ALTER TABLE orden.orden_address
    ADD CONSTRAINT "FK_orden_ID" FOREIGN KEY (orden_id)
    REFERENCES orden.ordens (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;