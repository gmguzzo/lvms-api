BEGIN;

-- [new] song table and sequence
CREATE TABLE song
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    title text NOT NULL,
    description text,
    lyric text NOT NULL,
    album_id bigint,
    CONSTRAINT song_pkey PRIMARY KEY (id),
    CONSTRAINT fk_album_id FOREIGN KEY (album_id)
      REFERENCES public.album (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE song OWNER to lvms;
CREATE SEQUENCE seq_song
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_song OWNER TO lvms;

COMMIT;