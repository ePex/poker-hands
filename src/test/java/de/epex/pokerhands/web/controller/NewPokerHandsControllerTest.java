package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.service.Evaluator;
import de.epex.pokerhands.service.exception.InvalidPokerHandException;
import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.instanceOf; // For model attribute type checking

@ExtendWith(SpringExtension.class)
@WebMvcTest(PokerHandsController.class)
class NewPokerHandsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Evaluator evaluatorService; // Renamed to avoid conflict with local var if any

    @Test
    void displayForm_shouldReturnFormView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/poker-hands"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("enter-poker-hands"))
               // Check for the attribute compareHandsDto of type CompareHandsDto
               // The actual value is new CompareHandsDto(null, null)
               .andExpect(MockMvcResultMatchers.model().attribute("compareHandsDto", instanceOf(CompareHandsDto.class)));
    }

    @Test
    void compareHands_validInput_shouldReturnResultView() throws Exception {
        String successMessage = "First hand wins! (High card)";
        when(evaluatorService.evaluate(anyString(), anyString())).thenReturn(successMessage);

        mockMvc.perform(MockMvcRequestBuilders.post("/poker-hands/compare-hands")
                       .param("firstHand", "AS KS QS JS TH")
                       .param("secondHand", "2C 3C 4C 5C 7C"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("comparison-result"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("comparisonResultDto"))
               .andExpect(MockMvcResultMatchers.model().attribute("comparisonResultDto",
                              new ComparisonResultDto(successMessage)));
    }

    @Test
    void compareHands_invalidInput_shouldReturnResultViewWithError() throws Exception {
        String errorMessage = "Test error: Invalid hand";
        // This mock simulates the Evaluator service throwing the exception,
        // which then should be caught by the @ExceptionHandler in PokerHandsController
        when(evaluatorService.evaluate(anyString(), anyString()))
            .thenThrow(new InvalidPokerHandException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.post("/poker-hands/compare-hands")
                       .param("firstHand", "INVALID INPUT")
                       .param("secondHand", "AS KS QS JS TH"))
               .andExpect(MockMvcResultMatchers.status().isOk()) // Exception handler configured to return ModelAndView with 200 OK
               .andExpect(MockMvcResultMatchers.view().name("comparison-result"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("comparisonResultDto"))
               .andExpect(MockMvcResultMatchers.model().attribute("comparisonResultDto",
                              new ComparisonResultDto("Error: " + errorMessage)));
    }
}
