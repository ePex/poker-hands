package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Card;
import de.epex.pokerhands.service.model.Hand;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Ranker {

    public Rank getRank(Hand hand) {
        if (isRoyalFlush(hand)) {
            return Rank.ROYAL_FLUSH;
        }
        if (isStraightFlush(hand)) {
            return Rank.STRAIGHT_FLUSH;
        }
        if (isFourOfAKind(hand)) {
            return Rank.FOUR_OF_A_KIND;
        }
        if (isFullHouse(hand)) {
            return Rank.FULL_HOUSE;
        }
        if (isFlush(hand)) {
            return Rank.FLUSH;
        }
        if (isStraight(hand)) {
            return Rank.STRAIGHT;
        }
        if (isThreeOfAKind(hand)) {
            return Rank.THREE_OF_A_KIND;
        }
        if (isTwoPair(hand)) {
            return Rank.TWO_PAIRS;
        }
        if (isPair(hand)) {
            return Rank.PAIR;
        }

        return Rank.HIGH_CARD;
    }

    private boolean isRoyalFlush(Hand hand) {
        List<Card> cards = hand.getCards();
        Card referenceCard = new Card("DA");
        Card highestCardFromHand = cards.get(cards.size() - 1);

        return isStraightFlush(hand) && referenceCard.getValue() == highestCardFromHand.getValue();
    }

    private boolean isStraightFlush(Hand hand) {
        return isStraight(hand) && isFlush(hand);
    }

    private boolean isFourOfAKind(Hand hand) {
        return hasCountOfAKind(hand, 4);
    }

    private boolean isFullHouse(Hand hand) {
        return hasCountOfAKind(hand,2) && hasCountOfAKind(hand,3);
    }

    private boolean isFlush(Hand hand) {
        List<Card> cards = hand.getCards();
        String suite = null;
        for (Card card : cards) {
            if (suite == null || suite.equalsIgnoreCase(card.getSuite())) {
                suite = card.getSuite();
            } else {
                return false;
            }
        }

        return true;
    }

    private boolean isStraight(Hand hand) {
        List<Card> cards = hand.getCards();
        Card previousCard = null;
        int count = 0;
        for (Card card : cards) {
            if (previousCard == null || (previousCard.getValue() == card.getValue() - 1)) {
                previousCard = card;
                count++;
            } else {
                return false;
            }
        }

        return count == 5;
    }

    private boolean isThreeOfAKind(Hand hand) {
        return hasCountOfAKind(hand, 3);
    }

    private boolean hasCountOfAKind(Hand hand, int count) {
        List<Integer> cards = hand.getCards().stream().map(Card::getValue).collect(Collectors.toList());
        Set<Integer> uniqueSet = new HashSet<>(cards);

        return uniqueSet.stream().anyMatch(temp -> Collections.frequency(cards, temp) == count);
    }

    private boolean isTwoPair(Hand hand) {
        return getPairCount(hand) == 2;
    }

    private boolean isPair(Hand hand) {
        return getPairCount(hand) == 1;
    }

    private int getPairCount(Hand hand) {
        Set<Integer> pairs = hand.getPairs();

        return pairs.size();
    }

}
