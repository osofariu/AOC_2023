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
}
