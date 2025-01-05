package be.kdg.integration5.checkerscontext.adapter.out.persistence.piece;

import be.kdg.integration5.checkerscontext.adapter.out.persistence.game.GameJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.persistence.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.domain.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
