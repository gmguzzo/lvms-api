BEGIN;

-- [new] album table and sequence
CREATE TABLE album
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    album_name text not null,
    artist_id bigint,
    genre text NOT NULL,
    debut text,
    CONSTRAINT album_pkey PRIMARY KEY (id),
    CONSTRAINT fk_artist_id FOREIGN KEY (artist_id)
      REFERENCES public.artist (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE album OWNER to lvms;
CREATE SEQUENCE seq_album
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_album OWNER TO lvms;

COMMIT;