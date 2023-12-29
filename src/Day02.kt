class Day02(private val games: List<String>, private val cubesAvailable: Map<Color, Int>) {
    enum class Color {
        red, green, blue
    }

    fun part1(): Int {
        return games.sumOf {  sumValidGameIDs(it) }
    }
    private fun sumValidGameIDs(game: String): Int {
        val (gameId: String, rounds: String) = extractGameIdAndRounds(game)
        val roundsForGame = rounds.split(";")

        return if (roundsForGame.all { roundValid(it) })
            gameId.toInt()
        else
            0
    }

    private fun roundValid(round: String): Boolean {
        return round.split(",").all { c -> haveEnoughOfColor(c) }
    }

    private fun haveEnoughOfColor(colorEntry: String): Boolean {
        val (count, color) = Regex("(\\d+)\\s(\\w+)").matchEntire(colorEntry)!!.destructured
        return cubesAvailable[Color.valueOf(color)]!! >= count.toInt()
    }

    private fun extractGameIdAndRounds(game: String): Pair<String, String> {
        val trimmedGame = game
            .replace(": ", ":")
            .replace("; ", ";")
            .replace(", ", ",")

        val (gameId, roundsString) = Regex("Game (\\d+):(.+)$").matchEntire(trimmedGame)!!.destructured
        return Pair(gameId, roundsString)
    }
}
fun makeCube(red: Int, green: Int, blue: Int): Map<Day02.Color, Int> {
    return mapOf(Day02.Color.red to red, Day02.Color.green to green, Day02.Color.blue to blue)
}

fun main(args: Array<String>) {
    val input = readInput("day02")
    println("part 1 input for " + input.size + " records: " + Day02(input, makeCube(12, 13, 14) ).part1())
}
