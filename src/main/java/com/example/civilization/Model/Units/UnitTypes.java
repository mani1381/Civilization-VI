package com.example.civilization.Model.Units;

import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.TechnologyTypes;

public enum UnitTypes {
    ARCHER(4, 70, CombatTypes.ARCHERY, 4, 6, 2, 2, null,"A"),
    CHARIOT_ARCHER(4, 60, CombatTypes.MOUNTED, 3, 6, 2, 4, ResourceTypes.HORSES,"C"),
    SCOUT(2, 25, CombatTypes.RECON, 4, 0, 0, 2,  null,"S"),
    SETTLER(2, 89, CombatTypes.CIVILIAN, 0, 0, 0, 2, null,"S"),
    SPEARMAN(5, 50, CombatTypes.MELEE, 7, 0, 0, 2,  null,"S"),
    WARRIOR(4, 40, CombatTypes.MELEE, 6, 0, 0, 2,  null,"W"),
    WORKER(2, 70, CombatTypes.CIVILIAN, 0, 0, 0, 2,  null,"W"),
    CATAPULT(5, 100, CombatTypes.SIEGE, 4, 14, 2, 2, ResourceTypes.IRON,"C"),
    HORSESMAN(4, 80, CombatTypes.MOUNTED, 12, 0, 0, 4, ResourceTypes.HORSES,"H"),
    SWORDSMAN(5, 80, CombatTypes.MELEE, 11, 0, 0, 2, ResourceTypes.IRON,"S"),
    CROSSBOWMAN(5, 120, CombatTypes.ARCHERY, 6, 12, 2, 2, null,"C"),
    KNIGHT(6, 150, CombatTypes.MOUNTED, 18, 0, 0, 3, ResourceTypes.HORSES,"K"),
    LONGSWORDSMAN(6, 150, CombatTypes.MELEE, 18, 0, 0, 3, ResourceTypes.IRON,"L"),
    PIKEMAN(4, 100, CombatTypes.MELEE, 10, 0, 0, 2, null,"P"),
    TREBUCHET(6, 170, CombatTypes.SIEGE, 6, 20, 2, 2, ResourceTypes.IRON,"T"),
    CANNON(7, 250, CombatTypes.SIEGE, 10, 26, 2, 2, null,"C"),
    CAVALRY(7, 260, CombatTypes.MOUNTED, 25, 0, 0, 3, ResourceTypes.HORSES, "C"),
    LANCER(6, 220, CombatTypes.MOUNTED, 22, 0, 0, 4, ResourceTypes.HORSES, "L"),
    MUSKETMAN(4, 120, CombatTypes.GUNPOWDER, 16, 0, 0, 2, null,"M"),
    RIFLEMAN(5, 200, CombatTypes.GUNPOWDER, 25, 0, 0, 2, null, "R"),
    ANTI_TANKGUN(7, 300, CombatTypes.GUNPOWDER, 32, 0, 0, 2, null,"A"),
    ARTILLERY(8, 420, CombatTypes.SIEGE, 16, 32, 3, 2, null,"A"),
    INFANTRY(3, 300, CombatTypes.GUNPOWDER, 36, 0, 0, 2, null, "I"),
    PANZER(5, 450, CombatTypes.ARMORED, 60, 0, 0, 5, null, "P"),
    TANK(5, 450, CombatTypes.ARMORED, 50, 0, 0, 4, null,"T");

    private  int cost;
    private  CombatTypes combatTypes;
    private  int combatStrengh;
    private  int rangedCombatStrengh;
    private  int Range;
    private  int movement;
    private  ResourceTypes resourceRequirements;
    private String showMap;
    private int turn;

    UnitTypes(int turn, int cost, CombatTypes combatTypes, int combatStrengh, int rangedCombatStrengh, int range, int movement,
            ResourceTypes resourceRequirements,String showMap) {
        this.cost = cost;
        this.turn = turn;
        this.combatTypes = combatTypes;
        this.combatStrengh = combatStrengh;
        this.rangedCombatStrengh = rangedCombatStrengh;
        this.Range = range;
        this.movement = movement;
        this.resourceRequirements = resourceRequirements;
        this.showMap = showMap;

    }

    public String getShowMap() {
        return this.showMap;
    }

    public int getCost() {
        return this.cost;
    }

    public ResourceTypes getResourceRequirements() {
        return resourceRequirements;
    }

    public TechnologyTypes getTechnologyRequirements() {
       for(TechnologyTypes technologyTypes : TechnologyTypes.values()){
           if(technologyTypes.getUnlocks().contains(this)){
               return technologyTypes;
           }
       }
       return null;
    }

    public CombatTypes getCombatTypes() {
        return combatTypes;
    }

    public int getCombatStrengh() {
        return combatStrengh;
    }

    public int getRangedCombatStrengh() {
        return rangedCombatStrengh;
    }

    public int getRange() {
        return Range;
    }

    public int getMovement() {
        return movement;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

}