package puzzles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day3Test {
    val puzzle: Puzzle = Day3()
    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("749376")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("2372923")
    }

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("198")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("230")
    }
}