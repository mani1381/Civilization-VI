package com.example.civilization.Model.Resources;

import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;

import java.util.ArrayList;

public enum ResourceTypes {
    BANANAS(1, 0, 0, "BANANAS"),

    CATTLE(1, 0, 0, "CATTLE"),

    DEER(1, 0, 0, "DEER"),

    SHEEP(2, 0, 0, "SHEEP"),

    WHEAT(1, 0, 0,  "WHEAT"),

    COAL(0, 1, 0,  "COAL"),

    HORSES(0, 1, 0,  "HORSES"),

    IRON(0, 1,0 , "IRON"),

    COTTON(0, 0, 2, "COTTON"),

    DYES(0, 0, 2, "DYES"),

    FURS(0, 0, 2, "FURS"),

    GEMS(0, 0, 3, "GEMS"),

    GOLD(0, 0, 2, "GOLD"),

    INCENSE(0, 0, 2, "INCENSE"),

    IVORY(0, 0, 2, "IVORY"),

    MARBLE(0, 0, 2, "MARBLE"),

    SILK(0, 0, 2,   "SILK"),

    SILVER(0, 0, 2,  "SILVER"),

    SUGAR(0, 0, 2,"SUGAR");

    int food;
    int production;
    int gold;

    String ShowResourceMap;

    ResourceTypes(int food, int production, int gold, String ShowResourceMap) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.ShowResourceMap = ShowResourceMap;
    }

    public String getShowResourceMap() {
        return this.ShowResourceMap;
    }

    public ArrayList<Object> getObject() {
        ArrayList<Object> canBeFoundOn  = new ArrayList<>();
        for(TerrainTypes terrainTypes : TerrainTypes.values()){
            if(terrainTypes.getPossibleResources() != null && terrainTypes.getPossibleResources().contains(this)){
                canBeFoundOn.add(terrainTypes);
            }
        }
        for(TerrainFeatureTypes terrainFeatureTypes: TerrainFeatureTypes.values()){
            if(terrainFeatureTypes.getResourceType()!=null && terrainFeatureTypes.getResourceType().contains(this)){
                canBeFoundOn.add(terrainFeatureTypes);
            }
        }
        return canBeFoundOn;
    }

    public int getFood() {
        return this.food;
    }

    public int getGold() {
        return this.gold;
    }

    public int getProduction() {
        return this.production;
    }

    public ImprovementTypes getRequiredImprovements() {
       for(ImprovementTypes improvementTypes : ImprovementTypes.values()){
           if(improvementTypes.getResourcesAccessed().contains(this)){
               return improvementTypes;
           }
       }
       return null;
    }

    public TechnologyTypes getRequiredTechnologies() {
        for (TechnologyTypes technologyTypes : TechnologyTypes.values()) {
            if (technologyTypes.getUnlocks().contains(this)) {
                return technologyTypes;
            }
        }
        return null;
    }

}