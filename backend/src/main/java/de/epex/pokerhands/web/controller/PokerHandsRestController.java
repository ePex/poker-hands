package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.service.Evaluator;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/poker-hands")
public class PokerHandsRestController {

    private final Evaluator evaluator;

    @Autowired
    public PokerHandsRestController(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @RequestMapping(value = "/compare-hands", method = RequestMethod.POST)
    public ComparisonResultDto compareHands(@RequestBody CompareHandsDto compareHandsDto) {
        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setMessage(getResultMessage(compareHandsDto));

        return comparisonResultDto;
    }

    @RequestMapping(value= "/compare-hands", method=RequestMethod.OPTIONS)
    public void corsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
    }

    private String getResultMessage(CompareHandsDto compareHandsDto) {
        return evaluator.evaluate(compareHandsDto.getFirstHand(), compareHandsDto.getSecondHand());
    }

}
