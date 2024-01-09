package day7.types

class Hand(val stringValue: String) {

    enum class HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,
    }

    fun handValue() = handType().ordinal
    fun handType(): HandType {
        val groupByCard = stringValue.toList().groupBy { it }
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
}


