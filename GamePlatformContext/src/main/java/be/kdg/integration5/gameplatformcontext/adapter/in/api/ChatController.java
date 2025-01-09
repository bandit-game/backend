package be.kdg.integration5.gameplatformcontext.adapter.in.api;

import be.kdg.integration5.gameplatformcontext.adapter.in.api.dto.QueryRequestDTO;
import be.kdg.integration5.gameplatformcontext.port.in.SendChatbotQueryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final SendChatbotQueryUseCase sendChatbotQueryUseCase;

    public ChatController(SendChatbotQueryUseCase sendChatbotQueryUseCase) {
        this.sendChatbotQueryUseCase = sendChatbotQueryUseCase;
    }

    @PostMapping("/query")
    public ResponseEntity<String> sendChatbotQuery(@RequestBody QueryRequestDTO requestDTO) {
        String response = sendChatbotQueryUseCase.processQuery(requestDTO.query());
        return ResponseEntity.ok(response);
    }

}
