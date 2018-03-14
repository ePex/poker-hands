package de.epex.pokerhands.service.model;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testCardParsingRegularValue() {
        Card result = new Card("S5");
        assertThat(result.getSuite(), is(equalTo("S")));
        assertThat(result.getValue(), is(equalTo(5)));
    }

    @Test
    public void testCardParsingJack() {
        Card result = new Card("SJ");
        assertThat(result.getValue(), is(equalTo(10)));
    }

    @Test
    public void testCardParsingQueen() {
        Card result = new Card("SQ");
        assertThat(result.getValue(), is(equalTo(11)));
    }

    @Test
    public void testCardParsingKing() {
        Card result = new Card("SK");
        assertThat(result.getValue(), is(equalTo(12)));
    }

    @Test
    public void testCardParsingAce() {
        Card result = new Card("SA");
        assertThat(result.getValue(), is(equalTo(13)));
    }

    @Test
    public void testToString() {
        Card result = new Card("S5");
        assertThat(result.toString(), is(equalTo("S5")));
    }

}