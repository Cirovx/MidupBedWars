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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author cirov
 */
public class MenuPotions extends Menu {

    private EconomyManager economy;

    public MenuPotions() {
        super("Pocoes");
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
                buy.type(Material.POTION);
                switch (event.getSlot()) {
                    case 10:
                        buy.potion(PotionType.SPEED, PotionEffectType.SPEED, 20 * 45, 1, false);
                        break;
                    case 11:
                        buy.potion(PotionType.JUMP, PotionEffectType.JUMP, 20 * 45, 5, false);
                        break;
                    case 12:
                        buy.potion(PotionType.INVISIBILITY, PotionEffectType.INVISIBILITY, 20 * 30, 0, false);
                        break;
                }

                player.getInventory().addItem(buy.build());

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
        ItemCustom speed = new ItemCustom().type(Material.GLASS_BOTTLE).name(color + "Poçao de Velocide II").addLore("§8Itens").addLore(" §7▪ Poçao de Velocidade II (45 seg)").addLore(" ").addLore("§7Valor: §21 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 1));
        ItemCustom jump = new ItemCustom().type(Material.GLASS_BOTTLE).name(color + "Poçao de Pulo V").addLore("§8Itens").addLore(" §7▪ Poçao de Pulo V (45 seg)").addLore(" ").addLore("§7Valor: §21 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 1));
        ItemCustom invisibility = new ItemCustom().type(Material.GLASS_BOTTLE).name(color + "Poçao de Invisibilidade").addLore("§8Itens").addLore(" §7▪ Poçao de Invisibilidade (45 seg)").addLore(" ").addLore("§7Valor: §21 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 1));

        iv.setItem(10, speed.build());
        iv.setItem(11, jump.build());
        iv.setItem(12, invisibility.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());
        player.openInventory(iv);
    }

}
