package be.kdg.integration5.guessitcontext.domain;


import lombok.Getter;

@Getter
public enum ResultType {
    INCORRECT("Invalid guess. Please guess a number between 1 and 10."),
    LESS("Too low! Try again."),
    MORE("Too high! Try again."),
    SUCCESS("Correct! You guessed the number!");

    private final String message;

    ResultType(String message) {
        this.message = message;
    }

}
