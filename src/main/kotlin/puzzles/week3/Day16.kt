package puzzles.week3

import puzzles.Puzzle

class Day16: Puzzle(16) {
    override fun solveDemoPart1(): String {
        val binaryString = convertToBinary(inputDemo.first())
        val version = parseVersion(binaryString)
        val type = parseType(binaryString.drop(3))

        println ("$version $type")

        if(type == 4) {
            println(parseLiteral(binaryString.drop(6)))
        }

        return convertToBinary(inputDemo.first())
    }

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun convertToBinary(input: String): String {
        val mapHexToBin = mapOf(
            '0' to "0000", '1' to "0001", '2' to "0010", '3' to "0011", '4' to "0100",
            '5' to "0101", '6' to "0110", '7' to "0111", '8' to "1000", '9' to "1001",
            'A' to "1010", 'B' to "1011", 'C' to "1100", 'D' to "1101", 'E' to "1110", 'F' to "1111")

        tailrec fun convertToBinary(input: List<Char>, binaryRepresentation: String, conversionMap: Map<Char, String>): String {
            if (input.isEmpty()) {
                return binaryRepresentation
            }

            return convertToBinary(input.drop(1), binaryRepresentation+conversionMap[input.first()]!!, conversionMap)
        }

        return convertToBinary(input.toCharArray().toList(), "", mapHexToBin)
    }

    private fun parseVersion(input: String): Int {
        return input.substring((0..2)).toInt(2)
    }

    private fun parseType(input: String): Int {
        return input.substring((0..2)).toInt(2)
    }

    private fun parseLiteral(input: String): Pair<Int, Int> {
        tailrec fun parseLiteral(input: String, decoded: String, sectionCount: Int): Pair<Int, Int> {
            if (input.startsWith('0')) {
                val section = input.substring(0..4).drop(1)
                return Pair(decoded.plus(section).toInt(2), sectionCount.inc()*5)
            }

            val section = input.substring(0..4).drop(1)
            return parseLiteral(input.drop(5), decoded.plus(section), sectionCount.inc())
        }

        return parseLiteral(input, "", 0)
    }
}