package puzzles.week3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day21Test {
    val puzzle = Day21()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("739785")
    }

    @Test
    fun solveDemoPart2() {
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("798147")
    }

    @Test
    fun solvePart2() {
    }
}