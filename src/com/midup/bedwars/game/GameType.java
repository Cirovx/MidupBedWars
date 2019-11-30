package com.midup.bedwars.game;

/**
 *
 * @author cirov
 */
public enum GameType {

    /**
     * Solo-8 Jogadores | 8 times | Minimo=6
     */
    SOLO("Solo"),
    /**
     * Dupla-16 Jogadores | 8 times | Minimo=12
     */
    DOUBLE("Dupla"),
    /**
     * Trio-12 Jogadores | 4 times) | Minimo=8
     */
    TRIPLE("Trio"),
    /**
     * Quarteto-16 Jogadores | 4 times | Minimo=12
     */
    QUARTET("Quarteto");

    private final String name;

    private GameType(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }

}
