-- Sample data for players table
INSERT INTO players (player_id, name, is_first)
VALUES ('11111111-1111-1111-1111-111111111111', 'Alice', TRUE),
       ('22222222-2222-2222-2222-222222222222', 'Bob', FALSE);

-- Sample data for games table
INSERT INTO games (game_id, started_time, finished_time)
VALUES ('33333333-3333-3333-3333-333333333333', '2024-12-01 10:00:00', NULL);

-- Sample data for games_players table
INSERT INTO games_players (game_id,player_id)
VALUES ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111'),
       ('33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222');

-- Sample data for boards table
INSERT INTO boards (game_id, current_player_player_id)
VALUES ('33333333-3333-3333-3333-333333333333',
        '11111111-1111-1111-1111-111111111111');

-- Sample data for pieces table
DO '
    DECLARE
        piece_number INTEGER;
    BEGIN
        -- Insert 20 white pieces
        FOR piece_number IN 1..20 LOOP
                INSERT INTO pieces (piece_number, board_id, piece_color, is_king)
                VALUES (
                           piece_number,
                           ''33333333-3333-3333-3333-333333333333''::UUID,
                           ''BLACK'',
                           FALSE
                       );
            END LOOP;

        -- Insert 20 black pieces
        FOR piece_number IN 21..40 LOOP
                INSERT INTO pieces (piece_number, board_id, piece_color, is_king)
                VALUES (
                           piece_number,
                           ''33333333-3333-3333-3333-333333333333''::UUID,
                           ''WHITE'',
                           FALSE
                       );
            END LOOP;
    END;
' LANGUAGE plpgsql;


-- Sample data for squares table
-- Creating 100 squares for the board
DO '
    DECLARE
        i           INTEGER := 0;
        j           INTEGER := 0;
        piece_index INTEGER := 1;
    BEGIN
        FOR i IN 0..9
            LOOP
                FOR j IN 0..9
                    LOOP
                        INSERT INTO squares (y, x, board_game_id, board_id, placed_piece_piece_number, placed_piece_board_id)
                        VALUES (i, j, ''33333333-3333-3333-3333-333333333333'', ''33333333-3333-3333-3333-333333333333'',
                                CASE WHEN ((i * 10 + j) < 40 OR (i * 10 + j) > 60) AND (i + j) % 2 = 1 THEN piece_index ELSE NULL END,
                                CASE WHEN ((i * 10 + j) < 40 OR (i * 10 + j) > 60) AND (i + j) % 2 = 1 THEN ''33333333-3333-3333-3333-333333333333''::UUID ELSE NULL END);
                        IF ((i * 10 + j) < 40 OR (i * 10 + j) > 60) AND (i + j) % 2 = 1 THEN piece_index := piece_index + 1;
                            END IF;
                    END LOOP;
            END LOOP;
    END ' LANGUAGE plpgsql;;
