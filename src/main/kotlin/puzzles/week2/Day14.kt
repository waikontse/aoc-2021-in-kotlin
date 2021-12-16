package puzzles.week2

import puzzles.Puzzle
import java.math.BigInteger

class Day14: Puzzle(14) {
    override fun solveDemoPart1(): String {
        return solve(inputDemo, 10)
    }

    override fun solveDemoPart2(): String {
        return solve(inputDemo, 40)
    }

    override fun solvePart1(): String {
        return solve(input, 10)
    }

    override fun solvePart2(): String {
        return solve(input, 40)
    }

    private fun solve(input: List<String>, steps: Int): String {
        tailrec fun solve(polymer: Map<String, BigInteger>, polymerInsertionRules: Map<String, String>, steps: Int, lastChar: Char): String {
            if (steps < 1) {
                val polymerChainGrouped = breakDownPolymers(polymer, lastChar)
                return (polymerChainGrouped.maxOf { it.value } -  polymerChainGrouped.minOf { it.value }).toString()
            }

            val nextPolymerChain = polymer.entries
                .flatMap { getNextPolymerChain(it, polymerInsertionRules) }
                .groupBy({it.first}, {it.second})
                .mapValues { (_,values) -> values.fold(BigInteger.ZERO) { acc, count -> acc.plus(count) }}

            return solve(nextPolymerChain, polymerInsertionRules, steps.dec(), lastChar)
        }

        val polymerTemplate = input.first().zipWithNext().map { it.getPolymer() to BigInteger.ONE }.toMap()
        val polymerInsertionRules = preparePolymerInsertionRules(input.drop(2))
        val lastChar = input.first().last()

        return solve(polymerTemplate, polymerInsertionRules, steps, lastChar)
    }

    private fun getNextPolymerChain(polymer: Map.Entry<String, BigInteger>,
                                    polymerInsertionRules: Map<String, String>): List<Pair<String, BigInteger>> {
        val pair1 = Pair(polymer.key[0] + polymerInsertionRules[polymer.key]!!, polymer.value)
        val pair2 = Pair(polymerInsertionRules[polymer.key]!! + polymer.key[1], polymer.value)

        return listOf(pair1, pair2)
    }

    private fun breakDownPolymers(polymer: Map<String, BigInteger>, lastChar: Char): Map<Char, BigInteger> {
        return polymer.entries.map { it.key[0] to it.value }
            .plus(lastChar to BigInteger.ONE)
            .groupBy({it.first}, {it.second})
            .mapValues { (_, values) -> values.fold(BigInteger.ZERO) { acc, c -> acc.plus(c) } }

    }

    private fun preparePolymerInsertionRules(rules: List<String>): Map<String, String>
            = rules.map { it.split("->") }.associate { it[0].trim() to it[1].trim() }

    private fun Pair<Char, Char>.getPolymer(): String
            =  this.first.toString()+this.second
}