package be.kdg.integration5.checkerscontext.controller;

import be.kdg.integration5.checkerscontext.domain.Board;
import be.kdg.integration5.checkerscontext.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        Board board = new Board();
//        boardRepository.save(board);
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