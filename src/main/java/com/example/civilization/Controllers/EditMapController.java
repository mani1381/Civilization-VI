package com.example.civilization.Controllers;

import com.example.civilization.Model.Database;
import com.example.civilization.Model.Map;
import com.example.civilization.Model.Resources.Resource;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.River;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class EditMapController {
    private static EditMapController instance;
    private static Map map;
    private boolean up_up;
    private boolean up_left;
    private boolean up_right;
    private boolean down_down;
    private boolean down_left;
    private boolean down_right;

    public static EditMapController getInstance() {
        if (instance == null) {
            instance = new EditMapController();
            map = Database.getInstance().getMap();
        }
        return instance;
    }

    public boolean haveDigits(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) < '0' || num.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public void setRiverOdd(int i, int j) {
        if (j % 2 == 1) {
            if (up_up) {
                if (i - 1 >= 0) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i - 1][j]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (up_left) {
                if (i - 1 >= 0 && j - 1 >= 0) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i - 1][j - 1]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (up_right) {
                if (i - 1 >= 0 && j + 1 < Database.getInstance().getMap().getCOL()) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i - 1][j + 1]);
                    Database.getInstance().getMap().addRiver(river);
                }

            }
            if (down_down) {
                if (i + 1 < Database.getInstance().getMap().getROW()) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i + 1][j]);
                    Database.getInstance().getMap().addRiver(river);
                }

            }
            if (down_left) {
                if (j - 1 >= 0) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i][j - 1]);
                    Database.getInstance().getMap().addRiver(river);
                }

            }
            if (down_right) {
                if (j + 1 < Database.getInstance().getMap().getCOL()) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i][j + 1]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
        }
    }

    public void setRiverEven(int i, int j) {
        if (j % 2 == 0) {
            if (up_up) {
                if (i - 1 >= 0) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i - 1][j]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (up_left) {
                if (j - 1 >= 0) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i][j - 1]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (up_right) {
                if (j + 1 < Database.getInstance().getMap().getCOL()) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i][j + 1]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (down_down) {
                if (i + 1 < Database.getInstance().getMap().getROW()) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i + 1][j]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (down_left) {
                if (i + 1 < Database.getInstance().getMap().getROW() && j - 1 >= 0) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i + 1][j - 1]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }
            if (down_right) {
                if (i + 1 < Database.getInstance().getMap().getROW() && j + 1 < Database.getInstance().getMap().getCOL()) {
                    River river = new River(Database.getInstance().getMap().getTerrain()[i][j], Database.getInstance().getMap().getTerrain()[i + 1][j + 1]);
                    Database.getInstance().getMap().addRiver(river);
                }
            }

        }
    }

    public void setTileInMap(int i, int j, String terrainType, String terrainFeature, String terrainResource) {
        TerrainTypes[] types = TerrainTypes.values();
        TerrainTypes typeTile = null;
        for (TerrainTypes type : types) {
            if (type.name().equalsIgnoreCase(terrainType)) {
                typeTile = type;
                break;
            }
        }
        ArrayList<TerrainFeatureTypes> feature = new ArrayList<>();
        TerrainFeatureTypes[] typeOfFeature = TerrainFeatureTypes.values();
        for (TerrainFeatureTypes type : typeOfFeature) {
            if (type.name().equalsIgnoreCase(terrainFeature)) {
                feature.add(type);
                break;
            }
        }
        Resource resource = null;
        ResourceTypes[] typeOfResource = ResourceTypes.values();
        for (ResourceTypes type : typeOfResource) {
            if (type.name().equalsIgnoreCase(terrainResource)) {
                resource = new Resource(type);
                break;
            }
        }
        map.getTerrain()[i][j].setTerrainTypes(typeTile);
        map.getTerrain()[i][j].setTerrainFeatureTypesArray(feature);
        map.getTerrain()[i][j].setTerrainResource(resource);
        setRiverOdd(i, j);
        setRiverEven(i, j);
    }

    public void changeCheckBoxState(CheckBox up_up, CheckBox up_left, CheckBox up_right, CheckBox down_down, CheckBox down_left, CheckBox down_right) {
        this.up_up = false;
        this.up_left = false;
        this.up_right = false;
        this.down_down = false;
        this.down_left = false;
        this.down_right = false;
        if (up_up.isSelected()) {
            this.up_up = true;
        }
        if (up_left.isSelected()) {
            this.up_left = true;
        }
        if (up_right.isSelected()) {
            this.up_right = true;
        }
        if (down_down.isSelected()) {
            this.down_down = true;
        }
        if (down_left.isSelected()) {
            this.down_left = true;
        }
        if (down_right.isSelected()) {
            this.down_right = true;
        }
        up_up.setSelected(false);
        up_left.setSelected(false);
        up_right.setSelected(false);
        down_down.setSelected(false);
        down_left.setSelected(false);
        down_right.setSelected(false);
    }
}
