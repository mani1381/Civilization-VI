package com.example.civilization.Model.City;

import com.example.civilization.Model.Terrain;

public class Citizen {
    private City city;
    private boolean hasWork;
    private Terrain terrain;
    private int production;

    public Citizen(City city) {
        this.city = city;
        this.hasWork = false;
        this.terrain = city.getCentralTerrain();
        this.production = 1;
    }

    public void assignWork(Terrain tile) {
        this.production += 1;
        this.terrain = tile;
        this.hasWork = true;

    }

    public void deleteWork()
    {
        this.production = 1;
        this.terrain = city.getCentralTerrain();

    }

    public boolean getHasWork()
    {
        return hasWork;
    }

    public void setHasWork(boolean hasWork)
    {
        this.hasWork = hasWork;
    }

    public int getProduction()
    {
        return production;
    }

    public void setProduction(int production)
    {
        this.production = production;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
