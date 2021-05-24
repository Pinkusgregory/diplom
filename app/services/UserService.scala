package services

import controllers.UserRegistrationData
import models._
import org.squeryl.PrimitiveTypeMode._
import utils.{HashHelper, SquerylSession, UuidHelper}



class UserService extends SquerylSession {

    import models.AppSchema.user

    def findBy(userId: String): Option[User] = tx {
        user.lookup(userId)
    }

    def getBy(userId: String): User = tx {
        findBy(userId).getOrElse(throw new Exception("Not found id" + userId))
    }

    def save(data: UserRegistrationData) = tx {
        user.insert(
            User(
                id = UuidHelper.randomUuid,
                firstName = data.firstName,
                email = data.email,
                lastName = data.lastName,
                password = data.password
            )
        )
    }
}