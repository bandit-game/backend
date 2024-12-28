package be.kdg.integration5.statisticscontext.domain;

import java.util.Objects;

public record Location(String country, String city) {
    public Location {
        Objects.requireNonNull(country);
        Objects.requireNonNull(city);
    }
}