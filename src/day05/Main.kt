package day05

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val lines = input.map { Line(it) }
        val grid = Grid(findMaxX(lines), findMaxY(lines))

        for (line in lines) {
            if (line.isDiagonal()) continue

            grid.markPointsInLine(line)
        }

        return grid.countOverlaps()
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { Line(it) }
        val grid = Grid(findMaxX(lines), findMaxY(lines))

        for (line in lines) {
            grid.markPointsInLine(line)
        }

        return grid.countOverlaps()
    }

    val input = readInput("day05/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

class Point(input: String) {
    val x: Int
    val y: Int

    init {
        val parts = input.trim().split(",")
        x = parts[0].toInt()
        y = parts[1].toInt()
    }

    override fun toString() = "($x,$y)"
}

class Line(input: String) {
    val from: Point
    val to: Point

    init {
        val parts = input.split("->")
        from = Point(parts[0])
        to = Point(parts[1])
    }

    fun isVertical() = from.x == to.x

    fun isHorizontal() = from.y == to.y

    fun isDiagonal() = !isVertical() && !isHorizontal()

    override fun toString() = "$from -> $to"
}

fun findMaxX(lines: List<Line>): Int {
    var max: Int = Int.MIN_VALUE

    for (line in lines) {
        if (line.from.x > max) {
            max = line.from.x
        }

        if (line.to.x > max) {
            max = line.to.x
        }
    }

    return max
}

fun findMaxY(lines: List<Line>): Int {
    var max = Int.MIN_VALUE

    for (line in lines) {
        if (line.from.y > max) {
            max = line.from.y
        }

        if (line.to.y > max) {
            max = line.to.y
        }
    }

    return max
}

class Grid(
    val columns: Int = 0,
    val rows: Int = 0,
) {

    // 1st list is for columns (Point.x)
    // 2nd list if for rows (Point.y)
    val grid: MutableList<MutableList<Int>>

    init {
        grid = MutableList(columns + 1) { MutableList(rows + 1) { 0 } }
    }

    override fun toString(): String {
        var out = ""
        for (column in grid) {
            for (row in column) {
                out += if (row == 0) "." else "$row"
            }
            out += "\n"
        }
        return out
    }

    fun markPointsInLine(line: Line) {
        if (line.isHorizontal()) {
            val column = line.from.y
            for (row in getRange(line.from.x, line.to.x)) {
                grid[column][row] += 1
            }
        }

        if (line.isVertical()) {
            val row = line.from.x
            for (column in getRange(line.from.y, line.to.y)) {
                grid[column][row] += 1
            }
        }

        if (line.isDiagonal()) {
            val columns = getRange(line.from.y, line.to.y)
            val rows = getRange(line.from.x, line.to.x)

            for ((column, row) in columns zip rows) {
                grid[column][row] += 1
            }
        }
    }

    fun countOverlaps(threshold: Int = 1): Int {
        var overlaps = 0
        for (columns in grid) {
            for (row in columns) {
                if (row > threshold) overlaps++
            }
        }

        return overlaps
    }

    private fun getRange(from: Int, to: Int): IntProgression {
        return if (from < to) {
            from..to
        } else {
            from downTo to
        }
    }
}