package de.epex.pokerhands.service.model;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class HandTest {

    private Hand validHand = new Hand("C5 D3 D4 S7 C6");

    @Test
    public void testHandParsing() {
        assertThat(validHand.getCards(), hasSize(5));
        assertThat(validHand.getCards(), hasItem(new Card("C5")));
        assertThat(validHand.getCards(), hasItem(new Card("D3")));
        assertThat(validHand.getCards(), hasItem(new Card("D4")));
        assertThat(validHand.getCards(), hasItem(new Card("S7")));
        assertThat(validHand.getCards(), hasItem(new Card("C6")));
    }

    @Test
    public void testHandIsSorted() {
        assertThat(validHand.getCards().get(0).getValue(), is(equalTo(3)));
        assertThat(validHand.getCards().get(1).getValue(), is(equalTo(4)));
        assertThat(validHand.getCards().get(2).getValue(), is(equalTo(5)));
        assertThat(validHand.getCards().get(3).getValue(), is(equalTo(6)));
        assertThat(validHand.getCards().get(4).getValue(), is(equalTo(7)));
    }

    @Test
    public void testToString() {
        assertThat(validHand.toString(), is(equalTo("D3 D4 C5 C6 S7 ")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandToShort() {
        new Hand("C5 D3 D4 S7");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandToBig() {
        new Hand("C5 D3 D4 S7 D5 SA");
    }

    @Test
    public void testGetHighCard() {
        Card highCard = validHand.getHighCard();
        assertThat(highCard, is(equalTo(new Card("S7"))));
    }

    @Test
    public void testHasPair() {
        Hand hand = new Hand("S3 C6 S7 HQ DQ");

        assertThat(hand.hasPair(), is(true));
    }

    @Test
    public void testHasTwoPair() {
        Hand hand = new Hand("S4 D8 H8 CJ SJ");

        assertThat(hand.hasTwoPair(), is(true));
    }

    @Test
    public void testHasThreeOfAKind() {
        Hand hand = new Hand("H4 S2 HA SA DA");

        assertThat(hand.hasThreeOfAKind(), is(true));
    }

    @Test
    public void testHasStraight() {
        Hand hand = new Hand("S3 D4 S5 H6 C7");

        assertThat(hand.hasStraight(), is(true));
    }

    @Test
    public void testHasStraightWithSymbols() {
        Hand hand = new Hand("S8 D9 S10 HJ CQ");

        assertThat(hand.hasStraight(), is(true));
    }

    @Test
    public void testHasFlush() {
        Hand hand = new Hand("D3 D6 D9 DQ DK");

        assertThat(hand.hasFlush(), is(true));
    }

    @Test
    public void testHasFullHouse() {
        Hand hand = new Hand("S2 H2 CQ HQ SQ");

        assertThat(hand.hasFullHouse(), is(true));
    }

    @Test
    public void testHasFourOfAKind() {
        Hand hand = new Hand("H2 SJ HJ CJ DJ");

        assertThat(hand.hasFourOfAKind(), is(true));
    }

    @Test
    public void testHasStraightFlush() {
        Hand hand = new Hand("S3 S4 S5 S6 S7");

        assertThat(hand.hasStraightFlush(), is(true));
    }

    @Test
    public void testHasRoyalFlush() {
        Hand hand = new Hand("H10 HJ HQ HK HA");

        assertThat(hand.hasRoyalFlush(), is(true));
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