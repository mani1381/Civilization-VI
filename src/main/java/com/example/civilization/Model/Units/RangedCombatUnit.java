package com.example.civilization.Model.Units;

public class RangedCombatUnit extends CombatUnit {

    private boolean isSetUpForRangedAttack;

    public RangedCombatUnit(int x, int y, int number, int militaryPower, int life, int speed, boolean isAsleep, boolean isFinished, UnitTypes unitType, boolean isSelected, boolean isGarrisoned, boolean alert, boolean fortify, boolean fortifyUntilHeal, boolean isSetUpForRangedAttack) {
        super(x, y, number, militaryPower, life, speed, isAsleep, isFinished, unitType, isSelected, isGarrisoned, alert, fortify, fortifyUntilHeal);
        //TODO Auto-generated constructor stub
        this.isSetUpForRangedAttack = isSetUpForRangedAttack;
    }

    public boolean isIsSetUpForRangedAttack() {
        return this.isSetUpForRangedAttack;
    }

    public boolean getIsSetUpForRangedAttack() {
        return this.isSetUpForRangedAttack;
    }

    public void setIsSetUpForRangedAttack(boolean isSetUpForRangedAttack) {
        this.isSetUpForRangedAttack = isSetUpForRangedAttack;
    }

}
