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
public class MenuTools extends Menu {

    private EconomyManager economy;

    public MenuTools() {
        super("Ferramentas");
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
        ItemCustom shears = new ItemCustom().type(Material.SHEARS).name(color + "Tesoura").addLore("§8Itens").addLore(" §7▪ Tesoura").addLore(" ").addLore("§7Valor: §f30 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 30));
        ItemCustom wood = new ItemCustom().type(Material.WOOD_PICKAXE).name(color + "Picareta de Madeira").addLore("§8Itens").addLore(" §7▪ Picareta de Madeira").addLore(" ").addLore("§7Valor: §f10 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 10));
        wood.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemCustom stone = new ItemCustom().type(Material.STONE_PICKAXE).name(color + "Picareta de Pedra").addEnchant(Enchantment.DIG_SPEED, 1).addLore("§8Itens").addLore(" §7▪ Picareta de Pedra (Eficiencia I)").addLore(" ").addLore("§7Valor: §f20 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 20));
        stone.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        stone.addFlags(ItemFlag.HIDE_ENCHANTS);
        ItemCustom iron = new ItemCustom().type(Material.IRON_PICKAXE).name(color + "Picareta de Ferro").addEnchant(Enchantment.DIG_SPEED, 2).addLore("§8Itens").addLore(" §7▪ Pidareta de Ferro (Eficiencia II)").addLore(" ").addLore("§7Valor: §68 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 8));
        iron.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        iron.addFlags(ItemFlag.HIDE_ENCHANTS);
        ItemCustom diamond = new ItemCustom().type(Material.DIAMOND_PICKAXE).name(color + "Picareta de Diamante").addEnchant(Enchantment.DIG_SPEED, 3).addLore("§8Itens").addLore(" §7▪ Picareta de Diamante (Eficiencia III)").addLore(" ").addLore("§7Valor: §612 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 12));
        diamond.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        diamond.addFlags(ItemFlag.HIDE_ENCHANTS);
        ItemCustom axe = new ItemCustom().type(Material.DIAMOND_AXE).name(color + "Machado de Diamante").addEnchant(Enchantment.DIG_SPEED, 2).addLore("§8Itens").addLore(" §7▪ Machado de Diamante (Eficiencia II)").addLore(" ").addLore("§7Valor: §612 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 12));
        axe.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        axe.addFlags(ItemFlag.HIDE_ENCHANTS);

        iv.setItem(10, shears.build());
        iv.setItem(11, wood.build());
        iv.setItem(12, stone.build());
        iv.setItem(13, iron.build());
        iv.setItem(14, diamond.build());
        iv.setItem(15, axe.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());
        player.openInventory(iv);
    }

}
