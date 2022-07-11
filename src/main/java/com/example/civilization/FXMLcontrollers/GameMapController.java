package com.example.civilization.FXMLcontrollers;


import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Controllers.SaveGame;
import com.example.civilization.Main;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Units.UnitTypes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameMapController {

    public TextField text;
    @FXML
    private ArrayList<Polygon> terrainHexagons = new ArrayList<>();



    DatabaseController databaseController = DatabaseController.getInstance();

    /*
            object.setY(object.getY() + (KeyEventHandler.getStatus("S", "Down") - KeyEventHandler.getStatus("W", "Up")) * AirPlane.speed);
        object.setY(Math.max(50, Math.min(object.getY(), object.getPane().getHeight() - object.getHeight())));
        object.setX(object.getX() + (KeyEventHandler.getStatus("D", "Right") - KeyEventHandler.getStatus("A", "Left")) * AirPlane.speed);
     */

    @FXML
    private Pane pane;
    int start_X_InShowMap = 0;
    int start_Y_InShowMap = 0;




    @FXML
    public void initialize() throws IOException {

        this.databaseController.getMap().initializeMapUser(DatabaseController.getInstance().getDatabase().getActiveUser());


        pane.setMaxSize(1300, 700);

//Adding coordinates to the polygon
        setHexagons(start_X_InShowMap, start_Y_InShowMap, 280, 130, 50);

        for (Polygon polygon : terrainHexagons) {
            pane.getChildren().add(polygon);
        }

        Platform.runLater(()-> {
            try {
                changingDirection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });




    }

    @FXML
    private void changingDirection() throws IOException {
       this.databaseController.getMap().initializeMapUser(DatabaseController.getInstance().getDatabase().getActiveUser());
        pane.getScene().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> {
                    if(start_X_InShowMap > 0){
                        start_X_InShowMap--;
                        mapForNewCoordinates();
                    }
                }
                case DOWN -> {
                    if(start_X_InShowMap + 6 < databaseController.getMap().getROW() -1){
                        start_X_InShowMap++;
                        mapForNewCoordinates();
                    }
                }
                case LEFT -> {
                    if(start_Y_InShowMap > 0){
                        start_Y_InShowMap--;
                        mapForNewCoordinates();
                    }

                }
                case RIGHT -> {
                    if(start_Y_InShowMap + 12 < databaseController.getMap().getCOL() -1){
                        start_Y_InShowMap++;
                        mapForNewCoordinates();
                    }

                }
            }
        });

    }

    private void mapForNewCoordinates() {
        try {
            for (Polygon polygon : terrainHexagons) {
                pane.getChildren().remove(polygon);
            }
            setHexagons(start_X_InShowMap, start_Y_InShowMap, 280, 130, 50);
            for (Polygon polygon : terrainHexagons) {
                pane.getChildren().addAll(polygon);
            }


        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Double[] drawingPolygonWithCenterAndRadius(double x, double y, double radius) {
        double v = x + radius * Math.cos(Math.PI / 3);
        double v1 = y + radius * Math.sin(Math.PI / 3);
        double v2 = y - radius * Math.sin(Math.PI / 3);
        double v3 = x - radius * Math.cos(Math.PI / 3);
        return new Double[]{v, v1, x + radius, y, v, v2, v3, v2, x - radius, y, v3, v1,};

    }

    public void setHexagons(int start_x, int start_y, double x0, double y0, int radius) throws FileNotFoundException {


        //   System.out.println(pane.getMaxWidth());
        int i = start_x, j = start_y;
        // System.out.println(databaseController.getTerrainByCoordinates(1, 3).getTerrainTypes().name());
        for (double x = x0; x < pane.getMaxWidth() - radius; x += 3 * radius) {
            i = start_x;

            for (double y = y0; y < pane.getMaxHeight() - radius; y += 2 * radius * Math.sin(Math.PI / 3)) {
                i = hexagonsType(radius, i, j, x, y);

            }
            j += 2;

        }
        i = start_x;
        j = start_y + 1;

        for (double x = x0 + 1.5 * radius; x < pane.getMaxWidth() - radius; x += 3 * radius) {
            i = start_x;
            for (double y = y0 + radius * Math.sin(Math.PI / 3); y < pane.getMaxHeight() - radius; y += 2 * radius * Math.sin(Math.PI / 3)) {
                i = hexagonsType(radius, i, j, x, y);
            }
            j += 2;
        }
    }

    private int hexagonsType(int radius, int i, int j, double x, double y) throws FileNotFoundException {
        Polygon polygonTerrainType = new Polygon();
        Polygon polygonTerrainFeatureType = new Polygon();
        Polygon polygonCombatUnit = new Polygon();
        Polygon polygonNonCombatUnit = new Polygon();

        polygonTerrainType.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));
        polygonTerrainFeatureType.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));
        polygonCombatUnit.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));
        polygonNonCombatUnit.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));

        polygonTerrainType.setFill(new ImagePattern(new Image(new FileInputStream(getImagePatternOfTiles(databaseController.getTerrainByCoordinates(i, j).getTerrainTypes().name())))));
        terrainHexagons.add(polygonTerrainType);
        if (!databaseController.getTerrainByCoordinates(i, j).getTerrainFeatureTypes().isEmpty() && databaseController.getTerrainByCoordinates(i, j).getTerrainFeatureTypes().get(0) != null) {

            polygonTerrainFeatureType.setFill(new ImagePattern(new Image(new FileInputStream(getImagePatternOfTiles(databaseController.getTerrainByCoordinates(i, j).getTerrainFeatureTypes().get(0).name())))));
            terrainHexagons.add(polygonTerrainFeatureType);

        }
        if (databaseController.getTerrainByCoordinates(i, j).getCombatUnit() != null) {

        }
        if (databaseController.getTerrainByCoordinates(i, j).getNonCombatUnit() != null) {

        }
        Polygon rivers = addingRivers(radius, i, j, x, y);
        terrainHexagons.add(rivers);
        if(databaseController.getTerrainByCoordinates(i, j).getType().equals("revealed")){
            polygonTerrainType.setOpacity(0.2);
            polygonTerrainFeatureType.setOpacity(0.2);
        }
        else if(databaseController.getTerrainByCoordinates(i, j).getType().equals("fog of war")){
            polygonTerrainFeatureType.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/CrosshatchHexagon.png"))));
            polygonTerrainType.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/CrosshatchHexagon.png"))));
            rivers.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/CrosshatchHexagon.png"))));
       }

       /* terrainTypeHexagons.add(polygonCombatUnit);
        terrainTypeHexagons.add(polygonNonCombatUnit);

*/

        showingPopUp(new ArrayList<>(Arrays.asList(rivers, polygonTerrainType, polygonTerrainFeatureType)), i, j);


        i++;
        return i;
    }

    public void showingPopUp(ArrayList<Polygon> polygons, int i, int j) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/terrainsPopUp.fxml"));
            Parent root = loader.load();
            TerrainPopUpController secController = loader.getController();
            secController.setData(databaseController.getTerrainByCoordinates(i, j));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            for (Polygon polygon : polygons) {
                polygon.setOnMousePressed(e -> stage.show());
                polygon.setOnMouseReleased(e -> stage.close());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public Polygon addingRivers(int radius, int i, int j, double x, double y) throws FileNotFoundException {
        Polygon rivers = new Polygon();
        for (int a = -1; a < 2; a++) {
            for (int b = -1; b < 2; b++) {
                if (i + a >= 0 && i + a < databaseController.getMap().getROW() && j + b >= 0 && j + b < databaseController.getMap().getCOL()) {
                    if (databaseController.getMap().hasRiver(databaseController.getMap().getTerrain()[i + a][j + b], databaseController.getMap().getTerrain()[i][j]) != null) {

                        if (a == 1 && b == 0) {
                            //       System.out.println(i + " " + j + " " + a + " " + b);
                            rivers.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));
                            rivers.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/Tiles/River-Bottom.png"))));
                        } else {
                            if ((j % 2 == 0 && a == 0 && b == 1) || (j % 2 == 1 && a == 1 && b == 1)) {
                                rivers.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));
                                rivers.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/Tiles/River-BottomRight.png"))));


                            }
                            if ((j % 2 == 0 && a == 0 && b == 1) || (j % 2 == 1 && a == 1 && b == -1)) {
                                rivers.getPoints().addAll(drawingPolygonWithCenterAndRadius(x, y, radius));
                                rivers.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/Tiles/River-BottomLeft.png"))));
                            }
                        }
                    }
                }
            }
        }
                /*if(databaseController.getTerrainByCoordinates(i, j).getType().equals("revealed")){
            rivers.setOpacity(0.2);

        }
        else if(databaseController.getTerrainByCoordinates(i, j).getType().equals("fog of war"){


            rivers.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/icons/OtherIcons/whiteDot.png"))));
       }

         */
        return rivers;
    }

    public String getImagePatternOfTiles(String name) {
        if (terrainTypeAndFeatureAddress(name))
            return "src/main/resources/com/example/civilization/PNG/civAsset/map/Tiles/" + name + ".png";

        for (UnitTypes unitTypes : UnitTypes.values()) {
            if (unitTypes.name().equalsIgnoreCase(name)) {
                return " src/main/resources/com/example/civilization/PNG/civAsset/units/Units/" + name + ".png";
            }
        }
        return null;
    }

    static boolean terrainTypeAndFeatureAddress(String name) {
        for (TerrainTypes terrainTypes : TerrainTypes.values()) {
            if (terrainTypes.name().equalsIgnoreCase(name)) {
                return true;
            }
        }

        for (TerrainFeatureTypes terrainFeatureTypes : TerrainFeatureTypes.values()) {
            if (terrainFeatureTypes.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public void goToChooseResearch() {
        Main.changeMenu("ChooseResearch");

    }


    public void save(MouseEvent mouseEvent) throws IOException {
        SaveGame.getInstance().saveGame();
    }

    public void load(MouseEvent mouseEvent) throws IOException {
        String t = text.getText();
        int num = Integer.parseInt(t);
        SaveGame.getInstance().loadGame(num);
    }
}


