package models

import org.squeryl.KeyedEntity

case class UserToRole(
                       id: String,
                       userId: String,
                       roleId: String
                     ) extends KeyedEntity[String]


