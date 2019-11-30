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
public class MenuUtilitiy extends Menu {

    private EconomyManager economy;

    public MenuUtilitiy() {
        super("Utilitarios");
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

                switch (event.getSlot()) {
                    case 10:
                        buy.type(Material.GOLDEN_APPLE);
                        break;
                    case 11:
                        buy.type(Material.SNOW_BALL);
                        buy.addFlags(ItemFlag.HIDE_ENCHANTS);
                        buy.addLore("§7Use as traças a seu favor.").addLore("§7Elas distraem seu inimigo,").addLore("§7enquanto voce o mata!").addLore("§7Fique tranquilo, elas nao lhe atacam.");
                        break;
                    case 12:
                        buy.type(Material.MONSTER_EGG);
                        buy.addLore("§7Uma ajudinha extra vai bem né.").addLore("§7Use o Golem para lhe ajudar").addLore("§7nesta batalha.").addLore("§7Fique tranquilo, ele nao lhe ataca.");
                        break;
                    case 13:
                        buy.type(Material.FIREBALL);
                        break;
                    case 14:
                        buy.type(Material.TNT);
                        break;
                    case 15:
                        buy.type(Material.ENDER_PEARL);
                        break;
                    case 16:
                        buy.type(Material.WATER_BUCKET);
                        break;
                }
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
        ItemCustom apple = new ItemCustom().type(Material.GOLDEN_APPLE).name(color + "Maça Dourada").addLore("§8Itens").addLore(" §7▪ Maça Dourada").addLore(" ").addLore("§7Valor: §63 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 3));
        ItemCustom snow = new ItemCustom().type(Material.SNOW_BALL).name(color + "Bola de Neve com Montros").addLore("§8Itens").addLore(" §7▪ Bola de neve com monstros").addLore(" ").addLore("§7Valor: §f50 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 50));
        snow.addEnchant(Enchantment.DURABILITY, 1);
        snow.addFlags(ItemFlag.HIDE_ENCHANTS);
        ItemCustom egg = new ItemCustom().type(Material.MONSTER_EGG).name(color + "Iron Golem").addLore("§8Itens").addLore(" §7▪ Iron Golem").addLore(" ").addLore("§7Valor: §f150 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 150));
        ItemCustom firaball = new ItemCustom().type(Material.FIREBALL).name(color + "Bola de Fogo").addLore("§8Itens").addLore(" §7▪ Bola de Fogo").addLore(" ").addLore("§7Valor: §f50 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 50));
        ItemCustom tnt = new ItemCustom().type(Material.TNT).name(color + "TNT").addLore("§8Itens").addLore(" §7▪ TNT").addLore(" ").addLore("§7Valor: §65 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 5));
        ItemCustom pearl = new ItemCustom().type(Material.ENDER_PEARL).name(color + "Perola do Fim").addLore("§8Itens").addLore(" §7▪ Pérola do Fim").addLore(" ").addLore("§7Valor: §24 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 2));
        ItemCustom bucket = new ItemCustom().type(Material.WATER_BUCKET).name(color + "Balde de Agua").addLore("§8Itens").addLore(" §7▪ Balde de Agua").addLore(" ").addLore("§7Valor: §21 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 1));

        iv.setItem(10, apple.build());
        iv.setItem(11, snow.build());
        iv.setItem(12, egg.build());
        iv.setItem(13, firaball.build());
        iv.setItem(14, tnt.build());
        iv.setItem(15, pearl.build());
        iv.setItem(16, bucket.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());
        player.openInventory(iv);
    }

}
