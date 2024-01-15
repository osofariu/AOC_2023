package day9

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day09Test {

    @Test
    fun `given provided input for part1, the sum of predicted next values is 114`() {
        val testInput = readInput("day09")
        expectThat(Day09(testInput).part1()).isEqualTo(114)
    }

    @Test
    fun `given provided input for part2, the sum of predicted next values is 2`() {
        val testInput = readInput("day09")
        expectThat(Day09(testInput).part2()).isEqualTo(2)
    }
}
