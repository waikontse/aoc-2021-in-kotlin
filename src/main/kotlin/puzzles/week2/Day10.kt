package puzzles.week2

import puzzles.Puzzle

class Day10: Puzzle(10) {
    override fun solveDemoPart1(): String {
        return solve(inputDemo)
    }

    override fun solveDemoPart2(): String {
        return solve2(inputDemo)
    }

    override fun solvePart1(): String {
        return solve(input)
    }

    override fun solvePart2(): String {
        return  solve2(input)
    }

    private fun solve(input: List<String>): String {
        return input.filter { isIncorrectLine(it) }
            .map { findIncorrectChar(it) }
            .map { mapCharToPoint(it.second) }
            .sumOf { it }
            .toString()
    }

    private fun solve2(input: List<String>): String {
        return input.filter { !isIncorrectLine(it) }
            .map { findIncorrectChar(it) }
            .map { it.first.reversed() }
            .map { it.map { c -> mapOpeningCharToClosingChar(c) }  }
            .map { it.map { c -> mapClosingCharToPoint(c) } }
            .map { calculateAutocompleteScore(it) }
            .sorted()
            .let { it[it.size/2] }.toString()
    }

    private fun isIncorrectLine(lineInput: String): Boolean {
        tailrec fun isIncorrectLine(openingChars: ArrayDeque<Char>, inputChars: List<Char>): Boolean {
            if (inputChars.isEmpty()) { return false }
            if (openingChars.isEmpty() && isClosingChar(inputChars.first())) { return true }

            if (isOpeningChar(inputChars.first())) {
                openingChars.add(inputChars.first())
            } else if (isClosingChar(inputChars.first())) {
                if (isClosingCharOf(openingChars.last(), inputChars.first())) {
                    openingChars.removeLast()
                } else {
                    return true
                }
            } else if (!isClosingCharOf(openingChars.last(), inputChars.first())) {
                return true
            }

            return isIncorrectLine(openingChars, inputChars.drop(1))
        }

        return isIncorrectLine(ArrayDeque(), lineInput.toCharArray().toList())
    }

    private fun findIncorrectChar(input: String): Pair<List<Char>, Char> {
        val openingChars = ArrayDeque<Char>()
        var inputChars = input.toCharArray().toList()
        var incorrectChar = 'x'

        while (inputChars.isNotEmpty()) {
            if (isOpeningChar(inputChars.first())) {
                openingChars.add(inputChars.first())
            } else if (isClosingChar(inputChars.first()) && isClosingCharOf(openingChars.last(), inputChars.first())) {
                openingChars.removeLast()
            } else {
                incorrectChar = inputChars.first()
                inputChars = listOf()
            }

            inputChars = inputChars.drop(1)
        }

        return Pair(openingChars.toList(), incorrectChar)
    }

    private fun isOpeningChar(openingChar: Char): Boolean
            = setOf('(', '[', '{', '<').contains(openingChar)

    private fun isClosingChar(closingChar: Char): Boolean
            = !isOpeningChar(closingChar)

    private fun isClosingCharOf(openingChar: Char, closingChar: Char): Boolean
            = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')[openingChar] == closingChar

    private fun mapCharToPoint(closingChar: Char): Int {
        if (isOpeningChar(closingChar)) throw RuntimeException("Unexpected closing char '$closingChar'")

        return mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)[closingChar]!!
    }

    private fun mapOpeningCharToClosingChar(openingChar: Char): Char
        = mapOf('(' to ')', '{' to '}', '<' to '>', '[' to ']')[openingChar]!!

    private fun mapClosingCharToPoint(closingChar: Char): Int {
        return mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)[closingChar]!!
    }

    private fun calculateAutocompleteScore(points: List<Int>): Long {
        return points.fold(0) { acc, s -> acc*5+s }
    }
}