package models

import play.api.mvc.CookieBaker

case class RememberMe(data: Map[String, String] = Map.empty[String, String]) {

  import RememberMe._

  def get(key: String) = data.get(key)

  def isEmpty: Boolean = data.isEmpty

  def +(kv: (String, String)) = copy(data + kv)

  def -(key: String) = copy(data - key)

  def series: Option[Long] = AsLong(data.get(SERIES_NAME))

  def userId: Option[String] = data.get(USER_ID_NAME)

  def token: Option[Long] = AsLong(data.get(TOKEN_NAME))

  def apply(key: String) = data(key)

  def AsLong(value: Option[String]) = Some(value.getOrElse("0").toLong)

}

object RememberMe extends CookieBaker[RememberMe] {

  def apply(userId: String, series: Long, token: Long): RememberMe = {
    val map = Map(
      RememberMe.USER_ID_NAME -> userId,
      RememberMe.SERIES_NAME -> series.toString,
      RememberMe.TOKEN_NAME -> token.toString)
    RememberMe(map)
  }

  val COOKIE_NAME = "REMEMBER_ME"

  val SERIES_NAME = "series"
  val USER_ID_NAME = "userId"
  val TOKEN_NAME = "token"

  val emptyCookie = new RememberMe

  override val isSigned = true
  override val secure = false
  override val maxAge = Some(60 * 60 * 24 * 30)

  def deserialize(data: Map[String, String]) = new RememberMe(data)

  def serialize(rememberme: RememberMe) = rememberme.data

}
