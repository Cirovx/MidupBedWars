package com.midup.bedwars.spectator;

import com.midup.bedwars.Main;
import com.midup.bedwars.user.User;
import com.midup.bedwars.user.UserStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class GhostMaker {

    private ScoreboardManager manager = null;
    private Scoreboard board = null;
    @SuppressWarnings("unused")
    private Team team = null;

    public GhostMaker() {
        this.manager = Bukkit.getScoreboardManager();
        this.board = manager.getNewScoreboard();
        this.team = board.registerNewTeam("Ghosts");

    }

    private Team ghostsTeam = null;

    @SuppressWarnings("deprecation")
    public void setGhost(Player player, Boolean ghost) {
        for (User user : Main.getUserManager().getAllOnlineUsers()) {
            if (user.getStatus() == UserStatus.PLAYING || user.getStatus() == UserStatus.TEMP_SPEC) {
                user.getPlayer().hidePlayer(player);
            } else {
                player.showPlayer(user.getPlayer());
            }
        }

    }

    @SuppressWarnings("deprecation")
    private void removeGhost(Player player) {
        if (ghostsTeam == null) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            ghostsTeam = scoreboard.getTeam("Ghosts");
            if (ghostsTeam == null) {
                ghostsTeam = scoreboard.registerNewTeam("Ghosts");
                ghostsTeam.setCanSeeFriendlyInvisibles(true);
            }
        }
        if (ghostsTeam.hasPlayer(player)) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            ghostsTeam.removePlayer(player);
        }
    }

}
