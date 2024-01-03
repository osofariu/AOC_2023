package day4

import readInput
import kotlin.math.pow

class Day04(private val scratchCards: List<String>) {
    public fun part1(): Int {
        return scratchCards.sumOf {
            processCard(it)
        }
    }
    private fun processCard(card: String): Int {
        val (win: List<Int>, have: List<Int>) = extractNumbers(card)
        return scoreCardPart1(win, have)
    }

    private fun extractNumbers(card: String): Pair<List<Int>,List<Int>> {
        val (winNumbers, haveNumbers) = Regex("Card\\s+\\d+:\\s+([0-9 ]+)\\|([0-9 ]+)$")
            .matchEntire(card)!!.destructured
        return Pair(intValues(winNumbers), intValues(haveNumbers))
    }
    private fun intValues(intStrings: String) = intStrings.split(" ").filter { it != "" }.map { it.toInt() }

    private fun scoreCardPart1(winList: List<Int>, haveList: List<Int>): Int {
       val winningCards: Int = winningCardsCount(haveList, winList)
        return 2.0.pow( winningCardsCount(haveList, winList) - 1.0).toInt()
    }

    private fun winningCardsCount(haveList: List<Int>, winList: List<Int>) =
        haveList.fold(0) { acc: Int, number: Int ->
            if (winList.contains(number)) acc + 1 else acc
        }

    fun part2(): Int {
        val scratchCardsEarned = scratchCards
            .mapIndexed { index, card -> generateScratchCards(index+1, card) }
            .toMap().toMutableMap()
        (scratchCards.size-1).downTo(1).forEach { index ->
                updateScratchCardsForIndex(index, scratchCardsEarned)
        }
        return scratchCardsEarned.values.sumOf { it.size } + scratchCardsEarned.size
    }
    private fun generateScratchCards(index: Int, card: String): Pair<Int, List<Int>> {
        val (win: List<Int>, have: List<Int>) = extractNumbers(card)
        val points = winningCardsCount(win, have)
        val children =(index+ 1..index+points).toList()
        return Pair(index, children)
    }
    private fun updateScratchCardsForIndex(cardToUpdate: Int, scratchCards: MutableMap<Int, List<Int>>): Unit {
        val cardsToMultiply = scratchCards[cardToUpdate]!!.orEmpty()
        (1..scratchCards.size).forEach {indexToUpdate ->
            scratchCards[indexToUpdate] =
                if (scratchCards[indexToUpdate]!!.contains(cardToUpdate)) {
                    cardsToMultiply.plus(scratchCards[indexToUpdate]!!)
                } else {
                    scratchCards[indexToUpdate].orEmpty()
                }
        }
    }
}
fun main() {
    val input = readInput("day04")
    println("part 1 input for " + input.size + " sum: " + Day04(input).part1() )
    println("part 2 input for " + input.size + " sum: " + Day04(input).part2() )
}
