package day5

import day4.Day04
import readInput

class Day05(val input: List<String>) {
    fun part1(): Long {
        val (seeds, rangeMaps) = parseRangeMaps(input)
        return seeds.minOf {seed -> locationForSeed(seed, rangeMaps)}
    }
    private fun locationForSeed(seed: Long, allRangeMaps: List<List<RangeMap>>) =
         allRangeMaps.fold(seed) {acc: Long, rangeMaps: List<RangeMap> ->
            lookup(acc, rangeMaps)
    }
    private fun parseRangeMaps(input: List<String>): Pair<List<Long>, List<List<RangeMap>>> {
        val seeds: List<Long> = extractSeeds(input)
        val allRanges: List<List<RangeMap>> = extractAllRanges(input)
        return Pair(seeds, allRanges)
    }

    private fun extractAllRanges(input: List<String>): List<List<RangeMap>> {
        val mapSections = input.joinToString ( "\n" ).split("\n\n").drop(1)
        return mapSections.map { section ->
            val numberRanges = section.split("\n").drop(1).map {
                it.split(" ").map {it.toLong()}
            }
            numberRanges.map{ rangeLongs -> RangeMap(rangeLongs[0], rangeLongs[1], rangeLongs[2])}
        }
    }

    private fun extractSeeds(input: List<String>): List<Long> {
        val res = Regex("seeds: (.*)")
            .matchEntire(input[0])!!.destructured.component1()
            .split(" ")
            .filter { it != " " }

        return res.map { it.toLong()}
    }
}
fun main() {
    val input = readInput("day05")
    println("part 1 input for " + input.size + "  " + Day05(input).part1() )
}
