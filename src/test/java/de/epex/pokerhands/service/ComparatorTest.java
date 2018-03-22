package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class ComparatorTest {

    private final Comparator classUnderTest = new Comparator(new Ranker());

    @Test
    public void testCompareFirstHandWinsWithStraight() {
        Hand firstHand = new Hand("C5 D3 D4 S7 C6");
        Hand secondHand = new Hand("DA D3 D5 H8 S8");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(firstHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(firstHand)));
    }

    @Test
    public void testCompareHighCardDrawNextHighestCardWins() {
        Hand firstHand = new Hand("S4 D7 S9 HJ CA");
        Hand secondHand = new Hand("S3 H7 D9 DJ CK");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(firstHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(firstHand)));
    }

    @Test
    public void testCompareHighCardRealDraw() {
        Hand firstHand = new Hand("S3 D7 S9 HJ CA");
        Hand secondHand = new Hand("C3 H7 D9 DJ CA");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, nullValue());

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, nullValue());
    }

    @Test
    public void testComparePairDrawHigherPairWins() {
        Hand firstHand = new Hand("S3 C6 S7 HQ DQ");
        Hand secondHand = new Hand("S4 H4 D9 DJ CK");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(firstHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(firstHand)));
    }

    @Test
    public void testComparePairDrawNextHighestCard() {
        Hand firstHand = new Hand("S3 C6 S7 C2 D2");
        Hand secondHand = new Hand("S2 H2 D9 DJ C10");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(secondHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(secondHand)));
    }

    @Test
    public void testComparePairDrawRealDraw() {
        Hand firstHand = new Hand("S3 C6 S7 C2 D2");
        Hand secondHand = new Hand("S2 H2 D7 D6 C3");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, nullValue());

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, nullValue());
    }

    @Test
    public void testCompareTwoPairDrawHighestPairWins() {
        Hand firstHand = new Hand("S4 D8 H8 CJ SJ");
        Hand secondHand = new Hand("S3 H3 D9 DK CK");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(secondHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(secondHand)));
    }

    @Test
    public void testCompareTwoPairDrawSecondHighestPairWins() {
        Hand firstHand = new Hand("S4 D8 H8 CJ SJ");
        Hand secondHand = new Hand("S3 H3 D9 DJ HJ");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(firstHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(firstHand)));
    }

    @Test
    public void testCompareTwoPairDrawRemainingHighestCardWins() {
        Hand firstHand = new Hand("S10 H10 S7 H2 D2");
        Hand secondHand = new Hand("S2 H2 D9 D10 C10");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(secondHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(secondHand)));
    }

    @Test
    public void testCompareTwoPairDrawRealDraw() {
        Hand firstHand = new Hand("S3 C3 S7 H2 D2");
        Hand secondHand = new Hand("S2 H2 D9 D10 C10");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(secondHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(secondHand)));
    }

    @Test
    public void testCompareThreeOfAKindDraw() {
        Hand firstHand = new Hand("S3 C3 H3 S7 D6");
        Hand secondHand = new Hand("S4 C4 H4 S8 D7");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(secondHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(secondHand)));
    }

    @Test
    public void testCompareStraightDraw() {
        Hand firstHand = new Hand("S3 C4 H5 S6 D7");
        Hand secondHand = new Hand("D5 C6 H7 S8 D9");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(secondHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(secondHand)));
    }

    @Test
    public void testCompareFlushDraw() {
        Hand firstHand = new Hand("D3 D8 D10 D6 DK");
        Hand secondHand = new Hand("S5 S3 SQ S7 S9");

        Hand result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(firstHand)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(firstHand)));
    }

}