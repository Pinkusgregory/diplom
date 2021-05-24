package utils

import java.security.SecureRandom

trait PasswordHelper extends HashHelper {

    private val rnd = new java.security.SecureRandom()

    private val lowerCaseChars = "abcdefghijklmnopqrstuvwxyz"
    private val upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val numericChars = "0123456789"
    private val specialChars = "!@#$%^&*"

    /**
     * Secure password generation function
     * @param length password length, default 16
     * @param level password level, default 3 (level 1 - lower case, level 2 - lower and upper cases, level 3 - lower, upper and numeric, level 4 - lower, upper, numeric and special chars)
     * @return password string
     */
    def generatePassword(length: Int = 16)(implicit level: Int = 3) = (for (i <- 0 until length) yield randomSymbol).mkString

    private def randomSymbol(implicit level: Int) = rnd.nextInt(level) match {
        case 0 => lowerCaseChars.charAt(rnd.nextInt(lowerCaseChars.length))
        case 1 => upperCaseChars.charAt(rnd.nextInt(upperCaseChars.length))
        case 2 => numericChars.charAt(rnd.nextInt(numericChars.length))
        case 3 => specialChars.charAt(rnd.nextInt(specialChars.length))

        case _ => (lowerCaseChars + upperCaseChars + numericChars + specialChars).charAt(
          rnd.nextInt(lowerCaseChars.length + upperCaseChars.length + numericChars.length + specialChars.length)
        )
    }

    def generateSalt: String = {
        val salt = new Array[Byte](16)
        new SecureRandom().nextBytes(salt)
        byteArrayToHex(salt)
    }

    def computeHash(data: String, salt: String) = sha512(sha512(data) + salt)

}

object PasswordHelper extends PasswordHelper
