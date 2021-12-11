package day08

class Display(
    val segmentMappings: MutableMap<Segment, MutableList<Segment>>,
)

fun createDisplay(digit: Digit): Pair<Digital, Display> {
    return when (digit.countActiveSegments()) {
        DIGIT_ONE_SIZE -> {
            Pair(Digital.ONE, createDisplayForDigitOne(digit))
        }
        DIGIT_FOUR_SIZE -> {
            Pair(Digital.FOUR, createDisplayForDigitFour(digit))
        }
        DIGIT_SEVEN_SIZE -> {
            Pair(Digital.SEVEN, createDisplayForDigitSeven(digit))
        }
        DIGIT_EIGHT_SIZE -> {
            Pair(Digital.EIGHT, createDisplayForDigitEight(digit))
        }
        else -> throw Exception("not a unique displayable digit")
    }
}

fun createDisplayForDigitOne(digit: Digit): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    segmentMappings[Segment.C] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.F] = digit.activeSegments.toMutableList()

    return Display(segmentMappings)
}


fun createDisplayForDigitTwo(digit: Digit, unscrambledConnections: Map<Segment, Segment>): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    val segmentA = unscrambledConnections[Segment.A]
    if (segmentA != null) {
        segmentMappings[Segment.A] = mutableListOf(segmentA)
    } else {
        segmentMappings[Segment.A] = digit.activeSegments.toMutableList()
    }

    val segmentC = unscrambledConnections[Segment.C]
    if (segmentC != null) {
        segmentMappings[Segment.C] = mutableListOf(segmentC)
    } else {
        segmentMappings[Segment.C] = digit.activeSegments.toMutableList()
    }

    val segmentD = unscrambledConnections[Segment.D]
    if (segmentD != null) {
        segmentMappings[Segment.D] = mutableListOf(segmentD)
    } else {
        segmentMappings[Segment.D] = digit.activeSegments.toMutableList()
    }

    segmentMappings[Segment.E] = digit.activeSegments.toMutableList()

    val segmentG = unscrambledConnections[Segment.G]
    if (segmentG != null) {
        segmentMappings[Segment.G] = mutableListOf(segmentG)
    } else {
        segmentMappings[Segment.G] = digit.activeSegments.toMutableList()
    }

    return Display(segmentMappings)
}

fun createDisplayForDigitThree(digit: Digit, unscrambledConnections: Map<Segment, Segment>): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    val segmentA = unscrambledConnections[Segment.A]
    if (segmentA != null) {
        segmentMappings[Segment.A] = mutableListOf(segmentA)
    } else {
        segmentMappings[Segment.A] = digit.activeSegments.toMutableList()
    }

    val segmentC = unscrambledConnections[Segment.C]
    if (segmentC != null) {
        segmentMappings[Segment.C] = mutableListOf(segmentC)
    } else {
        segmentMappings[Segment.C] = digit.activeSegments.toMutableList()
    }

    val segmentF = unscrambledConnections[Segment.F]
    if (segmentF != null) {
        segmentMappings[Segment.F] = mutableListOf(segmentF)
    } else {
        segmentMappings[Segment.F] = digit.activeSegments.toMutableList()
    }

    segmentMappings[Segment.D] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.G] = digit.activeSegments.toMutableList()

    return Display(segmentMappings)
}

fun createDisplayForDigitFour(digit: Digit): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    segmentMappings[Segment.B] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.C] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.D] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.F] = digit.activeSegments.toMutableList()

    return Display(segmentMappings)
}

fun createDisplayForDigitFive(digit: Digit, unscrambledConnections: Map<Segment, Segment>): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    val segmentA = unscrambledConnections[Segment.A]
    if (segmentA != null) {
        segmentMappings[Segment.A] = mutableListOf(segmentA)
    } else {
        segmentMappings[Segment.A] = digit.activeSegments.toMutableList()
    }

    segmentMappings[Segment.B] = digit.activeSegments.toMutableList()

    val segmentD = unscrambledConnections[Segment.D]
    if (segmentD != null) {
        segmentMappings[Segment.D] = mutableListOf(segmentD)
    } else {
        segmentMappings[Segment.D] = digit.activeSegments.toMutableList()
    }


    val segmentF = unscrambledConnections[Segment.F]
    if (segmentF != null) {
        segmentMappings[Segment.F] = mutableListOf(segmentF)
    } else {
        segmentMappings[Segment.F] = digit.activeSegments.toMutableList()
    }

    val segmentG = unscrambledConnections[Segment.G]
    if (segmentG != null) {
        segmentMappings[Segment.G] = mutableListOf(segmentG)
    } else {
        segmentMappings[Segment.G] = digit.activeSegments.toMutableList()
    }

    return Display(segmentMappings)
}


fun createDisplayForDigitSix(digit: Digit): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    segmentMappings[Segment.A] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.B] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.D] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.E] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.F] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.G] = digit.activeSegments.toMutableList()

    return Display(segmentMappings)
}

fun createDisplayForDigitSeven(digit: Digit): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    segmentMappings[Segment.A] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.C] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.F] = digit.activeSegments.toMutableList()

    return Display(segmentMappings)
}

fun createDisplayForDigitEight(digit: Digit): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    segmentMappings[Segment.A] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.B] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.C] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.D] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.E] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.F] = digit.activeSegments.toMutableList()
    segmentMappings[Segment.G] = digit.activeSegments.toMutableList()

    return Display(segmentMappings)
}

fun createDisplayForFiveSegmentDigits(digits: List<Digit>): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    return Display(segmentMappings)
}

fun createDisplayForSixSegmentDigits(digits: List<Digit>): Display {
    val segmentMappings = mutableMapOf<Segment, MutableList<Segment>>()

    return Display(segmentMappings)
}