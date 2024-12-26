package be.kdg.integration5.gameplatformcontext.adapter.in.dto;

import java.util.Objects;

public record QueryRequestDTO(String query) {
    public QueryRequestDTO {
        Objects.requireNonNull(query);
    }
}
