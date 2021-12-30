package puzzles.week3

import puzzles.Puzzle
import kotlin.math.abs

typealias XVelocity = Int
typealias YVelocity = Int
data class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
    fun getAbsMinY(): Int = abs(minY)
    fun getAbsMaxY(): Int = abs(maxY)

    fun isInYArea(height: Int): Boolean = height in minY..maxY
}

class Day17: Puzzle(17, false) {
    override fun solveDemoPart1(): String {
        return findHighestYVelocity(inputDemo)
    }

    override fun solveDemoPart2(): String {
        return findTotalCombinations(inputDemo)
    }

    override fun solvePart1(): String {
        return findHighestYVelocity(input)
    }

    override fun solvePart2(): String {
        return findTotalCombinations(input)
    }

    private fun findYVelocitiesForXVelocity(xVelocityConfig: Pair<Int, Int>, target: TargetArea): List<Int> {
        return (target.minY..target.getAbsMinY())
            .filter { yVelocityStepsToHitArea(it, target, xVelocityConfig.second, xVelocityConfig.first == xVelocityConfig.second) }
    }

    private fun findHighestYVelocity(input: List<String>): String {
        val targetArea = parseInput(input)
        return findTriangleNumbersHeight(targetArea)
            .last()
            .first.toString()
    }

    private fun findTotalCombinations(input: List<String>): String {
        val targetArea = parseInput(input)

        val total = findTriangleNumbersWidth(targetArea)
            .map { it.second.flatMap { steps -> findYVelocitiesForXVelocity(it.first to steps, targetArea) } }
            .map { it.distinct().size }
            .fold(0) {acc,v -> acc + v}

        return total.plus(findTriangleNumbersWidthTrivial(targetArea)).toString()
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

    private fun findTriangleNumbersWidth(target: TargetArea): List<Pair<Int, List<Int>>> {
        return (1..target.maxX/2+1)
            .filter { xVelocityHitsArea(it, target) }
            .map { it to xVelocityStepsToHitArea(it, target) }
    }

    private fun findTriangleNumbersWidthTrivial(target: TargetArea): Int {
        return (target.maxX - target.minX).inc().times((target.getAbsMinY()-target.getAbsMaxY()).inc())
    }

    private fun findTriangleNumbersHeight(target: TargetArea): List<Pair<Int, Int>> {
        tailrec fun findTriangleNumbersHeight(target: TargetArea, velocity: YVelocity, acc: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
            if (velocity >= target.getAbsMinY()) {
                return acc
            }

            val maxHeight = findMaxHeight(velocity)
            val newAccList = if (yVelocityHitsArea(velocity, target)) acc.plus(maxHeight to velocity) else acc
            return findTriangleNumbersHeight(target, velocity.inc(), newAccList)
        }

        return findTriangleNumbersHeight(target, 0, listOf())
    }

    private fun findMaxHeight(yVelocity: YVelocity): Int {
        return (yVelocity * (yVelocity + 1).toDouble().div(2)).toInt()
    }

    private fun xVelocityHitsArea(initialVelocity: XVelocity, target: TargetArea): Boolean {
        tailrec fun xVelocityHitsArea(velocity: XVelocity, target: TargetArea, sum: Int): Boolean {
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

    private fun xVelocityStepsToHitArea(initialVelocity: XVelocity, target: TargetArea): List<Int> {
        fun xVelocityStepsToHitArea(velocity: XVelocity, target: TargetArea, sum: Int, steps: Int, accSteps: List<Int>): List<Int> {
            if ((sum >= target.minX && sum <= target.maxX) && velocity >= 0) {
                return xVelocityStepsToHitArea(velocity.dec(), target, sum.plus(velocity), steps.inc(), accSteps.plus(steps))
            }

            if (velocity < 0) {
                return accSteps
            }

            return xVelocityStepsToHitArea(velocity.dec(), target, sum.plus(velocity), steps.inc(), accSteps)
        }

        return xVelocityStepsToHitArea(initialVelocity, target, 0, 0, listOf())
    }

    private fun yVelocityHitsArea(initialVelocity: YVelocity, target: TargetArea): Boolean {
        tailrec fun yVelocityHitsArea(velocity: YVelocity, target: TargetArea, sum: Int): Boolean {
            if (sum > target.getAbsMinY()) {
                return false
            }

            if (sum in target.getAbsMaxY()..target.getAbsMinY()) {
                return true
            }

            return yVelocityHitsArea(velocity.inc(), target, sum.plus(velocity))
        }

        return yVelocityHitsArea(initialVelocity, target, initialVelocity.inc())
    }

    private fun yVelocityStepsToHitArea(initialVelocity: YVelocity, target: TargetArea, steps: Int, ignoreStepsLimit: Boolean): Boolean {
        log ("yVelocity: $initialVelocity steps: $steps")
        tailrec fun yVelocityStepsToHitArea(velocity: YVelocity, target: TargetArea, steps: Int, sum: Int, ignoreStepsLimit: Boolean): Boolean {
            log ("yVelocity: $velocity steps: $steps sum: $sum ignoreStepsLimit: $ignoreStepsLimit")
            if (target.isInYArea(sum) && ignoreStepsLimit(ignoreStepsLimit, steps)) {
                return true
            }
            if (sum < target.minY || (steps <= 1 && !ignoreStepsLimit)) {
                return false
            }

            return yVelocityStepsToHitArea(velocity.dec(), target, steps.dec(), sum.plus(velocity), ignoreStepsLimit)
        }

        return yVelocityStepsToHitArea(initialVelocity.dec(), target, steps, initialVelocity, ignoreStepsLimit)
    }

    private fun ignoreStepsLimit(ignoreStepsLimit: Boolean, steps: Int): Boolean {
        return if (ignoreStepsLimit && steps <= 1) true
        else if(ignoreStepsLimit && steps > 1) false
        else !ignoreStepsLimit && steps <= 1
    }
}