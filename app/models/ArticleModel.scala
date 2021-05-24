/*
package models

import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsArray, JsPath, JsValue, Json, OWrites, Reads, Writes}

case class Article(@Column("id_article") id: String,
                   @Column("short_name") shortName: String,
                   @Column("full_name") fullName: String,
                   @Column("text")text: String,
                   @Column("id_upper_chapter") upperChapterId: String) extends KeyedEntity[String]

object Article {
  implicit val format = Json.format[Article]
  implicit val articlesRead = Reads.list[Article]
}

case class ArticleReader(shortName: String,
                         fullName: String,
                         text: String)

object ArticleReader {
  implicit val format = Json.format[ArticleReader]
  implicit val articlesRead = Reads.list[ArticleReader]
}

case class ArticleWithoutId(shortName: String,
                            fullName: String,
                            text: String)

class ArticleDTO() {

  implicit val ListArticleWriter: OWrites[List[(String, String, String, String, String)]] = (
    (JsPath \ "article").write[JsArray]
      .contramap[List[(String, String, String, String, String)]](list => JsArray(list.map(g => Json.obj("id" -> g._1,
        "short_name" ->g._2,
        "full_name" ->g._3,
        "text" ->g._4,
        "chapter_upper_id" ->g._5))))
    )

  implicit val articlesRead: Reads[List[ArticleReader]] = Reads.list[ArticleReader]

  def getListArticleJson(list: List[Article]): JsValue = {
    Json.toJson(list)
  }

}*/
