package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class ThreeOfAKindRankerTest {

    private ThreeOfAKindRanker classUnderTest = new ThreeOfAKindRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("H4 S2 HA SA DA");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void testCompareThreeOfAKindDraw() {
        Hand firstHand = new Hand("S3 C3 H3 S7 D6");
        Hand secondHand = new Hand("S4 C4 H4 S8 D7");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

}