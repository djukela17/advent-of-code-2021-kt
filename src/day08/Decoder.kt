package day08

import toDecimal

class Decoder {

    private val unscrambledConnections: MutableMap<Segment, Segment> = mutableMapOf()

    fun decode(entries: List<Entry>) {
        val outputs = mutableListOf<Int>()

        for (entry in entries) {
            outputs += decodeEntry(entry)
            reset()
        }

        println(outputs)
    }

    fun decodeEntry(entry: Entry): Int {
        unscrambleConnections(entry)

        val rewired = unscrambledConnections.map {
            Pair(it.value, it.key)
        }.toMap()

        val output = entry.output.map {
            it.getDigitValue(rewired)
        }

        return output.toDecimal()
    }

    private fun reset() {
        unscrambledConnections.clear()
    }

    private fun unscrambleConnections(entry: Entry) {
        val knownDigits = extractKnownDigits(entry.patterns)

        val one = createDisplayForDigitOne(knownDigits.filter { it.countActiveSegments() == 2 }[0])
        val four = createDisplayForDigitFour(knownDigits.filter { it.countActiveSegments() == 4 }[0])
        val seven = createDisplayForDigitSeven(knownDigits.filter { it.countActiveSegments() == 3 }[0])
        val eight = createDisplayForDigitEight(knownDigits.filter { it.countActiveSegments() == 7 }[0])
        val fiveSegmentDigits = entry.patterns.filter { it.countActiveSegments() == 5 }.toMutableList()

        decodeSegmentA(one, seven)
        unscrambledConnections[Segment.A] = seven.segmentMappings[Segment.A]!![0]

        decodeKnownSegmentsForDigitFour(one, four)
        decodeKnownSegmentsForDigitEight(eight, seven, four)

        val six = extractDigitSix(one, entry.patterns)
        decodeSegmentF(one, six)
        unscrambledConnections[Segment.C] = one.segmentMappings[Segment.C]!![0]
        unscrambledConnections[Segment.F] = one.segmentMappings[Segment.F]!![0]

        val three = extractDigitThree(fiveSegmentDigits)

        updateKnownSegmentMappings(four)

        decodeSegmentD(three, four)
        decodeSegmentG(three)

        decodeSegmentsBE(fiveSegmentDigits)
    }

    private fun extractKnownDigits(digits: List<Digit>): List<Digit> {
        return digits.filter {
            it.getPossibleDigitsBySegmentSize().size == 1
        }
    }

    // requires already knowing mappings for Segment.C and Segment.F
    private fun extractDigitThree(digitsTwoThreeFive: MutableList<Digit>): Display {
        val three = digitsTwoThreeFive.filter {
            it.activeSegments.contains(unscrambledConnections[Segment.C]!!) &&
                    it.activeSegments.contains(unscrambledConnections[Segment.F]!!)
        }[0]

        digitsTwoThreeFive.remove(three)

        return createDisplayForDigitThree(three, unscrambledConnections)
    }

    private fun decodeSegmentsBE(digitsTwoFive: MutableList<Digit>) {
        val digitTwoInput = digitsTwoFive.filter {
            it.activeSegments.contains(unscrambledConnections[Segment.C]!!)
        }[0]

        val digitFiveInput = digitsTwoFive.filter {
            it.activeSegments.contains(unscrambledConnections[Segment.F]!!)
        }[0]

        val two = createDisplayForDigitTwo(digitTwoInput, unscrambledConnections)
        val five = createDisplayForDigitFive(digitFiveInput, unscrambledConnections)


        two.segmentMappings[Segment.E]!!.remove(two.segmentMappings[Segment.A]!![0])
        two.segmentMappings[Segment.E]!!.remove(two.segmentMappings[Segment.C]!![0])
        two.segmentMappings[Segment.E]!!.remove(two.segmentMappings[Segment.D]!![0])
        two.segmentMappings[Segment.E]!!.remove(two.segmentMappings[Segment.G]!![0])

        five.segmentMappings[Segment.B]!!.remove(five.segmentMappings[Segment.A]!![0])
        five.segmentMappings[Segment.B]!!.remove(five.segmentMappings[Segment.D]!![0])
        five.segmentMappings[Segment.B]!!.remove(five.segmentMappings[Segment.F]!![0])
        five.segmentMappings[Segment.B]!!.remove(five.segmentMappings[Segment.G]!![0])

        unscrambledConnections[Segment.E] = two.segmentMappings[Segment.E]!![0]
        unscrambledConnections[Segment.B] = five.segmentMappings[Segment.B]!![0]
    }

