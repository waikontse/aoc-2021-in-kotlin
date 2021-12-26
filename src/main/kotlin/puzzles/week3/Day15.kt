package puzzles.week3

import puzzles.Puzzle
import java.util.*

typealias Node = Int
data class Edge(val endPoint: Node, val cost: Int)
data class GraphSize(val width: Int, val height: Int)

class Graph(size: GraphSize) {
    private val graph: MutableMap<Node, MutableList<Edge>> = mutableMapOf()

    init {
        (0 until (size.height*size.width)).onEach { graph[it] = mutableListOf() }
    }

    fun addEdges(node: Node, edges:List<Edge>) {
        graph[node]?.addAll(edges)
    }

    fun getEdges(node: Node): List<Edge> {
        return graph[node]?.toList() ?: listOf()
    }

    fun vertices(): Set<Node>
            = graph.keys

    fun verticesCount(): Int
            = graph.keys.size

    fun print()
            = graph.keys.onEach { println("From: $it with edges: ${getEdges(it)}") }
}

class Day15: Puzzle(15) {
    override fun solveDemoPart1(): String {
        return solve(inputDemo, 1)
    }

    override fun solveDemoPart2(): String {
        return solve(inputDemo, 5)
    }

    override fun solvePart1(): String {
        return solve(input, 1)
    }

    override fun solvePart2(): String {
        return solve(input, 5)
    }

    private fun solve(input: List<String>, expand: Int): String {
        val dijkstraResult = dijkstra(0, parseGraph(input, expand))

        return dijkstraResult.first.last().toString()
    }

    private fun dijkstra(firstNode: Node, graph: Graph): Pair<IntArray, Map<Node, Edge>> {
        val distances = IntArray(graph.verticesCount()) { if (it == 0) 0 else Int.MAX_VALUE }
        val visited = BooleanArray(graph.verticesCount()) { it == 0 }
        val pathMap = mutableMapOf<Node, Edge>()
        val pq = PriorityQueue<Edge> { left, right -> left.cost - right.cost }

        pq.offer(Edge(firstNode, 0))

        while(pq.isNotEmpty()) {
            val u = pq.poll().endPoint
            val distU = distances[u]

            for (edge in graph.getEdges(u)) {
                val distV = distances[edge.endPoint]
                val pathWeight = edge.cost + distU

                if (!visited[edge.endPoint] || (distV > pathWeight)) {
                    visited[edge.endPoint] = true
                    distances[edge.endPoint] = pathWeight
                    pathMap[edge.endPoint] = edge
                    pq.offer(Edge(edge.endPoint, pathWeight))
                }
            }
        }

        return Pair(distances, pathMap)
    }

    private fun expandGraph(rawGraph: List<List<Int>>, expand: Int): List<List<Int>> {
        return rawGraph.map { expandAndIncrementLine(it, expand) }
            .let { expandAndIncrementGraph(it, expand) }
    }

    private fun expandAndIncrementLine(list: List<Int>, expand: Int): List<Int> {
        tailrec fun expandAndIncrementLine(list: List<Int>, expand: Int, acc: List<Int>): List<Int> {
            if (expand <= 1) {
                return acc
            }

            val incrementedList = incrementLine(list)
            return expandAndIncrementLine(incrementedList, expand.dec(), acc.plus(incrementedList))
        }

        return expandAndIncrementLine(list, expand, list)
    }

    private fun incrementLine(list: List<Int>): List<Int> {
        return list.map { it.inc() }.map { if (it < 10) it else 1 }
    }

    private fun expandAndIncrementGraph(graph: List<List<Int>>, expand: Int): List<List<Int>> {
        tailrec fun expandAndIncrementGraph(graph: List<List<Int>>, expand: Int, acc: List<List<Int>>): List<List<Int>> {
            if (expand <= 1) {
                return acc
            }

            val incrementedGraph = graph.map { incrementLine(it) }
            return expandAndIncrementGraph(incrementedGraph, expand.dec(), acc.plus(incrementedGraph))
        }

        return expandAndIncrementGraph(graph, expand, graph)
    }

    private fun parseGraph(input: List<String>, expand: Int): Graph {
        val width = input.first().length * expand
        val heigth = input.size * expand
        val size = GraphSize(width, heigth)
        val rawGraph = input.map { it.toCharArray() }
            .map { it.map { c -> c.digitToInt() } }
            .map { it }
            .let { expandGraph(it, expand) }
            .flatten()

        val graph = Graph(size)
        graph.vertices()
            .forEach { graph.addEdges(it, parseEdges(it, rawGraph, size)) }

        return graph
    }

    fun parseEdges(from: Node, graph: List<Node>, graphSize: GraphSize): List<Edge> {
        val coor = from.toCoor(graphSize)
        return listOf(getEdge(coor.getTop(), graph, graphSize),
            getEdge(coor.getBottom(), graph, graphSize),
            getEdge(coor.getLeft(), graph, graphSize),
            getEdge(coor.getRight(), graph, graphSize))
            .flatten()
    }

    private fun getEdge(coor: Pair<Int, Int>, graph: List<Node>, size: GraphSize): List<Edge> {
        return if (!coor.isOutOfBounds(size)) listOf(  Edge(coor.toIndex(size), graph[coor.toIndex(size)]) )
        else listOf()
    }

    private fun Node.toCoor(size: GraphSize): Pair<Int, Int> {
        return Pair(this%size.width, this/size.height)
    }

    private  fun Pair<Int, Int>.getTop(): Pair<Int, Int>
            = this.copy(second = this.second-1)

    private  fun Pair<Int, Int>.getBottom(): Pair<Int, Int>
            = this.copy(second = this.second+1)

    private  fun Pair<Int, Int>.getLeft(): Pair<Int, Int>
            = this.copy(first = this.first-1)

    private  fun Pair<Int, Int>.getRight(): Pair<Int, Int>
            = this.copy(first = this.first+1)

    private fun Pair<Int, Int>.isOutOfBounds(graphSize: GraphSize): Boolean {
        return (this.first < 0 || this.first >= graphSize.width) ||
                (this.second < 0 || this.second >= graphSize.height)
    }

    private fun Pair<Int, Int>.toIndex(graphSize: GraphSize): Node {
        return this.first + this.second * graphSize.width
    }
}