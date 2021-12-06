package puzzles

import util.ReaderUtil
import kotlin.math.max

class Day5: Puzzle(5) {
    val inputDemo = ReaderUtil.readResourceAsStrings("input5demo.txt")
    val input = ReaderUtil.readResourceAsStrings("input5.txt")

    override fun solveDemoPart1(): String {
        return solvePart1(inputDemo)
    }

    override fun solvePart1(): String {
        return solvePart1(input)
    }

    private fun solvePart1(rawInput: List<String>): String {
        val vectors = rawInput.map { it.split(" -> ") }
            .map { parseLine(it) }
            .let { filterDiagonalLines(it) }
            .let { reorderVectors(it) }
        val largestNumber = findLargestPoint(vectors)
        val map = initializeMap(largestNumber+1)

        vectors.forEach { markLine(map, it.first, it.second, largestNumber+1) }

        return map.count { it > 1 }.toString()
    }

    override fun solveDemoPart2(): String {
        return solvePart2(inputDemo).toString()
    }

    override fun solvePart2(): String {
       return solvePart2(input).toString()
    }

    fun solvePart2(rawInput: List<String>): Int {
        val vectors = rawInput.map { it.split(" -> ") }
            .map { parseLine(it) }
            .let { reorderVectors(it) }
        val largestNumber = findLargestPoint(vectors)
        val map = initializeMap(largestNumber+1)

        vectors.forEach { markLine(map, it.first, it.second, largestNumber+1) }

        return map.count { it > 1 }
    }

    private fun initializeMap(size: Int): MutableList<Int> = MutableList(size*size) {0}

    private fun parseLine(vector: List<String>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return vector.map { it.split(",") }
            .map { Pair(it[0].trim().toInt(), it[1].trim().toInt()) }
            .let { Pair(it[0], it[1]) }
    }

    private fun findLargestPoint(vectors: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        return vectors.maxOf { max(max(it.first.first, it.first.second), max(it.second.first, it.second.second)) }
    }

    private fun filterDiagonalLines(vectors: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        return vectors.filter { it.first.first == it.second.first || it.first.second == it.second.second }
            .toList()
    }

    private fun reorderVectors(vectors: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        return vectors.map { reorderVector(it) }
    }
    
    private fun reorderVector(vector: Pair<Pair<Int, Int>, Pair<Int, Int>>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        return if (vector.first.first < vector.second.first) {
            vector
        } else if (vector.first.first > vector.second.first) {
            vector.copy(first = vector.second, second = vector.first)
        } else if (vector.first.first == vector.second.first && vector.first.second > vector.second.second) {
            vector.copy(first = vector.second, second = vector.first)
        } else {
            // First point is above the second point
            vector
        }
    }

    private fun markLine(map: MutableList<Int>, from: Pair<Int, Int>, to: Pair<Int, Int>, maxSize: Int) {
        tailrec fun markLine2(map: MutableList<Int>, from: Pair<Int, Int>, to: Pair<Int, Int>, maxSize: Int) {
            if (from == to) {
                map[from.first + from.second * maxSize]++
                return
            }

            map[from.first + from.second * maxSize]++
            markLine2(map, getNewPoint(from, to), to, maxSize)
        }

        return markLine2(map, from, to, maxSize)
    }

    private fun getNewPoint(from: Pair<Int, Int>, to: Pair<Int, Int>): Pair<Int, Int> {
        return if (from.first == to.first) {
            from.copy(second = from.second.inc())
        } else if (from.first < to.first && from.second == to.second) {
            from.copy(first = from.first.inc())
        } else if (from.first != to.first && from.second < to.second) {
            from.copy(first = from.first.inc(), second = from.second.inc())
        } else {
            from.copy(first = from.first.inc(), second = from.second.dec())
        }
    }
}