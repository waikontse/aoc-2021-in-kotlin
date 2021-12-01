package puzzles

import util.ReaderUtil

class Day1 : Puzzle {
    private val input: List<String> = ReaderUtil.readResourseAsStrings("input1Full.txt")
    override fun solveDemoPart1(): String {
        TODO("Not yet implemented")
    }

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        return solve(convertToIntList(input)).toString()
    }

    override fun solvePart2(): String {
        return solve(convertToIntList(input)
            .zipWithNext()
            .zip(convertToIntList(input).drop(2))
            .map { foldZipped3(it) }).toString()
    }

    private fun convertToIntList(strings: List<String>) : List<Int> =
        strings.map { it.toInt() }.toList()

    private fun solve(riddle: List<Int>) : Int =
        riddle.zipWithNext()
            .map { if(it.first < it.second) 1 else 0 }
            .fold(0) { l, r -> l + r }

    private fun foldZipped3(zipped: Pair<Pair<Int, Int>, Int>) : Int =
        zipped.first.first + zipped.first.second + zipped.second
}