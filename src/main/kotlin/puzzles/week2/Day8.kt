package puzzles.week2

import puzzles.Puzzle

class Day8: Puzzle(8) {
    val distanceToNumer: Map<Int, List<Pair<Int, Int>>> = mapOf(
        1 to listOf(4 to 2, 3 to 3, 4 to 5, 5 to 6, 4 to 9, 4 to 0),
        4 to listOf(3 to 2, 2 to 3, 2 to 5, 3 to 6, 2 to 9, 3 to 0),
        7 to listOf(3 to 2, 2 to 3, 3 to 5, 4 to 6, 3 to 9, 3 to 0),
        8 to listOf(2 to 2, 2 to 3, 2 to 5, 2 to 6, 1 to 9, 1 to 0)
    )

    override fun solveDemoPart1(): String {
        return solve(inputDemo).toString()
    }

    override fun solveDemoPart2(): String {
        return inputDemo.map { solve3(it) }
            .map { it.toInt() }
            .sum().toString()
    }

    private fun solve3(input: String): String {
        val fullListRight = input.split(" | ")[1].split(" ")
        val fullListLeft = input.split(" | ")[0].split(" ")
        val uniqueLeft = fullListLeft.let { filterUnique(it) }

        return fullListRight.map {determineNumber(it, uniqueLeft)}.fold("") {acc, i -> acc+i }
    }

    private fun filterUnique(list: List<String>): List<String> {
        return list.filter { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }
    }

    private fun determineNumber(unknown: String, unique: List<String>): Int {
        return if (stringToNumber(unknown) != -1) {
            stringToNumber(unknown)
        } else determineUnknown(unknown, unique)
    }

    private fun determineUnknown(unknown: String, unique: List<String>): Int {
        val listCandidates = unique.map { stringToNumber(it) to getDistance(it, unknown) }
            .map { distanceToNumer[it.first]?.filter { p -> p.first == it.second } }
            .map { it!!.map { pair -> pair.second } }
            .flatten().groupingBy { it }.eachCount().maxByOrNull { it.value }?.key

        return listCandidates!!
    }

    override fun solvePart1(): String {
        return solve(input).toString()
    }

    override fun solvePart2(): String {
        return input.map { solve3(it) }.sumOf { it.toInt() }.toString()
    }

    private fun solve(input: List<String>): Int {
        return input.map { it.split(" | ") }
            .map { it[1] }
            .flatMap { it.split(" ") }
            .map { it.length }.count { it == 2 || it == 4 || it == 3 || it == 7 }
    }

    private fun stringToNumber(length: String): Int {
        return when(length.length) {
            2 -> 1
            4 -> 4
            3 -> 7
            7 -> 8
            else -> -1
        }
    }

    private fun getDistance(unique: String, to: String): Int {
        val toSet = to.toSet()
        val uniqueSet = unique.toSet()
        val distanceD =
            if (uniqueSet.size < toSet.size) toSet.minus(uniqueSet).size
            else uniqueSet.minus(toSet).size

        return distanceD
    }
}