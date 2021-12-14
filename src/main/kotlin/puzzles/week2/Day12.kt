package puzzles.week2

import puzzles.Puzzle

class Day12: Puzzle(12) {
    override fun solveDemoPart1(): String = solve(inputDemo, this::cannotContinuePart1, this::getNextLocationPart1).toString()

    override fun solveDemoPart2(): String = solve(inputDemo, this::cannotContinuePart2, this::getNextLocationPart2).toString()

    override fun solvePart1(): String  = solve(input, this::cannotContinuePart1, this::getNextLocationPart1).toString()

    override fun solvePart2(): String = solve(input, this::cannotContinuePart2, this::getNextLocationPart2).toString()

    private fun solve(input: List<String>,
                      cannotContinue: (String, List<String>) -> Boolean,
                      nextLocation: (String, String, List<String>) -> List<String>): Int {
        return input.map { it.split("-") }
            .map { it[0] to it[1] }
            .let { buildGraph(it) }
            .let { generateMap(it, cannotContinue, nextLocation) }
            .size
    }

    private fun buildGraph(pairs: List<Pair<String, String>>): Map<String, List<String>> {
        val forwardGraph = pairs.groupBy( { k -> k.first }, { v -> v.second })
        val reverseGraph = pairs.groupBy( { k -> k.second}, { v -> v.first })

        return (forwardGraph.asSequence() + reverseGraph.asSequence())
            .groupBy( {it.key}, {it.value})
            .mapValues { (_, values) -> values.flatten() }
    }

    private fun generateMap(graph: Map<String, List<String>>,
                            predicate: (String, List<String>) -> Boolean,
                            function: (String, String, List<String>) -> List<String>): List<String> {
        return generateMap2(graph, "start", listOf("start"), predicate, function)
    }

    private fun generateMap2(graph: Map<String, List<String>>,
                             currentLocation: String,
                             currentRoute: List<String>,
                             cannotContinue: (String, List<String>) -> Boolean,
                             function: (String, String, List<String>) -> List<String>): List<String> {
        if (isCaveExit(currentLocation)) {
            return listOf(currentRoute.joinToString(","))
        } else if (cannotContinue(currentLocation, currentRoute)) {
            return emptyList()
        }

        return graph[currentLocation]?.flatMap {
            val newLocation = function(currentLocation, it, currentRoute)
            generateMap2(graph, it, currentRoute.plus(newLocation), cannotContinue, function)
        }!!
    }

    private fun isCaveExit(point: String): Boolean
            = point.equals("end", true)

    private fun cannotContinuePart1(currentLocation: String, currentRoute: List<String>): Boolean {
        return isSmallCave(currentLocation) && currentRoute.count { it == currentLocation } > 1
    }

    private fun cannotContinuePart2(currentLocation: String, currentRoute: List<String>): Boolean {
        return if (currentLocation == "start" && currentRoute.size > 2) true
        else isSmallCave(currentLocation) && currentRoute.count { it == currentLocation } > 1 && currentRoute.contains("double")
    }

    private fun getNextLocationPart1(currentLocation: String, nextLocation: String, currentRoute: List<String>): List<String> {
        return listOf(nextLocation)
    }

    private fun getNextLocationPart2(currentLocation: String, nextLocation: String, currentRoute: List<String>): List<String> {
        return if(isSmallCave(currentLocation) && currentRoute.count { it == currentLocation } > 1  && !currentRoute.contains("double")) {
            listOf("double", nextLocation)
        } else {
            listOf(nextLocation)
        }
    }

    private fun isSmallCave(location: String): Boolean
            = location.toCharArray().count { it.isLowerCase() } == location.length
}