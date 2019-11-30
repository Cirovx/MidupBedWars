package com.midup.bedwars.listeners;

import com.midup.Midup;
import com.midup.bedwars.Main;
import com.midup.bedwars.game.GameManager;
import com.midup.bedwars.game.GameStatus;
import com.midup.bedwars.user.UserManager;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author cirov
 */
public class PlayerListener implements Listener {

    private GameManager game;
    private UserManager users;

    public PlayerListener() {
        this.game = Main.getGameManager();
        this.users = Main.getUserManager();
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent evt) {
        Player player = evt.getPlayer();
        switch (game.getGame().getStatus()) {
            case STARTING:
                evt.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Iniciando sala, aguarde...");
                break;
            case PRE_GAME:
                if (Bukkit.getOnlinePlayers().size() >= game.getGame().getMaxPlayers()) {
                    evt.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Esta sala esta cheia!");
                }
                break;
            case IN_GAME:
                if (!users.hasRegisteredUser(player.getUniqueId())) {
                    evt.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Esta sala ja iniciou!");
                }
                break;
            case FINISHED:
                evt.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Esta sala está fechada!");
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo().getY() <= 0) {
            Main.getPvPManager().customDeath(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        UUID uuid = player.getUniqueId();
        Midup.getScoreboardManager().registerNewPlayer(evt.getPlayer());
        if (!users.hasRegisteredUser(uuid)) {
            users.registerUser(uuid);
            player.getActivePotionEffects().clear();
        }
        if (game.getGame().getStatus() == GameStatus.PRE_GAME) {
            game.recalculatePlayerJoin(player);
        }
        users.getUser(uuid).setupUser();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        Player player = evt.getPlayer();
        UUID uuid = player.getUniqueId();
        if (game.getGame().getStatus() == GameStatus.PRE_GAME) {
            users.unregisterUser(Main.getUserManager().getUser(uuid));
            game.recalculatePlayerQuit(evt.getPlayer());
        } else {
            Main.getUserManager().getUser(uuid).setOnline(false);
        }
    }

    @EventHandler
    public void onQuit(PlayerRespawnEvent evt) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Main.getUserManager().getUser(evt.getPlayer().getUniqueId()).setupUser();
            }
        }.runTaskLater(Main.getInstance(), 1);
    }

}
