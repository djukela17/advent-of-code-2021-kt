package day03


import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return DiagnosticReport(input).calculatePowerConsumption()
    }

    fun part2(input: List<String>): Int {
        return DiagnosticReport(input).calculateLifeSupportRating()
    }

    val input = readInput("day03/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

class DiagnosticReport(input: List<String>) {
    private val lines = input.map { BinaryNumber(it) }

    fun calculatePowerConsumption(): Int {
        return calculateGammaRate() * calculateEpsilonRate()
    }

    fun calculateLifeSupportRating(): Int {
        val oxygenRating = calculateOxygenGeneratorRating()
        val co2ScrubberRating = calculateCO2ScrubberRating()

        return oxygenRating * co2ScrubberRating
    }

    private fun calculateGammaRate(): Int {
        var gamma = ""

        val reportLineSize = lines[0].bits.size
        for (i in 0 until reportLineSize) {
            var zeroes = 0
            var ones = 0

            for (line in lines) {
                if (line.bits[i] == 0) zeroes++ else ones++
            }

            gamma += findMostCommonBitAtIndex(lines, i).toString()
        }

        return BinaryNumber(gamma).toDecimal()
    }

    private fun calculateEpsilonRate(): Int {
        var epsilon = ""

        val reportLineSize = lines[0].bits.size
        for (i in 0 until reportLineSize) {
            var zeroes = 0
            var ones = 0

            for (line in lines) {
                if (line.bits[i] == 0) zeroes++ else ones++
            }

            epsilon += findLeastCommonBitAtIndex(lines, i).toString()
        }

        return BinaryNumber(epsilon).toDecimal()
    }

    private fun calculateOxygenGeneratorRating(): Int {
        var remainingLines = lines.toList()

        for (i in 0 until lines[0].size()) {
            var mostCommonBit = findMostCommonBitAtIndex(remainingLines, i)
            if (mostCommonBit == -1) {
                mostCommonBit = 1
            }

            remainingLines = remainingLines.filter {
                it.bits[i] == mostCommonBit
            }

            if (remainingLines.size == 1) {
                return remainingLines[0].toDecimal()
            }
        }

        return 0
    }

    private fun calculateCO2ScrubberRating(): Int {
        var remainingLines = lines.toList()

        for (i in 0 until lines[0].size()) {
            var mostCommonBit = findLeastCommonBitAtIndex(remainingLines, i)
            if (mostCommonBit == -1) {
                mostCommonBit = 0
            }

            remainingLines = remainingLines.filter {
                it.bits[i] == mostCommonBit
            }

            if (remainingLines.size == 1) {
                return remainingLines[0].toDecimal()
            }
        }

        return 0
    }

    private fun findMostCommonBitAtIndex(lines: List<BinaryNumber>, i: Int): Int {
        var zeroes = 0
        var ones = 0

        for (line in lines) {
            if (line.bits[i] == 0) zeroes++ else ones++
        }

        return if (zeroes > ones) {
            0
        } else if (ones > zeroes) {
            1
        } else {
            -1
        }
    }

    private fun findLeastCommonBitAtIndex(lines: List<BinaryNumber>, i: Int): Int {
        var zeroes = 0
        var ones = 0

        for (line in lines) {
            if (line.bits[i] == 0) zeroes++ else ones++
        }

        return if (zeroes < ones) {
            0
        } else if (ones < zeroes) {
            1
        } else {
            -1
        }
    }
}

class BinaryNumber(input: String = "") {
    val bits = input.map { it.toString().toInt() }

    override fun toString() = bits.joinToString("")

    fun size() = bits.size

    fun toDecimal(): Int {
        var decimal = 0

        for ((weight, bit) in bits.reversed().withIndex()) {
            if (bit == 0) continue

            decimal += 2.pow(weight)
        }

        return decimal
    }
}

fun Int.pow(p: Int): Int {
    var result = 1
    repeat(p) {
        result *= this
    }

    return result
}