package com.example.civilization.Model.Technologies;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.Buildings.BuildingTypes;
import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Units.UnitTypes;

import java.util.ArrayList;

public enum TechnologyTypes {
    AGRICULTURE(20, new ArrayList<>() {
    }, new ArrayList<>() {
        {
            add(ImprovementTypes.FARM);
        }
    }),POTTERY(35, new ArrayList<>() {
        {
            add(TechnologyTypes.AGRICULTURE);

        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.GRANARY);
        }
    }), ANIMAL_HUSBANDRY(35, new ArrayList<>() {
        {
            add(TechnologyTypes.AGRICULTURE);

        }
    }, new ArrayList<>() {
        {
            add(ResourceTypes.HORSES);
            add(ImprovementTypes.PASTURE);
        }
    }), ARCHERY(35, new ArrayList<>() {
        {
            add(TechnologyTypes.AGRICULTURE);

        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.ARCHER);
        }
    }), MINING(35, new ArrayList<>() {
        {
            add(TechnologyTypes.AGRICULTURE);
        }
    }, new ArrayList<>() {
        {
            add(ImprovementTypes.MINE);
            // TODO remove FOREST
        }
    }), BRONZE_WORKING(55, new ArrayList<>() {
        {
            add(TechnologyTypes.MINING);

        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.SPEARMAN);
            add(BuildingTypes.BARRACKS);
            // TODO REMOVING JUNGLE
        }
    }), CALENDAR(70, new ArrayList<>() {
        {
            add(TechnologyTypes.POTTERY);

        }
    }, new ArrayList<>() {
        {
            add(ImprovementTypes.PLANTATION);
        }
    }), MASONRY(55, new ArrayList<>() {
        {
            add(TechnologyTypes.MINING);

        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.WALLS);
            add(ImprovementTypes.QUARRY);
            // TODO remove marsh
        }
    }), THE_WHEEL(55, new ArrayList<>() {
        {
            add(TechnologyTypes.ANIMAL_HUSBANDRY);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.CHARIOT_ARCHER);
            add(BuildingTypes.WATERMILL);
            add(ImprovementTypes.ROAD);
            // TODO build a road
        }
    }), TRAPPING(55, new ArrayList<>() {
        {
            add(TechnologyTypes.ANIMAL_HUSBANDRY);
        }
    }, new ArrayList<>() {
        {
            add(ImprovementTypes.TRADINGPOST);
            add(ImprovementTypes.CAMP);
        }
    }), WRITING(55, new ArrayList<>() {
        {
            add(TechnologyTypes.POTTERY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.LIBRARY);
        }
    }), CONSTRUCTION(100, new ArrayList<>() {
        {
            add(TechnologyTypes.MASONRY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.COLOSSEUM);
            // TODO bridges over rivers
        }
    }), HORSEBACK_RIDING(100, new ArrayList<>() {
        {
            add(TechnologyTypes.THE_WHEEL);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.HORSESMAN);
            add(BuildingTypes.STABLE);
            add(BuildingTypes.CIRCUS);
        }
    }), IRON_WORKING(150, new ArrayList<>() {
        {
            add(TechnologyTypes.BRONZE_WORKING);
        }
    }, new ArrayList<>() {
        {
            add(ResourceTypes.IRON);
            add(UnitTypes.SWORDSMAN);
            add(BuildingTypes.ARMORY);
        }
    }), MATHEMATICS(100, new ArrayList<>() {
        {
            add(TechnologyTypes.THE_WHEEL);
            add(TechnologyTypes.ARCHERY);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.CATAPULT);
            add(BuildingTypes.COURTHOUSE);
        }
    }), PHILOSOPHY(100, new ArrayList<>() {
        {
            add(TechnologyTypes.WRITING);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.BURIAL_TOMB);
            add(BuildingTypes.TEMPLE);
        }
    }), THEOLOGY(250, new ArrayList<>() {
        {
            add(TechnologyTypes.CALENDAR);
            add(TechnologyTypes.PHILOSOPHY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.MONASTERY);
            add(BuildingTypes.GARDEN);
        }
    }), CIVIL_SERVICE(400, new ArrayList<>() {
        {
            add(TechnologyTypes.PHILOSOPHY);
            add(TechnologyTypes.TRAPPING);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.PIKEMAN);
        }
    }), CURRENCY(250, new ArrayList<>() {
        {
            add(TechnologyTypes.MATHEMATICS);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.MARKET);
            add(BuildingTypes.MINT);
        }
    }), ENGINEERING(250, new ArrayList<>() {
        {
            add(TechnologyTypes.MATHEMATICS);
            add(TechnologyTypes.CONSTRUCTION);

        }
    }, new ArrayList<>() {
        {
            add(ImprovementTypes.MANUFACTORY);
            add(ImprovementTypes.LUMBERMILL);
        }
    }),METAL_CASTING(240, new ArrayList<>() {
        {
            add(TechnologyTypes.IRON_WORKING);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.FORGE);
            add(BuildingTypes.WORKSHOP);
        }
    }),CHIVALRY(440, new ArrayList<>() {
        {
            add(TechnologyTypes.CIVIL_SERVICE);
            add(TechnologyTypes.HORSEBACK_RIDING);
            add(TechnologyTypes.CURRENCY);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.KNIGHT);
            add(BuildingTypes.CASTLE);
        }
    }), EDUCATION(440, new ArrayList<>() {
        {
            add(TechnologyTypes.THEOLOGY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.UNIVERSITY);
        }
    }), MACHINERY(440, new ArrayList<>() {
        {
            add(TechnologyTypes.ENGINEERING);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.CROSSBOWMAN);
            // TODO 1.2 faster road movement
        }
    }), PHYSICS(440, new ArrayList<>() {
        {
            add(TechnologyTypes.ENGINEERING);
            add(TechnologyTypes.METAL_CASTING);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.TREBUCHET);
        }
    }), STEEL(440, new ArrayList<>() {
        {
            add(TechnologyTypes.METAL_CASTING);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.LONGSWORDSMAN);
        }
    }), ACOUSTICS(650, new ArrayList<>() {
        {
            add(TechnologyTypes.EDUCATION);
        }
    }, new ArrayList<>() {
    }),BANKING(650, new ArrayList<>() {
        {
            add(TechnologyTypes.EDUCATION);
            add(TechnologyTypes.CHIVALRY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.SATRAPS_COURT);
            add(BuildingTypes.BANK);

        }
    }), PRINTING_PRESS(650, new ArrayList<>() {
        {
            add(TechnologyTypes.MACHINERY);
            add(TechnologyTypes.PHYSICS);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.THEATER);
        }
    }), GUNPOWDER(680, new ArrayList<>() {
        {
            add(TechnologyTypes.PHYSICS);
            add(TechnologyTypes.STEEL);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.MUSKETMAN);
        }
    }), ARCHAEOLOGY(1300, new ArrayList<>() {
        {
            add(TechnologyTypes.ACOUSTICS);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.MUSEUM);
            add(BuildingTypes.OPERA_HOUSE);
        }
    }), SCIENTIFIC_THEORY(1300, new ArrayList<>() {
        {
            add(TechnologyTypes.ACOUSTICS);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.PUBLIC_SCHOOL);
            add(ResourceTypes.COAL);
        }
    }),CHEMISTRY(900, new ArrayList<>() {
        {
            add(TechnologyTypes.GUNPOWDER);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.CANNON);
        }


    }), METALLURGY(900, new ArrayList<>() {
        {
            add(TechnologyTypes.GUNPOWDER);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.LANCER);
        }
    }),  ECONOMICS(900, new ArrayList<>() {
        {
            add(TechnologyTypes.BANKING);
            add(TechnologyTypes.PRINTING_PRESS);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.WINDMILL);
        }
    }), FERTILIZER(1300, new ArrayList<>() {
        {
            add(TechnologyTypes.CHEMISTRY);
        }
    }, new ArrayList<>() {
        {
            // todo Farms without Fresh Water yield increased by 1
        }
    }), MILITARY_SCIENCE(1300, new ArrayList<>() {
        {
            add(TechnologyTypes.ECONOMICS);
            add(TechnologyTypes.CHEMISTRY);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.CAVALRY);
            add(BuildingTypes.MILITARY_ACADEMY);
        }
    }), RIFLING(1425, new ArrayList<>() {
        {
            add(TechnologyTypes.METALLURGY);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.RIFLEMAN);
        }
    }), BIOLOGY(1680, new ArrayList<>() {
        {
            add(TechnologyTypes.ARCHAEOLOGY);
            add(TechnologyTypes.SCIENTIFIC_THEORY);
        }
    }, new ArrayList<>() {
        {
        add(BuildingTypes.HOSPITAL);
        }
    }), STEAM_POWER(1680, new ArrayList<>() {
        {
            add(TechnologyTypes.SCIENTIFIC_THEORY);
            add(TechnologyTypes.MILITARY_SCIENCE);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.FACTORY);
        }
    }),  DYNAMITE(1900, new ArrayList<>() {
        {
            add(TechnologyTypes.FERTILIZER);
            add(TechnologyTypes.RIFLING);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.ARTILLERY);
        }
    }), ELECTRICITY(1900, new ArrayList<>() {
        {
            add(TechnologyTypes.BIOLOGY);
            add(TechnologyTypes.STEAM_POWER);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.STOCK_EXCHANGE);
        }
    }), RAILROAD(1900, new ArrayList<>() {
        {
            add(TechnologyTypes.STEAM_POWER);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.ARSENAL);
            add(ImprovementTypes.RAILROAD);
        }
    }), REPLACEABLE_PARTS(1900, new ArrayList<>() {
        {
            add(TechnologyTypes.STEAM_POWER);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.ANTI_TANKGUN);
            add(UnitTypes.INFANTRY);
        }
    }),COMBUSTION(2200, new ArrayList<>() {
        {
            add(TechnologyTypes.REPLACEABLE_PARTS);
            add(TechnologyTypes.RAILROAD);
            add(TechnologyTypes.DYNAMITE);
        }
    }, new ArrayList<>() {
        {
            add(UnitTypes.TANK);
            add(UnitTypes.PANZER);
        }
    }),RADIO(2200, new ArrayList<>() {
        {
            add(TechnologyTypes.ELECTRICITY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.BROADCAST_TOWER);
        }
    }),  TELEGRAPH(2200, new ArrayList<>() {
        {
            add(TechnologyTypes.ELECTRICITY);
        }
    }, new ArrayList<>() {
        {
            add(BuildingTypes.MILITARY_BASE);
        }
    });

    int cost;
    ArrayList<TechnologyTypes> requirement;

    ArrayList<Object> unlocks;
    TechnologyTypes(int cost, ArrayList<TechnologyTypes> requirement,
                    ArrayList<Object> unlocks) {
        this.cost = cost;
        this.requirement = requirement;
        this.unlocks = unlocks;
    }

    public ArrayList<TechnologyTypes> getRequirements() {
        return requirement;
    }

    public ArrayList<TechnologyTypes> getTechnologyUnlocks() {
        return DatabaseController.getInstance().unlockTechnologies(this);
    }

    public ArrayList<Object> getUnlocks() {
        return unlocks;
    }


    public int getCost() {
        return cost;
    }
}