package puzzles

import util.ReaderUtil

class Day1 : Puzzle(1) {

    private val input: List<String> = ReaderUtil.readResourceAsStrings("input1.txt")
    override fun solveDemoPart1(): String {
        TODO("Not yet implemented")
    }

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        return convertToIntList(input)
            .zipWithNext { l, r -> l < r}
            .count { it }
            .toString()
    }

    override fun solvePart2(): String {
        return convertToIntList(input)
            .windowed(3) { list -> list.sumOf { it } }
            .zipWithNext {l, r -> l < r}
            .count { it }
            .toString()
    }

    private fun convertToIntList(strings: List<String>) : List<Int> =
        strings.map { it.toInt() }.toList()
}