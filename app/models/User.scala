package models

import org.squeryl.KeyedEntity

case class User(
                 id: String,
                 email: String,
                 firstName: String,
                 lastName: String,
                 password: String
               ) extends KeyedEntity[String] {

  def isAdmin: Boolean = false
}
