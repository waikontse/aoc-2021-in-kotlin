package puzzles

import util.ReaderUtil
import java.lang.RuntimeException

class Day2 : Puzzle {
    val input = ReaderUtil.readResourceAsStrings("input2.txt")
    val demoInput = ReaderUtil.readResourceAsStrings("input2demo.txt")

    override fun solveDemoPart1(): Int {
        return demoInput.map { str -> str.split(" ")}
            .fold(Pair(0,0)) { coordinates, command ->  solve(command, coordinates)}
            .let { it.first * it.second }
    }

    override fun solveDemoPart2(): Int {
        return demoInput.map { str -> str.split(" ")}
            .fold(Triple(0,0,0)) { coordinates, command ->  solve2(command, coordinates)}
            .let { it.first * it.second }
    }

    override fun solvePart1(): Int {
        return input.map { it.split(" ") }
            .fold(Pair(0,0)) { coordinates, command ->  solve(command, coordinates)}
            .let { it.first * it.second }
    }

    private fun solve(riddle: List<String>, coordinates: Pair<Int, Int>): Pair<Int, Int> {
        val head = riddle[0]
        val steps = riddle[1].toInt()
        return when(head) {
            "forward" -> coordinates.copy(first = coordinates.first + steps)
            "up" -> coordinates.copy( second = coordinates.second - steps)
            "down" -> coordinates.copy(second = coordinates.second + steps)
            else -> throw RuntimeException("Wrong input received $head")
        }
    }

    override fun solvePart2(): Int {
        return input.map { it.split(" ") }
            .fold(Triple(0,0,0)) { coordinates, command ->  solve2(command, coordinates)}
            .let { it.first * it.second }
    }

    private fun solve2(riddle: List<String>, coordinates: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
        val head = riddle[0]
        val steps = riddle[1].toInt()
        return when(head) {
            "forward" -> coordinates.copy(first = coordinates.first + steps, second = coordinates.second + coordinates.third * steps)
            "up" -> coordinates.copy( third = coordinates.third - steps)
            "down" -> coordinates.copy(third = coordinates.third + steps)
            else -> throw RuntimeException("Wrong input received $head")
        }
    }
}