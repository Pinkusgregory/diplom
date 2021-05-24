package utils

import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.duration.FiniteDuration
import play.api.libs.concurrent.Akka
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._

trait AkkaHelper {
  /**
   * запускает в шедулере задачу f через concurrent.future,
   * concurrent.future нужен, чтобы при exception'е в задаче, задача продолжала запускаться шедулером
   */
  def akkaSchedule(initialDelay: FiniteDuration, interval : FiniteDuration)(f : => Unit) = {
    Akka.system.scheduler.schedule(initialDelay, interval) { concurrent.future(f) }
  }
}

object AkkaHelper extends AkkaHelper
{
  implicit val defaultRiseTimeout = Timeout(1 minute)
}
