package de.epex.pokerhands.service.rankers;

import de.epex.pokerhands.service.model.Hand;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class HighCardRankerTest {

    HighCardRanker classUnderTest = new HighCardRanker();

    @Test
    public void matches() {
        assertThat(classUnderTest.matches(new Hand("C2 D5 SA D4 C9")), is(true));;
    }

    @Test
    public void compareHighCardDrawNextHighestCardWins() {
        Hand firstHand = new Hand("S4 D7 S9 HJ CA");
        Hand secondHand = new Hand("S3 H7 D9 DJ CK");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(greaterThan(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(lessThan(0)));
    }

    @Test
    public void compareHighCardRealDraw() {
        Hand firstHand = new Hand("S3 D7 S9 HJ CA");
        Hand secondHand = new Hand("C3 H7 D9 DJ CA");

        int result = classUnderTest.compare(firstHand, secondHand);
        assertThat(result, is(equalTo(0)));

        result = classUnderTest.compare(secondHand, firstHand);
        assertThat(result, is(equalTo(0)));
    }
}