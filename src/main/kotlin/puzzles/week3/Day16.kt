package puzzles.week3

import puzzles.Puzzle

data class Packet(val version: Int, val typeId: Int, val lengthType: Int = -1, val literal: Long, val subPackets: List<Packet>)
data class ParseInfo(val input: String, val currentPos: Int) {
    fun movePos(increment: Int): ParseInfo = this.copy(currentPos = this.currentPos + increment)
    fun charAtPos(): Char = input[currentPos]
    fun substring(length: Int): String = input.substring(currentPos until currentPos + length)
    fun charsLeft(): Int = input.length - currentPos
}

class Day16: Puzzle(16) {
    override fun solveDemoPart1(): String {
        return getAllVersionNumbers(inputDemo)
    }

    override fun solveDemoPart2(): String {
        return calculate(inputDemo)
    }

    override fun solvePart1(): String {
        return getAllVersionNumbers(input)
    }

    override fun solvePart2(): String {
        return calculate(input)
    }

    private fun getAllVersionNumbers(input: List<String>): String {
        fun getAllVersionNumbers(packets: List<Packet>): Int {
            if (packets.isEmpty()) {
                return 0
            }

            return packets.sumOf { it.version } + packets.sumOf { getAllVersionNumbers(it.subPackets) }
        }

        val binaryString = convertToBinary(input.first())
        val packets = parsePackets(binaryString)

        return getAllVersionNumbers(packets).toString()
    }

    private fun calculate(input: List<String>): String {
        fun calculate(packet: Packet): Long {
            return when (packet.typeId) {
                0 -> packet.subPackets.map { calculate(it) }.fold(0) {acc, v -> acc + v}
                1 -> packet.subPackets.map { calculate(it) }.fold(1) {acc, v -> acc * v}
                2 -> packet.subPackets.minOf { calculate(it) }
                3 -> packet.subPackets.maxOf { calculate(it) }
                4 -> packet.literal
                5 -> if (calculate(packet.subPackets[0]) > calculate(packet.subPackets[1])) 1 else 0
                6 -> if (calculate(packet.subPackets[0]) < calculate(packet.subPackets[1])) 1 else 0
                7 -> if (calculate(packet.subPackets[0]) == calculate(packet.subPackets[1])) 1 else 0
                else ->  throw UnsupportedOperationException()
            }
        }

        val binaryString = convertToBinary(input.first())
        val packets = parsePackets(binaryString)

        return calculate(packets[0]).toString()
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
        val version = parseVersion(parseInfo)
        val typeId = parseType(version.second)
        val literal: Pair<Long, ParseInfo>
        val lengthType: Pair<Int, ParseInfo>
        val subPackets: Pair<List<Packet>, ParseInfo>
        if (typeId.first == 4) {
            literal = parseLiteral(typeId.second)
            lengthType = -1 to literal.second
            subPackets = listOf<Packet>() to lengthType.second
        } else {
            lengthType = parseLengthType(typeId.second)
            literal = parseLengthLiteral(lengthType.second, lengthType.first)
            subPackets = parseOperator(literal.second, lengthType.first, literal.first)
        }

        return Packet(version.first, typeId.first, lengthType.first, literal.first, subPackets.first) to subPackets.second
    }

    private fun parseVersion(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        return parseInfo.substring(3).toInt(2) to parseInfo.movePos(3)
    }

    private fun parseType(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        return parseInfo.substring(3).toInt(2) to parseInfo.movePos(3)
    }

    private fun parseLiteral(parseInfo: ParseInfo): Pair<Long, ParseInfo> {
        tailrec fun parseLiteral(parseInfo: ParseInfo, decoded: String): Pair<Long, ParseInfo> {
            if (parseInfo.charAtPos() == '0') {
                val section = parseInfo.substring(5).drop(1)
                return decoded.plus(section).toLong(2) to parseInfo.movePos(5)
            }

            val section = parseInfo.substring(5).drop(1)
            return parseLiteral(parseInfo.movePos(5), decoded.plus(section))
        }

        return parseLiteral(parseInfo, "")
    }

    private fun parseLengthType(parseInfo: ParseInfo): Pair<Int, ParseInfo> {
        return parseInfo.substring(1).toInt() to parseInfo.movePos(1)
    }

    private fun parseLengthLiteral(parseInfo: ParseInfo, lengthType: Int): Pair<Long, ParseInfo> {
        return when(lengthType) {
            0 -> parseInfo.substring(15).toLong(2) to parseInfo.movePos(15)
            1 -> parseInfo.substring(11).toLong(2) to parseInfo.movePos(11)
            else -> throw UnsupportedOperationException("Wrong length type: $lengthType")
        }
    }

    private fun parseOperator(parseInfo: ParseInfo, lengthType: Int, lengthLiteral: Long): Pair<List<Packet>, ParseInfo> {
        return when (lengthType) {
            0 -> parseOperatorOnBitLength(parseInfo, lengthLiteral)
            1 -> parseOperatorOnPacketLength(parseInfo, lengthLiteral)
            else -> throw UnsupportedOperationException("Unsupported parsing based on length type")
        }
    }

    private fun parseOperatorOnBitLength(parseInfo: ParseInfo, lengthLiteral: Long): Pair<List<Packet>, ParseInfo> {
        val packets = parsePackets(parseInfo.substring(lengthLiteral.toInt()))
        return packets to parseInfo.movePos(lengthLiteral.toInt())
    }

    private fun parseOperatorOnPacketLength(parseInfo: ParseInfo, lengthLiteral: Long): Pair<List<Packet>, ParseInfo> {
        tailrec fun parseOperatorOnPacketLength(parseInfo: ParseInfo, lengthLiteral: Long, parsedPackets: List<Packet>): Pair<List<Packet>, ParseInfo> {
            if (lengthLiteral < 1) {
                return parsedPackets to parseInfo
            }

            val newlyParsedPacket = parsePacket(parseInfo)
            return parseOperatorOnPacketLength(newlyParsedPacket.second, lengthLiteral.dec(), parsedPackets.plus(newlyParsedPacket.first))
        }

        return parseOperatorOnPacketLength(parseInfo, lengthLiteral, listOf())
    }
}