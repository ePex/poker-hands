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
    }

    @Test
    public void testCompareHighCardDraw() {
        Hand firstHand = new Hand("S4 D7 S9 HJ CA");
        Hand secondHand = new Hand("S3 H7 D9 DJ CK");

        Hand result = classUnderTest.compare(firstHand, secondHand);

        assertThat(result, is(equalTo(firstHand)));
    }

    @Test
    public void testCompareHighCardRealDraw() {
        Hand firstHand = new Hand("S3 D7 S9 HJ CA");
        Hand secondHand = new Hand("C3 H7 D9 DJ CA");

        Hand result = classUnderTest.compare(firstHand, secondHand);

        assertThat(result, nullValue());
    }

    @Test
    public void testComparePairDraw() {
        Hand firstHand = new Hand("S3 C6 S7 HQ DQ");
        Hand secondHand = new Hand("S4 H4 D9 DJ CK");

        Hand result = classUnderTest.compare(firstHand, secondHand);

        assertThat(result, is(equalTo(firstHand)));
    }

    @Test
    public void testCompareTwoPairDraw() {
        Hand firstHand = new Hand("S4 D8 H8 CJ SJ");




        Hand secondHand = new Hand("S3 H7 D9 DJ CK");

        Hand result = classUnderTest.compare(firstHand, secondHand);

        assertThat(result, is(equalTo(firstHand)));
    }



}