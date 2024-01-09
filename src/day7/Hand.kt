package day7

class Hand(val hand: String) {

    internal fun handValue() = handType().ordinal
    internal fun handType(): HandType {
        val groupByCard = hand.toList().groupBy { it }
        val countsOrdered = groupByCard.values.map { it.size }.sortedDescending()

        return if (groupByCard.size == 1)
            HandType.FIVE_OF_A_KIND
        else if (groupByCard.size == 2 && countsOrdered[0] == 4)
            HandType.FOUR_OF_A_KIND
        else if (groupByCard.size == 2 && countsOrdered[0] == 3)
            HandType.FULL_HOUSE
        else if (countsOrdered[0] == 3)
            HandType.THREE_OF_A_KIND
        else if (countsOrdered.size == 3 && countsOrdered[0] == 2)
            HandType.TWO_PAIR
        else if (groupByCard.size == 4)
            HandType.ONE_PAIR
        else
            HandType.HIGH_CARD
    }

    fun maximizeJokerPower(): Hand =
        jokerReplacementOptions()
            .map { Pair(it, it.handValue()) }
            .sortedWith(compareBy { it.second })
            .last()
            .first

    private fun jokerReplacementOptions(): List<Hand> {
        return jokerReplacementOptions(hand).map { Hand(it) }
    }

    public override fun toString(): String = "hand: $hand"
}

val cardReplacements = listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2")
private fun jokerReplacementOptions(cards: String): List<String> {
    return if (cards.isEmpty())
        listOf()
    else if (cards[0].equals('J')) {
        if (cards.length == 1) {
            cardReplacements
        } else {
            val replacements = cardReplacements.map { r -> r + cards.substring(1) }
            cardReplacements.flatMap { r -> combine(r, jokerReplacementOptions(cards.substring(1))) }
        }
    } else {
        combine(cards[0].toString(), jokerReplacementOptions(cards.substring(1)))
    }
}

fun combine(s: String, options: List<String>): List<String> =
    if (options.isEmpty()) listOf(s) else options.map { o -> s + o }

val handComparator = Comparator<Hand> { hand1, hand2 ->
    if (hand1.handValue() < hand2.handValue())
        -1
    else if (hand1.handValue() > hand2.handValue())
        1
    else compareEqualTypeHands(hand1, hand2, 1)
}

val handComparatorPart2 = Comparator<Play> { play1, play2 ->
    if (play1.bestHand.handValue() < play2.bestHand.handValue())
        -1
    else if (play1.bestHand.handValue() > play2.bestHand.handValue())
        1
    else compareEqualTypeHands(play1.originalHand, play2.originalHand, 2)
}

private fun getCardValue(c: Char): Int {
    return when (c) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        else -> c.toString().toInt()
    }
}

private fun getCardValueForPart(c: Char, part: Int): Int {
    val res = getCardValue(c)
    return if (part == 2 && c == 'J') 1 else res
}

private fun compareEqualTypeHands(hand1: Hand, hand2: Hand, part: Int): Int {
    hand1.hand.zip(hand2.hand).forEach() { p: Pair<Char, Char> ->
        if (getCardValueForPart(p.first, part) < getCardValueForPart(p.second, part)) {
            return -1
        }
        if (getCardValueForPart(p.first, part) > getCardValueForPart(p.second, part)) {
            return 1
        }
    }
    return 0
}
