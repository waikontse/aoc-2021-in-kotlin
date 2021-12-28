package puzzles.week3

import puzzles.Puzzle

data class Packet(val version: Int, val typeId: Int, val lengthType: Int = -1, val literal: Int, val subPackets: List<Packet>)
data class ParseInfo(val input: String, val currentPos: Int) {
    fun movePos(increment: Int): ParseInfo = this.copy(currentPos = this.currentPos + increment)
    fun charAtPos(): Char = input[currentPos]
    fun substring(length: Int): String = input.substring(currentPos until currentPos + length)
    fun charsLeft(): Int = input.length - currentPos
}

class Day16: Puzzle(16) {
    override fun solveDemoPart1(): String {
        val binaryString = convertToBinary(inputDemo.first())
        //val parseInfo = ParseInfo(binaryString, 0)
        val packet = parsePackets(binaryString)
        println("$packet")

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

    private fun parsePackets(input: String): List<Packet> {
        tailrec fun parsePackets(parseInfo: ParseInfo, parsedPackets: List<Packet>): List<Packet> {
            if (parseInfo.charsLeft() < 8) {
                return parsedPackets
            }

            val newlyParsedPacket = parsePacket(parseInfo)
            return parsePackets(newlyParsedPacket.second, parsedPackets.plus(newlyParsedPacket.first))
        }

        return parsePackets(ParseInfo(input, 0), listOf())
    }

    private fun parsePacket(parseInfo: ParseInfo): Pair<Packet, ParseInfo> {
        println ("Starting parse packet $parseInfo")
        // parseVersion
        val version = parseVersion(parseInfo)
        // parseType
        val typeId = parseType(version.second)
        // parse literal / parse operator
        val literal: Pair<Int, ParseInfo>
        val lengthType: Pair<Int, ParseInfo>
        val subPackets: List<Packet>
        if (typeId.first == 4) {
            literal = parseLiteral(typeId.second)
            lengthType = -1 to parseInfo
            subPackets = listOf()
        } else {
            lengthType = parseLengthType(typeId.second)
            literal = parseLengthLiteral(lengthType.second, lengthType.first)
            subPackets = listOf()
        }

        return Packet(version.first, typeId.first, lengthType.first, literal.first, subPackets) to literal.second
    }

    private fun parseVersion(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        return parseInfo.substring(3).toInt(2) to parseInfo.movePos(3)
    }

    private fun parseType(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        return parseInfo.substring(3).toInt(2) to parseInfo.movePos(3)
    }

    private fun parseLiteral(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        tailrec fun parseLiteral(parseInfo: ParseInfo, decoded: String): Pair<Int, ParseInfo> {
            println ("ParseLiteral 2  $parseInfo")
            if (parseInfo.charAtPos() == '0') {
                val section = parseInfo.substring(5).drop(1)
                return decoded.plus(section).toInt(2) to parseInfo.movePos(5)
            }

            val section = parseInfo.substring(5).drop(1)
            return parseLiteral(parseInfo.movePos(5), decoded.plus(section))
        }

        println ("ParseLiteral 1 $parseInfo")

        return parseLiteral(parseInfo, "")
    }

    private fun parseLengthType(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        return parseInfo.substring(1).toInt() to parseInfo.movePos(1)
    }

    private fun parseLengthLiteral(parseInfo: ParseInfo, lengthType: Int): Pair<Int, ParseInfo> {
        //println("parse length literal $parseInfo ${parseInfo.substring(15)}")
        return when(lengthType) {
            0 -> parseInfo.substring(15).toInt(2) to parseInfo.movePos(15)
            1 -> parseInfo.substring(11).toInt(2) to parseInfo.movePos(2)
            else -> throw UnsupportedOperationException("Wrong length type: $lengthType")
        }
    }

    private fun parseOperator(parseInfo: ParseInfo, lengthType: Int, lengthLiteral: Int): Pair<List<Packet>, ParseInfo> {
        when (lengthType) {

        }
    }
}