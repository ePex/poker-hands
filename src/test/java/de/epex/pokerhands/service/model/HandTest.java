package de.epex.pokerhands.service.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class HandTest {

    @Test
    public void testHandParsing() {

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

}