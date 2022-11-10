package com.ing.minesweeper.commands;

import com.ing.minesweeper.game.MinesweeperGame;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MinesweeperCommands {

    final MinesweeperGame game;

    @ShellMethod("Start new game")
    public String start(int rowsCount, int columnsCount, int minesCount) {
        game.init(rowsCount, columnsCount, minesCount);
        return game.print();
    }

    @ShellMethod("Print current board")
    public String print() {
        return game.print();
    }

    @ShellMethod("Open given cell")
    public String open(int rowNo, int colNo) {
        game.openCell(rowNo, colNo);
        return game.print();
    }

    @ShellMethod("Flag given cell")
    public String flag(int rowNo, int colNo) {
        game.flag(rowNo, colNo);
        return game.print();
    }
}