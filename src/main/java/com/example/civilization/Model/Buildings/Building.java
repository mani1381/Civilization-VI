package com.example.civilization.Model.Buildings;



public class Building {

    private int passedTurns = 0;
    private int x, y;
    private final BuildingTypes buildingType;

    public Building(int x, int y, BuildingTypes buildingType) {
        this.x = x;
        this.y = y;
        this.buildingType = buildingType;
    }

    public int getPassedTurns() {
        return passedTurns;
    }

    public void setPassedTurns(int passedTurns) {
        this.passedTurns = passedTurns;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BuildingTypes getBuildingType() {
        return buildingType;
    }

}
