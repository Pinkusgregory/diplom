package utils

import scala.util.Random

trait UuidHelper {
  def random(size: Int) = {
    assert(size <= 32)
    randomUuid.take(size)
  }
  def availableEngLetters = "abcdefghijklmnopqrstuvwxyz"
  def letterEng = availableEngLetters.charAt(Random.nextInt(availableEngLetters.length))
  def digit = Random.nextInt(10).toString
  def randomUuid = java.util.UUID.randomUUID.toString.replaceAll("-", "")
}

object UuidHelper extends UuidHelper
