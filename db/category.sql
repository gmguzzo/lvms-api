BEGIN;

-- [new] category table and sequence
CREATE TABLE category
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    category_name text NOT NULL,
    description text,
    CONSTRAINT category_pkey PRIMARY KEY (id)
);
ALTER TABLE category OWNER to lvms;
CREATE SEQUENCE seq_category
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_category OWNER TO lvms;

-- [new] song_category table and sequence
CREATE TABLE song_category
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    song_id bigint NOT NULL,
    category_id bigint NOT NULL,
    main boolean NOT NULL,
    CONSTRAINT song_category_pkey PRIMARY KEY (id),
    CONSTRAINT fk_song_id FOREIGN KEY (song_id)
      REFERENCES public.song (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id)
      REFERENCES public.category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE song_category OWNER to lvms;
CREATE SEQUENCE seq_song_category
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_song_category OWNER TO lvms;

COMMIT;