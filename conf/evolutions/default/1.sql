# User schema

# --- !Ups

CREATE TABLE "User"
(
  id character varying NOT NULL,
  email character varying NOT NULL,
  firstName character varying NOT NULL,
  lastName character varying NOT NULL,
  password character varying NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE "User";