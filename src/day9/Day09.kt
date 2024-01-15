package day9

import readInput

class Day09(val input: List<String>) {
    fun part1(): Long {
        return parseAsInts(input)
            .sumOf { predictNext(it) }
    }

    private fun parseAsInts(input: List<String>): List<List<Int>> {
        return input.map { it.split(" ").map { it.toInt() } }
    }

    private fun predictNext(input: List<Int>): Long {
        val differenceLists = generateDifferenceLists(input, emptyList())
        val extendedDifferenceLists = extendDifferenceLists(differenceLists, Direction.RIGHT)
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

    enum class Direction {
        LEFT, RIGHT
    }

    private fun extendDifferenceLists(hist: List<List<Int>>, direction: Direction): List<List<Int>> {
        return hist.reversed()
            .fold(emptyList<List<Int>>()) { acc: List<List<Int>>, currentList: List<Int> ->
                if (currentList.all { it == 0 }) {
                    acc + listOf((currentList + 0))
                } else if (direction == Direction.RIGHT) {
                    acc + listOf((currentList + (acc.last().last() + currentList.last())))
                } else {
                    acc + listOf((listOf((currentList.first() - acc.last().first())) + currentList ))
                }
            }.reversed()
    }

    fun part2(): Long {
        return parseAsInts(input)
            .sumOf { predictPrevious(it) }
    }

    private fun predictPrevious(list: List<Int>): Long {
        val differenceLists = generateDifferenceLists(list, emptyList())
        val extendedDifferenceLists = extendDifferenceLists(differenceLists, Direction.LEFT)
        return extendedDifferenceLists[0].first().toLong()
    }
}

fun main() {
    val input = readInput("day09")
    println("part 1 input for " + input.size + "  " + Day09(input).part1())
    println("part 2 input for " + input.size + "  " + Day09(input).part2())
}
