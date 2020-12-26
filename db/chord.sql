BEGIN;

-- [new] chord table and sequence
CREATE TABLE chord
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    symbol text NOT NULL,
    bar integer,
    start_fret integer,
    bass_string integer,
    sounded_strings jsonb,
    muted_strings jsonb,
    diagram jsonb,
    CONSTRAINT chord_pkey PRIMARY KEY (id)
);
ALTER TABLE chord OWNER to lvms;
CREATE SEQUENCE seq_chord
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_chord OWNER TO lvms;

COMMIT;