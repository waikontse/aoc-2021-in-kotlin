package puzzles.week2

import puzzles.Puzzle

class Day11: Puzzle(11) {
    override fun solveDemoPart1(): String = solve(inputDemo).toString()

    override fun solveDemoPart2(): String = solve2(inputDemo).toString()

    override fun solvePart1(): String = solve(input).toString()

    override fun solvePart2(): String = solve2(input).toString()

    private fun solve(input: List<String>): Int {
        val octoMap = input.map { it.toCharArray().toList() }.flatMap { it.map { c -> c.toString().toInt() } }
        val width = input[0].length
        val height = input.size

        return simulateDays(100, octoMap, width, height)
    }

    private fun solve2(input: List<String>): Int {
        val octoMap = input.map { it.toCharArray().toList() }.flatMap { it.map { c -> c.toString().toInt() } }
        val width = input[0].length
        val height = input.size

        return simulateDays2(octoMap, width, height)
    }

    private fun simulateDays(daysToSimulate: Int, octoMap: List<Int>, width: Int, height: Int): Int {
        tailrec fun simulateDays(daysToSimulate: Int, currentDay: Int, octoMap: List<Int>, width: Int, heigth: Int, totalFlashes: Int): Int {
            if (currentDay >= daysToSimulate) {
                return totalFlashes
            }

            val propagatedOctoMap = increaseAndPropagate(octoMap, width, height)
            val flashingPoints = countFlashingPoints(propagatedOctoMap)
            val cleanedPropagatedMap = propagatedOctoMap.map { if (isFlashing(it)) 0 else it }

            return simulateDays(daysToSimulate, currentDay.inc(), cleanedPropagatedMap, width, heigth, totalFlashes.plus(flashingPoints))
        }

        return simulateDays(daysToSimulate, 0, octoMap, width, height, 0)
    }

    private fun simulateDays2(octoMap: List<Int>, width: Int, height: Int): Int {
        tailrec fun simulateDays2(currentDay: Int, octoMap: List<Int>, width: Int, height: Int, totalFlashes: Int): Int {
            if (totalFlashes == width * height) {
                return currentDay
            }

            val propagatedOctoMap = increaseAndPropagate(octoMap, width, height)
            val flashingPoints = countFlashingPoints(propagatedOctoMap)
            val cleanedPropagatedMap = propagatedOctoMap.map { if (isFlashing(it)) 0 else it }

            return simulateDays2(currentDay.inc(), cleanedPropagatedMap, width, height, flashingPoints)
        }

        return simulateDays2(0, octoMap, width, height, 0)
    }

    private fun increaseAndPropagate(octoMap: List<Int>, width: Int, height: Int): List<Int> {
        val propagatedOctoMap = octoMap.map { it + 1 }.let {
            propagateFlashes(it,
                it.indices.filter { i -> isFlashing(it[i]) }.map { i -> getPositionFromIndex(i, width) }
                , width, height
            )
        }

        return propagatedOctoMap
    }

    private fun countFlashingPoints(octoMap: List<Int>): Int = octoMap.count { isFlashing(it) }

    private fun isFlashing(point: Int): Boolean = point > 9

    private tailrec fun propagateFlashes(octoMap: List<Int>, positions: List<Pair<Int, Int>>, width: Int, height: Int): List<Int> {
        if (positions.isEmpty()) {
            return octoMap
        }

        return propagateFlashes(propagateFlash(octoMap, positions.first(), width, height, true), positions.drop(1), width, height)
    }

    private fun propagateFlash(octoMap: List<Int>, position: Pair<Int, Int>, width: Int, height: Int, force: Boolean): List<Int> {
        if (isOutOfBounds(position, width, height)) { return octoMap }
        if (isFlashing(octoMap[position.getIndex(width)]) && !force) { return octoMap }

        val updatedMap = octoMap.mapIndexed { i, e -> if (i == position.getIndex(width)) e.inc() else e }

        if (!isFlashing(updatedMap[position.getIndex(width)])) {
            return updatedMap
        }

        return updatedMap
            .let { propagateFlash(it, getTopLeft(position), width, height, false) }
            .let { propagateFlash(it, getTop(position), width, height, false) }
            .let { propagateFlash(it, getTopRight(position), width, height, false) }
            .let { propagateFlash(it, getLeft(position), width, height, false) }
            .let { propagateFlash(it, getRight(position), width, height, false) }
            .let { propagateFlash(it, getBottomLeft(position), width, height, false) }
            .let { propagateFlash(it, getBottomRight(position), width, height, false) }
            .let { propagateFlash(it, getBottom(position), width, height, false) }
    }

    private fun isOutOfBounds(pos: Pair<Int, Int>, width: Int, height: Int)
            = isXOutOfBounds(pos.first, width) || isYOutOfBounds(pos.second, height)

    private fun isYOutOfBounds(y: Int, height: Int): Boolean
            = y < 0 || y >= height

    private fun isXOutOfBounds(x: Int, width: Int): Boolean
            = x < 0 || x >= width

    private fun getTopLeft(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(first = pos.first.dec(), second = pos.second.dec())
    }
    private fun getTop(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(second = pos.second.dec())
    }
    private fun getTopRight(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(first = pos.first.inc(), second = pos.second.dec())
    }

    private fun getBottomLeft(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(first = pos.first.dec(), second = pos.second.inc())
    }
    private fun getBottom(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(second = pos.second.inc())
    }
    private fun getBottomRight(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(first = pos.first.inc(), second = pos.second.inc())
    }

    private fun getLeft(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(first = pos.first.dec())
    }
    private fun getRight(pos: Pair<Int, Int>): Pair<Int, Int> {
        return pos.copy(first = pos.first.inc())
    }

    private fun getPositionFromIndex(index: Int, width: Int): Pair<Int, Int> {
        return Pair(index%width, index/width)
    }

    fun Pair<Int, Int>.getIndex(width: Int): Int {
        return this.first + this.second*width
    }
}