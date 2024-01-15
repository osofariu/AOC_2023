package day9

import readInput

class Day09(val input : List<String>) {
    fun part1(): Long {
        return parseAsInts(input)
            .sumOf { predictNext(it) }
    }

    private fun parseAsInts(input: List<String>): List<List<Int>> {
        return input.map { it.split(" ").map { it.toInt() } }
    }

    private fun predictNext(input: List<Int>): Long {
        val differenceLists = generateDifferenceLists(input, emptyList())
        val extendedDifferenceLists = extendDifferenceLists(differenceLists)
        return extendedDifferenceLists[0].last().toLong()
    }

    private fun generateDifferenceLists(sequence: List<Int>, acc: List<List<Int>>): List<List<Int>> {
        return if (sequence.all { it == 0 }) {
            acc + (listOf(sequence))
        } else {
            val nextSequence = sequence.windowed(2).map { it[1] - it[0] }
            generateDifferenceLists(nextSequence, acc + (listOf(sequence)))
        }
    }

    private fun extendDifferenceLists(hist: List<List<Int>>) : List<List<Int>> {
        return hist.reversed()
            .fold(emptyList<List<Int>>()) { acc: List<List<Int>>, currentList: List<Int> ->
                if (currentList.all { it == 0 }) {
                    acc + listOf((currentList + 0))
                } else {
                    acc + listOf((currentList + (acc.last().last() + currentList.last())))
                }
            }.reversed()
    }
}

fun main() {
    val input = readInput("day09")
    println("part 1 input for " + input.size + "  " + Day09(input).part1())
}
