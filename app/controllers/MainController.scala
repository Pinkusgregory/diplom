package controllers

import javax.inject.Inject
import play.api.mvc._
import utils.{Auth, PrincipalConverters}

class MainController @Inject() () extends Controller with Auth {

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

  val chapterForm: Form[ChapterWithoutId] = Form {
    mapping(
      "short_name" -> nonEmptyText,
      "full_name" -> nonEmptyText
    )(ChapterWithoutId.apply)(ChapterWithoutId.unapply)
  }

  def listChapter() = Action { implicit request =>
    Ok(chapterDTO.getListChapterJson(chapterService.list()))
  }

  def listArticle() = Action { implicit request =>
    val name = "Stefan"
    var str=
      """
        [
          {
            "shortName": """
    str += """"""" + name + """""""
    str += """},"""
    str += """]"""
    val r = Json.parse(str.dropRight(2) + "]").toString()
    println(r)
    println(Json.parse(r))

    Ok(articleDTO.getListArticleJson(articleService.list()))
  }

  def list() = Action { implicit request =>

    def recursiveWriter(upperChapterId: String): String ={
      var str = ""
      chapterService.list().foreach( chapter => {
        if (chapter.upperChapterId == upperChapterId) {
          str += """
                  [
                    {
                      "shortName": """
          str += """"""" + chapter.shortName + """""""
          str += "," + """ "fullName":  """
          str += """"""" + chapter.fullName + """""""
          articleService.list().foreach(article =>
            if (article.upperChapterId == chapter.id) {
              str += "," + """ "articles":  """
              str += """
                  [
                    {
                      "shortName": """
              str += """"""" + article.shortName + """""""
              str += "," + """ "fullName":  """
              str += """"""" + article.fullName + """""""
              str += "," + """ "text":  """
              str += """"""" + article.text + """""""
              str += "}" +
                  "]"
            }
          )
          str += "," + """ "children":  """
          str += recursiveWriter(chapter.id)
        }
      })
      str
    }

    println(Json.parse(recursiveWriter("0")))

  //  Ok(chapterDTO.getRecursiveListChapterJson(recursiveWriter("0")))
  }

  def loadFromJson = Action { implicit request =>

    val config = ConfigFactory.load("application.conf").getConfig("importJson")
    val fileName = config.getString("path")

    val source = Source.fromFile(fileName).getLines.mkString
    val json = Json.parse(source)

    val jsResult = json.validate[List[ChapterReader]](chapterDTO.ChaptersRead)

    articleService.deleteAllArticles()
    chapterService.deleteAllChapters()

    def recursiveReader(upperChapterId: String, chapterReader: ChapterReader): Unit ={
      val newChapterId = java.util.UUID.randomUUID().toString
      chapterService.createChapter(Chapter(newChapterId, chapterReader.shortName, chapterReader.fullName, upperChapterId))
      chapterReader.articles match {
        case Some(value) => value.foreach(article =>
          articleService.createArticle(Article(java.util.UUID.randomUUID().toString,
            article.shortName,
            article.fullName,
            article.text,
            newChapterId)))
        case None =>
      }
      chapterReader.children match {
        case Some(value) =>
          value.foreach( children =>
            recursiveReader(newChapterId, children))
        case None =>
      }
    }

    jsResult.get.foreach( chapter => recursiveReader("0", chapter))

    Redirect(routes.MainController.index())
  }*/

    def about = optionalUserAction { implicit rc =>
        Ok(views.html.about()(rc))
    }

    def contacts = optionalUserAction { implicit rc =>
        Ok(views.html.contacts()(rc))
    }

    def index = optionalUserAction { implicit rc =>
        val userOpt = PrincipalConverters.ContextWrapper(rc).authUserOpt
        if (userOpt.isEmpty) {
            Ok(views.html.index()(rc))
        } else {
            Ok(views.html.indexLoggedIn()(rc))
        }
    }
}
