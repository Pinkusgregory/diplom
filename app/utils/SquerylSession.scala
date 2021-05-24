package utils

import java.sql.SQLException
import org.squeryl.dsl.QueryDsl
import org.squeryl.internals.Utils
import org.squeryl.{Session, SessionFactory}
import play.api.Play.current
import scala.runtime.NonLocalReturnControl
import scala.util.Try

trait SquerylSession {
    import ExtendedPrimitiveTypeMode._

    def tx[P](fun: => P) = customTransaction(fun)

    def onTxSuccess[P](fun: => P)(successFun: P => Unit): P = customTransaction(fun, Some(successFun))

    def updateOnSuccessAction(successAction: => Unit) = ExtendedPrimitiveTypeMode.updateOnSuccessAction(_ => successAction)
}

object ExtendedPrimitiveTypeMode extends org.squeryl.PrimitiveTypeMode with ExtendedQueryDsl

trait ExtendedQueryDsl extends QueryDsl {
    def cache = CachePlugin().cacheManager.getCache("squeryl", classOf[String], classOf[Seq[TxSuccessAction[_]]])

    def customTransaction[A](action: => A, successAction: Option[A => _] = None): A = {
        if(!Session.hasCurrentSession) {
            _executeTransaction(action, successAction)
        } else {
            val res = action
            val s = Session.currentSession
            successAction.foreach(sa => updateActionList(s.toString, TxSuccessAction(res, sa)))
            res
        }
    }

    def updateOnSuccessAction(successAction: Any => Unit) = {
        val s = Session.currentSession
        updateActionList(s.toString, TxSuccessAction(Unit, successAction))
    }

    def updateActionList(key: String, action: TxSuccessAction[_]) {
        val actionList = Try(Option(cache.get(key))).toOption.flatten.getOrElse(Nil)
        cache.put(key, actionList :+ action)
    }

    def _executeTransaction[A](action: => A, successAction: Option[A => _]): A = {

        val s = SessionFactory.newSession

        val c = s.connection

        val originalAutoCommit = c.getAutoCommit
        if(originalAutoCommit)
            c.setAutoCommit(false)

        var txOk = false
        try {
            val res = using(s)(action)
            txOk = true
            successAction.foreach(sa => updateActionList(s.toString, TxSuccessAction(res, sa)))
            res
        }
        catch {
            case e:NonLocalReturnControl[_] =>
            {
                txOk = true
                throw e
            }
        }
        finally {
            try {
                if(txOk) {
                    c.commit()
                    val txSuccessActions = Try(Option(cache.get(s.toString))).toOption.flatten.getOrElse(Nil)
                    txSuccessActions.foreach(_.execute)
                    cache.remove(s.toString)
                } else
                    c.rollback()
                if(originalAutoCommit != c.getAutoCommit)
                    c.setAutoCommit(originalAutoCommit)
            }
            catch {
                case e:SQLException =>
                    Utils.close(c)
                    if(txOk) throw e // if an exception occured b4 the commit/rollback we don't want to obscure the original exception
            }
            try{c.close()}
            catch {
                case e:SQLException =>
                    if(txOk) throw e // if an exception occured b4 the close we don't want to obscure the original exception
            }
        }
    }
}
