package com.midup.bedwars.game.action.actions;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.action.Action;
import com.midup.bedwars.generator.models.GeneratorType;
import org.bukkit.Bukkit;

public class UPDiamond1 extends Action {

    public UPDiamond1() {
        super("Upgrade Diamante", 360);
    }

    @Override
    public void run() {
        Main.getGeneratorManager().getGenerators().forEach(generator -> {
            if (generator.getType() == GeneratorType.DIAMOND) {
                generator.setLevel(2);
                generator.setSpawnTime(25);
                generator.setupHologram();
            }
        });
        Bukkit.broadcastMessage("§eO gerador de diamante está nivel II");
    }

}
