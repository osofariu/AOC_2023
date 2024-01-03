import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day03Test {
    @Test
    fun `given provided example for part1, expect a match`() {
        val testInput = readInput("day03a")
        expectThat(Day03(testInput).part1()).isEqualTo(4361)
    }
    @Test
    fun `when next digit is on next row and column treat it as a new cluster`() {
        val testInput = readInput("day03b")
        expectThat(Day03(testInput).part1()).isEqualTo(581)
    }
    @Test
    fun `given a simple example of two number near a start, use them`() {
        val testInput = readInput("day03b")
        expectThat(Day03(testInput).part2()).isEqualTo(53238)
    }

    @Test
    fun `given example for part1, expect correct answer`() {
        val testInput = readInput("day03a")
        expectThat(Day03(testInput).part2()).isEqualTo(467835)
    }
}
