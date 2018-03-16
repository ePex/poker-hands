package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Card;
import de.epex.pokerhands.service.model.Hand;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class Comparator {

    private final Ranker ranker;

    public Comparator(Ranker ranker) {
        this.ranker = ranker;
    }

    /**
     *
     * @param firstHand
     * @param secondHand
     * @return null on draw otherwise winning hand
     */
    public Hand compare(Hand firstHand, Hand secondHand) {
        int valueFirstHand = ranker.getRank(firstHand).getValue();
        int valueSecondHand = ranker.getRank(secondHand).getValue();

        if (valueFirstHand == valueSecondHand) {
            //pair draw
            Set<Integer> firstHandPairs = firstHand.getPairs();
            Set<Integer> secondHandPairs = secondHand.getPairs();
            valueFirstHand = firstHandPairs.stream().mapToInt(Integer::intValue).sum();
            valueSecondHand = secondHandPairs.stream().mapToInt(Integer::intValue).sum();

            //if equal -> compare remaining by excluding pair and looping backwards from highest value*/

            if (valueFirstHand == valueSecondHand) {
                // high card draw
                int index = 5;
                while (index > 0) {
                    index--;
                    valueFirstHand = firstHand.getCards().get(index).getValue();
                    valueSecondHand = secondHand.getCards().get(index).getValue();
                    if (firstHand.getCards().get(index).getValue() != secondHand.getCards().get(index).getValue()) {
                        break;
                    }
                }
            }
        }
        return valueFirstHand == valueSecondHand ? null : valueFirstHand > valueSecondHand ? firstHand : secondHand;
    }

}
