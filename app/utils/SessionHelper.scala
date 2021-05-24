package utils

import controllers.{Anonymous, ConcreteUserPrincipal}
import play.api.mvc.Session

object SessionHelper extends SessionHelper

trait SessionHelper {


    /**
     * A type that represents a user in your application.
     * `User`, `Account` and so on.
     */

    /**
     * A type that is used to identify a user.
     * `String`, `Int`, `Long` and so on.
     */
    type Id = String

    /**
     * The session timeout in seconds
     */
    val sessionTimeoutInSeconds: Int = 3600

    def userIdFromSession(session: Session) = {
        val resolver = new RelationResolver[String]
        for {
            sessionId <- session.get("sessionId")
            userId <- resolver.sessionId2userId(sessionId)
        } yield {
            resolver.prolongTimeout(sessionId, sessionTimeoutInSeconds)
            userId
        }
    }

    def principalFromSession(session: Session) = {

        userIdFromSession(session) match {
            case Some(userId) => ConcreteUserPrincipal(userId)
            case _ => Anonymous
        }

    }

}
