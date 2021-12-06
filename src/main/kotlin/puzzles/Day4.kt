package puzzles

import util.ReaderUtil

class Day4: Puzzle(4) {
    val inputDemo = ReaderUtil.readResourceAsStrings("input4demo.txt")
    val input = ReaderUtil.readResourceAsStrings("input4.txt")

    override fun solveDemoPart1(): String {
        val sequence = inputDemo.first().split(",").map { it.toInt() }
        val raffles: List<MutableList<Pair<Int, Boolean>>> = inputDemo.drop(1)
            .chunked(6) { parseRaffle(it)}

        var hasWon = false
        var i = 0
        while (!hasWon) {
            raffles.forEach { raffle -> markRaffle(raffle, sequence[i]) }
            val hasWons = raffles.map { hasWon(it) }
            println(hasWons)
            hasWon =  hasWons.contains(true)
            i++
        }

        return countPoint(raffles.filter { hasWon(it) }.first(), sequence[i-1]).toString()
    }

    override fun solveDemoPart2(): String {
        val sequence = inputDemo.first().split(",").map { it.toInt() }
        val raffles: List<MutableList<Pair<Int, Boolean>>> = inputDemo.drop(1)
            .chunked(6) { parseRaffle(it)}

        var allHasWon = false
        var i = 0
        while (!allHasWon) {
            raffles.forEach { raffle -> markRaffle(raffle, sequence[i]) }
            val hasWons = raffles.map { hasWon(it) }
            allHasWon = hasWons.count { it == false } == 0
            i++
        }

        val raffles2: List<MutableList<Pair<Int, Boolean>>> = inputDemo.drop(1)
            .chunked(6) {parseRaffle(it) }
        sequence.take(i-1)
            .forEach { num -> raffles2.forEach { markRaffle(it, num) }}

        // find last raffle
        val lastRaffle = raffles2.filter { !hasWon(it) }.first()
        markRaffle(lastRaffle, sequence[i-1])

        return countPoint(lastRaffle, sequence[i-1]).toString()
    }

    override fun solvePart1(): String {
        val sequence = input.first().split(",").map { it.toInt() }
        val raffles: List<MutableList<Pair<Int, Boolean>>> = input.drop(1)
            .chunked(6) { parseRaffle(it)}

        var hasWon = false
        var i = 0
        while (!hasWon) {
            raffles.forEach { raffle -> markRaffle(raffle, sequence[i]) }
            hasWon = raffles.map { hasWon(it) }.contains(true)
            i++
        }

        return countPoint(raffles.filter { hasWon(it) }.first(), sequence[i-1]).toString()
    }

    override fun solvePart2(): String {
        val sequence = input.first().split(",").map { it.toInt() }
        val raffles: List<MutableList<Pair<Int, Boolean>>> = input.drop(1)
            .chunked(6) { parseRaffle(it)}

        var allHasWon = false
        var i = 0
        while (!allHasWon) {
            raffles.forEach { raffle -> markRaffle(raffle, sequence[i]) }
            val hasWons = raffles.map { hasWon(it) }
            allHasWon = hasWons.count { it == false } == 0
            i++
        }

        val raffles2: List<MutableList<Pair<Int, Boolean>>> = input.drop(1)
            .chunked(6) {parseRaffle(it) }
        sequence.take(i-1)
            .forEach { num -> raffles2.forEach { markRaffle(it, num) }}

        // find last raffle
        val lastRaffle = raffles2.first { !hasWon(it) }
        markRaffle(lastRaffle, sequence[i-1])

        return countPoint(lastRaffle, sequence[i-1]).toString()
    }

    private fun parseRaffle(raffle: List<String>): MutableList<Pair<Int, Boolean>> {
        return raffle.joinToString(" ").split("\\s+".toRegex()).filterNot { it.isBlank() }
            .map { Pair(it.toInt(), false) }
            .toMutableList()
    }

    private fun markRaffle(raffle: MutableList<Pair<Int, Boolean>>, number: Int) {
        return raffle.forEachIndexed { i, square -> if(square.first == number) raffle[i] = square.copy(second = true) }
    }

    private fun hasWon(raffle: List<Pair<Int, Boolean>>): Boolean
            = checkRow(raffle, 0, 5) or checkColumn(raffle, 0, 5)

    private fun checkRow(raffle: List<Pair<Int, Boolean>>, row: Int, maxRow: Int): Boolean {
        if (row >= maxRow) { return false }

        return raffle.filterIndexed { i, _ -> i >= row * maxRow && i < (row + 1) * maxRow }.count { it.second } == maxRow
                || checkRow(raffle, row+1, maxRow)
    }

    private fun checkColumn(raffle: List<Pair<Int, Boolean>>, column: Int, maxColumn: Int): Boolean {
        if (column >= maxColumn) { return false }

        return raffle.filterIndexed { i, _ -> i.mod(maxColumn) == column }.count { it.second } == maxColumn
                || checkColumn(raffle, column + 1, maxColumn)
    }

    private fun countPoint(raffle: List<Pair<Int, Boolean>>, lastNumber: Int): Int
            = raffle.filter { !it.second }.sumOf { it.first } * lastNumber
}