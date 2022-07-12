package com.example.civilization.View;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Controllers.CombatController;
import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Controllers.saveData;
import com.example.civilization.Enums.GameEnums;
import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Resources.Resource;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Terrain;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.User;
import com.example.civilization.Model.City.City;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    private final DatabaseController databaseController = DatabaseController.getInstance();
    private final ArrayList<User> users;
    private CityController cityController;
    private CombatController combatController;

    public GameMenu(DatabaseController databaseController, ArrayList<User> users) {
        this.cityController = new CityController();
        cityController.setDatabaseController(databaseController);
        this.users = users;
        this.combatController = new CombatController(DatabaseController.getInstance(), this.cityController);
    }

    public void run(Scanner scanner) {

        DatabaseController.getInstance().getMap().generateMap();
        DatabaseController.getInstance().setCivilizations(users);

        while (true) {
            for (User user : users) {
                System.out.println(user.getUsername() + "'s turn");
                DatabaseController.getInstance().setAllUnitsUnfinished(user);
                while (!DatabaseController.getInstance().isAllTasksFinished(user)) {
                    Matcher matcher;
                    String input = scanner.nextLine();
                    // input.replaceFirst("^\\s*", "");
                    // input = input.trim().replaceAll("\\s+", " ");
                    if(input.equals("EXIT")){
                        saveData saveData = new saveData();
                        saveData.saveUsers(DatabaseController.getInstance().getDatabase());
                        System.exit(0);
                    }
                    if ((matcher = GameEnums.getMatcher(input, GameEnums.SELECT_UNIT)) != null) {
                        selectUnit(user, matcher);
                        while (DatabaseController.getInstance().HasOneUnitBeenSelected()) {
                            input = scanner.nextLine();
                            if((matcher = GameEnums.getMatcher(input, GameEnums.ATTACK_CITY) ) != null)
                            {
                                if (DatabaseController.getInstance().getSelectedCombatUnit() != null)
                                {
                                    CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
                                    String temp = combatController.unitAttackCity(matcher, user);
                                    if (temp.equals("You won.The city is yours. Do you wish to destroy it or make it yours?"))
                                    {
                                        System.out.println("You won.The city is yours. Do you wish to destroy it or make it yours?");
                                        input = scanner.nextLine();
                                        Terrain terrain = DatabaseController.getInstance().getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY());
                                        City city = terrain.getCity();
                                        this.cityController.whatToDoWithTheCity(input, city, user.getCivilization());
                                    }
                                    else
                                        System.out.println(temp);
                                }
                                else
                                    System.out.println("You cannot attack a city with a non-combat unit.");
                            }
                            else if ((matcher = GameEnums.getMatcher(input, GameEnums.RANGED_ATTACK_CITY) ) != null)
                            {
                                if ( DatabaseController.getInstance().getSelectedCombatUnit() != null)
                                {
                                    CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
                                    String temp = combatController.rangedAttack(matcher, user);
                                    if ( temp.equals("You won. The city is yours. Please move a combat unit to the tile to win it"))
                                    {
                                        System.out.println("You won. The city is yours. Please move a combat unit to the tile to win it");

                                    }
                                    else
                                        System.out.println(temp);
                                }
                                else
                                    System.out.println("You cannot attack a city with a non-combat unit.");
                            }

                            else
                                oneUnitHasBeenSelected(input, matcher, user);

                        }
                    }
                    else if ((matcher = GameEnums.getMatcher(input, GameEnums.SELECT_CITY_POSITION)) != null) {
                        System.out.println("City selected successfully");
                        input = scanner.nextLine();
                        selectedCityActions(getCityFromMatcher(matcher), input);
                    }

                    else{
                        runCommands(user, input);
                    }


                }
                if ( user.getCivilization().getCities() != null)
                {
                    for ( City city : user.getCivilization().getCities())
                    {
                        this.cityController.playTurn(city);
                    }
                }
                DatabaseController.getInstance().movementOfAllUnits(user);
                DatabaseController.getInstance().setTerrainsOfEachCivilization(user);
                DatabaseController.getInstance().setHappinessUser(user);
                DatabaseController.getInstance().setScience(user);
                user.getCivilization().setAvailability();
                DatabaseController.getInstance().setUnitsParametersAfterEachTurn(users);
            }
        }
    }

    public void showInfo(Scanner scanner, Matcher matcher, User user) {
        switch (matcher.group("section")) {
            case "RESEARCH":
                System.out.println(databaseController.researchInfo(user));
                break;
            case "UNITS":
                System.out.println(databaseController.unitsInfo(user));
                String input = scanner.nextLine();
                if ((matcher = GameEnums.getMatcher(input, GameEnums.ACTIVATE_UNIT)) != null) {

                    int x = Integer.parseInt(matcher.group("x"));
                    int y = Integer.parseInt(matcher.group("y"));
                    String name = matcher.group("subdivision");
                    System.out.println(DatabaseController.getInstance().activateUnit(user, name, x, y));

                } else if (input.equals("INFO MILITARY")) {

                    System.out.println(DatabaseController.getInstance().militaryOverview(user));
                } else {
                    System.out.println("You decided to not select any unit");
                }

                break;
            case "CITIES":

                System.out.println(DatabaseController.getInstance().cityPanel(user));
                String input2 = scanner.nextLine();
                if ((matcher = GameEnums.getMatcher(input2, GameEnums.CITY_INFO)) != null) {
                    cityInfoOptions(matcher, user);

                } else if (input2.equals("INFO ECONOMIC")) {

                    System.out.println(DatabaseController.getInstance().economicOverview(user));
                } else {
                    System.out.println("You decided to not select any unit");
                }
                break;

            case "DEMOGRAPHICS":
                System.out.println(DatabaseController.getInstance().demographicPanel());

                break;
            case "NOTIFICATIONS":
                System.out.println(DatabaseController.getInstance().notificationHistory(user));

                break;
            case "MILITARY":
                System.out.println(DatabaseController.getInstance().militaryOverview(user));

                break;
            case "ECONOMIC":
                System.out.println(DatabaseController.getInstance().economicOverview(user));

                break;

            default:
                System.out.println("INVALID COMMAND");
                break;
        }
    }

    public void selectUnit(User user, Matcher matcher) {
        if (matcher.group("subdivision").equals("COMBAT")) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            System.out.println(DatabaseController.getInstance().selectAndDeselectCombatUnit(user, x, y));

        } else if (matcher.group("subdivision").equals("NONCOMBAT")) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));

            System.out.println(DatabaseController.getInstance().selectAndDeselectNonCombatUnit(user, x, y));

        } else {
            System.out.println("INVALID COMMAND");
        }
    }

    public void buildUnit(Matcher matcher, User user) {
        String name = matcher.group("subdivision");
        switch (name) {
            case "ROAD":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.ROAD));

                break;
            case "RAILROAD":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.RAILROAD));

                break;
            case "FARM":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.FARM));

                break;
            case "MINE":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.MINE));

                break;
            case "TRADINGPOST":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.TRADINGPOST));

                break;
            case "LUMBERMILL":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.LUMBERMILL));

                break;
            case "PASTURE":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.PASTURE));

                break;
            case "CAMP":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.CAMP));

                break;
            case "PLANTATION":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.PLANTATION));

                break;
            case "QUARRY":
                System.out.println(DatabaseController.getInstance().buildingAnImprovement(user, ImprovementTypes.QUARRY));

                break;
            default:
                System.out.println("INVALID COMMAND");

        }

    }

    public void deleteUnit(Matcher matcher, User user) {
        String name = matcher.group("subdivision");
        switch (name) {
            case "FOREST":
                System.out.println(DatabaseController.getInstance().deleteFeatures("FOREST"));

                break;
            case "JUNGLE":
                System.out.println(DatabaseController.getInstance().deleteFeatures("JUNGLE"));

                break;
            case "MARSH":
                System.out.println(DatabaseController.getInstance().deleteFeatures("MARSH"));

                break;
            case "ROUTE":
                System.out.println(DatabaseController.getInstance().deleteFeatures("ROUTE"));

                break;
            default:
                System.out.println("INVALID COMMAND");

        }
    }

    public void repairImprovement() {
        System.out.println(DatabaseController.getInstance().repairImprovement());

    }

    public void moveUnit(User user, Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        System.out.println(DatabaseController.getInstance().unitMovement(x, y, user));

    }

    public void selectTechnology(Matcher matcher, User user) {
        String name = matcher.group("name");
        switch (name) {
            case "AGRICULTURE":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.AGRICULTURE));

                break;
            case "ANIMAL_HUSBANDRY":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ANIMAL_HUSBANDRY));

                break;
            case "ARCHERY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ARCHERY));

                break;
            case "BRONZE_WORKING":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.BRONZE_WORKING));

                break;
            case "CALENDAR":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.CALENDAR));

                break;
            case "MASONRY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.MASONRY));

                break;
            case "MINING":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.MINING));

                break;
            case "POTTERY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.POTTERY));

                break;
            case "THE_WHEEL":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.THE_WHEEL));

                break;
            case "TRAPPING":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.TRAPPING));

                break;
            case "WRITING":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.WRITING));

                break;
            case "CONSTRUCTION":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.CONSTRUCTION));

                break;
            case "HORSEBACK_RIDING":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.HORSEBACK_RIDING));

                break;
            case "IRON_WORKING":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.IRON_WORKING));

                break;
            case "MATHEMATICS":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.MATHEMATICS));

                break;
            case "PHILOSOPHY":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.PHILOSOPHY));

                break;
            case "CHIVALRY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.CHIVALRY));

                break;
            case "CIVIL_SERVICE":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.CIVIL_SERVICE));

                break;
            case "CURRENCY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.CURRENCY));

                break;
            case "EDUCATION":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.EDUCATION));

                break;
            case "ENGINEERING":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ENGINEERING));

                break;
            case "MACHINERY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.MACHINERY));

                break;
            case "METAL_CASTING":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.METAL_CASTING));

                break;
            case "PHYSICS":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.PHYSICS));

                break;
            case "STEEL":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.STEEL));

                break;
            case "THEOLOGY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.THEOLOGY));

                break;
            case "ACOUSTICS":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ACOUSTICS));

                break;
            case "ARCHAEOLOGY":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ARCHAEOLOGY));

                break;
            case "BANKING":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.BANKING));

                break;
            case "CHEMISTRY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.CHEMISTRY));

                break;
            case "ECONOMICS":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ECONOMICS));

                break;
            case "FERTILIZER":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.FERTILIZER));

                break;
            case "GUNPOWDER":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.GUNPOWDER));

                break;
            case "METALLURGY":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.METALLURGY));

                break;
            case "MILITARY_SCIENCE":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.MILITARY_SCIENCE));

                break;
            case "PRINTING_PRESS":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.PRINTING_PRESS));

                break;
            case "SCIENTIFIC_THEORY":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.SCIENTIFIC_THEORY));

                break;
            case "BIOLOGY":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.BIOLOGY));

                break;
            case "COMBUSTION":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.COMBUSTION));

                break;
            case "DYNAMITE":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.DYNAMITE));

                break;
            case "ELECTRICITY":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.ELECTRICITY));

                break;
            case "RADIO":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.RADIO));

                break;
            case "RAILROAD":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.RAILROAD));

                break;
            case "REPLACEABLE_PARTS":
                System.out.println(
                        DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.REPLACEABLE_PARTS));

                break;
            case "STEAM_POWER":
                System.out
                        .println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.STEAM_POWER));

                break;
            case "TELEGRAPH":
                System.out.println(DatabaseController.getInstance().choosingATechnologyToStudy(user, TechnologyTypes.TELEGRAPH));

                break;

        }

    }

    public static String increaseTurnCheat(Matcher matcher) {

        int amount = Integer.parseInt(matcher.group("amount"));
        return DatabaseController.getInstance().increaseTurnCheat(amount);

    }

    public static String increaseGoldCheat(User user, Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        return DatabaseController.getInstance().increaseGoldCheat(user, amount);

    }

    public static String increaseHappinessCheat(User user, Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        return DatabaseController.getInstance().increaseHappinessCheat(user, amount);

    }

    public static String increaseScienceCheat(User user, Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        return DatabaseController.getInstance().increaseScienceCheat(user, amount);
    }

    public static String buyTechnologyCheat(Matcher matcher, User user) {
        String name = matcher.group("name");
        for(TechnologyTypes technologyTypes : TechnologyTypes.values()){
            if(technologyTypes.name().equals(name)){
                return DatabaseController.getInstance().buyTechnologyCheat(user, technologyTypes);
            }
        }
        return "";

    }

    public static String repairImprovementCheat(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return DatabaseController.getInstance().repairCheatImprovement(x, y);

    }

    public static String deleteImprovementCheat(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return DatabaseController.getInstance().deleteCheatImprovement(x, y);
    }

    public static String setCheatTerrainType(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String name = matcher.group("name");
        for(TerrainTypes terrainTypes : TerrainTypes.values()){
            return DatabaseController.getInstance().setCheatTerrainType(terrainTypes, x, y);
        }
        return "";

    }

    public static String setCheatTerrainFeature(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String name = matcher.group("name");
        for(TerrainFeatureTypes terrainFeatureTypes : TerrainFeatureTypes.values()){
            if(terrainFeatureTypes.name().equals(name)){
                return  DatabaseController.getInstance().setCheatTerrainFeatureType(terrainFeatureTypes, x, y);
            }
        }
       return "";
    }

    public static String setCheatResource(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String name = matcher.group("name");
        for(ResourceTypes resourceTypes : ResourceTypes.values()){
            if(resourceTypes.name().equals(name)){
                return DatabaseController.getInstance().setCheatResource(resourceTypes, x, y);
            }
        }
        return "";

    }

    public static String setCheatImprovement(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String name = matcher.group("name");
        for(ImprovementTypes improvementTypes : ImprovementTypes.values()){
            if(improvementTypes.name().equals(name)){
                return DatabaseController.getInstance().setCheatImprovement(improvementTypes, x, y);
            }
        }

        return "";

    }

    public static String setCheatUnit(User user, Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String name = matcher.group("name");
        return DatabaseController.getInstance().setCheatUnit(user, name, x, y);

    }

    public static String buyCheatTile(User user, Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return DatabaseController.getInstance().buyCheatTile(user, x, y);
    }

    public static String cheatMoveCombatUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return DatabaseController.getInstance().cheatMoveCombatUnit(x, y);
    }

    public static String cheatMoveNonCombatUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return DatabaseController.getInstance().cheatMoveNonCombatUnit(x, y);

    }

    public void runCommands(User user, String input) {
        Matcher matcher;
        if ((matcher = GameEnums.getMatcher(input, GameEnums.INFO)) != null) {
            Scanner scanner = new Scanner(System.in);
             showInfo(scanner, matcher, user);

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SELECT_TECHNOLOGY)) != null) {
            selectTechnology(matcher, user);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_TURN)) != null) {
            increaseTurnCheat(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_GOLD)) != null) {
            increaseGoldCheat(user, matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_HAPPINESS)) != null) {
            increaseHappinessCheat(user, matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_SCIENCE)) != null) {
            increaseScienceCheat(user, matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.BUY_TECHNOLOGY)) != null) {
            buyTechnologyCheat(matcher, user);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.BUY_CHEAT_TILE)) != null) {
            buyCheatTile(user, matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_UNIT)) != null) {
            setCheatUnit(user, matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_IMPROVEMENT)) != null) {
            setCheatImprovement(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_RESOURCE)) != null) {
            setCheatResource(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_TERRAIN_FEATURE_TYPE)) != null) {
            setCheatTerrainFeature(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_TERRAIN_TYPE)) != null) {
            setCheatTerrainType(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.DELETE_CHEAT_IMPROVEMENT)) != null) {
            deleteImprovementCheat(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.REPAIR_CHEAT_IMPROVEMENT)) != null) {
            repairImprovementCheat(matcher);
        }  else if ((matcher = GameEnums.getMatcher(input, GameEnums.IMPROVEMENT_REPAIR)) != null) {
            repairImprovement();

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_BUILD)) != null) {
            buildUnit(matcher, user);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_REMOVE)) != null) {
            deleteUnit(matcher, user);

        } else if (input.equals("SHOW MAP")) {
            String[][] result = DatabaseController.getInstance().getMap().printMap(DatabaseController.getInstance().getDatabase(), user);
            for (int i = 0; i < DatabaseController.getInstance().getMap().getROW(); i++) {
                for (int j = 0; j < DatabaseController.getInstance().getMap().getIteration(); j++) {
                    System.out.println(result[i][j]);
                }
            }
        } else {
            System.out.println("INVALID COMMAND");
        }
    }

    public void oneUnitHasBeenSelected(String input, Matcher matcher, User user) {
        if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_MOVETO)) != null) {
            moveUnit(user, matcher);
        }
        else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_SLEEP)) != null) {
            DatabaseController.getInstance().changingTheStateOfAUnit("sleep");
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.COMBAT_UNIT_CHEAT_MOVE)) != null) {
            cheatMoveCombatUnit(matcher);
        } else if (GameEnums.getMatcher(input, GameEnums.GARRISON_CITY) != null)
        {
            System.out.println(this.cityController.garrisonCity(DatabaseController.getInstance().getSelectedCombatUnit()));

        }
        else if ( GameEnums.getMatcher(input, GameEnums.UNGARRISON_CITY) != null)
        {
            System.out.println(this.cityController.ungarrisonCity(DatabaseController.getInstance().getSelectedCombatUnit()));
        }
        else if ((matcher = GameEnums.getMatcher(input, GameEnums.NON_COMBAT_UNIT_CHEAT_MOVE)) != null) {
            cheatMoveNonCombatUnit(matcher);
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_ALERT)) != null) {
            if (DatabaseController.getInstance().getSelectedCombatUnit() == null) {
                System.out.println("this unit is not a combat unit");
            } else {
                System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("alert"));

            }

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_FORTIFY)) != null) {
            if (DatabaseController.getInstance().getSelectedCombatUnit() == null) {
                System.out.println("this unit is not a combat unit");
            } else {
                System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("fortify"));

            }

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_PILLAGE)) != null) {
            if (DatabaseController.getInstance().getSelectedCombatUnit() == null) {
                System.out.println("this unit is not a combat unit");
            } else {
                CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
                System.out.println(DatabaseController.getInstance().pillageImprovement(combatUnit,
                        DatabaseController.getInstance().getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY())));
            }

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_FORTIFY_HEAL)) != null) {
            if (DatabaseController.getInstance().getSelectedCombatUnit() == null) {
                System.out.println("this unit is not a combat unit");
            } else {
                System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("fortify until heal"));

            }

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_GARRISON)) != null) {
            if (DatabaseController.getInstance().getSelectedCombatUnit() == null) {
                System.out.println("this unit is not a combat unit");
            } else {
                System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("garrison"));

            }

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_SETUP_RANGED)) != null) {
            if (DatabaseController.getInstance().getSelectedCombatUnit() == null) {
                System.out.println("this unit is not a combat unit");
            } else if (DatabaseController.getInstance().getSelectedCombatUnit() != null) {
                System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("setup ranged"));

            }

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_FOUND_CITY)) != null) {
            if ( DatabaseController.getInstance().getSelectedNonCombatUnit() != null)
            {
                this.cityController.foundCity(user.getCivilization(), DatabaseController.getInstance().getSelectedNonCombatUnit(),
                        DatabaseController.getInstance().getTerrainByCoordinates(
                                DatabaseController.getInstance().getSelectedNonCombatUnit().getX(),
                                DatabaseController.getInstance().getSelectedNonCombatUnit().getY()));
            }
            else
            {
                System.out.println("This unit cannot found a city");
            }
        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_CANCEL_MISSION)) != null) {

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_WAKE)) != null) {
            System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("wake"));

        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.UNIT_DELETE)) != null) {
            System.out.println(DatabaseController.getInstance().changingTheStateOfAUnit("delete"));

        }
        else {
            System.out.println("INVALID COMMAND");
        }
    }

    public City getCityFromMatcher(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        City city = DatabaseController.getInstance().getTerrainByCoordinates(x, y).getCity();
        if (city != null) {
            return city;
        } else {
            System.out.println(" There are no cities in these coordinates");
            return null;
        }

    }

    public void selectedCityActions(City city, String input) {
        if (city != null) {
            Matcher matcher;
            if ((matcher = GameEnums.getMatcher(input, GameEnums.ASSIGN_CITIZEN)) != null) {
                int index = Integer.parseInt(matcher.group("CitizenIndex"));
                this.cityController.assignCitizen(city, city.getCitizens().get(index),
                        DatabaseController.getInstance().getTerrainByCoordinates(Integer.parseInt(matcher.group("X")),
                                Integer.parseInt(matcher.group("Y"))));
            } else if ((matcher = GameEnums.getMatcher(input, GameEnums.BUY_UNIT)) != null) {
                System.out.println(this.cityController.createUnit(matcher, city));
            } else if ( (matcher = GameEnums.getMatcher(input, GameEnums.CREATE_UNIT)) != null)
            {
                System.out.println(this.cityController.createUnitWithTurn(matcher, city));
            }
            else if ((matcher = GameEnums.getMatcher(input, GameEnums.REMOVE_FROM_WORK)) != null) {
                this.cityController
                        .removeCitizenFromWork(city.getCitizens().get(Integer.parseInt(matcher.group("CitizenIndex"))));
            } else if ((matcher = GameEnums.getMatcher(input, GameEnums.BUY_TILE)) != null) {
                this.cityController.buyTile(Integer.parseInt(matcher.group("X")), Integer.parseInt(matcher.group("Y")),
                        city);
            }

        }

    }

    public void cityInfoOptions(Matcher matcher, User user)
    {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (DatabaseController.getInstance().getCityByCoordinates(x, y, user) == null) {
            System.out.print("You do not own any city with this coordinates");
        }

        System.out.println("Food " + this.cityController
                .cityOutput(DatabaseController.getInstance().getCityByCoordinates(x, y, user)).get("food"));
        System.out.println("production " + this.cityController
                .cityOutput(DatabaseController.getInstance().getCityByCoordinates(x, y, user)).get("production"));
        System.out.println("gold " + this.cityController
                .cityOutput(DatabaseController.getInstance().getCityByCoordinates(x, y, user)).get("gold"));
        System.out.println("turns remaining until population increase "
                + this.cityController.cityOutput(DatabaseController.getInstance().getCityByCoordinates(x, y, user))
                        .get("turns remaining until population increase"));

    }

    public void setCityController(CityController cityController) {
        this.cityController = cityController;
    }
}
