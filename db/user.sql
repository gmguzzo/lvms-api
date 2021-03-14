BEGIN;

-- [new] user table and sequence
CREATE TABLE "user"
(
    id bigint NOT NULL,
    created timestamp with time zone NOT NULL,
    updated timestamp with time zone NOT NULL,
    username text not null,
    "password" text not null,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);
ALTER TABLE "user" OWNER to lvms;
CREATE SEQUENCE seq_user
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_user OWNER TO lvms;

COMMIT;