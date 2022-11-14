package com.ing.minesweeper.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinesweeperGameTest {

    final private MinesweeperProperties properties = new MinesweeperProperties(5, 5, 1, 10, 10);

    private MinesweeperGame game;

    @BeforeEach
    void init() {
        game = new MinesweeperGame(properties);
    }

    @Test
    void shouldInitTheGame() {
        assertTrue(game.getStatus().equals(MinesweeperGameStatus.OPEN));
        assertEquals(game.getInfo(), "");
    }

    @Test
    void shouldLooseTheGameWhenMineIsHit() {
        //Given
        game.init(1, 1, 1);

        //When
        game.open(1, 1);

        //Then
        assertTrue(game.getStatus().equals(MinesweeperGameStatus.DEFEAT));
        assertTrue(game.getInfo().contains("You lose"));
    }

    @Test
    void shouldWinTheGameWhenMineIsFlagged() {
        //Given
        game.init(1, 1, 1);

        //When
        game.flag(1, 1);

        //Then
        assertTrue(game.getStatus().equals(MinesweeperGameStatus.VICTORY));
        assertTrue(game.getInfo().contains("You win"));
    }
}