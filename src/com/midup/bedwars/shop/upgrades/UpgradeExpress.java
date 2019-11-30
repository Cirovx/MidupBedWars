package com.midup.bedwars.shop.upgrades;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.models.Upgrade;
import com.midup.bedwars.team.Team;
import com.midup.bedwars.user.User;
import com.midup.integer.IntegerUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UpgradeExpress extends Upgrade {

    public UpgradeExpress() {
        super(Currency.DIAMOND, 2, "Pressa", 0, 2, Material.GOLD_PICKAXE);
    }

    @Override
    protected void execute(Player player) {
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        for (User user : team.getUsers()) {
            Player pOn = Bukkit.getPlayer(user.getUuid());
            if (pOn != null) {
                for (PotionEffect pe : player.getActivePotionEffects()) {
                    pOn.removePotionEffect(pe.getType());
                }
                pOn.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, getLevel()));
            }
        }
        setLevel(getLevel() + 1);
        setPrice(getPrice() * 2);
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("§7Todos os membros da equipe");
        if (getLevel() < getMaxLevel()) {
            lore.add("§7ganham pressa " + IntegerUtils.ConvertToRoman((getLevel() + 1)));
            lore.add("");
            lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
        } else {
            lore.add("§7ja possuem pressa " + IntegerUtils.ConvertToRoman(getMaxLevel()));
            lore.add("");
            lore.add(Message.YOU_HAS_ALREADY_MAXIMUM.getMessage());
        }
        lore.add("");
        if (getLevel() < getMaxLevel()) {
            lore.add(Main.getEconomyManager().getMessagePerBalance(player, Currency.DIAMOND, getPrice()));
        } else {
            lore.add("§aDESBLOQUEADO");
        }
        return lore;
    }

}
