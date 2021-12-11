package day08

import measure
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return countSimpleDigitsInOutput(parseInput(input))
    }

    val input = readInput("day08/input")
    measure {
        println("part1: ${part1(input)}")
    }
    measure {
        println("part2: ${part2(input)}")
    }
}

const val DIGIT_ONE_SIZE = 2
const val DIGIT_FOUR_SIZE = 4
const val DIGIT_SEVEN_SIZE = 3
const val DIGIT_EIGHT_SIZE = 7

fun countSimpleDigitsInOutput(entries: List<Entry>): Int {
    val simpleDigitSizes = intArrayOf(
        DIGIT_ONE_SIZE,
        DIGIT_FOUR_SIZE,
        DIGIT_SEVEN_SIZE,
        DIGIT_EIGHT_SIZE
    )

    var count = 0

    for (entry in entries) {
        for (outputDigit in entry.output) {
            if (simpleDigitSizes.contains(outputDigit.countActiveSegments())) {
                count++
            }
        }
    }

    return count
}

fun parseInput(input: List<String>): List<Entry> {
    return input.map {
        parseLine(it)
    }
}

fun parseLine(line: String): Entry {
    val (signalPatternValues, digitValues) = line.split("|")

    val signals = mutableListOf<Digit>()
    val digits = mutableListOf<Digit>()

    for (pattern in signalPatternValues.trim().split(" ")) {
        signals += Digit(pattern)
    }

    for (pattern in digitValues.trim().split(" ")) {
        digits += Digit(pattern)
    }

    return Entry(signals, digits)
}

class Entry(
    val patterns: List<Digit>,
    val output: List<Digit>,
)
