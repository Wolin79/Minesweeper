package com.ing.minesweeper.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("minesweeper")
public class MinesweeperProperties {
    private int defRowsCount;
    private int defColumnsCount;
    private int defMinesCount;
    private int maxRowsCount;
    private int maxColumnsCount;
}
