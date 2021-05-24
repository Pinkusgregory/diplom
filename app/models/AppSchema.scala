package models

import org.squeryl.Schema
import services.RememberMeToken

object AppSchema extends Schema {

  implicit val user = table[User]
  implicit val role = table[Role]
  implicit val userToRole = table[UserToRole]
  implicit val task = table[Task]
  implicit val company = table[Company]
  implicit val rememberMeToken = table[RememberMeToken]

}