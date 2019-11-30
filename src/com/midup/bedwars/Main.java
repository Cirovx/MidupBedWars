package com.midup.bedwars;

import com.midup.Midup;
import com.midup.bedwars.chat.ChatListener;
import com.midup.bedwars.chat.CommandGlobal;
import com.midup.bedwars.game.GameManager;
import com.midup.bedwars.game.action.ActionManager;
import com.midup.bedwars.generator.GeneratorManager;
import com.midup.bedwars.listeners.GeralListeners;
import com.midup.bedwars.listeners.PlayerListener;
import com.midup.bedwars.pvp.PvPListener;
import com.midup.bedwars.pvp.PvPManager;
import com.midup.bedwars.scoreboard.ScoreboardManager;
import com.midup.bedwars.shop.ShopManager;
import com.midup.bedwars.shop.economy.EconomyManager;
import com.midup.bedwars.shop.listeners.ShopListener;
import com.midup.bedwars.spectator.SpectatorListener;
import com.midup.bedwars.spectator.SpectatorManager;
import com.midup.bedwars.team.TeamManager;
import com.midup.bedwars.user.UserManager;
import com.midup.bedwars.world.WorldManager;
import com.midup.command.CommandManager;
import com.midup.menucustom.manager.MenuManager;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author cirov
 *
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private static GameManager gameManager;
    private static TeamManager teamManager;
    private static UserManager userManager;
    private static WorldManager worldManager;
    private static ShopManager shopManager;
    private static EconomyManager economyManager;
    private static GeneratorManager generatorManager;
    private static SpectatorManager spectatorManager;
    private static PvPManager pvpManager;
    private static ActionManager actionManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadInstances();
        registerCommands();
        registerListeners();
        ScoreboardManager.loadPreGame();
        MenuManager.load(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("%20", " ")).getAbsoluteFile(), "shop.menus");
        MenuManager.load(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("%20", " ")).getAbsoluteFile(), "spectator.menus");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        Bukkit.getScheduler().cancelAllTasks();
        getShopManager().destroyNPC();
        Bukkit.unloadWorld(getWorldManager().getWorld(), false);
        Midup.getScoreboardManager().unregisterScoreboardAllPlayers();
    }

    private void loadInstances() {
        instance = this;
        actionManager = new ActionManager();
        gameManager = new GameManager(this);
        worldManager = new WorldManager();
        teamManager = new TeamManager();
        teamManager.load();
        userManager = new UserManager();
        shopManager = new ShopManager();
        economyManager = new EconomyManager();
        generatorManager = new GeneratorManager();
        generatorManager.load();
        spectatorManager = new SpectatorManager();
        pvpManager = new PvPManager();
    }

    public static ActionManager getActionManager() {
        return actionManager;
    }

    public static PvPManager getPvPManager() {
        return pvpManager;
    }

    public static SpectatorManager getSpectatorManager() {
        return spectatorManager;
    }

    public static GeneratorManager getGeneratorManager() {
        return generatorManager;
    }

    public static EconomyManager getEconomyManager() {
        return economyManager;
    }

    public static ShopManager getShopManager() {
        return shopManager;
    }

    public static WorldManager getWorldManager() {
        return worldManager;
    }

    public static UserManager getUserManager() {
        return userManager;
    }

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    public static Main getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("global").setExecutor(new CommandGlobal());
        CommandManager.loadCommands(this, "com.midup.bedwars.commands");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GeralListeners(), this);
        getServer().getPluginManager().registerEvents(new PvPListener(), this);
        getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getServer().getPluginManager().registerEvents(new SpectatorListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

}
