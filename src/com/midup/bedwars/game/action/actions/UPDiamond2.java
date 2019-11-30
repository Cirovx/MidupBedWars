package com.midup.bedwars.game.action.actions;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.action.Action;
import com.midup.bedwars.generator.models.GeneratorType;
import org.bukkit.Bukkit;

public class UPDiamond2 extends Action {

    public UPDiamond2() {
        super("Diamante FULL", 360);
    }

    @Override
    public void run() {
        Main.getGeneratorManager().getGenerators().forEach(generator -> {
            if (generator.getType() == GeneratorType.DIAMOND) {
                generator.setLevel(3);
                generator.setSpawnTime(20);
                generator.setupHologram();
            }
        });
        Bukkit.broadcastMessage("§eO gerador de diamante está no nivel máximo");

    }

}
