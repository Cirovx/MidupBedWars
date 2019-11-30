package com.midup.bedwars.spectator.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.user.User;
import com.midup.inventory.ItemCustom;
import com.midup.menucustom.models.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author cirov
 */
public class MenuTeleporter extends Menu {

    public MenuTeleporter() {
        super("Jogadores");
    }

    @Override
    public void onClickMenu(InventoryClickEvent event) {
        String clicked = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        Player player = Bukkit.getPlayer(clicked);
        if (player != null) {
            event.getWhoClicked().teleport(player.getLocation());
            event.getWhoClicked().sendMessage("§eTeleportado para " + clicked);
        }
    }

    @Override
    public void openMenu(Player player) {
        Inventory iv = Bukkit.createInventory(null, 27, getName());
        for (User user : Main.getUserManager().getAllUsers()) {
            if (user.isLive()) {
                Player pOn = Bukkit.getPlayer(user.getUuid());
                if (pOn != null) {
                    iv.addItem(new ItemCustom().ownerSkull(pOn.getName(), user.getTeam().getType().getColorCode() + "" + pOn.getName()).addLore("§7Clique para teleportar-se").build());
                }
            }
        }
        player.openInventory(iv);
    }

}
