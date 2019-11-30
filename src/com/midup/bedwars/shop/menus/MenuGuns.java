package com.midup.bedwars.shop.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.economy.EconomyManager;
import com.midup.bedwars.team.Team;
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
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author cirov
 */
public class MenuGuns extends Menu {

    private EconomyManager economy;

    public MenuGuns() {
        super("Armas");
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
            Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
            if (economy.hasBalance(player, economy.getCurrencyByLore(lore), economy.getAmountByLore(lore))) {
                ItemCustom gun = new ItemCustom();
                economy.withdrawn(player, economy.getCurrencyByLore(lore), economy.getAmountByLore(lore));
                switch (event.getSlot()) {
                    case 10:
                        gun.type(Material.STONE_SWORD);
                        break;
                    case 11:
                        gun.type(Material.IRON_SWORD);
                        break;
                    case 12:
                        gun.type(Material.DIAMOND_SWORD);
                        break;
                    case 13:
                        gun.type(Material.STICK);
                        break;
                }
                if (event.getSlot() == 13) {
                    gun.addEnchant(Enchantment.KNOCKBACK, 1);
                } else {
                    if (team.getEnchantSword() != null) {
                        gun.addEnchant(team.getEnchantSword(), team.getEnchantSwordLevel());
                        Main.getTeamManager().updateSword(team.getType());
                    }
                }

                if (event.getCurrentItem().getType().name().contains("SWORD")) {
                    int aux = 0;
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item != null && item.getType() != Material.AIR) {
                            if (item.getType() == Material.WOOD_SWORD) {
                                player.getInventory().setItem(aux, gun.build());
                                aux = -1;
                                break;
                            }
                        }
                        aux++;
                    }
                    if (aux > 0 - 1) {
                        player.getInventory().addItem(gun.build());
                    }
                } else {
                    player.getInventory().addItem(gun.build());
                }

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
        ItemCustom stone = new ItemCustom().type(Material.STONE_SWORD).name(color + "Espada de Pedra").addLore("§8Itens").addLore(" §7▪ Espada de Pedra").addLore(" ").addLore("§7Valor: §f10 Ferro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.IRON_INGOT, 10));
        stone.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemCustom iron = new ItemCustom().type(Material.IRON_SWORD).name(color + "Espada de Ferro").addLore("§8Itens").addLore(" §7▪ Espada de Ferro").addLore(" ").addLore("§7Valor: §67 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 7));
        iron.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemCustom diamond = new ItemCustom().type(Material.DIAMOND_SWORD).name(color + "Espada de Diamante").addLore("§8Itens").addLore(" §7▪ Espada de Diamante").addLore(" ").addLore("§7Valor: §24 Esmeralda").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.EMERALD, 4));
        diamond.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemCustom stick = new ItemCustom().type(Material.STICK).name(color + "Graveto de Repulsao").addEnchant(Enchantment.KNOCKBACK, 1).addLore("§8Itens").addLore(" §7▪ Graveto (Repulsao I)").addLore(" ").addLore("§7Valor: §610 Ouro").addLore(" ").addLore(Main.getEconomyManager().getMessagePerBalance(player, Currency.GOLD_INGOT, 10));
        stick.addFlags(ItemFlag.HIDE_ENCHANTS);
        iv.setItem(10, stone.build());
        iv.setItem(11, iron.build());
        iv.setItem(12, diamond.build());
        iv.setItem(13, stick.build());

        iv.setItem(22, new ItemCustom().type(Material.ARROW).name("§aVoltar").build());
        player.openInventory(iv);
    }

}
