package day11

import readInput
import kotlin.math.abs

typealias Galaxy = Pair<Long, Long>

class Day11(val input: List<String>) {
    fun part1(): Long {
        val expandIndices = expandIndicesHorizontally(input)
        val expandedCosmos: List<String> = input.flatMap {
            val expandedRow = expandRow(it, expandIndices)
            if (it.any { item -> item == '#' }) listOf(expandedRow) else listOf(
                expandedRow,
                emptyRow(expandedRow.length.toLong())
            )
        }
        return sumMinDistances(expandedCosmos)
    }

    private fun expandIndicesHorizontally(input: List<String>): List<Long> =
        (0..<input[0].length).fold(emptyList()) { acc: List<Long>, i ->
            val columnIsEmpty = (0..input.indices.last).all { input[it][i] == '.' }
            if (columnIsEmpty) acc + i.toLong() else acc
        }

    private fun expandRow(row: String, expandIndices: List<Long>): String {
        return if (expandIndices.isEmpty()) row
        else if (expandIndices.size == 1)
            row.substring(0, expandIndices[0].toInt()) + "." + row.substring(expandIndices[0].toInt())
        else {
            (listOf(0) + expandIndices).windowed(2)
                .joinToString(".") {
                    row.substring(it[0].toInt(), it[1].toInt())
                } + "." + row.substring(expandIndices.last().toInt())
        }
    }

    private fun sumMinDistances(cosmos: List<String>): Long {
        val galaxies = galaxyLocations(cosmos)
        return listCombinations(galaxies)
            .sumOf { (g1: Galaxy, g2: Galaxy) ->
                abs(g1.first - g2.first) + abs(g1.second - g2.second)
            }
    }

    private fun galaxyLocations(cosmos: List<String>) = cosmos.flatMapIndexed { i, y ->
        val xIndices = y.withIndex().filter { it.value == '#' }
        xIndices.map {
            Pair(i.toLong(), it.index.toLong())
        }
    }

    private fun <T> listCombinations(list: List<T>): List<Pair<T, T>> {
        val maxIndex = list.size
        val indexList = (0..<maxIndex).toList()
        val indexPairs =
            indexList.flatMap { first ->
                indexList.map { second ->
                    listOf(first, second)
                }.filterNot { it[0] == it[1] }
            }.map { it.sorted() }.toSet().toList().map { Pair(it[0], it[1]) }
        return indexPairs.map { Pair(list[it.first], list[it.second]) }
    }

    private fun emptyRow(rowLength: Long): String =
        ".".repeat(rowLength.toInt())

    fun part2(expansionFactor: Int): Long {
        val expandHorizontalIndices = expandIndicesHorizontally(input)
        val expandVerticalIndices = expandIndicesVertically(input)
        val galaxies = galaxyLocations(input)
        return listCombinations(galaxies)
            .sumOf { (g1: Galaxy, g2: Galaxy) ->
                val (g1y, g2y) = listOf(g1.first, g2.first).sorted()
                val verticalExpansionSlots = expandVerticalIndices.filter { it in (g1y..g2y) }.size
                val (g1x, g2x) = listOf(g1.second, g2.second).sorted()
                val horizontalExpansionSlots = expandHorizontalIndices.filter { it in (g1x..g2x) }.size
                (g2y - g1y - verticalExpansionSlots) + (verticalExpansionSlots * expansionFactor) + (g2x - g1x - horizontalExpansionSlots) + (horizontalExpansionSlots * expansionFactor)
            }
    }

    private fun expandIndicesVertically(input: List<String>): List<Long> =
        List(input.size) { index ->
            Pair(index, (input[index].none { it == '#' }))
        }.filter { it.second }.map { it.first.toLong() }
}

fun main() {
    val input = readInput("day11")
    println("part 1 input for " + input.size + "  " + Day11(input).part1())
    val expansionFactor = 1_000_000
    println("part 2 input for " + input.size + "  " + Day11(input).part2(expansionFactor))
}

