package com.ing.minesweeper.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinesweeperBoardRowTest {

    @Test
    void shouldReturnCell() {
        MinesweeperBoardRow row = new MinesweeperBoardRow(1);
        assertTrue(row.getCell(0).isPresent());
    }

    @Test
    void shouldReturnEmptyCell() {
        MinesweeperBoardRow row = new MinesweeperBoardRow(1);
        assertFalse(row.getCell(1).isPresent());
    }
}