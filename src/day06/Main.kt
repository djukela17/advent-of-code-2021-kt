package day06

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val fish = input[0].split(",").map { it.toInt() }

        return simulateReproduction(fish)
    }

    fun part2(input: List<String>): Long {
        val fish = input[0].split(",").map { it.toInt() }

        return simulateReproduction(fish, 256)
    }

    val input = readInput("day06/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

const val MATURE_FISH = 6
const val YOUNG_FISH = 8

fun simulateReproduction(initialFish: List<Int>, days: Int = 80): Long {
    val fishMap = mutableMapOf(
        Pair(0, 0L),
        Pair(1, 0L),
        Pair(2, 0L),
        Pair(3, 0L),
        Pair(4, 0L),
        Pair(5, 0L),
        Pair(6, 0L),
        Pair(7, 0L),
        Pair(8, 0L),
    )

    for (fish in initialFish) {
        val fishCount = fishMap.getOrDefault(fish, 0)
        fishMap[fish] = fishCount + 1
    }

    for(day in 1 .. days) {
        var newFish = 0L
        var matureFish = 0L
        for (currentTimer in 0 .. 8) {
            if (currentTimer == 0) {
                newFish = fishMap[0]!!
                matureFish = fishMap[0]!!

                fishMap[0] = 0
                continue
            }

            val previousTimer = currentTimer - 1
            fishMap[previousTimer] = fishMap[previousTimer]!! + fishMap[currentTimer]!!
            fishMap[currentTimer] = 0
        }

        fishMap[YOUNG_FISH] = newFish
        fishMap[MATURE_FISH] = fishMap[MATURE_FISH]!! + matureFish
    }

    return fishMap.toList().sumOf { (_, u) -> u }
}
