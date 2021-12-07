package day07

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }

        return calculateFuelCost(positions)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("day07/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

fun calculateFuelCost(positions: List<Int>): Int {
    val positionDistribution = mutableMapOf<Int, Int>()

    for (position in positions) {
        val count = positionDistribution[position]
        if (count == null) {
            positionDistribution[position] = 1
        } else {
            positionDistribution[position] = count + 1
        }
    }

    val highestPosition = positionDistribution.keys.maxOf { it }
    val lowestPosition = positionDistribution.keys.minOf { it }

    var lowestFuelCost = Int.MAX_VALUE

    for (i in lowestPosition .. highestPosition) {
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