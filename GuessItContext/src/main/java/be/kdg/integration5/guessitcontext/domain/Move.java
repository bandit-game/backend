package be.kdg.integration5.guessitcontext.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "moves")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class Move {

    @Id
    @Column(name = "move_id")
    private UUID moveId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime moveDateTime;

}
