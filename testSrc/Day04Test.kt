import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day04Test {

    @Test
    fun givenExamplePart1() {
        val testInput = readInput("day04")
        expectThat(Day04(testInput).part1()).isEqualTo(13)
    }
    @Test
    fun givenExamplePart2() {
        val testInput = readInput("day04part2")
        expectThat(Day04(testInput).part2()).isEqualTo(30)
    }
}
