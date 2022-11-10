package com.ing.minesweeper.game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinesweeperBoardCell {
    private boolean opened;
    private boolean flagged;
    private boolean mine;
    private int noOfAdjacentMines;

    public int increaseNoOfAdjacentMines() {
        return noOfAdjacentMines++;
    }
}
