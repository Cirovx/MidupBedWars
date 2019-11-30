package com.midup.bedwars.shop.menus;

import com.midup.bedwars.Main;
import com.midup.bedwars.team.Team;
import com.midup.inventory.ItemCustom;
import com.midup.menucustom.manager.MenuManager;
import com.midup.menucustom.models.Menu;
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
public class MenuItems extends Menu {

    public MenuItems() {
        super("Loja Itens");
    }

    @Override
    public void onClickMenu(InventoryClickEvent event) {
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        String nameItem = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        Menu menu = MenuManager.getMenu(nameItem);
        if (menu != null) {
            menu.openMenu(player);
        }
    }

    @Override
    public void openMenu(Player player) {
        Inventory iv = Bukkit.createInventory(null, 36, "Loja Itens");
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        ItemCustom armor = new ItemCustom().type(Material.CHAINMAIL_BOOTS).name("§aArmaduras").addLore("§7Disponivel:").addLore(" §8▪ Calça de Malha").addLore(" §8▪ Bota de Malha").addLore(" §8▪ Calça de Ferro").addLore(" §8▪ Bota de Ferro").addLore(" §8▪ Calça de Diamante").addLore(" §8▪ Bota de Diamante").addLore(" ").addLore("§eClique para selecionar!");
        if (team.getEnchantArmor() != null) {
            armor.addEnchant(Enchantment.DURABILITY, 1);
            armor.addFlags(ItemFlag.HIDE_ENCHANTS);
        }
        ItemCustom gun = new ItemCustom().type(Material.GOLD_SWORD).name("§aArmas").addLore("§7Disponivel:").addLore(" §8▪ Espada de Pedra").addLore(" §8▪ Espada de Ferro").addLore(" §8▪ Espada de Diamante").addLore(" §8▪ Graveto de Repulçao").addLore(" ").addLore("§eClique para selecionar!");
        gun.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemCustom block = new ItemCustom().type(Material.SANDSTONE).name("§aBlocos").addLore("§7Disponivel:").addLore(" §8▪ Lã").addLore(" §8▪ Arenito").addLore(" §8▪ Pedra do Fim").addLore(" §8▪ Escada").addLore(" §8▪ Taboas").addLore(" §8▪ Obsidian").addLore(" ").addLore("§eClique para selecionar!");
        ItemCustom bow = new ItemCustom().type(Material.BOW).name("§aArcos").addLore("§7Disponivel:").addLore(" §8▪ Flecha").addLore(" §8▪ Arco").addLore(" §8▪ Arco (Força I)").addLore(" §8▪ Arco (Força I, Repulsao I)").addLore(" ").addLore("§eClique para selecionar!");
        ItemCustom tool = new ItemCustom().type(Material.STONE_PICKAXE).name("§aFerramentas").addLore("§7Disponivel:").addLore(" §8▪ Tesoura").addLore(" §8▪ Picareta de Madeira").addLore(" §8▪ Picareta de Pedra (Eficiencia I)").addLore(" §8▪ Picareta de Ferro (Eficiencia II)").addLore(" §8▪ Picareta de Diamante (Eficiencia III)").addLore(" §8▪ Machado de Diamante (Eficiencia II)").addLore(" ").addLore("§eClique para selecionar!");
        tool.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemCustom potion = new ItemCustom().type(Material.BREWING_STAND_ITEM).name("§aPocoes").addLore("§7Disponivel:").addLore(" §8▪ Poçao de Velocidade II (45 segundos)").addLore(" §8▪ Poçao de Super Pulo V (45 segundos)").addLore(" §8▪ Poçao de Invisibilidade (30 segundos)").addLore(" ").addLore("§eClique para selecionar!");
        ItemCustom util = new ItemCustom().type(Material.TNT).name("§aUtilitarios").addLore("§7Disponivel:").addLore(" §8▪ Maça Dourada").addLore(" §8▪ Bola de neve trapaceira").addLore(" §8▪ Spawner de Iron Golem").addLore(" §8▪ Bola de Fogo").addLore(" §8▪ TNT").addLore(" §8▪ Perola do Fim").addLore(" §8▪ Balde de Agua").addLore(" ").addLore("§eClique para selecionar!");

        iv.setItem(11, armor.build());
        iv.setItem(12, gun.build());
        iv.setItem(13, block.build());
        iv.setItem(14, bow.build());
        iv.setItem(15, tool.build());
        iv.setItem(20, potion.build());
        iv.setItem(21, util.build());

        player.openInventory(iv);
    }

}
