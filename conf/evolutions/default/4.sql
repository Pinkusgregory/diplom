# UserToRole schema

#--- !Ups

CREATE TABLE "UserToRole"
(
  id character varying NOT NULL,
  userId character varying NOT NULL,
  roleId character varying NOT NULL,
  CONSTRAINT userToRole_pkey PRIMARY KEY (id),
  CONSTRAINT userToRole_userId_fkey FOREIGN KEY (userId) REFERENCES "User"(id),
  CONSTRAINT userToRole_roleId_fkey FOREIGN KEY (roleId) REFERENCES "Role"(id)
);

#--- !Downs

DROP TABLE "UserToRole";