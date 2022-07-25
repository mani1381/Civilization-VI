package com.example.civilization.Controllers;

import com.example.civilization.Model.Buildings.Building;
import com.example.civilization.Model.Buildings.BuildingTypes;
import com.example.civilization.Model.City.Citizen;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Civilization;
import com.example.civilization.Model.Database;
import com.example.civilization.Model.Map;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.Technology;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Terrain;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Units.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

public class CityController {

    private DatabaseController databaseController = DatabaseController.getInstance();
    private Map map = Database.getInstance().getMap();

    public static void createBuildingWithTurn(String buildingName, City city) {
        for (BuildingTypes buildingTypes : BuildingTypes.values()) {
            if (buildingTypes.name().equalsIgnoreCase(buildingName)) {
                Building building = new Building(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), buildingTypes);
                city.getBuildingWaitlist().add(building);
            }
        }

    }

    public static void createBuildingWithGold(String buildingName, City city) {
        for (BuildingTypes buildingTypes : BuildingTypes.values()) {
            if (buildingTypes.name().equalsIgnoreCase(buildingName)) {
                Building building = new Building(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), buildingTypes);
                city.setGold(city.getGold() - buildingTypes.getCost());
                city.getBuildings().add(building);
            }
        }


    }

    public static ArrayList<BuildingTypes> allPossibleToCreateBuildingsWithTurn(City city) {
        ArrayList<BuildingTypes> allPossible = new ArrayList<>();
        ArrayList<BuildingTypes> allBuildingTypesOfCity = new ArrayList<>();
        for (Building building : city.getBuildings()) {
            allBuildingTypesOfCity.add(building.getBuildingType());
        }
        for (BuildingTypes buildingType : BuildingTypes.values()) {
            boolean first = buildingType.getBuildingRequirements() == null || allBuildingTypesOfCity.containsAll(buildingType.getBuildingRequirements());
            boolean second = !allBuildingTypesOfCity.contains(buildingType);
            if (city.getCentralTerrain().getResource() == null && buildingType.getResourceRequirements() != null) {

            } else if (first && second && (buildingType.getResourceRequirements() == null || (buildingType.getResourceRequirements().get(0).equals(city.getCentralTerrain().getResource().getResourceType())))) {
                allPossible.add(buildingType);
            }
        }
        return allPossible;
    }

    public static ArrayList<BuildingTypes> allPossibleToCreateBuildingsWithGold(City city) {
        int money = city.getGold();
        ArrayList<BuildingTypes> allPossible = new ArrayList<>();
        ArrayList<BuildingTypes> allBuildingTypesOfCity = new ArrayList<>();
        for (Building building : city.getBuildings()) {
            allBuildingTypesOfCity.add(building.getBuildingType());
        }

        for (BuildingTypes buildingType : BuildingTypes.values()) {
            boolean first = buildingType.getBuildingRequirements() == null || allBuildingTypesOfCity.containsAll(buildingType.getBuildingRequirements());
            boolean second = !allBuildingTypesOfCity.contains(buildingType);
            boolean third = money < buildingType.getCost();
            if (city.getCentralTerrain().getResource() == null && buildingType.getResourceRequirements() != null) {

            } else if (third && first && second && (buildingType.getResourceRequirements() == null || (buildingType.getResourceRequirements().get(0).equals(city.getCentralTerrain().getResource().getResourceType())))) {
                allPossible.add(buildingType);
            }
        }
        return allPossible;
    }

    public void setDatabaseController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String garrisonCity(CombatUnit combatUnit) {
        Terrain unitTerrain = this.databaseController.getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY());
        City city = unitTerrain.getCity();
        if (city == null || !city.getMainTerrains().contains(unitTerrain)) {
            return "The unit has to enter a city first.";
        } else if (!city.getOwner().containsCombatUnit(combatUnit.getX(), combatUnit.getY())) {
            return "This city doesn't belong to you. Go garrison your own cities.";

        } else if (city.getGarrisoned()) {
            return "This city has already been garrisoned";
        } else {
            city.getCentralTerrain().setCombatUnit(combatUnit);
            city.getCentralTerrain().setCombatUnit(combatUnit); //not sure
            city.setCombatStrength(city.getCombatStrength() + combatUnit.getCombatStrength());
            city.setGarrisoned(true);
            combatUnit.setIsFinished(true);
            combatUnit.setIsSelected(false);
            return "You successfully garrisoned your city";
        }
    }

    public String ungarrisonCity(CombatUnit combatUnit) {
        Terrain unitTerrain = this.databaseController.getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY());
        City city = unitTerrain.getCity();
        if (city == null || !city.getMainTerrains().contains(unitTerrain)) {
            return "The unit is not in any cities";
        } else if (!city.getOwner().containsCombatUnit(combatUnit.getX(), combatUnit.getY())) {
            return "This city doesn't belong to you.";

        } else if (!city.getGarrisoned()) {
            return "This city has never been garrisoned";
        } else {
            city.setGarrisoned(false);
            city.getCentralTerrain().setCombatUnit(null);
            city.setCombatStrength(city.getCombatStrength() - combatUnit.getCombatStrength());
            return "You successfully ungarrisoned your city";
        }


    }

    public static void foundCity(Civilization civilization, NonCombatUnit unit, Terrain tile) {
        if (unit == null) {
            System.out.println("please select a unit first");
            return;
        }
        if (unit.getUnitType().equals(UnitTypes.SETTLER)) {
            if (tile.getCity() != null) {
                System.out.println("error : city already exists");
                return;
            }
            City newCity = new City(civilization, civilization, tile, 20, "none", 20, 20);
            tile.setCity(newCity);
            civilization.addCity(newCity);
            civilization.removeUnit(unit);
            tile.setNonCombatUnit(null);
        }
    }

    public void playTurn(City city) {

        city.setNeighbors(NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(city.getMainTerrains(), databaseController.getMap()));
        int foodIncrease = 0;
        int goldIncrease = 0;
        int productionIncrease = 0;
        for (Terrain tile : city.getNeighbors()) {
            if (tile.getResource() != null) {
                foodIncrease += tile.getResource().getFood();
                goldIncrease += tile.getResource().getGold();
                productionIncrease += tile.getResource().getProduction();
            }
            goldIncrease += tile.getGold();
        }
        goldIncrease += calculateCityGold(city);
        foodIncrease += calculateCityFood(city);
        productionIncrease += calculateCityProduction(city);
        for (Citizen citizen : city.getCitizens()) {
            productionIncrease += citizen.getProduction();
        }
        foodIncrease -= city.getPopulation() * 2;
        if (foodIncrease > 0) // creating Citizens
        {
            if (city.getCentralTerrain().getNonCombatUnit() != null && city.getCentralTerrain().getNonCombatUnit().getUnitType().equals(UnitTypes.SETTLER)) {
                foodIncrease = 0;
            }
            if (city.getOwner().getHappiness() < 0) {
                foodIncrease /= 3;
            }
            city.setFood(city.getFood() + foodIncrease);
            if (city.getFood() > 20) {
                Citizen newCitizen = new Citizen(city);
                city.addCitizen(newCitizen);
                city.setFood(city.getFood() - 20);
            }
        } else if (foodIncrease < 0) // Killing Citizens
        {
            foodIncrease = -foodIncrease;
            int numberOfDyingCitizens = foodIncrease / 2;
            for (int i = 0; i < numberOfDyingCitizens; i++) {
                city.removeCitiZen(i);
            }
            city.setFood(Math.max(city.getFood() - foodIncrease, 0));

        }
        city.setGold(city.getGold() + goldIncrease);
        city.setProduction(city.getProduction() + productionIncrease);
        city.setScience(city.getScience() + city.getCitizens().size());

        // update constructions
    }

    private int calculateCityFood(City city) {
        int foodIncrease = 0;
        for (Terrain terrain : city.getMainTerrains()) {
            foodIncrease += terrain.getTerrainTypes().getFood();
            if (terrain.getTerrainFeatureTypes() != null) {
                for (TerrainFeatureTypes terrainFeatureTypes : terrain.getTerrainFeatureTypes()) {
                    foodIncrease += terrainFeatureTypes.getFood();

                }
            }
            if (terrain.getTerrainImprovement() != null) {
                foodIncrease += terrain.getTerrainImprovement().getImprovementType().getFood();
            }
        }
        return foodIncrease;
    }

    private int calculateCityGold(City city) {
        int goldIncrease = 0;
        for (Terrain terrain : city.getMainTerrains()) {
            goldIncrease += terrain.getTerrainTypes().getGold();
            if (terrain.getTerrainFeatureTypes() != null) {
                for (TerrainFeatureTypes terrainFeatureTypes : terrain.getTerrainFeatureTypes()) {
                    goldIncrease += terrainFeatureTypes.getGold();

                }
            }
            if (terrain.getTerrainImprovement() != null) {
                goldIncrease += terrain.getTerrainImprovement().getImprovementType().getGold();
            }
        }
        return goldIncrease;
    }

    private int calculateCityProduction(City city) {
        int productionIncrease = 0;
        for (Terrain terrain : city.getMainTerrains()) {
            productionIncrease += terrain.getTerrainTypes().getProduct();
            if (terrain.getTerrainFeatureTypes() != null) {
                for (TerrainFeatureTypes terrainFeatureTypes : terrain.getTerrainFeatureTypes()) {
                    productionIncrease += terrainFeatureTypes.getProduct();

                }
            }
            if (terrain.getTerrainImprovement() != null) {
                productionIncrease += terrain.getTerrainImprovement().getImprovementType().getProduction();
            }
        }
        return productionIncrease;

    }

    public void destroyCity(Civilization destroyer, Civilization loser, City city) {
        loser.removeCity(city);
        destroyer.setHappiness(destroyer.getHappiness() + 25);
        // handle the tile itself


    }

    public void attachCity(Civilization civilization, City city) {
        civilization.addCity(city);
        city.setOwner(civilization);
        civilization.setHappiness(civilization.getHappiness() - 20);

    }

    public HashMap<String, String> cityOutput(City city) {

        HashMap<String, String> output = new HashMap<>();
        output.put("food", String.valueOf(city.getFood()));
        output.put("production", String.valueOf(city.getProduction()));
        output.put("gold", String.valueOf(city.getGold()));
        output.put("turns remaining until population increase", String.valueOf(city.getTurnsRemainingUntilPopulationIncrease()));
        return output;

    }

    public boolean containUnit(ArrayList<Technology> tech, TechnologyTypes technologyType) {
        for (int i = 0; i < tech.size(); i++) {
            if (tech.get(i).getTechnologyType() == technologyType) {
                return true;
            }
        }
        return false;
    }

    public String createUnitWithTurn(Matcher matcher, City city) {
        Civilization civilization = city.getOwner();
        String unitName = matcher.group("unitName");
        String lackTechnology = "You lack the required technology to construct this unit";
        String lackResources = "You lack the required resources to construct this unit";
        String unitAlreadyExists = "There is already a unit in this city";
        ArrayList<UnitTypes> rangedCombat = new ArrayList<>(List.of(UnitTypes.ARCHER, UnitTypes.CHARIOT_ARCHER, UnitTypes.CATAPULT, UnitTypes.CROSSBOWMAN, UnitTypes.TREBUCHET, UnitTypes.CANNON, UnitTypes.ARTILLERY));
        ArrayList<UnitTypes> nonRangedCombat = new ArrayList<>(List.of(UnitTypes.SCOUT, UnitTypes.SPEARMAN, UnitTypes.WARRIOR, UnitTypes.HORSESMAN, UnitTypes.SWORDSMAN, UnitTypes.KNIGHT, UnitTypes.LONGSWORDSMAN, UnitTypes.PIKEMAN, UnitTypes.CAVALRY, UnitTypes.LANCER, UnitTypes.MUSKETMAN, UnitTypes.RIFLEMAN, UnitTypes.ANTI_TANKGUN, UnitTypes.INFANTRY, UnitTypes.PANZER, UnitTypes.TANK));
        ArrayList<UnitTypes> nonCombat = new ArrayList<>(List.of(UnitTypes.SETTLER, UnitTypes.WORKER));

        for (UnitTypes unitTypes : rangedCombat) {
            if (getUnitTypeByName(unitName).equals(unitTypes)) {
                if (unitTypes.getTechnologyRequirements() != null && !containUnit(civilization.getTechnologies(), unitTypes.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getResource() != null && unitTypes.getResourceRequirements() != null && !city.getCentralTerrain().getResource().getResourceType().equals(unitTypes.getResourceRequirements())) {
                    return lackResources;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                }
                RangedCombatUnit newUnit = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, unitTypes, false, false, false, false, false, false);
                city.getConstructionWaitList().add(newUnit);
                return unitTypes.name() + " will be constructed in " + unitTypes.getTurn() + " turns";
            }
        }

        for (UnitTypes unitTypes : nonRangedCombat) {
            if (getUnitTypeByName(unitName).equals(unitTypes)) {
                if (unitTypes.getTechnologyRequirements() != null && !containUnit(civilization.getTechnologies(), unitTypes.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else if (city.getCentralTerrain().getResource() != null && unitTypes.getResourceRequirements() != null && !city.getCentralTerrain().getResource().getResourceType().equals(unitTypes.getResourceRequirements())) {
                    return lackResources;
                }
                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, unitTypes, false, false, false, false, false);
                city.getConstructionWaitList().add(newUnit);
                return unitTypes.name() + " will be constructed in " + unitTypes.getTurn() + " turns";
            }

        }

        for (UnitTypes unitTypes : nonCombat) {
            if (getUnitTypeByName(unitName).equals(unitTypes)) {
                if (unitTypes.getTechnologyRequirements() != null && !containUnit(civilization.getTechnologies(), unitTypes.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getNonCombatUnit() != null) {
                    return unitAlreadyExists;
                } else if (city.getCentralTerrain().getResource() != null && unitTypes.getResourceRequirements() != null && !city.getCentralTerrain().getResource().getResourceType().equals(unitTypes.getResourceRequirements())) {
                    return lackResources;
                }
                if (unitTypes.equals(UnitTypes.SETTLER)) {
                    if (civilization.getBooleanSettlerBuy()) {
                        NonCombatUnit newSettler = new NonCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SETTLER, false);
                        city.getConstructionWaitList().add(newSettler);
                        return unitTypes.name() + " will be constructed in " + unitTypes.getTurn() + " turns";
                    }

                } else {
                    NonCombatUnit newUnit = new NonCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, unitTypes, false);
                    city.getConstructionWaitList().add(newUnit);
                    return unitTypes.name() + " will be constructed in " + unitTypes.getTurn() + " turns";
                }
            }


        }

        return "invalid unit name";
    }

    public String createUnit(Matcher matcher, City city) {
        Civilization civilization = city.getOwner();
        String unitName = matcher.group("unitName");
        int money = city.getGold();
        String notEnoughMoney = "You do not have enough gold to construct this unit";
        String lackTechnology = "You lack the required technology to construct this unit";
        String lackResources = "You lack the required resources to construct this unit";
        String unitAlreadyExists = "There is already a unit in this city";
        String unitPurchasedSuccessfully = "Unit purchase was successful";

        switch (unitName) {
            case "ARCHER":
                if (money < UnitTypes.ARCHER.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.ARCHER.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newArcher = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.ARCHER, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.ARCHER.getCost());
                    civilization.addUnit(newArcher);
                    city.getCentralTerrain().setCombatUnit(newArcher);
                    return unitPurchasedSuccessfully;
                }

            case "CHARIOT_ARCHER":
                if (money < UnitTypes.CHARIOT_ARCHER.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.CHARIOT_ARCHER.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(ResourceTypes.HORSES)) {
                    return lackResources;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newChariotArcher = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CHARIOT_ARCHER, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.CHARIOT_ARCHER.getCost());
                    civilization.addUnit(newChariotArcher);
                    city.getCentralTerrain().setCombatUnit(newChariotArcher);
                    return unitPurchasedSuccessfully;
                }


            case "SCOUT":
                if (money < UnitTypes.SCOUT.getCost()) {
                    return notEnoughMoney;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newScout = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SCOUT, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.SCOUT.getCost());
                    civilization.addUnit(newScout);
                    city.getCentralTerrain().setCombatUnit(newScout);
                    return unitPurchasedSuccessfully;
                }


            case "SETTLER":
                if (money < UnitTypes.SETTLER.getCost()) {
                    return notEnoughMoney;
                } else if (city.getCentralTerrain().getNonCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    if (civilization.getBooleanSettlerBuy()) {
                        NonCombatUnit newSettler = new NonCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SETTLER, false);
                        civilization.setGold(money - UnitTypes.SETTLER.getCost());
                        civilization.addUnit(newSettler);
                        city.getCentralTerrain().setNonCombatUnit(newSettler);
                        return unitPurchasedSuccessfully;
                    }

                }


            case "SPEARMAN":
                if (money < UnitTypes.SPEARMAN.getCost()) {
                    return notEnoughMoney;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newScout = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SPEARMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.SPEARMAN.getCost());
                    civilization.addUnit(newScout);
                    city.getCentralTerrain().setCombatUnit(newScout);
                    return unitPurchasedSuccessfully;
                }


            case "WARRIOR":
                if (money < UnitTypes.WARRIOR.getCost()) {
                    return notEnoughMoney;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newWarrior = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.WARRIOR, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.WARRIOR.getCost());
                    civilization.addUnit(newWarrior);
                    city.getCentralTerrain().setCombatUnit(newWarrior);
                    return unitPurchasedSuccessfully;
                }


            case "WORKER":
                if (money < UnitTypes.WORKER.getCost()) {
                    return notEnoughMoney;
                } else if (city.getCentralTerrain().getNonCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonCombatUnit newWorker = new NonCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.WORKER, false);
                    civilization.setGold(money - UnitTypes.WORKER.getCost());
                    civilization.addUnit(newWorker);
                    city.getCentralTerrain().setNonCombatUnit(newWorker);
                    return unitPurchasedSuccessfully;
                }


            case "CATAPULT":
                if (money < UnitTypes.CATAPULT.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.CATAPULT.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.CATAPULT.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newCatapult = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CATAPULT, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.CATAPULT.getCost());
                    civilization.addUnit(newCatapult);
                    city.getCentralTerrain().setCombatUnit(newCatapult);
                    return unitPurchasedSuccessfully;
                }


            case "HORSESMAN":
                if (money < UnitTypes.HORSESMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.HORSESMAN.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.HORSESMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newHorsesman = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.HORSESMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.HORSESMAN.getCost());
                    civilization.addUnit(newHorsesman);
                    city.getCentralTerrain().setCombatUnit(newHorsesman);
                    return unitPurchasedSuccessfully;
                }


            case "SWORDSMAN":
                if (money < UnitTypes.SWORDSMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.SWORDSMAN.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.SWORDSMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newSwordsman = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.SWORDSMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.SWORDSMAN.getCost());
                    civilization.addUnit(newSwordsman);
                    city.getCentralTerrain().setCombatUnit(newSwordsman);
                    return unitPurchasedSuccessfully;
                }


            case "CROSSBOWMAN":
                if (money < UnitTypes.CROSSBOWMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.CROSSBOWMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newCrossbowman = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CROSSBOWMAN, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.CROSSBOWMAN.getCost());
                    civilization.addUnit(newCrossbowman);
                    city.getCentralTerrain().setCombatUnit(newCrossbowman);
                    return unitPurchasedSuccessfully;
                }


            case "KNIGHT":
                if (money < UnitTypes.KNIGHT.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.KNIGHT.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.KNIGHT.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newKnight = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.KNIGHT, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.KNIGHT.getCost());
                    civilization.addUnit(newKnight);
                    city.getCentralTerrain().setCombatUnit(newKnight);
                    return unitPurchasedSuccessfully;

                }


            case "LONGSWORDSMAN":
                if (money < UnitTypes.LONGSWORDSMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.LONGSWORDSMAN.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.LONGSWORDSMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newLong = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.LONGSWORDSMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.LONGSWORDSMAN.getCost());
                    civilization.addUnit(newLong);
                    city.getCentralTerrain().setCombatUnit(newLong);
                    return unitPurchasedSuccessfully;
                }


            case "PIKEMAN":
                if (money < UnitTypes.PIKEMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.PIKEMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newPikeman = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.PIKEMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.PIKEMAN.getCost());
                    civilization.addUnit(newPikeman);
                    city.getCentralTerrain().setCombatUnit(newPikeman);
                    return unitPurchasedSuccessfully;
                }


            case "TREBUCHET":
                if (money < UnitTypes.TREBUCHET.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.TREBUCHET.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.TREBUCHET.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newTrebuchet = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.TREBUCHET, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.TREBUCHET.getCost());
                    civilization.addUnit(newTrebuchet);
                    city.getCentralTerrain().setCombatUnit(newTrebuchet);
                    return unitPurchasedSuccessfully;

                }


            case "CANNON":
                if (money < UnitTypes.CANNON.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.CANNON.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newCannon = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CANNON, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.CANNON.getCost());
                    civilization.addUnit(newCannon);
                    city.getCentralTerrain().setCombatUnit(newCannon);
                    return unitPurchasedSuccessfully;

                }


            case "CAVALRY":
                if (money < UnitTypes.CAVALRY.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.CAVALRY.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.CAVALRY.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.CAVALRY, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.CAVALRY.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;

                }


            case "LANCER":
                if (money < UnitTypes.LANCER.getCost()) {
                    return notEnoughMoney;
                } else if (!city.getCentralTerrain().getResource().getResourceType().equals(UnitTypes.LANCER.getResourceRequirements())) {
                    return lackResources;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.LANCER.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.LANCER, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.LANCER.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


            case "MUSKETMAN":
                if (money < UnitTypes.MUSKETMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.MUSKETMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.MUSKETMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.MUSKETMAN.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


            case "RIFLEMAN":
                if (money < UnitTypes.RIFLEMAN.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.RIFLEMAN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.RIFLEMAN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.RIFLEMAN.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;

                }


            case "ANTI_TANKGUN":
                if (money < UnitTypes.ANTI_TANKGUN.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.ANTI_TANKGUN.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.ANTI_TANKGUN, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.ANTI_TANKGUN.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


            case "ARTILLERY":
                if (money < UnitTypes.ARTILLERY.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.ARTILLERY.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    RangedCombatUnit newUnit = new RangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.ARTILLERY, false, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.ARTILLERY.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


            case "INFANTRY":
                if (money < UnitTypes.INFANTRY.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.INFANTRY.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.INFANTRY, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.INFANTRY.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


            case "PANZER":
                if (money < UnitTypes.PANZER.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.PANZER.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.PANZER, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.PANZER.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


            case "TANK":
                if (money < UnitTypes.TANK.getCost()) {
                    return notEnoughMoney;
                } else if (!containUnit(civilization.getTechnologies(), UnitTypes.TANK.getTechnologyRequirements())) {
                    return lackTechnology;
                } else if (city.getCentralTerrain().getCombatUnit() != null) {
                    return unitAlreadyExists;
                } else {
                    NonRangedCombatUnit newUnit = new NonRangedCombatUnit(city.getCentralTerrain().getX(), city.getCentralTerrain().getY(), 0, 0, 0, 0, false, false, UnitTypes.TANK, false, false, false, false, false);
                    civilization.setGold(money - UnitTypes.TANK.getCost());
                    civilization.addUnit(newUnit);
                    city.getCentralTerrain().setCombatUnit(newUnit);
                    return unitPurchasedSuccessfully;
                }


        }
        return "invalid unit name";
    }


    public void assignCitizen(City city, Citizen citizen, Terrain tile) {
        if (city.getCitizens().contains(citizen)) {
            if (city.getNeighbors().contains(tile) && citizen.getHasWork()) {
                citizen.assignWork(tile);
                System.out.println("Citizen assigned successfully");
                return;
            }
        }
        System.out.println("error");

    }


    public String buyTile(int x, int y, City city) {
        Terrain tile = this.databaseController.getTerrainByCoordinates(x, y);
        ArrayList<Terrain> mainTerrains = city.getMainTerrains();
        if (NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(mainTerrains, this.map).contains(tile)) {
            if (city.getGold() < tile.getGold()) {
                return "Not enough money";
            }
            city.setGold(city.getGold() - tile.getGold());
            mainTerrains.add(tile);
            city.setMainTerrains(mainTerrains);
            return "Tile bought successfully";
        }
        return "You cannot buy this tile";

    }


    public ArrayList<Terrain> getNeighborTerrainsOfOneTerrain(Terrain terrain, Map map) {
        ArrayList<Terrain> neighbors = new ArrayList<>();
        Terrain[][] copy_map = map.getTerrain();
        int x_beginning = terrain.getX();
        int y_beginning = terrain.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x_beginning + i < 0 || x_beginning + i >= map.getROW() || y_beginning + j < 0 || y_beginning + j >= map.getCOL() || y_beginning % 2 == 0 && ((i == 0 && j == 0) || (i == 1 && j == 1)) || y_beginning % 2 == 1 && ((i == 0 && j == 0) || (i == -1 && j == -1))) {

                } else {
                    neighbors.add(copy_map[x_beginning + i][y_beginning + j]);
                }


            }

        }
        return neighbors;

    }

    public ArrayList<Terrain> NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(ArrayList<Terrain> terrains, Map map) {

        ArrayList<Terrain> neighbors = new ArrayList<>();
        for (Terrain terrain : terrains) {
            for (Terrain terrain2 : getNeighborTerrainsOfOneTerrain(terrain, map)) {
                neighbors.addAll(getNeighborTerrainsOfOneTerrain(terrain2, map));
            }
        }

        neighbors.removeAll(terrains);

        return deleteExcessTerrain(neighbors);

    }

    public ArrayList<Terrain> NeighborsAtADistanceOfTwoFromAnArraylistOfTerrains(ArrayList<Terrain> terrains, Map map) {

        ArrayList<Terrain> neighbors = new ArrayList<>();
        ArrayList<Terrain> neighborsAtADistanceOfOne = NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(terrains, map);

        neighbors.addAll(neighborsAtADistanceOfOne);
        neighbors.addAll(NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(neighborsAtADistanceOfOne, map));

        neighbors.removeAll(terrains);

        return deleteExcessTerrain(neighbors);

    }

    public ArrayList<Terrain> deleteExcessTerrain(ArrayList<Terrain> terrains) {
        ArrayList<Terrain> finalTerrains = new ArrayList<>();
        for (Terrain terrain : terrains) {
            boolean isNew = true;
            for (Terrain terrain1 : finalTerrains) {
                if (terrain.equals(terrain1)) {
                    isNew = false;
                    break;
                }
            }

            if (isNew) {
                finalTerrains.add(terrain);
            }
        }

        return finalTerrains;
    }

    public UnitTypes getUnitTypeByName(String name) {
        for (UnitTypes unitTypes : UnitTypes.values()) {
            if (unitTypes.name().equals(name)) {
                return unitTypes;
            }
        }
        return null;
    }

    public void removeCitizenFromWork(Citizen citizen) {
        if (citizen != null && citizen.getHasWork()) {
            citizen.setHasWork(false);
            citizen.deleteWork();
        }
        System.out.println("error");
    }

    public Boolean oneCombatTurn(City city, CombatUnit attacker) {
        double cityCombatStrength = city.getCombatStrength();
        int attackerCombatStrength = attacker.getCombatStrength();
        Terrain terrain = this.databaseController.getTerrainByCoordinates(attacker.getX(), attacker.getY());
        int modifier = terrain.getTerrainTypes().getCombatModifier();
        if (terrain.getTerrainFeatureTypes() != null) {
            for (TerrainFeatureTypes terrainFeatureTypes : terrain.getTerrainFeatureTypes()) {
                modifier += terrainFeatureTypes.getCombatModifier();
            }
        }
        if (attacker.getUnitType().equals(UnitTypes.CATAPULT) || attacker.getUnitType().equals(UnitTypes.TREBUCHET) || attacker.getUnitType().equals(UnitTypes.ARTILLERY) || attacker.getUnitType().equals(UnitTypes.CANNON)) {
            modifier += 10;
        }
        attackerCombatStrength = attackerCombatStrength * (1 + (modifier / 100));
        city.setHP(city.getHP() - attackerCombatStrength + 1);
        attacker.setHP(attacker.getHP() - cityCombatStrength);
        if (attacker.getHP() <= 0) {
            Civilization unitOwner = this.databaseController.getContainerCivilization(attacker);
            unitOwner.removeUnit(attacker);
            Terrain tile = this.databaseController.getTerrainByCoordinates(attacker.getX(), attacker.getY());
            tile.setCombatUnit(null);
            System.out.println("The city won.");
            return false;
        }
        if (city.getHP() <= 0) {
            System.out.println("The city lost.");
            return true;
            /*Civilization civilization = city.getOwner();
            civilization.removeCity(city);*/
            //Unit bayad bere tush
            // bayad bebinim turn kie

        }
        return false;
    }

    public void whatToDoWithTheCity(String input, City city, Civilization civilization) {
        if (civilization.getUnits().contains(city.getCentralTerrain().getCombatUnit()) && city.getHP() <= 0) {
            if (input.equals("ATTACH CITY")) {
                this.attachCity(civilization, city);
            } else {
                this.destroyCity(civilization, city.getOwner(), city);
            }
        }
    }

    public boolean rangedAttackToCityForOneTurn(RangedCombatUnit attacker, City city) {
        int combatStrength = attacker.getUnitType().getRangedCombatStrengh();
        int combatRange = attacker.getUnitType().getRange();
        city.setHP(city.getHP() - combatStrength + 1);
        return city.getHP() <= 0;

    }


}
