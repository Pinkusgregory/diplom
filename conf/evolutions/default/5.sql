# Task schema

#--- !Ups

CREATE TABLE "Task"
(
  id character varying NOT NULL,
  description character varying,
  userId character varying NOT NULL,
  startTime timestamp NOT NULL,
  endTime timestamp NOT NULL,
  CONSTRAINT task_pkey PRIMARY KEY (id),
  CONSTRAINT task_userId_fkey FOREIGN KEY (userId) REFERENCES "User"(id)
);

#--- !Downs

DROP TABLE "Task";