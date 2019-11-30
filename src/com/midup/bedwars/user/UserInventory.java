package com.midup.bedwars.user;

import com.midup.bedwars.Main;
import com.midup.bedwars.team.Team;
import com.midup.bedwars.team.TeamType;
import com.midup.inventory.ItemCustom;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UserInventory {

    private TeamType type;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private ItemStack sword;

    public UserInventory(TeamType type) {
        this.type = type;
        this.sword = new ItemStack(Material.WOOD_SWORD);
        this.sword.getItemMeta().spigot().setUnbreakable(true);
        setupArmor();
    }

    void setupArmor() {
        this.helmet = new ItemCustom().type(Material.LEATHER_HELMET).setColor(this.type.getColor()).build();
        this.helmet.getItemMeta().spigot().setUnbreakable(true);
        this.chestplate = new ItemCustom().type(Material.LEATHER_CHESTPLATE).setColor(this.type.getColor()).build();
        this.chestplate.getItemMeta().spigot().setUnbreakable(true);
        this.leggings = new ItemCustom().type(Material.LEATHER_LEGGINGS).setColor(this.type.getColor()).build();
        this.leggings.getItemMeta().spigot().setUnbreakable(true);
        this.boots = new ItemCustom().type(Material.LEATHER_BOOTS).setColor(this.type.getColor()).build();
        this.boots.getItemMeta().spigot().setUnbreakable(true);
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        this.chestplate = chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public void updateArmor(Player player) {
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        int enchantLevel = team.getUpgrade("Armadura Forte").getLevel();
        if (team.getEnchantArmor() != null) {
            getBoots().addEnchantment(team.getEnchantArmor(), enchantLevel);
            getLeggings().addEnchantment(team.getEnchantArmor(), enchantLevel);
            getHelmet().addEnchantment(team.getEnchantArmor(), enchantLevel);
            getChestplate().addEnchantment(team.getEnchantArmor(), enchantLevel);
        }
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());
        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.updateInventory();
    }

    public void updateSword(Player player) {
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        if (team.getEnchantSword() != null) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    if (item.getType().name().contains("SWORD")) {
                        item.addEnchantment(team.getEnchantSword(), team.getUpgrade("Afiação").getLevel());
                    }
                }
            }
        }
        player.updateInventory();
    }

    public void executePlayer(Player player) {
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        int enchantLevel = team.getUpgrade("Armadura Forte").getLevel();
        if (team.getEnchantArmor() != null) {
            getBoots().addEnchantment(team.getEnchantArmor(), enchantLevel);
            getLeggings().addEnchantment(team.getEnchantArmor(), enchantLevel);
            getHelmet().addEnchantment(team.getEnchantArmor(), enchantLevel);
            getChestplate().addEnchantment(team.getEnchantArmor(), enchantLevel);
        }
        player.getInventory().setLeggings(getLeggings());
        player.getInventory().setBoots(getBoots());
        player.getInventory().setHelmet(getHelmet());
        player.getInventory().setChestplate(getChestplate());
        player.getInventory().addItem(this.sword);
        updateSword(player);
        player.getInventory().setHeldItemSlot(0);
        player.updateInventory();
    }

}
