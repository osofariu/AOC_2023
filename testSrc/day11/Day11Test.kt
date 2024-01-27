package day11

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.math.exp

class Day11Test {
    @Test
    fun `first example`() {
        val input = readInput("day11")
        val sumOfDistance = Day11(input).part1()
        expectThat(Day11(input).part1()).isEqualTo(374)
    }
}
