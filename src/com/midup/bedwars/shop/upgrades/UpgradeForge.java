package com.midup.bedwars.shop.upgrades;

import com.midup.bedwars.Main;
import com.midup.bedwars.generator.models.GeneratorType;
import com.midup.bedwars.message.Message;
import com.midup.bedwars.shop.economy.Currency;
import com.midup.bedwars.shop.models.Upgrade;
import com.midup.bedwars.team.Team;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class UpgradeForge extends Upgrade {

    public UpgradeForge() {
        super(Currency.DIAMOND, 2, "Gerador", 0, 4, Material.FURNACE);
    }

    @Override
    protected void execute(Player player) {
        Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
        switch (getLevel()) {
            case 0:
                team.setTimeGenerators(GeneratorType.IRON, 2);
                team.setTimeGenerators(GeneratorType.GOLD, 8);
                break;
            case 1:
                team.setTimeGenerators(GeneratorType.IRON, 1);
                team.setTimeGenerators(GeneratorType.GOLD, 5);
                break;
            case 2:
                team.activateGenerators(GeneratorType.EMERALD, true);
                break;
            case 3:
                team.setAmountSpawnGenerator(GeneratorType.IRON, 2);
                team.setTimeGenerators(GeneratorType.GOLD, 3);
                team.setTimeGenerators(GeneratorType.EMERALD, 30);
                break;
        }
        this.setLevel(getLevel() + 1);
        this.setPrice(getPrice() * 2);
    }

    @Override
    public List<String> getDescription(Player player) {
        List<String> lore = new ArrayList<>();
        if (getLevel() < getMaxLevel()) {
            switch (getLevel()) {
                case 0:
                    lore.add("§7Evolua o gerador de sua ilha.");
                    lore.add("§7Aumente em 50% o drop de");
                    lore.add("§7ferro e ouro do gerador.");
                    lore.add("");
                    lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
                    break;
                case 1:
                    lore.add("§7Evolua o gerador de sua ilha.");
                    lore.add("§7Aumente em 100% o drop de");
                    lore.add("§7ferro e ouro do gerador.");
                    lore.add("");
                    lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
                    break;
                case 2:
                    lore.add("§7Evolua o gerador de sua ilha.");
                    lore.add("§7Adicione o spawn de ");
                    lore.add("§7esmeraldas no gerador.");
                    lore.add("");
                    lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
                    break;
                case 3:
                    lore.add("§7Evolua o gerador de sua ilha.");
                    lore.add("§7Aumente em 200% o drop de ferro");
                    lore.add("§7ouro e esmeralda do gerador.");
                    lore.add("");
                    lore.add("§7Valor: §b" + getPrice() + " " + getCurrency().getDisplay());
                    break;
            }
        } else {
            lore.add("§7Seu gerador j§ esta a todo vapor!");
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
