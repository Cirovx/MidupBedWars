package com.midup.bedwars.chat;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.GameStatus;
import com.midup.bedwars.user.User;
import com.midup.bedwars.user.UserStatus;
import com.midup.display.PlayerDisplay;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author cirov
 */
public class CommandGlobal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String string, String[] strings) {
        if (cs instanceof Player) {
            Player player = (Player) cs;
            if (strings.length == 0) {
                player.sendMessage("§cUse §f/g <menssagem>");
                return false;
            }
            if (Main.getGameManager().getGame().getStatus() == GameStatus.IN_GAME) {
                User user = Main.getUserManager().getUser(player.getUniqueId());
                String chanel = "";
                String prefixTeam = user.getTeam().getType().getColorCode() + "[" + user.getTeam().getType().getNameDisplay().toUpperCase() + "]";
                String playerName = PlayerDisplay.getDisplayPlayer(player.getName());
                StringBuilder message = new StringBuilder();
                for (String s : strings) {
                    message.append(s).append(" ");
                }
                if (user.getStatus() == UserStatus.PLAYING) {
                    chanel = "§6[G]";
                    String finalMessage = chanel + " " + prefixTeam + " " + playerName + "§f: " + message.toString();
                    Bukkit.getOnlinePlayers().forEach(pOn -> pOn.sendMessage(finalMessage));
                } else if (user.getStatus() == UserStatus.SPEC) {
                    player.sendMessage("§cVoce nao pode usar este chat agora");
                }
            } else {
                player.sendMessage("§cEste chat nao está liberado no momento. §eFale normalmente!");
            }
        }
        return true;
    }

}
