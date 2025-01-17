package day5

class RangeMap(private val destinationStart: Long, private val sourceStart: Long, private val rangeLength: Long) {
    public fun lookup(key: Long): Long {
        return if (key >= sourceStart && key < sourceStart + rangeLength) {
            key - sourceStart + destinationStart
        } else {
            key
        }
    }
}

fun lookup(key: Long, rangeMaps: List<RangeMap>): Long =
    rangeMaps.map { range -> range.lookup(key) }
        .firstOrNull() { value -> value != key } ?: key
