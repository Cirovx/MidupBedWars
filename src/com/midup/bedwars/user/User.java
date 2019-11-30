package com.midup.bedwars.user;

import com.midup.bedwars.Main;
import com.midup.bedwars.team.Team;
import com.midup.display.PlayerDisplay;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author cirov
 */
public class User implements Comparable<User> {

    private final UUID uuid;
    private final String name;
    private final String displayName;
    private Team team;
    private UserInventory inventory;
    private UserStatistics statistics;
    private UserStatus status;
    private boolean isLive = true;
    private boolean isOnline = true;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(this.uuid).getName();
        this.team = Main.getTeamManager().selectTeam(this);
        this.inventory = new UserInventory(this.team.getType());
        this.statistics = new UserStatistics();
        this.status = UserStatus.PLAYING;
        this.displayName = PlayerDisplay.getDisplayPlayer(name);
        addToTeam();
    }

    /**
     * !!! Observação !!! Metodo criado para tentar solucionar erro esporadico:
     * http://paste.ubuntu.com/25347066/
     */
    private void addToTeam() {
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + name + " prefix " + PlayerDisplay.getPrefix(name) + "" + PlayerDisplay.getColorCode(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public boolean isLive() {
        return isLive;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        if (!isOnline && !team.hasBed()) {
            this.setLive(false);
        }
        this.isOnline = isOnline;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserStatistics getStatistics() {
        return statistics;
    }

    public UserInventory getInventory() {
        return inventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setupUser() {
        Player player = Bukkit.getPlayer(this.getUuid());
        player.getInventory().clear();
        this.setOnline(true);
        switch (Main.getGameManager().getGame().getStatus()) {
            case PRE_GAME:
                setupPreGame(player);
                break;
            case IN_GAME:
                setupInGame(player);
                break;
            default:
                break;
        }
    }

    void setupInGame(Player player) {
        if (this.getStatus() == UserStatus.SPEC) {
            Main.getSpectatorManager().setSpectator(player);
        } else if (this.getStatus() == UserStatus.PLAYING) {
            for (PotionEffect pe : player.getActivePotionEffects()) {
                player.removePotionEffect(pe.getType());
            }
            if (this.getTeam().getUpgrade("Pressa").getLevel() > 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, this.getTeam().getUpgrade("Pressa").getLevel() - 1));
            }
            player.setGameMode(GameMode.SURVIVAL);
            getInventory().executePlayer(player);
            player.teleport(this.getTeam().getSpawn());
        }

    }

    void setupPreGame(Player player) {
        if (this.isLive) {
            for (PotionEffect pe : player.getActivePotionEffects()) {
                player.removePotionEffect(pe.getType());
            }
            player.getInventory().setArmorContents(null);
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(Main.getWorldManager().getSpawnEspera());
            player.updateInventory();
        } else {
            Main.getSpectatorManager().setSpectator(player);
        }

    }

    public boolean hasPlayerFight() {
        if (this.getPlayer().hasMetadata("LastDamageHit")) {
            long lastHit = Long.parseLong(this.getPlayer().getMetadata("LastDamageHit").get(0).value().toString().split("@")[1]);
            long current = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            long diff = current - lastHit;
            if (diff <= 10) {
                return true;
            }
        }
        return false;
    }

    public void setPlayerFight(String damager) {
        this.getPlayer().setMetadata("LastDamageHit", new FixedMetadataValue(Main.getInstance(), damager + "@" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUuid());
    }

    @Override
    public int compareTo(User o) {
        if (o.getStatistics().getKills() > this.getStatistics().getKills()) {
            return 1;
        } else if (o.getStatistics().getKills() < this.getStatistics().getKills()) {
            return -1;
        } else {
            return 0;
        }
    }

}
