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
}
