package day7.comparator

import day7.types.Play

class PlayComparator : BaseComparator<Play> {
    override fun compare(first: Play, second: Play): Int {
        return if (first.hand.handValue() < second.hand.handValue())
            -1
        else if (first.hand.handValue() > second.hand.handValue())
            1
        else compareEqualTypeHands(first.hand.stringValue, second.hand.stringValue)
    }

    override fun getCardValue(c: Char): Int {
        return when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> c.toString().toInt()
        }
    }
}
