package be.kdg.integration5.gameplatformcontext.adapter.out.exception;

public class ChatbotRequestFailedException extends RuntimeException{
    public ChatbotRequestFailedException(String message) {
        super(message);
    }
}
