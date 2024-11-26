package be.kdg.integration5.checkerscontext.controller;

import be.kdg.integration5.checkerscontext.adapter.in.MovesController;
import be.kdg.integration5.checkerscontext.adapter.out.BoardJpaEntity;
import be.kdg.integration5.checkerscontext.port.out.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class MovesControllerTest {
    private UUID createdBoardId;

    @Autowired
    private MovesController movesController;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        createdBoardId = UUID.randomUUID();
        //TODO
        // Implement domain model
        BoardJpaEntity board = new BoardJpaEntity();
        boardRepository.save(board);
    }

    @Test
    void whenRequestedListOfValidMovesForSpecificPiecesShouldBeReturned() {
        //TODO
    }

    @AfterEach
    void tearDown() {
        boardRepository.deleteById(createdBoardId);
    }
}