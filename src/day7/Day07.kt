package day7
import readInput

class Day07(val input: List<String>) {

    private val playComparator = Comparator<Pair<Hand, Int>> { play1, play2 ->
        handComparator.compare(play1.first, play2.first)
    }
    fun part1(): Int {
        val plays: List<Pair<Hand, Int>> = parse(input)
        return plays
            .sortedWith(playComparator)
            .foldIndexed(0) { index, acc, play ->
                acc + play.second * (index + 1)
            }
    }

    private fun parse(input: List<String>): List<Pair<Hand, Int>> {
        return input.map { line ->
            val (hand: Hand, bid: Int) = extractPlay(line)
            Pair(hand, bid)
        }}

    private fun extractPlay(line: String): Pair<Hand, Int> {
        val lineParts = line.split(" ")
        val hand = Hand(lineParts[0])
        val bid = lineParts[1].toInt()
        return Pair(hand, bid)
    }

    fun part2(): Int {
        TODO("Not yet implemented")
    }
}
fun main() {
    val input = readInput("day07")
    println("part 1 input for " + input.size + "  " + Day07(input).part1() )
    println("part 2 input for " + input.size + "  " + Day07(input).part2() )
}
