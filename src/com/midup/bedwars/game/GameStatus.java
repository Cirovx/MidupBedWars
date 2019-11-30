package com.midup.bedwars.game;

/**
 *
 * @author cirov
 */
public enum GameStatus {

    /**
     * O plugin acaba de reiniciar, porem ainda não fez o setup completo.
     */
    STARTING,
    /**
     * Tudo pronto para receber os jogadores, aqui se formarão os times.
     */
    PRE_GAME,
    /**
     * Jogo em andamento, so será possivel entrar neste momento, ja tiver
     * registrado em um time
     */
    IN_GAME,
    /**
     * Partida finalizada, teleportando players para o lobby.
     */
    FINISHED;

}
