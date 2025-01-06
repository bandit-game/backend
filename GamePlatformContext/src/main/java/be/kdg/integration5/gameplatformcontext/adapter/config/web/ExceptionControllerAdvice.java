package be.kdg.integration5.gameplatformcontext.adapter.config.web;

import be.kdg.integration5.gameplatformcontext.adapter.out.web.exception.ChatbotRequestFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = { ChatbotRequestFailedException.class })
    private ErrorResponse handleChatbotRequestFailedException(ChatbotRequestFailedException ex, WebRequest request) {
        return ErrorResponse.create(ex, HttpStatus.BAD_GATEWAY, ex.getLocalizedMessage());
    }
}
