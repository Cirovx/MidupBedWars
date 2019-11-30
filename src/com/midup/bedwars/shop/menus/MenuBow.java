package com.midup.bedwars.shop.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.economy.EconomyManager;
import com.midup.inventory.ItemCustom;
import com.midup.menucustom.manager.MenuManager;
import com.midup.menucustom.models.Menu;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

/**
 *
 * @author cirov
 */
public class MenuBow extends Menu {

    private EconomyManager economy;

    public MenuBow() {
        super("Arcos");
        this.economy = Main.getEconomyManager();
    }

    @Override
    public void onClickMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String nameItem = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        if (nameItem.equalsIgnoreCase("Voltar")) {
            MenuManager.getMenu("Loja Itens").openMenu(player);
            return;
        } else {
            List<String> lore = event.getCurrentItem().getItemMeta().getLore();
            if (economy.hasBalance(player, economy.getCurrencyByLore(lore), economy.getAmountByLore(lore))) {
                economy.withdrawn(player, economy.getCurrencyByLore(lore), economy.getAmountByLore(lore));
                ItemCustom buy = new ItemCustom();
                buy.type(event.getCurrentItem().getType());
                buy.amount(event.getCurrentItem().getAmount());
                buy.data((short) event.getCurrentItem().getDurability());
                buy.addEnchants(event.getCurrentItem().getEnchantments());

                player.getInventory().addItem(buy.build());
                player.updateInventory();

                Message.YOU_HAS_BUY.sendPlayerMessage(player, nameItem);
            } else {
                Message.YOU_DONT_HAVE_BALANCE.sendPlayerMessage(player);
            }
        }
    }

    @Override
    public void openMenu(Player player) {
        Inventory iv = Bukkit.createInventory(null, 27, getName());
        String color = "§e";
        ItemCustom arrow = new ItemCustom().type(Material.ARROW).name(color + "Flechas").amount(8).addLore("§8Itens").addLore(" §7▪ Flecha").addLore(" ").addLore("§7Valor: §62 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 2));
        ItemCustom bow1 = new ItemCustom().type(Material.BOW).name(color + "Arco").addLore("§8Itens").addLore(" §7▪ Arco").addLore(" ").addLore("§7Valor: §612 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 12));
        ItemCustom bow2 = new ItemCustom().type(Material.BOW).name(color + "Arco").addEnchant(Enchantment.ARROW_DAMAGE, 1).addLore("§8Itens").addLore(" §7▪ Arco (Força I)").addLore(" ").addLore("§7Valor: §624 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 24));
        bow2.addFlags(ItemFlag.HIDE_ENCHANTS);
        ItemCustom bow3 = new ItemCustom().type(Material.BOW).name(color + "Arco").addEnchant(Enchantment.ARROW_DAMAGE, 1).addEnchant(Enchantment.ARROW_KNOCKBACK, 1).addLore("§8Itens").addLore(" §7▪ Arco (Força I, Repulçao I)").addLore(" ").addLore("§7Valor: §26 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 6));
        bow3.addFlags(ItemFlag.HIDE_ENCHANTS);
        iv.setItem(10, arrow.build());
        iv.setItem(11, bow1.build());
        iv.setItem(12, bow2.build());
        iv.setItem(13, bow3.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());

        player.openInventory(iv);
    }

}
