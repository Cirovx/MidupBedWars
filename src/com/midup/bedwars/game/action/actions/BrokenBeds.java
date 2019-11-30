package com.midup.bedwars.game.action.actions;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.action.Action;
import org.bukkit.Bukkit;

public class BrokenBeds extends Action {

    public BrokenBeds() {
        super("As camas quebrarao", 360);
    }

    @Override
    public void run() {
        Main.getTeamManager().getTeams().forEach(team -> {
            if (team.hasBed()) {
                team.brokenBed();
            }
        });
        Bukkit.broadcastMessage("§eTodas as camas foram quebradas!");
    }

}
