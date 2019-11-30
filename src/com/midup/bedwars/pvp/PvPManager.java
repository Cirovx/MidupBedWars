package com.midup.bedwars.pvp;

import com.midup.bedwars.Main;
import com.midup.bedwars.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author cirov
 */
public class PvPManager {

    private List<Material> dropItems = new ArrayList<>(Arrays.asList(Material.DIAMOND, Material.EMERALD, Material.IRON_INGOT, Material.GOLD_INGOT));

    public void customDeath(Player player) {
        User user = Main.getUserManager().getUser(player.getUniqueId());
        if (player.getKiller() == null) {
            if (player.hasMetadata("LastDamageHit")) {
                long lastHit = Long.parseLong(player.getMetadata("LastDamageHit").get(0).value().toString().split("@")[1]);
                long current = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                long diff = current - lastHit;
                if (diff <= 10) {
                    String playerName = player.getMetadata("LastDamageHit").get(0).value().toString().split("@")[0];
                    processKill(Bukkit.getPlayer(playerName), player);
                }
            } else {
                processKill(player.getKiller(), player);
            }
        } else {
            processKill(player.getKiller(), player);
        }
        if (user.getTeam().hasBed()) {
            Main.getSpectatorManager().setTemporarySpectator(player);
        } else {
            Main.getSpectatorManager().setSpectator(player);
            user.setLive(false);
        }
    }

    private void processKill(Player killer, Player player) {
        String playerName = Main.getUserManager().getUser(player.getUniqueId()).getTeam().getType().getColorCode() + "" + player.getName();
        User userPlayer = Main.getUserManager().getUser(player.getUniqueId());
        if (killer == null) {
            if (userPlayer.getTeam().hasBed()) {
                Bukkit.broadcastMessage(playerName + " §emorreu sozinho!");
            } else {
                Bukkit.broadcastMessage(playerName + " §efoi eliminado!");
            }
        } else {
            giveItems(killer, player);
            String killerName = Main.getUserManager().getUser(killer.getUniqueId()).getTeam().getType().getColorCode() + "" + killer.getName();
            User userKiller = Main.getUserManager().getUser(killer.getUniqueId());
            userPlayer.getStatistics().addDeath();
            userKiller.getStatistics().addKill();
            if (userPlayer.getTeam().hasBed()) {
                Bukkit.broadcastMessage(killerName + " §ematou o jogador " + playerName);
            } else {
                Bukkit.broadcastMessage(killerName + " §eeliminou o jogador " + playerName);
            }
        }

    }

    private void giveItems(Player killer, Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (dropItems.contains(item.getType())) {
                    String message = "";
                    killer.getInventory().addItem(item.clone());
                    switch (item.getType()) {
                        case IRON_INGOT:
                            message = "§7+ " + item.getAmount() + " Ferros";
                            break;
                        case GOLD_INGOT:
                            message = "§6+ " + item.getAmount() + " Ouros";
                            break;
                        case DIAMOND:
                            message = "§b+ " + item.getAmount() + " Diamantes";
                            break;
                        case EMERALD:
                            message = "§2+ " + item.getAmount() + " Esmeraldas";
                            break;
                        default:
                            break;
                    }
                    killer.sendMessage(message);
                }
            }
        }
    }
}
