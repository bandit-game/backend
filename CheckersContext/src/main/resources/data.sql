-- Sample data for players table
INSERT INTO players (player_id, name)
VALUES ('7701935d-4efc-4f53-81f1-1010dce8a943', 'sofiia'),
       ('5c035da0-3a05-4cd7-8e0f-d9cae488de8b', 'anna');

-- Sample data for games table
INSERT INTO games (game_id, is_finished, is_draw, current_player_player_id)
VALUES ('33333333-3333-3333-3333-333333333333', FALSE, FALSE, '7701935d-4efc-4f53-81f1-1010dce8a943');

-- Sample data for games_players table
INSERT INTO games_players (game_id, player_id)
VALUES ('33333333-3333-3333-3333-333333333333', '7701935d-4efc-4f53-81f1-1010dce8a943'),
       ('33333333-3333-3333-3333-333333333333', '5c035da0-3a05-4cd7-8e0f-d9cae488de8b');

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
                    WHEN (y * 10 + x) < 40 THEN ''7701935d-4efc-4f53-81f1-1010dce8a943''::UUID
                    WHEN (y * 10 + x) > 60 THEN ''5c035da0-3a05-4cd7-8e0f-d9cae488de8b''::UUID
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