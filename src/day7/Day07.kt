package day7
import day7.comparator.PlayComparator
import day7.comparator.JokerPlayComparator
import day7.types.Hand
import day7.types.JokerPlay
import day7.types.Play
import readInput

class Day07(private val input: List<String>) {

    fun part1(): Int = input
        .map { extractPlay(it) }
        .sortedWith(PlayComparator())
        .foldIndexed(0) { index, acc, play ->
            acc + play.bid * (index + 1)
        }

    private fun extractPlay(line: String): Play {
        val lineParts = line.split(" ")
        return  Play(Hand(lineParts[0]), lineParts[1].toInt())
    }

    fun part2(): Int =
        input
            .map { extractPlay(it) }
            .map { JokerPlay(it.hand, maximizeJokerPower(it), it.bid) }
            .sortedWith(JokerPlayComparator())
            .foldIndexed(0) { index: Int, acc: Int, play: JokerPlay ->
                acc + (play.bid * (index + 1))
            }

    private fun maximizeJokerPower(play: Play): Hand =
        jokerReplacementOptionsForHand(play.hand.stringValue)
            .map { Pair(Hand(it), Hand(it).handValue()) }
            .sortedWith(compareBy { it.second })
            .last()
            .first
}

private fun jokerReplacementOptionsForHand(cards: String): List<String> {
    return if (cards.isEmpty())
        listOf()
    else if (firstCardNotJoker(cards)) {
        combineWithList(cards[0].toString(), jokerReplacementOptionsForHand(cards.substring(1)))
    } else {
        createJokerReplacements(cards)
    }
}

private fun createJokerReplacements(cards: String): List<String> {
    val jokerCardReplacements = listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2")
    return if (processingLastCard(cards)) {
        jokerCardReplacements
    } else {
        jokerCardReplacements.flatMap { r -> combineWithList(r, jokerReplacementOptionsForHand(cards.substring(1))) }
    }
}

private fun processingLastCard(cards: String) = cards.length == 1

private fun firstCardNotJoker(cards: String) = cards[0] != 'J'

private fun combineWithList(s: String, options: List<String>): List<String> =
    if (options.isEmpty()) listOf(s) else options.map { o -> s + o }

fun main() {
    val input = readInput("day07")
    println("part 1 input for " + input.size + "  " + Day07(input).part1() )
    println("part 2 input for " + input.size + "  " + Day07(input).part2() )
}
