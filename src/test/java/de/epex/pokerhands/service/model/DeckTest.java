package de.epex.pokerhands.service.model;

import org.junit.Test;

public class DeckTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIsInDeck() {
        Deck.isInDeck(new Card("S1"));
    }

}