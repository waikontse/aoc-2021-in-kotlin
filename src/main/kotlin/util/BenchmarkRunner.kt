package util

import puzzles.Puzzle
import java.time.Duration
import java.time.Instant

class BenchmarkRunner {
    fun run(puzzle: Puzzle): List<Long> {
        val timingPart1 = runMicrobench(puzzle::solvePart1)
        val timingPart2 = runMicrobench(puzzle::solvePart2)

        return listOf(timingPart1, timingPart2)
    }

    fun runMicrobench(runnable: Runnable): Long {
        val runCount = 1000L
        val start = Instant.now()
        for(i in 1..runCount) {
            runnable.run()
        }
        val stop = Instant.now()

        return Duration.between(start, stop).dividedBy(runCount).toNanos()
    }
}