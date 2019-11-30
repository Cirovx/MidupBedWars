package com.midup.bedwars.user;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.Game;

public class UserStatistics {

    private int kills;
    private int deaths;
    private int brokenBeds;
    private int vipsInGame;

    public UserStatistics() {
        this.kills = 0;
        this.deaths = 0;
        this.brokenBeds = 0;
    }

    public int getTotalCoins() {
        Game game = Main.getGameManager().getGame();
        int multiply = this.kills == 0 ? 1 : this.kills;
        return (game.getCoinsPerKill() * multiply) + (game.getCoinsPerVip() * this.vipsInGame) + (game.getCoinsPerBrokenBed() * this.brokenBeds);
    }

    public int getCoinsPerVip() {
        return Main.getGameManager().getGame().getCoinsPerVip() * this.vipsInGame;
    }

    public int getVipsInGame() {
        return vipsInGame;
    }

    public int getCoinsPerBrokenBed() {
        return Main.getGameManager().getGame().getCoinsPerBrokenBed() * this.brokenBeds;
    }

    public void addVipInGame(int coinsPerVip) {
        this.vipsInGame = coinsPerVip;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKill() {
        this.kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int getBrokenBeds() {
        return brokenBeds;
    }

    public void addBrokenBeds() {
        this.brokenBeds++;
    }

}
