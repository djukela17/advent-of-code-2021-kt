package day07

import readInput
import measure
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }

        return calculateFuelCostFlat(createMap(positions))
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }

        return calculateIncrementalFuelCost(createMap(positions))
    }

    val input = readInput("day07/input")
    measure {
        println("part1: ${part1(input)}")
    }
    measure {
        println("part2: ${part2(input)}")
    }
}

fun createMap(positions: List<Int>): Map<Int, Int> {
    val positionDistribution = mutableMapOf<Int, Int>()

    for (position in positions) {
        val count = positionDistribution[position]
        if (count == null) {
            positionDistribution[position] = 1
        } else {
            positionDistribution[position] = count + 1
        }
    }

    return positionDistribution
}

fun calculateFuelCostFlat(positionDistribution: Map<Int, Int>): Int {
    val highestPosition = positionDistribution.keys.maxOf { it }
    val lowestPosition = positionDistribution.keys.minOf { it }

    var lowestFuelCost = Int.MAX_VALUE

    for (i in lowestPosition..highestPosition) {
        val fuelCost = calculateFuelCostForPosition(i, positionDistribution)
        if (fuelCost < lowestFuelCost) {
            lowestFuelCost = fuelCost
        }
    }

    return lowestFuelCost
}

fun calculateFuelCostForPosition(targetPosition: Int, positionDistribution: Map<Int, Int>): Int {
    var cost = 0

    for (entry in positionDistribution.entries) {
        cost += abs(entry.key - targetPosition) * entry.value
    }

    return cost
}

fun calculateIncrementalFuelCost(positionDistribution: Map<Int, Int>): Int {
    val highestPosition = positionDistribution.keys.maxOf { it }
    val lowestPosition = positionDistribution.keys.minOf { it }

    var lowestFuelCost = Int.MAX_VALUE

    for (i in lowestPosition..highestPosition) {
        val fuelCost = calculateIncrementalFuelCostForPosition(i, positionDistribution)
        if (fuelCost < lowestFuelCost) {
            lowestFuelCost = fuelCost
        }
    }

    return lowestFuelCost
}

fun calculateIncrementalFuelCostForPosition(targetPosition: Int, positionDistribution: Map<Int, Int>): Int {
    var cost = 0

    for (entry in positionDistribution.entries) {
        cost += calculate(entry.key, targetPosition) * entry.value
    }

    return cost
}

fun calculate(start: Int, end: Int): Int {
    val delta = abs(start - end)

    var cost = 0

    for (i in 1..delta) {
        cost += i
    }

    return cost
}

class IncrementalCost(
    val positionDistribution: Map<Int, Int>,
) {
    fun calculateLowesFuelCost(): Int {
        var lowestFuelCost = Int.MAX_VALUE

        return lowestFuelCost
    }

    fun calculate(start: Int, end: Int): Int {
        val delta = abs(start - end)

        var cost = 0

        for (i in 1..delta) {
            cost += i
        }

        return cost
    }
}