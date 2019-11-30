package com.midup.bedwars.spectator.menus;

import com.midup.inventory.ItemCustom;
import com.midup.menucustom.models.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author cirov
 */
public class MenuFligth extends Menu {

    public MenuFligth() {
        super("Opções de Vôo");
    }

    @Override
    public void onClickMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        switch (event.getSlot()) {
            case 10:
                player.setFlySpeed(0.1F);
                player.sendMessage("§eDefinido velocidade §bI");
                break;
            case 13:
                player.setFlySpeed(0.3F);
                player.sendMessage("§eDefinido velocidade §3II");
                break;
            case 16:
                player.setFlySpeed(0.9F);
                player.sendMessage("§eDefinido velocidade §9III");
                break;
            default:
                break;
        }
        player.closeInventory();
    }

    @Override
    public void openMenu(Player player) {
        Inventory iv = Bukkit.createInventory(null, 27, getName());
        ItemStack velI = new ItemCustom().type(Material.IRON_BOOTS).name("§bVelocidade I").build();
        ItemStack velII = new ItemCustom().type(Material.GOLD_BOOTS).name("§3Velocidade II").build();
        ItemStack velIII = new ItemCustom().type(Material.DIAMOND_BOOTS).name("§9Velocidade III").build();
        iv.setItem(10, velI);
        iv.setItem(13, velII);
        iv.setItem(16, velIII);
        player.openInventory(iv);
    }

}
