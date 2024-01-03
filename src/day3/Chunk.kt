package day3

class Chunk(private val tokens: List<Token>) {
    fun neighbours(tokenMap: Map<Pair<Int, Int>, Token>): List<Char> {
        val neighborPairs = tokens.flatMap { it.tokenNeighbors()}
            .toSet().minus(tokens.map { Pair(it.row, it.column) }.toSet())
       return neighborPairs.map { tokenMap[it]?.value?:'.' }
    }
    fun existsAtLocation(row: Int, column: Int): Boolean {
        return tokens.any {it.row == row && it.column == column}
    }
    fun intValue(): Int {
        return value().toInt()
    }
    private fun value(): String {
        return tokens.map { it.value }.joinToString("")
    }
}
