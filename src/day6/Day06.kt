package day6

import readInput

class Day06(private val input: List<String>) {
    fun part1(): Int {
        val races: List<Pair<Int, Int>> = parseRaces(input)
        return races
            .map { waysToWin(it) }
            .reduce { acc, it -> acc * it }
    }
    private fun parseRaces(input: List<String>): List<Pair<Int, Int>> {
        fun extractNumbersFromLine(rowText: String, rowIndex: Int) =
            Regex("$rowText([ \\d]+)$").matchEntire(input[rowIndex])!!.destructured
            .component1().split(" ").filter { it != "" }.map { it.toInt() }

        val times = extractNumbersFromLine("Time:", 0)
        val distances = extractNumbersFromLine("Distance:", 1)
        return times.zip(distances)
    }
    private fun waysToWin(race: Pair<Int, Int>): Int {
        val (recordTime, distance) = race
        return (1..<recordTime)
            .map { buttonPress ->
                buttonPress * (recordTime - buttonPress)}
            .filter { it > distance }
            .size
    }
}

fun main() {
    val input = readInput("day06")
    println("part 1 input for " + input.size + "  " + Day06(input).part1() )
}
