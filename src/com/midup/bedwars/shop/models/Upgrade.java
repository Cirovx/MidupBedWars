package com.midup.bedwars.shop.models;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.user.UserStatus;
import com.midup.inventory.ItemCustom;
import com.midup.title.Title;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Upgrade {

    private Currency currency;
    private int price;
    private String name;
    private int level;
    private int maxLevel;
    private Material icon;

    public Upgrade(Currency currency, int price, String name, int level, int maxLevel, Material icon) {
        this.currency = currency;
        this.price = price;
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.icon = icon;
    }

    protected abstract void execute(Player player);

    public void buy(Player player, Currency currency, int price) {
        if (Main.getEconomyManager().hasBalance(player, currency, price)) {
            Main.getEconomyManager().withdrawn(player, getCurrency(), getPrice());
            Message.PLAYER_HAS_BUY.sendTeamMessage(player, player.getName(), getMaxLevel() > 1 ? this.getName() + " " + (getLevel() + 1) : this.getName());
            execute(player);
            player.closeInventory();
        } else {
            Message.YOU_DONT_HAVE_BALANCE.sendPlayerMessage(player);
        }
    }

    public ItemStack getIcon(Player player) {
        ItemCustom item = new ItemCustom();
        item.type(getIconType());
        item.lore(getDescription(player));
        String nameColor = "§e";
        if (getLevel() == getMaxLevel()) {
            nameColor = "§a";
        } else {
            nameColor = Main.getEconomyManager().hasBalance(player, getCurrency(), getPrice()) ? "§e" : "§c";
        }
        item.name(nameColor + getName());
        item.addFlags(ItemFlag.HIDE_ATTRIBUTES);
        return item.build();
    }

    public Currency getCurrency() {
        return currency;
    }

    public Material getIconType() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public abstract List<String> getDescription(Player player);

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public static void checkActivatedUpgrades() {
        Main.getUserManager().getAllOnlineUsers().forEach(user -> {
            if (user.getStatus() == UserStatus.PLAYING) {
                Main.getTeamManager().getTeams().forEach(team -> {
                    double distance = user.getPlayer().getLocation().distance(team.getSpawn());
                    if (user.getTeam() == team) {
                        if (team.getUpgrade("Regeneração").getLevel() > 0) {
                            if (distance <= 5) {
                                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 0));
                            }
                        }
                    } else {
                        if (team.getUpgrade("Armadilha Poderosa").getLevel() > 0) {
                            if (distance <= 15) {
                                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 1));
                                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 1));
                                Title title = new Title(user.getDisplayName() + " §cINVADIU SUA BASE");
                                team.getUpgrade("Armadilha Poderosa").setLevel(0);
                                team.getUsers().forEach(userOn -> {
                                    if (user.isOnline()) {
                                        title.send(userOn.getPlayer());
                                    }
                                });
                            }
                        } else if (team.getUpgrade("Armadilha de Fadiga").getLevel() > 0) {
                            if (distance <= 15) {
                                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 10, 1));
                                Title title = new Title(user.getDisplayName() + " §cINVADIU SUA BASE");
                                team.getUpgrade("Armadilha de Fadiga").setLevel(0);
                                team.getUsers().forEach(userOn -> {
                                    if (user.isOnline()) {
                                        title.send(userOn.getPlayer());
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

    }

}
