package com.midup.bedwars.game;

import com.midup.Midup;
import com.midup.bedwars.Main;
import com.midup.bedwars.MainTask;
import com.midup.bedwars.scoreboard.ScoreboardManager;
import com.midup.bedwars.team.Team;
import com.midup.bedwars.user.User;
import com.midup.bedwars.user.UserStatistics;
import com.midup.display.PlayerDisplay;
import com.midup.message.CenteredMessage;
import com.midup.minigames.MidupMinigames;
import com.midup.minigames.status.Status;
import com.midup.title.Title;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author cirov
 */
public class GameManager {

    private final Game game;
    private final Main plugin;
    private BukkitTask task;
    private Team teamWin;

    public GameManager(Main plugin) {
        this.plugin = plugin;
        this.game = new Game();
        setupMatch();
        startPreGame();
    }

    public Game getGame() {
        return game;
    }

    public Team getTeamWin() {
        return teamWin;
    }

    public void setTeamWin(Team teamWin) {
        this.teamWin = teamWin;
    }

    public boolean startPreGame() {
        try {
            Main.getActionManager().setNextAction("Aguardando...");
            MidupMinigames.setStatus(Status.ESPERA);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean startGame() {
        try {
            Main.getActionManager().setNextAction(Main.getActionManager().getAction().get(0).getDescription());
            Main.getActionManager().setTimeForNextAction(Main.getActionManager().getAction().get(0).getTimeForAction());
            MidupMinigames.setStatus(Status.ANDAMENTO);
            Main.getShopManager().spawnVillagers();
            game.setStatus(GameStatus.IN_GAME);
            Midup.getScoreboardManager().unregisterScoreboardAllPlayers();
            ScoreboardManager.loadInGame();
            for (User user : Main.getUserManager().getAllUsers()) {
                user.setupUser();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + user.getName() + " prefix " + user.getTeam().getType().getColorCode().replace("§", "&"));
            }

            for (Team team : Main.getTeamManager().getTeams()) {
                int onlineUsers = 0;
                for (User user : team.getUsers()) {
                    if (user.isOnline()) {
                        onlineUsers++;
                    }
                }
                if (onlineUsers == 0) {
                    team.brokenBed();
                }
            }

            Main.getWorldManager().clearLobby();
            Main.getGeneratorManager().startUpdateEffect();
            System.out.println("INICIADO COM SUCESSO!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean endGame() {
        try {
            MidupMinigames.setStatus(Status.FECHADO);
            Bukkit.getOnlinePlayers().forEach(player -> Main.getSpectatorManager().setSpectator(player));
            sendMessageWin();
            game.setStatus(GameStatus.FINISHED);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BukkitTask getTask() {
        return task;
    }

    private void sendMessageWin() {
        try {
            Team team = getTeamWin();
            String teamWin = "§7Nenhum";
            String players = "";
            Title title = new Title("");
            if (team != null) {
                for (User user : Main.getUserManager().getAllUsers()) {
                    if (user.isOnline()) {
                        if (user.getTeam() == team) {
                            title.setTitle("§aPARABENS");
                            title.setSubtitle("§fseu time venceu!");
                            title.send(user.getPlayer());
                            players = players + "§7" + PlayerDisplay.getDisplayPlayer(user.getPlayer().getName()) + "§7, ";
                        } else {
                            title.setTitle("§cQUE PENA :(");
                            title.setSubtitle("§fseu time perdeu!");
                            title.send(user.getPlayer());
                        }
                    }
                }
                teamWin = team.getType().getNameDisplay();
            } else {
                title.setTitle("§eFim da partida");
                title.setSubtitle("§fnao houve vencedores!");
                Bukkit.getOnlinePlayers().forEach(pOn -> title.send(pOn));
            }
            if (players.length() > 2) {
                players = players.substring(0, players.length() - 2);
            }
            List<User> topKillers = Main.getUserManager().getAllUsers();
            Collections.sort(topKillers);
            for (Player player : Bukkit.getOnlinePlayers()) {
                CenteredMessage.sendCenteredMessage(player, "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                CenteredMessage.sendCenteredMessage(player, "§f§lBed Wars");
                player.sendMessage("");
                CenteredMessage.sendCenteredMessage(player, "§eVENCEDOR: " + teamWin);
                CenteredMessage.sendCenteredMessage(player, players);
                player.sendMessage("");
                CenteredMessage.sendCenteredMessage(player, "§lTOP KILLERS");
                player.sendMessage("");
                for (int i = 0; i < topKillers.size(); i++) {
                    if (i == 3) {
                        break;
                    }
                    User user = topKillers.get(i);
                    if (user != null) {
                        int kil = topKillers.get(i).getStatistics().getKills();
                        CenteredMessage.sendCenteredMessage(player, "§6" + (i + 1) + "º §7- §f" + user.getDisplayName() + " §7(" + kil + ")");
                    }
                }
                CenteredMessage.sendCenteredMessage(player, "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageInfo() {
        for (User user : Main.getUserManager().getAllOnlineUsers()) {
            Player player = user.getPlayer();
            UserStatistics stats = user.getStatistics();
            if (player != null) {
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "§6§lGAME INFO");
                user.getPlayer().sendMessage("");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "Você matou &6" + stats.getKills() + " &finimigos");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "Morreu &6" + stats.getDeaths() + " &fvezes");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "Quebrou &6" + stats.getBrokenBeds() + " &fcama(s)");
                user.getPlayer().sendMessage("");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "Recebeu o total de &6" + stats.getTotalCoins() + " &fCoins");
                user.getPlayer().sendMessage("");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "&7(+" + stats.getCoinsPerVip() + ") por " + stats.getVipsInGame() + " &3VIP's &7no jogo");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "&7(+" + stats.getCoinsPerBrokenBed() + ") por quebrar §6" + stats.getBrokenBeds() + " &7camas");
                CenteredMessage.sendCenteredMessage(user.getPlayer(), "§a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

            }
        }
    }

    private Team getLiveTeams() {
        List<Team> teams = new ArrayList<>();
        for (Team team : Main.getTeamManager().getTeams()) {
            for (User user : team.getUsers()) {
                if (user.isLive() && user.isOnline()) {
                    if (!teams.contains(team)) {
                        teams.add(team);
                    }
                }
            }
        }
        if (teams.size() == 1) {
            return teams.get(0);
        } else {
            return null;
        }
    }

    public void checkWin() {
        Team team = getLiveTeams();
        if (team != null) {
            setTeamWin(team);
            endGame();
        }
    }

    @SuppressWarnings("deprecation")
    void startCountdownToStart() {
        Main.getActionManager().setNextAction("Iniciando em:");
        this.task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new MainTask(this.game), 0, 20);
    }

    public void recalculatePlayerQuit(Player player) {
        if (game.getStatus() == GameStatus.PRE_GAME) {
            if (Bukkit.getOnlinePlayers().size() - 1 < game.getMinPlayersForStart()) {
                if (this.task != null) {
                    this.task.cancel();
                    this.task = null;
                }
                Main.getActionManager().setNextAction("Aguardando...");
            }
        }
    }

    public void recalculatePlayerJoin(Player player) {
        if (game.getStatus() == GameStatus.PRE_GAME) {
            if (Bukkit.getOnlinePlayers().size() >= game.getMinPlayersForStart()) {
                if (this.task == null) {
                    startCountdownToStart();
                }
            }
        }
    }

    void setupMatch() {
        Main.getActionManager().setTimeForNextAction(plugin.getConfig().getInt("MatchConfiguration.countdown-to-start"));
        getGame().setGameType(GameType.valueOf(plugin.getConfig().getString("MatchConfiguration.game-type")));
        getGame().setMaxPlayers(plugin.getConfig().getInt("MatchConfiguration.max-players"));
        getGame().setMinPlayersForStart(plugin.getConfig().getInt("MatchConfiguration.min-players-for-start"));
        getGame().setPlayersPerTeam(plugin.getConfig().getInt("MatchConfiguration.players-per-team"));
        getGame().setTotalTeams(plugin.getConfig().getInt("MatchConfiguration.total-teams"));
        getGame().setStatus(GameStatus.PRE_GAME);
        getGame().setPortRange(plugin.getConfig().getInt("RangePort"));
        getGame().setRoomID("b0" + (Bukkit.getPort() - getGame().getPortRange()) + getGame().getGameType().name().charAt(0));
    }

}
