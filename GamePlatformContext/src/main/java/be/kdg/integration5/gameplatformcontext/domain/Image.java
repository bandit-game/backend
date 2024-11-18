package be.kdg.integration5.gameplatformcontext.domain;

import java.util.Objects;

public record Image(String url) {
    public Image {
        Objects.requireNonNull(url);
    }
}
