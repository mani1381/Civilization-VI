package com.example.civilization.Model.City;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Model.Buildings.Building;
import com.example.civilization.Model.Buildings.BuildingTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Civilization;
import com.example.civilization.Model.Terrain;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonCombatUnit;
import com.example.civilization.Model.Units.Unit;

import java.util.ArrayList;

public class City {
    private final Civilization founder;
    private final Terrain centralTerrain;
    private ArrayList<Unit> constructionWaitList = new ArrayList<>();

    private ArrayList<Building> buildingWaitlist = new ArrayList<>();

    private ArrayList<Building> buildings = new ArrayList<>();
    private Civilization owner;
    private boolean isPuppet;
    private ArrayList<Terrain> neighbors = new ArrayList<>();
    private int population;
    private double HP;
    private String type;
    private double combatStrength;
    private boolean iConstructingUnit;
    private int rangedCombatStrength;
    private boolean garrisoned;
    private boolean isCaptured;
    private ArrayList<Citizen> citizens;
    private boolean isBuildingSomething;
    private int food;
    private int science;
    private int gold;
    private int production;
    private int turnsRemainingUntilPopulationIncrease;
    private int foodStorage;
    private boolean isUnderAttack;
    private ArrayList<Terrain> mainTerrains = new ArrayList<>();
    private CityController cityController = new CityController();

    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;


    public City(Civilization founder, Civilization owner, Terrain centralTerrain, int HP, String type, int combatStrength, int rangedCombatStrength) {
        this.founder = founder;
        this.owner = owner;
        this.isPuppet = false;
        this.centralTerrain = centralTerrain;
        this.HP = HP;
        this.type = type;
        this.combatStrength = combatStrength;
        if (this.centralTerrain != null && this.centralTerrain.getTerrainTypes().equals(TerrainTypes.HILLS))
        {
            this.combatStrength += 20;
            this.HP += 7;
        }
        this.iConstructingUnit = false;
        this.rangedCombatStrength = rangedCombatStrength;
        this.garrisoned = false;
        this.isCaptured = false;
        this.citizens = new ArrayList<Citizen>();
        this.citizens.add(new Citizen(this));
        this.isBuildingSomething = false;
        this.food = 0;
        this.science = 0;
        this.foodStorage = 0;
        this.mainTerrains.add(centralTerrain);

    }


    public Civilization getFounder() {
        return this.founder;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }



    public int getScience() {
        return science;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public Civilization getOwner() {
        return this.owner;
    }

    public void setOwner(Civilization owner) {
        this.owner = owner;
    }

    public boolean isIsPuppet() {
        return this.isPuppet;
    }

    public boolean getIsPuppet() {
        return this.isPuppet;
    }

    public void setIsPuppet(boolean isPuppet) {
        this.isPuppet = isPuppet;
    }

    public Terrain getCentralTerrain() {
        return this.centralTerrain;
    }


    public double getHP() {
        return this.HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCombatStrength() {
        return this.combatStrength;
    }

    public void setCombatStrength(double combatStrength) {
        this.combatStrength = combatStrength;
    }

    public boolean isIConstructingUnit() {
        return this.iConstructingUnit;
    }

    public boolean getIConstructingUnit() {
        return this.iConstructingUnit;
    }

    public void setIConstructingUnit(boolean iConstructingUnit) {
        this.iConstructingUnit = iConstructingUnit;
    }

    public int getRangedCombatStrength() {
        return this.rangedCombatStrength;
    }

    public void setRangedCombatStrength(int rangedCombatStrength) {
        this.rangedCombatStrength = rangedCombatStrength;
    }


    public boolean getGarrisoned() {
        return this.garrisoned;
    }

    public void setGarrisoned(boolean garrisoned) {
        this.garrisoned = garrisoned;
    }

    public boolean isIsCaptured() {
        return this.isCaptured;
    }

    public boolean getIsCaptured() {
        return this.isCaptured;
    }

    public void setIsCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    public ArrayList<Citizen> getCitizens() {
        return this.citizens;
    }

    public void setCitizens(ArrayList<Citizen> citizens) {
        this.citizens = citizens;
    }

    public City HP(int HP) {
        setHP(HP);
        return this;
    }

    public City type(String type) {
        setType(type);
        return this;
    }

    public City combatStrength(int combatStrength) {
        setCombatStrength(combatStrength);
        return this;
    }

    public City iConstructingUnit(boolean iConstructingUnit) {
        setIConstructingUnit(iConstructingUnit);
        return this;
    }

    public City rangedCombatStrength(int rangedCombatStrength) {
        setRangedCombatStrength(rangedCombatStrength);
        return this;
    }

    public City garrisoned(boolean garrisoned) {
        setGarrisoned(garrisoned);
        return this;
    }

    public City isCaptured(boolean isCaptured) {
        setIsCaptured(isCaptured);
        return this;
    }

    public City citizens(ArrayList<Citizen> citizens) {
        setCitizens(citizens);
        return this;
    }


    public CombatUnit getCombatUnit()
    {
        return combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit)
    {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit()
    {
        return nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit)
    {
        this.nonCombatUnit = nonCombatUnit;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public int getGold()
    {
        return gold;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }

    public int getProduction()
    {
        return production;
    }

    public void setProduction(int production)
    {
        this.production = production;
    }

    public int getTurnsRemainingUntilPopulationIncrease()
    {
        return turnsRemainingUntilPopulationIncrease;
    }

    public void setTurnsRemainingUntilPopulationIncrease(int turnsRemainingUntilPopulationIncrease)
    {
        this.turnsRemainingUntilPopulationIncrease = turnsRemainingUntilPopulationIncrease;
    }
    public void setNeighbors(ArrayList<Terrain> neighbour)
    {
       this.neighbors = neighbour;
    }
    public ArrayList<Terrain> getNeighbors()
    {
        return neighbors;
    }

    public int getFoodStorage()
    {
        return foodStorage;
    }

    public void setFoodStorage(int foodStorage)
    {
        this.foodStorage = foodStorage;
    }

    public void addCitizen ( Citizen citizen)
    {
        ArrayList<Citizen> cityCitizens = this.getCitizens();
        cityCitizens.add(citizen);
        this.setCitizens(cityCitizens);

    }

    public void removeCitiZen(int index) {
        this.citizens.remove(index);
    }

    public ArrayList<Terrain> getMainTerrains() {
        return this.mainTerrains;
    }

    public void setMainTerrains(ArrayList<Terrain> mainTerrains) {
        this.mainTerrains = mainTerrains;
    }

    public ArrayList<Unit> getConstructionWaitList() {
        return constructionWaitList;
    }

    public void setConstructionWaitList(ArrayList<Unit> constructionWaitList) {
        this.constructionWaitList = constructionWaitList;
    }

    public ArrayList<Building> getBuildingWaitlist() {
        return buildingWaitlist;
    }

    public void setBuildingWaitlist(ArrayList<Building> buildingWaitlist) {
        this.buildingWaitlist = buildingWaitlist;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

}