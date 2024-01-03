package day5

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RangeMapTest {

    @Test
    fun `given simple range, generate a map`() {
        val range = RangeMap(50, 100, 12)
        expectThat(range.lookup(1)).isEqualTo(1)
        expectThat(range.lookup(99)).isEqualTo(99)
        expectThat(range.lookup(100)).isEqualTo(50)
        expectThat(range.lookup(111)).isEqualTo(61)
        expectThat(range.lookup(112)).isEqualTo(112)
    }

    @Test
    fun `lookup handles multiple maps correctly`() {
        val rangeMaps = listOf(
            RangeMap(50, 98, 2),
            RangeMap(52, 50, 48))

        expectThat(lookup(0, rangeMaps)).isEqualTo(0)
        expectThat(lookup(49, rangeMaps)).isEqualTo(49)
        expectThat(lookup(50, rangeMaps)).isEqualTo(52)
        expectThat(lookup(96, rangeMaps)).isEqualTo(98)
        expectThat(lookup(97, rangeMaps)).isEqualTo(99)
        expectThat(lookup(98, rangeMaps)).isEqualTo(50)
        expectThat(lookup(99, rangeMaps)).isEqualTo(51)
    }
}
