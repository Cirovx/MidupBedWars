package com.midup.bedwars.shop.economy;

import com.midup.bedwars.message.Message;
import com.midup.inventory.InventoryUtils;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author cirov
 */
public class EconomyManager {

    public int getBalance() {
        return 0;
    }

    public boolean hasBalance(Player player, Currency currency, int amount) {
        int has = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (item.getType() == currency.getMaterial()) {
                    has += item.getAmount();
                }
            }
        }
        return has >= amount;
    }

    public String getMessagePerBalance(Player player, Currency currency, int amount) {
        return hasBalance(player, currency, amount) ? Message.CLICK_TO_BUY.getMessage() : Message.YOU_DONT_HAVE_BALANCE.getMessage();
    }

    public void withdrawn(Player player, Currency currency, int amount) {
        InventoryUtils.removeItem(player.getInventory(), new ItemStack(currency.getMaterial()), amount);
    }

    public int getAmountByLore(List<String> lore) {
        return Integer.parseInt(ChatColor.stripColor(lore.get((lore.size() - 3)).split(" ")[1]));
    }

    public Currency getCurrencyByLore(List<String> lore) {
        return Currency.getCurrencyByName(ChatColor.stripColor(lore.get((lore.size() - 3)).split(" ")[2]));
    }

}
