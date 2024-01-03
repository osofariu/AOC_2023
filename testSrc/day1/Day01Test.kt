package day1

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day01Test {
    @Test
    fun `given a simple example of calibration codes`() {
        val testInput = listOf("1abc2", "fd8g")
        expectThat(Day01().part1(testInput)).isEqualTo(100)
    }
    @Test
    fun `given the example given, expect 142`() {
        val testInput = readInput("day01a")
        expectThat(Day01().part1(testInput)).isEqualTo(142)
    }

    @Test
     fun `make sure I am using Regex correctly`() {
         expectThat(Regex("one").replace("3one4one", "1")).isEqualTo("3141")
     }

    @Test
    fun `given a simple example, extract the complex calibration codes for part2`() {
        expectThat(Day01().part2(listOf("4nine"))).isEqualTo(49)
    }

    @Test
    fun `starting digits get mapped as they occur in the text`() {
        val testInput = listOf("eightwothree", "14gxqgqsqqbxfpxnbccjc33eight")
        expectThat(Day01().part2(testInput)).isEqualTo(83+18)
    }

    @Test
    fun `ending digits spelled out get matched from the right`() {
        expectThat(Day01().part2(listOf("five5nzlcdc45clclzrrkjthreeoneoneightsd"))).isEqualTo(58)
    }

    @Test
    fun `all  digits get mapped from text to number`() {
        val testInput = listOf("one","two", "three", "four", "five", "six", "seven", "eight", "nine")
        expectThat(Day01().part2(testInput)).isEqualTo(495)
    }
    @Test
    fun `given example provided and expected result, calculate calibration for part2`() {
        val testInput = readInput("day01b")
        expectThat(Day01().part2(testInput)).isEqualTo(281)
    }
}
