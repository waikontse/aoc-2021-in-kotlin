package puzzles

import util.ReaderUtil

class Day2 : Puzzle {
    val input = ReaderUtil.readResourseAsStrings("input2.txt")

    override fun solveDemoPart1(): String {
        TODO("Not yet implemented")
    }

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        input.map { str -> str.split("").map {  } }
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun splitLine(str : String) : Pair<String, Int> {
        str.split(" ")
    }

}