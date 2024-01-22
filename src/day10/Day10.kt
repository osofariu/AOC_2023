package day10

import day9.Day09
import readInput

class Day10(val input: List<String>) {
    fun part1(): Int {
        val maze = Maze(input)
        return maze.findLongestPath()
    }
    fun part2(): Int {
        val maze = Maze(input)
        return maze.countTilesInLoop()
    }
}




fun main() {
    val input = readInput("day10")
    println("part 1 input for " + input.size + "  " + Day10(input).part1())
    println("part 2 input for " + input.size + "  " + Day10(input).part2())
}
