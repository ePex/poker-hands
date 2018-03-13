package de.epex.pokerhands.web.controller;

import de.epex.pokerhands.web.dto.CompareHandsDto;
import de.epex.pokerhands.web.dto.ComparisonResultDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PokerHandsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDisplayForm() throws Exception {
        mockMvc.perform(get("/poker-hands"))
                .andExpect(model().attribute("compareHandsDto", any(CompareHandsDto.class)))
                .andExpect(view().name("enter-poker-hands"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCompareHands() throws Exception {
        mockMvc.perform(post("/poker-hands/compare-hands", new CompareHandsDto()))
                .andExpect(model().attribute("comparisonResultDto", any(ComparisonResultDto.class)))
                .andExpect(view().name("comparison-result"))
                .andExpect(status().isOk());
    }
}