package de.epex.pokerhands.service.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DeckTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIsInDeck() {
        Deck.isInDeck(new Card("S1"));
    }

    @Test
    public void testDeckSizeIs52() {
        assertThat(Deck.getDeckSize(), is(equalTo(52)));
    }

}