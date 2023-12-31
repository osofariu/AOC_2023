class Schematic(val tokens: List<Token> , val tokenArray: Map<Pair<Int, Int>, Token>) {
    companion object {
        fun from(schematicRows: List<String>): Schematic {
            val tokens = schematicRows.flatMapIndexed() { rowIndex, s ->
                s.mapIndexed { colIndex, c -> Token(rowIndex, colIndex, c) }
            }
            val tokenArray = tokens.associateBy { Pair(it.row, it.column) }
            return Schematic(tokens, tokenArray)
        }
    }
    inner class Chunk(private val tokens: List<Token>) {
        fun neighbours(): List<Char> {
            val neighborPairs = tokens.map { Pair(it.row, it.column) }
                .flatMap {
                    listOf(
                        Pair(it.first - 1, it.second - 1), Pair(it.first - 1, it.second), Pair(it.first - 1, it.second + 1),
                        Pair(it.first, it.second - 1), Pair(it.first, it.second + 1), Pair(it.first + 1, it.second - 1),
                        Pair(it.first + 1, it.second), Pair(it.first + 1, it.second + 1)
                    )
                }
                .toSet().minus(tokens.map { Pair(it.row, it.column) }.toSet())
           return neighborPairs.map { tokenArray[it]?.value?:'.' }
        }

        fun value(): Int {
            return tokens.map { it.value }.joinToString("").toInt()
        }
    }

    fun chunks(regex: String): List<Chunk> {
        val (allChunks, lastTokens) = tokens.filter { Regex(regex).matches(it.value.toString()) }
            .fold(Pair(emptyList<Chunk>(), emptyList<Token>())) { acc, token ->
                val (chunks, tokens) = acc
                if (tokens.isEmpty() || (tokens.last().column == token.column - 1 && tokens.last().row == token.row)) {
                    Pair(chunks, tokens.plus(token))
                } else {
                    Pair(chunks.plus(Chunk(tokens)), listOf(token))
                }
            }
        return allChunks.plus(Chunk(lastTokens))
    }
    class Token(val row: Int, val column: Int, val value: Char) {
        fun value(): Int {
           return value.toString().toInt()
        }
    }
}
