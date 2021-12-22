package puzzles.week3

import puzzles.Puzzle

class Day21: Puzzle(21) {
    override fun solveDemoPart1(): String {
//        (1..3).map { rollDiceTurn(it, 100) }
//
//        parseStartingPositions(inputDemo).forEach { println(it) }
//        println (rollDiceTurn(993%481, 100))
//        println (rollDiceTurn(34%481, 100))
//        println (rollDiceTurn(67%481, 100))
//        println (rollDiceTurn(100%481, 100))

//        (1..1000)
//            .filterIndexed { i, v ->  993 % v == 31}
//            .forEach(::println)


        return solve(inputDemo)
    }

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        return solve(input)
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun solve(input: List<String>): String {
        val result = rollDie(parseStartingPositions(input), 100)
        //println ("result $result ${result[2]-1 * 3}")

        return result.subList (0, 2)
            .filter { it < 1000 }
            .map { it * result[2].dec() * 3 }
            .first()
            .toString()
    }

    private fun rollDie(startPositions: List<Int>, dieSides: Int): List<Int> {
        tailrec fun rollDie(currentPositions: List<Int>, sum1: Int, sum2: Int, dieSides: Int, turn: Int): List<Int> {
            if (sum1 >= 1000 || sum2 >= 1000) {
                return listOf(sum1, sum2, turn)
            }

            // calculate the steps
            // update the position for player
            // update the sum for the player
            val playerToWalk = if (turn % 2 == 1) 0 else 1
            val steps = rollDiceTurn(turn, dieSides)
            val positionOnBoard = walkOnBoard(currentPositions[playerToWalk], steps, 10)
            val newPositions = updatePosition(currentPositions, playerToWalk, positionOnBoard)
            val newSum1 = if (playerToWalk == 0) sum1.plus(positionOnBoard) else sum1
            val newSum2 = if (playerToWalk == 1) sum2.plus(positionOnBoard) else sum2

            return rollDie(newPositions, newSum1, newSum2, dieSides, turn.inc())
        }


        return rollDie(startPositions, 0, 0, dieSides, 1)
    }

    private fun walkOnBoard(currentPosition: Int, steps: Int, boardSize: Int): Int {
        return getNormalizedBoardPosition(currentPosition+steps, boardSize)
    }

    private fun getNormalizedBoardPosition(currentPosition: Int, boardSize: Int): Int
            = if (currentPosition % boardSize == 0) boardSize else currentPosition % boardSize

    private fun parseStartingPositions(input: List<String>): List<Int> {
        return input.map { it.split(": ") }
            .map { it[1].toInt() }
    }

    private fun updatePosition(currentPositions: List<Int>, positionToChange: Int, value: Int): List<Int>
        = currentPositions.mapIndexed { index, i -> if (index == positionToChange) value else i }

    private fun rollDiceTurn(turn: Int, sided: Int): Int {
        val normalizedTurn = getNormalizedTurn(turn)
        return getNormalizedDiceValue((normalizedTurn*3), sided) +
                getNormalizedDiceValue((normalizedTurn*3)-1, sided) +
                getNormalizedDiceValue((normalizedTurn*3)-2, sided)
    }

    private fun getNormalizedTurn(turn: Int): Int
            = turn % 481

    private fun getNormalizedDiceValue(value: Int, sided: Int):Int
            = if (value % sided == 0) 100 else value % sided

    val points0 = (1..99).chunked(3).map { it.sum() }
    val points1 = (listOf(100) + (1..98)).chunked(3).map { it.sum() }
    val points2 = (listOf(99, 100) + (1..97)).chunked(3).map { it.sum() }
    val points3 = (listOf(98, 99, 100) + (1..96)).chunked(3).map { it.sum() }
    //val points = listOf(points0, points1, points2, points3)




}