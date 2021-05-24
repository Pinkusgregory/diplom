package controllers

import models.RememberMe
import play.api.mvc.{DiscardingCookie, _}
import services.RememberMeToken
import utils.Auth

import scala.util.Random

trait LoginLogout {

    self: Controller with Auth =>

    def gotoLoginSucceededWithRedirect[A](userId: Id, rememberUser: Boolean)(redirect: Result)(implicit request: Request[A]): Result = {
        val sessionId = generateSessionId(request)
        resolver.addSessionToUser(sessionId, userId, sessionTimeoutInSeconds)
        val rememberMeCookieOpt = rememberMeCookie(rememberUser, userId)
        val resultWithSession = redirect.withSession(Session() + ("sessionId" -> sessionId))
        rememberMeCookieOpt.map(rmc => resultWithSession.withCookies(rmc)).getOrElse(resultWithSession)
    }

    def rememberMeCookie(toRemember: Boolean, userId: String) = {
        if (toRemember) Some{
            val rememberMe = RememberMe(userId, Random.nextLong(), Random.nextLong())
            persistRememberMeToken(rememberMe)
            RememberMe.encodeAsCookie(rememberMe)
        }
        else None
    }

    def gotoLogoutSucceeded[A](implicit rc: RequestContext): Result = {
        val request = rc.request
        request.session.get("sessionId") foreach resolver.removeBySessionId

        val remember = request.cookies.get(RememberMe.COOKIE_NAME)
        if(remember.isDefined) {
            val rememberMe = RememberMe.decodeFromCookie(remember)
            dropRememberMeToken(rememberMe)
        }
        Redirect(routes.MainController.index()).withNewSession.discardingCookies(DiscardingCookie(RememberMe.COOKIE_NAME))
    }

    def gotoLogoutSucceededWithRedirect[A](redirect: Result)(implicit rc: RequestContext): Result = {
        val request = rc.request
        request.session.get("sessionId") foreach resolver.removeBySessionId

        val remember = request.cookies.get(RememberMe.COOKIE_NAME)
        remember.foreach{ rm =>
            dropRememberMeToken(RememberMe.decodeFromCookie(Some(rm)))
        }
        redirect.withNewSession.discardingCookies(DiscardingCookie(RememberMe.COOKIE_NAME))
    }

    def persistRememberMeToken(rememberMe: RememberMe) = rememberMeTokenService.create(RememberMeToken(rememberMe))

    def dropRememberMeToken(rememberMe: RememberMe) { rememberMeTokenService.remove(RememberMeToken(rememberMe)) }

}

