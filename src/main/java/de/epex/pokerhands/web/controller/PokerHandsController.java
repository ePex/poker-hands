package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.service.Evaluator;
import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping; // Added
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping; // Added
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/poker-hands") // Base path can remain
public class PokerHandsController {

    private final Evaluator evaluator;

    @Autowired
    public PokerHandsController(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @GetMapping // Changed from @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("enter-poker-hands");
        // For records, if the form expects an object to bind to,
        // providing one with null/empty initial values is common.
        modelAndView.addObject("compareHandsDto", new CompareHandsDto(null, null));
        return modelAndView;
    }

    @PostMapping(value = "/compare-hands") // Changed from @RequestMapping
    public ModelAndView compareHands(@ModelAttribute CompareHandsDto compareHandsDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("comparison-result");

        // evaluator.evaluate can now throw InvalidPokerHandException,
        // which will be handled by the @ExceptionHandler
        String message = evaluator.evaluate(compareHandsDto.firstHand(), compareHandsDto.secondHand());

        ComparisonResultDto comparisonResultDto = new ComparisonResultDto(message);
        modelAndView.addObject("comparisonResultDto", comparisonResultDto);

        return modelAndView;
    }

    // getResultMessage method is removed as its logic is now inlined in compareHands
    // and exception handling is done by @ExceptionHandler

    @ExceptionHandler(InvalidPokerHandException.class)
    public ModelAndView handleInvalidPokerHandException(InvalidPokerHandException ex,
                                                        @ModelAttribute CompareHandsDto compareHandsDto) {
        ModelAndView modelAndView = new ModelAndView();
        // Decide if we want to return to the form or show the error on the result page.
        // Showing on result page for now.
        modelAndView.setViewName("comparison-result");
        modelAndView.addObject("comparisonResultDto", new ComparisonResultDto("Error: " + ex.getMessage()));

        // To allow resubmission or display of entered values if returning to form:
        // modelAndView.setViewName("enter-poker-hands");
        // modelAndView.addObject("compareHandsDto", compareHandsDto); // The DTO that caused the error

        return modelAndView;
    }
}
