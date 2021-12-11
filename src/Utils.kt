import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Measures the time taken by function
 */
fun measure(block: () -> Unit) {
    val start = Date()
    block()
    println("time: ${Date().time - start.time}ms")
}

fun List<Int>.toDecimal(): Int {
    var result = 0

    var p = 1

    for (n in this.reversed()) {
        result += n * p
        p *= 10
    }

    return result
}
