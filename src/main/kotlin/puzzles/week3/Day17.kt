package puzzles.week3

import puzzles.Puzzle
import kotlin.math.abs

typealias XVelocity = Int
typealias YVelocity = Int
data class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
    fun getAbsMinY(): Int = abs(minY)
    fun getAbsMaxY(): Int = abs(maxY)
}

class Day17: Puzzle(17, true) {
    override fun solveDemoPart1(): String {
        return findHighestYVelocity(inputDemo)
    }

    override fun solveDemoPart2(): String {
        val targetArea = parseInput(inputDemo)
        findTriangleNumbersWidth(targetArea)
            .onEach { log(it) }
        log ("Trivial cases ${findTriangleNumbersWidthTrivial(targetArea)}")

        return "0"
    }

    override fun solvePart1(): String {
        return findHighestYVelocity(input)
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun findHighestYVelocity(input: List<String>): String {
        val targetArea = parseInput(input)
        return findTriangleNumbersHeight(targetArea)
            .onEach { println(it) }
            .last()
            .first.toString()
    }

    private fun parseInput(input: List<String>): TargetArea {
        val splitInput = input[0]
            .replace(",", "")
            .replace("x=", "")
            .replace("y=", "")
            .split(" ")
        val xValues = splitInput[2].split("..")
        val yValues = splitInput[3].split("..")

        return TargetArea(xValues[0].toInt(), xValues[1].toInt(), yValues[0].toInt(), yValues[1].toInt())
    }

    private fun findTriangleNumbersWidth(target: TargetArea): List<Pair<Int, Int>> {
        return (1..target.maxX/2+1).filter { xVelocityHitsArea(it, target) }
    }

    private fun findTriangleNumbersWidthTrivial(target: TargetArea): Int {
        return (target.maxX - target.minX).inc().times((target.getAbsMinY()-target.getAbsMaxY()).inc())
    }

    private fun findTriangleNumbersHeight(target: TargetArea): List<Pair<Int, Int>> {
        tailrec fun findTriangleNumbersHeight(target: TargetArea, velocity: YVelocity, acc: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
            if (velocity >= target.getAbsMinY()) {
                return acc
            }

            val maxHeight = findMaxHeigth(velocity)
            val newAccList = if (yVelocityHitsArea(velocity, target)) acc.plus(maxHeight to velocity) else acc
            return findTriangleNumbersHeight(target, velocity.inc(), newAccList)
        }

        return findTriangleNumbersHeight(target, 0, listOf())
    }

    private fun findMaxHeigth(yVelocity: YVelocity): Int {
        return (yVelocity * (yVelocity + 1).toDouble().div(2)).toInt()
    }

    private fun xVelocityHitsArea(initialVelocity: XVelocity, target: TargetArea): Boolean {
        tailrec fun xVelocityHitsArea(velocity: XVelocity, target: TargetArea, sum: Int): Boolean {
            log("Velocity: $velocity sum: $sum")
            if (sum >= target.minX && sum <= target.maxX) {
                return true
            }
            if (sum > target.maxX || velocity < 1) {
                return false
            }

            return xVelocityHitsArea(velocity.dec(), target, sum.plus(velocity.dec()))
        }

        return xVelocityHitsArea(initialVelocity, target, initialVelocity)
    }

    private fun yVelocityHitsArea(initialVelocity: YVelocity, target: TargetArea): Boolean {
        tailrec fun yVelocityHitsArea(velocity: YVelocity, target: TargetArea, sum: Int): Boolean {
            log ("velocity: $velocity $target sum: $sum")
            if (sum > target.getAbsMinY()) {
                return false
            }

            if (sum >= target.getAbsMaxY() && sum <= target.getAbsMinY()) {
                return true
            }

            return yVelocityHitsArea(velocity.inc(), target, sum.plus(velocity))
        }

        return yVelocityHitsArea(initialVelocity, target, initialVelocity.inc())
    }
}