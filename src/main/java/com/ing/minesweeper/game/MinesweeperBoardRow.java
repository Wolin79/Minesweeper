package com.ing.minesweeper.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class MinesweeperBoardRow {

    private final ArrayList<MinesweeperBoardCell> cells;

    public MinesweeperBoardRow(int cellsCount) {
        this.cells = new ArrayList<>(cellsCount);
        for (int i=0; i<cellsCount; i++) {
            cells.add(new MinesweeperBoardCell(false, false, false, 0));
        }
    }

    public Iterator<MinesweeperBoardCell> iterator() {
        return cells.iterator();
    }

    public Optional<MinesweeperBoardCell> getCell(int index) {
        if (index < 0 || index >= cells.size()) {
            return Optional.empty();
        }
        return Optional.of(cells.get(index));
    }
}
