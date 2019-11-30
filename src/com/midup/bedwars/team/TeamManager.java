package com.midup.bedwars.team;

import com.midup.bedwars.Main;
import com.midup.bedwars.game.Game;
import com.midup.bedwars.user.User;
import com.midup.cuboid.Cuboid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

public class TeamManager {

    private Game game;
    private Map<TeamType, Team> teams = new LinkedHashMap<>();

    public TeamManager() {
        this.game = Main.getGameManager().getGame();
        setupTeams();

    }

    public void load() {
        Main.getWorldManager().loadIslandLocations();
        loadBeds();
        loadChests();
        loadProtectedBlocks();
    }

    /**
     * Define todos os blocos que serao protegidos na ilha de cada team
     *
     */
    private void loadProtectedBlocks() {
        for (Team team : getTeams()) {
            Cuboid cu = new Cuboid(team.getProtectedRegionP1(), team.getProtectedRegionP2());
            for (Block block : cu.getBlocks()) {
                game.addProtectedLocations(block.getLocation());
            }
        }
    }

    /**
     * Define a cama da team de acordo com as locations carregadas da config do
     * mapa. Seta uma metadada chamada BedTeam, sendo o valor, o nome da
     * team(Nome do enum)
     */
    private void loadBeds() {
        for (Team team : getTeams()) {
            Block block = team.getBedLocation().getBlock();
            team.getBedLocation().getBlock().setMetadata("BedTeam", new FixedMetadataValue(Main.getInstance(), team.getType().name()));
            for (BlockFace bf : BlockFace.values()) {
                if (block.getRelative(bf).getType() == Material.BED_BLOCK) {
                    block.getRelative(bf).setMetadata("BedTeam", new FixedMetadataValue(Main.getInstance(), team.getType().name()));
                    break;
                }
            }
        }
    }

    /**
     * Define o bau da team de acordo com as locations carregadas da config do
     * mapa. Seta uma metadada chamada ChestTeam, sendo o valor, o nome da
     * team(Nome do enum)
     */
    private void loadChests() {
        for (Team team : getTeams()) {
            team.getChestLocation().getBlock().setMetadata("ChestTeam", new FixedMetadataValue(Main.getInstance(), team.getType().name()));
        }
    }

    /**
     * Adicioan um usuario a uma team
     *
     * @param team A team a ser adicionado o usuario
     */
    void addTeam(Team team) {
        this.teams.put(team.getType(), team);
    }

    /**
     * Cria as teams necessarias, de acordo com as configurações feitas no
     * arquivo config.yml
     */
    void setupTeams() {
        int aux = 0;
        for (int i = 0; i < game.getTotalTeams(); i++) {
            TeamType type = TeamType.getTeams().get(aux);
            Team team = new Team(type);
            addTeam(team);
            aux++;
        }
    }

    /**
     *
     * @param type O tipo da team
     * @return A team
     */
    public Team getTeam(TeamType type) {
        return this.teams.get(type);
    }

    /**
     *
     * @return Todas as teams registradas
     */
    public List<Team> getTeams() {
        List<Team> teamList = new ArrayList<>();
        for (Team team : this.teams.values()) {
            teamList.add(team);
        }
        return teamList;
    }

    /**
     * Seleciona uma team para o usuario, de acordo com o balanceamento de
     * teams. O cache já é feito neste metodo!
     *
     * @param user O usuario para ser selecionado a team
     * @return A team selecionada
     */
    public Team selectTeam(User user) {
        Team team = getTeamWithFewerPlayers();
        team.addUser(user);
        return team;
    }

    /**
     *
     * @return A team que possuir o menor numero de jogadores
     */
    private Team getTeamWithFewerPlayers() {
        Team selectedTeam = null;
        for (Team team : getTeams()) {
            if (selectedTeam == null) {
                selectedTeam = team;
            } else {
                if (team.getUsers().size() < selectedTeam.getUsers().size()) {
                    selectedTeam = team;
                }
            }
        }
        return selectedTeam;
    }

    /**
     * Compara se dois usuarios sao da mesma equipe!
     *
     * @param uuid1 A uuid do usuario
     * @param uuid2 A uuid do outro usuario
     * @return true se forem da mesma equipe, false se nao
     */
    public boolean usersOnSameTeam(UUID uuid1, UUID uuid2) {
        return Main.getUserManager().getUser(uuid1).getTeam() == Main.getUserManager().getUser(uuid2).getTeam();
    }

    /**
     * Invoca o metodo updateArmor de UserInventory para definir armadura de
     * acordo com a team e as informações salvas no mesmo! Este metodo definirá
     * a armadura de todos os membros da team
     *
     * @param team A team
     */
    public void updateArmor(TeamType team) {
        for (User user : Main.getUserManager().getTeamUsers(team)) {
            if (user.isOnline()) {
                user.getInventory().updateArmor(Bukkit.getPlayer(user.getUuid()));
            }
        }
    }

    /**
     * Invoca o metodo updateSword de UserInventory para definir a espada de
     * acordo com a team e as informações salvas no mesmo! Este metodo definirá
     * todas as espadas no inventario de todos os membros da team
     *
     * @param team A team
     */
    public void updateSword(TeamType team) {
        for (User user : Main.getUserManager().getTeamUsers(team)) {
            if (user.isOnline()) {
                user.getInventory().updateSword(Bukkit.getPlayer(user.getUuid()));
            }
        }
    }

}
