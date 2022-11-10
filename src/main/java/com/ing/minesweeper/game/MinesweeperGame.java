package com.ing.minesweeper.game;

import com.ing.minesweeper.errors.MinesweeperException;
import org.springframework.stereotype.Component;

@Component
public class MinesweeperGame {

    private MinesweeperBoard board;
    private final MinesweeperProperties properties;
    private MinesweeperException lastException;

    public MinesweeperGame(MinesweeperProperties properties) {
        this.properties = properties;
        init(properties.getDefRowsCount(), properties.getDefColumnsCount(), properties.getDefMinesCount());
    }

    public String print() {
        MinesweeperBoardPrinter printer = new MinesweeperBoardPrinter();
        return printer.print(board);
    }

    public void init(int rowsCount, int columnsCount, int minesCount) {
        if (rowsCount > properties.getMaxRowsCount()) {
            throw new MinesweeperException("To many rows! Max is " +  properties.getMaxRowsCount());
        }

        if (columnsCount > properties.getMaxColumnsCount()) {
            throw new MinesweeperException("To many columns! Max is " +  properties.getMaxColumnsCount());
        }

        board = new MinesweeperBoard(rowsCount, columnsCount, minesCount);
    }

    public void openCell(int rowNo, int colNo) {
        board.openCell(rowNo, colNo);
    }

    public void flag(int rowNo, int colNo) {
        board.flagCell(rowNo, colNo);
    }
}
