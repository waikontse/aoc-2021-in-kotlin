package puzzles

import util.ReaderUtil

class Day3: Puzzle {
    val inputDemo: List<String> = ReaderUtil.readResourceAsStrings("input3demo.txt")
    val input: List<String> = ReaderUtil.readResourceAsStrings("input3.txt")

    override fun solveDemoPart1(): Int {
        return solve1(inputDemo.fold(MutableList(5){mutableMapOf()}) { acc, riddle -> count1(riddle, acc) })
    }

    override fun solvePart1(): Int {
        return solve1(input.fold(MutableList(12) {mutableMapOf()}) { acc, riddle -> count1(riddle, acc) })
    }
    override fun solvePart2(): Int {
        TODO("Not yet implemented")
    }

    override fun solveDemoPart2(): Int {
        TODO("Not yet implemented")
    }

    private fun count1(riddle: String, counter: List<MutableMap<Char, Int>>): List<MutableMap<Char, Int>> {
        riddle.toCharArray().forEachIndexed {
                index, c -> counter[index].compute(c) { _, oldInt -> if (oldInt == null) 1 else oldInt+1 }
        }
        return counter
    }

    private fun solve1(counter: List<MutableMap<Char, Int>>): Int {
        val gamma: String = counter.map { c -> if (c['0']!! < c['1']!!) '1' else '0' }.joinToString("")
        val epsilon: String = counter.map { c -> if (c['0']!! < c['1']!!) '0' else '1' }.joinToString("")

        return gamma.toInt(2) * epsilon.toInt(2)
    }
}