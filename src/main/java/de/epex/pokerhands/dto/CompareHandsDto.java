package de.epex.pokerhands.dto;

import java.io.Serializable;

public class CompareHandsDto implements Serializable {

    private String firstHand;

    private String secondHand;

    public String getFirstHand() {
        return firstHand;
    }

    public void setFirstHand(String firstHand) {
        this.firstHand = firstHand;
    }

    public String getSecondHand() {
        return secondHand;
    }

    public void setSecondHand(String secondHand) {
        this.secondHand = secondHand;
    }
}
