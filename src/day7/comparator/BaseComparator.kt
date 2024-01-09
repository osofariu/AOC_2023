package day7.comparator

interface BaseComparator<T> : Comparator<T>{
    override fun compare(first: T, second: T): Int

    fun getCardValue(c: Char): Int

    fun compareEqualTypeHands(first: String, second: String): Int {
        first.zip(second).forEach() { p: Pair<Char, Char> ->
            if (getCardValue(p.first) < getCardValue(p.second)) {
                return -1
            }
            if (getCardValue(p.first) > getCardValue(p.second)) {
                return 1
            }
        }
        return 0
    }
}
