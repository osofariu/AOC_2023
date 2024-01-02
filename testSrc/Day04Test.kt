import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day04Test {

    @Test
    fun givenExample() {
        val testInput = readInput("day04")
        expectThat(Day04(testInput).part1()).isEqualTo(13)
    }
}
