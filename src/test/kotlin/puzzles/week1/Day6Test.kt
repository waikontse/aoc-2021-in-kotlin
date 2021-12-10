package puzzles.week1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day6Test {
    val puzzle = Day6()

    @Test
    fun solveDemoPart1() {
        assertThat(puzzle.solveDemoPart1()).isEqualTo("5934")
    }

    @Test
    fun solveDemoPart2() {
        assertThat(puzzle.solveDemoPart2()).isEqualTo("26984457539")
    }

    @Test
    fun solvePart1() {
        assertThat(puzzle.solvePart1()).isEqualTo("391888")
    }

    @Test
    fun solvePart2() {
        assertThat(puzzle.solvePart2()).isEqualTo("1754597645339")
    }
}