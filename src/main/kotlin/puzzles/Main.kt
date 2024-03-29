package puzzles

import puzzles.week1.*
import puzzles.week2.Day9
import util.AnswerPrinter

fun main() {
    println("""
  __________________________________
/ Hello, my friend! Stay a while and \
\ listen...                          /
  ----------------------------------
         \   ^__^ 
          \  (oo)\_______
             (__)\       )\/\
                 ||----w |
                 ||     ||
    """.trimIndent())


    val puzzleList = listOf(Day1(), Day2(), Day3(), Day4(), Day5(), Day6(), Day7(), Day9())
    val printer = AnswerPrinter()
    puzzleList.forEach { printer.printPuzzleAnswer(it)}
}

