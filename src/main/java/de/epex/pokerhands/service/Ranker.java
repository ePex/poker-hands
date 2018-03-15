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

        if (isStraight(hand) && isFlush(hand)) {
            List<Card> cards = hand.getCards();
            if (new Card("DA").getValue() == cards.get(cards.size() - 1).getValue()) {
                return Rank.ROYAL_FLUSH;
            }
            return Rank.STRAIGHT_FLUSH;
        }

        if (hasCountOfAKind(hand, 4)) {
            return Rank.FOUR_OF_A_KIND;
        }

        if (hasCountOfAKind(hand,2) && hasCountOfAKind(hand,3)) {
            return Rank.FULL_HOUSE;
        }

        if (isFlush(hand)) {
            return Rank.FLUSH;
        }

        if (isStraight(hand)) {
            return Rank.STRAIGHT;
        }

        if (hasCountOfAKind(hand, 3)) {
            return Rank.THREE_OF_A_KIND;
        }

        int pairCount = getPairCount(hand);
        if (pairCount == 2) {
            return Rank.TWO_PAIRS;
        }
        if (pairCount == 1) {
            return Rank.PAIR;
        }

        return Rank.HIGH_CARD;
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

    private boolean hasCountOfAKind(Hand hand, int count) {
        List<Integer> cards = hand.getCards().stream().map(Card::getValue).collect(Collectors.toList());
        Set<Integer> uniqueSet = new HashSet<>(cards);
        for (Integer temp : uniqueSet) {
            if (Collections.frequency(cards, temp) == count) {
                return true;
            }
        }

        return false;
    }

    private int getPairCount(Hand hand) {
        List<Integer> cards = hand.getCards().stream().map(Card::getValue).collect(Collectors.toList());
        Set<Integer> allItems = new HashSet<>();
        Set<Integer> duplicates = cards.stream()
                .filter(n -> !allItems.add(n)) //Set.add() returns false if the item was already in the set.
                .collect(Collectors.toSet());

        return duplicates.size();
    }

}
