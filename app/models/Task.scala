package models

import java.sql.Timestamp

import org.squeryl.KeyedEntity

case class Task(
                 id: String,
                 description: Option[String],
                 userId: String,
                 startTime: Timestamp,
                 endTime: Timestamp
               ) extends KeyedEntity[String]


