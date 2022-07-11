package com.example.civilization.Model.Buildings;


import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.TechnologyTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BuildingTypes {
    BARRACKS(7,80, 1,null,null)
    , GRANARY(8,100, 1, null,null)
    , LIBRARY(7,80, 1 ,null,null)
    , MONUMENT(9,60, 1,null,null)
    , WALLS(10,100, 1,null,null)
    , WATERMILL(8,120, 2,null,null)
    , ARMORY(9,130, 3, new ArrayList<>(List.of(BuildingTypes.BARRACKS)),null)
    , BURIAL_TOMB(11,120, 0,null,null)
    , CIRCUS(9,150, 3, null,new ArrayList<>(Arrays.asList(ResourceTypes.IVORY,ResourceTypes.HORSES)))
    , COLOSSEUM(7,150, 3,null,null)
    , COURTHOUSE(9,200, 5,null,null)
    , STABLE(12,100, 1,null,new ArrayList<>(Arrays.asList(ResourceTypes.HORSES)))
    , TEMPLE(13,120, 2,new ArrayList<>(List.of(BuildingTypes.MONUMENT)),null)
    , CASTLE(14,200, 3,new ArrayList<>(List.of(BuildingTypes.WALLS)),null)
    , FORGE(11,150, 2,null,new ArrayList<>(Arrays.asList(ResourceTypes.IRON)))
    , GARDEN(13,120, 2,null,null)
    , MARKET(15,120, 0,null,null)
    , MINT(16,120, 0,null,null)
    , MONASTERY(14,120, 2,null,null)
    , UNIVERSITY(13,200, 3,new ArrayList<>(List.of(BuildingTypes.LIBRARY)),null)
    , WORKSHOP(15,100, 2,null,null)
    , BANK(16,220, 0,new ArrayList<>(List.of(BuildingTypes.MARKET)),null)
    , MILITARY_ACADEMY(13,350, 3,new ArrayList<>(List.of(BuildingTypes.BARRACKS)),null)
    , OPERA_HOUSE(17,220, 3,new ArrayList<>(Arrays.asList(BuildingTypes.TEMPLE,BuildingTypes.BURIAL_TOMB)),null)
    , MUSEUM(15,350, 3,new ArrayList<>(List.of(BuildingTypes.OPERA_HOUSE)),null)
    , PUBLIC_SCHOOL(15,350, 3,new ArrayList<>(List.of(BuildingTypes.UNIVERSITY)),null)
    , SATRAPS_COURT(14,220, 0,new ArrayList<>(List.of(BuildingTypes.MARKET)),null)
    , THEATER(18,300, 5,new ArrayList<>(List.of(BuildingTypes.COLOSSEUM)),null)
    , WINDMILL(19,180, 2,null,null)
    , ARSENAL(17,350, 3,new ArrayList<>(List.of(BuildingTypes.MILITARY_ACADEMY)),null)
    , BROADCAST_TOWER(20,600, 3,new ArrayList<>(List.of(BuildingTypes.MUSEUM)),null)
    , FACTORY(21,300, 3,null,new ArrayList<>(Arrays.asList(ResourceTypes.COAL)))
    , HOSPITAL(22,400, 2,null,null)
    , MILITARY_BASE(23,450, 4,new ArrayList<>(List.of(BuildingTypes.CASTLE)),null)
    , STOCK_EXCHANGE(18,650, 0,new ArrayList<>(Arrays.asList(BuildingTypes.BANK,BuildingTypes.SATRAPS_COURT)),null) ;

    final int cost;

    final int turn;
    final int maintenance;

    final ArrayList<BuildingTypes> buildingRequirements;

    final ArrayList<ResourceTypes> resourceRequirements;


    BuildingTypes(int turn ,int cost, int maintenance, ArrayList<BuildingTypes> buildingRequirements, ArrayList<ResourceTypes> resourceRequirements) {
        this.turn = turn;
        this.cost = cost;
        this.maintenance = maintenance;
        this.buildingRequirements = buildingRequirements;
        this.resourceRequirements = resourceRequirements;
    }

    public int getMeintenance() {
        return this.maintenance;
    }

    public int getTurn() {
        return turn;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public ArrayList<BuildingTypes> getBuildingRequirements() {
        return buildingRequirements;
    }

    public ArrayList<ResourceTypes> getResourceRequirements() {
        return resourceRequirements;
    }

    public TechnologyTypes getRequirement() {

        for(TechnologyTypes technologyTypes : TechnologyTypes.values()){
            if(technologyTypes.getUnlocks().contains(this)){
                return technologyTypes;
            }
        }
        return null;
    }
}