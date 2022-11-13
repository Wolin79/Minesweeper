package com.ing.minesweeper.commands;

import com.ing.minesweeper.game.MinesweeperGame;
import com.ing.minesweeper.printer.MinesweeperGamePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MinesweeperCommands {

    private final MinesweeperGame game;
    private final MinesweeperGamePrinter printer;

    @ShellMethod("Start new game")
    public String start(int rowsCount,
                        int columnsCount,
                        int minesCount) {
        game.init(rowsCount, columnsCount, minesCount);
        return printer.print(game);
    }

    @ShellMethod("Restart current game")
    public String restart() {
        game.restart();
        return printer.print(game);
    }

    @ShellMethod("Print current board")
    public String print() {
        return printer.print(game);
    }

    @ShellMethod("Open given cell")
    public String open(int rowNo, int colNo) {
        game.openCell(rowNo, colNo);
        return printer.print(game);
    }

    @ShellMethod("Flag given cell")
    public String flag(int rowNo, int colNo) {
        game.flag(rowNo, colNo);
        return printer.print(game);
    }
}