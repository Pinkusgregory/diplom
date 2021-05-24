package controllers

import java.security.Principal

trait Context
{
  def principal: Principal
  def ip: String
}
