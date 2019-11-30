package com.midup.bedwars.game.action;

public abstract class Action {

    private String description;
    private int timeForAction;

    public Action(String description, int timeForAction) {
        this.description = description;
        this.timeForAction = timeForAction;
    }

    public abstract void run();

    public String getDescription() {
        return description;
    }

    public int getTimeForAction() {
        return timeForAction;
    }

}
