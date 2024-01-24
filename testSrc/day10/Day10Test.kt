package day10

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day10Test {
    @Test
    fun `give simple example calculate the longest distance from S`() {
        val testInput = readInput("day10")
        expectThat(Day10(testInput).part1()).isEqualTo(8)
    }

    @Test
    fun `give simple example calculate the number of tiles within the loop`() {
        val testInput = readInput("day10b")
        expectThat(Day10(testInput).part2()).isEqualTo(4)
    }
    @Test
    fun `give another example calculate the number of tiles within the loop`() {
        val testInput = readInput("day10c")
        expectThat(Day10(testInput).part2()).isEqualTo(10)
    }

    @Test
    fun `give yet another example calculate the number of tiles within the loop`() {
        val testInput = readInput("day10d")
        expectThat(Day10(testInput).part2()).isEqualTo(8)
    }
    @Test
    fun `give yet another another example calculate the number of tiles within the loop`() {
        val testInput = readInput("day10e")
        expectThat(Day10(testInput).part2()).isEqualTo(2)
    }
}
