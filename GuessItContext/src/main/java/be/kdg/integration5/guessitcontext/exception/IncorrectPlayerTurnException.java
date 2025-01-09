package be.kdg.integration5.guessitcontext.exception;

public class IncorrectPlayerTurnException extends IllegalArgumentException{
    public IncorrectPlayerTurnException(String s) {
        super(s);
    }
}
