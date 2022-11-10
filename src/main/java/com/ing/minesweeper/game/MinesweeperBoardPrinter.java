package com.ing.minesweeper.game;

import java.util.Iterator;

public class MinesweeperBoardPrinter {

    public String print(MinesweeperBoard board) {
        StringBuffer result = new StringBuffer();
        printLine(result, board.getColumnsCount());
        Iterator<MinesweeperBoardRow> rowsIterator = board.iterator();
        rowsIterator.forEachRemaining(row -> printRow(result, row));
        printLine(result, board.getColumnsCount());
        return result.toString();
    }

    private void printLine(StringBuffer result, int columnsCount) {
        result.append("-");
        for (int i=0; i<columnsCount; i++) {
            result.append("----");
        }
        result.append("\n");
    }

    private void printRow(StringBuffer result, MinesweeperBoardRow row) {
        Iterator<MinesweeperBoardCell> cellsIterator = row.iterator();
        cellsIterator.forEachRemaining(cell -> result.append("| " + printCell(cell) + " "));
        result.append("|\n");
    }

    private String printCell(MinesweeperBoardCell cell) {
        if (cell.isOpened()) {
            if (cell.isMine()) {
                return "!";
            }
            else if (cell.isFlagged()) {
                return "F";
            }
            else {
                return String.valueOf(cell.getNoOfAdjacentMines());
            }
        }
        else {
            return " ";
        }
    }
}
