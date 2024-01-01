class Token(val row: Int, val column: Int, val value: Char) {
    fun value(): String {
       return value.toString()
    }

    fun tokenNeighbors() = listOf(
        Pair(row - 1, column - 1), Pair(row - 1, column), Pair(row - 1, column + 1),
        Pair(row, column - 1), Pair(row, column + 1), Pair(row + 1, column - 1),
        Pair(row + 1, column), Pair(row + 1, column + 1)
    )
}
