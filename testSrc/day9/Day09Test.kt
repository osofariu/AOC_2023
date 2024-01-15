package day9

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day09Test {

    @Test
    fun `given provided input the sum of predicted next values us 114`() {
        val testInput = readInput("day09")
        expectThat(Day09(testInput).part1()).isEqualTo(114)
    }
}
