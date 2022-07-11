package com.example.civilization.Model.Terrains;

import com.example.civilization.Model.Color;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;

import java.util.ArrayList;

public enum TerrainTypes {
    DESERT(0, 0, 0, -33, 1, new ArrayList<TerrainFeatureTypes>() {
        {
            add(TerrainFeatureTypes.OASIS);
            add(TerrainFeatureTypes.FLOODPLAINS);

        }
    }, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.IRON);
            add(ResourceTypes.GOLD);
            add(ResourceTypes.SILVER);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.MARBLE);
            add(ResourceTypes.COTTON);
            add(ResourceTypes.INCENSE);
            add(ResourceTypes.SHEEP);
        }
    }, Color.YELLOW_BACKGROUND_BRIGHT),
    GRASSLAND(2, 0, 0, -33, 1, new ArrayList<TerrainFeatureTypes>() {
        {
            add(TerrainFeatureTypes.FOREST);
            add(TerrainFeatureTypes.MARSH);
        }
    }, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.IRON);
            add(ResourceTypes.HORSES);
            add(ResourceTypes.COAL);
            add(ResourceTypes.CATTLE);
            add(ResourceTypes.GOLD);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.MARBLE);
            add(ResourceTypes.COTTON);
            add(ResourceTypes.SHEEP);
            add(ResourceTypes.WHEAT);
        }
    }, Color.GREEN_BACKGROUND_BRIGHT),
    HILLS(0, 2, 0, 25, 2, new ArrayList<TerrainFeatureTypes>() {
        {
            add(TerrainFeatureTypes.FOREST);
            add(TerrainFeatureTypes.JUNGLE);
        }
    }, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.IRON);
            add(ResourceTypes.COAL);
            add(ResourceTypes.DEER);
            add(ResourceTypes.GOLD);
            add(ResourceTypes.SILVER);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.MARBLE);
            add(ResourceTypes.SHEEP);
        }
    }, Color.GREEN_BACKGROUND),
    MOUNTAIN(0, 0, 0, 25, 9999999, null, null, Color.YELLOW_BACKGROUND),
    OCEAN(1, 0, 1, 0, 1, new ArrayList<TerrainFeatureTypes>() {
        {
            add(TerrainFeatureTypes.ICE);
        }
    }, new ArrayList<ResourceTypes>() {
        {

        }
    }, Color.CYAN_BACKGROUND),
    PLAINS(1, 1, 0, -33, 1, new ArrayList<TerrainFeatureTypes>() {
        {
            add(TerrainFeatureTypes.FOREST);
            add(TerrainFeatureTypes.JUNGLE);
        }
    }, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.IRON);
            add(ResourceTypes.HORSES);
            add(ResourceTypes.COAL);
            add(ResourceTypes.WHEAT);
            add(ResourceTypes.GOLD);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.MARBLE);
            add(ResourceTypes.IVORY);
            add(ResourceTypes.COTTON);
            add(ResourceTypes.INCENSE);
            add(ResourceTypes.SHEEP);
        }
    }, Color.MAGENTA_BACKGROUND),
    SNOW(0, 0, 0, -33, 1, null, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.IRON);
        }
    }, Color.WHITE_BACKGROUND),
    TUNDRA(1, 0, 0, -33, 1, new ArrayList<TerrainFeatureTypes>() {
        {
            add(TerrainFeatureTypes.FOREST);
        }
    }, new ArrayList<ResourceTypes>() {
        {
            add(ResourceTypes.IRON);
            add(ResourceTypes.HORSES);
            add(ResourceTypes.DEER);
            add(ResourceTypes.SILVER);
            add(ResourceTypes.GEMS);
            add(ResourceTypes.MARBLE);
            add(ResourceTypes.FURS);
        }
    }, Color.RED_BACKGROUND);

    int food;
    int product;
    int gold;
    int movementCost;
    int combatModifier;
    ArrayList<TerrainFeatureTypes> possibleFeatures;
    ArrayList<ResourceTypes> possibleResources;
    Color color;
    TerrainTypes(int food, int product, int gold, int combatModifier, int movementCost,
                 ArrayList<TerrainFeatureTypes> possibleFeatures, ArrayList<ResourceTypes> possibleResources, Color color) {
        this.food = food;
        this.product = product;
        this.gold = gold;
        this.movementCost = movementCost;
        this.combatModifier = combatModifier;
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getMovementCost() {
        return this.movementCost;

    }

    public ArrayList<TerrainFeatureTypes> getPossibleFeatures() {
        return this.possibleFeatures;
    }

    public int getGold() {
        return this.gold;
    }

    public int getFood() {
        return this.food;
    }

    public int getProduct()
    {
        return product;
    }

    public int getCombatModifier()
    {
        return combatModifier;
    }

    public ArrayList<ResourceTypes> getPossibleResources() {
        return possibleResources;
    }
}