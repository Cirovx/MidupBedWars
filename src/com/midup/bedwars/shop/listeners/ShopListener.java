package com.midup.bedwars.shop.listeners;

import com.midup.menucustom.manager.MenuManager;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 *
 * @author cirov
 */
public class ShopListener implements Listener {

    @EventHandler
    public void onDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Villager) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
            if (event.getRightClicked().hasMetadata("ShopItems")) {
                MenuManager.getMenu("Loja Itens").openMenu(event.getPlayer());
            } else if (event.getRightClicked().hasMetadata("ShopUpgrades")) {
                MenuManager.getMenu("Loja Melhorias").openMenu(event.getPlayer());
            }
        }
    }

}
