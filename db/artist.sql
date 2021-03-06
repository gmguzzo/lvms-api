BEGIN;

-- [new] artist table and sequence
CREATE TABLE artist
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    artist_name text NOT NULL,
    genre text,
    since text,
    CONSTRAINT artist_pkey PRIMARY KEY (id)
);
ALTER TABLE artist OWNER to lvms;
CREATE SEQUENCE seq_artist
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_artist OWNER TO lvms;

COMMIT;