package controllers

import java.security.Principal

case object Anonymous extends Principal {
  def getName: String = toString
}
