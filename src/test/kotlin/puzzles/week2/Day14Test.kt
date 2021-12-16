package puzzles.week2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day14Test {
    val puzzle = Day14()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("1588")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("2188189693529")
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("2509")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("2827627697643")
    }
}