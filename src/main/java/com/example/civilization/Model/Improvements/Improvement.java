package com.example.civilization.Model.Improvements;


public class Improvement {
    private int passedTurns = 2;
    private int x;
    private int y;

    private boolean isAvailable = false;

    private boolean isBeingWorkedOn = true;
    private boolean isPillaged = false;
    private boolean hasToBeDeleted = false;
    private boolean isBeingRepaired = false;
    private ImprovementTypes improvementType;

    public Improvement(int x, int y, ImprovementTypes improvementType) {
        this.x = x;
        this.y = y;
        this.improvementType = improvementType;
    }

    public Improvement clone(){
        return new Improvement(this.getX(), this.getY(), this.getImprovementType());
    }

    public boolean isPillaged() {
        return isPillaged;
    }

    public void setPillaged(boolean pillaged) {
        isPillaged = pillaged;
    }

    public boolean isHasToBeDeleted() {
        return hasToBeDeleted;
    }

    public void setHasToBeDeleted(boolean hasToBeDeleted) {
        this.hasToBeDeleted = hasToBeDeleted;
    }

    public boolean isBeingRepaired() {
        return isBeingRepaired;
    }

    public void setBeingRepaired(boolean beingRepaired) {
        isBeingRepaired = beingRepaired;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPassedTurns() {
        return passedTurns;
    }

    public void setPassedTurns(int passedTurns) {
        this.passedTurns = passedTurns;
    }

    public ImprovementTypes getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(ImprovementTypes improvementType) {
        this.improvementType = improvementType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isBeingWorkedOn() {
        return isBeingWorkedOn;
    }

    public void setBeingWorkedOn(boolean beingWorkedOn) {
        isBeingWorkedOn = beingWorkedOn;
    }
}