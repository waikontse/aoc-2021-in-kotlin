package puzzles.week2

import puzzles.Puzzle

class Day12: Puzzle(12) {
    override fun solveDemoPart1(): String = solve(inputDemo).toString()

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun solve(input: List<String>): Int {
        return input.map { it.split("-") }
            .map { it[0] to it[1] }
            .let { buildGraph(it) }
            .let { generateMap(it) }
            .size
    }

    private fun buildGraph(pairs: List<Pair<String, String>>): Map<String, List<String>> {
        val forwardGraph = pairs.groupBy( { k -> k.first }, { v -> v.second })
        val reverseGraph = pairs.groupBy( { k -> k.second}, { v -> v.first })
        val biGraph = (forwardGraph.asSequence() + reverseGraph.asSequence())
            .groupBy( {it.key}, {it.value})
            .mapValues { (_, values) -> values.flatten() }

        return biGraph
    }

    private fun generateMap(graph: Map<String, List<String>>): List<String> {
        return generateMap2(graph, "start", listOf(), listOf())
    }

    private fun generateMap2(graph: Map<String, List<String>>,
                             currentLocation: String,
                             currentRoute: List<String>,
                             possibleRoutes: List<String>): List<String> {
        // Stop conditions
        // We reached the end? Add the route
        // We reached the small cave again?
        if (isCaveExit(currentLocation)) {
            //return possibleRoutes.toMutableList().add(currentRoute.toMutableList().
            return possibleRoutes
        }
        return listOf()
    }

    private fun isCaveExit(point: String): Boolean
            = point.equals("end", true)
}