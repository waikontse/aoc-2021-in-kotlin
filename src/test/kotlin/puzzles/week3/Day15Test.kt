package puzzles.week3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day15Test {

    val puzzle = Day15()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("40")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("315")
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("720")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("3025")
    }
}