package day7.comparator

import day7.types.JokerPlay

class JokerPlayComparator : BaseComparator<JokerPlay> {
    override fun compare(first: JokerPlay, second: JokerPlay): Int {
        return if (first.bestHand.handValue() < second.bestHand.handValue())
            -1
        else if (first.bestHand.handValue() > second.bestHand.handValue())
            1
        else compareEqualTypeHands(first.hand.stringValue, second.hand.stringValue)
    }

    override fun getCardValue(c: Char): Int {
        return when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> c.toString().toInt()
        }
    }
}
