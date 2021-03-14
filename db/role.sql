BEGIN;

-- [new] role table and sequence
CREATE TABLE role
(
    id bigint NOT NULL,
    name text not null,
    CONSTRAINT role_pkey PRIMARY KEY (id)
);
ALTER TABLE role OWNER to lvms;
CREATE SEQUENCE seq_role
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_role OWNER TO lvms;

-- [new] role table and sequence
CREATE TABLE role_person
(
    id bigint NOT NULL,
    role_id bigint not null,
    person_id bigint not null,
    CONSTRAINT role_person_pkey PRIMARY KEY (id),
    CONSTRAINT fk_person_id FOREIGN KEY (person_id)
      REFERENCES public.person (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id)
      REFERENCES public.role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE role OWNER to lvms;
CREATE SEQUENCE seq_role
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
ALTER SEQUENCE seq_role OWNER TO lvms;

COMMIT;