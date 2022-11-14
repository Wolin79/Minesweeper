package com.ing.minesweeper.game;

import com.ing.minesweeper.errors.MinesweeperInvalidFlagException;
import com.ing.minesweeper.errors.MinesweeperMineHitException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperBoardTest {

    @Test
    void shouldOpenCell() {
        //Given
        var board = new MinesweeperBoard(10, 10, 1);
        var rowNoToOpen = 5;
        var colNoToOpen = 5;

        //When
        try {
            board.openCell(rowNoToOpen, colNoToOpen);
        }
        catch (MinesweeperMineHitException e) {
            //We were unlucky and hit a mine - try one more time
            board.openCell(++rowNoToOpen, colNoToOpen);
        }

        //Then
        var cell = board.getCellOrThrow(rowNoToOpen, colNoToOpen);
        assertTrue(cell.isOpened());
        assertFalse(cell.isFlagged());
        assertFalse(cell.isMine());
    }

    @Test
    void shouldThrowExceptionWhenHitMine() {
        //Given
        var board = new MinesweeperBoard(1, 1, 1);

        //When/Then
        assertThrowsExactly(MinesweeperMineHitException.class, () -> board.openCell(1, 1));
    }

    @Test
    void shouldFlagCell() {
        //Given
        var board = new MinesweeperBoard(1, 1, 1);

        //When
        board.flagCell(1, 1);

        //Then
        var cell = board.getCellOrThrow(1, 1);
        assertTrue(cell.isOpened());
        assertTrue(cell.isFlagged());
        assertTrue(cell.isMine());
    }

    @Test
    void shouldThrowExceptionWhenFlagClearCell() {
        //Given
        var board = new MinesweeperBoard(2, 2, 1);
        final var cellCords = new int[] {1, 1};

        //Check if given cell is a mined - is so then choose another one
        if (board.getCellOrThrow(cellCords[0], cellCords[1]).isMine()) {
            cellCords[0] = cellCords[0]+1;
        }

        //When/Then
        assertThrowsExactly(MinesweeperInvalidFlagException.class, () -> board.flagCell(cellCords[0], cellCords[1]));
    }

    @Test
    void shouldResetBoard() {
        //Given
        var rowCount = 5;
        var colCount = 5;
        var board = new MinesweeperBoard(rowCount, 5, colCount);

        //When
        for (int rowIndex=1; rowIndex<=rowCount; rowIndex++) {
            for (int colIndex=1; colIndex<=colCount; colIndex++) {
                try {
                    board.openCell(rowIndex, colIndex);
                }
                catch (MinesweeperMineHitException e) {
                    //Ignore it
                }
            }

        }
        board.reset();

        //Then
        for (int rowIndex=1; rowIndex<=rowCount; rowIndex++) {
            for (int colIndex = 1; colIndex <= colCount; colIndex++) {
                assertFalse(board.getCellOrThrow(rowIndex, colIndex).isOpened());
            }
        }
    }
}