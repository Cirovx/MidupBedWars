package com.midup.bedwars.message;

import com.midup.bedwars.Main;
import com.midup.bedwars.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author cirov
 */
public enum Message {

    YOU_DONT_HAVE_BALANCE("§cVocê não possui saldo para comprar isto!"), YOU_HAS_BUY("§aVocê comprou §6@§a!"), PLAYER_HAS_BUY("§a@ comprou §6@§a!"), YOU_HAS_ALREADY_MAXIMUM("§aVocê já possui o máximo!"), CLICK_TO_BUY("§eClique para comprar!");

    private String description;

    private Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void sendPlayerMessage(Player player, String... replaces) {
        player.sendMessage(constructMessage(replaces));
    }

    public void sendBroadcastMessage(String... replaces) {
        Bukkit.broadcastMessage(constructMessage(replaces));
    }

    public String getMessage(String... replaces) {
        return constructMessage(replaces);
    }

    public void sendTeamMessage(Player player, String... replaces) {
        User user = Main.getUserManager().getUser(player.getUniqueId());
        for (User users : Main.getUserManager().getTeamUsers(user.getTeam().getType())) {
            if (users.isOnline()) {
                users.getPlayer().sendMessage(constructMessage(replaces));
            }
        }
    }

    private String constructMessage(String... replaces) {
        String finalMessage = this.getDescription();
        if (finalMessage.contains("@")) {
            if (replaces != null) {
                for (String replace : replaces) {
                    finalMessage = finalMessage.replaceFirst("@", replace);
                }
            }
        }
        return finalMessage;
    }

}
