# RememberMeToken schema

#--- !Ups

CREATE TABLE "RememberMeToken"
(
  userId character varying NOT NULL,
  series bigint,
  token character varying,
  CONSTRAINT task_pkey PRIMARY KEY (userId)
);

#--- !Downs

DROP TABLE "RememberMeToken";