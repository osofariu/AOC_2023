import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class Day02Test {
    @Test
    fun part1Simple() {
        val games = listOf("Game 123: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")
        expectThat(Day02(games).part1(makeCube(4, 2, 6))).isEqualTo(123)
        expectThat(Day02(games).part1(makeCube(3, 2, 6))).isEqualTo(0)
        expectThat(Day02(games).part1(makeCube(4, 1, 6))).isEqualTo(0)
        expectThat(Day02(games).part1(makeCube(4, 2, 5))).isEqualTo(0)
    }

    @Test
    fun part1AnotherExample() {
        val games = listOf("Game 1: 3 green, 1 blue, 3 red; 3 blue, 1 green, 3 red; 2 red, 12 green, 7 blue; 1 red, 4 blue, 5 green; 7 green, 2 blue, 2 red")
        expectThat(Day02(games).part1(makeCube(12, 13 ,14))).isEqualTo(1)
        expectThat(Day02(games).part1(makeCube(12, 6 ,14))).isEqualTo(0)
    }

    @Test
    fun part2Simple() {
        val games = listOf("Game 1: 3 green, 1 blue, 3 red; 3 blue, 1 green, 3 red; 2 red, 12 green, 7 blue; 1 red, 4 blue, 5 green; 7 green, 2 blue, 2 red")
        expectThat(Day02(games).part2()).isEqualTo(12*7*3)
    }

    @Test
    fun part2providedExample() {
        val testInput = readInput("day02")
        expectThat(Day02(testInput).part2()).isEqualTo(2286)
    }
    fun makeCube(red: Int, green: Int, blue: Int): Map<Day02.Color, Int> {
        return mapOf(Day02.Color.red to red, Day02.Color.green to green, Day02.Color.blue to blue)
    }
}


