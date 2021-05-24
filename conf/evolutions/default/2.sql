# Company schema

# --- !Ups

CREATE TABLE "Company"
(
  id character varying NOT NULL,
  title character varying NOT NULL,
  description character varying,
  CONSTRAINT company_pkey PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE "Company";