package puzzles.week3

import puzzles.Puzzle
import kotlin.math.ceil
import kotlin.math.floor

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

    data class Sn(var l: Any?, var r: Any?) {
        var parent: Sn? = null
        fun height(): Int {
            tailrec fun height(current: Sn?, currentHeight: Int): Int {
                if (current == null) {
                    return currentHeight
                }

                return height(current.parent, currentHeight.inc())
            }

            return height(parent, 0)
        }

        fun canExplode(): Boolean = l is Int && r is Int
        fun root(): Sn {
            if (this.parent == null) {
                return this
            }

            return this.parent!!.root()
        }

        operator fun plus(other: Sn): Sn {
            val newParent = Sn(this, other)
            this.parent = newParent
            other.parent = newParent

            return newParent
        }
    }

    override fun solveDemoPart1(): String {
        val snailNumber = parseSnailNumbers(inputDemo)
        //snailNumber.onEach { log(it) }
        //snailNumber.onEach { log(magnitude(it)) }
        snailNumber.reduce {acc, v -> reduce(acc + v) }
            //.let { reduce(it) }
            .let { println(it) }
//        snailNumber.map { reduce(it) }
//            .onEach { log(it) }

        //printHasParent(snailNumber[0])

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

    private fun parseSnailNumbers(input: List<String>): List<Sn> {
        return input.map { parseSnailNumber(ParseInfo(it, 0)) }
    }

    private fun parseSnailNumber(snailGraph: ParseInfo): Sn {
        fun parseSnailNumber2(snailGraph: ParseInfo): Any {
            log ("$snailGraph")
            if (snailGraph.currentChar().isOpenChar()) {
                // Move forward and start a new snail number
                snailGraph.incrementPos()

                val leftSnailNumber = parseSnailNumber2(snailGraph)
                snailGraph.checkCommaAndIncrementPos()

                val rightSnailNumber = parseSnailNumber2(snailGraph)
                snailGraph.checkClosingCharAndIncrementPos()

                return setParentForSnailDescendants(Sn(leftSnailNumber, rightSnailNumber), leftSnailNumber, rightSnailNumber)
            } else if (snailGraph.currentChar().isDigit()) {
                val digit = snailGraph.currentChar().digitToInt()
                snailGraph.incrementPos()
                return digit
            } else {
                throw UnsupportedOperationException("Unsupported char ${snailGraph.currentChar()}")
            }
        }

        return parseSnailNumber2(snailGraph) as Sn
    }

    private fun setParentForSnailDescendants(parent: Sn, leftChild: Any, rightChild: Any): Sn {
        if (leftChild is Sn) {
            leftChild.parent = parent
        }

        if (rightChild is Sn) {
            rightChild.parent = parent
        }

        return parent
    }

    private fun reduce(snailNumbers: Sn): Sn {
        fun reduce2(snailNumbers: Any?) {
            if (snailNumbers is Sn) {
                log ("Reducing snail number: $snailNumbers with heigth: ${snailNumbers.height()}")
                if (snailNumbers.height() == 4) {
                    explodeAndPropagate(snailNumbers)
                } else {
                    reduce2(snailNumbers.l)
                    reduce2(snailNumbers.r)
                }
            }
         }

        reduce2(snailNumbers)
        return snailNumbers.root()
    }

    private fun explodeAndPropagate(target: Sn) {
        log ("Explode and propagating $target. Is LeftMost: ${isLeftMost(target)}")

        // Decide which node to explode
        val snailNumber = getExplodableSnail(target)
        log ("Choosen $snailNumber")

        val updatedLeftNode = propagateClosestLeft(snailNumber, snailNumber.parent, snailNumber.l as Int)
        val updatedRightNode = propagateClosestRight(snailNumber, snailNumber.parent, snailNumber.r as Int)
        reduceNodeToZero(snailNumber.parent!!, snailNumber.parent?.l == snailNumber)

        log ("New updated numbers ${snailNumber.root()}")

        val newSplittedLeftNode = split(updatedLeftNode)
        //if (newSplittedLeftNode?.height() == 4) { explodeAndPropagate(newSplittedLeftNode) }
        if (newSplittedLeftNode != null) reduce(newSplittedLeftNode)

        val newSplittedRightNode = split(updatedRightNode)
        //if (newSplittedRightNode?.height() == 4) { explodeAndPropagate(newSplittedRightNode) }
        if (updatedRightNode != null) reduce(updatedRightNode)

//        if (updatedLeftNode != null) reduce(updatedLeftNode)
//        if (updatedRightNode != null) reduce(updatedRightNode)
    }

    private fun getExplodableSnail(target: Sn): Sn {
        if (target.canExplode()) {
            return target
        } else if (target.l is Sn) {
            return target.l as Sn
        } else if(target.r is Sn) {
            return target.r as Sn
//            throw UnsupportedOperationException("Could not explode snail $target")
        } else {
            throw UnsupportedOperationException("Could not explode snail $target")
        }
    }

    private fun reduceNodeToZero(parent: Sn, isLeft: Boolean) {
        if (isLeft) parent.l = 0 else parent.r = 0
    }

    private fun propagateClosestLeft(origin: Sn, currentParent: Sn?, number: Int): Sn? {
        if (isLeftMost(origin)) {
            return null
        }

        return propagateClosestLeft2(origin, currentParent, number)
    }

    private fun propagateClosestLeft2(origin: Sn, currentParent: Sn?, number: Int): Sn {
        if (currentParent?.l is Int) {
            currentParent.l = (currentParent.l as Int).plus(number)
            return currentParent
        }

        if (currentParent?.l is Sn && currentParent.l != origin) {
            // update the right most one
            return updateRightMost(currentParent.l as Sn, number)
        }

        if (currentParent?.parent == null) {
            return currentParent!!
        }

        return propagateClosestLeft2(currentParent, currentParent.parent, number)
    }

    private fun updateRightMost(currentParent: Sn, newNumber: Int): Sn {
        if (currentParent.r is Int) {
            currentParent.r = (currentParent.r as Int).plus(newNumber)
            return currentParent
        }

        return updateRightMost(currentParent.r as Sn, newNumber)
    }


    private fun propagateClosestRight(origin: Sn, currentParent: Sn?, number: Int): Sn? {
        if (isRightMost(currentParent!!)) {
            return null
        }

        fun propagateClosestRight2(origin: Sn, currentParent: Sn?, number: Int): Sn {
            if (currentParent?.r is Int) {
                currentParent.r = (currentParent.r as Int).plus(number)
                return currentParent
            }

            if (currentParent?.r is Sn && currentParent.r != origin) {
                // get the left mode value and update it
                return updateLeftMost(currentParent.r as Sn, number)
            }

            if (currentParent?.parent == null) {
                return currentParent!!
            }

            return propagateClosestRight2(currentParent, currentParent.parent, number)
        }

        return propagateClosestRight2(origin, currentParent, number)
    }

    private fun updateLeftMost(currentParent: Sn, newNumber: Int): Sn {
        if (currentParent.l is Int) {
            currentParent.l = (currentParent.l as Int).plus(newNumber)
            return currentParent
        }

        return updateLeftMost(currentParent.l as Sn, newNumber)
    }

    private fun split(snailNumber: Sn?): Sn? {
        if (snailNumber == null) { return null }

        if (snailNumber.l is Int && snailNumber.l as Int >= 10) {
            log ("Splitting left for $snailNumber")
            snailNumber.l = splitNumberToNode(snailNumber.l as Int, snailNumber)
            log ("After splitting: ${snailNumber.root()}")
            return snailNumber.l as Sn
        }

        if (snailNumber.r is Int && snailNumber.r as Int >= 10) {
            log ("Splitting right for $snailNumber")
            snailNumber.r = splitNumberToNode(snailNumber.r as Int, snailNumber)
            log ("After splitting: ${snailNumber.root()}")

            return snailNumber.r as Sn
        }

        return null
    }

    private fun splitNumberToNode(number: Int, parent: Sn): Sn {
        val div = number.toDouble().div(2)
        val newSnailNumber = Sn(floor(div).toInt(), ceil(div).toInt())
        newSnailNumber.parent = parent

        return newSnailNumber
    }

    private fun magnitude(snailNumber: Sn): Long {
        fun magnitude2(snailNumber: Any?): Long {
            return when(snailNumber) {
                is Sn -> 3 * magnitude2(snailNumber.l) + 2 * magnitude2(snailNumber.r)
                is Int -> snailNumber.toLong()
                else -> throw UnsupportedOperationException("Unsupported type")
            }
        }

        return magnitude2(snailNumber)
    }

    private fun isLeftMost(target: Sn): Boolean {
        tailrec fun isLeftMost(target: Sn, currentPos: Any?, depth: Int): Boolean {
            if (depth == 3 && (currentPos as Sn).l is Sn) {
                return currentPos.l == target
            } else if (depth == 3) {
                return false
            }

            if (currentPos is Int) {
                return false
            }

            return isLeftMost(target, (currentPos as Sn).l, depth.inc())
        }

        return isLeftMost(target, target.root(), 0)
    }

    private fun isRightMost(target: Sn): Boolean {
        tailrec fun isRightMost(target: Sn, currentPos: Any?, depth: Int): Boolean {

            if (depth == 3 && (currentPos as Sn).r is Sn) {
                return currentPos.r == target
            } else if (depth == 3) {
                return false
            } else if (currentPos is Int) {
                return false
            }

            return isRightMost(target, (currentPos as Sn).r, depth.inc())
        }

        return isRightMost(target, target.root(), 0)
    }
}