package com.ing.minesweeper.game;

import com.ing.minesweeper.errors.MinesweeperException;
import com.ing.minesweeper.errors.MinesweeperInvalidFlagException;
import com.ing.minesweeper.errors.MinesweeperMineHitException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class MinesweeperBoard {

    private final ArrayList<MinesweeperBoardRow> rows;
    @Getter
    private final int rowsCount;
    @Getter
    private final int columnsCount;
    @Getter
    private final int minesCount;

    public MinesweeperBoard(int rowsCount, int columnsCount, int minesCount) {
        if (minesCount < 1 ||  minesCount > rowsCount * columnsCount) {
            throw new MinesweeperException("Invalid mines count!");
        }

        this.rows = new ArrayList<>(rowsCount);
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.minesCount = minesCount;

        init(rowsCount, columnsCount, minesCount);
    }

    public Iterator<MinesweeperBoardRow> iterator() {
        return rows.iterator();
    }

    public void openCell(int rowNo, int colNo) {
        var cell = getCellOrThrow(rowNo, colNo);
        if (!cell.isOpened()) {
            cell.setOpened(true);
            if (cell.isMine()) {
                throw new MinesweeperMineHitException("BOOM!!! Given cell is mined!");
            }
        }
    }

    public void flagCell(int rowNo, int colNo) {
        var cell = getCellOrThrow(rowNo, colNo);
        if (!cell.isOpened()) {
            cell.setOpened(true);
            if (!cell.isMine()) {
                throw new MinesweeperInvalidFlagException("Given cell isn't mined!");
            }
            cell.setFlagged(true);
        }
    }

    public void reset() {
        iterator().forEachRemaining(row -> {
            row.iterator().forEachRemaining(cell -> {
                cell.setFlagged(false);
                cell.setOpened(false);
            });
        });
    }

    public boolean isAllOpened() {
        for (int rowIndex=1; rowIndex<=rowsCount; rowIndex++) {
            for (int colIndex = 1; colIndex <= columnsCount; colIndex++) {
                if (!getCellOrThrow(rowIndex, colIndex).isOpened()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected Optional<MinesweeperBoardRow> getRow(int index) {
        if (index < 0 || index >= rows.size()) {
            return Optional.empty();
        }
        return Optional.of(rows.get(index));
    }

    protected MinesweeperBoardCell getCellOrThrow(int rowNo, int colNo) {
        var row = getRow(rowNo-1).orElseThrow(() -> new MinesweeperException("Invalid row number!"));
        return row.getCell(colNo-1).orElseThrow(() -> new MinesweeperException("Invalid column number!"));
    }

    private void init(int rowsCount, int columnsCount, int minesCount) {
        for (int i = 0; i < rowsCount; i++) {
            rows.add(new MinesweeperBoardRow(columnsCount));
        }

        //Mine random cells
        var minedCells = 0;
        while (minedCells < minesCount) {
            var rowIndexToMine = ThreadLocalRandom.current().nextInt(rowsCount);
            var columnIndexToMine = ThreadLocalRandom.current().nextInt(columnsCount);
            var rowToMine = rows.get(rowIndexToMine);
            var cellToMine = rowToMine.getCell(columnIndexToMine).get();
            if (!cellToMine.isMine()) {
                cellToMine.setMine(true);
                minedCells++;
                //Increase mine count in adjacent cells
                increaseMineCountInAdjacentCells(rowIndexToMine, columnIndexToMine);
            }
        }
    }

    private void increaseMineCountInAdjacentCells(int rowNo, int columnNo) {
        for (int rowIndex=rowNo-1; rowIndex<=rowNo+1; rowIndex++) {
            for (int cellIndex=columnNo-1; cellIndex<=columnNo+1; cellIndex++) {
                final int index = cellIndex;
                getRow(rowIndex).ifPresent(row -> row.getCell(index).map(MinesweeperBoardCell::increaseNoOfAdjacentMines));
            }
        }
    }
}
