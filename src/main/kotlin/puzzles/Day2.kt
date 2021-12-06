package puzzles

import util.ReaderUtil

class Day2 : Puzzle(2) {
    val input = ReaderUtil.readResourceAsStrings("input2.txt")
    val demoInput = ReaderUtil.readResourceAsStrings("input2demo.txt")

    val cmdMapper2 = { cmd: String, steps: Int, coor: List<Int> ->
        when (cmd) {
            "forward" ->  coor.toMutableList().apply { this[0] = this[0] + steps }
            "up" -> coor.toMutableList().apply { this[1] = this[1] - steps }
            "down" -> coor.toMutableList().apply { this[1] = this[1] + steps }
            else -> throw RuntimeException("Wrong input received $cmd")
        }
    }

    val cmdMapper3 = { cmd: String, steps: Int, coor: List<Int> ->
        when(cmd) {
            "forward" -> coor.toMutableList().apply {this[0] = this[0] + steps; this[1] = this[1] + this[2] * steps}
            "up" -> coor.toMutableList().apply { this[2] = this[2] - steps }
            "down" -> coor.toMutableList().apply { this[2] = this[2] + steps }
            else -> throw RuntimeException("Wrong input received $cmd")
        }
    }

    override fun solveDemoPart1(): String {
        return demoInput.map { str -> str.split(" ")}
            .fold(listOf(0,0)) { coordinates, command -> cmdMapper2(command[0], command[1].toInt(), coordinates) }
            .let { it[0] * it[1] }
            .toString()
    }

    override fun solveDemoPart2(): String {
        return demoInput.map { str -> str.split(" ")}
            .fold(listOf(0,0,0)) { coordinates, command ->  cmdMapper3(command[0], command[1].toInt(), coordinates) }
            .let { it[0] * it[1] }
            .toString()
    }

    override fun solvePart1(): String {
        return input.map { it.split(" ") }
            .fold(listOf(0,0)) { coordinates, command -> cmdMapper2(command[0], command[1].toInt(), coordinates) }
            .let { it[0] * it[1] }
            .toString()
    }

    override fun solvePart2(): String {
        return input.map { it.split(" ") }
            .fold(listOf(0,0,0)) { coordinates, command -> cmdMapper3(command[0], command[1].toInt(), coordinates) }
            .let { it[0] * it[1] }
            .toString()
    }
}