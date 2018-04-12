package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FourOfAKindRankerTest {

    private FourOfAKindRanker classUnderTest = new FourOfAKindRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("H2 SJ HJ CJ DJ");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void testCompareFourOfAKindDraw() {
        Hand firstHand = new Hand("D3 C3 H3 S3 DK");
        Hand secondHand = new Hand("S5 C5 D5 H5 C7");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(lessThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(greaterThan(0)));
    }

}