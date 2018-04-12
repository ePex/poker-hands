package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FlushRankerTest {

    private FlushRanker classUnderTest = new FlushRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("D3 D6 D9 DQ DK");

        assertThat(classUnderTest.matches(hand), is(true));
    }

    @Test
    public void testCompareFlushDraw() {
        Hand firstHand = new Hand("D3 D8 D10 D6 DK");
        Hand secondHand = new Hand("S5 S3 SQ S7 S9");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(greaterThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(lessThan(0)));
    }

}