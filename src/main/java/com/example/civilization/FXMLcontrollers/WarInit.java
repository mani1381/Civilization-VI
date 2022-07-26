package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.CombatController;
import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Civilization;

public class WarInit {
    private Civilization attacker;
    private Civilization target;
    private int targetX;
    private int targetY;

    private int attackType;

    public void setData(Civilization attacker, Civilization target, int i, int j, int attackType) {
        this.attacker = attacker;
        this.target = target;
        targetX = i;
        targetY = j;
        this.attackType = attackType;
    }

    public void declareWar() {
        attacker.getStatusWithOtherCivilizations().put(target, !attacker.getStatusWithOtherCivilizations().get(target));
        target.getStatusWithOtherCivilizations().put(attacker, !target.getStatusWithOtherCivilizations().get(attacker));
        if (attackType == 0) {
            CombatController.unitAttackUnit(DatabaseController.getInstance().getSelectedCombatUnit().getX(), DatabaseController.getInstance().getSelectedCombatUnit().getY(), targetX, targetY);
        } else if (attackType == 1) {
            CombatController.unitAttackCity(targetX, targetY, DatabaseController.getInstance().getDatabase().getActiveUser());
        } else if (attackType == 2) {
            CombatController.rangedAttack(targetX, targetY, DatabaseController.getInstance().getDatabase().getActiveUser());
        }

        Main.changeMenu("gameMap");


    }

    public void doNotDeclare() {
        Main.changeMenu("gameMap");

    }


}
