package template

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("dayXX/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}
