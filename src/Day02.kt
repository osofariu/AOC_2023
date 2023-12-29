class Day02(private val games: List<String>) {
    enum class Color {
        red, green, blue
    }

    fun part1(cubesAvailable: Map<Color, Int>): Int {
        return games.sumOf { sumValidGameIDs(it, cubesAvailable) }
    }

    private fun sumValidGameIDs(game: String, cubesAvailable: Map<Color, Int>): Int {
        val (gameId: String, rounds: String) = extractGameIdAndRounds(game)
        return if (rounds.split(";").all { roundValid(it, cubesAvailable) })
            gameId.toInt()
        else
            0
    }

    private fun roundValid(round: String, cubesAvailable: Map<Color, Int>): Boolean {
        return round.split(",").all { c -> haveEnoughOfColorForCube(c, cubesAvailable) }
    }

    private fun haveEnoughOfColorForCube(colorEntry: String, cubesAvailable: Map<Color, Int>): Boolean {
        val (count, color) = extractColorCounts(colorEntry)
        return cubesAvailable[Color.valueOf(color)]!! >= count.toInt()
    }

    private fun extractGameIdAndRounds(game: String): Pair<String, String> {
        val trimmedGame = game
            .replace(": ", ":")
            .replace("; ", ";")
            .replace(", ", ",")

        val (gameId, roundsString) = extractGameIdAndRoundsForGame(trimmedGame)
        return Pair(gameId, roundsString)
    }

    fun part2(): Int {
        return games.sumOf { sumMinimumPowersForGame(it) }
    }
    private fun sumMinimumPowersForGame(game: String): Int {
        val (_: String, rounds: String) = extractGameIdAndRounds(game)
        val aggregateCounts = rounds.split(";").fold(ColorCounts()) { acc, round ->
            applyMinimumCountsForRound(round, acc)
        }
        return aggregateCounts.red * aggregateCounts.green * aggregateCounts.blue
    }

    private fun applyMinimumCountsForRound(round: String, colorCounts: ColorCounts): ColorCounts {
        return round.split(",").fold(colorCounts) { acc, colorEntry ->
            val (count, color) = extractColorCounts(colorEntry)
            acc.applyMaximumCountForColor(color, count.toInt())
        }
    }

    private fun extractGameIdAndRoundsForGame(trimmedGame: String) =
        Regex("Game (\\d+):(.+)$").matchEntire(trimmedGame)!!.destructured

    private fun extractColorCounts(colorEntry: String) =
        Regex("(\\d+)\\s(\\w+)").matchEntire(colorEntry)!!.destructured

    data class ColorCounts(val red: Int = 0, val green: Int = 0, val blue: Int = 0) {
        fun applyMaximumCountForColor(color: String, count: Int): ColorCounts {
            return when (color) {
                "red" -> copy(red = Math.max(red, count))
                "blue" -> copy(blue = Math.max(blue, count))
                "green" -> copy(green = Math.max(green, count))
                else -> this
            }
        }
    }
}

fun main(args: Array<String>) {
    val input = readInput("day02")
    println("part 2 input for " + input.size + " records: " + Day02(input).part2() )
}
