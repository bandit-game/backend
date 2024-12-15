package be.kdg.integration5.checkerscontext.adapter.out.piece;

import be.kdg.integration5.checkerscontext.adapter.out.game.GameJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "pieces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJpaEntity {
    @EmbeddedId
    private PieceJpaEntityId pieceId;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    @MapsId("gameId")
    private GameJpaEntity game;

    @Column(nullable = false)
    private boolean isKing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Piece.PieceColor pieceColor;


    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "player_id")
    private PlayerJpaEntity owner;

    public PieceJpaEntity(boolean isKing, Piece.PieceColor pieceColor) {
        this.isKing = isKing;
        this.pieceColor = pieceColor;
    }

    public static PieceJpaEntity of(Piece piece) {
        return new PieceJpaEntity(
                piece.isKing(),
                piece.getColor());
    }

}
