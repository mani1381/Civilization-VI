package com.example.civilization.Model;

import java.util.ArrayList;

import com.example.civilization.Model.Improvements.Improvement;
import com.example.civilization.Model.Resources.Resource;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonCombatUnit;

public class Revealed{

    private User user;
    private TerrainTypes terrainTypes;
    private ArrayList<TerrainFeatureTypes> terrainFeatureTypes;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private Improvement TerrainImprovement;
    private Resource TerrainResource;
    private boolean unlockResource;

    public Revealed(User user, TerrainTypes terrainTypes, ArrayList<TerrainFeatureTypes> terrainFeatureTypes, CombatUnit combatUnit, NonCombatUnit nonCombatUnit, Improvement TerrainImprovement, Resource TerrainResource, boolean bool) {
        this.user = user;
        this.terrainTypes = terrainTypes;
        this.terrainFeatureTypes = terrainFeatureTypes;
        this.combatUnit = combatUnit;
        this.nonCombatUnit = nonCombatUnit;
        this.TerrainImprovement = TerrainImprovement;
        this.TerrainResource = TerrainResource;
        this.unlockResource = bool;
    }


    public void setBooleanResource(boolean bool){
        this.unlockResource = bool;
    }

    public boolean getBooleanResource(){
        return this.unlockResource;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setTerrainFeatureTypes(ArrayList<TerrainFeatureTypes> terrainFeatureTypes) {
        this.terrainFeatureTypes = terrainFeatureTypes;
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

    public Improvement getTerrainImprovement() {
        return this.TerrainImprovement;
    }

    public void setTerrainImprovement(Improvement TerrainImprovement) {
        this.TerrainImprovement = TerrainImprovement;
    }

    public Resource getTerrainResource() {
        return this.TerrainResource;
    }

    public void setTerrainResource(Resource TerrainResource) {
        this.TerrainResource = TerrainResource;
    }
}