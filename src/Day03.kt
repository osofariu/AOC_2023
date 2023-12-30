class Day03(private val schematicRows: List<String>) {
    fun part1(): Int {
        return Schematic.from(schematicRows).chunks("\\d").fold(0) { acc, chunk: Schematic.Chunk ->
             if (chunk.neighbours().any { isSymbol(it) })
                acc + chunk.value()
            else
                acc
        }
    }
    private fun isSymbol(c: Char): Boolean {
        return (!Regex("\\d|\\w|\\.").matches(c.toString()))
    }
}
fun main(args: Array<String>) {
    val input = readInput("day03")
    println("part 1 input for " + input.size + " sum: " + Day03(input).part1() )
}
