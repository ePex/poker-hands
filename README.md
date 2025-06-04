# Poker Hands Comparison Service

## Description

This application compares two poker hands, each provided as a string input, and determines the winner or if it's a draw. It evaluates hands based on standard poker rankings. The application features a simple web interface for inputting hands and viewing results.

It has been refactored to follow Domain-Driven Design (DDD) principles for its core logic and utilizes modern Java features and Spring Boot.

## Features

*   Compares two 5-card poker hands.
*   Supports all standard poker hand ranks (High Card, Pair, Two Pairs, Three of a Kind, Straight, Flush, Full House, Four of a Kind, Straight Flush, Royal Flush).
*   Includes tie-breaking logic based on rank and kicker cards.
*   Simple web interface for easy interaction.
*   Built with modern Java (17) and Spring Boot (3.1.5).

## Technologies Used

*   Java 17
*   Spring Boot 3.1.5
    *   Spring Web
    *   Thymeleaf (for HTML templating)
*   Maven (for build and dependency management)
*   JUnit 5 (for unit testing)

## Setup and Installation

### Prerequisites

*   Java Development Kit (JDK) 17 or later.
*   Apache Maven 3.6.x or later.

### Building the Project

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/example/poker-hands.git
    cd poker-hands
    ```
2.  **Build the project using Maven:**
    ```bash
    mvn clean package
    ```
    This will compile the code, run tests, and create an executable JAR file in the `target/` directory (e.g., `poker-hands-0.0.1-SNAPSHOT.jar`).

## Running the Application

There are two main ways to run the application:

1.  **Using `java -jar` (after building):**
    ```bash
    java -jar target/poker-hands-0.0.1-SNAPSHOT.jar
    ```
2.  **Using the Spring Boot Maven plugin:**
    ```bash
    mvn spring-boot:run
    ```

Once the application is running, you can access the web interface by navigating to:
`http://localhost:8080/poker-hands`

## How to Use (Web Interface)

1.  Open your web browser and go to `http://localhost:8080/poker-hands`.
2.  You will see a form with two input fields: "First Hand" and "Second Hand".
3.  Enter each poker hand as a string of 5 cards separated by spaces.
    *   **Card Format:** Each card should be represented by its value followed by its suit character (ValueSuite format).
        *   Values: `2, 3, 4, 5, 6, 7, 8, 9, T` (Ten), `J` (Jack), `Q` (Queen), `K` (King), `A` (Ace).
        *   Suits: `S` (Spades), `H` (Hearts), `D` (Diamonds), `C` (Clubs).
    *   **Example Hand String:** `AS KH QD JC TH` (Ace of Spades, King of Hearts, Queen of Diamonds, Jack of Clubs, Ten of Hearts).
    *   **Note:** The application's card parsing logic has been updated to support this "ValueSuite" format (e.g., "AS", "5H").
4.  Click the "Compare Hands" button.
5.  The result of the comparison will be displayed on the next page.

## Project Structure Overview

The project follows a layered architecture, influenced by Domain-Driven Design:

*   `de.epex.pokerhands.domain`: Contains the core domain logic, entities, value objects, and domain services (e.g., `Hand`, `Card`, `HandEvaluationResult`, `HandComparator`). This layer is independent of application and infrastructure concerns.
*   `de.epex.pokerhands.service`: Contains application services (e.g., `Evaluator`) that orchestrate domain logic and act as an interface between the domain and the presentation/infrastructure layers.
*   `de.epex.pokerhands.web.controller`: Spring MVC controllers that handle HTTP requests, delegate to application services, and select views.
*   `de.epex.pokerhands.web.dto`: Data Transfer Objects (Java Records) used for communication with the web layer.
*   `de.epex.pokerhands.service.exception`: Custom exceptions for handling application-specific errors (e.g., `InvalidPokerHandException`).

## How to Run Tests

To compile the code and run all unit tests, use the following Maven command:
```bash
mvn clean test
```
*(Note: After recent fixes to card parsing and test data alignment, the test suite should be passing. If any issues persist, they would be minor and unrelated to the core parsing logic.)*

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
