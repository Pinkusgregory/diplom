package utils

import java.security.SecureRandom

import models._
import controllers._
import play.api.mvc._
import services.RememberMeTokenService
import utils._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.annotation.tailrec
import scala.concurrent.Future
import scala.util.Random

trait Auth extends SessionHelper {

    self: Controller =>

    lazy val rememberMeTokenService = new RememberMeTokenService

    def processRequest[A <: AnyContent, T](f: RequestContext => T, redirectWrapper: Result => T)(req: Request[A]) = {
        val principal = principalFromSession(req.session)
        val contextWrapper = RequestContext(req, principal)
        principal match {
          case Anonymous => checkRememberMe(req) match {
              case Some(userId) => redirectWrapper(redirectLoggedIn(userId)(Redirect(req.uri))(req))
              case None => f(contextWrapper)
          }
          case _ => f(contextWrapper)
        }
    }

    def optionalUserAction(
                            f: RequestContext => Result,
                            bodyParser: BodyParser[AnyContent] = BodyParsers.parse.anyContent
                          ): Action[AnyContent] = {
        Action(bodyParser)(processRequest(f, redirect => redirect))
    }

    def optionalUserActionAsync(
                                 f: RequestContext => Future[Result],
                                 bodyParser: BodyParser[AnyContent] = BodyParsers.parse.anyContent
                               ): Action[AnyContent] = {
        Action.async(bodyParser)(processRequest(f, redirect => Future.successful(redirect)))
    }

    private def checkRememberMe[A](request: Request[A]): Option[Id] = {
        request.cookies.get(RememberMe.COOKIE_NAME).map(r => RememberMe.decodeFromCookie(Option(r)))
            .filter(checkRememberMeToken).flatMap(_.userId)
    }

    def checkRememberMeToken(rememberMe: RememberMe) = {
        val persistedToken = rememberMeTokenService.findByUserIdAndSeries(rememberMe.userId.get, rememberMe.series.get)
        persistedToken.exists(_.token.equals(PasswordHelper.sha512(rememberMe.token.get.toString)))
    }

    private val random = new Random(new SecureRandom())

    @tailrec
    final def generateSessionId[A](implicit request: Request[A]): String = {
        val table = "abcdefghijklmnopqrstuvwxyz1234567890-_.!~*'()"
        val token = Stream.continually(random.nextInt(table.size)).map(table).take(64).mkString
        if (resolver.exists(token)) generateSessionId(request) else token
    }

    def redirectLoggedIn[A](userId: Id)(redirect: Result)(implicit request: Request[A]): Result = {
        val sessionId = generateSessionId(request)
        resolver.addSessionToUser(sessionId, userId, sessionTimeoutInSeconds)
        redirect.withSession(Session() + ("sessionId" -> sessionId))
    }


    def resolver[A](implicit request: Request[A]): RelationResolver[Id] = new RelationResolver[Id]

    def basicAuth[A, U](authHandler: BasicAuthHandler[U])(action: Action[A]) = Action.async(action.parser) { request =>
        request.headers.get("Authorization").flatMap { authorization =>
            authorization.split(" ").drop(1).headOption.filter { encoded =>
                new String(org.apache.commons.codec.binary.Base64.decodeBase64(encoded.getBytes)).split(":").toList match {
                    case login :: password :: Nil => authHandler.authenticate(login, password).nonEmpty
                    case _ => false
                }
            }.map(_ => action(request))
        }.getOrElse {
            Future.successful(Unauthorized.withHeaders("WWW-Authenticate" -> """Basic realm="Secured""""))
        }
    }
}

trait BasicAuthHandler[U] {
    def authenticate(login: String, password: String): Option[U]
}
