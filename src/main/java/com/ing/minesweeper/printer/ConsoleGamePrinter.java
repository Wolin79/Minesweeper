package com.ing.minesweeper.printer;

import com.ing.minesweeper.game.MinesweeperBoardCell;
import com.ing.minesweeper.game.MinesweeperBoardRow;
import com.ing.minesweeper.game.MinesweeperGame;
import org.springframework.stereotype.Component;

@Component
public class ConsoleGamePrinter implements MinesweeperGamePrinter {

    public String print(MinesweeperGame game) {
        var result = new StringBuffer();
        printHeader(result, game.getBoard().getColumnsCount());
        var rowsIterator = game.getBoard().iterator();
        int rowNo = 1;
        while (rowsIterator.hasNext()) {
            printRow(result, rowsIterator.next(), rowNo++);
        }
        printFooter(result, game.getBoard().getColumnsCount());
        result.append(game.getInfo());
        return result.toString();
    }

    private void printHeader(StringBuffer result, int columnsCount) {
        result.append("   ");
        for (int i=0; i<columnsCount; i++) {
            result.append(String.format(" %2d ", i+1));
        }
        result.append("\n");
        result.append("    ");
        for (int i=0; i<columnsCount; i++) {
            result.append("----");
        }
        result.append("\n");
    }

    private void printFooter(StringBuffer result, int columnsCount) {
        result.append("    ");
        for (int i=0; i<columnsCount; i++) {
            result.append("----");
        }
        result.append("\n");
        result.append("   ");
        for (int i=0; i<columnsCount; i++) {
            result.append(String.format(" %2d ", i+1));
        }
        result.append("\n");
    }

    private void printRow(StringBuffer result, MinesweeperBoardRow row, int rowNo) {
        var cellsIterator = row.iterator();
        result.append(String.format("%2d ", rowNo));
        cellsIterator.forEachRemaining(cell -> result.append("| " + printCell(cell) + " "));
        result.append("|\n");
    }

    private String printCell(MinesweeperBoardCell cell) {
        if (cell.isOpened()) {
            if (cell.isFlagged()) {
                return "F";
            }
            else if (cell.isMine()) {
                return "!";
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
