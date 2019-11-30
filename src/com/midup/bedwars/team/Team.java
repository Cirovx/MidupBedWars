package com.midup.bedwars.team;

import com.midup.bedwars.Main;
import com.midup.bedwars.generator.models.GeneratorType;
import com.midup.bedwars.generator.models.IslandGenerator;
import com.midup.bedwars.shop.models.Upgrade;
import com.midup.bedwars.shop.upgrades.UpgradeArmor;
import com.midup.bedwars.shop.upgrades.UpgradeExpress;
import com.midup.bedwars.shop.upgrades.UpgradeFatigue;
import com.midup.bedwars.shop.upgrades.UpgradeForge;
import com.midup.bedwars.shop.upgrades.UpgradeHeal;
import com.midup.bedwars.shop.upgrades.UpgradeShapness;
import com.midup.bedwars.shop.upgrades.UpgradeSlowness;
import com.midup.bedwars.user.User;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;

/**
 *
 * @author cirov
 */
public class Team {

    private TeamType type;
    private int maxMembers;
    private List<User> users = new ArrayList<>();
    private Location spawn;
    private Location villagerItem;
    private Location villagerUpgrade;
    private Location chestLocation;
    private Location protectedRegionP1;
    private Location protectedRegionP2;
    private Enchantment enchantArmor;
    private int enchantArmorLevel;
    private Enchantment enchantSword;
    private int enchantSwordLevel;
    private Map<String, Upgrade> upgrades;
    private List<IslandGenerator> generators = new ArrayList<>();
    private Location bedLocation;
    private boolean hasBed = true;

    public Team(TeamType type) {
        this.type = type;
        this.maxMembers = Main.getGameManager().getGame().getPlayersPerTeam();
        this.upgrades = loadUpgrades();
    }

    public void brokenBed() {
        setHasBed(false);
        getBedLocation().getBlock().breakNaturally();
    }

    public Location getChestLocation() {
        return chestLocation;
    }

    public Location getProtectedRegionP1() {
        return protectedRegionP1;
    }

    public void setProtectedRegionP1(Location protectedRegionP1) {
        this.protectedRegionP1 = protectedRegionP1;
    }

    public Location getProtectedRegionP2() {
        return protectedRegionP2;
    }

    public void setProtectedRegionP2(Location protectedRegionP2) {
        this.protectedRegionP2 = protectedRegionP2;
    }

    public void setChestLocation(Location chestLocation) {
        this.chestLocation = chestLocation;
    }

    public boolean hasBed() {
        return hasBed;
    }

    public void setHasBed(boolean hasBed) {
        this.hasBed = hasBed;
    }

    public Enchantment getEnchantArmor() {
        return enchantArmor;
    }

    public int getEnchantArmorLevel() {
        return enchantArmorLevel;
    }

    public void setEnchantArmorLevel(int enchantArmorLevel) {
        this.enchantArmorLevel = enchantArmorLevel;
    }

    public int getEnchantSwordLevel() {
        return enchantSwordLevel;
    }

    public void setEnchantSwordLevel(int enchantSwordLevel) {
        this.enchantSwordLevel = enchantSwordLevel;
    }

    public void setEnchantArmor(Enchantment enchantArmor) {
        this.enchantArmor = enchantArmor;
    }

    public Enchantment getEnchantSword() {
        return enchantSword;
    }

    public void setEnchantSword(Enchantment enchantSword) {
        this.enchantSword = enchantSword;
    }

    public Location getLocationVillagerItem() {
        return villagerItem;
    }

    public void setLocationVillagerItem(Location villagerItem) {
        this.villagerItem = villagerItem;
    }

    public Location getLocationVillagerUpgrade() {
        return villagerUpgrade;
    }

    public Location getBedLocation() {
        return bedLocation;
    }

    public void setBedLocation(Location bedLocation) {
        this.bedLocation = bedLocation;
    }

    public void setLocationVillagerUpgrade(Location villagerUpgrade) {
        this.villagerUpgrade = villagerUpgrade;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void remUser(User user) {
        this.users.remove(user);
    }

    public TeamType getType() {
        return type;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<User> getLiveUsers() {
        List<User> users = new ArrayList<>();
        for (User user : getUsers()) {
            if (user.isLive()) {
                users.add(user);
            }
        }
        return users;
    }

    public User getUser(UUID uuid) {
        for (User user : this.users) {
            if (user.getUuid().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public Map<String, Upgrade> getUpgrades() {
        return this.upgrades;
    }

    public List<IslandGenerator> getGenerators() {
        return generators;
    }

    public void setTimeGenerators(GeneratorType type, int time) {
        getGenerators().forEach(gen -> {
            if (gen.getType() == type) {
                gen.setSpawnTime(time);
            }
        });
    }

    public void activateGenerators(GeneratorType type, boolean value) {
        getGenerators().forEach(gen -> {
            if (gen.getType() == type) {
                gen.setIsActivated(value);
            }
        });
    }

    public void setAmountSpawnGenerator(GeneratorType type, int amount) {
        getGenerators().forEach(gen -> {
            if (gen.getType() == type) {
                gen.setDropAmmount(amount);
            }
        });
    }

    public Upgrade getUpgrade(String name) {
        for (Upgrade upgrade : getUpgrades().values()) {
            if (upgrade.getName().equalsIgnoreCase(name)) {
                return upgrade;
            }
        }
        return null;
    }

    private Map<String, Upgrade> loadUpgrades() {
        Map<String, Upgrade> upgrades = new LinkedHashMap<>();

        UpgradeForge forge = new UpgradeForge();
        UpgradeExpress express = new UpgradeExpress();
        UpgradeShapness sharpness = new UpgradeShapness();
        UpgradeArmor armor = new UpgradeArmor();
        UpgradeSlowness slowness = new UpgradeSlowness();
        UpgradeFatigue fatigue = new UpgradeFatigue();
        UpgradeHeal heal = new UpgradeHeal();

        upgrades.put(forge.getName(), forge);
        upgrades.put(express.getName(), express);
        upgrades.put(sharpness.getName(), sharpness);
        upgrades.put(armor.getName(), armor);
        upgrades.put(slowness.getName(), slowness);
        upgrades.put(fatigue.getName(), fatigue);
        upgrades.put(heal.getName(), heal);
        return upgrades;
    }

    public void addGenerator(IslandGenerator generator) {
        this.generators.add(generator);
    }

}
