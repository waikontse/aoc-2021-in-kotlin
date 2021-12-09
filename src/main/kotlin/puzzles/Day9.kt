package puzzles

class Day9: Puzzle(9) {
    override fun solveDemoPart1(): String = solve(inputDemo)

    override fun solveDemoPart2(): String = solve2(inputDemo)

    override fun solvePart1(): String = solve(input)

    override fun solvePart2(): String = solve2(input)

    private fun solve(input: List<String>): String {
        val heightMap = input.map { it.toCharArray() }
            .flatMap { it.toList().map { c -> c.toString().toInt() } }
        val width = input[0].length
        val height = input.size

        return heightMap.filterIndexed { index, _ -> isPointSmallestAt(heightMap, index%width, index/width ,width, height) }
            .sumOf { it+1 }.toString()
    }

    private fun solve2(input: List<String>): String {
        val heightMap = input.map { it.toCharArray() }
            .flatMap { it.toList().map { c -> c.toString().toInt() } }
        val width = input[0].length
        val height = input.size

        return heightMap.indices.asSequence()
            .filter { isPointSmallestAt(heightMap, it%width, it/width, width, height) }
            .map { findBasinSizeAtPoint(heightMap, it%width, it/width, width, height) }
            .sortedDescending()
            .take(3)
            .fold(1) {acc, c -> acc * c}
            .toString()
    }

    private fun isPointSmallestAt(heightMap: List<Int>, x: Int, y: Int, width: Int, height: Int): Boolean {
        return isPointSmallestAt(gatherPointsAt(heightMap, x, y, width, height), heightMap[getPos(x, y, width)])
    }
    private fun isPointSmallestAt(points: List<Int>, target: Int): Boolean {
        return if (points.distinct().size == 1) false else points.minOrNull() == target
    }

    private fun findBasinSizeAtPoint(heightMap: List<Int>, x: Int, y: Int, width: Int, height: Int): Int {
        val toVisitList = mutableListOf(Pair(x, y))
        val seenList = mutableListOf(Pair(x, y))

        while(toVisitList.isNotEmpty()) {
            val currentSpot = toVisitList.removeFirst()
            val currentSpotValue = heightMap[getPos(currentSpot.first, currentSpot.second, width)]

            val topValue = getTop(heightMap, currentSpot.first, currentSpot.second, width)
            val bottomValue = getBottom(heightMap, currentSpot.first, currentSpot.second, width, height)
            val leftValue = getLeft(heightMap, currentSpot.first, currentSpot.second, width)
            val rightValue = getRight(heightMap, currentSpot.first, currentSpot.second, width)

            updateOnPoint(toVisitList,seenList, currentSpotValue, topValue, currentSpot.copy(second = currentSpot.second.dec()))
            updateOnPoint(toVisitList,seenList, currentSpotValue, bottomValue, currentSpot.copy(second = currentSpot.second.inc()))

            updateOnPoint(toVisitList,seenList, currentSpotValue, leftValue, currentSpot.copy(first = currentSpot.first.dec()))
            updateOnPoint(toVisitList,seenList, currentSpotValue, rightValue, currentSpot.copy(first = currentSpot.first.inc()))
        }

        return seenList.size
    }

    private fun updateOnPoint(toVisitList: MutableList<Pair<Int, Int>>,
                              seenList: MutableList<Pair<Int, Int>>,
                              currentSpotValue: Int,
                              toVisitSpotValue: Int,
                              toVisitSpot: Pair<Int, Int>) {
        if (toVisitSpotValue != 9 && toVisitSpotValue > currentSpotValue) {
            if (!seenList.contains(toVisitSpot)) {
                seenList.add(toVisitSpot)
                toVisitList.add(toVisitSpot)
            }
        }
    }

    private fun gatherPointsAt(heightMap: List<Int>, x: Int, y: Int, width: Int, height: Int): List<Int> {
        return listOf(
            getTop(heightMap, x, y, width), getBottom(heightMap, x, y, width, height),
            getLeft(heightMap, x, y, width), getRight(heightMap, x, y, width),
            heightMap[getPos(x, y, width)])
    }

    private fun getTop(heightMap: List<Int>, x: Int, y: Int, width: Int): Int
            = if (y <= 0) 9 else heightMap[getPos(x, y-1, width)]

    private fun getBottom(heightMap: List<Int>, x: Int, y: Int, width: Int, height: Int): Int
            = if (y>= height-1) 9 else heightMap[getPos(x, y+1, width)]

    private fun getLeft(heightMap: List<Int>, x: Int, y: Int, width: Int): Int
            = if (x <= 0) 9 else heightMap[getPos(x-1, y, width)]

    private fun getRight(heightMap: List<Int>, x: Int, y: Int, width: Int): Int
            = if (x>= width-1) 9 else heightMap[getPos(x+1, y, width)]

    private fun getPos(x: Int, y: Int, width: Int): Int = x + y*width
}