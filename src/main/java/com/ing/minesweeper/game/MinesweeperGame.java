package com.ing.minesweeper.game;

import com.ing.minesweeper.errors.MinesweeperBoomException;
import com.ing.minesweeper.errors.MinesweeperException;
import com.ing.minesweeper.errors.MinesweeperInvalidFlagException;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class MinesweeperGame {

    private final MinesweeperProperties properties;

    @Getter
    private MinesweeperBoard board;
    @Getter
    private String info;
    @Getter
    private MinesweeperGameStatus status;

    public MinesweeperGame(MinesweeperProperties properties) {
        this.properties = properties;
        init(properties.getDefRowsCount(), properties.getDefColumnsCount(), properties.getDefMinesCount());
    }

    public void init(int rowsCount, int columnsCount, int minesCount) {
        try {
            if (rowsCount <= 1) {
                throw new MinesweeperException("Minimum number of rows is 2");
            }

            if (columnsCount <= 1) {
                throw new MinesweeperException("Minimum number of column is 2");
            }

            if (minesCount <= 0) {
                throw new MinesweeperException("Minimum number of mines is 1");
            }

            if (rowsCount > properties.getMaxRowsCount()) {
                throw new MinesweeperException("To many rows! Max is " + properties.getMaxRowsCount());
            }

            if (columnsCount > properties.getMaxColumnsCount()) {
                throw new MinesweeperException("To many columns! Max is " + properties.getMaxColumnsCount());
            }

            board = new MinesweeperBoard(rowsCount, columnsCount, minesCount);
            info = "";
            status = MinesweeperGameStatus.OPEN;
        }
        catch (MinesweeperException e) {
            info = e.getMessage();
        }
    }

    public void restart() {
        board.reset();
        info = "";
        status = MinesweeperGameStatus.OPEN;
    }

    public void openCell(int rowNo, int colNo) {
        if (status != MinesweeperGameStatus.OPEN) {
            info = "Game over.";
        }

        try {
            board.openCell(rowNo, colNo);
        }
        catch (MinesweeperBoomException e) {
            status = MinesweeperGameStatus.DEFEAT;
            info = "You lose: " + e.getMessage();
        }
    }

    public void flag(int rowNo, int colNo) {
        if (status != MinesweeperGameStatus.OPEN) {
            info = "Game over.";
        }

        try {
            board.flagCell(rowNo, colNo);
        }
        catch (MinesweeperInvalidFlagException e) {
            status = MinesweeperGameStatus.DEFEAT;
            info = "You lose: " + e.getMessage();
        }
    }
}
