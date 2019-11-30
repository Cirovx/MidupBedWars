package com.midup.bedwars.spectator;

import com.midup.bedwars.Main;
import com.midup.bedwars.user.UserStatus;
import com.midup.bungee.BungeeExecute;
import com.midup.menucustom.manager.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author cirov
 */
public class SpectatorListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (Main.getUserManager().getUser(event.getPlayer().getUniqueId()).getStatus() == UserStatus.SPEC) {
            event.setCancelled(true);
            if (event.getAction() == Action.PHYSICAL || event.getItem() == null) {
                return;
            }
            switch (event.getItem().getType()) {
                case LEATHER_BOOTS:
                    MenuManager.getMenu("Opções de Vôo").openMenu(event.getPlayer());
                    break;
                case BED:
                    BungeeExecute.teleportPlayer(event.getPlayer(), "lobby");
                    break;
                case COMPASS:
                    MenuManager.getMenu("Jogadores").openMenu(event.getPlayer());
                    break;
                default:
                    break;
            }
        }

    }

}
