package com.midup.bedwars.game.action.actions;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.action.Action;

public class EndGame extends Action {

    public EndGame() {
        super("Fim do jogo", 360);
    }

    @Override
    public void run() {
        Main.getGameManager().endGame();

    }

}
