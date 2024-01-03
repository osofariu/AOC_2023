package day1

import readInput

class Day01 {
    fun part1(input: List<String>): Int {
        return input.sumOf { this.extractSimpleCalibrationCode(it) }
    }

    private fun extractSimpleCalibrationCode(calibrationText: String): Int {
        val firstDigit = calibrationText.first { it.isDigit() }.toString().toInt()
        val lastDigit = calibrationText.last { it.isDigit() }.toString().toInt()
        return 10 * firstDigit + lastDigit;
    }

    fun part2(testInput: List<String>): Int {
        return testInput.sumOf { this.extractComplexCalibrationCode(it) }
    }

    private fun extractComplexCalibrationCode(calibrationText: String): Int {
        val digitMap = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5,
            "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
        )
        val firstDigit: Int = findDigit(calibrationText, digitMap)
        val lastDigit = findDigit(calibrationText.reversed(),
            digitMap.entries.map { it.key.reversed() to it.value }.associateBy({ it.first }, { it.second })
        )
        return firstDigit * 10 + lastDigit
    }

    private fun findDigit(calibrationText: String, digitMap: Map<String, Int>): Int {
        val firstDigit: Int = calibrationText.foldIndexed(0) { index: Int, acc: Int, c: Char ->
            if (acc == 0 && c.isDigit()) {
                c.toString().toInt()
            } else if (acc == 0 && charMatchesDigits(digitMap, calibrationText, index)) {
                mapCharToDigit(digitMap, calibrationText, index)
            } else
                acc
        }
        return firstDigit
    }

    private fun charMatchesDigits(
        digitMap: Map<String, Int>,
        calibrationText: String,
        index: Int
    ): Boolean {
        val matcher: (String) -> Boolean = { calibrationText.substring(index).startsWith(it) }
        return digitMap.keys.any(matcher)
    }

    private fun mapCharToDigit(digitMap: Map<String, Int>, calibrationText: String, index: Int): Int {
        val matcher: (key: String) -> Boolean = { calibrationText.substring(index).startsWith(it) }
        return digitMap[digitMap.keys.first(matcher)].toString().toInt()
    }
}

fun main(args: Array<String>) {
    val input = readInput("day01")
    println("part 1 input for " + input.size + " records: " + Day01().part1(input))
    println("part 2 input for " + input.size + " records: " + Day01().part2(input))
}
