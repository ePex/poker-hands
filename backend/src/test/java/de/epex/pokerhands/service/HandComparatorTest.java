package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import de.epex.pokerhands.service.rankers.Ranker;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.is;

public class HandComparatorTest {

    private final HandComparator classUnderTest = new HandComparator();

    @Test
    public void testCompareFirstHandWinsWithStraight() {
        Hand firstHand = new Hand("C5 D3 D4 S7 C6");
        Hand secondHand = new Hand("DA D3 D5 H8 S8");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(greaterThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(lessThan(0)));
    }

    @Test
    public void testCompareDraw() {
        Hand firstHand = new Hand("C3 D4 D5 S6 C7");
        Hand secondHand = new Hand("D3 C4 C5 H6 S7");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(0)));
    }

    @Test
    public void testHighCardRanker() {
        Hand firstHand = new Hand("C3 D8 D5 S6 CA");
        Hand secondHand = new Hand("D3 C9 C5 H6 SK");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(greaterThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(lessThan(0)));
    }

}