package puzzles.week2

import puzzles.Puzzle

class Day13: Puzzle(13) {
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
        return solve2(input)
    }

    private fun solve(input:List<String>): String {
        val splitIndex = input.indices.first { input[it].isBlank() }
        val foldInstructions = input.subList(splitIndex+1, input.size)
        val paper = input.subList(0, splitIndex).map { toPoint(it) }.toSet()


        val foldInstructions2 = foldInstructions.map { toFoldInstruction(it) }

        return fold(paper, listOf(foldInstructions2.first()))
            .size
            .toString()
    }

    private fun solve2(input:List<String>): String {
        val splitIndex = input.indices.first { input[it].isBlank() }
        val foldInstructions = input.subList(splitIndex+1, input.size)
        val paper = input.subList(0, splitIndex).map { toPoint(it) }.toSet()

        val foldInstructions2 = foldInstructions.map { toFoldInstruction(it) }

        return drawMap(fold(paper, foldInstructions2))
    }

    private fun fold(paper: Set<Pair<Int, Int>>, foldInstructions: List<Pair<String, Int>>): Set<Pair<Int, Int>> {
        tailrec fun fold2(paper: Set<Pair<Int, Int>>, foldInstructions: List<Pair<String, Int>>): Set<Pair<Int, Int>> {
            if (foldInstructions.isEmpty()) {
                return paper
            }

            return fold2(foldInstruction3(foldInstructions.first(), paper), foldInstructions.drop(1))
        }

        return fold2(paper, foldInstructions)
    }

    private fun foldInstruction3(foldInstruction: Pair<String, Int>, paper: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        return if(foldInstruction.first == "x") foldXAxis(paper, foldInstruction.second)
        else foldYAxis(paper, foldInstruction.second)
    }

    private fun foldXAxis(paper: Set<Pair<Int, Int>>, foldAlongX: Int): Set<Pair<Int, Int>> {
        val keepSet = paper.filter { it.first < foldAlongX }.toSet()
        val newSet = paper.filter { it.first > foldAlongX }
            .map { it.copy(first = foldAlongX - (it.first - foldAlongX)) }

        return keepSet.plus(newSet)
    }

    private fun foldYAxis(paper: Set<Pair<Int, Int>>, foldAlongY: Int): Set<Pair<Int, Int>> {
        val keepSet = paper.filter { it.second < foldAlongY }.toSet()
        val newSet = paper.filter { it.second > foldAlongY }
            .map { it.copy(second = foldAlongY - (it.second - foldAlongY)) }

        return keepSet.plus(newSet)
    }

    private fun foldAlong(paper: Set<Pair<Int, Int>>,
                          foldFunc: (Set<Pair<Int, Int>>) -> Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        return setOf()
    }

    private fun toPoint(point: String): Pair<Int, Int> {
        return point.split(",").map { it.toInt() }.let { it[0] to it[1] }
    }

    private fun toFoldInstruction(instruction: String): Pair<String, Int> {
        return instruction.split(" ")[2].split("=").let { it[0] to it[1].toInt() }
    }

    private fun drawMap(points: Set<Pair<Int, Int>>): String {
        val width = points.maxOf { it.first } + 1
        val height = points.maxOf { it.second } + 1
        val mutableList = MutableList(width*height) { " " }

        points.forEach { mutableList[it.first + width* it.second] = "#" }

        mutableList.chunked(width).onEach(::println)

        return "hello"
    }
}