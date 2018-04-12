package de.epex.pokerhands.service;

import de.epex.pokerhands.service.model.Hand;
import de.epex.pokerhands.service.rankers.FlushRanker;
import de.epex.pokerhands.service.rankers.FourOfAKindRanker;
import de.epex.pokerhands.service.rankers.FullHouseRanker;
import de.epex.pokerhands.service.rankers.HighCardRanker;
import de.epex.pokerhands.service.rankers.PairRanker;
import de.epex.pokerhands.service.rankers.Ranker;
import de.epex.pokerhands.service.rankers.RoyalFlushRanker;
import de.epex.pokerhands.service.rankers.StraightFlushRanker;
import de.epex.pokerhands.service.rankers.StraightRanker;
import de.epex.pokerhands.service.rankers.ThreeOfAKindRanker;
import de.epex.pokerhands.service.rankers.TwoPairRanker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class HandComparator implements Comparator<Hand> {

    private final List<Ranker> rankers = Arrays.asList(
            new RoyalFlushRanker(),
            new StraightFlushRanker(),
            new FourOfAKindRanker(),
            new FullHouseRanker(),
            new FlushRanker(),
            new StraightRanker(),
            new ThreeOfAKindRanker(),
            new TwoPairRanker(),
            new PairRanker()
    );

    /**
     *
     * @param firstHand
     * @param secondHand
     * @return zero on draw negative value if first hand is less then second hand and positive value if first hand is higher then second hand
     */
    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        Ranker ranker = getRanker(firstHand, secondHand);

        if (ranker.matches(firstHand) && ranker.matches(secondHand)) {
            return ranker.compare(firstHand, secondHand);
        } else if (ranker.matches(firstHand)) {
            return 1;
        } else {
            return -1;
        }
    }

    private Ranker getRanker(Hand firstHand, Hand secondHand) {
        for (Ranker ranker : rankers) {
            if (ranker.matches(firstHand) || ranker.matches(secondHand)) {
                return ranker;
            }
        }
        return new HighCardRanker();
    }

    public String compareAndGetResultMessage(Hand firstHand, Hand secondHand) {
        int compareResult = compare(firstHand, secondHand);

        return compareResult == 0 ? "It's a draw!" :
                compareResult > 0 ? String.format("First hand wins! (%s)", firstHand.getRank().getName())
                        : String.format("Second hand wins! (%s)", secondHand.getRank().getName());
    }
}
