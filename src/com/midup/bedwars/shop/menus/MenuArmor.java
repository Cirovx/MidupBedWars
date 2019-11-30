package com.midup.bedwars.shop.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.economy.EconomyManager;
import com.midup.bedwars.team.Team;
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
import org.bukkit.inventory.ItemFlag;

/**
 *
 * @author cirov
 */
public class MenuArmor extends Menu {

    private EconomyManager economy;

    public MenuArmor() {
        super("Armaduras");
        economy = Main.getEconomyManager();
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
                User user = Main.getUserManager().getUser(player.getUniqueId());
                ItemCustom leggings = new ItemCustom();
                ItemCustom boots = new ItemCustom();
                switch (event.getSlot()) {
                    case 10:
                        leggings.type(Material.CHAINMAIL_LEGGINGS);
                        boots.type(Material.CHAINMAIL_BOOTS);
                        break;
                    case 11:
                        leggings.type(Material.IRON_LEGGINGS);
                        boots.type(Material.IRON_BOOTS);
                        break;
                    case 12:
                        leggings.type(Material.DIAMOND_LEGGINGS);
                        boots.type(Material.DIAMOND_BOOTS);
                        break;
                }
                Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
                if (team.getEnchantArmor() != null) {
                    leggings.addEnchant(team.getEnchantArmor(), team.getEnchantArmorLevel());
                    boots.addEnchant(team.getEnchantArmor(), team.getEnchantArmorLevel());
                }
                player.getInventory().setLeggings(leggings.build());
                player.getInventory().setBoots(boots.build());
                user.getInventory().setLeggings(leggings.build());
                user.getInventory().setBoots(boots.build());
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
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        String color = "§e";
        ItemCustom chain = new ItemCustom().type(Material.CHAINMAIL_BOOTS).name(color + "Armadura de Malha").addLore("§8Itens").addLore(" §7▪ Calça de malha").addLore(" §7▪ Bota de malha").addLore(" ").addLore("§8§oVoce nao perderá ao morrer!").addLore(" ").addLore("§7Valor: §f40 Ferro").addLore("").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 40));
        ItemCustom iron = new ItemCustom().type(Material.IRON_BOOTS).name(color + "Armadura de Ferro").addLore("§8Itens").addLore(" §7▪ Calça de Ferro").addLore(" §7▪ Bota de Ferro").addLore(" ").addLore("§8§oVoce nao perderá ao morrer!").addLore(" ").addLore("§7Valor: §612 Ouro").addLore("").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 12));
        ItemCustom diamond = new ItemCustom().type(Material.DIAMOND_BOOTS).name(color + "Armadura de Diamante").addLore("§8Itens").addLore(" §7▪ Calça de Diamante").addLore(" §7▪ Bota de Diamante").addLore(" ").addLore("§8§oVoce nao perderá ao morrer!").addLore(" ").addLore("§7Valor: §26 Esmeralda").addLore("").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 6));
        if (team.getEnchantArmor() != null) {
            chain.addEnchant(team.getEnchantArmor(), team.getEnchantArmorLevel()).addFlags(ItemFlag.HIDE_ENCHANTS);
            iron.addEnchant(team.getEnchantArmor(), team.getEnchantArmorLevel()).addFlags(ItemFlag.HIDE_ENCHANTS);
            diamond.addEnchant(team.getEnchantArmor(), team.getEnchantArmorLevel()).addFlags(ItemFlag.HIDE_ENCHANTS);
        }
        iv.setItem(10, chain.build());
        iv.setItem(11, iron.build());
        iv.setItem(12, diamond.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());

        player.openInventory(iv);
    }
}
