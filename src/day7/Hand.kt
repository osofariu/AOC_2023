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
        else if (groupByCard.size == 2 && countsOrdered[0] == 3 )
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
}

val handComparator = Comparator<Hand> { hand1, hand2 ->
    if (hand1.handValue() < hand2.handValue())
        -1
    else if (hand1.handValue() > hand2.handValue())
        1
    else compareEqualTypeHands(hand1, hand2)
}
private fun getCardValue(c: Char): Int {
    return when(c) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> 11
        'T' -> 10
        else -> c.toString().toInt()
    }
}

private fun compareEqualTypeHands(hand1: Hand, hand2: Hand): Int {
    hand1.hand.zip(hand2.hand).forEach() { p: Pair<Char, Char> ->
        if (getCardValue(p.first) < getCardValue(p.second))
            return -1
        else if (getCardValue(p.first) > getCardValue(p.second))
            return 1
    }
    return 0
}
