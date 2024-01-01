import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day03Test {
    @Test
    fun testPart1ProvidedExample() {
        val testInput = readInput("day03")
        expectThat(Day03(testInput).part1()).isEqualTo(4361)
    }
    @Test
    fun whenNextDigitIsOnNextRowAndColumnItsANewCluster() {
        val testInput = readInput("day03b")
        expectThat(Day03(testInput).part1()).isEqualTo(581)
    }
    @Test
    fun part2SimpleExample() {
        val testInput = readInput("day03b")
        expectThat(Day03(testInput).part2()).isEqualTo(53238)
    }

    @Test
    fun part2GivenExample() {
        val testInput = readInput("day03")
        expectThat(Day03(testInput).part2()).isEqualTo(467835)
    }
}