    private fun extractDigitSix(one: Display, digits: List<Digit>): Display {
        val digitsZeroSixNine = digits.filter {
            it.countActiveSegments() == 6
        }

        val six = digitsZeroSixNine.filter {
            val segment1 = one.segmentMappings[Segment.C]!![0]
            val segment2 = one.segmentMappings[Segment.C]!![1]

            !(it.activeSegments.contains(segment1) && it.activeSegments.contains(segment2))
        }[0]

        return createDisplayForDigitSix(six)
    }

    private fun decodeSegmentA(one: Display, seven: Display) {
        val segmentA = seven.segmentMappings[Segment.A]!!

        for (segment in one.segmentMappings[Segment.C]!!) {
            segmentA.remove(segment)
        }

        seven.segmentMappings[Segment.C]!!.remove(segmentA[0])
        seven.segmentMappings[Segment.F]!!.remove(segmentA[0])
    }

    private fun decodeSegmentD(three: Display, four: Display) {
        unscrambledConnections[Segment.D] = four.segmentMappings[Segment.D]!!.filter {
            three.segmentMappings[Segment.D]!!.contains(it)
        }[0]
    }

    private fun decodeSegmentF(one: Display, six: Display) {
        val oneSegmentFs = one.segmentMappings[Segment.F]!!
        val sixSegmentFs = six.segmentMappings[Segment.F]!!

        one.segmentMappings[Segment.F] = sixSegmentFs.filter {
            oneSegmentFs.contains(it)
        }.toMutableList()

        one.segmentMappings[Segment.C] = one.segmentMappings[Segment.C]!!.filter {
            it != one.segmentMappings[Segment.F]!![0]
        }.toMutableList()
    }

    private fun decodeSegmentG(three: Display) {
        updateKnownSegmentMappings(three)

        three.segmentMappings[Segment.G]!!.remove(three.segmentMappings[Segment.A]!![0])
        three.segmentMappings[Segment.G]!!.remove(three.segmentMappings[Segment.C]!![0])
        three.segmentMappings[Segment.G]!!.remove(three.segmentMappings[Segment.D]!![0])
        three.segmentMappings[Segment.G]!!.remove(three.segmentMappings[Segment.F]!![0])

        unscrambledConnections[Segment.G] = three.segmentMappings[Segment.G]!![0]
    }

    private fun decodeKnownSegmentsForDigitFour(one: Display, four: Display) {
        for (segment in one.segmentMappings[Segment.C]!!) {
            four.segmentMappings[Segment.B]!!.remove(segment)
            four.segmentMappings[Segment.D]!!.remove(segment)
        }

        for (segment in four.segmentMappings[Segment.B]!!) {
            four.segmentMappings[Segment.C]!!.remove(segment)
            four.segmentMappings[Segment.F]!!.remove(segment)
        }
    }

    private fun decodeKnownSegmentsForDigitEight(eight: Display, seven: Display, four: Display) {
        val segmentA = seven.segmentMappings[Segment.A]!![0]

        // use known mappings from 7
        eight.segmentMappings[Segment.A] = seven.segmentMappings[Segment.A]!!
        eight.segmentMappings[Segment.C] = seven.segmentMappings[Segment.C]!!
        eight.segmentMappings[Segment.F] = seven.segmentMappings[Segment.F]!!

        // use known mappings from 4
        eight.segmentMappings[Segment.B] = four.segmentMappings[Segment.B]!!
        eight.segmentMappings[Segment.D] = four.segmentMappings[Segment.D]!!

        val segmentEs = eight.segmentMappings[Segment.E]!!
        val segmentGs = eight.segmentMappings[Segment.G]!!

        segmentGs.remove(segmentA)
        segmentEs.remove(segmentA)

        for (segment in eight.segmentMappings[Segment.C]!!) {
            segmentGs.remove(segment)
            segmentEs.remove(segment)
        }

        for (segment in eight.segmentMappings[Segment.B]!!) {
            segmentGs.remove(segment)
            segmentEs.remove(segment)
        }
    }

    private fun updateKnownSegmentMappings(display: Display) {
        for (connection in unscrambledConnections) {
            if (display.segmentMappings.containsKey(connection.key)) {
                display.segmentMappings[connection.key] = mutableListOf(connection.value)
            }
        }
    }
}
