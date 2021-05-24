package models

import org.squeryl.KeyedEntity

case class Company(
                    id: String,
                    title: String,
                    description: Option[String]
                  ) extends KeyedEntity[String]


