package com.midup.bedwars.generator.models;

import com.midup.bedwars.Main;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 *
 * @author cirov
 */
public class IslandGenerator {

    private GeneratorType type;
    private ItemStack drop;
    private Location location;
    private int currentTime;
    private int spawnTime;
    private boolean isActivated;

    public IslandGenerator(GeneratorType type, int spawnTime, ItemStack drop) {
        this.type = type;
        this.drop = drop;
        this.spawnTime = spawnTime;
        this.currentTime = 1;
        this.isActivated = type == GeneratorType.EMERALD ? false : true;
    }

    public void setIsActivated(boolean value) {
        this.isActivated = value;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void addCurrentTime() {
        this.currentTime++;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(int spawnTime) {
        this.spawnTime = spawnTime;
    }

    public void addSpawnTime() {
        this.spawnTime++;
    }

    public void resetCurrentTime() {
        this.currentTime = 1;
    }

    public void run() {
        if (isActivated) {
            if (getCurrentTime() >= getSpawnTime()) {
                Item item = Main.getWorldManager().getWorld().dropItem(getLocation().clone().add(0, 0.5, 0), getDrop());
                item.setVelocity(new Vector());
                resetCurrentTime();
            } else {
                addCurrentTime();
            }
        }
    }

    public ItemStack getDrop() {
        return drop;
    }

    public void setDropAmmount(int value) {
        getDrop().setAmount(value);
    }

    public GeneratorType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
