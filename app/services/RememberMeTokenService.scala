package services

import utils.{HashHelper, SquerylSession}
import models._
import org.squeryl.PrimitiveTypeMode._

case class RememberMeToken(userId: String, series: Long, token: String)

object RememberMeToken extends HashHelper {

    def apply(rememberMe: RememberMe): RememberMeToken = {
        RememberMeToken(
            userId = rememberMe.userId.get,
            series = rememberMe.series.get,
            token = sha512(rememberMe.token.get.toString)
        )
    }

}

class RememberMeTokenService extends SquerylSession {

    import models.AppSchema.rememberMeToken

    def create(token: RememberMeToken): RememberMeToken = tx {
        rememberMeToken.insert(token)
    }

    def remove(token: RememberMeToken) = tx {
        rememberMeToken.deleteWhere(rmt => (rmt.userId === token.userId) and (rmt.series === token.series))
    }

    def removeTokensForUser(except: RememberMeToken) = tx {
        rememberMeToken.deleteWhere(rmt => (rmt.userId === except.userId) and not(rmt.series === except.series))
    }

    def removeAllTokensForUser(userId: String) = tx {
        rememberMeToken.deleteWhere(rmt => (rmt.userId === userId))
    }

    def findByUserIdAndSeries(userId: String, series: Long): Option[RememberMeToken] = tx {
        from(rememberMeToken)(rmt => where(rmt.userId === userId and rmt.series === series) select(rmt)).headOption
    }
}