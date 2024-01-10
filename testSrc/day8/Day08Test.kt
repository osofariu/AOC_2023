package day8

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day08Test {

    @Test
    fun `given example input for part1, it takes 6 steps to reach ZZZ`() {
        val mapInstructions = readInput("day08a")
        expectThat(Day08(mapInstructions).part1()).isEqualTo(6)
    }

    @Test
    fun `given example input for part2, it takes 6 steps to reach Z`() {
        val mapInstructions = readInput("day08b")
        expectThat(Day08(mapInstructions).part2()).isEqualTo(6)
    }

    @Test
    fun `given another example input for part2, it takes more steps to reach Z`() {
        val mapInstructions = readInput("day08c")
        expectThat(Day08(mapInstructions).part2()).isEqualTo(60)
    }
}
