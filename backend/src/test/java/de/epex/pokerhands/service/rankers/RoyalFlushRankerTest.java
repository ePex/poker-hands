package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class RoyalFlushRankerTest {

    private RoyalFlushRanker classUnderTest = new RoyalFlushRanker();

    @Test
    public void matches() {
        Hand hand = new Hand("H10 HJ HQ HK HA");

        assertThat(classUnderTest.matches(hand), is(true));
    }

}