package puzzles.week1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import puzzles.Puzzle

internal class Day1Test {
    val puzzle: Puzzle = Day1()

    @Test
    fun `Check Day 1, part 1`() {
        assertThat(puzzle.solvePart1()).isEqualTo("1602")
    }

    @Test
    fun `Check Day 1, part 2`() {
        assertThat(puzzle.solvePart2()).isEqualTo("1633")
    }
}