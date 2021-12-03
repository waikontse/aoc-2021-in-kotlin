package puzzles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day3Test {
    val puzzle: Puzzle = Day3()

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo(749376)
    }

    @Test
    fun solvePart2() {
        val vals = listOf(1, 2, 3, 4, 5)
        vals.windowed(2).forEach(::println)
    }

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo(198)
    }

    @Test
    fun solveDemoPart2() {
    }
}