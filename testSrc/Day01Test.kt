import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day01Test {
    @Test
    fun trivialExample() {
        val testInput = listOf("1abc2", "fd8g")
        expectThat(Day01().part1(testInput)).isEqualTo(100)
    }
    @Test
    fun providedExample() {
        val testInput = readInput("day01_1")
        expectThat(Day01().part1(testInput)).isEqualTo(142)
    }

    @Test
     fun checkRegexReplacement() {
         expectThat(Regex("one").replace("3one4one", "1")).isEqualTo("3141")
     }

    @Test
    fun simpleExample() {
        expectThat(Day01().part2(listOf("4nine"))).isEqualTo(49)
    }

    @Test
    fun trickyExampleMapSomeDigits() {
        val testInput = listOf("eightwothree", "14gxqgqsqqbxfpxnbccjc33eight")
        expectThat(Day01().part2(testInput)).isEqualTo(83+18)
    }

    @Test
    fun anotherTrickyExample() {
        expectThat(Day01().part2(listOf("five5nzlcdc45clclzrrkjthreeoneoneightsd"))).isEqualTo(58)
    }

    @Test
    fun trivialExampleMapAllDigits() {
        val testInput = listOf("one","two", "three", "four", "five", "six", "seven", "eight", "nine")
        expectThat(Day01().part2(testInput)).isEqualTo(495)
    }
    @Test
    fun providedExample2() {
        val testInput = readInput("day01_2")
        expectThat(Day01().part2(testInput)).isEqualTo(281)
    }
}
