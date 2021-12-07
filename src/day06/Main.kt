package day06

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val lanternFish = input[0]
            .split(",")
            .map { LanternFish(it.toByte()) }

        return simulateLanternFishReproduction(lanternFish)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("day06/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

class LanternFish(
    var internalTimer: Byte = 8,
) {
    override fun toString() = "$internalTimer"

    fun reduceInternalTimer(): Boolean {
        if (internalTimer == 0.toByte()) {
            internalTimer = 6
            return true
        }

        internalTimer--

        return false
    }
}

fun simulateLanternFishReproduction(initialFish: List<LanternFish>, days: Int = 80): Int {
    val lanternFish = initialFish.toMutableList()

    for (day in 1..days) {
        val newFish = mutableListOf<LanternFish>()
        for (fish in lanternFish) {
            if (fish.reduceInternalTimer()) newFish += LanternFish()
        }

        lanternFish.addAll(newFish)
    }

    return lanternFish.size
}
