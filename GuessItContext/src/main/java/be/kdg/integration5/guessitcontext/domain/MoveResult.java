package be.kdg.integration5.guessitcontext.domain;

import java.util.Objects;

public record MoveResult(ResultType resultType, boolean isFinished) {
    public MoveResult {
        Objects.requireNonNull(resultType);
    }
}
