import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day01KtTest {
    @Test
    fun trivialExample() {
        val testInput = listOf("1abc2", "fd8g")
        expectThat(Day01().part1(testInput)).isEqualTo(100)
    }
    @Test
    fun providedExample() {
        val testInput = readInput("day01_test")
        expectThat(Day01().part1(testInput)).isEqualTo(142)
    }
}
