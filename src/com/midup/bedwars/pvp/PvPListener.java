package com.midup.bedwars.pvp;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.GameManager;
import com.midup.bedwars.game.GameStatus;
import com.midup.bedwars.team.TeamManager;
import com.midup.bedwars.user.User;
import com.midup.bedwars.user.UserManager;
import com.midup.bedwars.user.UserStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 *
 * @author cirov
 */
public class PvPListener implements Listener {

    private GameManager game;
    private UserManager users;
    private TeamManager team;

    public PvPListener() {
        this.game = Main.getGameManager();
        this.users = Main.getUserManager();
        this.team = Main.getTeamManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            User user = users.getUser(evt.getEntity().getUniqueId());
            if (user.getStatus() == UserStatus.SPEC || user.getStatus() == UserStatus.TEMP_SPEC) {
                evt.setCancelled(true);
                return;
            }
            if (evt.getCause() == DamageCause.BLOCK_EXPLOSION || evt.getCause() == DamageCause.ENTITY_EXPLOSION) {
                evt.setDamage(5D);
                return;
            }
            if (game.getGame().getStatus() == GameStatus.IN_GAME) {
                Player player = (Player) evt.getEntity();
                if (player.getHealth() - evt.getDamage() <= 0) {
                    player.setHealth(20D);
                    Main.getPvPManager().customDeath(player);
                    evt.setCancelled(true);

                    return;
                }
            } else {
                evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent evt) {
        if (evt.getCause() == DamageCause.ENTITY_EXPLOSION) {
            evt.setCancelled(true);
            return;
        }
        if (game.getGame().getStatus() == GameStatus.IN_GAME) {
            if (evt.getEntity() instanceof Player) {
                Player player = (Player) evt.getEntity();
                User user = users.getUser(evt.getEntity().getUniqueId());
                if (user.getStatus() == UserStatus.SPEC || user.getStatus() == UserStatus.TEMP_SPEC) {
                    evt.setCancelled(true);
                    return;
                }
                if (evt.getDamager() instanceof IronGolem) {
                    if (evt.getDamager().hasMetadata(user.getTeam().getType().getName())) {
                        evt.setCancelled(true);
                        IronGolem golem = (IronGolem) evt.getDamager();
                        golem.setTarget(null);
                        return;
                    }
                } else if (evt.getDamager() instanceof Silverfish) {
                    if (evt.getDamager().hasMetadata(user.getTeam().getType().getName())) {
                        evt.setCancelled(true);
                        Silverfish golem = (Silverfish) evt.getDamager();
                        golem.setTarget(null);
                        return;
                    }
                }
                if (player.getHealth() - evt.getDamage() <= 0) {
                    player.setHealth(20D);
                    Main.getPvPManager().customDeath(player);
                    evt.setCancelled(true);
                    return;
                }
                switch (evt.getDamager().getType()) {
                    case PLAYER:
                        if (!team.usersOnSameTeam(evt.getEntity().getUniqueId(), evt.getDamager().getUniqueId())) {
                            if (user.getStatus() == UserStatus.PLAYING && users.getUser(evt.getDamager().getUniqueId()).getStatus() == UserStatus.PLAYING) {
                                user.setPlayerFight(evt.getDamager().getName());
                            } else {
                                evt.setCancelled(true);
                            }
                        } else {
                            evt.setCancelled(true);
                        }
                        break;
                    case ARROW:
                        Player damager = (Player) ((Arrow) evt.getDamager()).getShooter();
                        if (team.usersOnSameTeam(damager.getUniqueId(), evt.getEntity().getUniqueId())) {
                            evt.setCancelled(true);
                        } else {
                            user.setPlayerFight(damager.getName());
                        }
                        break;
                    default:
                        break;
                }
            }
        } else {
            evt.setCancelled(true);
        }
    }

}
