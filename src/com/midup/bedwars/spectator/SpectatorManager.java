package com.midup.bedwars.spectator;

import com.midup.bedwars.Main;
import com.midup.bedwars.user.UserStatus;
import com.midup.inventory.ItemCustom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author cirov
 */
public class SpectatorManager {

    private Map<UUID, Spectator> spectators = Collections.synchronizedMap(new HashMap<>());
    private GhostMaker ghostMaker;

    public SpectatorManager() {
        ghostMaker = new GhostMaker();
    }

    public GhostMaker getGhostMaker() {
        return ghostMaker;
    }

    public void setSpectator(Player player) {
        setupHotBar(player);
        Main.getUserManager().getUser(player.getUniqueId()).setStatus(UserStatus.SPEC);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 10));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 10));
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setFlySpeed(0.2F);
        player.teleport(Main.getWorldManager().getSpawn());
        ghostMaker.setGhost(player, true);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + player.getName() + " prefix §8");
    }

    private void setupHotBar(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(0, new ItemCustom().type(Material.COMPASS).name("§aJogadores").addLore("§7Teleporte-se até um").addLore("§7jogador vivo!").build());
        player.getInventory().setItem(4, new ItemCustom().type(Material.LEATHER_BOOTS).name("§aOpções de vôo").addLore("§7Confiure a velocidade de").addLore("§7vôo desejada!").build());
        player.getInventory().setItem(8, new ItemCustom().type(Material.BED).name("§aVoltar para o lobby").addLore("§7Volte para o lobby principal!").build());

        player.updateInventory();
    }

    public void setTemporarySpectator(Player player) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }
        Main.getUserManager().getUser(player.getUniqueId()).setStatus(UserStatus.TEMP_SPEC);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 10));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 10));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 4));
        player.spigot().setCollidesWithEntities(false);
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setFlySpeed(0.2F);
        player.teleport(Main.getWorldManager().getSpawn());
        player.updateInventory();
        Spectator spec = new Spectator(player.getUniqueId(), 5);
        this.spectators.put(spec.getUuid(), spec);
    }

    public void removeSpectator(Player player) {
        Main.getUserManager().getUser(player.getUniqueId()).setStatus(UserStatus.PLAYING);
        player.spigot().setCollidesWithEntities(true);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.teleport(Main.getUserManager().getUser(player.getUniqueId()).getTeam().getSpawn());
        Main.getUserManager().getUser(player.getUniqueId()).setupUser();
        Main.getUserManager().getUser(player.getUniqueId()).setLive(true);
    }

    public void execute() {
        List<Spectator> removeSpec = new ArrayList<>();
        for (Spectator spec : this.spectators.values()) {
            if (spec.run()) {
                removeSpec.add(spec);
            }
        }
        removeSpec.forEach(spec -> this.spectators.remove(spec.getUuid()));
    }

}
