package controllers

import java.security.Principal

object AnonymousContext extends Context {
  def principal: Principal = Anonymous
  def ip = null
}
