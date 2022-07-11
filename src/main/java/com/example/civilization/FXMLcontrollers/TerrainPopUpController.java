package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.Improvements.Improvement;
import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Revealed;
import com.example.civilization.Model.Terrain;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TerrainPopUpController {

    @FXML
    private Label x = new Label();
    @FXML
    private Label y = new Label();

    @FXML
    private Label Type = new Label();
    @FXML
    private Label TerrainType = new Label();
    @FXML
    private Label FeatureType = new Label();
    @FXML
    private Label ResourceType = new Label();
    @FXML
    private Label ImprovementType = new Label();
    @FXML
    private Label TypeFood = new Label();
    @FXML
    private Label TypeProduction = new Label();
    @FXML
    private Label TypeGold = new Label();
    @FXML
    private Label FeatureFood = new Label();
    @FXML
    private Label FeatureProduction = new Label();
    @FXML
    private Label FeatureGold = new Label();
    @FXML
    private Label ResourceFood = new Label();
    @FXML
    private Label ResourceProduction = new Label();
    @FXML
    private Label ResourceGold = new Label();
    @FXML
    private Label ImprovementFood = new Label();
    @FXML
    private Label ImprovementProduction = new Label();
    @FXML
    private Label ImprovementGold = new Label();
    @FXML
    private ImageView TerrainImage = new ImageView();
    @FXML
    private ImageView FeatureImage = new ImageView();
    @FXML
    private ImageView ResourceImage = new ImageView();
    @FXML
    private ImageView ImprovementImage = new ImageView();

    public void setData(Terrain terrain) throws FileNotFoundException {

        x.setText("x : " + terrain.getX());
        y.setText("y : " + terrain.getY());
        if(terrain.getType().equals("visible")) {

            setTerrainTypeAndFeatureType(terrain, terrain.getTerrainTypes(), terrain.getTerrainFeatureTypes());
            if (terrain.getResource() != null) {
                ResourceType.setText(terrain.getResource().getResourceType().name());
                ResourceFood.setText(Integer.toString(terrain.getResource().getResourceType().getFood()));
                ResourceProduction.setText(Integer.toString(terrain.getResource().getResourceType().getProduction()));
                ResourceGold.setText(Integer.toString(terrain.getResource().getResourceType().getGold()));
                ResourceImage.setImage((new Image(new FileInputStream(getImagePatternOfTiles(terrain.getResource().getResourceType().name())))));
            }
            setImprovement(terrain.getTerrainImprovement(), terrain);
        }
        else if(terrain.getType().equals("revealed")){
            for(Revealed revealed : terrain.getReveals()){
                if(revealed.getUser().equals(DatabaseController.getInstance().getDatabase().getActiveUser())){
                    setTerrainTypeAndFeatureType(terrain, revealed.getTerrainTypes(), revealed.getTerrainFeatureTypes());
                    if (revealed.getTerrainResource() != null) {
                        ResourceType.setText(revealed.getTerrainResource().getResourceType().name());
                        ResourceFood.setText(Integer.toString(revealed.getTerrainResource().getResourceType().getFood()));
                        ResourceProduction.setText(Integer.toString(revealed.getTerrainResource().getResourceType().getProduction()));
                        ResourceGold.setText(Integer.toString(revealed.getTerrainResource().getResourceType().getGold()));
                        ResourceImage.setImage((new Image(new FileInputStream(getImagePatternOfTiles(revealed.getTerrainResource().getResourceType().name())))));
                    }
                    setImprovement(revealed.getTerrainImprovement(), terrain);
                }
            }

        }


    }

    private void setImprovement(Improvement terrainImprovement, Terrain terrain) throws FileNotFoundException {
        if (terrainImprovement != null) {
            ImprovementType.setText(terrainImprovement.getImprovementType().name());
            ImprovementFood.setText(Integer.toString(terrainImprovement.getImprovementType().getFood()));
            ImprovementProduction.setText(Integer.toString(terrainImprovement.getImprovementType().getProduction()));
            ImprovementGold.setText(Integer.toString(terrainImprovement.getImprovementType().getGold()));
            ImprovementImage.setImage((new Image(new FileInputStream(getImagePatternOfTiles(terrainImprovement.getImprovementType().name())))));
        }
    }

    private void setTerrainTypeAndFeatureType(Terrain terrain, TerrainTypes terrainTypes, ArrayList<TerrainFeatureTypes> terrainFeatureTypes) throws FileNotFoundException {
        TerrainType.setText(terrainTypes.name());
        TypeFood.setText(Integer.toString(terrainTypes.getFood()));
        TypeProduction.setText(Integer.toString(terrainTypes.getProduct()));
        TypeGold.setText(Integer.toString(terrainTypes.getGold()));
        TerrainImage.setImage((new Image(new FileInputStream(getImagePatternOfTiles(terrainTypes.name())))));
        if (terrain.getType() != null) {
            Type.setText(terrain.getType());
        }
        if (terrainFeatureTypes != null && !terrainFeatureTypes.isEmpty()) {
            FeatureType.setText(terrainFeatureTypes.get(0).name());
            FeatureFood.setText(Integer.toString(terrainFeatureTypes.get(0).getFood()));
            FeatureProduction.setText(Integer.toString(terrainFeatureTypes.get(0).getProduct()));
            FeatureGold.setText(Integer.toString(terrainFeatureTypes.get(0).getGold()));
            FeatureImage.setImage((new Image(new FileInputStream(getImagePatternOfTiles(terrainFeatureTypes.get(0).name())))));
        }
    }

    public String getImagePatternOfTiles(String name) throws FileNotFoundException {
        if (GameMapController.terrainTypeAndFeatureAddress(name))
            return "src/main/resources/com/example/civilization/PNG/civAsset/map/Tiles/" + name + ".png";

        for (ResourceTypes resourceTypes : ResourceTypes.values()) {
            if (resourceTypes.name().equalsIgnoreCase(name)) {
                return "src/main/resources/com/example/civilization/PNG/civAsset/icons/ResourceIcons/" + name + ".png";
            }

        }

        for (ImprovementTypes improvementTypes : ImprovementTypes.values()) {
            if (improvementTypes.name().equalsIgnoreCase(name)) {
                return "src/main/resources/com/example/civilization/PNG/civAsset/icons/ImprovementIcons/" + name + ".png";
            }
        }
        return null;
    }


}
