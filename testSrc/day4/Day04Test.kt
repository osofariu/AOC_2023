package day4

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day04Test {

    @Test
    fun `given the sample input for part1, expect the correct result`() {
        val testInput = readInput("day04a")
        expectThat(Day04(testInput).part1()).isEqualTo(13)
    }
    @Test
    fun `given the sample input for part2, expect the correct result`() {
        val testInput = readInput("day04b")
        expectThat(Day04(testInput).part2()).isEqualTo(30)
    }
}
