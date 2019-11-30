package com.midup.bedwars.listeners;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.GameStatus;
import com.midup.bedwars.team.Team;
import com.midup.bedwars.team.TeamType;
import com.midup.bedwars.user.User;
import com.midup.bedwars.user.UserStatus;
import com.midup.display.PlayerDisplay;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 *
 * @author cirov
 */
public class GeralListeners implements Listener {

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event) {
        if (event.toWeatherState() == true) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        if (Main.getGameManager().getGame().getStatus() == GameStatus.PRE_GAME) {
            evt.setJoinMessage(PlayerDisplay.getDisplayColor(evt.getPlayer().getName()) + " §eentrou (§b" + Bukkit.getOnlinePlayers().size() + "§e/§b" + Main.getGameManager().getGame().getMaxPlayers() + "§e)");
        } else {
            if (Main.getUserManager().getUser(evt.getPlayer().getUniqueId()).isLive()) {
                evt.setJoinMessage(PlayerDisplay.getDisplayColor(evt.getPlayer().getName()) + " §evoltou para o jogo!");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Main.getUserManager().getUser(event.getPlayer().getUniqueId()).getTeam().hasBed()) {
            event.setQuitMessage(PlayerDisplay.getDisplayColor(event.getPlayer().getName()) + " §esaiu");
        } else {
            event.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onInteractAt(PlayerInteractAtEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(BlockExplodeEvent event) {
        List<Block> breakable = new ArrayList<>();
        for (Block b : event.blockList()) {
            if (b.hasMetadata("isBreakable")) {
                breakable.add(b);
            }
        }
        event.blockList().clear();
        breakable.forEach(block -> event.blockList().add(block));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity().getType() == EntityType.FIREBALL) {
            Main.getWorldManager().getWorld().createExplosion(event.getLocation(), 4);
        } else if (event.getEntity().getType() == EntityType.PRIMED_TNT) {
            for (Entity ett : event.getEntity().getNearbyEntities(5, 5, 5)) {
                if (ett.getType() == EntityType.PLAYER) {
                    Player player = (Player) ett;
                    player.damage(5D);
                }
            }
        }
        List<Block> breakable = new ArrayList<>();
        for (Block b : event.blockList()) {
            if (b.hasMetadata("isBreakable")) {
                breakable.add(b);
            }
        }
        event.blockList().clear();
        breakable.forEach(block -> event.blockList().add(block));
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (!event.toWeatherState()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball sb = (Snowball) event.getEntity();
            Player player = (Player) sb.getShooter();
            Team team = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
            Silverfish sf = event.getEntity().getWorld().spawn(event.getEntity().getLocation(), Silverfish.class);
            sf.setMetadata(team.getType().getName(), new FixedMetadataValue(Main.getInstance(), team.getType().getName()));
            sf.getNearbyEntities(5, 5, 5).forEach(nearby -> {
                if (nearby instanceof Player) {
                    if (!Main.getUserManager().getUser(nearby.getUniqueId()).getTeam().getType().getName().equalsIgnoreCase(team.getType().getName())) {
                        sf.setTarget((LivingEntity) nearby);
                    }
                }
            });
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTarget(EntityTargetEvent event) {
        if (event.getEntityType() == EntityType.IRON_GOLEM || event.getEntityType() == EntityType.SILVERFISH) {
            if (event.getTarget() != null) {
                if (event.getTarget() instanceof Player) {
                    if (event.getEntity().hasMetadata(event.getTarget().getName())) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().getType() == Material.FIREBALL) {
            if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }
            Projectile fireball;
            int fb_speed = 1;
            final Vector fb_direction = player.getEyeLocation().getDirection().multiply(fb_speed);
            player.playSound(player.getLocation(), Sound.GHAST_FIREBALL, 10, 1);
            fireball = player.getWorld().spawn(player.getEyeLocation().add(fb_direction.getX(), fb_direction.getY(), fb_direction.getZ()), Fireball.class);
            fireball.setShooter((ProjectileSource) player);
            fireball.setVelocity(fb_direction);
        } else if (event.getItem() != null && event.getItem().getType() == Material.MONSTER_EGG) {
            if (event.getClickedBlock() != null) {
                if (player.getItemInHand().getAmount() > 1) {
                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                } else {
                    player.setItemInHand(null);
                }
                IronGolem ett = (IronGolem) player.getWorld().spawn(event.getClickedBlock().getLocation().add(0, 1, 0), IronGolem.class);
                Team owner = Main.getUserManager().getUser(player.getUniqueId()).getTeam();
                ett.setMetadata(owner.getType().getName(), new FixedMetadataValue(Main.getInstance(), owner.getType().getName()));
                ett.getNearbyEntities(5, 5, 5).forEach(nearby -> {
                    if (nearby instanceof Player) {
                        if (!Main.getUserManager().getUser(nearby.getUniqueId()).getTeam().getType().getName().equalsIgnoreCase(owner.getType().getName())) {
                            ett.setTarget((LivingEntity) nearby);
                        }
                    }
                });

            }

        }

        if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
            return;
        }
        if (event.getAction().name().contains("CLICK_BLOCK")) {
            if (event.getClickedBlock().getType() == Material.CHEST) {
                if (event.getClickedBlock().hasMetadata("ChestTeam")) {
                    Team teamChest = Main.getTeamManager().getTeam(TeamType.valueOf(event.getClickedBlock().getMetadata("ChestTeam").get(0).value().toString()));
                    if (Main.getUserManager().getUser(player.getUniqueId()).getTeam() != teamChest) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
            return;
        }
        Block block = event.getBlock();
        if (block.hasMetadata("BedTeam") && block.getType() == Material.BED_BLOCK) {
            User user = Main.getUserManager().getUser(event.getPlayer().getUniqueId());
            Team team = Main.getUserManager().getUser(event.getPlayer().getUniqueId()).getTeam();
            if (block.getMetadata("BedTeam").get(0).value().toString().equalsIgnoreCase(team.getType().name())) {
                event.getPlayer().sendMessage("§cVocê nao pode quebrar sua própria cama!");
                event.setCancelled(true);
            } else {
                Team teamBroken = Main.getTeamManager().getTeam(TeamType.valueOf(block.getMetadata("BedTeam").get(0).value().toString()));
                Bukkit.broadcastMessage(user.getTeam().getType().getColorCode() + event.getPlayer().getName() + " §adestruiu a cama do time " + teamBroken.getType().getNameDisplay());
                teamBroken.setHasBed(false);
                event.getBlock().getDrops().clear();
                user.getStatistics().addBrokenBeds();
            }
            return;
        }
        if (!event.getBlock().hasMetadata("isBreakable")) {
            event.getPlayer().sendMessage("§cVocê só pode quebrar blocos colocados por um jogador");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
            return;
        }
        if (Main.getGameManager().getGame().getProtectedLocations().contains(event.getBlockClicked().getLocation())) {
            event.setCancelled(true);
            player.sendMessage("§cVocê nao pode colocar agua aqui!");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
            return;
        }
        if (Main.getGameManager().getGame().getProtectedLocations().contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage("§cVocê nao pode colocar blocos aqui!");
        } else {
            if (event.getBlock().getType() == Material.TNT) {
                if (player.getItemInHand().getAmount() > 1) {
                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                } else {
                    player.setItemInHand(null);
                }
                Main.getWorldManager().getWorld().spawnEntity(event.getBlock().getLocation().add(0.5, 0.5, 0.5), EntityType.PRIMED_TNT);
                event.setCancelled(true);
            }
            event.getBlock().setMetadata("isBreakable", new FixedMetadataValue(Main.getInstance(), "isBreakable"));
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        if ((player.isOp()) && (player.getGameMode() == GameMode.CREATIVE)) {
            for (Integer i = 0; i < event.getLines().length; i++) {
                String line = ChatColor.translateAlternateColorCodes('&', event.getLine(i));
                event.setLine(i, line);
            }
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
                return;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = (Player) event.getPlayer();
        if (player.isOp() && (player.getGameMode() == GameMode.CREATIVE)) {
            event.getItemDrop().remove();
            return;
        }
        if (Main.getUserManager().getUser(player.getUniqueId()).getStatus() == UserStatus.SPEC) {
            event.setCancelled(true);

        } else if (event.getItemDrop().getItemStack().getType() == Material.WOOD_SWORD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerpickupItem(PlayerPickupItemEvent event) {
        if (event.getItem().getItemStack().getType().name().contains("BED")) {
            event.setCancelled(true);
        } else if (Main.getUserManager().getUser(event.getPlayer().getUniqueId()).getStatus() == UserStatus.SPEC) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (event.getInventory().getType() == InventoryType.CRAFTING) {
            switch (event.getSlot()) {
                case 36:
                case 37:
                case 38:
                case 39:
                    event.setCancelled(true);
                    break;
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().split(" ")[0].equalsIgnoreCase("/me")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Hey galera, eu sou muito chato!");
        } else if (event.getMessage().startsWith("/bukkit")) {
            event.setCancelled(true);
        }
    }

}
