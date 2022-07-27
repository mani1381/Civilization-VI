package com.example.civilization.Controllers;

import com.example.civilization.FXMLcontrollers.GameMapController;
import com.example.civilization.Model.Buildings.Building;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.*;
import com.example.civilization.Model.Improvements.Improvement;
import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Resources.Resource;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.Technology;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Units.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.util.Comparator.naturalOrder;

public class DatabaseController {
    private static DatabaseController instance;
    public HashMap<User, String> notificationHistory = new HashMap<>();
    private Database database;

    public DatabaseController() {
        this.database = Database.getInstance();
    }

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    public Database getDatabase() {
        return this.database;
    }

    public Map getMap() {

        return this.database.getMap();
    }

    public String createUser(String u, String p, String n) {

        ArrayList<User> users = this.database.getAllUsers();

        for (User user : users) {
            if (user.getUsername().equals(u)) {
                return "user with username " + u + " already exists";
            }
            if (user.getNickname().equals(n)) {
                System.out.println();
                return "user with nickname " + n + " already exists";
            }
        }

        User newUser = new User(u, p, n, null);
        this.database.addToAllUser(newUser);
        return "user created successfully!";
    }

    public User userLogin(String u, String p) {
        return this.database.getUserByUsernameAndPassword(u, p);
    }

    public String changeUserNickname(String n, User player) {

        User user = database.getUserByNickname(n);
        if (user != null) {
            return "user with nickname " + n + " already exists";
        }
        player.setNickname(n);
        return "nickname changed successfully!";
    }

    public String changePassword(String p, User user) {
        String currentPassword = user.getPassword();


        if (currentPassword.equals(p)) {
            return "please enter a new password";
        }
        user.setPassword(p);
        return "password changed successfully! Please Login again with your new password";
    }

    public String selectAndDeselectCombatUnit(User user, int x, int y) {
        Map map = this.getMap();
        int mapRows = map.getROW();
        int mapColumns = map.getCOL();
        if (x > mapRows || x < 0 || y > mapColumns || y < 0) {
            return "there is no tile with these coordinates";
        }
        if (this.database.getMap().getTerrain()[x][y].getCombatUnit() != null && this.database.getMap().getTerrain()[x][y].getCombatUnit().getIsFinished()) {
            return "you have selected this unit before";
        }
        if (user.getCivilization().containsUnit(this.database.getMap().getTerrain()[x][y].getCombatUnit())) {
            boolean initialIsSelectedValue = this.database.getMap().getTerrain()[x][y].getCombatUnit().getIsSelected();
            this.database.getMap().getTerrain()[x][y].getCombatUnit().setIsSelected(!initialIsSelectedValue);

            return "Combat unit was selected";
        }
        return "you do not have access to this unit";
    }

    public void deselectAllUnits() {
        if (DatabaseController.getInstance().getSelectedNonCombatUnit() != null) {
            DatabaseController.getInstance().getSelectedNonCombatUnit().setIsSelected(false);
        }
        if (DatabaseController.getInstance().getSelectedCombatUnit() != null) {
            DatabaseController.getInstance().getSelectedCombatUnit().setIsSelected(false);
        }
    }

    public String selectAndDeselectNonCombatUnit(User user, int x, int y) {
        Map map = this.getMap();
        int mapRows = map.getROW();
        int mapColumns = map.getCOL();
        if (x > mapRows || x < 0 || y > mapColumns || y < 0) {
            return "there is no tile with these coordinates";
        }
        if (this.database.getMap().getTerrain()[x][y].getNonCombatUnit() != null && this.database.getMap().getTerrain()[x][y].getNonCombatUnit().getIsFinished()) {
            return "you have selected this unit before";
        }
        if (user.getCivilization().containsUnit(this.database.getMap().getTerrain()[x][y].getNonCombatUnit())) {
            boolean initialIsSelectedValue = this.database.getMap().getTerrain()[x][y].getNonCombatUnit().getIsSelected();
            this.database.getMap().getTerrain()[x][y].getNonCombatUnit().setIsSelected(!initialIsSelectedValue);

            return "Noncombat unit was selected";
        }
        return "you do not have access to this unit";
    }

    public String changingTheStateOfACombatUnit(CombatUnit combatUnit, String action) {
        setAllParametersFalse(combatUnit);
        switch (action) {
            case "sleep":
                combatUnit.setIsAsleep(true);
                break;
            case "alert":
                combatUnit.setAlert(true);
                break;
            case "fortify":
                combatUnit.setFortify(true);
                break;
            case "fortify until heal":
                combatUnit.setFortifyUntilHeal(true);
                break;
            case "garrison":
                combatUnit.setIsGarrisoned(true);
                break;
            case "wake":
                combatUnit.setIsAsleep(false);
                break;
            case "delete":
                combatUnit = null;
                break;
            case "setup ranged":
                if (combatUnit.getUnitType().getCombatTypes().equals(CombatTypes.SIEGE)) {
                    ((RangedCombatUnit) combatUnit).setIsSetUpForRangedAttack(true);
                } else {
                    return "This unit doesn't need to be set up";
                }
                break;
        }
        if (!action.equals("delete")) {
            combatUnit.getNextTerrain().clear();
            combatUnit.setIsFinished(true);
            combatUnit.setIsSelected(false);
        }

        return "Unit will " + action;
    }

    public String changingTheStateOfANonCombatUnit(NonCombatUnit nonCombatUnit, String action) {
        setAllParametersFalse(nonCombatUnit);
        switch (action) {
            case "sleep" -> nonCombatUnit.setIsAsleep(true);
            case "wake" -> nonCombatUnit.setIsAsleep(false);
            case "found city" ->
                    CityController.foundCity(getContainerCivilization(nonCombatUnit), nonCombatUnit, getTerrainByCoordinates(nonCombatUnit.getX(), nonCombatUnit.getY()));
            case "delete" -> nonCombatUnit = null;
        }
        if (!action.equals("delete")) {
            nonCombatUnit.setIsFinished(true);
            nonCombatUnit.setIsSelected(false);
            nonCombatUnit.getNextTerrain().clear();
        }
        return "Unit will " + action;

    }

