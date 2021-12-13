package puzzles.week2

import puzzles.Puzzle

class Day11: Puzzle(11) {
    override fun solveDemoPart1(): String = solve(inputDemo).toString()

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String = solve(input).toString()

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun solve(input: List<String>): Int {
        val octoMap = input.map { it.toCharArray().toList() }.flatMap { it.map { c -> c.toString().toInt() } }
        val width = input[0].length
        val height = input.size

        simulateDays(1, octoMap, width, height)

        return 0
    }

    private fun simulateDays(daysToSimulate: Int, octoMap: List<Int>, width: Int, heigth: Int): Int {
        tailrec fun simulateDays(daysToSimulate: Int, currentDay: Int, octoMap: List<Int>, width: Int, heigth: Int, totalFlashes: Int): Int {
            if (currentDay >= daysToSimulate) {
                return totalFlashes
            }

            // increase all value by 1
            val propagatedFlashesMap = octoMap.map { it+1 }
                .let { propagateFlashes(it, it.indices.filter { i -> isFlashing(i) }, width, heigth) }

            val flashingPoints = countFlashingPoints(propagatedFlashesMap)

            println ("propagated")
            propagatedFlashesMap.chunked(width).forEach { println(it) }

            return simulateDays(daysToSimulate, currentDay.inc(), propagatedFlashesMap, width, heigth, totalFlashes.plus(flashingPoints))
        }

        return simulateDays(daysToSimulate, 0, octoMap, width, heigth, 0)
    }

    private fun countFlashingPoints(octoMap: List<Int>): Int = octoMap.count { isFlashing(it) }

    private fun isFlashing(point: Int): Boolean = point > 9

    private fun propagateFlashes(octoMap: List<Int>, index: List<Int>, width: Int, heigth: Int): List<Int> {
        tailrec fun propagateFlashes(toVisit: List<Pair<Int, Int>>,
                                     octoMap: List<Int>,
                                     width: Int,
                                     heigth: Int): List<Int> {
            if (toVisit.isEmpty()) {
                return octoMap
            }


        }


        return octoMap
    }

    private fun propagateFlash(octoMap: List<Int>, index: Int, width: Int, heigth: Int): List<Int> {
        // out of bounds, return
        if (outOfBounds) { return octoMap }
        if (isFlashing()) { return octoMap }

        return octoMap
            // top left
            .let { propagateFlash(it, index, width, heigth) }
            // top
            .let {  }
            // top right
            .let {  }
            // left
            .let {  }
            // right
            .let {  }
            // bottom left
            .let {  }
            // bottom right
            .let {  }
            // bottom
            .let {  }
    }

    private fun getTopLeft(): Pair<Int, Int> {
        return Pair(0,0)
    }
    private fun getTop(): Pair<Int, Int> {
        return Pair(0,0)
    }
    private fun getTopRight(): Pair<Int, Int> {
        return Pair(0,0)
    }

    private fun getBottomLeft(): Pair<Int, Int> {
        return Pair(0,0)
    }
    private fun getBottom(): Pair<Int, Int> {
        return Pair(0,0)
    }
    private fun getBottomRight(): Pair<Int, Int> {
        return Pair(0,0)
    }

    private fun getLeft(): Pair<Int, Int> {
        return Pair(0,0)
    }
    private fun getRight(): Pair<Int, Int> {
        return Pair(0,0)
    }

    private fun getPositionFromIndex(index: Int, width: Int): Pair<Int, Int> {
        return Pair(index%width, index/width)
    }
}