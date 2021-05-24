package controllers

import java.security.Principal

trait UserPrincipal extends Principal {
  def id: String
  def getName: String = id.toString
}

case class ConcreteUserPrincipal(id: String) extends UserPrincipal
