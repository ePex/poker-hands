package de.epex.pokerhands;

import org.junit.jupiter.api.Test; // JUnit 5 Test annotation
import org.springframework.boot.test.context.SpringBootTest;
// @ExtendWith(SpringExtension.class) is not strictly needed with @SpringBootTest in JUnit 5
// as @SpringBootTest already includes it.

@SpringBootTest // This annotation handles context loading and integrates with JUnit 5
class NewPokerHandsApplicationTests {

	@Test // JUnit 5 Test annotation
	void contextLoads() {
		// This test verifies that the Spring application context loads successfully.
		// It's typically kept empty.
	}

}
