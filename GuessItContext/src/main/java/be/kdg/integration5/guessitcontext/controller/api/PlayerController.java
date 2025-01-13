package be.kdg.integration5.guessitcontext.controller.api;

import be.kdg.integration5.guessitcontext.controller.api.dto.AchievementGetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    //TODO Only for presentation purposes
    @GetMapping("/{playerId}/achievements")
    public ResponseEntity<List<AchievementGetDto>> getAllAchievementsForPlayer(@PathVariable("playerId") UUID playerId, @RequestParam(value = "open", required = false) Boolean open) {
        List<AchievementGetDto> achievementGetDtos = List.of(
                new AchievementGetDto(UUID.fromString("0f80ba07-3ece-43bb-80d8-35a0d156f9c3"), "The lucky one", "Guess the number from the first try.", "https://picsum.photos/200", false),
                new AchievementGetDto(UUID.fromString("0700c8c7-df9a-4d71-a09e-7b2be4129789"), "The unlucky one", "Guess the number from the last try.", "https://picsum.photos/200", false)
        );
        return ResponseEntity.ok(achievementGetDtos);
    }
}
