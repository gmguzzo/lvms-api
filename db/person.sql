BEGIN;

-- [new] person table and sequence
CREATE TABLE person
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    username text not null,
    "password" text not null,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);
ALTER TABLE person OWNER to lvms;
CREATE SEQUENCE seq_person
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_person OWNER TO lvms;

COMMIT;