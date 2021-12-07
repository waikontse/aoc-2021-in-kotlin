package puzzles

import java.math.BigInteger

class Day6: Puzzle(6) {
    override fun solveDemoPart1(): String {
        return solve(inputDemo, 80).toString()
    }

    override fun solvePart1(): String {
        return solve(input, 80).toString()
    }

    override fun solveDemoPart2(): String {
        return solve(inputDemo, 256).toString()
    }

    override fun solvePart2(): String {
        return solve(input, 256).toString()
    }

    private fun solve(startingColony: List<String>, simulationDays: Int): BigInteger {
        return initColony(startingColony.flatMap { it.split(",") })
            .apply { simulate(simulationDays, this) }
            .values.fold(BigInteger.ZERO) { acc, v -> acc.add(v) }
    }

    private fun initColony(startingColony: List<String>): MutableMap<Int, BigInteger> {
        val colonyMap = (-1..8).associateWith { BigInteger.ZERO }.toMutableMap()
        startingColony.forEach {
            colonyMap.compute(it.toInt()) { _,v -> v?.plus(BigInteger.ONE) }
        }

        return colonyMap
    }

    private fun simulate(days: Int, colony: MutableMap<Int, BigInteger>) {
        tailrec fun simulate(days: Int, colony: MutableMap<Int, BigInteger>) {
            if (days < 1) { return }

            reduceDay(colony)
            updateRebornGen(colony)
            updateNewGen(colony)
            colony[-1] = BigInteger.ZERO
            simulate(days-1, colony)
        }

        simulate(days, colony)
    }

    private fun reduceDay(colony: MutableMap<Int, BigInteger>) {
        (0..8).forEach { colony[it-1] = colony[it]!! }
    }

    private fun updateRebornGen(colony: MutableMap<Int, BigInteger>) {
        colony[6] = colony[-1]!! + colony[6]!!
    }

    private fun updateNewGen(colony: MutableMap<Int, BigInteger>) {
        colony[8] = colony[-1]!!
    }
}