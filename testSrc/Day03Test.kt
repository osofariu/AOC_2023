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
}
