package be.kdg.integration5.checkerscontext.adapter.out.game;

import be.kdg.integration5.checkerscontext.adapter.out.exception.GameNotFoundException;
import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaEntityId;
import be.kdg.integration5.checkerscontext.adapter.out.piece.PieceJpaRepository;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaEntity;
import be.kdg.integration5.checkerscontext.adapter.out.player.PlayerJpaRepository;
import be.kdg.integration5.checkerscontext.domain.*;
import be.kdg.integration5.checkerscontext.port.out.DeleteGamePort;
import be.kdg.integration5.checkerscontext.port.out.FindGamePort;
import be.kdg.integration5.checkerscontext.port.out.PersistGamePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GameDatabaseAdapter implements PersistGamePort, DeleteGamePort, FindGamePort {
    private final GameJpaRepository gameJpaRepository;
    private final PieceJpaRepository pieceJpaRepository;
    private final PlayerJpaRepository playerJparepository;
    private final GameJpaConverter gameJpaConverter;
    private final Logger logger = LoggerFactory.getLogger(GameDatabaseAdapter.class);

    public GameDatabaseAdapter(GameJpaRepository gameJpaRepository, PieceJpaRepository pieceJpaRepository, PlayerJpaRepository playerJparepository, GameJpaConverter gameJpaConverter) {
        this.gameJpaRepository = gameJpaRepository;
        this.pieceJpaRepository = pieceJpaRepository;
        this.playerJparepository = playerJparepository;
        this.gameJpaConverter = gameJpaConverter;
    }

    @Override
    public Game save(Game game) {
        GameJpaEntity gameJpaEntity = gameJpaConverter.toJpa(game);

        Set<PlayerJpaEntity> players = new HashSet<>();
        for (Player player : game.getPlayers()) {
            PlayerJpaEntity playerJpaEntity = playerJparepository.getReferenceById(player.getPlayerId().uuid());

            if (player.getPlayerId().equals(game.getBoard().getCurrentPlayer().getPlayerId()))
                gameJpaEntity.setCurrentPlayer(playerJpaEntity);

            players.add(playerJpaEntity);
        }
        gameJpaEntity.setPlayers(players);

        GameJpaEntity savedGameJpaEntity = gameJpaRepository.save(gameJpaEntity);

        for (Piece piece : game.getBoard().getPieces()) {
            PieceJpaEntityId pieceJpaEntityId = new PieceJpaEntityId(
                    game.getPlayedMatchId().uuid(),
                    piece.getPiecePosition().x(),
                    piece.getPiecePosition().y());
            PieceJpaEntity pieceJpaEntity = new PieceJpaEntity(piece.isKing(), piece.getColor());
            PlayerJpaEntity ownerPlayer = playerJparepository.getReferenceById(piece.getOwner().getPlayerId().uuid());

            pieceJpaEntity.setPieceId(pieceJpaEntityId);
            pieceJpaEntity.setGame(savedGameJpaEntity);
            pieceJpaEntity.setOwner(ownerPlayer);
            pieceJpaRepository.save(pieceJpaEntity);
        }

        return game;
    }

    @Override
    public Game update(Game game) {
        // Update only Game Jpa fields
        GameJpaEntity gameJpaEntity = gameJpaRepository.getReferenceById(game.getPlayedMatchId().uuid());
        UUID currentPlayerId = game.getBoard().getCurrentPlayer().getPlayerId().uuid();
        gameJpaEntity.setCurrentPlayer(playerJparepository.getReferenceById(currentPlayerId));
        gameJpaEntity.setFinished(game.isFinished());
        gameJpaRepository.save(gameJpaEntity);

        // Update pieces
        Set<PieceJpaEntity> existingPieces =  gameJpaEntity.getPieces();
        Map<PieceJpaEntityId, PieceJpaEntity> existingPieceMap = existingPieces.stream()
                .collect(Collectors.toMap(PieceJpaEntity::getPieceId, piece -> piece));

        for (Piece piece : game.getBoard().getPieces()) {
            PieceJpaEntityId pieceId = new PieceJpaEntityId(
                    game.getPlayedMatchId().uuid(),
                    piece.getPiecePosition().x(),
                    piece.getPiecePosition().y()
            );
            PieceJpaEntity pieceJpaEntity = existingPieceMap.remove(pieceId);
            if (pieceJpaEntity == null) {
                // Create and add a new piece
                pieceJpaEntity = new PieceJpaEntity(piece.isKing(), piece.getColor());
                pieceJpaEntity.setPieceId(pieceId);
                pieceJpaEntity.setGame(gameJpaEntity);
                pieceJpaEntity.setOwner(playerJparepository.getReferenceById(piece.getOwner().getPlayerId().uuid()));
                existingPieces.add(pieceJpaEntity);
            } else {
                pieceJpaEntity.setKing(piece.isKing());
            }
        }
        existingPieces.removeAll(existingPieceMap.values());
        pieceJpaRepository.deleteAll(existingPieceMap.values());
        pieceJpaRepository.saveAll(existingPieces);

        GameJpaEntity updatedGame = gameJpaRepository.findByIdFetched(game.getPlayedMatchId().uuid())
                .orElseThrow(() -> new GameNotFoundException("Game with ID [%s] not found.".formatted(game.getPlayedMatchId().uuid())));

        return gameJpaConverter.toDomain(updatedGame);
    }

    @Override
    public void deleteById(GameId gameId) {
        //TODO Implement
    }

    @Override
    public Game findById(GameId gameId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByIdFetched(gameId.uuid()).orElseThrow(
                () -> new GameNotFoundException("Game with given id [%s] is not found".formatted(gameId.uuid()))
        );
        return gameJpaConverter.toDomain(gameJpaEntity);
    }

    @Override
    public Game findGameByPlayerAndGameEndNull(PlayerId playerId) {
        GameJpaEntity gameJpaEntity = gameJpaRepository.findByPlayerIdAndEndDateNullFetched(playerId.uuid())
                .orElseThrow(() -> new GameNotFoundException("Game for player [%s] not found.]".formatted(playerId.uuid())));

        return gameJpaConverter.toDomain(gameJpaEntity);
    }


}
