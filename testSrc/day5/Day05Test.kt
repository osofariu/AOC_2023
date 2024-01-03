package day5

import org.junit.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day05Test {
    @Test
    fun `simple multi-line regex match`() {
        val regex = "-+\\nab(\\d+)\ncd(\\d+)\n=+"
        val text =
            """
                ---
                ab1234
                cd4321
                ===
            """.trimIndent()

        val matchEntire = Regex(regex, setOf(RegexOption.MULTILINE, RegexOption.UNIX_LINES)).matchEntire(text)
        expectThat(matchEntire!!.groups[1]?.value).isEqualTo("1234")
        expectThat(matchEntire.groups[2]?.value).isEqualTo("4321")
    }

    @Test
    fun `given Part1 Example return smallest location number`() {
        val testInput = readInput("day05")
        expectThat(Day05(testInput).part1()).isEqualTo(35)
    }
}
