package de.epex.pokerhands.domain;

import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import org.junit.jupiter.api.Test; // JUnit 5
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*; // JUnit 5 Assertions

class HandTest { // Class can be package-private or public for JUnit 5

    @Test
    void constructor_validHand_parsesAndSortsCards() {
        Hand hand = new Hand("C5 D3 D4 S7 C6");
        assertEquals(5, hand.getCards().size());
        // Cards are sorted by value: 3, 4, 5, 6, 7
        assertEquals(3, hand.getCards().get(0).value());
        assertEquals(4, hand.getCards().get(1).value());
        assertEquals(5, hand.getCards().get(2).value());
        assertEquals(6, hand.getCards().get(3).value());
        assertEquals(7, hand.getCards().get(4).value());
    }

    @Test
    void constructor_validHand_evaluatesRank() {
        Hand hand = new Hand("C5 D3 D4 S7 C6"); // Straight 3-7
        assertNotNull(hand.getEvaluationResult());
        assertEquals(Rank.STRAIGHT, hand.getEvaluationResult().rank());
        assertEquals(List.of(7), hand.getEvaluationResult().relevantCardValues()); // Highest card in straight
    }

    @Test
    void toString_returnsCorrectFormat() {
        Hand hand = new Hand("C5 D3 D4 S7 C6");
        // Record's default toString is "Card[suite=C, value=5]"
        String expectedString = "Card[suite=D, value=3] Card[suite=D, value=4] Card[suite=C, value=5] Card[suite=C, value=6] Card[suite=S, value=7]";
        assertEquals(expectedString, hand.toString());
    }

