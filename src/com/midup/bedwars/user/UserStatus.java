package com.midup.bedwars.user;

public enum UserStatus {
    /**
     * O player esta jogando, em espera ou com a partida em andamento!
     */
    PLAYING,
    /**
     * O players esta espectando a partida!
     */
    SPEC,
    /**
     * O player morreu, e esta aguardando respawn!
     */
    TEMP_SPEC;
}
