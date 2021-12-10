package puzzles.week1

import puzzles.Puzzle

class Day2 : Puzzle(2) {
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
        return solve(inputDemo, listOf(0,0), cmdMapper2)
    }

    override fun solveDemoPart2(): String {
        return solve(inputDemo, listOf(0,0,0), cmdMapper3)
    }

    override fun solvePart1(): String {
        return solve(input, listOf(0,0), cmdMapper2)
    }

    override fun solvePart2(): String {
        return solve(input, listOf(0,0,0), cmdMapper3)
    }

    private fun solve(input: List<String>, acc: List<Int>, cmdMapper: (String, Int, List<Int>) -> MutableList<Int>): String {
        return input.map { it.split(" ") }
            .fold(acc) { coordinates, command -> cmdMapper(command[0], command[1].toInt(), coordinates) }
            .let { it[0] * it[1] }
            .toString()
    }
}