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
        fail();
    }

    @Test
    public void testGetRankThreeOfAKind() {
        fail();
    }

    @Test
    public void testGetRankStraight() {
        fail();
    }

    @Test
    public void testGetRankFlush() {
        fail();
    }

    @Test
    public void testGetRankFullHouse() {
        fail();
    }

    @Test
    public void testGetRankFourOfAKind() {
        fail();
    }

    @Test
    public void testGetRankStraightFlush() {
        fail();
    }

    // royal flush?

    // kicker?
}