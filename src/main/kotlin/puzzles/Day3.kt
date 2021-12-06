package puzzles

import util.ReaderUtil

class Day3: Puzzle {
    val inputDemo: List<String> = ReaderUtil.readResourceAsStrings("input3demo.txt")
    val input: List<String> = ReaderUtil.readResourceAsStrings("input3.txt")

    override fun solveDemoPart1(): Int {
        return powerReading(inputDemo, 5)
            .map { it.toInt(2) }
            .reduce { gamma, epsilon -> gamma * epsilon }
    }

    override fun solvePart1(): Int {
        return powerReading(input, 12)
            .map { it.toInt(2) }
            .reduce {gamma, epsilon -> gamma * epsilon }
    }

    override fun solvePart2(): Int {
        val readings = powerReading(input, 12)
        return filter(readings, input)
            .map { it.toInt(2) }
            .reduce {gamma, epsilon -> gamma * epsilon }
    }

    override fun solveDemoPart2(): Int {
        val readings = powerReading(inputDemo, 5)
        return filter(readings, inputDemo)
            .map { it.toInt(2) }
            .reduce {gamma, epsilon -> gamma * epsilon }
    }

    private fun powerReading(riddle: List<String>, valueLength: Int): List<String> {
        val counter: List<MutableMap<Char, Int>> = MutableList(valueLength) { mutableMapOf('0' to 0, '1' to 0) }
        riddle.fold( counter) { acc, r -> countOccurences(r, acc)}

        return listOf(counter.map { c -> if (c['0']!! < c['1']!!) '1' else '0' }.joinToString(""),
                      counter.map { c -> if (c['0']!! < c['1']!!) '0' else '1' }.joinToString(""))
    }

    private fun countOccurences(riddle: String, counter: List<MutableMap<Char, Int>>): List<MutableMap<Char, Int>> {
        riddle.toCharArray()
            .forEachIndexed {
                    index, c -> counter[index].compute(c) { _, oldInt -> if (oldInt == null) 1 else oldInt+1 }
            }

        return counter
    }

    private fun filter(powerLevels: List<String>, readings: List<String>): List<String> {
        return  filter2(powerLevels[0], 0, readings) + filter3(powerLevels[1], 0, readings)
    }

    private fun filter2(reading: String, index: Int, readings: List<String>): List<String> {
        if (readings.size == 1) {
            return readings
        }

        if (readings.size % 2 == 0) {
            val charCount = readings.groupingBy { it[index] }.eachCount()
            val newReadings =
            if (charCount['0'] == charCount['1']) readings.filter { it[index] == '1' } else readings.filter { it[index] == reading[index] }

            val newReading = powerReading(newReadings, reading.length)[0]

            return filter2(newReading, index+1, newReadings)
        }
        else {
            val newReadings = readings.filter { it[index] == reading[index] }
            val newReading = powerReading(newReadings, reading.length)[0]

            return filter2(newReading, index+1, newReadings)
        }
    }

    private fun filter3(reading: String, index: Int, readings: List<String>): List<String> {
        if (readings.size == 1) {
            return readings
        }

        if (readings.size % 2 == 0) {
            val charCount = readings.groupingBy { it[index] }.eachCount()
            val newReadings =
                if (charCount['0'] == charCount['1']) readings.filter { it[index] == '0' } else readings.filter { it[index] == reading[index] }

            val newReading = powerReading(newReadings, reading.length)[1]

            return filter3(newReading, index+1, newReadings)
        }
        else {
            val newReadings = readings.filter { it[index] == reading[index] }
            val newReading = powerReading(newReadings, reading.length)[1]

            return filter3(newReading, index+1, newReadings)
        }
    }
}