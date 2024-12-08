package be.kdg.integration5.checkerscontext.adapter.in.dto;

import java.util.UUID;

public record GetMovesRequestDto(UUID gameId, Integer x, Integer y){
}
