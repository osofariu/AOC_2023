package day11

import readInput
import kotlin.math.abs

typealias Galaxy = Pair<Int, Int>

class Day11(val input: List<String>) {
    fun part1(): Int {
        val expandIndices = expandIndicesHorizontally(input)
        val expandedCosmos: List<String> = input.flatMap {
            val expandedRow = expandRow(it, expandIndices)
            if (it.any { item -> item == '#' }) listOf(expandedRow) else listOf(
                expandedRow,
                emptyRow(expandedRow.length)
            )
        }
        return sumMinDistances(expandedCosmos)
    }

    private fun expandIndicesHorizontally(input: List<String>): List<Int> =
        (0..<input[0].length).fold(emptyList()) { acc: List<Int>, i ->
            val columnIsEmpty = (0..input.indices.last).all { input[it][i] == '.' }
            if (columnIsEmpty) acc + i else acc
        }

    private fun expandRow(row: String, expandIndices: List<Int>): String {
        return if (expandIndices.isEmpty()) row
        else if (expandIndices.size == 1)
            row.substring(0, expandIndices[0]) + "." + row.substring(expandIndices[0])
        else {
            (listOf(0) + expandIndices).windowed(2)
                .joinToString(".") {
                    row.substring(it[0], it[1])
                } + "." + row.substring(expandIndices.last())
        }
    }

    private fun sumMinDistances(cosmos: List<String>): Int {
        val galaxies = cosmos.flatMapIndexed { i, y ->
            val xIndices = y.withIndex().filter { it.value == '#' }
            xIndices.map {
                Pair(i, it.index)
            }
        }
        return listCombinations(galaxies)
            .sumOf { (g1: Galaxy, g2: Galaxy) ->
                abs(g1.first - g2.first) + abs(g1.second - g2.second)
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

    private fun emptyRow(rowLength: Int): String =
        ".".repeat(rowLength)
}

fun main() {
    val input = readInput("day11")
    println("part 1 input for " + input.size + "  " + Day11(input).part1())
}
