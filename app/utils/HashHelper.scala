package utils

trait HashHelper {

  def md5(data: String): String = hash(data, "MD5")

  def sha512(data: String): String = hash(data, "SHA-512")

  def sha256(data: String): String = hashFixedLength(data, "SHA-256", 64)

  def sha1(data: String): String = hash(data, "SHA-1")

  def sha512_with_head_zero(data: String): String = hashFixedLength(data, "SHA-512", 128)

  private def getMD(alg: String) = java.security.MessageDigest.getInstance(alg)

  private def hashBytes(data: String, alg: String) = getMD(alg).digest(data.getBytes("UTF-8"))

  private def hash(data: String, alg: String) = byteArrayToHex(hashBytes(data, alg))
  private def hashFixedLength(data: String, alg: String, length: Int) = byteArrayToHexFixedLength(hashBytes(data, alg), length)

  def byteArrayToHex(byteData: Array[Byte]): String = new java.math.BigInteger(1, byteData).toString(16)
  def byteArrayToHexFixedLength(byteData: Array[Byte], l: Int) = String.format(s"%0${l}x", new java.math.BigInteger(1, byteData))
}

object HashHelper extends HashHelper
