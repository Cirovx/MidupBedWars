package com.midup.bedwars.shop;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.midup.bedwars.Main;
import com.midup.bedwars.team.Team;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.trait.LookClose;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

public class ShopManager {

    private NPCRegistry npcRegister;

    public ShopManager() {
        this.npcRegister = CitizensAPI.getNPCRegistry();
    }

    /**
     * Percorre a lista de team registradas e spawna os NPCs de acordo com as
     * locations salvas na team
     */
    public void spawnVillagers() {
        for (Team team : Main.getTeamManager().getTeams()) {
            if (team.getLocationVillagerItem() != null) {
                createNPC(team.getLocationVillagerItem(), "ShopItems");
            }
            if (team.getLocationVillagerUpgrade() != null) {
                createNPC(team.getLocationVillagerUpgrade(), "ShopUpgrades");
            }
            createHolograms(team.getLocationVillagerItem(), "§e§lLOJA", "§e§lITENS");
            createHolograms(team.getLocationVillagerUpgrade(), "§e§lLOJA", "§e§lMELHORIAS");
        }
    }

    /**
     * Deleta os NPCs criados pelo CitizensAPI (Recomendado executar no
     * onDisable)
     */
    public void destroyNPC() {
        this.npcRegister.deregisterAll();
    }

    /**
     * Cria um Holograma para o Villager do Shop de acordo com a location e as
     * linhas passadas
     *
     * @param location A location onde sera spawnado o NPC
     * @param lines As linhas do holograma
     */
    private void createHolograms(Location location, String... lines) {
        Hologram holo = HologramsAPI.createHologram(Main.getInstance(), location.add(0, 2.8, 0));
        for (String name : lines) {
            holo.appendTextLine(name);
        }
    }

    /**
     * Cria um NCP de Shop de acordo com location passada
     *
     * @param location A location onde sera spawnada o NCP
     * @param name O nome do NCP (sera salvo em metadata)
     */
    private void createNPC(Location location, String name) {
        ArmorStand stand = (ArmorStand) Main.getWorldManager().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setBasePlate(false);
        stand.setSmall(true);
        stand.setVisible(false);
        NPC npc = this.npcRegister.createNPC(EntityType.VILLAGER, "§r");
        LookClose look = new LookClose();
        look.lookClose(true);
        look.setRange(4);
        npc.addTrait(look);
        npc.spawn(location);
        npc.getEntity().setPassenger(stand);
        npc.getEntity().setMetadata(name, new FixedMetadataValue(Main.getInstance(), name));
    }

}
