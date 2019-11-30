package com.midup.bedwars.game.action;

import com.midup.bedwars.game.action.actions.BrokenBeds;
import com.midup.bedwars.game.action.actions.UPDiamond1;
import com.midup.bedwars.game.action.actions.UPDiamond2;
import com.midup.bedwars.game.action.actions.UPEmerald1;
import com.midup.bedwars.game.action.actions.UPEmerald2;
import java.util.ArrayList;
import java.util.List;

public class ActionManager {

    private List<Action> action = new ArrayList<>();
    private int currentAction = 0;
    private int timeForNextAction = 0;
    private String nextAction = "";

    /*
 * nivel 1 de dima= 31 segundos (default)
 * nivel 2 de dima = 24 segundos
 * nivel 3 de dima =  segundos
 * 
 * nivel 1 esmeralda 65 segundos (default)
 * nivel 2 esmeralda = 50 segundos
 * nivel 3 esmeralda = segundos
     */
    public ActionManager() {
        loadActions();
    }

    public void run() {
        if (getTimeForNextAction() > 0) {
            decrementTimeForNextAction();
        } else {
            if (getAction().size() > currentAction) {
                Action action = getAction().get(getCurrentAction());
                setCurrentAction(getCurrentAction() + 1);
                if (getAction().size() == currentAction) {
                    setNextAction("FIM DO JOGO");
                } else {
                    Action nextAction = getAction().get(getCurrentAction());
                    setTimeForNextAction(nextAction.getTimeForAction());
                    setNextAction(nextAction.getDescription());
                }
                action.run();
            }
        }
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public void setTimeForNextAction(int timeForNextAction) {
        this.timeForNextAction = timeForNextAction;
    }

    public int getTimeForNextAction() {
        return timeForNextAction;
    }

    public void decrementTimeForNextAction() {
        this.timeForNextAction--;
    }

    public int getCurrentAction() {
        return currentAction;
    }

    private void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    public List<Action> getAction() {
        return this.action;
    }

    private void loadActions() {
        UPDiamond1 upd1 = new UPDiamond1();
        this.action.add(upd1);

        UPEmerald1 upe1 = new UPEmerald1();
        this.action.add(upe1);

        UPDiamond2 upd2 = new UPDiamond2();
        this.action.add(upd2);

        UPEmerald2 upe2 = new UPEmerald2();
        this.action.add(upe2);

        BrokenBeds bb = new BrokenBeds();
        this.action.add(bb);
    }

}
