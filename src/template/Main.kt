import readInput
import measure

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("dayXX/input")
    measure {
        println("part1: ${part1(input)}")
    }
    measure {
        println("part2: ${part2(input)}")
    }
}
