package de.epex.pokerhands.service.model;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testCardParsingRegularValue() {
        Card result = new Card("S5");
        assertThat(result.getSuite(), is(equalTo("S")));
        assertThat(result.getValue(), is(equalTo(5)));
    }

    @Test
    public void testCardParsingTen() {
        Card result = new Card("S10");
        assertThat(result.getValue(), is(equalTo(10)));
    }

    @Test
    public void testCardParsingJack() {
        Card result = new Card("SJ");
        assertThat(result.getValue(), is(equalTo(11)));
    }

    @Test
    public void testCardParsingQueen() {
        Card result = new Card("SQ");
        assertThat(result.getValue(), is(equalTo(12)));
    }

    @Test
    public void testCardParsingKing() {
        Card result = new Card("SK");
        assertThat(result.getValue(), is(equalTo(13)));
    }

    @Test
    public void testCardParsingAce() {
        Card result = new Card("SA");
        assertThat(result.getValue(), is(equalTo(14)));
    }

    @Test
    public void testToString() {
        Card result = new Card("S5");
        assertThat(result.toString(), is(equalTo("S5")));
    }

    @Test
    public void testEquals() {
        Card result = new Card("S5");
        Card secondHard = new Card("S5");
        assertThat(result, is(equalTo(secondHard)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCardNotInDeck() {
        new Card("S1");
    }

    @Test
    public void testCardCompareToLess() {
        int result = new Card("S2").compareTo(new Card("S3"));
        assertThat(result, is(lessThan(0)));
    }

    @Test
    public void testCardCompareToEqual() {
        int result = new Card("S2").compareTo(new Card("D2"));
        assertThat(result, is(equalTo(0)));
    }

    @Test
    public void testCardCompareToGreater() {
        int result = new Card("S3").compareTo(new Card("S2"));
        assertThat(result, is(greaterThan(0)));
    }

}