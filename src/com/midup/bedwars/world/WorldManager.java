package com.midup.bedwars.world;

import com.midup.bedwars.Main;
import com.midup.bedwars.generator.models.GeneratorType;
import com.midup.bedwars.generator.models.IslandGenerator;
import com.midup.bedwars.team.Team;
import com.midup.cuboid.Cuboid;
import com.midup.serializer.LocationSerializer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author cirov
 */
public final class WorldManager {

    private final World world;
    private final Location spawn;
    private final File file;
    private FileConfiguration fileConfiguration;
    private final String worldName;
    private Location lobbyEspera1;
    private Location lobbyEspera2;
    private Location spawnEspera;
    private long worldTime;

    public WorldManager() {
        this.world = loadWorld();
        this.spawn = this.world.getSpawnLocation();
        this.file = new File(this.getWorld().getName() + "/saves.yml");
        if (checkFile()) {
            fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        }
        this.worldName = this.world.getName().split("_")[1];
        this.worldTime = 0L;
        setWorldTime();
    }

    /**
     *
     * @return O spawn default do mundo (getSpawnLocation())
     */
    public Location getSpawn() {
        return spawn;
    }

    public String getWorldName() {
        return worldName;
    }

    /**
     * Este é o ponto 1 da cuboid que sera criada para remover o lobby de espera
     *
     * @return Ponto 1 do lobby de espera
     */
    public Location getLobbyEspera1() {
        return lobbyEspera1;
    }

    public void setLobbyEspera1(Location lobbyEspera1) {
        this.lobbyEspera1 = lobbyEspera1;
    }

    /**
     * Este é o ponto 2 da cuboid que sera criada para remover o lobby de espera
     *
     * @return Ponto 2 do lobby de espera
     */
    public Location getLobbyEspera2() {
        return lobbyEspera2;
    }

    public void setLobbyEspera2(Location lobbyEspera2) {
        this.lobbyEspera2 = lobbyEspera2;
    }

    /**
     *
     * @return O spawn principal, no lobby de espera!
     */
    public Location getSpawnEspera() {
        return spawnEspera;
    }

    public void setSpawnEspera(Location spawnEspera) {
        this.spawnEspera = spawnEspera;
    }

    /**
     * Este mundo possui auto-save == false. Nele ocorrerá a partida!
     *
     * @return O mundo selecionado para a partida atual
     */
    public World getWorld() {
        return world;
    }

    /**
     * Percorre todos os diretorios da raiz do servidor procurando por diretorio
     * que iniciem com o nome MAPA_ para criar uma lista de mundos, que entrarao
     * no sorteio de mapas.
     *
     * @return
     */
    private List<String> getWordList() {
        List<String> worlds = new ArrayList<>();
        File folderMain = new File("plugins/../");
        for (File folders : folderMain.listFiles()) {
            if (folders.isDirectory()) {
                String folder = folders.getName();
                if (folder.startsWith("MAPA_")) {
                    worlds.add(folder);
                    Bukkit.getLogger().info("Carregando Mapa: " + folder);
                }
            }
        }
        Bukkit.getLogger().info("Total de mapas carregados == " + worlds.size());
        return worlds;
    }

    /**
     * Escolhe um mundo aleatorio de uma lista de mundos.
     *
     * @return O mundo escolhido
     */
    private String selectWorld() {
        List<String> worlds = getWordList();
        Random r = new Random();
        return worlds.get(r.nextInt(worlds.size()));
    }

    /**
     * Remove o lobby de espera de cima do mapa do game. Limpa todas as
     * entidades do tipo DROPPED_ITEM.
     */
    public void clearLobby() {
        Cuboid cuboid = new Cuboid(this.lobbyEspera1, this.lobbyEspera2);
        for (Block block : cuboid.getBlocks()) {
            block.setType(Material.AIR);
            block.getState().update();
        }
        for (Chunk chunk : Main.getWorldManager().getWorld().getLoadedChunks()) {
            for (Entity entity : chunk.getEntities()) {
                if (entity.getType() == EntityType.DROPPED_ITEM) {
                    entity.remove();
                }
            }
        }

    }

