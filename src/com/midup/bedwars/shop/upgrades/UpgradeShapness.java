package com.midup.bedwars.shop.upgrades;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.models.Upgrade;
import com.midup.bedwars.team.Team;
import com.midup.integer.IntegerUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class UpgradeShapness extends Upgrade {

    public UpgradeShapness() {
        super(Currency.DIAMOND, 4, "Afiação", 0, 1, Material.IRON_SWORD);

    }

    @Override
    protected void execute(Player player) {
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        team.setEnchantSword(Enchantment.DAMAGE_ALL);
        team.setEnchantSwordLevel(1);
        this.setLevel(1);
        Main.getTeamManager().updateSword(team.getType());
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> lore = new ArrayList<>();
        lore.add("§7Todos os membros da equipe");
        if (getLevel() < getMaxLevel()) {
            lore.add("§7ganham afiaçao " + IntegerUtils.ConvertToRoman((getLevel() + 1)) + " na espada.");
            lore.add("");
            lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
        } else {
            lore.add("§7ja possuem proteção " + IntegerUtils.ConvertToRoman(getMaxLevel()) + " na espada.");
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
