package be.kdg.integration5.guessitcontext.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @Column(name = "session_id")
    private UUID sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player firstPlayer;

    @ManyToMany
    private List<Player> players;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player currentPlayer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player winner;

    private boolean isFinished;

    private boolean isDraw;

    private int targetNumber;

    public Session(Player firstPlayer, UUID sessionId, boolean isFinished, boolean isDraw) {
        Random random = new Random();
        this.firstPlayer = firstPlayer;
        this.sessionId = sessionId;
        this.targetNumber = random.nextInt(10) + 1;
        this.isFinished = isFinished;
        this.currentPlayer = firstPlayer;
        this.isDraw = isDraw;
    }
}
