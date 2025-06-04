package de.epex.pokerhands.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class HandComparatorTest {

    private HandComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new HandComparator();
    }

    // --- Tests for compare() method ---

    @Test
    void compare_firstHandWins_higherRank_returnsPositive() {
        Hand royalFlush = new Hand("AS KS QS JS TS"); // Royal Flush
        Hand straight = new Hand("2H 3C 4D 5S 6H");   // Straight
        assertTrue(comparator.compare(royalFlush, straight) > 0, "Royal Flush should beat Straight");
    }

    @Test
    void compare_secondHandWins_higherRank_returnsNegative() {
        Hand straight = new Hand("2H 3C 4D 5S 6H");   // Straight
        Hand royalFlush = new Hand("AS KS QS JS TS"); // Royal Flush
        assertTrue(comparator.compare(straight, royalFlush) < 0, "Straight should lose to Royal Flush");
    }

    @Test
    void compare_sameRank_tieBreakByRelevantValues_firstWins() {
        // Pair of Aces, King high kicker vs Pair of Aces, Queen high kicker
        Hand hand1 = new Hand("AS AH KS JS TH"); // Pair Aces, Kicker K
        Hand hand2 = new Hand("AD AC QS JS TH"); // Pair Aces, Kicker Q
        assertTrue(comparator.compare(hand1, hand2) > 0, "Pair Aces with King kicker should beat Pair Aces with Queen kicker");
    }

    @Test
    void compare_sameRank_tieBreakByRelevantValues_secondWins() {
        Hand hand1 = new Hand("AD AC QS JS TH"); // Pair Aces, Kicker Q
        Hand hand2 = new Hand("AS AH KS JS TH"); // Pair Aces, Kicker K
        assertTrue(comparator.compare(hand1, hand2) < 0, "Pair Aces with Queen kicker should lose to Pair Aces with King kicker");
    }

    @Test
    void compare_draw_returnsZero() {
        Hand hand1 = new Hand("AS KS QS JS TH"); // Ace high, Ten kicker
        Hand hand2 = new Hand("AD KD QD JD TC"); // Ace high, Ten kicker (different suits)
        assertEquals(0, comparator.compare(hand1, hand2), "Identical value hands should be a draw");
    }

    @Test
    void compare_fullHouse_higherTripletWins() {
        Hand hand1 = new Hand("AH AS AD KS KH"); // Aces full of Kings
        Hand hand2 = new Hand("KH KS KD AS AH"); // Kings full of Aces
        assertTrue(comparator.compare(hand1, hand2) > 0, "Aces full should beat Kings full");
    }

    @Test
    void compare_fullHouse_sameTriplet_higherPairWins() {
        Hand hand1 = new Hand("AH AS AD KS KH"); // Aces full of Kings
        Hand hand2 = new Hand("AC AD AH QS QH"); // Aces full of Queens
        assertTrue(comparator.compare(hand1, hand2) > 0, "Aces full of Kings should beat Aces full of Queens");
    }


    // --- Tests for determineWinner() method ---

    @Test
    void determineWinner_firstHandWins_correctOutcome() {
        Hand royalFlush = new Hand("AS KS QS JS TS"); // Royal Flush
        Hand straight = new Hand("2H 3C 4D 5S 6H");   // Straight

        ComparisonOutcome outcome = comparator.determineWinner(royalFlush, straight);

        assertEquals(Winner.FIRST_HAND, outcome.winner());
        assertEquals(Rank.ROYAL_FLUSH, outcome.firstHandRank());
        assertEquals(Rank.STRAIGHT, outcome.secondHandRank());
    }

    @Test
    void determineWinner_secondHandWins_correctOutcome() {
        Hand pairOfAces = new Hand("AS AH QS JS TH"); // Pair of Aces
        Hand twoPair = new Hand("KD KH QC QH JS");    // Kings and Queens

        ComparisonOutcome outcome = comparator.determineWinner(pairOfAces, twoPair);

        assertEquals(Winner.SECOND_HAND, outcome.winner());
        assertEquals(Rank.PAIR, outcome.firstHandRank());
        assertEquals(Rank.TWO_PAIRS, outcome.secondHandRank());
    }

    @Test
    void determineWinner_draw_correctOutcome() {
        Hand hand1 = new Hand("AS KS QS JS TH"); // Ace high
        Hand hand2 = new Hand("AD KD QD JD TC"); // Ace high (different suits)

        ComparisonOutcome outcome = comparator.determineWinner(hand1, hand2);

        assertEquals(Winner.DRAW, outcome.winner());
        assertEquals(Rank.STRAIGHT, outcome.firstHandRank()); // Corrected from HIGH_CARD
        assertEquals(Rank.STRAIGHT, outcome.secondHandRank()); // Corrected from HIGH_CARD
    }

    // Parameterized test for various comparisons
    @ParameterizedTest
    @CsvSource({
        "AS KS QS JS TS, 2H 3C 4D 5S 6H, FIRST_HAND, ROYAL_FLUSH, STRAIGHT", // Royal Flush vs Straight
        "2H 3C 4D 5S 6H, AS KS QS JS TS, SECOND_HAND, STRAIGHT, ROYAL_FLUSH", // Straight vs Royal Flush
        "AS AH KS QS JS, KD KH QC QH JC, SECOND_HAND, PAIR, TWO_PAIRS",      // Pair vs Two Pair
        "AS AH AD KS KH, KC KD KH QC QH, FIRST_HAND, FULL_HOUSE, FULL_HOUSE", // Corrected: Full House vs Full House
        "AS KS QS JS 9S, AD KD QD JD 8D, FIRST_HAND, FLUSH, FLUSH",          // Flush (Ace high) vs Flush (King high)
        "AS AH KS KH QH, AD AC KD KC QC, DRAW, TWO_PAIRS, TWO_PAIRS"       // Two Pair Draw (same kickers)
    })
    void determineWinner_parameterized(String hand1Str, String hand2Str, Winner expectedWinner, Rank rank1, Rank rank2) {
        Hand hand1 = new Hand(hand1Str);
        Hand hand2 = new Hand(hand2Str);

        ComparisonOutcome outcome = comparator.determineWinner(hand1, hand2);

        assertEquals(expectedWinner, outcome.winner());
        assertEquals(rank1, outcome.firstHandRank());
        assertEquals(rank2, outcome.secondHandRank());
    }
}
