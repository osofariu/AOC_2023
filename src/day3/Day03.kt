package day3

import readInput

class Day03(private val schematicRows: List<String>) {
    fun part1(): Int {
        val schematic = Schematic.from(schematicRows)
        return schematic.numericChunks.fold(0) { acc, chunk: Chunk ->
             if (chunk.neighbours(schematic.tokenMap).any { isSymbol(it) })
                acc + chunk.intValue()
            else
                acc
        }
    }
    private fun isSymbol(c: Char): Boolean {
        return (!Regex("\\d|\\w|\\.").matches(c.toString()))
    }

    fun part2(): Int {
        val schematic = Schematic.from(schematicRows)
        return schematic.tokens.asSequence().filter { it.value() == "*" }
            .map { it.tokenNeighbors() }
            .map { schematic.neighborChunks(it) }
            .toSet()
            .fold(0) {acc: Int, chunksNearLocation: List<Chunk> ->
                if (chunksNearLocation.size == 2)
                    acc + (chunksNearLocation[0].intValue() * chunksNearLocation[1].intValue())
                else
                    acc
        }
    }
}

fun main() {
    val input = readInput("day3")
    println("part 1 input for " + input.size + " sum: " + Day03(input).part1() )
    println("part 2 input for " + input.size + " sum: " + Day03(input).part2() )
}
