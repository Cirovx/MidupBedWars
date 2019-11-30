package com.midup.bedwars.commands;

import com.midup.bedwars.Main;
import com.midup.bedwars.team.Team;
import com.midup.bedwars.team.TeamType;
import com.midup.bedwars.user.User;
import com.midup.command.CommandAnnotation;
import com.midup.message.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author cirov
 */
@CommandAnnotation(command = "game")
public class CommandGame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {

        // COMANDO APENAS PARA DEBUG DE SALA
        if (cs.isOp()) {
            Player player = (Player) cs;
            if (strings[0].equalsIgnoreCase("check")) {
                for (Team team : Main.getTeamManager().getTeams()) {
                    for (User user : team.getUsers()) {
                        System.out.println("User " + user.getDisplayName() + "| Vivo: " + user.isLive() + "| Online: " + user.isOnline() + "| hasBed: " + user.getTeam().hasBed());
                    }
                }

            } else if (strings[0].equalsIgnoreCase("infos")) {
                cs.sendMessage("§aComando game");
                cs.sendMessage("§eMaxPlayers: §7" + Main.getGameManager().getGame().getMaxPlayers());
                cs.sendMessage("§eMinPlayersForStart: §7" + Main.getGameManager().getGame().getMinPlayersForStart());
                cs.sendMessage("§ePlayersPerTeam: §7" + Main.getGameManager().getGame().getPlayersPerTeam());
                cs.sendMessage("§eTotalTeams: §7" + Main.getGameManager().getGame().getTotalTeams());
                cs.sendMessage("§eGameType: §7" + Main.getGameManager().getGame().getGameType().getDisplayName());
                cs.sendMessage("§eTimeForNextAction: §7" + Main.getActionManager().getNextAction());
                cs.sendMessage("§eTimes registrados: §7" + Main.getTeamManager().getTeams().size());
                Main.getTeamManager().getTeams().forEach(team -> {
                    team.getGenerators().forEach(gen -> {
                        cs.sendMessage(team.getType().getName() + " - " + gen.getType());
                    });
                });
            } else if (strings[0].equalsIgnoreCase("teams")) {
                for (Team team : Main.getTeamManager().getTeams()) {
                    cs.sendMessage("§l" + team.getType().getCharDisplay(true) + " - " + team.getType().getNameDisplay());
                }
                for (TeamType t : TeamType.getTeams()) {
                    cs.sendMessage("-> " + t.name());
                }
            } else if (strings[0].equalsIgnoreCase("player")) {
                for (User user : Main.getUserManager().getAllUsers()) {
                    cs.sendMessage("UserUUID : " + user.getUuid());
                    cs.sendMessage("UserName: " + Bukkit.getPlayer(user.getUuid()));
                    cs.sendMessage("User: " + user.getTeam().getType().getNameDisplay());
                }
            } else if (strings[0].equalsIgnoreCase("world")) {
                player.sendMessage("teleportando...");
                player.teleport(Bukkit.getWorld(strings[1]).getSpawnLocation());
            }
        } else {
            Messages.ERROR_PERMISSION.sendMessage(cs);
        }
        return true;
    }
}
