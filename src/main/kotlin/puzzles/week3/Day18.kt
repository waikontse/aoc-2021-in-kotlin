package puzzles.week3

import puzzles.Puzzle

private fun Char.isOpenChar(): Boolean = this == '['
private fun Char.isClosingChar(): Boolean = this == ']'
private fun Char.isComma(): Boolean = this == ','

class Day18: Puzzle(18, true) {
    data class ParseInfo(val input: String, var pos: Int) {
        fun currentChar(): Char =  input[pos]
        fun incrementPos() = pos++
        fun checkCommaAndIncrementPos() {
            if (currentChar().isComma() ) incrementPos() else throw UnsupportedOperationException("Expected ',' char")
        }
        fun checkClosingCharAndIncrementPos() {
            if (currentChar().isClosingChar() ) incrementPos() else throw UnsupportedOperationException("Expected ']' char")
        }
    }

    data class SnailNumber(val left: Any?, val right: Any?) {
        var parent: SnailNumber? = null
    }

    override fun solveDemoPart1(): String {
        val snailNumber = parseSnailNumbers(inputDemo)
        snailNumber.onEach { log(it) }
        snailNumber.onEach { log(magnitude(it)) }

        return "0"
    }

    override fun solveDemoPart2(): String {
        TODO("Not yet implemented")
    }

    override fun solvePart1(): String {
        val snailNumber = parseSnailNumbers(input)
        snailNumber.onEach { log(it) }

        return "0"
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun parseSnailNumbers(input: List<String>): List<SnailNumber> {
        return input.map { parseSnailNumber(ParseInfo(it, 0)) }
    }

    private fun parseSnailNumber(snailGraph: ParseInfo): SnailNumber {
        fun parseSnailNumber2(snailGraph: ParseInfo): Any {
            //log ("$snailGraph $parent")
            if (snailGraph.currentChar().isOpenChar()) {
                // Move forward and start a new snail number
                snailGraph.incrementPos()

                val leftSnailNumber = parseSnailNumber2(snailGraph)
                snailGraph.checkCommaAndIncrementPos()

                val rightSnailNumber = parseSnailNumber2(snailGraph)
                snailGraph.checkClosingCharAndIncrementPos()

                return setParentForSnailDescendents(SnailNumber(null, null), leftSnailNumber, rightSnailNumber)
            } else if (snailGraph.currentChar().isDigit()) {
                val digit = snailGraph.currentChar().digitToInt()
                snailGraph.incrementPos()
                return digit
            } else {
                throw UnsupportedOperationException("Unsupported char ${snailGraph.currentChar()}")
            }
        }

        return parseSnailNumber2(snailGraph) as SnailNumber
    }

    private fun setParentForSnailDescendents(parent: SnailNumber, leftChild: Any, rightChild: Any): SnailNumber {
        if (leftChild is SnailNumber) {
            leftChild.parent = parent
        }

        if (rightChild is SnailNumber) {
            rightChild.parent = parent
        }

        return parent.copy(left = leftChild, right = rightChild)
    }

    private fun magnitude(snailNumber: SnailNumber): Long {
        fun magnitude2(snailNumber: Any?): Long {
            return when(snailNumber) {
                is SnailNumber -> 3 * magnitude2(snailNumber.left) + 2 * magnitude2(snailNumber.right)
                is Int -> snailNumber.toLong()
                else -> throw UnsupportedOperationException("Unsupported type")
            }
        }

        return magnitude2(snailNumber)
    }
}