-- Sample data for players table
INSERT INTO players (player_id, name)
VALUES ('11111111-1111-1111-1111-111111111111', 'Alice'),
       ('22222222-2222-2222-2222-222222222222', 'Bob');

-- Sample data for games table
INSERT INTO games (game_id, is_finished, current_player_player_id)
VALUES ('33333333-3333-3333-3333-333333333333', FALSE, '11111111-1111-1111-1111-111111111111');

-- Sample data for games_players table
INSERT INTO games_players (game_id, player_id)
VALUES ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111'),
       ('33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222');

-- Sample data for pieces table
DO '
BEGIN
FOR y IN 0..9
    LOOP
    FOR x IN 0..9
        LOOP
        IF (y + x) % 2 = 1 AND ((y * 10 + x) < 40 OR (y * 10 + x) > 60) THEN
            INSERT INTO pieces (currentx, currenty, is_king, game_id, owner_id, piece_color)
            VALUES (x, y, false, ''33333333-3333-3333-3333-333333333333'',
                CASE
                    WHEN (y * 10 + x) < 40 THEN ''11111111-1111-1111-1111-111111111111''::UUID
                    WHEN (y * 10 + x) > 60 THEN ''22222222-2222-2222-2222-222222222222''::UUID
                    END,
                CASE
                    WHEN (y * 10 + x) < 40 THEN ''BLACK''
                    WHEN (y * 10 + x) > 60 THEN ''WHITE''
                    END);
            END IF;
        END LOOP;
    END LOOP;
END;
' LANGUAGE plpgsql;