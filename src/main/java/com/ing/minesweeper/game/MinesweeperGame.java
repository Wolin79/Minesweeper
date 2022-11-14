package com.ing.minesweeper.game;

import com.ing.minesweeper.errors.MinesweeperException;
import com.ing.minesweeper.errors.MinesweeperInvalidFlagException;
import com.ing.minesweeper.errors.MinesweeperMineHitException;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class MinesweeperGame {

    private final MinesweeperGameProperties properties;

    @Getter
    private MinesweeperBoard board;
    @Getter
    private String info;
    @Getter
    private MinesweeperGameStatus status;

    public MinesweeperGame(MinesweeperGameProperties properties) {
        this.properties = properties;
        init(properties.getDefRowsCount(), properties.getDefColumnsCount(), properties.getDefMinesCount());
    }

    public void init(int rowsCount, int columnsCount, int minesCount) {
        try {
            if (rowsCount < 1) {
                throw new MinesweeperException("Minimum number of rows is 1");
            }

            if (columnsCount < 1) {
                throw new MinesweeperException("Minimum number of column is 1");
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
            status = MinesweeperGameStatus.NOT_INITIALIZED;
            info = e.getMessage();
        }
    }

    public void restart() {
        board.reset();
        info = "";
        status = MinesweeperGameStatus.OPEN;
    }

    public void open(int rowNo, int colNo) {
        if (status != MinesweeperGameStatus.OPEN) {
            info = getDefInfoForStatus(status);
        }
        else {
            try {
                board.openCell(rowNo, colNo);
                checkForVictory();
            } catch (MinesweeperMineHitException e) {
                status = MinesweeperGameStatus.DEFEAT;
                info = "You lose: " + e.getMessage();
            }
        }
    }

    public void flag(int rowNo, int colNo) {
        if (status != MinesweeperGameStatus.OPEN) {
            info = getDefInfoForStatus(status);
        }
        else {
            try {
                board.flagCell(rowNo, colNo);
                checkForVictory();
            } catch (MinesweeperInvalidFlagException e) {
                status = MinesweeperGameStatus.DEFEAT;
                info = "You lose: " + e.getMessage();
            }
        }
    }

    private void checkForVictory() {
        if (board.isAllOpened()) {
            status = MinesweeperGameStatus.VICTORY;
            info = "You win!!!";
        }
    }

    private String getDefInfoForStatus(MinesweeperGameStatus status) {
        return switch (status) {
            case NOT_INITIALIZED -> "Please start new game";
            case OPEN -> "Game is open";
            case DEFEAT -> "You loose - please start new game or reset current board";
            case VICTORY -> "You win - please start new game or reset current board";
        };
    }
}
