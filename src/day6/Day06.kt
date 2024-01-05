package day6

import readInput

class Day06(private val input: List<String>) {
    fun part1() = parseRaces()
            .map { waysToWin(it) }
            .reduce { acc, it -> acc * it }

    private fun parseRaces(): List<Pair<Long, Long>> {
        val times: List<Long> = extractNumericsFromLine("Time:", 0)
        val distances: List<Long> = extractNumericsFromLine("Distance:", 1)
        return times.zip(distances)
    }
    private fun extractNumericsFromLine(rowText: String, rowIndex: Int) =
        extractNumberStringsFromLine(rowText, rowIndex).map {it.toLong()}

    private fun extractNumberStringsFromLine(rowText: String, rowIndex: Int): List<String> =
        Regex("$rowText([ \\d]+)$").matchEntire(input[rowIndex])!!.destructured
            .component1().split(" ").filter {
                it != ""
            }

    private fun waysToWin(race: Pair<Long, Long>): Int {
        val (recordTime, distance) = race
        return (1.toLong()..<recordTime)
            .map { buttonPress ->
                buttonPress * (recordTime - buttonPress)}
            .filter { it > distance }
            .size
    }

    fun part2() = waysToWin(parsePart2Race())

    private fun parsePart2Race(): Pair<Long, Long> {
        val raceTime = extractNumberStringsFromLine("Time:", 0).reduce {acc,s -> acc + s}.toLong()
        val  raceDistance = extractNumberStringsFromLine("Distance:", 1).reduce {acc,s -> acc + s}.toLong()
        return Pair(raceTime, raceDistance)
    }
}

fun main() {
    val input = readInput("day06")
    println("part 1 input for " + input.size + "  " + Day06(input).part1() )
    println("part 2 input for " + input.size + "  " + Day06(input).part2() )
}
