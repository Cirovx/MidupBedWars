package com.midup.bedwars.generator;

import com.midup.bedwars.Main;
import com.midup.bedwars.generator.models.GeneratorType;
import com.midup.bedwars.generator.models.GlobalGenerator;
import com.midup.bedwars.generator.models.IslandGenerator;
import com.midup.bedwars.team.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author cirov
 */
public class GeneratorManager {

    private List<GlobalGenerator> generators = new ArrayList<>();
    private int dropTimeEmerald = 65;
    private int dropTimeDiamond = 30;

    public void load() {
        Main.getWorldManager().loadGlobalGenerators();
    }

    public int getDropTimeEmerald() {
        return dropTimeEmerald;
    }

    public void setDropTimeEmerald(int dropTimeEmerald) {
        this.dropTimeEmerald = dropTimeEmerald;
    }

    public int getDropTimeDiamond() {
        return dropTimeDiamond;
    }

    public void setDropTimeDiamond(int dropTimeDiamond) {
        this.dropTimeDiamond = dropTimeDiamond;
    }

    public List<GlobalGenerator> getGenerators() {
        return Collections.unmodifiableList(this.generators);
    }

    public void execute() {
        for (Team team : Main.getTeamManager().getTeams()) {
            for (IslandGenerator generator : team.getGenerators()) {
                generator.run();
            }
        }
        for (GlobalGenerator generator : this.generators) {
            generator.run();
        }
    }

    public void addGlobalGenerator(GeneratorType type, Location location) {
        GlobalGenerator generator = new GlobalGenerator(type, location);
        this.generators.add(generator);
    }

    public void startUpdateEffect() {
        new BukkitRunnable() {

            double rotation = 5;
            double crotation;

            @Override
            public void run() {
                crotation += rotation / 100;
                if (crotation >= 360) {
                    crotation -= 360;
                }
                for (GlobalGenerator gen : getGenerators()) {
                    gen.getArmorStand().setHeadPose(gen.getArmorStand().getHeadPose().setY(crotation));
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

}
