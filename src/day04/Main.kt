package day04

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val bingo = parse(input)
        bingo.runPlaySimulation()

        return bingo.getFirstWonBoard().score
    }

    fun part2(input: List<String>): Int {
        val bingo = parse(input)
        bingo.runPlaySimulation()

        return bingo.getLastWonBoard().score
    }

    val input = readInput("day04/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

fun parse(input: List<String>): BingoGame {
    // first line contains numbers
    val numbers = mutableListOf<Int>()

    input[0].split(",").forEach {
        numbers += it.toInt()
    }

    // remaining lines contain 5x5 boards
    val boards = mutableListOf<Board>()
    val lines = mutableListOf<String>()

    var serialNumber = 1

    for (line in input.drop(2)) {
        if (line == "") {
            boards += createBoard(serialNumber++, lines)
            lines.clear()
            continue
        }

        lines += line
    }

    return BingoGame(numbers, boards)
}


class BingoGame(
    private val numbers: List<Int>,
    private var boards: MutableList<Board>,
    private val wonBoards: MutableList<Board> = mutableListOf()
) {
    fun runPlaySimulation() {
        for (nextNumber in numbers) {
            for (board in boards) {
                board.markNumberIfExists(nextNumber)
            }

            val wonBoardIndexes = mutableListOf<Int>()
            for ((i, board) in boards.withIndex()) {
                if (board.hasBingo()) {
                    board.calculateScore(nextNumber)
                    wonBoardIndexes += i

                    if (boards.isEmpty()) return
                }
            }

            if (wonBoardIndexes.isNotEmpty()) {
                for (i in wonBoardIndexes) {
                    wonBoards += boards[i]
                }

                boards = boards.filter { !wonBoards.contains(it) }.toMutableList()
            }
        }

        if (boards.isNotEmpty()) {
            println("All numbers are drawn, but there are some boards that have not yet won: ${boards.size}")
        }
    }

    fun getFirstWonBoard(): Board {
        return wonBoards.first()
    }

    fun getLastWonBoard(): Board {
        return wonBoards.last()
    }

    private fun calculateScore(number: Int, board: Board): Int {
        return number * board.sumAllUnmarkedNumbers()
    }
}

data class Board(
    val serialNumber: Int,
    val numbers: List<MutableList<Number>>,
    var score: Int = 0,
) {
    override fun toString(): String {
        var output = ""
        output += "Board(${serialNumber}):\n"
        output += "-----------\n"
        for (line in numbers) {
            output += line.joinToString(" ")
            output += "\n"

        }
        return output
    }

    fun markNumberIfExists(numberToMark: Int) {
        for (row in numbers) {
            for (number in row) {
                if (number.value == numberToMark) {
                    number.isMarked = true
                }
            }
        }
    }

    fun hasBingo(): Boolean {
        return hasRowMarked() || hasColumnMarked()
    }

    fun hasRowMarked(): Boolean {
        for ((i, row) in numbers.withIndex()) {
            if (isRowMarked(row)) return true
        }
        return false
    }

    fun hasColumnMarked(): Boolean {
        for (i in 0 until numbers[0].size) {
            var isColumnMarked = true

            for (j in numbers.indices) {
                if (!numbers[j][i].isMarked) {
                    isColumnMarked = false
                    break
                }
            }

            if (isColumnMarked) return true
        }

        return false
    }

    fun calculateScore(number: Int) {
        score = number * sumAllUnmarkedNumbers()
    }

    private fun isRowMarked(row: List<Number>): Boolean {
        for (number in row) {
            if (!number.isMarked) return false
        }

        return true
    }

    fun sumAllUnmarkedNumbers(): Int {
        var sum = 0
        for (row in numbers) {
            sum += row.sumOf {
                if (!it.isMarked) it.value else 0
            }
        }
        return sum
    }
}

data class Number(
    val value: Int,
    var isMarked: Boolean = false,
) {
    override fun toString(): String {
        var output = ""
        if (value < 10) output += " "

        if (isMarked) {
            output += "x$value"
        } else {
            output += " $value"
        }

        return output
    }
}

fun createBoard(serialNumber: Int, lines: List<String>): Board {
    val numbers = mutableListOf<MutableList<Number>>()
    for (line in lines) {
        numbers += line.split(" ")
            .filter {
                it != ""
            }.map {
                Number(it.trim().toInt())
            }
            .toMutableList()
    }

    return Board(serialNumber, numbers)
}
