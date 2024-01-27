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
        expectThat(Day11(input).part1()).isEqualTo(374)
    }

    @Test
    fun `second example for part 2 with expansion factor of 10`() {
        val input = readInput("day11")
        expectThat(Day11(input).part2(10)).isEqualTo(1030)
    }
    @Test
    fun `second example for part 2 with expansion factor of 100`() {
        val input = readInput("day11")
        expectThat(Day11(input).part2(100)).isEqualTo(8410)
    }
}
