# Role schema

#--- !Ups

CREATE TABLE "Role"
(
  id character varying NOT NULL,
  name character varying NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (id)
);

#--- !Downs

DROP TABLE "Role";