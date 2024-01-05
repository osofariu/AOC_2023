package day6

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day06Test {

    @Test
    fun `given Example, find ways to win`() {
       val testInput = readInput("day06")
        expectThat(Day06(testInput).part1()).isEqualTo(288)
    }
}
