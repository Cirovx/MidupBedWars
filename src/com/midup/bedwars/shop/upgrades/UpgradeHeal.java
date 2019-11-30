package com.midup.bedwars.shop.upgrades;

import com.midup.bedwars.Main;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.models.Upgrade;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UpgradeHeal extends Upgrade {

    public UpgradeHeal() {
        super(Currency.DIAMOND, 1, "Regeneração", 0, 1, Material.BEACON);
    }

    @Override
    protected void execute(Player player) {
        setLevel(getLevel() + 1);
        setPrice(getPrice() * 2);
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> lore = new ArrayList<>();
        if (getLevel() < getMaxLevel()) {
            lore.add("§7Crie uma area de regeneração");
            lore.add("§7envolta de sua base.");
            lore.add("");
            lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
        } else {
            lore.add("§7Você jça está regenerado");
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
