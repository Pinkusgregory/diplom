package controllers

import play.api.mvc.{AnyContent, Request, WrappedRequest}
import java.security.Principal

case class RequestContext(
                           request: Request[AnyContent],
                           principal: Principal,
                           pointCode: String = null
                         ) extends WrappedRequest(request) with Context {
    def ip = Option(request).fold("")(_.remoteAddress)
    def withPointCode(pointCode: String) = RequestContext(request, principal, pointCode)
}

object RequestContext
{
  def empty = RequestContext(null, null)

  def anonymousWithRuLanguage = RequestContext(null, Anonymous)
}
