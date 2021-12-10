package puzzles.week2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import puzzles.week2.Day9

internal class Day9Test {
    val puzzle = Day9()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("15")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("1134")
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("570")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("899392")
    }
}