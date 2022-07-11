package com.example.civilization.Model.Units;

public class CombatUnit extends Unit {

    private boolean isGarrisoned;
    private boolean alert;
    private boolean fortify;
    private boolean fortifyUntilHeal;
    private int CombatStrength;
    public CombatUnit(int x, int y, int number, double militaryPower, int life, int speed, boolean isAsleep, boolean isFinished, UnitTypes unitType, boolean isSelected, boolean isGarrisoned, boolean alert, boolean fortify, boolean fortifyUntilHeal) {
        super(x, y, number, militaryPower, life, speed, isAsleep, isFinished, unitType, isSelected);
        // TODO Auto-generated constructor stub
        this.isGarrisoned = isGarrisoned;
        this.alert = alert;
        this.fortify = fortify;
        this.fortifyUntilHeal = fortifyUntilHeal;
        this.CombatStrength = 10;
    }

    public CombatUnit clone() {
        return new CombatUnit(this.getX(), this.getY(), this.getNumber(), this.getMilitaryPower(), this.getLife(), this.getSpeed(), this.getIsAsleep(), this.getIsFinished(), this.getUnitType(), this.getIsSelected(), this.getIsGarrisoned(), this.getAlert(), this.getFortify(), this.fortifyUntilHeal);

    }

    public boolean isIsGarrisoned() {
        return this.isGarrisoned;
    }

    public boolean getIsGarrisoned() {
        return this.isGarrisoned;
    }

    public void setIsGarrisoned(boolean isGarrisoned) {
        this.isGarrisoned = isGarrisoned;
    }

    public boolean isAlert() {
        return this.alert;
    }

    public boolean getAlert() {
        return this.alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public boolean isFortify() {
        return this.fortify;
    }

    public boolean getFortify() {
        return this.fortify;
    }

    public void setFortify(boolean fortify) {
        this.fortify = fortify;
    }

    public boolean isFortifyUntilHeal() {
        return this.fortifyUntilHeal;
    }

    public boolean getFortifyUntilHeal() {
        return this.fortifyUntilHeal;
    }

    public void setFortifyUntilHeal(boolean fortifyUntilHeal) {
        this.fortifyUntilHeal = fortifyUntilHeal;
    }

    public int getCombatStrength() {
        return CombatStrength;
    }

    public void setCombatStrength(int combatStrength) {
        CombatStrength = combatStrength;
    }
}


