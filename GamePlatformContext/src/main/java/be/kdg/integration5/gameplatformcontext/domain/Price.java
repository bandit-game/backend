package be.kdg.integration5.gameplatformcontext.domain;

import java.util.Currency;
import java.util.Objects;

public record Price(Double amount, Currency currency) {
    public Price {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
    }
}
