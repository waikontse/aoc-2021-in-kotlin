package puzzles

import util.ReaderUtil

abstract class Puzzle(val day: Int, val log: Boolean = false) {
    val inputDemo = ReaderUtil.readResourceAsStrings("input${day}demo.txt")
    val input = ReaderUtil.readResourceAsStrings("input${day}.txt")

    abstract fun solveDemoPart1() : String
    abstract fun solveDemoPart2() : String
    abstract fun solvePart1() : String
    abstract fun solvePart2() : String

    fun log(message: Any?) {
        if (log) {
            println(message)
        }
    }
}