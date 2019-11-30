package com.midup.bedwars.shop.economy;

import org.bukkit.Material;

/**
 *
 * @author cirov
 */
public enum Currency {

    IRON_INGOT("Ferro", "§f"), GOLD_INGOT("Ouro", "§6"), DIAMOND("Diamante", "§b"), EMERALD("Esmeralda", "§2");

    private String name;
    private String color;

    private Currency(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDisplay() {
        return getColor() + getName();
    }

    public static Currency getCurrencyByName(String name) {
        String currencyName = getEnumName(name);
        if (currencyName != null) {
            return Currency.valueOf(currencyName);
        } else {
            return null;
        }
    }

    private static String getEnumName(String name) {
        if (name.equalsIgnoreCase("ferro")) {
            return "IRON_INGOT";
        } else if (name.equalsIgnoreCase("ouro")) {
            return "GOLD_INGOT";
        } else if (name.equalsIgnoreCase("esmeralda")) {
            return "EMERALD";
        } else if (name.equalsIgnoreCase("diamante")) {
            return "DIAMOND";
        } else {
            return null;
        }
    }

    public Material getMaterial() {
        if (this.name.equalsIgnoreCase("ferro")) {
            return Material.IRON_INGOT;
        } else if (this.name.equalsIgnoreCase("ouro")) {
            return Material.GOLD_INGOT;
        } else if (this.name.equalsIgnoreCase("esmeralda")) {
            return Material.EMERALD;
        } else if (this.name.equalsIgnoreCase("diamante")) {
            return Material.DIAMOND;
        } else {
            return null;
        }
    }

}
