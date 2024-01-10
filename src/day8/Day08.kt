package day8

import readInput
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
        return instructions.fold(start) { from, direction ->
            navigateOneStep(map, from, direction)
        }
    }


    private fun navigateOneStep(map: Map<String, Pair<String, String>>, from: String, direction: Char): String {
        val res = when (direction) {
            'L' -> map[from]!!.first
            'R' -> map[from]!!.second
            else -> throw IllegalStateException("Bad input")
        }
       // println("from: $from, direction: $direction, to: $res")
        return res
    }

    fun part2(): Long {
        val (instructions, map) = parseInput(mapInstructions)
        val startingPoints = map.keys.filter { it.endsWith("A") }

        var intermediate = startingPoints
        var counter = 0
        println("part 2 starting with $intermediate")
        showProgress(counter)
        while (!intermediate.all { it.endsWith("Z") }) {
            counter += 1
            instructions.map { instruction ->
                intermediate = intermediate.map { startingPoint ->
                    when (instruction) {
                        'L' -> map[startingPoint]!!.first
                        'R' -> map[startingPoint]!!.second
                        else -> throw IllegalStateException("Bad input $startingPoint")
                    }
                }
            }
            if (counter % 1000000 == 0) {
                showProgress(counter)
            }
        }
        println("Finished. Counter = $counter")
        return counter.toLong() * instructions.length
    }
}



private fun showProgress(counter: Int) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val current = LocalDateTime.now().format(formatter)
    val counterStr = counter.toString().padStart(10, ' ')
    println("$current\t$counterStr")
}

fun main() {
    val input = readInput("day08")
    println("part 1 input for " + input.size + "  " + Day08(input).part1())
    println("part 2 input for " + input.size + "  " + Day08(input).part2())
}
