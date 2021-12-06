package util

import puzzles.Puzzle

class AnswerPrinter {
    val bmRunner = BenchmarkRunner()

    fun printPuzzleAnswer(puzzle: Puzzle) {
        val timingResult = bmRunner.run(puzzle)

        println("Day [${puzzle.day}][A]\t answer: [${puzzle.solvePart1()}]\t took avg: [${timingResult[0]} ns]")
        println("Day [${puzzle.day}][B]\t answer: [${puzzle.solvePart2()}]\t took avg: [${timingResult[1]} ns]")
    }
}