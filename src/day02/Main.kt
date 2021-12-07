package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val commands = parseCommands(input)

        val submarine = Submarine()
        submarine.executeCommands(commands)

        return submarine.position.horizontal * submarine.position.depth
    }

    fun part2(input: List<String>): Int {
        val commands = parseCommands(input)

        val submarine = Submarine()
        submarine.executeCommandsWithAim(commands)

        return submarine.position.horizontal * submarine.position.depth
    }

    val input = readInput("day02/input")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}

fun parseCommands(input: List<String>): List<Command> {
    return input.map {
        val (direction, units) = it.split(" ")
        Command(direction, units.toInt())
    }
}

class Command(val direction: String, val units: Int)

class Position(
    var horizontal: Int = 0,
    var depth: Int = 0,
)

class Submarine(
    val position: Position = Position(),
    var aim: Int = 0,
) {
    fun executeCommands(commands: List<Command>) {
        for (command in commands) {
            move(command)
        }
    }

    fun executeCommandsWithAim(commands: List<Command>) {
        for (command in commands) {
            moveWithAim(command)
        }
    }

    private fun move(command: Command) {
        when (command.direction) {
            "forward" -> position.horizontal += command.units
            "up" -> position.depth -= command.units
            "down" -> position.depth += command.units
        }
    }

    private fun moveWithAim(command: Command) {
        when (command.direction) {
            "forward" -> {
                position.horizontal += command.units
                position.depth += command.units * aim
            }
            "up" -> {
                aim -= command.units
            }
            "down" -> {
                aim += command.units
            }
        }
    }
}
