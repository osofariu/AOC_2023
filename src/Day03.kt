class Day03(private val schematicRows: List<String>) {
    fun part1(): Int {
        return Schematic.from(schematicRows).chunks("\\d").fold(0) { acc, chunk: Schematic.Chunk ->
             if (chunk.neighbours().any { isSymbol(it) })
                acc + chunk.value().toInt()
            else
                acc
        }
    }
    private fun isSymbol(c: Char): Boolean {
        return (!Regex("\\d|\\w|\\.").matches(c.toString()))
    }

    fun part2(): Int {
        val schematic = Schematic.from(schematicRows)
        val chunks = schematic.chunks("\\d")

        return  schematic.tokens.filter { it.value().toString() == "*" }
            .map { it -> it.tokenNeighbors() }
            .map { neighborChunks(chunks, it) }
            .toSet()
            .fold(0) {acc: Int, chunksNearLocation: List<Schematic.Chunk> ->
                if (chunksNearLocation.size == 2) {
                    acc + (chunksNearLocation[0].value().toInt() * chunksNearLocation[1].value().toInt())
                } else {
                    acc
            }
        }
    }

    private fun neighborChunks(chunks: List<Schematic.Chunk>, neighborLocation: List<Pair<Int, Int>> ): List<Schematic.Chunk> {
        return neighborLocation.fold(emptySet<Schematic.Chunk>()){ acc: Set<Schematic.Chunk>, location: Pair<Int, Int> ->
            val chunkAtLocation = chunks.find {it.existsAtLocation(location.first, location.second) }
            if (chunkAtLocation != null)
                acc.plus(chunkAtLocation)
            else
                acc
        }.toList()
    }
}

fun main(args: Array<String>) {
    val input = readInput("day03")
    println("part 1 input for " + input.size + " sum: " + Day03(input).part1() )
    println("part 2 input for " + input.size + " sum: " + Day03(input).part2() )
}
