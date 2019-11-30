package com.midup.bedwars.team;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Color;

public enum TeamType {

    RED("V", "Vermelho", "§c", Color.RED, (short) 14),
    BLUE("A", "Azul", "§9", Color.BLUE, (short) 11),
    GREEN("V", "Verde", "§a", Color.GREEN, (short) 5),
    YELLOW("A", "Amarelo", "§e", Color.YELLOW, (short) 4),
    AQUA("C", "Ciano", "§b", Color.AQUA, (short) 3),
    WHITE("B", "Branco", "§f", Color.WHITE, (short) 0),
    PINK("R", "Rosa", "§d", Color.FUCHSIA, (short) 6),
    GRAY("C", "Cinza", "§8", Color.GRAY, (short) 8);

    private final String name;
    private final String charr;
    private final String colorCode;
    private final Color color;
    private final short idWool;

    private TeamType(String charr, String name, String colorCode, Color color, short idWool) {
        this.name = name;
        this.charr = charr;
        this.colorCode = colorCode;
        this.color = color;
        this.idWool = idWool;
    }

    public short getIdWool() {
        return idWool;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getCharr() {
        return charr;
    }

    public String getColorCode() {
        return colorCode;
    }

    /**
     *
     * @param bold Use true para receber o retorno com capslock.
     * @return O caracter correspondente a team em questao, juntamente com a
     * respectiva cor.
     */
    public String getCharDisplay(boolean bold) {
        return bold ? this.colorCode + "§l" + this.charr : this.colorCode + this.charr;
    }

    /**
     *
     * @return Uma string com o nome completo da team, juntamente com a
     * respectiva cor
     */
    public String getNameDisplay() {
        return this.colorCode + this.name;
    }

    public static List<TeamType> getTeams() {
        List<TeamType> teams = new ArrayList<>();
        teams.add(TeamType.RED);
        teams.add(TeamType.BLUE);
        teams.add(TeamType.GREEN);
        teams.add(TeamType.YELLOW);
        teams.add(TeamType.AQUA);
        teams.add(TeamType.WHITE);
        teams.add(TeamType.PINK);
        teams.add(TeamType.GRAY);
        return teams;
    }

}
