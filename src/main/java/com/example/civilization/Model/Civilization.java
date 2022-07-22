package com.example.civilization.Model;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Technologies.Technology;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonCombatUnit;
import com.example.civilization.Model.Units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {

    private ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<Terrain> ownedTerrains = new ArrayList<>();
    private ArrayList<Terrain> terrains = new ArrayList<>();
    private ArrayList<Terrain> visibleTerrains = new ArrayList<>();
    private ArrayList<Terrain> revealedTerrains = new ArrayList<>();

    private HashMap<Civilization,Boolean> statusWithOtherCivilizations = new HashMap<>();
    private int gold;
    private int happiness;
    private ArrayList<Technology> technologies = new ArrayList<>();
    private ArrayList<TechnologyTypes> technologyTypes = new ArrayList<>();

    private HashMap<Civilization,String> tradeRequests = new HashMap<>();

    private HashMap<Civilization,String> demandRequests = new HashMap<>();
    private String name;
    private int science = 500;
    private int score = 0;
    private boolean canBuySettler;

    private boolean hasEverHadCity = false;

    private City currentCapital;
    private City firstCapital;

    public Civilization(int gold, int happiness, String name) {

        Technology technology = new Technology(false, 0, TechnologyTypes.AGRICULTURE, true);
        Technology technology1 = new Technology(true, 0, TechnologyTypes.ANIMAL_HUSBANDRY, false);
        Technology technology2 = new Technology(false, 0, TechnologyTypes.POTTERY, true);
        technologies.add(technology);
        technologies.add(technology1);
        technologies.add(technology2);
        this.gold = gold;
        this.happiness = happiness;
        this.name = name;
    }



    public boolean getBooleanSettlerBuy() {
        return this.canBuySettler;
    }

    public void setBooleanSettlerBuy(boolean bool) {
        this.canBuySettler = bool;
    }

    public ArrayList<Terrain> getOwnedTerrains() {
        return this.ownedTerrains;
    }

    public void setOwnedTerrains(ArrayList<Terrain> ownedTerrains) {
        this.ownedTerrains = ownedTerrains;
    }

    public ArrayList<Terrain> getVisibleTerrains() {
        return this.visibleTerrains;
    }

    public void setVisibleTerrains(ArrayList<Terrain> visibleTerrains) {
        this.visibleTerrains = visibleTerrains;
    }

    public ArrayList<Terrain> getRevealedTerrains() {
        return this.revealedTerrains;
    }

    public void setRevealedTerrains(ArrayList<Terrain> revealedTerrains) {
        this.revealedTerrains = revealedTerrains;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Terrain> getTerrains() {
        return this.terrains;
    }

    public void setTerrains(ArrayList<Terrain> terrains) {
        this.terrains = terrains;
    }

    public ArrayList<Unit> getUnits() {
        return this.units;
    }

    public void setUnits(ArrayList<Unit> unit) {
        this.units = unit;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getHappiness() {
        return this.happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public boolean containsUnit(Unit unit2) {
        for (Unit unit : units) {
            if (unit.equals(unit2)) {
                return true;
            }

        }
        return false;
    }

    public boolean containsCombatUnit(int x, int y) {
        for (Unit unit : units) {
            if (unit instanceof CombatUnit) {
                if (unit.getX() == x && unit.getY() == y) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean containsNonCombatUnit(int x, int y) {
        for (Unit unit : units) {
            if (unit instanceof NonCombatUnit) {
                if (unit.getX() == x && unit.getY() == y) {
                    return true;
                }
            }

        }
        return false;
    }

    public ArrayList<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ArrayList<Technology> technologies) {
        this.technologies = technologies;
    }

    public void setAvailability() {
        for (Technology technology : this.technologies) {
            if (!technology.getIsAvailable() && technology.getUnderResearch()) {
                technology.setCostsForResearch(technology.getCostsForResearch() + science);
                if (technology.getCostsForResearch() >= technology.getTechnologyType().getCost()) {
                    technology.setIsAvailable(true);
                }
            }
        }
    }

    public void increaseGold(int gold) {
        this.gold += gold;
    }


    public void addUnit(Unit unit) {
        this.units.add(unit);
    }


    public void addCity(City city) {
        this.cities.add(city);
    }

    public void removeUnit(Unit unit) {
        for (Unit testUnit : this.units) {
            if (testUnit.equals(unit)) {
                this.units.remove(testUnit);
                return;
            }

        }
    }

    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public void removeCity(City city) {
        for (City cityTest : this.cities) {
            if (cityTest.equals(city)) {
                this.cities.remove(city);
                return;
            }

        }
    }

    public ArrayList<TechnologyTypes> getTechnologyTypes() {
        return technologyTypes;
    }

    public void setTechnologyTypes(ArrayList<TechnologyTypes> technologyTypes) {
        this.technologyTypes = technologyTypes;
    }

    public City getCurrentCapital() {
        return currentCapital;
    }

    public void setCurrentCapital(City currentCapital) {
        this.currentCapital = currentCapital;
    }

    public City getFirstCapital() {
        return firstCapital;
    }

    public void setFirstCapital(City firstCapital) {
        this.firstCapital = firstCapital;
    }

    public boolean isHasEverHadCity() {
        return hasEverHadCity;
    }

    public void setHasEverHadCity(boolean hasEverHadCity) {
        this.hasEverHadCity = hasEverHadCity;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HashMap<Civilization, Boolean> getStatusWithOtherCivilizations() {
        return statusWithOtherCivilizations;
    }

    public void setStatusWithOtherCivilizations(HashMap<Civilization, Boolean> statusWithOtherCivilizations) {
        this.statusWithOtherCivilizations = statusWithOtherCivilizations;
    }

    public HashMap<Civilization, String> getTradeRequests() {
        return tradeRequests;
    }

    public HashMap<Civilization, String> getDemandRequests() {
        return demandRequests;
    }
}