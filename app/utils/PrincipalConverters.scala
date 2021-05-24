package utils

import java.security.Principal

import controllers._
import models._
import services.UserService

object PrincipalConverters {
    lazy val userService = new UserService

    implicit class ContextWrapper(val ctx: Context) extends AnyVal {

        def principalWrapper = ctx.principal
        def authUserOpt: Option[User] = principalWrapper.toUserOpt
        def authUser = authUserOpt.orNull
        def authUserId = principalWrapper.toUserId
        def userIdOpt = principalWrapper.toUserIdOpt
    }

    implicit class UserWrapper(val u: User) extends AnyVal {
        def isAdmin = u.isAdmin
    }

    implicit class PrincipalWrapper(val p: Principal) extends AnyVal {
        def toUserIdOpt = p match
        {
            case p:UserPrincipal => Some(p.id)
            case _ => None
        }

        def toUserId = p match {
            case p:UserPrincipal => p.id
            case _ => throw new RuntimeException("can't convert principal to userId")
        }

        def toUserOpt = p match {
            case p:UserPrincipal => Some(userService.getBy(p.id))
            case _ => None
        }

        def toUser = p match {
            case p: UserPrincipal => userService.getBy(p.id)
            case _ => throw new RuntimeException("can't convert principal to user")
        }
    }
}