package controllers

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.mvc._
import services.UserService
import utils.{Auth, PrincipalConverters}

import scala.concurrent.Future

class AuthController @Inject()() extends Controller with Auth {

    lazy val userService = new UserService

    def signIn = optionalUserAction { implicit rc =>
        val userOpt = PrincipalConverters.ContextWrapper(rc).authUserOpt
        if (userOpt.isEmpty) {
            Ok(views.html.signIn()(rc))
        } else {
            Redirect(routes.MainController.index())
        }
    }

    def signUp = optionalUserAction { implicit rc =>
        val userOpt = PrincipalConverters.ContextWrapper(rc).authUserOpt
        if (userOpt.isEmpty) {
            Ok(views.html.signUp(userRegistrationForm)(rc))
        } else {
            Redirect(routes.MainController.index())
        }
    }

    def signUpSubmit = optionalUserActionAsync { implicit rc =>
        userRegistrationForm.bindFromRequest.fold(
            errors => Future.successful(BadRequest(views.html.signUp(errors))),
            data => {
                userService.save(data)
                Future.successful(Redirect(routes.MainController.index()))
            }
        )
    }

    def userRegistrationForm: Form[UserRegistrationData] = Form {
        mapping(
            "email" -> nonEmptyText,
            "firstName" -> nonEmptyText,
            "lastName" -> nonEmptyText,
            "password" -> nonEmptyText,
            "company" -> nonEmptyText
        )(UserRegistrationData.apply)(UserRegistrationData.unapply)
    }
}

case class UserRegistrationData(
                                 email: String,
                                 firstName: String,
                                 lastName: String,
                                 password: String,
                                 company: String
                               )
