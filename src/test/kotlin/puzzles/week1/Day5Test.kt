package puzzles.week1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day5Test {
    private val puzzle = Day5()

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("4873")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("19472")
    }

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("5")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("12")
    }
}