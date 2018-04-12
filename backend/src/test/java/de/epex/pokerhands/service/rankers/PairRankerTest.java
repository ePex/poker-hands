package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class PairRankerTest {

    private PairRanker classUnderTest = new PairRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("S3 C6 S7 HQ DQ");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void comparePairDrawHigherPairWins() {
        Hand firstHand = new Hand("S3 C6 S7 HQ DQ");
        Hand secondHand = new Hand("S4 H4 D9 DJ CK");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(greaterThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(lessThan(0)));
    }

    @Test
    public void comparePairDrawNextHighestCard() {
        Hand firstHand = new Hand("S3 C6 S7 C2 D2");
        Hand secondHand = new Hand("S2 H2 D9 DJ C10");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

    @Test
    public void comparePairDrawRealDraw() {
        Hand firstHand = new Hand("S3 C6 S7 C2 D2");
        Hand secondHand = new Hand("S2 H2 D7 D6 C3");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(0)));
    }
}