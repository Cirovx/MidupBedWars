package com.midup.bedwars.chat;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.GameStatus;
import com.midup.bedwars.game.GameType;
import com.midup.bedwars.user.User;
import com.midup.bedwars.user.UserStatus;
import com.midup.display.PlayerDisplay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author cirov
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        User user = Main.getUserManager().getUser(player.getUniqueId());
        String prefixTeam = user.getTeam().getType().getColorCode() + "[" + user.getTeam().getType().getNameDisplay().toUpperCase() + "]";
        String playerName = PlayerDisplay.getDisplayPlayer(player.getName());
        String message = event.getMessage();
        if (Main.getGameManager().getGame().getStatus() == GameStatus.IN_GAME) {
            if (user.getStatus() == UserStatus.PLAYING) {
                String finalMessage = prefixTeam + " " + playerName + "§7: " + message.toString();
                if (Main.getGameManager().getGame().getGameType() == GameType.SOLO) {
                    Bukkit.broadcastMessage(finalMessage);
                } else {
                    for (User userOn : Main.getUserManager().getAllOnlineUsers()) {
                        if (userOn.isOnline() && userOn.getStatus() == UserStatus.PLAYING) {
                            if (userOn.getTeam() == user.getTeam()) {
                                userOn.getPlayer().sendMessage(finalMessage);
                            }
                        }
                    }
                }
            } else if (user.getStatus() == UserStatus.SPEC) {
                String finalMessage = "§8[ESPEC] " + prefixTeam + " " + playerName + "§7: " + message.toString();
                for (User userOn : Main.getUserManager().getAllOnlineUsers()) {
                    if (userOn.isOnline() && userOn.getStatus() == UserStatus.SPEC) {
                        userOn.getPlayer().sendMessage(finalMessage);
                    }
                }
            }
        } else {
            String finalMessage = playerName + "§7: " + message.toString();
            Bukkit.getOnlinePlayers().forEach(pOn -> pOn.sendMessage(finalMessage));
        }
    }
}
