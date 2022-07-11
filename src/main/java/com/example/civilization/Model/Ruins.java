package com.example.civilization.Model;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Technologies.Technology;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Units.NonCombatUnit;
import com.example.civilization.Model.Units.UnitTypes;

import java.util.ArrayList;
import java.util.Random;

public class Ruins {

    NonCombatUnit nonCombatUnit;
    int ruinsGold;
    Technology technologyRuins;

    Civilization civilization;

    City city;


    public Ruins(int x, int y, Civilization civilization, Map map) {

        this.civilization = civilization;

        Random ran = new Random();
        int nxt = ran.nextInt(2);
        if (nxt == 0) {
            if (map.getTerrain()[x][y].getNonCombatUnit() == null) {
                NonCombatUnit settler = new NonCombatUnit(x, y, 0, 0, 0, 0, false, false, UnitTypes.SETTLER, false);
                nonCombatUnit = settler;
                civilization.getUnits().add(settler);
                map.getTerrain()[x][y].setNonCombatUnit(settler);
                System.out.println("settler added " + x + " " + y);
            }

        } else {
            if (map.getTerrain()[x][y].getNonCombatUnit() == null) {
                NonCombatUnit worker = new NonCombatUnit(x, y, 0, 0, 0, 0, false, false, UnitTypes.WORKER, false);
                nonCombatUnit = worker;
                civilization.getUnits().add(worker);
                map.getTerrain()[x][y].setNonCombatUnit(worker);
                System.out.println("worker added" + x + " " + y);
            }
        }

        if (civilization.getCities().size() > 0) {
            nxt = ran.nextInt(civilization.getCities().size());
            this.city = civilization.getCities().get(nxt);
            int gold = ran.nextInt(200);
            ruinsGold = gold;
            civilization.getCities().get(nxt).setGold(civilization.getCities().get(nxt).getGold() + gold);
        }

        ArrayList<TechnologyTypes> values = DatabaseController.getInstance().unlockableTechnologies(DatabaseController.getInstance().getUserByCivilization(civilization));
        if (!values.isEmpty()) {
            TechnologyTypes value = values.get(new Random().nextInt(values.size()));
            boolean isNew = true;
            for (Technology technology : civilization.getTechnologies()) {
                if (technology.getTechnologyType().equals(value)) {
                    System.out.println("you have this tech");
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                Technology technology = new Technology(false, 0, value, true);
                technologyRuins = technology;
                civilization.getTechnologies().add(technology);
                System.out.println("Tech added " + x + " " + y);
            }

        }


    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }

    public int getRuinsGold() {
        return ruinsGold;
    }

    public Technology getTechnologyRuins() {
        return technologyRuins;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public City getCity() {
        return city;
    }
}