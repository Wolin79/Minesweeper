package com.ing.minesweeper.game;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("minesweeper")
public class MinesweeperProperties {
    private int defRowsCount;
    private int defColumnsCount;
    private int defMinesCount;
    private int maxRowsCount;
    private int maxColumnsCount;
}
