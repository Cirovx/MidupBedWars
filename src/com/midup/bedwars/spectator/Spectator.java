package com.midup.bedwars.spectator;

import com.midup.bedwars.Main;
import java.util.UUID;
import org.bukkit.Bukkit;

/**
 *
 * @author cirov
 */
public class Spectator {

    private UUID uuid;
    private int time = 5;

    public Spectator(UUID uuid, int time) {
        this.uuid = uuid;
        this.time = time;
    }

    public boolean run() {
        if (Main.getUserManager().getUser(uuid).isOnline()) {
            if (getTime() == 0) {
                Main.getSpectatorManager().removeSpectator(Bukkit.getPlayer(getUuid()));
                return true;
            } else {
                Bukkit.getPlayer(getUuid()).sendMessage("§eVoce renascerá em §c" + getTime() + " §esegundos...");
                decrementTime();
                return false;
            }
        }
        return false;
    }

    public int getTime() {
        return time;
    }

    public void decrementTime() {
        this.time--;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public UUID getUuid() {
        return uuid;
    }

}
