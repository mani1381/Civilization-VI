package com.example.civilization.Controllers;

import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Civilization;
import com.example.civilization.Model.Terrain;
import com.example.civilization.Model.Units.*;
import com.example.civilization.Model.User;

import java.util.regex.Matcher;

public class CombatController {
    private DatabaseController databaseController = DatabaseController.getInstance();
    private CityController cityController = new CityController();

    public CombatController() {


    }

    public static String unitAttackUnit(int x1, int y1, int x2, int y2) {

        Terrain attackerTerrain = DatabaseController.getInstance().getTerrainByCoordinates(x1, y1);

        Terrain targetTerrain = DatabaseController.getInstance().getTerrainByCoordinates(x2, y2);
        CombatUnit attackerCombatUnit = attackerTerrain.getCombatUnit();
        CombatUnit targetCombatUnit = targetTerrain.getCombatUnit();
        if (targetCombatUnit == null) return " There is no unit in this tile.";
        else if (DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getUnits().contains((targetCombatUnit))){
            return "You cannot attack your own unit";
        }
        else {
            playOneTurnInUnitAttack(attackerCombatUnit, targetCombatUnit);
            return "You attacked the unit";

        }
    }

    public static void playOneTurnInUnitAttack(CombatUnit attacker, CombatUnit target) {
        int attackerCombatStrength = attacker.getCombatStrength();
        int targetCombatStrength = target.getCombatStrength();
        int attackerModifier = 0;
        int targetModifier = 0;
        if (attacker.getUnitType().equals(UnitTypes.CATAPULT) || attacker.getUnitType().equals(UnitTypes.TREBUCHET) || attacker.getUnitType().equals(UnitTypes.ARTILLERY) || attacker.getUnitType().equals(UnitTypes.CANNON)) {
            attackerModifier += 10;
        }
        attackerCombatStrength = attackerCombatStrength * (1 + (attackerModifier / 100));
        if (target.getUnitType().equals(UnitTypes.CATAPULT) || target.getUnitType().equals(UnitTypes.TREBUCHET) || target.getUnitType().equals(UnitTypes.ARTILLERY) || target.getUnitType().equals(UnitTypes.CANNON)) {
            targetModifier += 10;
        }
        targetCombatStrength = targetCombatStrength * (1 + (targetModifier / 100));
        attacker.setHP(attacker.getHP() - targetCombatStrength);
        target.setHP(target.getHP() - attackerCombatStrength);
        if (target.getHP() <= 0) {
            Civilization targetOwner = DatabaseController.getInstance().getContainerCivilization(target);
            targetOwner.removeUnit(target);
            Terrain tile = DatabaseController.getInstance().getTerrainByCoordinates(target.getX(), target.getY());
            tile.setCombatUnit(null);

        }
        if (attacker.getHP() <= 0) {
            Civilization attackerOwner = DatabaseController.getInstance().getContainerCivilization(attacker);
            attackerOwner.removeUnit(attacker);
            Terrain tile = DatabaseController.getInstance().getTerrainByCoordinates(attacker.getX(), attacker.getY());
            tile.setCombatUnit(null);

        }

    }

    public static String unitAttackCity(int x, int y, User user) {
        Terrain terrain = DatabaseController.getInstance().getTerrainByCoordinates(x, y);
        City city = terrain.getCity();
        if (city == null) {
            return "There is no city in the tile you selected";
        }

        if (user.getCivilization().equals(city.getOwner())) {
            return "You cannot attack your own city";
        }

        CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
        if (combatUnit instanceof NonRangedCombatUnit && !city.getMainTerrains().contains(DatabaseController.getInstance().getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY()))) {
            return "You have to enter the city first";
        }
        if (combatUnit instanceof NonRangedCombatUnit) {
            if (CityController.oneCombatTurn(city, combatUnit)) {
                combatUnit.setIsSelected(false);
                combatUnit.setIsFinished(true);
                return "You won.The city is yours. Do you wish to destroy it or make it yours?";
            } else {
                combatUnit.setIsSelected(false);
                combatUnit.setIsFinished(true);
                return "You played one turn in combat. Please attack the city next turn again if you wish to continue.";
            }
        }
        if (combatUnit instanceof RangedCombatUnit) {
            return "This unit is a ranged combat unit. Please initialize a ranged attack.";
        }
        return "Error";
    }

    public static String rangedAttack(int x,int y, User user) {

        Terrain terrain = DatabaseController.getInstance().getTerrainByCoordinates(x, y);
        City city = terrain.getCity();
        if (city == null) {
            return "There is no city in the tile you selected";
        }

        if (user.getCivilization().equals(city.getOwner())) {
            return "You cannot attack your own city";
        }

        CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
        Terrain unitTerrain = DatabaseController.getInstance().getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY());

        if (combatUnit instanceof NonRangedCombatUnit) {
            return "You have to select a ranged combat unit.";
        }

        if (combatUnit.getUnitType().getCombatTypes().equals(CombatTypes.SIEGE)) {
            if (!((RangedCombatUnit) combatUnit).getIsSetUpForRangedAttack()) {
                return "You have to set up unit for ranged attack first";
            }
        }

        if (!(CityController.NeighborsAtADistanceOfTwoFromAnArraylistOfTerrains(city.getMainTerrains(), DatabaseController.getInstance().getMap()).contains(unitTerrain) || CityController.NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(city.getMainTerrains(), DatabaseController.getInstance().getMap()).contains(unitTerrain))) {
            return "Your unit is not close enough for a ranged attack";
        }

        if (CityController.rangedAttackToCityForOneTurn((RangedCombatUnit) combatUnit, city)) {
            return "You won. The city is yours. Please move a combat unit to the tile to win it";
        } else {
            return "You played one turn of ranged attack. If you wish to continue, please attack again next turn";
        }
    }
}


