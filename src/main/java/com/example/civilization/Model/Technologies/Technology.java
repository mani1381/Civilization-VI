package com.example.civilization.Model.Technologies;

public class Technology {

    private boolean underResearch;
    private int costsForResearch;
    private TechnologyTypes technologyType;
    private boolean isAvailable;


    public Technology(boolean underResearch, int costsForResearch, TechnologyTypes technologyType, boolean isAvailable) {
        this.underResearch = underResearch;
        this.costsForResearch = costsForResearch;
        this.technologyType = technologyType;
        this.isAvailable = isAvailable;
    }


    public boolean isIsAvailable() {
        return this.isAvailable;
    }

    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isUnderResearch() {
        return this.underResearch;
    }

    public boolean getUnderResearch() {
        return this.underResearch;
    }

    public void setUnderResearch(boolean underResearch) {
        this.underResearch = underResearch;
    }

    public int getCostsForResearch() {
        return this.costsForResearch;
    }

    public void setCostsForResearch(int costsForResearch) {
        this.costsForResearch = costsForResearch;
    }

    public TechnologyTypes getTechnologyType() {
        return this.technologyType;
    }

    public void setTechnologyType(TechnologyTypes technologyType) {
        this.technologyType = technologyType;
    }

    @Override
    public String toString() {
        return "{" + "costsForResearch='" + getCostsForResearch() + "'" + ", technologyType='" + getTechnologyType().name() + "'" + ", isAvailable='" + isIsAvailable() + "'" + ", leads to following technologies='" + getTechnologyType().getTechnologyUnlocks().toString() + "'" + "}";
    }


}
