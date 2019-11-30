package com.midup.bedwars.shop.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.economy.EconomyManager;
import com.midup.bedwars.shop.models.Upgrade;
import com.midup.bedwars.team.Team;
import com.midup.bedwars.user.User;
import com.midup.menucustom.models.Menu;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author cirov
 */
public class MenuUpgrades extends Menu {

    private EconomyManager economy;
    private int[] slots = new int[]{11, 12, 13, 14, 15, 20, 21};

    public MenuUpgrades() {
        super("Loja Melhorias");
        this.economy = Main.getEconomyManager();
    }

    @Override
    public void onClickMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String nameItem = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        List<String> lore = event.getCurrentItem().getItemMeta().getLore();
        Upgrade upgrade = team.getUpgrade(nameItem);

        if (upgrade != null) {
            Currency currency = economy.getCurrencyByLore(lore);
            if (currency != null) {
                upgrade.buy(player, currency, economy.getAmountByLore(lore));
            } else {
                Message.YOU_HAS_ALREADY_MAXIMUM.sendPlayerMessage(player);
                player.closeInventory();
            }
        }
    }

    @Override
    public void openMenu(Player player) {
        Inventory iv = Bukkit.createInventory(null, 36, getName());
        User user = Main.getUserManager().getUser(player.getUniqueId());

        int aux = 0;
        for (Upgrade upgrade : user.getTeam().getUpgrades().values()) {
            iv.setItem(this.slots[aux], upgrade.getIcon(player));
            aux++;
        }
        player.openInventory(iv);

    }

}
