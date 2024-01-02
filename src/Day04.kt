import kotlin.math.pow

class Day04(private val scratchCards: List<String>) {
    public fun part1(): Int {
        return scratchCards.sumOf {
            processCard(it)
        }
    }
    private fun processCard(card: String): Int {
        val (win: List<Int>, have: List<Int>) = extractNumbers(card)
        return scoreCard(win, have)
    }

    private fun extractNumbers(card: String): Pair<List<Int>,List<Int>> {
        val (winNumbers, haveNumbers) = Regex("Card\\s+\\d+:\\s+([0-9 ]+)\\|([0-9 ]+)$")
            .matchEntire(card)!!.destructured
        return Pair(intValues(winNumbers), intValues(haveNumbers))
    }
    private fun intValues(intStrings: String) = intStrings.split(" ").filter { it != "" }.map { it.toInt() }

    private fun scoreCard(winList: List<Int>, haveList: List<Int>): Int {
       val winningCards: Int = haveList.fold(0) { acc: Int, number: Int ->
           if (winList.contains(number)) acc + 1 else acc
       }
        return 2.0.pow(winningCards - 1.0).toInt()
    }
}
fun main() {
    val input = readInput("day04")
    println("part 1 input for " + input.size + " sum: " + Day04(input).part1() )
}
