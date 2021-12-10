package puzzles.week2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day10Test {
    val puzzle = Day10()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("26397")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("288957")
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("339411")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("2289754624")
    }
}