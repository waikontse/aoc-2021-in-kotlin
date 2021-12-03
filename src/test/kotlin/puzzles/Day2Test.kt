package puzzles

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day2Test {
    val puzzle: Puzzle = Day2()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("150")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("900")
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("1989265")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("2089174012")
    }
}