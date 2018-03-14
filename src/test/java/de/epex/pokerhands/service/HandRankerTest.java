package de.epex.pokerhands.service;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class HandRankerTest {

    private final HandRanker classUnderTest = new HandRanker();

    @Test
    public void testGetRankHighCard() {
        Rank result = classUnderTest.getRank("S4 D7 S9 HJ CK");
        assertThat(result, is(equalTo(Rank.HIGH_CARD)));
    }

    @Test
    public void testGetRankPair() {
        Rank result = classUnderTest.getRank("S3 C6 S7 HQ DQ");
        assertThat(result, is(equalTo(Rank.PAIR)));
    }

    @Test
    public void testGetRankTwoPairs() {
        Rank result = classUnderTest.getRank("S4 D8 H8 CJ SJ");
        assertThat(result, is(equalTo(Rank.TWO_PAIRS)));
    }

    @Test
    public void testGetRankThreeOfAKind() {
        Rank result = classUnderTest.getRank("H4 S2 HA SA DA");
        assertThat(result, is(equalTo(Rank.THREE_OF_A_KIND)));
    }

    @Test
    public void testGetRankStraight() {
        //Rank result = classUnderTest.getRank("S3 D4 S5 H6 C7");
        //assertThat(result, is(equalTo(Rank.STRAIGHT)));
    }

    @Test
    public void testGetRankFlush() {
        fail();
    }

    @Test
    public void testGetRankFullHouse() {
        Rank result = classUnderTest.getRank("S2 H2 CQ HQ SQ");
        assertThat(result, is(equalTo(Rank.FULL_HOUSE)));
    }

    @Test
    public void testGetRankFourOfAKind() {
        Rank result = classUnderTest.getRank("H2 SJ HJ CJ DJ");
        assertThat(result, is(equalTo(Rank.FOUR_OF_A_KIND)));
    }

    @Test
    public void testGetRankStraightFlush() {
        fail();
    }

    // royal flush?

    // kicker?
}