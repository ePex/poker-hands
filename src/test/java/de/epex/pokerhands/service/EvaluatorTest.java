package de.epex.pokerhands.service;

import de.epex.pokerhands.domain.ComparisonOutcome;
import de.epex.pokerhands.domain.Hand;
import de.epex.pokerhands.domain.HandComparator;
import de.epex.pokerhands.domain.Rank;
import de.epex.pokerhands.domain.Winner;
import de.epex.pokerhands.service.exception.InvalidPokerHandException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EvaluatorTest { // Renamed NewEvaluatorTest to EvaluatorTest

    private final static String VALID_HAND_STR_1 = "AS KS QS JS TS"; // Represents Royal Flush
    private final static String VALID_HAND_STR_2 = "2H 3H 4H 5H 6H"; // Represents Straight Flush (6 high)
    private final static String VALID_HAND_STR_3 = "AD KD QD JD 9D"; // Represents Ace high Flush
    private final static String INVALID_HAND_STR = "AS KS QS JS XX"; // Contains invalid card "XX"

    @Mock
    private HandComparator mockHandComparator;

    private Evaluator evaluator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Use the package-private constructor of Evaluator to inject the mock
        evaluator = new Evaluator(mockHandComparator);
    }

    @Test
    void evaluate_firstHandWins_returnsCorrectMessage() {
        // Arrange
        // Note: Actual Hand objects are created inside evaluator.evaluate().
        // The mock setup doesn't depend on the actual content of these strings,
        // only on what ComparisonOutcome the mocked HandComparator returns.
        ComparisonOutcome outcome = new ComparisonOutcome(Winner.FIRST_HAND, Rank.ROYAL_FLUSH, Rank.STRAIGHT_FLUSH);
        when(mockHandComparator.determineWinner(any(Hand.class), any(Hand.class))).thenReturn(outcome);

        // Act
        String result = evaluator.evaluate(VALID_HAND_STR_1, VALID_HAND_STR_2);

        // Assert
        assertEquals("First hand wins! (Royal flush)", result);
    }

    @Test
    void evaluate_secondHandWins_returnsCorrectMessage() {
        // Arrange
        ComparisonOutcome outcome = new ComparisonOutcome(Winner.SECOND_HAND, Rank.STRAIGHT, Rank.FLUSH);
        when(mockHandComparator.determineWinner(any(Hand.class), any(Hand.class))).thenReturn(outcome);

        // Act
        String result = evaluator.evaluate(VALID_HAND_STR_2, VALID_HAND_STR_3);

        // Assert
        assertEquals("Second hand wins! (Flush)", result);
    }

    @Test
    void evaluate_draw_returnsCorrectMessage() {
        // Arrange
        ComparisonOutcome outcome = new ComparisonOutcome(Winner.DRAW, Rank.HIGH_CARD, Rank.HIGH_CARD);
        when(mockHandComparator.determineWinner(any(Hand.class), any(Hand.class))).thenReturn(outcome);

        // Act
        // Using arbitrary valid hand strings here as the outcome is mocked.
        String result = evaluator.evaluate(VALID_HAND_STR_1, VALID_HAND_STR_2);

        // Assert
        assertEquals("It's a draw! (Both High card)", result);
    }

    @Test
    void evaluate_invalidFirstHandString_throwsInvalidPokerHandException() {
        // Arrange: No mocking needed for HandComparator, as Hand construction fails first.

        // Act & Assert
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> {
            evaluator.evaluate(INVALID_HAND_STR, VALID_HAND_STR_1);
        });
        // Check if the message from the exception contains the expected part.
        assertTrue(exception.getMessage().contains("Invalid card in hand: 'XX'"));
    }

    @Test
    void evaluate_invalidSecondHandString_throwsInvalidPokerHandException() {
        // Arrange

        // Act & Assert
        Exception exception = assertThrows(InvalidPokerHandException.class, () -> {
            evaluator.evaluate(VALID_HAND_STR_1, INVALID_HAND_STR);
        });
        assertTrue(exception.getMessage().contains("Invalid card in hand: 'XX'"));
    }
}
