package com.midup.bedwars.shop.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.economy.EconomyManager;
import com.midup.bedwars.user.User;
import com.midup.inventory.ItemCustom;
import com.midup.menucustom.manager.MenuManager;
import com.midup.menucustom.models.Menu;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author cirov
 */
public class MenuBlocks extends Menu {

    private EconomyManager economy;

    public MenuBlocks() {
        super("Blocos");
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
        User user = Main.getUserManager().getUser(player.getUniqueId());
        String color = "§e";
        ItemCustom wool = new ItemCustom().type(Material.WOOL).name(color + "Lã").amount(16).addLore("§8Itens").addLore(" §7▪ Lã").addLore(" ").addLore("§7Valor: §f4 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 4));
        wool.data((short) (user.getTeam().getType().getIdWool()));
        ItemCustom sandstone = new ItemCustom().type(Material.SANDSTONE).name(color + "Arenito").amount(16).addLore("§8Itens").addLore(" §7▪ Arenito").addLore(" ").addLore("§7Valor: §f12 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 12));
        ItemCustom enderstone = new ItemCustom().type(Material.ENDER_STONE).name(color + "Pedra do Fim").amount(12).addLore("§8Itens").addLore(" §7▪ Pedra do Fim").addLore(" ").addLore("§7Valor: §f24 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 24));
        ItemCustom ladder = new ItemCustom().type(Material.LADDER).name(color + "Escada").amount(16).addLore("§8Itens").addLore(" §7▪ Escada").addLore(" ").addLore("§7Valor: §f4 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 4));
        ItemCustom wood = new ItemCustom().type(Material.WOOD).name(color + "Táboas").amount(16).addLore("§8Itens").addLore(" §7▪ Táboas").addLore(" ").addLore("§7Valor: §64 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 4));
        ItemCustom obsidian = new ItemCustom().type(Material.OBSIDIAN).name(color + "Obsidian").amount(4).addLore("§8Itens").addLore(" §7▪ Obsidian").addLore(" ").addLore("§7Valor: §24 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 4));

        iv.setItem(10, wool.build());
        iv.setItem(11, sandstone.build());
        iv.setItem(12, enderstone.build());
        iv.setItem(13, ladder.build());
        iv.setItem(14, wood.build());
        iv.setItem(15, obsidian.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());
        player.openInventory(iv);
    }

}
