class Schematic(val tokens: List<Token>, val tokenMap: Map<Pair<Int, Int>, Token>) {
    companion object {
        fun from(schematicRows: List<String>): Schematic {
            val tokens = schematicRows.flatMapIndexed() { rowIndex, s ->
                s.mapIndexed { colIndex, c -> Token(rowIndex, colIndex, c) }
            }
            val tokenArray = tokens.associateBy { Pair(it.row, it.column) }
            return Schematic(tokens, tokenArray)
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
    fun neighborChunks(chunks: List<Schematic.Chunk>, neighborLocation: List<Pair<Int, Int>> ): List<Schematic.Chunk> {
         return neighborLocation.map {
            val (row, column) = it
            chunks.find { it.existsAtLocation(row, column) }
        }.filterNotNull().toSet().toList()
    }

    inner class Chunk(private val tokens: List<Token>) {
        fun neighbours(): List<Char> {
            val neighborPairs = tokens.flatMap { it.tokenNeighbors()}
                .toSet().minus(tokens.map { Pair(it.row, it.column) }.toSet())
           return neighborPairs.map { tokenMap[it]?.value?:'.' }
        }

        fun value(): String {
            return tokens.map { it.value }.joinToString("")
        }

        fun existsAtLocation(row: Int, column: Int): Boolean {
            return tokens.any {it.row == row && it.column == column}
        }
    }
}
