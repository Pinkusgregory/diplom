import java.util.TimeZone

import org.squeryl.adapters.PostgreSqlAdapter
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.{Session, SessionFactory}
import play.api._
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import utils._

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * Фильтр используется для отключения кэша для страниц с типом text/html
 */

trait NoCacheFilter extends Filter {
    val noCache = "no-cache, max-age=0, must-revalidate, no-store"
    def mimeTypes: String
    def processContentType(result: Result) = {
        result.header.headers.get(HeaderNames.CONTENT_TYPE).collect{
            case ct if ct.split(";").head == mimeTypes =>
                HeaderNames.CACHE_CONTROL -> "no-cache, max-age=0, must-revalidate, no-store"
        }.map(result.withHeaders(_)).getOrElse(result)
    }

    def apply(next: (RequestHeader) => Future[Result])(rh: RequestHeader) = {
        next(rh).map(processContentType)
    }
}

object HtmlNoCacheFilter extends NoCacheFilter {
    override def mimeTypes: String = MimeTypes.HTML
}

object JSONNoCacheFilter extends NoCacheFilter {
    override def mimeTypes: String = MimeTypes.JSON
}

object Global extends WithFilters(HtmlNoCacheFilter, JSONNoCacheFilter, JSONNoCacheFilter)
              with GlobalSettings
              with AkkaHelper {

    override def beforeStart(app: Application) {
        System.setProperty("user.timezone", "Etc/GMT")
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/GMT"))
    }

    override def onStart(app: Application) {
      initializeSqueryl(app)
    }

    private def initializeSqueryl(app: Application) {
        SessionFactory.concreteFactory = Some(() => {
            val s = getSession(new PostgreSqlAdapter, app)
            s
        })
    }

    def getSession(adapter:DatabaseAdapter, app: Application) = {
        Session.create(MyBoneCPPlugin.getConnection(name = "default", autocommit = false)(app), adapter)
    }

/*    override def onBadRequest(request: RequestHeader, error: String) = {
      GlobalErrorHandler.handleBadRequest(error)(getRequestContext(request)) match {
        case Some(r) => Future.successful(r)
        case None => super.onBadRequest(request, error)
      }
    }

    override def onError(request: RequestHeader, ex: Throwable) = {
      Future.successful(GlobalErrorHandler.handleError(ex)(getRequestContext(request)))
    }

    override def onHandlerNotFound(request: RequestHeader) = {
      Future.successful(GlobalErrorHandler.handleNotFound(getRequestContext(request)))
    }

    private def getRequestContext(request: RequestHeader): RequestContext =
      RequestContext(request = Request.apply(request, AnyContentAsEmpty),
        principal = SessionHelper.principalFromSession(request.session),
        lang = CookieLanguageHelper.languageFromCookie(request.cookies, request.headers)
      )*/
}
