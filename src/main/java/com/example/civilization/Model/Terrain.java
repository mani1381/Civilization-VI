package com.example.civilization.Model;

import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Improvements.Improvement;
import com.example.civilization.Model.Resources.Resource;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonCombatUnit;
import com.example.civilization.Model.Units.Unit;

import java.util.ArrayList;

public class Terrain {


    private int x;
    private int y;
    private String Type;
    private TerrainTypes terrainTypes;
    private  ArrayList<TerrainFeatureTypes> terrainFeatureTypes;
    private boolean isBeingWorkedOn;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private Improvement TerrainImprovement;
    private Resource TerrainResource;
    private boolean unlockResource;
    private ArrayList<Revealed> reveals ;
    private City city;

    private boolean hasToBeDeleted = false;

    private int passedTurns = 0;

    private boolean isRuin;

    public Terrain(int x, int y, String Type, TerrainTypes terrainTypes, ArrayList<TerrainFeatureTypes> terrainFeatureTypes, CombatUnit combatUnit, NonCombatUnit nonCombatUnit, Improvement TerrainImprovement, Resource TerrainResource, ArrayList<Revealed> reveals) {

        this.x = x;
        this.y = y;
        this.Type = Type;
        this.terrainTypes = terrainTypes;
        this.terrainFeatureTypes = terrainFeatureTypes;
        this.combatUnit = combatUnit;
        this.nonCombatUnit = nonCombatUnit;

        this.isBeingWorkedOn = false;

        this.TerrainImprovement = TerrainImprovement;
        this.TerrainResource = TerrainResource;
        this.reveals = new ArrayList<>();

        // this.city = null;
    }

    public int getPassedTurns() {
        return passedTurns;
    }

    public void setPassedTurns(int passedTurns) {
        this.passedTurns = passedTurns;
    }

    public boolean isHasToBeDeleted() {
        return hasToBeDeleted;
    }

    public void setHasToBeDeleted(boolean hasToBeDeleted) {
        this.hasToBeDeleted = hasToBeDeleted;
    }

    public boolean getBooleanResource() {
        return this.unlockResource;
    }

    public void setBooleanResource(boolean bool) {
        this.unlockResource = bool;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public TerrainTypes getTerrainTypes() {
        return this.terrainTypes;
    }

    public void setTerrainTypes(TerrainTypes terrainTypes) {
        this.terrainTypes = terrainTypes;
    }

    public ArrayList<TerrainFeatureTypes> getTerrainFeatureTypes() {
        return this.terrainFeatureTypes;
    }

    public void setTerrainFeatureTypes(TerrainFeatureTypes terrainFeatureTypes) {
        this.terrainFeatureTypes.add(terrainFeatureTypes);
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    public Resource getResource() {
        return TerrainResource;
    }

    public Resource getTerrainResource() {
        return this.TerrainResource;
    }

    public void setTerrainResource(Resource TerrainResource) {
        this.TerrainResource = TerrainResource;
    }

    public ArrayList<Revealed> getReveals() {
        return this.reveals;
    }

    public void setReveals(Revealed reveals) {
        this.reveals.add(reveals);
    }

    public void setRevealedTest(ArrayList<Revealed> reveals) {
        this.reveals = reveals;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public boolean containsUnit(Unit unit2) {
        return unit2.equals(combatUnit) || unit2.equals(nonCombatUnit);
    }

    public boolean isBeingWorkedOn() {
        return isBeingWorkedOn;
    }

    public void setBeingWorkedOn(boolean beingWorkedOn) {
        isBeingWorkedOn = beingWorkedOn;
    }

    public int getGold() {
        if (!this.isBeingWorkedOn) {
            return 0;
        } else {

        }
        return 0;
    }
    public void setTerrainFeatureTypesArray(ArrayList<TerrainFeatureTypes> terrainFeatureTypes) {
        this.terrainFeatureTypes = terrainFeatureTypes;
    }

    public Improvement getTerrainImprovement() {
        return this.TerrainImprovement;
    }

    public void setTerrainImprovement(Improvement TerrainImprovement) {
        this.TerrainImprovement = TerrainImprovement;
    }

    public boolean isRuin() {
        return isRuin;
    }

    public void setRuin(boolean ruin) {
        isRuin = ruin;
    }
}