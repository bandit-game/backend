package be.kdg.integration5.gameplatformcontext.domain;

import java.util.Objects;

public record Location(String country, String city) {
    public Location {
        Objects.requireNonNull(country);
        Objects.requireNonNull(city);
    }
}