    public void setAllParametersFalse(Unit unit) {
        unit.setIsAsleep(false);
        if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).setAlert(false);
            ((CombatUnit) unit).setFortify(false);
            ((CombatUnit) unit).setFortifyUntilHeal(false);
            ((CombatUnit) unit).setIsGarrisoned(false);

        }
        if (unit instanceof RangedCombatUnit) {
            ((RangedCombatUnit) unit).setIsSetUpForRangedAttack(false);
        }
    }

    public String changingTheStateOfAUnit(String action) {
        CombatUnit combatUnit = getSelectedCombatUnit();
        NonCombatUnit nonCombatUnit = getSelectedNonCombatUnit();

        if (combatUnit != null) {
            return changingTheStateOfACombatUnit(combatUnit, action);
        } else {
            return changingTheStateOfANonCombatUnit(nonCombatUnit, action);
        }

    }

    public boolean HasOneUnitBeenSelected() {
        boolean isSelected = false;
        int row = this.database.getMap().getROW();
        int column = this.database.getMap().getCOL();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if ((this.database.getMap().getTerrain()[i][j].getCombatUnit() != null && this.database.getMap().getTerrain()[i][j].getCombatUnit().isIsSelected()) || (this.database.getMap().getTerrain()[i][j].getNonCombatUnit() != null && this.database.getMap().getTerrain()[i][j].getNonCombatUnit().isIsSelected())) {
                    isSelected = true;
                    break;
                }
            }
        }
        return isSelected;
    }

    public CombatUnit getSelectedCombatUnit() {
        int row = this.database.getMap().getROW();
        int column = this.database.getMap().getCOL();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.database.getMap().getTerrain()[i][j].getCombatUnit() != null && this.database.getMap().getTerrain()[i][j].getCombatUnit().isIsSelected()) {
                    return this.database.getMap().getTerrain()[i][j].getCombatUnit();
                }
            }
        }
        return null;
    }

    public NonCombatUnit getSelectedNonCombatUnit() {
        int row = this.database.getMap().getROW();
        int column = this.database.getMap().getCOL();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (this.database.getMap().getTerrain()[i][j].getNonCombatUnit() != null && this.database.getMap().getTerrain()[i][j].getNonCombatUnit().isIsSelected()) {
                    return this.database.getMap().getTerrain()[i][j].getNonCombatUnit();
                }
            }
        }
        return null;
    }

    public boolean isAllTasksFinished(User user) {
        for (Unit unit : user.getCivilization().getUnits()) {
            if (!unit.getIsFinished()) {
                return false;
            }
        }
        return true;
    }

    public void setAllUnitsUnfinished(User user) {
        for (Unit unit : user.getCivilization().getUnits()) {
            if (!unit.getIsAsleep() || !unit.getNextTerrain().isEmpty()) {
                unit.setIsFinished(false);
            }
            if (unit instanceof CombatUnit) {
                if (((CombatUnit) unit).getAlert() || ((CombatUnit) unit).getFortify() || ((CombatUnit) unit).getFortifyUntilHeal() || ((CombatUnit) unit).getIsGarrisoned()) {
                    unit.setIsFinished(true);
                }

            }

        }
    }

    public String unitMovement(int x_final, int y_final, User user) {
        Map map = this.getMap();
        int mapRows = map.getROW();
        int mapColumns = map.getCOL();
        if (x_final > mapRows || x_final < 0 || y_final > mapColumns || y_final < 0) {
            return "there is no tile with these coordinates";
        }
        CombatUnit combatUnit = getSelectedCombatUnit();
        NonCombatUnit nonCombatUnit = getSelectedNonCombatUnit();

        if (combatUnit != null) {
            return combatUnitMovement(combatUnit, x_final, y_final, user, map);

        } else if (nonCombatUnit != null) {
            return nonCombatUnitMovement(nonCombatUnit, x_final, y_final, user, map);
        }

        return "action completed";
    }

    public String combatUnitMovement(CombatUnit combatUnit, int x_final, int y_final, User user, Map map) {
        if (user.getCivilization().containsCombatUnit(x_final, y_final)) {
            return "you have another combat unit in this tile";
        }
        if (combatUnit.getNextTerrain() != null) {
            combatUnit.getNextTerrain().clear();
        }
        ArrayList<Terrain> path = new ArrayList<>();
        ArrayList<ArrayList<Terrain>> allPaths = new ArrayList<>();
        addingAllPath(0, combatUnit.getX(), combatUnit.getY(), x_final, y_final, map, path, allPaths);
        combatUnit.setNextTerrain(findingTheShortestPath(combatUnit, allPaths));
        if (combatUnit.getNextTerrain().isEmpty()) {
            return "You're unable to go to your destination";
        }
        combatUnit.setIsSelected(false);
        combatUnit.setIsFinished(true);
        return "action completed";
    }

    public String nonCombatUnitMovement(NonCombatUnit nonCombatUnit, int x_final, int y_final, User user, Map map) {
        if (user.getCivilization().containsNonCombatUnit(x_final, y_final)) {
            return "you have another non combat unit in this tile";
        }
        if (nonCombatUnit.getNextTerrain() != null) {
            nonCombatUnit.getNextTerrain().clear();
        }
        ArrayList<Terrain> path = new ArrayList<>();
        ArrayList<ArrayList<Terrain>> allPaths = new ArrayList<>();
        addingAllPath(0, nonCombatUnit.getX(), nonCombatUnit.getY(), x_final, y_final, map, path, allPaths);
        nonCombatUnit.setNextTerrain(findingTheShortestPath(nonCombatUnit, allPaths));
        if (nonCombatUnit.getNextTerrain().isEmpty()) {
            return "You're unable to go to your destination.";
        }
        nonCombatUnit.setIsSelected(false);
        nonCombatUnit.setIsFinished(true);
        return "action completed";
    }

    public void movementOfAllUnits(User user) {
        for (Unit unit : new ArrayList<>(user.getCivilization().getUnits())) {
            movementAsLongAsItHasMP(unit);
        }

    }

    public void movementAsLongAsItHasMP(Unit unit) {
        int indexOfLastTerrain;
        int movementCost = 0;
        if (unit.getNextTerrain() == null) {
            return;
        }

        for (indexOfLastTerrain = 0; indexOfLastTerrain < unit.getNextTerrain().size(); indexOfLastTerrain++) {
            Terrain terrain = findingTheContainerTerrain(unit);
            if (unit instanceof CombatUnit) {
                terrain.setCombatUnit(null);
                unit.getNextTerrain().get(indexOfLastTerrain).setCombatUnit((CombatUnit) unit);
            } else if (unit instanceof NonCombatUnit) {
                terrain.setNonCombatUnit(null);
                unit.getNextTerrain().get(indexOfLastTerrain).setNonCombatUnit((NonCombatUnit) unit);
            }

            unit.setXAndY(unit.getNextTerrain().get(indexOfLastTerrain).getX(), unit.getNextTerrain().get(indexOfLastTerrain).getY());
            if (unit.getNextTerrain().get(indexOfLastTerrain).isRuin()) {
                Ruins ruins = new Ruins(unit.getNextTerrain().get(indexOfLastTerrain).getX(), unit.getNextTerrain().get(indexOfLastTerrain).getY(), getContainerCivilization(unit), getMap());
                GameMapController.showingRuinsPopUp(ruins);
                unit.getNextTerrain().get(indexOfLastTerrain).setRuin(false);
            }
            movementCost += unit.getNextTerrain().get(indexOfLastTerrain).getTerrainTypes().getMovementCost();
            if (movementCost > unit.getUnitType().getMovement()) {
                break;
            }
            if (containEnemyInNearTerrains(unit)) {
                break;
            }

        }

//        ArrayList<Terrain> needToRemove = new ArrayList<>();
//        for (int i = 0; i < indexOfLastTerrain; i++) {
//            needToRemove.add(unit.getNextTerrain().get(i));
//        }
        unit.getNextTerrain().clear();

    }

    public boolean containEnemyInNearTerrains(Unit unit) {
        for (Terrain terrain : getNeighborTerrainsOfOneTerrain(getTerrainByCoordinates(unit.getX(), unit.getY()), getMap())) {
            if (terrain.getCombatUnit() != null && !getContainerCivilization(terrain.getCombatUnit()).equals(getContainerCivilization(unit))) {
                return true;
            }
        }
        return false;
    }

    public Terrain findingTheContainerTerrain(Unit unit) {
        Map map = this.getMap();
        int mapRows = map.getROW();
        int mapColumns = map.getCOL();
        for (int i = 0; i < mapRows; i++) {
            for (int j = 0; j < mapColumns; j++) {
                if (map.getTerrain()[i][j].containsUnit(unit)) {
                    return map.getTerrain()[i][j];
                }
            }
        }
        return null;
    }

    public void addingAllPath(int turn, int x_beginning, int y_beginning, int x_final, int y_final, Map map, ArrayList<Terrain> path, ArrayList<ArrayList<Terrain>> allPaths) {
        Terrain[][] copy_map = map.getTerrain();
        if ((x_beginning == x_final && y_beginning == y_final)) {
            allPaths.add(path);
            return;
        }
        if ((turn >= 6 && turn <= 10) && allPaths.size() > 0) {
            return;
        }
        if (turn == 10) {
            return;
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x_beginning + i < 0 || x_beginning + i >= map.getROW() || y_beginning + j < 0 || y_beginning + j >= map.getCOL() || y_beginning % 2 == 0 && ((i == 0 && j == 0) || (i == 1 && j == -1) || (i == 1 && j == 1)) || y_beginning % 2 == 1 && ((i == 0 && j == 0) || (i == -1 && j == 1) || (i == -1 && j == -1))) {

                } else {
                    ArrayList<Terrain> path_copy = new ArrayList<>(path);
                    path_copy.add(copy_map[x_beginning + i][y_beginning + j]);
                    if (map.hasRiver(copy_map[x_beginning][y_beginning], copy_map[x_beginning + i][y_beginning + j]) != null) {
                        continue;
                    }
                    addingAllPath(turn + 1, x_beginning + i, y_beginning + j, x_final, y_final, map, path_copy, allPaths);
                }

            }
        }

    }

    public ArrayList<Terrain> findingTheShortestPath(Unit unit, ArrayList<ArrayList<Terrain>> allPaths) {
        int movementCostOfTheShortestPath = 9999999;
        ArrayList<Terrain> shortestPath = new ArrayList<>();
        if (unit.getUnitType().equals(UnitTypes.SCOUT)) {
            for (ArrayList<Terrain> path : allPaths) {
                if (calculatingTheMovementCostForScout(path) < movementCostOfTheShortestPath) {
                    movementCostOfTheShortestPath = calculatingTheMovementCostForScout(path);
                    shortestPath = path;
                }
            }
        } else {
            for (ArrayList<Terrain> path : allPaths) {
                if (calculatingTheMovementCost(path) < movementCostOfTheShortestPath) {
                    movementCostOfTheShortestPath = calculatingTheMovementCost(path);
                    shortestPath = path;
                }
            }
        }

        for (Terrain terrain : shortestPath) {
            System.out.println(terrain.getX() + " " + terrain.getY());
        }
        return shortestPath;
    }

    public int calculatingTheMovementCost(ArrayList<Terrain> path) {
        int movementCost = 0;
        for (Terrain terrain : path) {
            if (terrain.getTerrainImprovement() != null && (terrain.getTerrainImprovement().getImprovementType().equals(ImprovementTypes.ROAD) || terrain.getTerrainImprovement().getImprovementType().equals(ImprovementTypes.RAILROAD))) {

                movementCost += 0.5 * terrain.getTerrainTypes().getMovementCost();
                if (terrain.getTerrainFeatureTypes() != null && terrain.getTerrainFeatureTypes().size() > 0) {
                    movementCost += 0.5 * terrain.getTerrainFeatureTypes().get(0).getMovementCost();
                }
            } else {

                movementCost += terrain.getTerrainTypes().getMovementCost();
                if (terrain.getTerrainFeatureTypes() != null && terrain.getTerrainFeatureTypes().size() > 0) {
                    movementCost += terrain.getTerrainFeatureTypes().get(0).getMovementCost();
                }
            }

        }
        return movementCost;
    }

    public int calculatingTheMovementCostForScout(ArrayList<Terrain> path) {
        int movementCost = 0;
        for (Terrain terrain : path) {

            movementCost += 1;

        }
        return movementCost;
    }

    public void changingUnitsParameters(User user) {
        for (Unit unit : user.getCivilization().getUnits()) {
            if (unit instanceof CombatUnit) {
                changingCombatUnitsParameters((CombatUnit) unit);
            } else {
                changingNonCombatUnitParameters((NonCombatUnit) unit);
            }
        }
    }

    public void changingCombatUnitsParameters(CombatUnit combatUnit) {
        if (combatUnit.getIsAsleep()) {

        } else if (combatUnit.getAlert()) {
            wakeUpFromAlert(combatUnit);

        } else if (combatUnit.getIsGarrisoned()) {

        } else if (combatUnit.getFortify()) {
            combatUnit.setCombatStrength(combatUnit.getCombatStrength() + 1);
        } else if (combatUnit.getFortifyUntilHeal()) {
            combatUnit.setHP(combatUnit.getHP() + 1);

        }
    }

    public void changingNonCombatUnitParameters(NonCombatUnit nonCombatUnit) {
        if (nonCombatUnit.getIsAsleep()) {

        }
    }

    public Technology getUnderResearchTechnology(User user) {
        for (Technology technology : user.getCivilization().getTechnologies()) {
            if (technology.getUnderResearch()) {
                return technology;
            }
        }
        return null;
    }

    public Terrain getTerrainByCoordinates(int x, int y) {
        return this.database.getMap().getTerrain()[x][y];
    }

    public void setTerrainsOfEachCivilization(User user) {

        ArrayList<Terrain> terrains = new ArrayList<>();
        ArrayList<Terrain> ownedTerrains = new ArrayList<>();

        for (Unit unit : user.getCivilization().getUnits()) {
            terrains.add(getTerrainByCoordinates(unit.getX(), unit.getY()));
        }

        for (City city : user.getCivilization().getCities()) {
            terrains.addAll(city.getMainTerrains());
            ownedTerrains.addAll(city.getMainTerrains());
        }

        user.getCivilization().setTerrains(deleteExcessTerrain(terrains));
        user.getCivilization().setOwnedTerrains(deleteExcessTerrain(ownedTerrains));
    }

    public void setCivilizations(ArrayList<User> users) {

        this.database.setCivilizationsName(new ArrayList<>(List.of("Incan", "Aztec", "Roman", "AncientGreek", "Chinese", "Maya", "AncientEgyptian", "IndusValley", "Mesopotamian", "Persian")));
        ArrayList<Integer> indices = setIndices(users);
        int i = 0;
        for (User user : users) {

            Civilization civilization = new Civilization(100, 100, this.database.getCivilizationsName().get(indices.get(i)));
            user.setCivilization(civilization);
            user.getCivilization().setBooleanSettlerBuy(true);
            createUnitForEachCivilization(user);
            setTerrainsOfEachCivilization(user);
            i++;
        }

        setInitialDiplomacy();
    }

    public ArrayList<Integer> setIndices(ArrayList<User> users) {
        Random rand = new Random();
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            int nextIndex = rand.nextInt(10);
            while (isContainInteger(indices, nextIndex)) {
                nextIndex = rand.nextInt(10);
            }

            indices.add(nextIndex);
        }

        return indices;

    }

    public boolean isContainInteger(ArrayList<Integer> indices, int random) {
        for (Integer integer : indices) {
            if (integer == random) {

                return true;
            }
        }
        return false;
    }


    public void createUnitForEachCivilization(User user) {
        ArrayList<Integer> unitsCoordinates = findingEmptyTiles();
        NonCombatUnit newSettler = new NonCombatUnit(unitsCoordinates.get(0), unitsCoordinates.get(1) + 1, 0, 0, 0, 0, false, false, UnitTypes.SETTLER, false);

        NonRangedCombatUnit newWarrior = new NonRangedCombatUnit(unitsCoordinates.get(0), unitsCoordinates.get(1), 0, 0, 0, 0, false, false, UnitTypes.WARRIOR, false, false, false, false, false);
        getMap().getTerrain()[unitsCoordinates.get(0)][unitsCoordinates.get(1)].setCombatUnit(newWarrior);
        getMap().getTerrain()[unitsCoordinates.get(0)][unitsCoordinates.get(1) + 1].setNonCombatUnit(newSettler);
//        user.getCivilization().addCity(new City(user.getCivilization(),user.getCivilization(),getTerrainByCoordinates(10,12),10,null,10,10));
//        user.getCivilization().addCity(new City(user.getCivilization(),user.getCivilization(),getTerrainByCoordinates(10,12),10,null,10,10));


        user.getCivilization().getUnits().add(newSettler);
        user.getCivilization().getUnits().add(newWarrior);

    }

    public ArrayList<Integer> findingEmptyTiles() {
        Random rand = new Random();
        ArrayList<Integer> coordinates = new ArrayList<>();
        int x = rand.nextInt(5, 25);
        int y = rand.nextInt(5, 13);
        while (!isTerrainEmpty(x, y)) {
            x = rand.nextInt(5, 25);
            y = rand.nextInt(5, 13);
        }
        coordinates.add(x);
        coordinates.add(y);
        return coordinates;

    }

    public boolean isTerrainEmpty(int x, int y) {

        return this.getMap().getTerrain()[x][y].getCombatUnit() == null && this.getMap().getTerrain()[x][y + 1].getNonCombatUnit() == null && !this.getMap().getTerrain()[x][y].isRuin() && !this.getMap().getTerrain()[x][y + 1].isRuin();
    }

    public void addGoldToUser(User user) {
        /*
        for (Terrain allTerrains : user.getCivilization().getOwnedTerrains()) {
            int gold = allTerrains.getCity().getGold();
            if (allTerrains.getTerrainTypes() != null) {
                gold += allTerrains.getTerrainTypes().getGold();
            }
            if (allTerrains.getTerrainFeatureTypes() != null) {
                for (TerrainFeatureTypes allTerrainFeature : allTerrains.getTerrainFeatureTypes()) {

                    gold += allTerrainFeature.getGold();
                }
            }

            if (allTerrains.getTerrainResource() != null && allTerrains.getBooleanResource()) {
                gold += allTerrains.getTerrainResource().getGold();
            }
            if (allTerrains.getTerrainImprovement() != null) {
                gold += allTerrains.getTerrainImprovement().getImprovementType().getGold();
            }

            allTerrains.getCity().setGold(gold);
        }

         */

        for (City city : user.getCivilization().getCities()) {
            int gold = city.getGold();
            user.getCivilization().increaseGold(gold);
        }
        int numberOfUnits = user.getCivilization().getUnits().size();
        if (user.getCivilization().getGold() >= 0) {
            user.getCivilization().increaseGold(-numberOfUnits * database.getTurn());
            for (City city : user.getCivilization().getCities()) {
                for (Building cityBuildings : city.getBuildings()) {
                    user.getCivilization().increaseGold(-cityBuildings.getBuildingType().getMeintenance() * database.getTurn());
                }
            }
        } else {
            user.getCivilization().setScience(user.getCivilization().getScience() - numberOfUnits * database.getTurn());
            for (City city : user.getCivilization().getCities()) {
                for (Building cityBuildings : city.getBuildings()) {
                    user.getCivilization().setScience(user.getCivilization().getScience() - cityBuildings.getBuildingType().getMeintenance() * database.getTurn());
                }
            }
        }


        // gold == 0?

    }

    public void consumptFood(User user) {
        for (City city : user.getCivilization().getCities()) {
            int numberOfcitizen = city.getCitizens().size();
            int firstFood = city.getFood();
            city.setFood(firstFood - 2 * numberOfcitizen);
        }
        /*
         * citizens add food when work
         * tara complete city
         *
         */

        ArrayList<City> allCitiesHaveSettler = new ArrayList<>();
        for (Terrain allTerrain : user.getCivilization().getOwnedTerrains()) {
            if (allTerrain.getNonCombatUnit().getUnitType() == UnitTypes.SETTLER) {
                allCitiesHaveSettler.add(allTerrain.getCity());
            }
        }

        int divide = 1;
        if (user.getCivilization().getHappiness() < 0) {
            divide = 3;
        }

        for (Terrain allTerrain : user.getCivilization().getOwnedTerrains()) {
            if (!allCitiesHaveSettler.contains(allTerrain.getCity())) {
                int food = 0;
                if (allTerrain.getTerrainTypes() != null) {
                    food += allTerrain.getTerrainTypes().getFood();
                }
                if (allTerrain.getTerrainFeatureTypes() != null) {
                    for (TerrainFeatureTypes allTerrainFeature : allTerrain.getTerrainFeatureTypes()) {

                        food += allTerrainFeature.getFood();
                    }
                }

                if (allTerrain.getTerrainResource() != null && allTerrain.getBooleanResource()) {
                    food += allTerrain.getTerrainResource().getFood();
                }
                if (allTerrain.getTerrainImprovement() != null) {
                    food += allTerrain.getTerrainImprovement().getImprovementType().getFood();
                }

                allTerrain.getCity().setFood(allTerrain.getCity().getFood() + food / divide);
            }

        }

        // roshd shahr

    }

    public void setHappinessUser(User user) {

        // without building
        // without technology

        ResourceTypes[] luxuryResource = {ResourceTypes.COTTON, ResourceTypes.DYES, ResourceTypes.FURS, ResourceTypes.GEMS, ResourceTypes.GEMS, ResourceTypes.GOLD, ResourceTypes.INCENSE, ResourceTypes.IVORY, ResourceTypes.MARBLE, ResourceTypes.SILK, ResourceTypes.SILVER, ResourceTypes.SUGAR};
        ArrayList<ResourceTypes> happinessLuxuryIncrease = new ArrayList<>();

        for (Terrain allTerrains : user.getCivilization().getOwnedTerrains()) {
            if (allTerrains.getTerrainResource() != null && allTerrains.getBooleanResource()) {
                for (ResourceTypes resourceTypes : luxuryResource) {
                    if (resourceTypes.equals(allTerrains.getTerrainResource().getResourceType())) {
                        if (!happinessLuxuryIncrease.contains(allTerrains.getTerrainResource().getResourceType())) {
                            happinessLuxuryIncrease.add(resourceTypes);
                        }
                    }
                }

            }
        }

        user.getCivilization().setHappiness(user.getCivilization().getHappiness() - 5 * user.getCivilization().getCities().size());

        user.getCivilization().setHappiness(user.getCivilization().getHappiness() + 4 * happinessLuxuryIncrease.size());

        if (user.getCivilization().getHappiness() < 0) {
            for (Terrain allTerrain : user.getCivilization().getOwnedTerrains()) {
                if (allTerrain.getCombatUnit() != null) {
                    allTerrain.getCombatUnit().setCombatStrength(allTerrain.getCombatUnit().getCombatStrength() - allTerrain.getCombatUnit().getCombatStrength() / 4);
                }
            }
            user.getCivilization().setBooleanSettlerBuy(false);
        } else {
            for (Terrain allTerrain : user.getCivilization().getOwnedTerrains()) {
                if (allTerrain.getCombatUnit() != null) {
                    allTerrain.getCombatUnit().setCombatStrength(allTerrain.getCombatUnit().getCombatStrength() + allTerrain.getCombatUnit().getCombatStrength() / 4);
                }
            }
            user.getCivilization().setBooleanSettlerBuy(true);
        }


    }

    public void setScience(User user) {
        user.getCivilization().setScience(user.getCivilization().getScience() + 3);
        for (City city : user.getCivilization().getCities()) {
            user.getCivilization().setScience(user.getCivilization().getScience() + city.getScience());
        }
    }

    public String choosingATechnologyToStudy(User user, TechnologyTypes technologyType) {
        for (TechnologyTypes technologyType2 : technologyType.getRequirements()) {
            if (!isContainTechnology(user, technologyType2)) {
                if (notificationHistory.get(user) != null) {
                    notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " you do not have required prerequisites" + "\n");
                } else {
                    notificationHistory.put(user, this.database.getTurn() + " you do not have required prerequisites" + "\n");
                }

                return "you do not have required prerequisites";
            } else if (isContainTechnology(user, technologyType2) && !getTechnologyByTechnologyType(user, technologyType2).getIsAvailable()) {
                if (notificationHistory.get(user) != null) {
                    notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " you do not have required prerequisites" + "\n");
                } else {
                    notificationHistory.put(user, this.database.getTurn() + " you do not have required prerequisites" + "\n");
                }
                return "you do not have required prerequisites";
            }
        }
        for (Technology technology : user.getCivilization().getTechnologies()) {
            technology.setUnderResearch(false);
        }
        if (isContainTechnology(user, technologyType)) {
            getTechnologyByTechnologyType(user, technologyType).setUnderResearch(true);
            if (notificationHistory.get(user) != null) {
                notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " Technology " + technologyType.name() + " is under research again" + "\n");
            } else {
                notificationHistory.put(user, this.database.getTurn() + " Technology " + technologyType.name() + " is under research again" + "\n");
            }

            return "Technology is under research again";
        } else {
            user.getCivilization().getTechnologies().add(new Technology(true, 0, technologyType, false));
        }
        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " Technology " + technologyType.name() + " is under research" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " Technology " + technologyType.name() + " is under research" + "\n");
        }

        return "Technology is under research";
    }

    public boolean isContainTechnology(User user, TechnologyTypes technologyType) {
        for (Technology technology : user.getCivilization().getTechnologies()) {
            if (technology.getTechnologyType() != null && technology.getTechnologyType().equals(technologyType) && technology.getIsAvailable()) {
                return true;
            }
        }
        return false;
    }

    public boolean isContainTechnologyType(User user, TechnologyTypes technologyType) {
        for (Technology technology : user.getCivilization().getTechnologies()) {
            if (technology.getTechnologyType() != null && technology.getTechnologyType().equals(technologyType)) {
                return true;
            }
        }
        return false;
    }

    public Technology getTechnologyByTechnologyType(User user, TechnologyTypes technologyType) {
        for (Technology technology : user.getCivilization().getTechnologies()) {
            if (technology.getTechnologyType().equals(technologyType)) {
                return technology;
            }
        }
        return null;
    }

    public String researchInfo(User user) {
        if (getUnderResearchTechnology(user) != null) {
            return getUnderResearchTechnology(user).toString();
        }
        return "there is no under research technology";

    }

    public ArrayList<Terrain> getNeighborTerrainsOfOneTerrain(Terrain terrain, Map map) {
        ArrayList<Terrain> neighbors = new ArrayList<>();
        Terrain[][] copy_map = map.getTerrain();
        int x_beginning = terrain.getX();
        int y_beginning = terrain.getY();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x_beginning + i < 0 || x_beginning + i >= map.getROW() || y_beginning + j < 0 || y_beginning + j >= map.getCOL() || y_beginning % 2 == 0 && ((i == 0 && j == 0) || (i == 1 && j == -1) || (i == 1 && j == 1)) || y_beginning % 2 == 1 && ((i == 0 && j == 0) || (i == -1 && j == 1) || (i == -1 && j == -1))) {

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

    public void increaseTurnInConstructingUnit(ArrayList<User> users) {
        for (User user : users) {
            for (City city : user.getCivilization().getCities()) {
                ArrayList<Unit> needToRemove = new ArrayList<>();
                ArrayList<Building> needToRemoveBuildings = new ArrayList<>();
                for (Unit unit : city.getConstructionWaitList()) {
                    if (unit.getPassedTurns() < unit.getUnitType().getTurn()) {
                        unit.setPassedTurns(unit.getPassedTurns() + 1);
                    } else {
                        unit.setPassedTurns(0);
                        user.getCivilization().getUnits().add(unit);
                        Terrain terrain = getTerrainByCoordinates(unit.getX(), unit.getY());
                        if (unit instanceof CombatUnit) {
                            terrain.setCombatUnit((CombatUnit) unit);
                        } else {
                            terrain.setNonCombatUnit((NonCombatUnit) unit);
                        }
                        needToRemove.add(unit);

                    }
                }
                for (Building building : city.getBuildingWaitlist()) {
                    if (building.getPassedTurns() < building.getBuildingType().getTurn()) {
                        building.setPassedTurns(building.getPassedTurns() + 1);
                    } else {
                        building.setPassedTurns(0);
                        city.getBuildings().add(building);
                        needToRemoveBuildings.add(building);

                    }
                }
                city.getConstructionWaitList().removeAll(needToRemove);
                city.getBuildingWaitlist().removeAll(needToRemoveBuildings);
            }
        }
    }

    public void setUnitsParametersAfterEachTurn(ArrayList<User> users) {
        increasingTurnInWorkersActions();
        increaseTurnInConstructingUnit(users);
        for (User user : users) {
            changingUnitsParameters(user);
        }


    }

    public String increaseTurnCheat(int amount) {
        this.database.setTurn(database.getTurn() + amount);

        return "number of turns increased";
    }

    public String increaseGoldCheat(User user, int amount) {
        user.getCivilization().setGold(user.getCivilization().getGold() + amount);
        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " user's gold increased" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " user's gold increased" + "\n");
        }

        return "user's gold increased";
    }


    public String increaseHappinessCheat(User user, int amount) {
        user.getCivilization().setHappiness(user.getCivilization().getHappiness() + amount);
        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " user's happiness increased" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " user's happiness increased" + "\n");
        }

        return "user's happiness increased";
    }

    public String increaseScienceCheat(User user, int amount) {
        user.getCivilization().setScience(user.getCivilization().getScience() + amount);
        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " user's science increased" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " user's science increased" + "\n");
        }

        return "user's science increased";
    }

    public String buyTechnologyCheat(User user, TechnologyTypes technologyType) {
        if (isContainTechnology(user, technologyType)) {
            return "you already have this technology";
        } else {
            user.getCivilization().getTechnologies().add(new Technology(false, 0, technologyType, true));
        }

        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " Technology was added illegally!" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " Technology was added illegally!" + "\n");
        }

        return "Technology was added illegally!";
    }

    public String cheatMoveCombatUnit(int x, int y) {
        CombatUnit combatUnit = getSelectedCombatUnit();
        if (combatUnit == null) {
            return "you have not selected a combat unit";
        }
        Terrain terrain = findingTheContainerTerrain(combatUnit);

        terrain.setCombatUnit(null);
        this.getMap().getTerrain()[x][y].setCombatUnit(combatUnit);

        combatUnit.setXAndY(x, y);

        if (combatUnit.getNextTerrain() != null) {
            combatUnit.getNextTerrain().clear();
        }

        combatUnit.setIsSelected(false);
        combatUnit.setIsFinished(true);

        return "Combat unit moved illegally!";

    }

    public String cheatMoveNonCombatUnit(int x, int y) {
        NonCombatUnit nonCombatUnit = getSelectedNonCombatUnit();
        if (nonCombatUnit == null) {
            return "you have non selected a nonCombat unit";
        }
        Terrain terrain = findingTheContainerTerrain(nonCombatUnit);

        terrain.setCombatUnit(null);
        this.getMap().getTerrain()[x][y].setNonCombatUnit(nonCombatUnit);

        nonCombatUnit.setXAndY(x, y);

        if (nonCombatUnit.getNextTerrain() != null) {
            nonCombatUnit.getNextTerrain().clear();
        }

        nonCombatUnit.setIsSelected(false);
        nonCombatUnit.setIsFinished(true);

        return "Combat unit moved illegally!";

    }

    public String buyCheatTile(User user, int x, int y) {
        if (user.getCivilization().getOwnedTerrains().contains(this.getMap().getTerrain()[x][y])) {
            return "you already own this tile";
        }
        user.getCivilization().getOwnedTerrains().add(this.getMap().getTerrain()[x][y]);
        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " you bought tile illegally!" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " you bought tile illegally!" + "\n");
        }
        return "you bought tile illegally!";

    }

    public String setCheatUnit(User user, String name, int x, int y) {

        Civilization civilization = user.getCivilization();
        Terrain terrain = this.getMap().getTerrain()[x][y];

        switch (name) {
            case "ARCHER":

                RangedCombatUnit newArcher = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.ARCHER, false, false, false, false, false, false);
                civilization.addUnit(newArcher);
                terrain.setCombatUnit(newArcher);

                break;
            case "CHARIOT_ARCHER":

                RangedCombatUnit newChariotArcher = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.CHARIOT_ARCHER, false, false, false, false, false, false);
                civilization.addUnit(newChariotArcher);
                terrain.setCombatUnit(newChariotArcher);

                break;
            case "SCOUT":

                NonRangedCombatUnit newScout = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.SCOUT, false, false, false, false, false);
                civilization.addUnit(newScout);
                terrain.setCombatUnit(newScout);

                break;
            case "SETTLER":

                NonCombatUnit newSettler = new NonCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.SETTLER, false);
                civilization.addUnit(newSettler);
                terrain.setNonCombatUnit(newSettler);

                break;
            case "SPEARMAN":

                NonRangedCombatUnit newSpearman = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.SPEARMAN, false, false, false, false, false);
                civilization.addUnit(newSpearman);
                terrain.setCombatUnit(newSpearman);

                break;
            case "WARRIOR":

                NonRangedCombatUnit newWarrior = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.WARRIOR, false, false, false, false, false);
                civilization.addUnit(newWarrior);
                terrain.setCombatUnit(newWarrior);

                break;
            case "WORKER":

                NonCombatUnit newWorker = new NonCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.WORKER, false);
                civilization.addUnit(newWorker);
                terrain.setNonCombatUnit(newWorker);

                break;
            case "CATAPULT":

                RangedCombatUnit newCatapult = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.CATAPULT, false, false, false, false, false, false);
                civilization.addUnit(newCatapult);
                terrain.setCombatUnit(newCatapult);

                break;
            case "HORSESMAN":

                NonRangedCombatUnit newHorsesman = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.HORSESMAN, false, false, false, false, false);
                civilization.addUnit(newHorsesman);
                terrain.setCombatUnit(newHorsesman);

                break;
            case "SWORDSMAN":

                NonRangedCombatUnit newSwordsman = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.SWORDSMAN, false, false, false, false, false);

                civilization.addUnit(newSwordsman);
                terrain.setCombatUnit(newSwordsman);

                break;
            case "CROSSBOWMAN":

                RangedCombatUnit newCrossbowman = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.CROSSBOWMAN, false, false, false, false, false, false);
                civilization.addUnit(newCrossbowman);
                terrain.setCombatUnit(newCrossbowman);

                break;
            case "KNIGHT":

                NonRangedCombatUnit newKnight = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.KNIGHT, false, false, false, false, false);
                civilization.addUnit(newKnight);
                terrain.setCombatUnit(newKnight);

                break;
            case "LONGSWORDSMAN":

                NonRangedCombatUnit newLong = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.LONGSWORDSMAN, false, false, false, false, false);

                civilization.addUnit(newLong);
                terrain.setCombatUnit(newLong);

                break;
            case "PIKEMAN":

                NonRangedCombatUnit newPikeman = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.PIKEMAN, false, false, false, false, false);
                civilization.addUnit(newPikeman);
                terrain.setCombatUnit(newPikeman);

                break;
            case "TREBUCHET":

                RangedCombatUnit newTrebuchet = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.TREBUCHET, false, false, false, false, false, false);
                civilization.addUnit(newTrebuchet);
                terrain.setCombatUnit(newTrebuchet);

                break;
            case "CANNON":

                RangedCombatUnit newCannon = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.CANNON, false, false, false, false, false, false);
                civilization.addUnit(newCannon);
                terrain.setCombatUnit(newCannon);

                break;
            case "CAVALRY":

                NonRangedCombatUnit newUnit = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.CAVALRY, false, false, false, false, false);
                civilization.addUnit(newUnit);
                terrain.setCombatUnit(newUnit);

                break;
            case "LANCER":

                NonRangedCombatUnit newLancer = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.LANCER, false, false, false, false, false);
                civilization.addUnit(newLancer);
                terrain.setCombatUnit(newLancer);

                break;
            case "MUSKETMAN":

                NonRangedCombatUnit newMusketman = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.MUSKETMAN, false, false, false, false, false);
                civilization.addUnit(newMusketman);
                terrain.setCombatUnit(newMusketman);

                break;
            case "RIFLEMAN":

                NonRangedCombatUnit newRifleman = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.RIFLEMAN, false, false, false, false, false);
                civilization.addUnit(newRifleman);
                terrain.setCombatUnit(newRifleman);

                break;
            case "ANTI_TANKGUN":

                NonRangedCombatUnit newAntiTank = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.ANTI_TANKGUN, false, false, false, false, false);
                civilization.addUnit(newAntiTank);
                terrain.setCombatUnit(newAntiTank);

                break;
            case "ARTILLERY":

                RangedCombatUnit newArtillery = new RangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.ARTILLERY, false, false, false, false, false, false);
                civilization.addUnit(newArtillery);
                terrain.setCombatUnit(newArtillery);

            case "INFANTRY":

                NonRangedCombatUnit newInfantry = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.INFANTRY, false, false, false, false, false);
                civilization.addUnit(newInfantry);
                terrain.setCombatUnit(newInfantry);

                break;
            case "PANZER":

                NonRangedCombatUnit newPanzer = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.PANZER, false, false, false, false, false);
                civilization.addUnit(newPanzer);
                terrain.setCombatUnit(newPanzer);

                break;
            case "TANK":

                NonRangedCombatUnit newTank = new NonRangedCombatUnit(terrain.getX(), terrain.getY(), 0, 0, 0, 0, false, false, UnitTypes.TANK, false, false, false, false, false);
                civilization.addUnit(newTank);
                terrain.setCombatUnit(newTank);

                break;
            default:
                return "invalid unit name";
        }
        return "unit was added illegally!";

    }

    public String setCheatImprovement(ImprovementTypes improvementType, int x, int y) {

        Improvement improvement = new Improvement(x, y, improvementType);
        improvement.setAvailable(true);
        getMap().getTerrain()[x][y].setTerrainImprovement(improvement);
        return "Improvement was added illegally!";
    }

    public String setCheatResource(ResourceTypes resourceType, int x, int y) {
        Resource resource = new Resource(resourceType);
        getMap().getTerrain()[x][y].setTerrainResource(resource);
        return "Resource was added illegally!";
    }

    public String setCheatTerrainFeatureType(TerrainFeatureTypes terrainFeatureTypes, int x, int y) {
        getMap().getTerrain()[x][y].setTerrainFeatureTypes(terrainFeatureTypes);
        return "Terrain feature was added illegally!";
    }

    public String setCheatTerrainType(TerrainTypes terrainTypes, int x, int y) {
        getMap().getTerrain()[x][y].setTerrainTypes(terrainTypes);
        return "Terrain was added illegally!";
    }

    public String deleteCheatImprovement(int x, int y) {
        getMap().getTerrain()[x][y].setTerrainImprovement(null);
        return "Improvement was deleted illegally!";
    }

    public String repairCheatImprovement(int x, int y) {
        if (getMap().getTerrain()[x][y].getTerrainImprovement() != null) {
            getMap().getTerrain()[x][y].getTerrainImprovement().setAvailable(true);
            getMap().getTerrain()[x][y].getTerrainImprovement().setPillaged(false);
            getMap().getTerrain()[x][y].getTerrainImprovement().setBeingRepaired(false);
            getMap().getTerrain()[x][y].getTerrainImprovement().setHasToBeDeleted(false);
            getMap().getTerrain()[x][y].getTerrainImprovement().setPassedTurns(0);
            return "Improvement was removed illegally!";
        }
        return "There is no improvement to delete!";
    }

    public void setTechnologyTypes(Civilization civilization) {
        ArrayList<Technology> technologies = civilization.getTechnologies();
        ArrayList<TechnologyTypes> technologyTypes = civilization.getTechnologyTypes();
        for (Technology technology : technologies) {
            technologyTypes.add(technology.getTechnologyType());

        }
        civilization.setTechnologyTypes(technologyTypes);
    }

    public String pillageImprovement(CombatUnit combatUnit, Terrain terrain) {
        if (terrain.getTerrainImprovement() == null) {
            return "There is no improvement in this tile";
        } else {
            terrain.getTerrainImprovement().setPillaged(true);
            combatUnit.getNextTerrain().clear();
            combatUnit.setIsFinished(true);
            combatUnit.setIsSelected(false);
            return "Improvement was pillaged";

        }
    }

    public ArrayList<Terrain> terrainsAtInputDistance(ArrayList<Terrain> terrains, int index, Map map) {
        if (index == 1) {
            return NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(terrains, map);

        } else {
            ArrayList<Terrain> neighbors = new ArrayList<>();
            ArrayList<Terrain> neighborsAtADistanceIndexMinusOne = terrainsAtInputDistance(terrains, index - 1, map);

            neighbors.addAll(neighborsAtADistanceIndexMinusOne);
            neighbors.addAll(NeighborsAtADistanceOfOneFromAnArraylistOfTerrains(neighborsAtADistanceIndexMinusOne, map));
            neighbors.removeAll(terrains);

            return deleteExcessTerrain(neighbors);

        }

    }

    public void wakeUpFromAlert(CombatUnit combatUnit) {
        ArrayList<Terrain> terrainsAtADistanceFour = terrainsAtInputDistance(new ArrayList<>() {
            {
                add(getTerrainByCoordinates(combatUnit.getX(), combatUnit.getY()));
            }

        }, 4, this.getMap());

        for (Terrain terrain : terrainsAtADistanceFour) {
            if (terrain.getCombatUnit() != null && (getContainerCivilization(combatUnit) != (getContainerCivilization(terrain.getCombatUnit())))) {
                combatUnit.setAlert(false);
                return;
            }
        }

    }

    public Civilization getContainerCivilization(Unit unit) {
        for (User user : this.database.getUsers()) {
            if (user.getCivilization().getUnits().contains(unit)) {
                return user.getCivilization();
            }
        }
        return null;
    }

    public Civilization getContainerCivilizationOfCity(City city) {
        for (User user : this.database.getUsers()) {
            if (user.getCivilization().getCities().contains(city)) {
                return user.getCivilization();
            }
        }
        return null;
    }

    public String demographicPanel() {
        StringBuilder output = new StringBuilder();

        for (User user : database.getUsers()) {
            Civilization civilization = user.getCivilization();
            int numberOfCitizens = 0;
            for (City city : civilization.getCities()) {
                numberOfCitizens += city.getCitizens().size();
            }
            output.append("Civilization ").append(civilization.getName()).append(" size : ").append(civilization.getOwnedTerrains().size()).append("\n");
            output.append("Civilization ").append(civilization.getName()).append(" Gold : ").append(civilization.getGold()).append("\n");
            output.append("Civilization ").append(civilization.getName()).append(" Science : ").append(civilization.getScience()).append("\n");
            output.append("Civilization ").append(civilization.getName()).append(" Happiness : ").append(civilization.getHappiness()).append("\n");
            output.append("Civilization ").append(civilization.getName()).append(" Number of Cities : ").append(civilization.getCities().size()).append("\n");
            output.append("Civilization ").append(civilization.getName()).append(" Number of Citizens : ").append(numberOfCitizens).append("\n\n\n");

        }
        return output.toString();
    }

    public String notificationHistory(User user) {
        if (notificationHistory.get(user) == null) {
            return "this user has no notification history";
        }
        return notificationHistory.get(user);
    }

    public String militaryOverview(User user) {
        StringBuilder unitsInformation = new StringBuilder();
        for (Unit unit : user.getCivilization().getUnits()) {
            unitsInformation.append(unit.toString()).append("\n");
        }
        return unitsInformation.toString();
    }

    public String unitsInfo(User user) {
        StringBuilder unitsInformation = new StringBuilder();
        for (Unit unit : user.getCivilization().getUnits()) {
            unitsInformation.append(unit.getX()).append(" ").append(unit.getY()).append(" ").append(unit.getUnitType().name()).append("\n");
        }
        return unitsInformation.toString();
    }


    public Unit getUnitByCoordinatesAndName(User user, String name, int x, int y) {
        for (Unit unit : user.getCivilization().getUnits()) {
            if (unit.getUnitType().name().equalsIgnoreCase(name) && unit.getX() == x && unit.getY() == y) {
                return unit;
            }
        }

        return null;
    }

    public String activateUnit(User user, String name, int x, int y) {
        Unit unit = getUnitByCoordinatesAndName(user, name, x, y);
        if (unit == null) {
            return "There is no unit with these characteristics";
        } else {
            setAllParametersFalse(unit);
            return "Unit was activated";
        }
    }

    public String economicOverview(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        for (City city : user.getCivilization().getCities()) {
            stringBuilder.append("City size ").append(city.getMainTerrains().size()).append("\n");
            stringBuilder.append("Population ").append(city.getCitizens().size()).append("\n");
            stringBuilder.append("HP ").append(city.getHP()).append("\n");
            stringBuilder.append("Gold ").append(city.getGold()).append("\n");
            stringBuilder.append("Science ").append(city.getScience()).append("\n");
            stringBuilder.append("Food Storage ").append(city.getFood()).append("\n");
            stringBuilder.append("Production ").append(city.getProduction()).append("\n");
            if (!city.getConstructionWaitList().isEmpty()) {
                stringBuilder.append(city.getConstructionWaitList().get(0).getUnitType().name()).append(" will be constructed in ").append(city.getConstructionWaitList().get(0).getUnitType().getTurn() - city.getConstructionWaitList().get(0).getPassedTurns()).append(" Turn").append("\n");
            } else {
                stringBuilder.append("You are not constructing any unit in this city").append("\n");
            }


        }
        return stringBuilder.toString();
    }

    public String cityPanel(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        for (City city : user.getCivilization().getCities()) {
            stringBuilder.append("X of Central Terrain: ").append(city.getCentralTerrain().getX()).append("\n");
            stringBuilder.append("Y of Central Terrain: ").append(city.getCentralTerrain().getY()).append("\n\n\n");

        }
        return stringBuilder.toString();
    }

    public City getCityByCoordinates(int x, int y, User user) {
        for (City city : user.getCivilization().getCities()) {
            if (x == city.getCentralTerrain().getX() && y == city.getCentralTerrain().getY()) {
                return city;
            }
        }
        return null;
    }


    public void choosingATechnologyToStudyForGraphic(User user, TechnologyTypes technologyType) {

        for (Technology technology : user.getCivilization().getTechnologies()) {
            technology.setUnderResearch(false);
        }
        if (isContainTechnologyType(user, getFirstRequiredTechnology(user, technologyType)) && !getTechnologyByTechnologyType(user, getFirstRequiredTechnology(user, technologyType)).getIsAvailable()) {
            getTechnologyByTechnologyType(user, getFirstRequiredTechnology(user, technologyType)).setUnderResearch(true);
            // System.out.println("Technology is under research again " + getFirstRequiredTechnology(user,technologyType).name());
        } else if (!isContainTechnologyType(user, getFirstRequiredTechnology(user, technologyType))) {
            user.getCivilization().getTechnologies().add(new Technology(true, 0, getFirstRequiredTechnology(user, technologyType), false));
        }

        //    System.out.println("Technology is under research " + getFirstRequiredTechnology(user,technologyType).name());
    }

    public void getNeededTechnologies(HashMap<TechnologyTypes, Integer> requiredTechnologies, int depth, User user, TechnologyTypes technologyTypes) {
        for (TechnologyTypes technologyTypes1 : technologyTypes.getRequirements()) {
            if (!isContainTechnology(user, technologyTypes1)) {
                requiredTechnologies.put(technologyTypes1, depth);
                getNeededTechnologies(requiredTechnologies, depth + 1, user, technologyTypes1);
            }
        }

    }

    public TechnologyTypes getFirstRequiredTechnology(User user, TechnologyTypes technologyTypes) {

        HashMap<TechnologyTypes, Integer> neededTechs = new HashMap<>();
        getNeededTechnologies(neededTechs, 1, user, technologyTypes);
        if (neededTechs.isEmpty()) {
            return technologyTypes;
        }
        List<Integer> list = new ArrayList<>(neededTechs.values());
        list.sort(naturalOrder());
        int max = list.get(list.size() - 1);
        List<TechnologyTypes> techs = neededTechs.entrySet().stream().filter(e -> e.getValue() == max).map(e -> e.getKey()).toList();
        return techs.get(0);

    }


    public ArrayList<TechnologyTypes> unlockableTechnologies(User user) {
        ArrayList<TechnologyTypes> technologyTypes = new ArrayList<>();
        for (TechnologyTypes technologyTypes1 : TechnologyTypes.values()) {
            if (haveAllPrerequisiteTechnologies(user, technologyTypes1) && !isContainTechnology(user, technologyTypes1)) {
                technologyTypes.add(technologyTypes1);
            }

        }
        return technologyTypes;
    }

    public boolean haveAllPrerequisiteTechnologies(User user, TechnologyTypes technologyTypes) {
        for (TechnologyTypes technologyTypes1 : technologyTypes.getRequirements()) {
            if (!isContainTechnology(user, technologyTypes1)) {
                return false;
            }
        }
        return true;
    }

    public User getUserByCivilization(Civilization civilization) {
        for (User user : database.getUsers()) {
            if (user.getCivilization().equals(civilization)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<Object> removeExcessObjects(ArrayList<Object> objects) {
        ArrayList<Object> finalArrayList = new ArrayList<>();
        for (Object object : objects) {
            boolean isNew = true;
            for (Object object1 : finalArrayList) {
                if (object.equals(object1)) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                finalArrayList.add(object);
            }
        }

        return finalArrayList;
    }

    public TechnologyTypes getTechnologyTypeByName(String name) {
        for (TechnologyTypes technologyTypes : TechnologyTypes.values()) {
            if (technologyTypes.name().equalsIgnoreCase(name)) {
                return technologyTypes;
            }
        }
        return null;
    }

    public ArrayList<TechnologyTypes> unlockTechnologies(TechnologyTypes technologyTypes) {
        ArrayList<TechnologyTypes> unlockTechnologies = new ArrayList<>();
        for (TechnologyTypes technologyTypes1 : TechnologyTypes.values()) {
            if (technologyTypes1.getRequirements().contains(technologyTypes)) {
                unlockTechnologies.add(technologyTypes1);
            }
        }
        return unlockTechnologies;
    }

    public TechnologyTypes lastUnlockedTechnology() {
        for (int i = getDatabase().getActiveUser().getCivilization().getTechnologies().size() - 1; i >= 0; i--) {
            if (getDatabase().getActiveUser().getCivilization().getTechnologies().get(i).getIsAvailable()) {
                return getDatabase().getActiveUser().getCivilization().getTechnologies().get(i).getTechnologyType();
            }
        }
        return null;
    }

    public String buildingAnImprovement(User user, ImprovementTypes improvementType) {
        NonCombatUnit nonCombatUnit = getSelectedNonCombatUnit();
        if (nonCombatUnit != null) {
            if (nonCombatUnit.getUnitType().equals(UnitTypes.WORKER)) {
                Terrain workersTerrain = getTerrainByCoordinates(nonCombatUnit.getX(), nonCombatUnit.getY());
                if (buildingAnImprovementInATerrain(user, improvementType, workersTerrain).equals("improvement was built successfully")) {
                    nonCombatUnit.setIsSelected(false);
                    nonCombatUnit.setIsFinished(true);
                }
                return buildingAnImprovementInATerrain(user, improvementType, workersTerrain);

            } else {
                return "you have to select a worker group first";
            }
        } else {
            return "you have to select a worker group first";
        }
    }

    public String buildingAnImprovementInATerrain(User user, ImprovementTypes improvementType, Terrain workersTerrain) {
        if (!user.getCivilization().getOwnedTerrains().contains(workersTerrain)) {
            return "you are not in your owned tiles";
        }

        if (workersTerrain.getTerrainImprovement() != null) {
            return "you have created another improvement in this terrain";
        } else {
            if (!isContainTechnology(user, improvementType.getRequiredTechnology())) {
                return "you lack prerequisite technologies";
            } else if (!improvementType.getCanBeBuiltON().contains(workersTerrain.getTerrainTypes()) && !improvementType.getCanBeBuiltON().contains(workersTerrain.getTerrainFeatureTypes())) {
                return "you can not create this improvement in this type of terrain";
            } else {
                Improvement improvement = new Improvement(workersTerrain.getX(), workersTerrain.getY(), improvementType);
                workersTerrain.setTerrainImprovement(improvement);
            }

        }
        if (notificationHistory.get(user) != null) {
            notificationHistory.put(user, notificationHistory.get(user) + this.database.getTurn() + " improvement will be built successfully" + "\n");
        } else {
            notificationHistory.put(user, this.database.getTurn() + " improvement will be built successfully" + "\n");
        }

        return "improvement will be built successfully";
    }

    public String deleteFeatures(String hasToBeDeleted) {
        NonCombatUnit nonCombatUnit = getSelectedNonCombatUnit();
        if (nonCombatUnit != null) {
            if (nonCombatUnit.getUnitType().equals(UnitTypes.WORKER)) {
                Terrain workersTerrain = getTerrainByCoordinates(nonCombatUnit.getX(), nonCombatUnit.getY());
                if (hasToBeDeleted.equals("ROUTE")) {
                    if (workersTerrain.getTerrainImprovement() == null) {
                        return "you have no road or railroad in this tile";
                    } else if (!workersTerrain.getTerrainImprovement().getImprovementType().equals(ImprovementTypes.ROAD) && !workersTerrain.getTerrainImprovement().getImprovementType().equals(ImprovementTypes.RAILROAD)) {
                        return "you have no road or railroad in this tile";
                    } else {
                        workersTerrain.getTerrainImprovement().setHasToBeDeleted(true);
                        nonCombatUnit.setIsSelected(false);
                        nonCombatUnit.setIsFinished(true);
                        return "route will be deleted";
                    }

                } else if (hasToBeDeleted.equals("JUNGLE") || hasToBeDeleted.equals("FOREST") || hasToBeDeleted.equals("MARSH")) {
                    if (workersTerrain.getTerrainFeatureTypes().get(0) == null) {
                        return "you have no Jungle or Forest or Marsh in this tile";
                    } else if (!workersTerrain.getTerrainFeatureTypes().get(0).equals(TerrainFeatureTypes.FOREST) && !workersTerrain.getTerrainFeatureTypes().get(0).equals(TerrainFeatureTypes.JUNGLE) && !workersTerrain.getTerrainFeatureTypes().get(0).equals(TerrainFeatureTypes.MARSH)) {
                        return "you have no Jungle or Forest or Marsh in this tile";
                    } else {
                        workersTerrain.setHasToBeDeleted(true);
                        nonCombatUnit.setIsSelected(false);
                        nonCombatUnit.setIsFinished(true);

                        return "feature will be deleted";

                    }

                } else {
                    return "There is nothing to delete";
                }

            } else {
                return "you have to select a settler group first";
            }
        } else {
            return "you have to select a settler group first";
        }

    }

    public String repairImprovement() {
        NonCombatUnit nonCombatUnit = getSelectedNonCombatUnit();
        if (nonCombatUnit != null) {
            if (nonCombatUnit.getUnitType().equals(UnitTypes.WORKER)) {
                Terrain workersTerrain = getTerrainByCoordinates(nonCombatUnit.getX(), nonCombatUnit.getY());
                if (workersTerrain.getTerrainImprovement() == null) {
                    return "you have no improvement in this tile";
                } else if (workersTerrain.getTerrainImprovement().isBeingRepaired()) {
                    return "your workers are repairing this improvement";
                } else if (workersTerrain.getTerrainImprovement().isAvailable()) {
                    return "there is no problem with this improvement";
                } else if (workersTerrain.getTerrainImprovement().isPillaged()) {
                    workersTerrain.getTerrainImprovement().setBeingRepaired(true);
                    workersTerrain.getTerrainImprovement().setPillaged(false);
                    return "improvement will be repaired";
                } else {
                    return "improvement is not available yet";
                }

            } else {
                return "you have to select a worker group first";
            }
        } else {
            return "you have to select a worker group first";
        }
    }

    public void increasingTurnInWorkersActions() {
        for (int i = 0; i < getMap().getROW(); i++) {
            for (int j = 0; j < getMap().getCOL(); j++) {
                Terrain terrain = getMap().getTerrain()[i][j];
                if (terrain.getTerrainImprovement() != null) {
                    if (terrain.getTerrainImprovement().isBeingRepaired()) {

                        if (terrain.getTerrainImprovement().getPassedTurns() < terrain.getTerrainImprovement().getImprovementType().getTurn()) {
                            int passedTurns = terrain.getTerrainImprovement().getPassedTurns() + 1;
                            terrain.getTerrainImprovement().setPassedTurns(passedTurns);
                        } else {
                            terrain.getTerrainImprovement().setAvailable(true);
                            terrain.getTerrainImprovement().setBeingRepaired(false);
                            terrain.getTerrainImprovement().setPassedTurns(0);

                        }

                    } else if (terrain.getTerrainImprovement().isHasToBeDeleted()) {

                        if (terrain.getTerrainImprovement().getPassedTurns() < terrain.getTerrainImprovement().getImprovementType().getTurn()) {
                            int passedTurns = terrain.getTerrainImprovement().getPassedTurns() + 1;
                            terrain.getTerrainImprovement().setPassedTurns(passedTurns);
                        } else {
                            terrain.setTerrainImprovement(null);

                        }
                    } else if (!terrain.getTerrainImprovement().isAvailable() && !terrain.getTerrainImprovement().isPillaged()) {

                        if (terrain.getTerrainImprovement().getPassedTurns() < terrain.getTerrainImprovement().getImprovementType().getTurn()) {
                            int passedTurns = terrain.getTerrainImprovement().getPassedTurns() + 1;
                            terrain.getTerrainImprovement().setPassedTurns(passedTurns);
                        } else {
                            terrain.getTerrainImprovement().setAvailable(true);
                            terrain.getTerrainImprovement().setPassedTurns(0);
                        }
                    }
                } else if (!terrain.getTerrainFeatureTypes().isEmpty()) {
                    if (terrain.isHasToBeDeleted()) {
                        if (terrain.getPassedTurns() < 6) {
                            int turn = terrain.getPassedTurns() + 1;
                            terrain.setPassedTurns(turn);
                        } else {
                            terrain.getTerrainFeatureTypes().clear();
                        }
                    }

                }
            }
        }
    }


    public ArrayList<ImprovementTypes> improvementsThatCanBeBuiltInThisTerrain() {
        NonCombatUnit workers = getSelectedNonCombatUnit();
        ArrayList<ImprovementTypes> improvementTypesList = new ArrayList<>();
        if (workers.getUnitType().equals(UnitTypes.WORKER)) {
            Terrain workersTerrain = getTerrainByCoordinates(workers.getX(), workers.getY());

            if (DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getOwnedTerrains().contains(workersTerrain)) {
                for (ImprovementTypes improvementTypes : ImprovementTypes.values()) {
                    if (workersTerrain.getTerrainImprovement() == null && isContainTechnology(DatabaseController.getInstance().getDatabase().getActiveUser(), improvementTypes.getRequiredTechnology()) && (improvementTypes.getCanBeBuiltON().contains(workersTerrain.getTerrainTypes()) || improvementTypes.getCanBeBuiltON().contains(workersTerrain.getTerrainFeatureTypes()))) {
                        improvementTypesList.add(improvementTypes);
                    }
                    if (workersTerrain.getTerrainImprovement() != null && workersTerrain.getTerrainImprovement().getImprovementType().equals(improvementTypes) && !workersTerrain.getTerrainImprovement().isBeingWorkedOn()) {
                        improvementTypesList.add(improvementTypes);
                    }

                }
            }

        }

        return improvementTypesList;


    }

    public ImprovementTypes routsThatCanBeDeletedInThisTerrain() {
        NonCombatUnit workers = getSelectedNonCombatUnit();
        if (workers.getUnitType().equals(UnitTypes.WORKER)) {
            Terrain workersTerrain = getTerrainByCoordinates(workers.getX(), workers.getY());
            if (workersTerrain.getTerrainImprovement() != null && workersTerrain.getTerrainImprovement().isAvailable() && (workersTerrain.getTerrainImprovement().getImprovementType().equals(ImprovementTypes.ROAD) || workersTerrain.getTerrainImprovement().getImprovementType().equals(ImprovementTypes.RAILROAD))) {
                return workersTerrain.getTerrainImprovement().getImprovementType();
            }
        }

        return null;

    }

    public TerrainFeatureTypes featuresThatCanBeDeletedInThisTerrain() {
        NonCombatUnit workers = getSelectedNonCombatUnit();
        if (workers.getUnitType().equals(UnitTypes.WORKER)) {
            Terrain workersTerrain = getTerrainByCoordinates(workers.getX(), workers.getY());
            if (!workersTerrain.getTerrainFeatureTypes().isEmpty() && workersTerrain.getTerrainFeatureTypes().get(0) != null && (workersTerrain.getTerrainFeatureTypes().get(0).equals(TerrainFeatureTypes.FOREST) || workersTerrain.getTerrainFeatureTypes().get(0).equals(TerrainFeatureTypes.JUNGLE) || workersTerrain.getTerrainFeatureTypes().get(0).equals(TerrainFeatureTypes.MARSH))) {
                return workersTerrain.getTerrainFeatureTypes().get(0);
            }
        }

        return null;

    }

    public ImprovementTypes improvementsThatCanBeRepairedInThisTerrain() {
        NonCombatUnit workers = getSelectedNonCombatUnit();
        if (workers.getUnitType().equals(UnitTypes.WORKER)) {
            Terrain workersTerrain = getTerrainByCoordinates(workers.getX(), workers.getY());
            if (workersTerrain.getTerrainImprovement() != null && !workersTerrain.getTerrainImprovement().isBeingRepaired() && !workersTerrain.getTerrainImprovement().isAvailable() && workersTerrain.getTerrainImprovement().isPillaged()) {
                return workersTerrain.getTerrainImprovement().getImprovementType();
            }
        }

        return null;

    }

    public User getNextTurnUser() {
        int i = DatabaseController.getInstance().getDatabase().getUsers().indexOf(DatabaseController.getInstance().getDatabase().getActiveUser());
        if (i == DatabaseController.getInstance().getDatabase().getUsers().size() - 1) {
            return DatabaseController.getInstance().getDatabase().getUsers().get(0);
        } else {
            return DatabaseController.getInstance().getDatabase().getUsers().get(i + 1);
        }
    }

    public Civilization theWinnerCivilization() {
        int count = 0;
        for (User user : database.getUsers()) {
            if (user.getCivilization().getCurrentCapital() != null) {
                if (user.getCivilization().getCurrentCapital().equals(user.getCivilization().getFirstCapital())) {
                    count++;
                }
            }

        }
        if (count == 1) {
            for (User user : database.getUsers()) {
                if (user.getCivilization().getCurrentCapital() != null) {
                    if (user.getCivilization().getCurrentCapital().equals(user.getCivilization().getFirstCapital())) {
                        return user.getCivilization();
                    }
                }

            }
        }
        return null;
    }

    public Civilization theWinnerAfterYear2050() {
        int max = -1;
        for (User user : database.getUsers()) {
            if (user.getCivilization().getScore() > max) {
                max = user.getCivilization().getScore();
            }
        }
        for (User user : database.getUsers()) {
            if (max == user.getCivilization().getScore()) {
                return user.getCivilization();
            }
        }
        return null;
    }

    public int calculatingScoreForEachCivilizationAfterEachRound(Civilization civilization) {
        if (civilization.isHasEverHadCity() && civilization.getCities().size() == 0) {
            return 0;
        }
        return civilization.getTerrains().size() * 50 + civilization.getTechnologies().size() * 500 + civilization.getCities().size() * 150;

    }

    public void increasingYearPerTurnInStandardMode() {
        if (database.getYear() >= -4000 && database.getYear() < 1000) {
            database.setYear(database.getYear() + 40);
        } else if (database.getYear() >= -1000 && database.getYear() < 500) {
            database.setYear(database.getYear() + 25);
        } else if (database.getYear() >= 500 && database.getYear() < 1000) {
            database.setYear(database.getYear() + 20);
        } else if (database.getYear() >= 1000 && database.getYear() < 1500) {
            database.setYear(database.getYear() + 10);
        } else if (database.getYear() >= 1500 && database.getYear() < 1800) {
            database.setYear(database.getYear() + 5);
        } else if (database.getYear() >= 1800 && database.getYear() < 1900) {
            database.setYear(database.getYear() + 2);
        } else if (database.getYear() >= 1900 && database.getYear() < 2020) {
            database.setYear(database.getYear() + 1);
        } else if (database.getYear() >= 2020 && database.getYear() < 2050) {
            database.setYear(database.getYear() + 0.5);
        }

    }

    public void increasingYearPerTurnInQuickMode() {
        if (database.getYear() >= -4000 && database.getYear() < 1000) {
            database.setYear(database.getYear() + 60);
        } else if (database.getYear() >= -1000 && database.getYear() < 200) {
            database.setYear(database.getYear() + 40);
        } else if (database.getYear() >= 200 && database.getYear() < 800) {
            database.setYear(database.getYear() + 30);
        } else if (database.getYear() >= 800 && database.getYear() < 1400) {
            database.setYear(database.getYear() + 20);
        } else if (database.getYear() >= 1400 && database.getYear() < 1650) {
            database.setYear(database.getYear() + 10);
        } else if (database.getYear() >= 1650 && database.getYear() < 1850) {
            database.setYear(database.getYear() + 5);
        } else if (database.getYear() >= 1850 && database.getYear() < 1980) {
            database.setYear(database.getYear() + 2);
        } else if (database.getYear() >= 1980 && database.getYear() < 2050) {
            database.setYear(database.getYear() + 1);
        }

    }

    public void setStatusOfEachCivilizationWithOthersAfterEachRound() {
        for (User user : database.getUsers()) {
            HashMap<Civilization, Boolean> statusOfCivilization = new HashMap<>();
            for (User user1 : database.getUsers()) {
                if (user.getCivilization().getStatusWithOtherCivilizations().containsKey(user1.getCivilization())) {
                    statusOfCivilization.put(user1.getCivilization(), user.getCivilization().getStatusWithOtherCivilizations().get(user1.getCivilization()));
                }
            }
            user.getCivilization().setStatusWithOtherCivilizations(statusOfCivilization);
        }
    }

    public void setInitialDiplomacy() {
        for (User user1 : DatabaseController.getInstance().getDatabase().getUsers()) {
            for (User user : DatabaseController.getInstance().getDatabase().getUsers()) {
                if (!user.equals(DatabaseController.getInstance().getUserByCivilization(user1.getCivilization()))) {
                    user1.getCivilization().getStatusWithOtherCivilizations().put(user.getCivilization(), false);
                }
            }
        }

    }

    public Civilization getCivilizationByName(String name) {
        for (User user : database.getUsers()) {
            if (user.getCivilization().getName().equalsIgnoreCase(name)) {
                return user.getCivilization();
            }
        }
        return null;
    }

    public Pair goToTheFirstCoordinates(Civilization civilization) {
        int x = civilization.getUnits().get(0).getX();
        int y = civilization.getUnits().get(0).getY();
        if (x % 2 != 0) {
            x--;
        }
        if (y % 2 != 0) {
            y--;
        }
        return new Pair(x, y);
    }


}