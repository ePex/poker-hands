package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.service.HandComparator;
import de.epex.pokerhands.service.model.Hand;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/poker-hands")
public class PokerHandsRestController {

    private final HandComparator handComparator;

    @Autowired
    public PokerHandsRestController(HandComparator handComparator) {
        this.handComparator = handComparator;
    }

    @RequestMapping(value = "/compare-hands", method = RequestMethod.POST)
    public ComparisonResultDto compareHands(@RequestBody CompareHandsDto compareHandsDto) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setMessage(getResultMessage(compareHandsDto));

        return comparisonResultDto;
    }

    private String getResultMessage(CompareHandsDto compareHandsDto) {
        try {
            Hand firstHand = new Hand(compareHandsDto.getFirstHand());
            Hand secondHand = new Hand(compareHandsDto.getSecondHand());

            return handComparator.compareAndGetResultMessage(firstHand, secondHand);
        } catch (IllegalArgumentException e) {
            return String.format("Input data is invalid: '%s'", e.getMessage());
        }
    }

}
