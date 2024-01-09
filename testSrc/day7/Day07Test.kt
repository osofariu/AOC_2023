package day7

import day6.Day06
import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day07Test {
    @Test
    fun `given Example, find ways to win part 1`() {
        val testInput = readInput("day07")
        expectThat(Day07(testInput).part1()).isEqualTo(6440)
    }
    @Test
    fun `given Example, find ways to win part 2`() {
        val testInput = readInput("day07")
        expectThat(Day07(testInput).part2()).isEqualTo(5905)
    }

    @Test
    fun `AJAAA has a higher rank than AJAJA`() {
        val testInput = listOf("AJAAA 100", "AJAJA 20")
        expectThat(Day07(testInput).part2()).isEqualTo(220)
    }
}
