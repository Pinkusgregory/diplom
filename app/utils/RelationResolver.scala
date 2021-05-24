package utils

import play.api.cache.Cache
import play.api.Play._
import reflect.ClassTag

class RelationResolver[Id: ClassTag] {

    private[utils] val sessionIdSuffix = ":sessionId"
    private[utils] val userIdSuffix = ":userId"

    def exists(sessionId: String) = sessionId2userId(sessionId).isDefined

    def sessionId2userId(sessionId: String): Option[Id] = Cache.getAs[Id](sessionId + sessionIdSuffix)

    def userId2sessionId(userId: Id): Option[String] = Cache.getAs[String](userId.toString + userIdSuffix)

    def sessionsBy(userId: Id) = {
        Cache.getAs[Seq[String]](userId.toString + userIdSuffix).getOrElse(Seq())
    }

    def removeBySessionId(sessionId: String) {
        sessionId2userId(sessionId) foreach unsetUserId
        unsetSessionId(sessionId)
    }
    def removeByUserId(userId: Id) {
        sessionsBy(userId).foreach(unsetSessionId)
        unsetUserId(userId)
    }
    private[utils] def unsetSessionId(sessionId: String) {
        Cache.remove(sessionId + sessionIdSuffix)
    }
    private[utils] def unsetUserId(userId: Id) {
        Cache.remove(userId.toString + userIdSuffix)
    }

    def addSessionToUser(sessionId: String, userId: Id, timeoutInSeconds: Int) = {
        Cache.set(sessionId + sessionIdSuffix, userId, timeoutInSeconds)
        Cache.set(
            userId.toString + userIdSuffix,
            (sessionsBy(userId) :+ sessionId).distinct,
            timeoutInSeconds
        )
    }

    def prolongTimeout(sessionId: String, timeoutInSeconds: Int) {
        sessionId2userId(sessionId).foreach(addSessionToUser(sessionId, _, timeoutInSeconds))
    }
}
