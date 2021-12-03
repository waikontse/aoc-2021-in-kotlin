package puzzles

import util.ReaderUtil

class Day1 : Puzzle {
    private val input: List<String> = ReaderUtil.readResourceAsStrings("input1.txt")
    override fun solveDemoPart1(): Int {
        TODO("Not yet implemented")
    }

    override fun solveDemoPart2(): Int {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): Int {
        return convertToIntList(input)
            .zipWithNext { l, r -> l < r}
            .count { it }
    }

    override fun solvePart2(): Int {
        return convertToIntList(input)
            .windowed(3) { list -> list.sumOf { it } }
            .zipWithNext {l, r -> l < r}
            .count { it }
    }

    private fun convertToIntList(strings: List<String>) : List<Int> =
        strings.map { it.toInt() }.toList()
}