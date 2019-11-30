package com.midup.bedwars.game;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

/**
 *
 * @author cirov
 */
public class Game {

    private GameType gameType;
    private int totalTeams;
    private int playersPerTeam;
    private int minPlayersForStart;
    private int maxPlayers;
    private GameStatus status;
    private int coinsPerKill = 10;
    private int coinsPerBrokenBed = 50;
    private int coinsPerVip = 10;
    private List<Location> protectedBlocks = new ArrayList<>();
    private String roomID = "0";
    private int portRange;

    public String getRoomID() {
        return roomID;
    }

    public int getPortRange() {
        return portRange;
    }

    public void setPortRange(int portRange) {
        this.portRange = portRange;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void addProtectedLocations(Location location) {
        this.protectedBlocks.add(location);
    }

    public List<Location> getProtectedLocations() {
        return this.protectedBlocks;
    }

    public int getCoinsPerVip() {
        return coinsPerVip;
    }

    public int getCoinsPerKill() {
        return coinsPerKill;
    }

    public int getCoinsPerBrokenBed() {
        return coinsPerBrokenBed;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(int totalTeams) {
        this.totalTeams = totalTeams;
    }

    public int getPlayersPerTeam() {
        return playersPerTeam;
    }

    public void setPlayersPerTeam(int playersPerTeam) {
        this.playersPerTeam = playersPerTeam;
    }

    public int getMinPlayersForStart() {
        return minPlayersForStart;
    }

    public void setMinPlayersForStart(int minPlayersForStart) {
        this.minPlayersForStart = minPlayersForStart;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

}
