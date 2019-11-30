package com.midup.bedwars.game.action.actions;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.action.Action;
import com.midup.bedwars.generator.models.GeneratorType;
import org.bukkit.Bukkit;

public class UPEmerald1 extends Action {

    public UPEmerald1() {
        super("Upgrade Esmeralda", 360);
    }

    @Override
    public void run() {
        Main.getGeneratorManager().getGenerators().forEach(generator -> {
            if (generator.getType() == GeneratorType.EMERALD) {
                generator.setLevel(2);
                generator.setSpawnTime(50);
                generator.setupHologram();
            }
        });
        Bukkit.broadcastMessage("§eO gerador de esmeralda está nivel II");
    }

}
