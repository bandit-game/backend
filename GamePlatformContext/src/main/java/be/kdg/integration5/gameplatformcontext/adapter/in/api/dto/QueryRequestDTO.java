package be.kdg.integration5.gameplatformcontext.adapter.in.api.dto;

import java.util.Objects;

public record QueryRequestDTO(String query) {
    public QueryRequestDTO {
        Objects.requireNonNull(query);
    }
}
