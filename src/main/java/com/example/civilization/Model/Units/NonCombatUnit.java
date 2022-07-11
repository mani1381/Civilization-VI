package com.example.civilization.Model.Units;

public class NonCombatUnit extends Unit {


    public NonCombatUnit(int x, int y, int number, double militaryPower, int life, int speed, boolean isAsleep, boolean isFinished, UnitTypes unitType, boolean isSelected) {
        super(x, y, number, militaryPower, life, speed, isAsleep, isFinished, unitType, isSelected);
        //TODO Auto-generated constructor stub
    }

    public NonCombatUnit clone() {
        return new NonCombatUnit(this.getX(), this.getY(), this.getNumber(), this.getMilitaryPower(), this.getLife(), this.getSpeed(), this.getIsAsleep(), this.getIsFinished(), this.getUnitType(), this.getIsSelected());
    }

}