package puzzles.week1

import puzzles.Puzzle
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Day7: Puzzle(7) {
    override fun solveDemoPart1(): String {
        return solve(inputDemo, ::getFuelCostPart1).toString()
    }

    override fun solveDemoPart2(): String {
        return solve(inputDemo, ::getFuelCostPart2).toString()
    }

    override fun solvePart1(): String {
        return solve(input, ::getFuelCostPart1).toString()
    }

    override fun solvePart2(): String {
        return solve(input, ::getFuelCostPart2).toString()
    }

    private fun solve(input: List<String>, fuelCalc: (Int, Int) -> Int): Int {
        val minValue = input.flatMap { it.split(",") }.map { it.toInt() }.minOrNull()
        val avg = input.flatMap { it.split(",") }.map { it.toInt() }.average().toInt()

        val crabsMap = input.flatMap { it.split(",") }
            .groupingBy { it.toInt() }.eachCount()

        return (minValue!!..(avg)+1).minOf { findDistance(crabsMap, it, fuelCalc) }
    }

    private fun findDistance (crabsMap: Map<Int, Int>, dest: Int, fuelCalc: (Int, Int) -> Int): Int
            = crabsMap.entries.sumOf { k -> fuelCalc(k.key, dest) * k.value }

    private fun getFuelCostPart1(from: Int, to: Int): Int
            = max(from, to).minus(min(from, to))

    private fun getFuelCostPart2(from: Int, to: Int): Int {
        val steps = abs(from - to)
        return ((1+steps).toDouble().div(2) * steps).toInt()
    }
}