-- Sample data for players table
INSERT INTO players (player_id)
VALUES ('7701935d-4efc-4f53-81f1-1010dce8a943'),
       ('5c035da0-3a05-4cd7-8e0f-d9cae488de8b');

-- Sample data for games table
INSERT INTO games (game_id)
VALUES ('33333333-3333-3333-3333-333333333333');

-- Sample data for games_players table
INSERT INTO games_players (game_id, player_id)
VALUES ('33333333-3333-3333-3333-333333333333', '7701935d-4efc-4f53-81f1-1010dce8a943'),
       ('33333333-3333-3333-3333-333333333333', '5c035da0-3a05-4cd7-8e0f-d9cae488de8b');

insert into achievements (desired_number_of_games, is_achieved, performer_id, dtype, description, game_ids_played, image_url, name)
values (10, FALSE, '7701935d-4efc-4f53-81f1-1010dce8a943', 'PLAYNGAMES', 'You need to play 10 times the Checkers Game.',
        '33333333-3333-3333-3333-333333333333', '', 'Play 10 Games'),
       (null, FALSE, '7701935d-4efc-4f53-81f1-1010dce8a943', 'MOVEPIECE', 'You need to move any piece in the game.',
        null, '', 'Move Your Piece'),
       (10, FALSE, '5c035da0-3a05-4cd7-8e0f-d9cae488de8b', 'PLAYNGAMES', 'You need to play 10 times the Checkers Game.',
        '33333333-3333-3333-3333-333333333333', '', 'Play 10 Games'),
       (null, FALSE, '5c035da0-3a05-4cd7-8e0f-d9cae488de8b', 'MOVEPIECE', 'You need to move any piece in the game.',
        null, '', 'Move Your Piece');

