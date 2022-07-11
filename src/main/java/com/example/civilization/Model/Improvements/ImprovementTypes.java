package com.example.civilization.Model.Improvements;

import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;

import java.util.ArrayList;

public enum ImprovementTypes {
    ROAD(3, 0, 0, 0, new ArrayList<>() {
    }, new ArrayList<>() {
        {

            add(TerrainTypes.TUNDRA);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.HILLS);
            add(TerrainTypes.SNOW);
            add(TerrainFeatureTypes.JUNGLE);
            add(TerrainFeatureTypes.FOREST);
            add(TerrainFeatureTypes.MARSH);
            add(TerrainFeatureTypes.FLOODPLAINS);
            add(TerrainFeatureTypes.OASIS);
            add(TerrainFeatureTypes.RIVER);

        }
    }, "RO"), RAILROAD(3, 0, 0, 0, new ArrayList<>() {
    }, new ArrayList<>() {
        {
            add(TerrainTypes.TUNDRA);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.HILLS);
            add(TerrainTypes.SNOW);
            add(TerrainFeatureTypes.JUNGLE);
            add(TerrainFeatureTypes.FOREST);
            add(TerrainFeatureTypes.MARSH);
            add(TerrainFeatureTypes.FLOODPLAINS);
            add(TerrainFeatureTypes.OASIS);
            add(TerrainFeatureTypes.RIVER);
        }

    }, "RRO"), CAMP(6, 0, 0, 0, new ArrayList<>() {
        {
            add(ResourceTypes.FURS);
            add(ResourceTypes.IVORY);
            add(ResourceTypes.DEER);
        }
    }, new ArrayList<>() {
        {
            add(TerrainFeatureTypes.FOREST);
            add(TerrainTypes.TUNDRA);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.HILLS);
        }
    }, "CAM"),

    FARM(10, 1, 0, 0, new ArrayList<>() {
        {
            add(ResourceTypes.WHEAT);
        }
    }, new ArrayList<>() {
        {
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
        }
    }, "FAR"), LUMBERMILL(6, 0, 1, 0, null, new ArrayList<>() {
        {
            add(TerrainFeatureTypes.FOREST);
        }
    }, "LUM"), MINE(12, 0, 1, 0, new ArrayList<>() {
        {
            add(ResourceTypes.WHEAT);
            add(ResourceTypes.IRON);
            add(ResourceTypes.COAL);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.GOLD);
            add(ResourceTypes.SILVER);
        }
    }, new ArrayList<>() {
        {
            add(TerrainFeatureTypes.FOREST);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.TUNDRA);
            add(TerrainFeatureTypes.JUNGLE);
            add(TerrainTypes.SNOW);
            add(TerrainTypes.HILLS);
        }
    }, "MIN"), PASTURE(7, 0, 0, 0, new ArrayList<>() {
        {
            add(ResourceTypes.HORSES);
            add(ResourceTypes.CATTLE);
            add(ResourceTypes.SHEEP);
            add(ResourceTypes.BANANAS);
        }
    }, new ArrayList<>() {
        {
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.TUNDRA);
            add(TerrainTypes.HILLS);
        }
    }, "PAS"), PLANTATION(5, 0, 0, 0, new ArrayList<>() {
        {
            add(ResourceTypes.BANANAS);
            add(ResourceTypes.DYES);
            add(ResourceTypes.SILK);
            add(ResourceTypes.SUGAR);
            add(ResourceTypes.COTTON);
            add(ResourceTypes.INCENSE);
        }
    }, new ArrayList<>() {
        {
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainFeatureTypes.FOREST);
            add(TerrainFeatureTypes.MARSH);
            add(TerrainFeatureTypes.FLOODPLAINS);
            add(TerrainFeatureTypes.JUNGLE);
        }
    }, "PLA"), QUARRY(7, 0, 0, 0, new ArrayList<>() {
        {
            add(ResourceTypes.MARBLE);
        }
    }, new ArrayList<>() {
        {
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.TUNDRA);
            add(TerrainTypes.HILLS);
        }
    }, "QUA"), TRADINGPOST(8, 0, 0, 1, null, new ArrayList<>() {
        {
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.TUNDRA);
        }
    }, "TRA"), MANUFACTORY(4, 0, 2, 0, null, new ArrayList<>() {
        {
            add(TerrainTypes.GRASSLAND);
            add(TerrainTypes.PLAINS);
            add(TerrainTypes.DESERT);
            add(TerrainTypes.TUNDRA);
            add(TerrainTypes.SNOW);
        }
    }, "MAN");

    int food;
    int production;
    int gold;
    int turn;
    ArrayList<ResourceTypes> resourcesAccessed;
    ArrayList<Object> canBeBuiltON;
    String ShowImprovement;


    ImprovementTypes(int turn, int food, int production, int gold, ArrayList<ResourceTypes> resourcesAccessed, ArrayList<Object> canBeBuiltOn, String ShowImprovement) {
        this.turn = turn;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.resourcesAccessed = resourcesAccessed;
        this.canBeBuiltON = canBeBuiltOn;
        this.ShowImprovement = ShowImprovement;
    }

    public String getShowImprovement() {
        return this.ShowImprovement;
    }

    public int getGold() {
        return this.gold;
    }

    public int getFood() {
        return this.food;
    }

    public int getProduction() {
        return production;
    }

    public ArrayList<Object> getCanBeBuiltON() {
        return canBeBuiltON;
    }

    public void setCanBeBuiltON(ArrayList<Object> canBeBuiltON) {
        this.canBeBuiltON = canBeBuiltON;
    }

    public ArrayList<ResourceTypes> getResourcesAccessed() {
        return resourcesAccessed;
    }

    public void setResourcesAccessed(ArrayList<ResourceTypes> resourcesAccessed) {
        this.resourcesAccessed = resourcesAccessed;
    }

    public TechnologyTypes getRequiredTechnology() {
        for(TechnologyTypes technologyTypes : TechnologyTypes.values()){
            if(technologyTypes.getUnlocks().contains(this)){
                return technologyTypes;
            }
        }
        return null;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}