package models

import org.squeryl.KeyedEntity

case class Role(
                 id: String,
                 name: String
               ) extends KeyedEntity[String]


