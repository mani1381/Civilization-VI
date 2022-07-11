package com.example.civilization.Model.Resources;

public class Resource {

    private int food;
    private int gold;
    private int production;
    private ResourceTypes resourceType;
    private String name;

    public Resource(ResourceTypes resourceType) {
        this.food = resourceType.getFood();
        this.gold = resourceType.getGold();
        this.production = resourceType.getProduction();
        this.resourceType = resourceType;
    }

    public Resource clone() {
        return new Resource(this.getResourceType());
    }

    public int getFood() {
        return this.food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getGold() {
        return this.gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getProduction() {
        return this.production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceTypes getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceTypes resourceType) {
        this.resourceType = resourceType;
    }
}