    /**
     * Verifica se existe um arquivo de configuração no world selecionado
     *
     * @return true se ja existir, false se nao
     */
    private boolean checkFile() {
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(WorldManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    /**
     * Define o horario do mundo. Fez-se necessario a criação deste metodo, pois
     * ele deve ser chamado após a criação completa do mundo (não encontrei
     * outra solução)!
     */
    private void setWorldTime() {
        if (fileConfiguration.getString("WorldTime") != null) {
            this.worldTime = Long.parseLong(fileConfiguration.getString("WorldTime"));
        }
        getWorld().setTime(this.worldTime);
    }

    /**
     * Carrega o mundo da partida atual, definindo todas as configurações
     * necessarias!
     *
     * @return O mundo carregado.
     */
    public World loadWorld() {
        WorldCreator creator = new WorldCreator(selectWorld());
        World createWorld = creator.createWorld();
        for (Entity entity : createWorld.getEntities()) {
            if ((entity instanceof LivingEntity)) {
                entity.remove();
            }
        }
        createWorld.setAutoSave(false);
        createWorld.setDifficulty(Difficulty.NORMAL);
        createWorld.setGameRuleValue("doDaylightCycle", "false");
        createWorld.setGameRuleValue("doMobSpawning", "false");
        createWorld.setKeepSpawnInMemory(true);
        createWorld.setStorm(false);
        createWorld.setThundering(false);
        createWorld.setWeatherDuration(0);
        return createWorld;
    }

    public void loadIslandLocations() {
        if (this.fileConfiguration.contains("TeamSpawn")) {
            for (int i = 0; i < Main.getGameManager().getGame().getTotalTeams(); i++) {
                Team team = Main.getTeamManager().getTeams().get(i);
                team.setSpawn(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamSpawn." + i), true));
                team.setBedLocation(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamBed." + i), true));
                team.setLocationVillagerItem(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamVillagerItem." + i), true));
                team.setLocationVillagerUpgrade(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamVillagerUpgrade." + i), true));
                team.setChestLocation(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamChest." + i), true));
                team.setProtectedRegionP1(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamProtectedP1." + i), true));
                team.setProtectedRegionP2(LocationSerializer.deserialize(this.fileConfiguration.getString("TeamProtectedP2." + i), true));
            }
            loadIslandGenerators();
            setLobbyEspera1(LocationSerializer.deserialize(this.fileConfiguration.getString("LobbyEspera1"), true));
            setLobbyEspera2(LocationSerializer.deserialize(this.fileConfiguration.getString("LobbyEspera2"), true));
            setSpawnEspera(LocationSerializer.deserialize(this.fileConfiguration.getString("SpawnEspera"), true));
        } else {
            Bukkit.getLogger().info("Configuração de mapa ausente!");
        }
    }

    public void loadIslandGenerators() {
        for (String type : this.fileConfiguration.getConfigurationSection("IslandGenerator").getKeys(false)) {
            for (String pos : this.fileConfiguration.getConfigurationSection("IslandGenerator." + type).getKeys(false)) {
                Team team = Main.getTeamManager().getTeams().get(Integer.parseInt(pos));
                for (String loc : this.fileConfiguration.getConfigurationSection("IslandGenerator." + type + "." + pos).getKeys(false)) {
                    GeneratorType tp = GeneratorType.valueOf(type.toUpperCase());
                    IslandGenerator ig = new IslandGenerator(tp, tp.getDefaultTime(), new ItemStack(tp.getMaterial()));
                    ig.setLocation(LocationSerializer.deserialize(this.fileConfiguration.getString("IslandGenerator." + type + "." + pos + "." + loc), true));
                    team.addGenerator(ig);
                }
            }
        }
    }

    public void loadGlobalGenerators() {
        for (String type : this.fileConfiguration.getConfigurationSection("GlobalGenerator").getKeys(false)) {
            for (String loc : this.fileConfiguration.getConfigurationSection("GlobalGenerator." + type).getKeys(false)) {
                Main.getGeneratorManager().addGlobalGenerator(GeneratorType.valueOf(type.toUpperCase()), LocationSerializer.deserialize(this.fileConfiguration.getString("GlobalGenerator." + type + "." + loc), true));
            }
        }
    }

}
