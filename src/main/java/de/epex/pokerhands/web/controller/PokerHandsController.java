package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.service.Evaluator;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/poker-hands")
public class PokerHandsController {

    private final Evaluator evaluator;

    @Autowired
    public PokerHandsController(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("enter-poker-hands");
        modelAndView.addObject(new CompareHandsDto());

        return modelAndView;
    }

    @RequestMapping(value = "/compare-hands", method = RequestMethod.POST)
    public ModelAndView compareHands(@ModelAttribute CompareHandsDto compareHandsDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("comparison-result");

        ComparisonResultDto comparisonResultDto = new ComparisonResultDto();
        comparisonResultDto.setMessage(getResultMessage(compareHandsDto));
        modelAndView.addObject(comparisonResultDto);

        return modelAndView;
    }

    private String getResultMessage(CompareHandsDto compareHandsDto) {
        return evaluator.evaluate(compareHandsDto.getFirstHand(), compareHandsDto.getSecondHand());
    }

}
