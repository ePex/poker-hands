package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class StraightFlushRankerTest {

    private StraightRanker classUnderTest = new StraightRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("S3 S4 S5 S6 S7");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void testStraightFlushDraw() {
        Hand firstHand = new Hand("D3 D4 D5 D6 D7");
        Hand secondHand = new Hand("S5 S6 S7 S8 S9");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

}