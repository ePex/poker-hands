package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import org.springframework.stereotype.Service;

@Service
public class Ranker {

    public Rank getRank(Hand hand) {
        if (hand.hasRoyalFlush()) {
            return Rank.ROYAL_FLUSH;
        }
        if (hand.hasStraightFlush()) {
            return Rank.STRAIGHT_FLUSH;
        }
        if (hand.hasFourOfAKind()) {
            return Rank.FOUR_OF_A_KIND;
        }
        if (hand.hasFullHouse()) {
            return Rank.FULL_HOUSE;
        }
        if (hand.hasFlush()) {
            return Rank.FLUSH;
        }
        if (hand.hasStraight()) {
            return Rank.STRAIGHT;
        }
        if (hand.hasThreeOfAKind()) {
            return Rank.THREE_OF_A_KIND;
        }
        if (hand.hasTwoPair()) {
            return Rank.TWO_PAIRS;
        }
        if (hand.hasPair()) {
            return Rank.PAIR;
        }

        return Rank.HIGH_CARD;
    }

}
