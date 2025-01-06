package be.kdg.integration5.gameplatformcontext.adapter.in.api;

import be.kdg.integration5.gameplatformcontext.adapter.in.api.dto.PlayerRegisterDTO;
import be.kdg.integration5.gameplatformcontext.port.in.RegisterNewPlayerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerRegistrationController {

    private final RegisterNewPlayerUseCase registerNewPlayerUseCase;

    public PlayerRegistrationController(RegisterNewPlayerUseCase registerNewPlayerUseCase) {
        this.registerNewPlayerUseCase = registerNewPlayerUseCase;
    }

    @PostMapping
    public ResponseEntity<?> registerNewPlayer(@RequestBody PlayerRegisterDTO playerRegisterDTO) {
        registerNewPlayerUseCase.registerNewPlayer(playerRegisterDTO.token());
        return ResponseEntity.ok().build();
    }
}
