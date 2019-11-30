package com.midup.bedwars;

import com.midup.bedwars.game.Game;
import com.midup.bedwars.shop.models.Upgrade;
import com.midup.bungee.BungeeExecute;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author cirov
 */
public class MainTask extends BukkitRunnable {

    private Game game;
    private int aux = 0;

    public MainTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        switch (game.getStatus()) {
            case PRE_GAME:
                if (Main.getActionManager().getTimeForNextAction() > 0 && Bukkit.getOnlinePlayers().size() < game.getMaxPlayers()) {
                    Main.getActionManager().decrementTimeForNextAction();
                } else {
                    Main.getGameManager().startGame();
                }
                break;
            case IN_GAME:
                Main.getActionManager().run();
                Main.getGeneratorManager().execute();
                Main.getSpectatorManager().execute();
                Main.getGameManager().checkWin();
                Upgrade.checkActivatedUpgrades();
                break;
            case FINISHED:
                switch (aux) {
                    case 5:
                        Main.getGameManager().sendMessageInfo();
                        break;
                    case 10:
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            BungeeExecute.teleportPlayer(player, "lobbybedwars01");
                        }
                        break;
                    case 15:
                        Bukkit.shutdown();
                        break;
                }
                aux++;
                break;
            default:
                System.out.println("DEFAULT-ERROR");
                break;
        }
        long end = System.currentTimeMillis();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                player.setLevel((int) (end - start));
            }
        }
    }
}
