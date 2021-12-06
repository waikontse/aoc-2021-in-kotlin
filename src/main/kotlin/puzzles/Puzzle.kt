package puzzles

abstract class Puzzle(val day: Int) {
    abstract fun solveDemoPart1() : String
    abstract fun solveDemoPart2() : String
    abstract fun solvePart1() : String
    abstract fun solvePart2() : String
}