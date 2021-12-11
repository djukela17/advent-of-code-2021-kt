package day08

import java.lang.Exception

enum class Segment { A, B, C, D, E, F, G }

enum class Digital {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    override fun toString() = when (this) {
        ZERO -> "0"
        ONE -> "1"
        TWO -> "2"
        THREE -> "3"
        FOUR -> "4"
        FIVE -> "5"
        SIX -> "6"
        SEVEN -> "7"
        EIGHT -> "8"
        NINE -> "9"
    }

}

class Digit(pattern: String) {
    val activeSegments = pattern.map {
        when (it) {
            'a' -> Segment.A
            'b' -> Segment.B
            'c' -> Segment.C
            'd' -> Segment.D
            'e' -> Segment.E
            'f' -> Segment.F
            'g' -> Segment.G
            else -> throw Exception("Invalid symbol")
        }
    }.toSet()

    fun countActiveSegments() = activeSegments.size

    fun getPossibleDigitsBySegmentSize(): Array<Digital> {
        return when (countActiveSegments()) {
            2 -> arrayOf(Digital.ONE)
            3 -> arrayOf(Digital.SEVEN)
            4 -> arrayOf(Digital.FOUR)
            5 -> arrayOf(Digital.TWO, Digital.THREE, Digital.FIVE)
            6 -> arrayOf(Digital.ZERO, Digital.SIX, Digital.NINE)
            7 -> arrayOf(Digital.EIGHT)
            else -> throw Exception("Invalid")
        }
    }

    fun getDigitValue(unscrambledConnections: Map<Segment, Segment>): Int {
        val zero = setOf(Segment.A, Segment.B, Segment.C, Segment.E, Segment.F, Segment.G)
        val one = setOf(Segment.C, Segment.F)
        val two = setOf(Segment.A, Segment.C, Segment.D, Segment.E, Segment.G)
        val three = setOf(Segment.A, Segment.C, Segment.D, Segment.F, Segment.G)
        val four = setOf(Segment.B, Segment.C, Segment.D, Segment.F)
        val five = setOf(Segment.A, Segment.B, Segment.D, Segment.F, Segment.G)
        val six = setOf(Segment.A, Segment.B, Segment.D, Segment.E, Segment.F, Segment.G)
        val seven = setOf(Segment.A, Segment.C, Segment.F)
        val eight = setOf(Segment.A, Segment.B, Segment.C, Segment.D, Segment.E, Segment.F, Segment.G)
        val nine = setOf(Segment.A, Segment.B, Segment.C, Segment.D, Segment.F, Segment.G)

        val correctPattern = activeSegments.map {
            unscrambledConnections[it]
        }.toSet()

        return when (correctPattern) {
            zero -> 0
            one -> 1
            two -> 2
            three -> 3
            four -> 4
            five -> 5
            six -> 6
            seven -> 7
            eight -> 8
            nine -> 9
            else -> throw Exception("Unknown digit")
        }
    }
}
