package com.midup.bedwars.generator.models;

import org.bukkit.Material;

/**
 *
 * @author cirov
 */
public enum GeneratorType {

    IRON(Material.IRON_INGOT, "Ferro", "§f", 3), GOLD(Material.GOLD_INGOT, "Ouro", "§6", 15), EMERALD(Material.EMERALD, "Esmeralda", "§2", 60), DIAMOND(Material.DIAMOND, "Diamante", "§b", 40);

    private Material material;
    private String name;
    private String color;
    private int defaultTime;

    private GeneratorType(Material material, String name, String color, int defaultTime) {
        this.material = material;
        this.name = name;
        this.color = color;
        this.defaultTime = defaultTime;
    }

    public int getDefaultTime() {
        return defaultTime;
    }

    public String getName() {
        return name;
    }

    public String getColor(boolean bold) {
        return bold ? color + "§l" : color;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return getColor(false) + getName();
    }

}
