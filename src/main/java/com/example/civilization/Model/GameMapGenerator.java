package com.example.civilization.Model;

import java.util.ArrayList;

public class GameMapGenerator {

    private int Iteration;
    private int ROW;
    private int COL;
    private Terrain[][] Terrains;
    private ArrayList<River> rivers = new ArrayList<River>();
    private String[][] Printmap;

    public GameMapGenerator(Terrain Terrains[][], ArrayList<River> rivers,int ROW,int COL,int Iteration) {
        this.Terrains = Terrains;
        this.rivers = rivers;
        this.ROW = ROW;
        this.COL = COL;
        this.Iteration = Iteration;
        this.Printmap =  new String[ROW][Iteration];
    }

    public River hasRiver(Terrain TerrainFirst, Terrain TerrainSecond) {
        for (River river : this.rivers) {
            if (river.getFirstTerrain() == TerrainFirst && river.getSecondTerrain() == TerrainSecond) {
                return river;
            } else if (river.getSecondTerrain() == TerrainFirst && river.getFirstTerrain() == TerrainSecond) {
                return river;
            }
        }
        return null;
    }

    public River hasRiver(Terrain TerrainFirst) {
        for (River river : this.rivers) {
            if (river.getFirstTerrain() == TerrainFirst) {
                return river;
            } else if (river.getSecondTerrain() == TerrainFirst) {
                return river;
            }
        }
        return null;
    }

    /// ADD SPACE FOR FIRST IN ROWS
    public void addSpace(int row, int col, int count) {
        for (int i = 0; i < count; i++) {
            Printmap[row][col] += " ";
        }
    }

    public void addUnderline(int row, int col, int count) {
        for (int i = 0; i < count; i++) {
            Printmap[row][col] += "_";
        }
    }

    public int returnHowManySpaceInLeft(int HowManySpace) {
        return HowManySpace / 2;
    }

    public int returnHowManySpaceInRight(int HowManySpace) {
        int HowManySpaceRight = 0;
        if (HowManySpace % 2 == 0) {
            HowManySpaceRight = HowManySpace / 2;
        } else {
            HowManySpaceRight = HowManySpace / 2 + 1;
        }
        return HowManySpaceRight;
    }

    public Revealed getRevealedFromArray(User user, int i, int l) {
        for (int count = Terrains[i][l].getReveals().size() - 1; count >= 0; count--) {
            if (Terrains[i][l].getReveals().get(count).getUser() == user) {
                return Terrains[i][l].getReveals().get(count);
            }
        }
        return null;
    }

