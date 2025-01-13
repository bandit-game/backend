package be.kdg.integration5.guessitcontext.controller.api.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/achievements")
public class AchievementController {
    //TODO Only for presentation purposes
    @GetMapping
    public ResponseEntity<List<AchievementGetDto>> getAllAchievementsForPlayer() {
        List<AchievementGetDto> achievementGetDtos = List.of(
                new AchievementGetDto(UUID.fromString("0f80ba07-3ece-43bb-80d8-35a0d156f9c3"), "The lucky one", "Guess the number from the first try.", null, false),
                new AchievementGetDto(UUID.fromString("0700c8c7-df9a-4d71-a09e-7b2be4129789"), "The unlucky one", "Guess the number from the last try.", null, false)
        );
        return ResponseEntity.ok(achievementGetDtos);
    }
}
