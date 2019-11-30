package com.midup.bedwars.shop.upgrades;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.models.Upgrade;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UpgradeFatigue extends Upgrade {

    public UpgradeFatigue() {
        super(Currency.DIAMOND, 2, "Armadilha de Fadiga", 0, 1, Material.IRON_PICKAXE);
    }

    @Override
    protected void execute(Player player) {
        setLevel(getLevel() + 1);
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> lore = new ArrayList<>();
        if (getLevel() < getMaxLevel()) {
            lore.add("§7O proximo inigmigo que entrar em sua");
            lore.add("§7base, receberá fadiga por 10 segundos.");
            lore.add("");
            lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
        } else {
            lore.add("§7Esta armadilha ja esta armada!");
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