    /// BETWEENT TWO TAIL
    private void betweetTwoTailFirstHalf(int i, int j, int l) {

        River river;
        if (i > 0 && l < COL - 1  && !Terrains[i][l].getType().equals("fog of war") && !Terrains[i - 1][l + 1].getType().equals("fog of war") &&(river = hasRiver(Terrains[i][l], Terrains[i - 1][l + 1])) != null ) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "\\";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "\\";
        }

    }

    private void betweenTwoTailSecondHalf(int i, int j, int l) {
        River river;
        if (l < COL - 1 &&  !Terrains[i][l].getType().equals("fog of war") && !Terrains[i][l + 1].getType().equals("fog of war") &&(river = hasRiver(Terrains[i][l], Terrains[i][l + 1])) != null ) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "/";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "/";
        }
    }






    // ALGHORITMS
    private void CivilizationAlghoritm(int iTerrain, int i, int j, int l, Database database) {
        String Civilization = "";
        int HowManySpaceLeft = 0;
        int HowManySpaceRight = 0;
        if (Terrains[iTerrain][l].getType().equals("revealed")) {
            Civilization = "REV";
            if(Terrains[iTerrain][l].getCity() != null){
                Civilization += ".C";
            }
            int HowManySpace = 5 - Civilization.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += Color.MAGENTA;
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            Printmap[i][j] += Civilization;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else if (Terrains[iTerrain][l].getType().equals("visible")) {
            if (database.getCivilizationUser(Terrains[iTerrain][l]) != null) {
                Civilization += database.getCivilizationUser(Terrains[iTerrain][l]).getCivilization().getName();
            }
            if(Terrains[iTerrain][l].getCity() != null){
                Civilization += ".C";
            }
            int HowManySpace = 5 - Civilization.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += Color.MAGENTA;
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            Printmap[i][j] += Civilization;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;

        } else {
            addSpace(i, j, 5);
        }
    }

    private void TerrainFeaturesAlghoritm(int iTerrain, int i, int j, int l, User user) {
        String TerrainFeatureType = "";
        int HowManySpaceLeft = 0;
        int HowManySpaceRight = 0;
        if (Terrains[iTerrain][l].getType().equals("revealed")) {
            if (getRevealedFromArray(user, iTerrain, l).getTerrainFeatureTypes() != null && getRevealedFromArray(user, iTerrain, l).getTerrainFeatureTypes().size() > 0) {
                TerrainFeatureType += getRevealedFromArray(user, iTerrain, l).getTerrainFeatureTypes().get(0)
                        .getShowFeatures();
            }
            int HowManySpace = 9 - TerrainFeatureType.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += TerrainFeatureType;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else if (Terrains[iTerrain][l].getType().equals("visible")) {
            if (Terrains[iTerrain][l].getTerrainFeatureTypes() != null
                    && Terrains[iTerrain][l].getTerrainFeatureTypes().size() > 0) {
                TerrainFeatureType += Terrains[iTerrain][l].getTerrainFeatureTypes().get(0).getShowFeatures();
            }
            int HowManySpace = 9 - TerrainFeatureType.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += TerrainFeatureType;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else {
            addSpace(i, j, 9);
        }
    }

    private void XandYAlghoritm(int iTerrain, int i, int j, int l) {
        String XcenterYcenter = "";
        int HowManySpaceLeft = 0;
        int HowManySpaceRight = 0;
        if (Terrains[iTerrain][l].getType().equals("revealed")) {
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            XcenterYcenter += Terrains[iTerrain][l].getX() + "," + Terrains[iTerrain][l].getY();
            int HowManySpace = 7 - XcenterYcenter.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += XcenterYcenter;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else if (Terrains[iTerrain][l].getType().equals("visible")) {
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            XcenterYcenter += Terrains[iTerrain][l].getX() + "," + Terrains[iTerrain][l].getY();
            int HowManySpace = 7 - XcenterYcenter.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += XcenterYcenter;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else {
            addSpace(i, j, 7);
        }
    }

    private void twocombatAlghoritm(int iTerrain, int i, int j, int l, User user) {
        int HowManySpaceLeft = 0;
        int HowManySpaceRight = 0;
        String AllUnit = "";
        if (Terrains[iTerrain][l].getType().equals("revealed")) {
            if (getRevealedFromArray(user, iTerrain, l).getNonCombatUnit() != null) {
                AllUnit += getRevealedFromArray(user, iTerrain, l).getNonCombatUnit().getUnitType().getShowMap();
            }
            AllUnit += " ";
            if (getRevealedFromArray(user, iTerrain, l).getCombatUnit() != null) {
                AllUnit += getRevealedFromArray(user, iTerrain, l).getCombatUnit().getUnitType().getShowMap();
            }
            int HowManySpace = 9 - AllUnit.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            addSpace(i, j, HowManySpaceLeft);
            int index = AllUnit.indexOf(" ");
            Printmap[i][j] += Color.RED;
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            Printmap[i][j] += AllUnit.substring(0, index);
            Printmap[i][j] += " ";
            Printmap[i][j] += Color.RED;
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            Printmap[i][j] += AllUnit.substring(index + 1);
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else if (Terrains[iTerrain][l].getType().equals("visible")) {
            if (Terrains[iTerrain][l].getNonCombatUnit() != null) {
                AllUnit += Terrains[iTerrain][l].getNonCombatUnit().getUnitType().getShowMap();
            }
            AllUnit += " ";
            if (Terrains[i][l].getCombatUnit() != null) {
                AllUnit += Terrains[iTerrain][l].getCombatUnit().getUnitType().getShowMap();
            }
            int HowManySpace = 9 - AllUnit.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            addSpace(i, j, HowManySpaceLeft);
            int index = AllUnit.indexOf(" ");
            Printmap[i][j] += Color.BLUE;
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            Printmap[i][j] += AllUnit.substring(0, index);
            Printmap[i][j] += " ";
            Printmap[i][j] += Color.MAGENTA;
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            Printmap[i][j] += AllUnit.substring(index + 1);
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else {
            addSpace(i, j, 9);
        }
    }

    private void ResourceAlghoritm(int iTerrain, int i, int j, int l, User user) {
        String Resource = "";
        int HowManySpaceLeft = 0;
        int HowManySpaceRight = 0;
        if (Terrains[iTerrain][l].getType().equals("revealed")) {
            if (getRevealedFromArray(user, iTerrain, l).getTerrainResource() != null && getRevealedFromArray(user, iTerrain, l).getBooleanResource() == true) {
                Resource += getRevealedFromArray(user, iTerrain, l).getTerrainResource().getResourceType()
                        .getShowResourceMap();
            }
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            int HowManySpace = 7 - Resource.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += Resource;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else if (Terrains[iTerrain][l].getType().equals("visible")) {
            if (Terrains[iTerrain][l].getTerrainResource() != null  && Terrains[iTerrain][l].getBooleanResource() == true) {
                Resource += Terrains[iTerrain][l].getTerrainResource().getResourceType().getShowResourceMap();
            }
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            int HowManySpace = 7 - Resource.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            addSpace(i, j, HowManySpaceLeft);
            Printmap[i][j] += Resource;
            addSpace(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else {
            addSpace(i, j, 7);
        }

    }

    private void ImprovementAlghoritm(int iTerrain, int i, int j, int l, User user) {
        String Improvement = "";
        int HowManySpaceLeft = 0;
        int HowManySpaceRight = 0;
        if (Terrains[iTerrain][l].getType().equals("revealed")) {
            if (getRevealedFromArray(user, iTerrain, l).getTerrainImprovement() != null) {
                Improvement += getRevealedFromArray(user, iTerrain, l).getTerrainImprovement().getImprovementType().getShowImprovement();
            }
            int HowManySpace = 5 - Improvement.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Color.MAGENTA_BACKGROUND_BRIGHT;
            addUnderline(i, j, HowManySpaceLeft);
            Printmap[i][j] += Improvement;
            addUnderline(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else if (Terrains[iTerrain][l].getType().equals("visible")) {
            if (Terrains[iTerrain][l].getTerrainImprovement() != null) {
                Improvement += Terrains[iTerrain][l].getTerrainImprovement().getImprovementType().getShowImprovement();
            }
            int HowManySpace = 5 - Improvement.length();
            HowManySpaceLeft = returnHowManySpaceInLeft(HowManySpace);
            HowManySpaceRight = returnHowManySpaceInRight(HowManySpace);
            Printmap[i][j] += Terrains[iTerrain][l].getTerrainTypes().getColor();
            addUnderline(i, j, HowManySpaceLeft);
            Printmap[i][j] += Improvement;
            addUnderline(i, j, HowManySpaceRight);
            Printmap[i][j] += Color.RESET;
        } else {
            addUnderline(i, j, 5);
        }

    }






    // call the alghoritm finction

    private void CivilizationPrintFirstHalf(int i, int j, int l, Database database) {
        River river;
        if (i > 0 && l > 0 && ((river = hasRiver(Terrains[i][l], Terrains[i - 1][l - 1])) != null) && !Terrains[i][l].getType().equals("fog of war") && !Terrains[i - 1][l - 1].getType().equals("fog of war")) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "/";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "/";
        }
        CivilizationAlghoritm(i, i, j, l, database);
    }

    private void CivilizationPrintSecondHalf(int i, int j, int l, Database database) {
        CivilizationAlghoritm(i, i, j, l + 1, database);
    }

    private void TerrainFeaturesFirstHalf(int i, int j, int l, User user) {
        TerrainFeaturesAlghoritm(i - 1, i, j, l + 1, user);
    }

    private void TerrainFeaturesSecondHalf(int i, int j, int l, User user) {
        River river;

        if (l > 0 && ((river = hasRiver(Terrains[i][l], Terrains[i][l - 1])) != null) &&  !Terrains[i][l].getType().equals("fog of war") && !Terrains[i][l - 1].getType().equals("fog of war")) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "\\";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "\\";
        }
        TerrainFeaturesAlghoritm(i, i, j, l, user);

    }

    private void XandYFirstHalf(int i, int j, int l) {
        River river;
        if (i > 0 && l > 0 && ((river = hasRiver(Terrains[i][l], Terrains[i - 1][l - 1])) != null) &&  !Terrains[i][l].getType().equals("fog of war") && !Terrains[i - 1][l - 1].getType().equals("fog of war")) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "/";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "/";
        }
        XandYAlghoritm(i, i, j, l);
    }

    private void XandYSecondHalf(int i, int j, int l) {
        XandYAlghoritm(i, i, j, l + 1);
    }

    private void ResourceFirstHalf(int i, int j, int l, User user) {
        ResourceAlghoritm(i - 1, i, j, l + 1, user);
    }

    private void ResourceSecondHalf(int i, int j, int l, User user) {
        River river;

        if (l > 0 && ((river = hasRiver(Terrains[i][l], Terrains[i][l - 1])) != null) &&  !Terrains[i][l].getType().equals("fog of war") && !Terrains[i][l - 1].getType().equals("fog of war")) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "\\";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "\\";
        }
        ResourceAlghoritm(i, i, j, l, user);
    }

    private void twoCombatFirstHalf(int i, int j, int l, User user) {

        River river;
        if (i > 0 && l > 0 && ((river = hasRiver(Terrains[i][l], Terrains[i - 1][l - 1])) != null) &&  !Terrains[i][l].getType().equals("fog of war") && !Terrains[i - 1][l - 1].getType().equals("fog of war")) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "/";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "/";
        }
        twocombatAlghoritm(i, i, j, l, user);
    }

    private void twoCombatSecondHalf(int i, int j, int l, User user) {
        twocombatAlghoritm(i, i, j, l + 1, user);
    }

    private void ImprovementFirstHalf(int i, int j, int l, User user) {
        ImprovementAlghoritm(i - 1, i, j, l + 1, user);
    }

    private void ImprovementSecondHalf(int i, int j, int l, User user) {
        River river;
        if (l > 0 && ((river = hasRiver(Terrains[i][l], Terrains[i][l - 1])) != null) &&  !Terrains[i][l].getType().equals("fog of war") && !Terrains[i][l - 1].getType().equals("fog of war")) {
            Printmap[i][j] += river.getColor();
            Printmap[i][j] += "\\";
            Printmap[i][j] += Color.RESET;
        } else {
            Printmap[i][j] += "\\";
        }
        ImprovementAlghoritm(i, i, j, l, user);
    }






    // Rows Of Map
    private void firstRow(int i, int j, int l, boolean check, Database database, User user) {
        if (check == true) {
            CivilizationPrintFirstHalf(i, j, l, database);
            betweetTwoTailFirstHalf(i, j, l);
            TerrainFeaturesFirstHalf(i, j, l, user);
        } else {
            CivilizationPrintFirstHalf(i, j, l, database);
            betweetTwoTailFirstHalf(i, j, l);
            addSpace(i, j, 9);
        }
    }

    private void secondRow(int i, int j, int l, boolean check, Database database, User user) {
        if (check == true) {
            XandYFirstHalf(i, j, l);
            betweetTwoTailFirstHalf(i, j, l);
            ResourceFirstHalf(i, j, l, user);
        } else {
            XandYFirstHalf(i, j, l);
            betweetTwoTailFirstHalf(i, j, l);
            addSpace(i, j, 7);
        }

    }

    private void thirdRow(int i, int j, int l, boolean check, User user) {

        if (check == true) {
            twoCombatFirstHalf(i, j, l, user);
            betweetTwoTailFirstHalf(i, j, l);
            ImprovementFirstHalf(i, j, l, user);
        } else {
            twoCombatFirstHalf(i, j, l, user);
            betweetTwoTailFirstHalf(i, j, l);
            addUnderline(i, j, 5);
        }
    }

    private void fourthRow(int i, int j, int l, boolean check, Database database, User user) {

        if (check == true) {
            TerrainFeaturesSecondHalf(i, j, l, user);
            betweenTwoTailSecondHalf(i, j, l);
            CivilizationPrintSecondHalf(i, j, l, database);
        } else {
            TerrainFeaturesSecondHalf(i, j, l, user);
            betweenTwoTailSecondHalf(i, j, l);
            addSpace(i, j, 5);
        }
    }

    private void fifthRow(int i, int j, int l, boolean check, Database database, User user) {
        if (check == true) {
            ResourceSecondHalf(i, j, l, user);
            betweenTwoTailSecondHalf(i, j, l);
            XandYSecondHalf(i, j, l);
        } else {
            ResourceSecondHalf(i, j, l, user);
            betweenTwoTailSecondHalf(i, j, l);
            addSpace(i, j, 7);
        }
    }

    private void sixthRow(int i, int j, int l, boolean check, User user) {
        if (check == true) {
            ImprovementSecondHalf(i, j, l, user);
            betweenTwoTailSecondHalf(i, j, l);
            twoCombatSecondHalf(i, j, l, user);
        } else {
            ImprovementSecondHalf(i, j, l, user);
            betweenTwoTailSecondHalf(i, j, l);
            addSpace(i, j, 9);
        }
    }






    // print map
    public void SwitchCaseFirstHalf(int i, int j, int l, Database database, User user, int count) {
        switch (j) {
            case 0:
                if (i - count > 0) {
                    firstRow(i, j, l, true, database, user);
                } else {
                    firstRow(i, j, l, false, database, user);
                }
                break;
            case 1:
                if (i - count > 0) {
                    secondRow(i, j, l, true, database, user);
                } else {
                    secondRow(i, j, l, false, database, user);
                }
                break;
            case 2:
                if (i - count > 0) {
                    thirdRow(i, j, l, true, user);
                } else {
                    thirdRow(i, j, l, false, user);
                }
                break;

        }
    }

    public void SwitchCaseSecondHalf(int i, int j, int l, Database database, User user, int count) {
        switch (j) {

            case 3:
                if (i != count) {
                    fourthRow(i, j, l, true, database, user);
                } else {
                    fourthRow(i, j, l, false, database, user);
                }
                break;
            case 4:
                if (i != count) {
                    fifthRow(i, j, l, true, database, user);
                } else {
                    fifthRow(i, j, l, false, database, user);
                }
                break;
            case 5:
                if (i != count) {
                    sixthRow(i, j, l, true, user);
                } else {
                    sixthRow(i, j, l, false, user);
                }
                break;
        }
    }

    public String[][] printMap(Database database, User user) {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < Iteration; j++) {
                Printmap[i][j] = "";
            }
        }
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < Iteration / 2; j++) {
                addSpace(i, j, Iteration / 2 - 1 - j);
                for (int l = 0; l < COL; l += 2) {
                    SwitchCaseFirstHalf(i, j, l, database, user, 0);
                }

            }
            for (int j = Iteration / 2; j < Iteration; j++) {
                addSpace(i, j, j - Iteration / 2);
                for (int l = 0; l < COL; l += 2) {
                    SwitchCaseSecondHalf(i, j, l, database, user, ROW - 1);
                }
            }
        }
        return this.Printmap;
    }

    public String[][] PrintMapXandY(Database database, User user, int x, int y) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Iteration; j++) {
                Printmap[i][j] = "";
            }
        }
        for (int i = x; i < x + 3; i++) {

            for (int j = 0; j < Iteration / 2; j++) {
                addSpace(i, j, Iteration / 2 - 1 - j);
                for (int l = 0; l < y + 6; l += 2) {
                    SwitchCaseFirstHalf(i, j, l, database, user, x);
                }

            }
            for (int j = Iteration / 2; j < Iteration; j++) {
                addSpace(i, j, j - Iteration / 2);
                for (int l = 0; l < y + 6; l += 2) {
                    SwitchCaseSecondHalf(i, j, l, database, user, x + 2);
                }
            }
        }
        return Printmap;
    }

}