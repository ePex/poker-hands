package de.epex.pokerhands.service.model;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class HandTest {

    @Test
    public void testHandParsing() {
        Hand result = new Hand("C5 D3 D4 S7 C6");
        assertThat(result.getCards(), hasSize(5));
        assertThat(result.getCards(), hasItem(new Card("C5")));
        assertThat(result.getCards(), hasItem(new Card("D3")));
        assertThat(result.getCards(), hasItem(new Card("D4")));
        assertThat(result.getCards(), hasItem(new Card("S7")));
        assertThat(result.getCards(), hasItem(new Card("C6")));
    }

    @Test
    public void testHandIsSorted() {
        Hand result = new Hand("C5 D3 D4 S7 C6");
        assertThat(result.getCards().get(0).getValue(), is(equalTo(3)));
        assertThat(result.getCards().get(1).getValue(), is(equalTo(4)));
        assertThat(result.getCards().get(2).getValue(), is(equalTo(5)));
        assertThat(result.getCards().get(3).getValue(), is(equalTo(6)));
        assertThat(result.getCards().get(4).getValue(), is(equalTo(7)));
    }

    @Test
    public void testToString() {
        Hand result = new Hand("C5 D3 D4 S7 C6");
        assertThat(result.toString(), is(equalTo("D3 D4 C5 C6 S7 ")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandToShort() {
        new Hand("C5 D3 D4 S7");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandToBig() {
        new Hand("C5 D3 D4 S7");
    }

    @Test
    public void testGetPairsNoPair() {
        Hand hand = new Hand("S3 C6 S7 HQ DK");
        Map<Integer, Long> result = hand.getCardsWithSameValue();

        int count = getPairOccurrenceCount(result);
        assertThat(count, is(equalTo(0)));
    }

    @Test
    public void testGetPairsOnePair() {
        Hand hand = new Hand("S3 C6 S7 HQ DQ");
        Map<Integer, Long> result = hand.getCardsWithSameValue();

        int count = getPairOccurrenceCount(result);
        assertThat(count, is(equalTo(1)));
    }

    @Test
    public void testGetPairsTwoPair() {
        Hand hand = new Hand("S6 C6 S7 HQ DQ");
        Map<Integer, Long> result = hand.getCardsWithSameValue();

        int count = getPairOccurrenceCount(result);
        assertThat(count, is(equalTo(2)));
    }

    private int getPairOccurrenceCount(Map<Integer, Long> result) {
        return (int) result.values().stream()
                    .filter(cardValueOccurrenceCount -> cardValueOccurrenceCount == 2)
                    .count();
    }

}