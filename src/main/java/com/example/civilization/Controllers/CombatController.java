package com.example.civilization.Controllers;

import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Terrain;
import com.example.civilization.Model.Units.CombatTypes;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonRangedCombatUnit;
import com.example.civilization.Model.Units.RangedCombatUnit;
import com.example.civilization.Model.User;

import java.util.regex.Matcher;

public class CombatController {
    private DatabaseController databaseController;
    private CityController cityController;

    public CombatController(DatabaseController databaseController, CityController cityController) {
        this.cityController = cityController;
        this.databaseController = databaseController;

    }

    public String unitAttackCity(Matcher matcher, User user) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Terrain terrain = this.databaseController.getTerrainByCoordinates(x, y);
        City city = terrain.getCity();
        if (city == null) {
            return "There is no city in the tile you selected";
        }

        if (user.getCivilization().equals(city.getOwner())) {
            return "You cannot attack your own city";
        }

        CombatUnit combatUnit = this.databaseController.getSelectedCombatUnit();
        if (combatUnit instanceof NonRangedCombatUnit && !city.getMainTerrains().contains(this.databaseController.getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY()))) {
            return "You have to enter the city first";
        }
        if (combatUnit instanceof NonRangedCombatUnit) {
            if (cityController.oneCombatTurn(city, combatUnit)) {
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

    public String rangedAttack(Matcher matcher, User user) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        Terrain terrain = this.databaseController.getTerrainByCoordinates(x, y);
        City city = terrain.getCity();
        if (city == null) {
            return "There is no city in the tile you selected";
        }

        if (user.getCivilization().equals(city.getOwner())) {
            return "You cannot attack your own city";
        }

        CombatUnit combatUnit = this.databaseController.getSelectedCombatUnit();
        Terrain unitTerrain = this.databaseController.getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY());

        if (combatUnit instanceof NonRangedCombatUnit) {
            return "You have to select a ranged combat unit.";
        }

        if (combatUnit.getUnitType().getCombatTypes().equals(CombatTypes.SIEGE)) {
            if (!((RangedCombatUnit) combatUnit).getIsSetUpForRangedAttack()) {
                return "You have to set up unit for ranged attack first";
            }
        }

        if (!(this.cityController.NeighborsAtADistanceOfTwoFromAnArraylistOfTerrains(city.getMainTerrains(), this.databaseController.getMap()).contains(unitTerrain)
                || this.cityController.NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(city.getMainTerrains(), this.databaseController.getMap()).contains(unitTerrain))) {
            return "Your unit is not close enough for a ranged attack";
        }

        if (this.cityController.rangedAttackToCityForOneTurn((RangedCombatUnit) combatUnit, city)) {
            return "You won. The city is yours. Please move a combat unit to the tile to win it";
        } else {
            return "You played one turn of ranged attack. If you wish to continue, please attack again next turn";
        }
    }
}
