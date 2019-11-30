package com.midup.bedwars.generator.models;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.midup.bedwars.Main;
import com.midup.integer.IntegerUtils;
import com.midup.inventory.ItemCustom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 *
 * @author cirov
 */
public class GlobalGenerator {

    private GeneratorType type;
    private Location location;
    private int currentTime;
    private int spawnTime;
    private Hologram holo;
    private ArmorStand armorStand;
    private int level = 1;

    public GlobalGenerator(GeneratorType type, Location location) {
        this.type = type;
        this.location = location;
        this.currentTime = 1;
        setupGenerator();
        this.armorStand = setupArmorStand();
        setupHologram();
    }

    private void setupGenerator() {
        switch (this.type) {
            case DIAMOND:
                this.spawnTime = Main.getGeneratorManager().getDropTimeDiamond();
                break;
            case EMERALD:
                this.spawnTime = Main.getGeneratorManager().getDropTimeEmerald();
                break;
            default:
                break;
        }
    }

    private ArmorStand setupArmorStand() {
        ArmorStand as = (ArmorStand) Main.getWorldManager().getWorld().spawnEntity(getLocation().clone().add(0, 0.5, 0), EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setCustomNameVisible(false);
        as.setBasePlate(false);
        as.setHelmet(new ItemCustom().type(Material.valueOf(getType().getMaterial().name() + "_BLOCK")).addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 0).build());
        return as;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public void setupHologram() {
        if (getHolo() != null) {
            getHolo().delete();
        }
        Hologram holo = HologramsAPI.createHologram(Main.getInstance(), location.clone().add(0, 3.5, 0));
        holo.appendTextLine("§eVelocidade §c" + IntegerUtils.ConvertToRoman(getLevel()));
        holo.appendTextLine(getType().getColor(true) + getType().getName());
        holo.appendTextLine("§e");
        this.holo = holo;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void decrementCurrentTime() {
        this.currentTime--;
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
        this.currentTime = getSpawnTime();
    }

    public Hologram getHolo() {
        return holo;
    }

    public void run() {
        getHolo().getLine(2).removeLine();
        getHolo().appendTextLine("§eGerando em §c" + getCurrentTime() + " §esegundos...");
        if (getCurrentTime() <= 0) {
            Item item = Main.getWorldManager().getWorld().dropItem(getLocation().clone().add(0, 0.5, 0), new ItemStack(this.getType().getMaterial(), 1));
            item.setVelocity(new Vector());
            item.setTicksLived(Integer.MAX_VALUE);
            resetCurrentTime();
        } else {
            decrementCurrentTime();
        }
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
