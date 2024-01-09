package day7

import day7.types.Hand
import day7.types.Hand.HandType
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class HandTest {
    @Test
    fun `hand types sanity check`() {
        expectThat(Hand("22222").handType()).isEqualTo(HandType.FIVE_OF_A_KIND)
        expectThat(Hand("AAAAA").handType()).isEqualTo(HandType.FIVE_OF_A_KIND)
        expectThat(Hand("AA8AA").handType()).isEqualTo(HandType.FOUR_OF_A_KIND)
        expectThat(Hand("23332").handType()).isEqualTo(HandType.FULL_HOUSE)
        expectThat(Hand("TTT98").handType()).isEqualTo(HandType.THREE_OF_A_KIND)
        expectThat(Hand("23432").handType()).isEqualTo(HandType.TWO_PAIR)
        expectThat(Hand("23456").handType()).isEqualTo(HandType.HIGH_CARD)
    }
}
