package day08

fun part2(input: List<String>): Int {
    val entries = parseInput(input)

    val output = mutableListOf<Int>()

    for (entry in entries) {
        output += Decoder().decodeEntry(entry)
    }

    return output.sum()
}
