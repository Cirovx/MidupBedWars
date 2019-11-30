package com.midup.bedwars.game.action.actions;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.action.Action;
import com.midup.bedwars.generator.models.GeneratorType;
import org.bukkit.Bukkit;

public class UPEmerald2 extends Action {

    public UPEmerald2() {
        super("Esmeralda FULL", 360);
    }

    @Override
    public void run() {
        Main.getGeneratorManager().getGenerators().forEach(generator -> {
            if (generator.getType() == GeneratorType.EMERALD) {
                generator.setLevel(3);
                generator.setSpawnTime(40);
                generator.setupHologram();
            }
        });
        Bukkit.broadcastMessage("§eO gerador de esmeralda está no nivel máximo");
    }

}
