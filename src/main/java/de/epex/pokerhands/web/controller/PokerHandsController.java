package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.service.Evaluator;
import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.springframework.beans.factory.annotation.Autowired; // Keep Autowired for Evaluator
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
// Removed HttpStatus as it's not used in the simplified handler

@Controller
@RequestMapping(value = "/poker-hands")
public class PokerHandsController {

    private final Evaluator evaluator;

    @Autowired // Keep if Evaluator is a Spring managed bean
    public PokerHandsController(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    @GetMapping
    public ModelAndView displayForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("enter-poker-hands");
        modelAndView.addObject("compareHandsDto", new CompareHandsDto(null, null));
        return modelAndView;
    }

    @PostMapping(value = "/compare-hands")
    public ModelAndView compareHands(@ModelAttribute CompareHandsDto compareHandsDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("comparison-result");

        String message = evaluator.evaluate(compareHandsDto.firstHand(), compareHandsDto.secondHand());

        ComparisonResultDto comparisonResultDto = new ComparisonResultDto(message);
        modelAndView.addObject("comparisonResultDto", comparisonResultDto);

        return modelAndView;
    }

    @ExceptionHandler(InvalidPokerHandException.class)
    public ModelAndView handleInvalidPokerHandException(InvalidPokerHandException ex) { // Removed @ModelAttribute CompareHandsDto
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("comparison-result");
        modelAndView.addObject("comparisonResultDto", new ComparisonResultDto("Error: " + ex.getMessage()));
        // To re-populate the form, you'd need to pass the original CompareHandsDto.
        // This might involve getting it from the request if possible, or changing how errors are handled.
        // For now, this simplification directly addresses the "No suitable resolver" error.
        // To return to form with error and empty DTO:
        // modelAndView.setViewName("enter-poker-hands");
        // modelAndView.addObject("errorMessage", "Error: " + ex.getMessage());
        // modelAndView.addObject("compareHandsDto", new CompareHandsDto(null, null));
        return modelAndView;
    }
}
