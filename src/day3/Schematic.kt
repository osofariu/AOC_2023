package day3

class Schematic(val tokens: List<Token>, val tokenMap: Map<Pair<Int, Int>, Token>, val numericChunks: List<Chunk>) {
    companion object {
        fun from(schematicRows: List<String>): Schematic {
            val tokens = schematicRows.flatMapIndexed { rowIndex, s ->
                s.mapIndexed { colIndex, c -> Token(rowIndex, colIndex, c) }
            }
            val tokenMap = tokens.associateBy { Pair(it.row, it.column) }
            val numericChunks = numericChunks( tokens)
            return Schematic(tokens, tokenMap, numericChunks)
        }
        private fun numericChunks(tokens: List<Token>): List<Chunk> {

            fun tokenPartOfChunk(lastTokenSeen: Token, token: Token) =
                (lastTokenSeen.column == token.column - 1 && lastTokenSeen.row == token.row)

            val (allChunks, lastTokens) = tokens
                .filter { Regex("\\d").matches(it.value.toString()) }
                .fold(Pair(emptyList<Chunk>(), emptyList<Token>())) { acc, token ->
                    val (recordedChunks, currentTokens) = acc
                    if (currentTokens.isEmpty() || tokenPartOfChunk(currentTokens.last(), token)) {
                        Pair(recordedChunks, currentTokens.plus(token))
                    } else {
                        Pair(recordedChunks.plus(Chunk(currentTokens)), listOf(token))
                    }
                }
            return allChunks.plus(Chunk(lastTokens))
        }
    }

    fun neighborChunks(neighborLocation: List<Pair<Int, Int>> ): List<Chunk> {
         return neighborLocation.mapNotNull { locations ->
             numericChunks.find { it.existsAtLocation(locations.first, locations.second) }
         }.toSet().toList()
    }

}
