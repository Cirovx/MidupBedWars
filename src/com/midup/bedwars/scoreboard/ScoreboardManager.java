package com.midup.bedwars.scoreboard;

import com.midup.Midup;
import com.midup.bedwars.Main;
import com.midup.bedwars.team.Team;
import com.midup.integer.IntegerUtils;
import com.midup.scoreboard.ColorScrollPlus;
import com.midup.scoreboard.IScoreboard;
import com.midup.scoreboard.OnScoreboardRecevied;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScoreboardManager {

    public static void loadPreGame() {

        IScoreboard scoreboard = new IScoreboard("BEDWARS", "BEDWARS");
        scoreboard.blankLine();// 8
        scoreboard.addLine("§fMapa: §a" + Main.getWorldManager().getWorldName());// 7
        scoreboard.addLine("§fJogadores: §a");// 6
        scoreboard.blankLine();// 5
        scoreboard.addLine("");// 4
        scoreboard.blankLine();// 3
        scoreboard.addLine("§fSala: §a" + Main.getGameManager().getGame().getRoomID());// 2
        scoreboard.blankLine();// 1
        scoreboard.addLine("§ewww.midup.com.br");// 0
        scoreboard.setScoreboardRecevied(new OnScoreboardRecevied() {

            @Override
            public void view(Player player, IScoreboard is) {
                is.update(Bukkit.getOnlinePlayers().size() + "/" + Main.getGameManager().getGame().getMaxPlayers(), 6);
                if (Main.getGameManager().getTask() == null) {
                    is.update("§fAguardando...", 4);
                } else {
                    is.update("§fIniciando em §a" + Main.getActionManager().getTimeForNextAction() + "s", 4);
                }

            }
        });
        Midup.getScoreboardManager().registerScoreboard(scoreboard);
        Midup.getScoreboardManager().registerScroll(new ColorScrollPlus(ChatColor.WHITE, "BEDWARS", "§6", "§6", "§6", true, false, ColorScrollPlus.ScrollType.FORWARD));
    }

    public static void loadInGame() {

        IScoreboard scoreboard = new IScoreboard("BEDWARS", "BEDWARS");
        scoreboard.addLine("§fSala: §a" + Main.getGameManager().getGame().getRoomID());
        scoreboard.blankLine();
        scoreboard.addLine("");
        scoreboard.addLine("");
        scoreboard.blankLine();
        for (Team team : Main.getTeamManager().getTeams()) {
            scoreboard.addLine(team.getType().getCharDisplay(true) + " §f" + team.getType().getName());
        }
        scoreboard.blankLine();// 1
        scoreboard.addLine("§ewww.midup.com.br");// 0
        scoreboard.setScoreboardRecevied(new OnScoreboardRecevied() {

            @Override
            public void view(Player player, IScoreboard is) {
                int aux = 2;
                for (int i = Main.getTeamManager().getTeams().size(); i > 0; i--) {
                    Team team = Main.getTeamManager().getTeams().get(i - 1);
                    String update = "";
                    int livePlayers = team.getLiveUsers().size();
                    if (team.hasBed()) {
                        update = team.getUser(player.getUniqueId()) != null ? " §2✔  §7(você)" : " §2✔";
                    } else {
                        if (livePlayers == 0) {
                            update = team.getUser(player.getUniqueId()) != null ? " §c✘  §7(você)" : " §c✘";
                        } else {
                            update = team.getUser(player.getUniqueId()) != null ? " §a" + livePlayers + "  §7(você)" : " §a" + livePlayers;
                        }
                    }
                    is.update(update, aux);
                    aux++;
                }
                aux++;
                is.update("§a" + IntegerUtils.convertToTime(Main.getActionManager().getTimeForNextAction()), aux);
                aux++;
                is.update(Main.getActionManager().getNextAction(), aux);
            }
        });
        Midup.getScoreboardManager().registerScoreboard(scoreboard);
        Midup.getScoreboardManager().registerScroll(new ColorScrollPlus(ChatColor.WHITE, "BEDWARS", "§6", "§6", "§6", true, false, ColorScrollPlus.ScrollType.FORWARD));
        Midup.getScoreboardManager().registerAllPlayers();
    }

}