    // Constructor Validation Tests
    @Test
    void constructor_nullHandString_throwsInvalidPokerHandException() {
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> new Hand(null));
        assertEquals("Hand string cannot be null or empty.", exception.getMessage());
    }

    @Test
    void constructor_emptyHandString_throwsInvalidPokerHandException() {
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> new Hand("   "));
        assertEquals("Hand string cannot be null or empty.", exception.getMessage());
    }

    @Test
    void constructor_tooFewCards_throwsInvalidPokerHandException() {
        String handStr = "AS KS QS JS";
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> new Hand(handStr));
        assertTrue(exception.getMessage().contains("Hand must contain exactly 5 cards"));
    }

    @Test
    void constructor_tooManyCards_throwsInvalidPokerHandException() {
        String handStr = "AS KS QS JS TS 2C";
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> new Hand(handStr));
        assertTrue(exception.getMessage().contains("Hand must contain exactly 5 cards"));
    }

    @Test
    void constructor_invalidCardString_throwsInvalidPokerHandException() {
        String handStr = "AS KS QS JS XX"; // XX is invalid
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> new Hand(handStr));
        assertTrue(exception.getMessage().contains("Invalid card in hand: 'XX'"));
    }

    @Test
    void constructor_duplicateCards_throwsInvalidPokerHandException() {
        String handStr = "AS KS QS JS AS"; // AS is duplicated
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> new Hand(handStr));
        assertTrue(exception.getMessage().contains("Hand must contain 5 distinct cards"));
    }

    // Rank Determination Tests (migrated from RankerTest and adapted)
    @Test
    void getEvaluationResult_royalFlush() {
        Hand hand = new Hand("AS KS QS JS TS");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.ROYAL_FLUSH, result.rank());
        assertEquals(List.of(14), result.relevantCardValues(), "Royal Flush: Ace high");
    }

    @Test
    void getEvaluationResult_straightFlush_kingHigh() {
        Hand hand = new Hand("KS QS JS TS 9S");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.STRAIGHT_FLUSH, result.rank());
        assertEquals(List.of(13), result.relevantCardValues(), "Straight Flush: King high");
    }

    @Test
    void getEvaluationResult_aceLowStraightFlush() {
        Hand hand = new Hand("AS 2S 3S 4S 5S");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.STRAIGHT_FLUSH, result.rank());
        assertEquals(List.of(5), result.relevantCardValues(), "Ace-low Straight Flush: 5 high");
    }

    @Test
    void getEvaluationResult_fourOfAKind() {
        Hand hand = new Hand("AH AS AD AC KH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.FOUR_OF_A_KIND, result.rank());
        assertEquals(List.of(14, 13), result.relevantCardValues(), "Four Aces, King kicker");
    }

    @Test
    void getEvaluationResult_fourOfAKind_lowerKicker() {
        Hand hand = new Hand("KH KS KD KC AH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.FOUR_OF_A_KIND, result.rank());
        assertEquals(List.of(13, 14), result.relevantCardValues(), "Four Kings, Ace kicker");
    }

    @Test
    void getEvaluationResult_fullHouse() {
        Hand hand = new Hand("AH AS AD KS KH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.FULL_HOUSE, result.rank());
        assertEquals(List.of(14, 13), result.relevantCardValues(), "Aces over Kings");
    }

    @Test
    void getEvaluationResult_fullHouse_kingsOverAces() {
        Hand hand = new Hand("KH KS KD AS AH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.FULL_HOUSE, result.rank());
        assertEquals(List.of(13, 14), result.relevantCardValues(), "Kings over Aces");
    }

    @Test
    void getEvaluationResult_flush() {
        Hand hand = new Hand("AS KS QS JS 8S");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.FLUSH, result.rank());
        assertEquals(List.of(14, 13, 12, 11, 8), result.relevantCardValues(), "Ace high flush");
    }

    @Test
    void getEvaluationResult_straight_aceHigh() {
        Hand hand = new Hand("AS KC QD JS TH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.STRAIGHT, result.rank());
        assertEquals(List.of(14), result.relevantCardValues(), "Ace high straight");
    }

    @Test
    void getEvaluationResult_straight_aceLow() {
        Hand hand = new Hand("AS 2C 3D 4S 5H");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.STRAIGHT, result.rank());
        assertEquals(List.of(5), result.relevantCardValues(), "Ace low straight (5 high)");
    }

    @Test
    void getEvaluationResult_threeOfAKind() {
        Hand hand = new Hand("AH AS AD KS QH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.THREE_OF_A_KIND, result.rank());
        assertEquals(List.of(14, 13, 12), result.relevantCardValues(), "Three Aces, K Q kickers");
    }

    @Test
    void getEvaluationResult_twoPairs() {
        Hand hand = new Hand("AH AS KS KH QH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.TWO_PAIRS, result.rank());
        assertEquals(List.of(14, 13, 12), result.relevantCardValues(), "Aces and Kings, Q kicker");
    }

    @Test
    void getEvaluationResult_twoPairs_lower() {
        Hand hand = new Hand("5H 5S KS KH QH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.TWO_PAIRS, result.rank());
        assertEquals(List.of(13, 5, 12), result.relevantCardValues(), "Kings and Fives, Q kicker");
    }


    @Test
    void getEvaluationResult_pair() {
        Hand hand = new Hand("AH AS KS QS JH");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.PAIR, result.rank());
        assertEquals(List.of(14, 13, 12, 11), result.relevantCardValues(), "Pair of Aces, K Q J kickers");
    }

    @Test
    void getEvaluationResult_highCard() {
        Hand hand = new Hand("AS KC QD JS 9H");
        HandEvaluationResult result = hand.getEvaluationResult();
        assertEquals(Rank.HIGH_CARD, result.rank());
        assertEquals(List.of(14, 13, 12, 11, 9), result.relevantCardValues(), "Ace high");
    }

    // Tests for getCardsInGroups (previously getCardsWithSameValue)
    @Test
    void getCardsInGroups_noGroups() {
        Hand hand = new Hand("S3 C6 S7 HQ DK"); // No pairs or groups
        Map<Integer, Long> result = hand.getCardsInGroups();
        assertTrue(result.isEmpty(), "Should be empty if no groups of 2 or more");
    }

    @Test
    void getCardsInGroups_onePair() {
        Hand hand = new Hand("S3 C6 S7 HQ DQ"); // Pair of Queens
        Map<Integer, Long> result = hand.getCardsInGroups();
        assertEquals(1, result.size());
        assertEquals(2L, result.get(12)); // Queen's value is 12
    }

    @Test
    void getCardsInGroups_twoPair() {
        Hand hand = new Hand("S6 C6 S7 HQ DQ"); // Pair of Sixes, Pair of Queens
        Map<Integer, Long> result = hand.getCardsInGroups();
        assertEquals(2, result.size());
        assertEquals(2L, result.get(6));
        assertEquals(2L, result.get(12));
    }

    @Test
    void getCardsInGroups_threeOfAKind() {
        Hand hand = new Hand("S6 C6 H6 S7 DQ"); // Three Sixes
        Map<Integer, Long> result = hand.getCardsInGroups();
        assertEquals(1, result.size());
        assertEquals(3L, result.get(6));
    }
}
