package be.kdg.integration5.checkerscontext.adapter.in.dto;

import java.util.UUID;

public record GetMovesRequestDTO(UUID gameId, Integer x, Integer y){
}
