package puzzles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day4Test {
    val puzzle = Day4()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo(4512)
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo(1924)
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo(65325)
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo(4624)
    }
}