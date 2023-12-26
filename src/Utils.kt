import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */

fun readInput(name: String): List<String> {
    val resourceAsStream = object {}.javaClass.getResourceAsStream("$name.txt")
    if (resourceAsStream != null) {
        return resourceAsStream.bufferedReader().readLines()
    } else {
        throw Exception("Resource not found: $name.txt")
    }
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
