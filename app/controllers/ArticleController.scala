/*
package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.MessagesApi
import services._
import models._


class ArticleController @Inject() (cc: ControllerComponents)(messages: MessagesApi) extends AbstractController(cc) {

/*  val articleService = new DbArticleService
  val chapterService = new DbChapterService
  val articleDTO = new ArticleDTO
  val chapterDTO = new ChapterDTO

  val articleForm: Form[ArticleWithoutId] = Form {
    mapping(
      "short_name" -> nonEmptyText,
      "full_name" -> nonEmptyText,
      "text" -> nonEmptyText
    )(ArticleWithoutId.apply)(ArticleWithoutId.unapply)
  }

  def get(id: String) = Action { implicit request =>

    articleService.findArticle(id) match {
      case Some(value) => Ok(views.html.articlePage(value, articleForm)(request, msg = messagesApi.preferred(request)))
      case None => NotFound
    }

  }

  def delete(id: String) = Action { implicit request =>

    articleService.deleteArticle(id)
    Redirect(routes.MainController.index())
  }

  def create(id: String) = Action { implicit request =>

    articleForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Not good"),
      formData => {
        articleService.createArticle(Article(
          java.util.UUID.randomUUID().toString,
          formData.shortName,
          formData.fullName,
          formData.text,
          id))
        Redirect(routes.MainController.index())
      }
    )
  }

  def update(id: String, upperChapterId: String) = Action { implicit request =>

    articleForm.bindFromRequest.fold(
      formWithErrors => BadRequest("Not good"),
      formData => {
        articleService.updateArticle(id,
          formData.shortName,
          formData.fullName,
          formData.text,
          upperChapterId)
        Redirect(routes.MainController.index())
      }
    )
  }*/
}*/
