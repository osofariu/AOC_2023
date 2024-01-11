package day8

import readInput

class Day08(private val mapInstructions: List<String>) {
    fun part1(): Int {
        val (instructions, map) = parseInput(mapInstructions)
        val iterations = generateSequence("AAA") { from -> navigate(from, instructions, map) }
            .takeWhile { destination -> destination != "ZZZ" }
            .count()
        return iterations * instructions.length
    }

    private fun parseInput(mapInstructions: List<String>): Pair<String, Map<String, Pair<String, String>>> {
        val instructions = mapInstructions.first()
        val map = mapInstructions
            .drop(2)
            .associateBy(keySelector = { line -> extractKey(line) }, valueTransform = { line -> extractChildren(line) })
        return Pair(instructions, map)
    }

    private fun extractKey(line: String): String =
        Regex("(\\w+).*").matchEntire(line)!!.destructured.component1()

    private fun extractChildren(line: String): Pair<String, String> {
        val (left, right) = Regex(".+\\((\\w+), (\\w+)\\)").matchEntire(line)!!.destructured
        return Pair(left, right)
    }

    private fun navigate(start: String, instructions: String, map: Map<String, Pair<String, String>>): String {
        var intermediate = start
        for (element in instructions) {
            intermediate = if (element == 'L') {
                map[intermediate]!!.first
            } else {
                map[intermediate]!!.second
            }
        }
        return intermediate
    }

    fun part2(): Long {
        val (instructions, stringMap) = parseInput(mapInstructions)
        val initialStringKeys = stringMap.keys.filter { it.endsWith("A") }
        val iterations = initialStringKeys.map {distanceToZ(it, stringMap, instructions) }.toSet().reduce { a: Long, b: Long -> a * b }
        val result = iterations * instructions.length
        println("iter: $iterations, result: $result")
        return result
    }

    private fun distanceToZ(key: String, stringMap:Map<String, Pair<String, String>>, instructions: String): Long {
        var counter: Long = 0
        var currentKey = key
        while (!currentKey.endsWith("Z")) {
            counter++
            for (element in instructions) {
                currentKey = if (element == 'L') {
                    stringMap[currentKey]!!.first
                } else {
                    stringMap[currentKey]!!.second
                }
            }
        }
        println("for $key it took $counter steps")
        return counter
    }
}

fun main() {
    val input = readInput("day08")
    println("part 1 input for " + input.size + "  " + Day08(input).part1())
    println("part 2 input for " + input.size + "  " + Day08(input).part2())
}
