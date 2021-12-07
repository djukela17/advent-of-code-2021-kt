package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val measurements = input.map {
            it.toInt()
        }

        return countDepthIncreases(measurements)
    }

    fun part2(input: List<String>): Int {
        val measurements = input.map {
            it.toInt()
        }

        return countDepthIncreases(sumDepthInWindows(measurements))
    }

    val input = readInput("day01/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

fun countDepthIncreases(measurements: List<Int>): Int {
    var depthIncreases = 0

    var lastDepth: Int? = null
    for (depth in measurements) {
        if (lastDepth == null) lastDepth = depth

        if (depth > lastDepth) depthIncreases++

        lastDepth = depth
    }

    return depthIncreases
}

fun sumDepthInWindows(measurements: List<Int>, windowSize: Int = 3): List<Int> {
    val windowedMeasurements = mutableListOf<Int>()
    val lastIndex = measurements.lastIndex

    for (i in measurements.indices) {
        if (i + 2 >= measurements.size) break

        windowedMeasurements += measurements[i] + measurements[i + 1] + measurements[i + 2]
    }

    return windowedMeasurements
}
