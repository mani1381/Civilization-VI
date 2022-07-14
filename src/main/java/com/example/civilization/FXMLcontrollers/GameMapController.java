package com.example.civilization.FXMLcontrollers;


import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Controllers.SaveGame;
import com.example.civilization.Main;
import com.example.civilization.Model.TerrainFeatures.TerrainFeatureTypes;
import com.example.civilization.Model.Terrains.TerrainTypes;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonCombatUnit;
import com.example.civilization.Model.Units.UnitTypes;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameMapController {

    static int start_X_InShowMap = 0;
    static int start_Y_InShowMap = 0;
    public TextField text;
    public Label coordinates;
    public Label hp;
    public Label cs;
    public Label rcs;
    public Button actions;
    public ImageView unitImage;
    DatabaseController databaseController = DatabaseController.getInstance();
    @FXML
    private ArrayList<Polygon> terrainHexagons = new ArrayList<>();
    @FXML
    private Pane pane;

    @FXML
    private AnchorPane unitPane;

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

    @FXML
    public void initialize() throws IOException {
        unitPane.setVisible(false);
//        DatabaseController.getInstance().getMap().generateMap();
//        DatabaseController.getInstance().setCivilizations(DatabaseController.getInstance().getDatabase().getUsers());
        this.databaseController.getMap().initializeMapUser(DatabaseController.getInstance().getDatabase().getActiveUser());

        pane.setMaxSize(1300, 700);

        setHexagons(start_X_InShowMap, start_Y_InShowMap, 280, 130, 50);

        for (Polygon polygon : terrainHexagons) {
            pane.getChildren().add(polygon);
        }

        Platform.runLater(() -> {
            try {
                changingDirection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                setSelectedUnitData();
            } catch (FileNotFoundException e) {
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
                    if (start_X_InShowMap > 0) {
                        start_X_InShowMap--;
                        mapForNewCoordinates();
                    }
                }
                case DOWN -> {
                    if (start_X_InShowMap + 6 < databaseController.getMap().getROW() - 1) {
                        start_X_InShowMap++;
                        mapForNewCoordinates();
                    }
                }
                case LEFT -> {
                    if (start_Y_InShowMap > 0) {
                        start_Y_InShowMap--;
                        mapForNewCoordinates();
                    }

                }
                case RIGHT -> {
                    if (start_Y_InShowMap + 12 < databaseController.getMap().getCOL() - 1) {
                        start_Y_InShowMap++;
                        mapForNewCoordinates();
                    }

                }
            }
        });

    }

    private void setSelectedUnitData() throws FileNotFoundException {
        unitPane.setVisible(false);
        CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
        NonCombatUnit nonCombatUnit = DatabaseController.getInstance().getSelectedNonCombatUnit();

        if (combatUnit != null) {
            unitPane.setVisible(true);
            coordinates.setText("X: " + combatUnit.getX() + " Y: " + combatUnit.getY());
            hp.setText("HP : " + combatUnit.getHP());
            cs.setText("CS: " + combatUnit.getUnitType().getCombatStrengh());
            rcs.setText("RCS: " + combatUnit.getUnitType().getRangedCombatStrengh());
            unitImage.setImage(new Image(new FileInputStream(getImagePatternOfTiles(combatUnit.getUnitType().name()))));

        } else if (nonCombatUnit != null) {
            unitPane.setVisible(true);
            coordinates.setText("X: " + nonCombatUnit.getX() + " Y: " + nonCombatUnit.getY());
            hp.setText("HP : " + nonCombatUnit.getHP());
            cs.setText("CS: " + nonCombatUnit.getUnitType().getCombatStrengh());
            rcs.setText("RCS: " + nonCombatUnit.getUnitType().getRangedCombatStrengh());
            unitImage.setImage(new Image(new FileInputStream(getImagePatternOfTiles(nonCombatUnit.getUnitType().name()))));
        }

        for (Node children : unitPane.getChildren()) {
            if (children instanceof Label) {
                ((Label) children).setFont(Font.font("Copperplate", 18));
            }
        }

    }


    private void mapForNewCoordinates() {
        int i = 0;
        try {
            for (Polygon polygon : terrainHexagons) {
                pane.getChildren().remove(polygon);
            }
            terrainHexagons.clear();
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


        int i = start_x, j = start_y;

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

        Polygon rivers = addingRivers(radius, i, j, x, y);
        terrainHexagons.add(rivers);
        if (databaseController.getTerrainByCoordinates(i, j).getType().equals("revealed")) {
            polygonTerrainType.setOpacity(0.2);
            polygonTerrainFeatureType.setOpacity(0.2);
        } else if (databaseController.getTerrainByCoordinates(i, j).getType().equals("fog of war")) {
            polygonTerrainFeatureType.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/CrosshatchHexagon.png"))));
            polygonTerrainType.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/CrosshatchHexagon.png"))));
            rivers.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/com/example/civilization/PNG/civAsset/map/CrosshatchHexagon.png"))));
        }

        if (databaseController.getTerrainByCoordinates(i, j).getCombatUnit() != null) {
            polygonCombatUnit.setFill(new ImagePattern(new Image(new FileInputStream(getImagePatternOfTiles(databaseController.getTerrainByCoordinates(i, j).getCombatUnit().getUnitType().name())))));
            terrainHexagons.add(polygonCombatUnit);

        }
        if (databaseController.getTerrainByCoordinates(i, j).getNonCombatUnit() != null) {
            polygonNonCombatUnit.setFill(new ImagePattern(new Image(new FileInputStream(getImagePatternOfTiles(databaseController.getTerrainByCoordinates(i, j).getNonCombatUnit().getUnitType().name())))));
            terrainHexagons.add(polygonNonCombatUnit);
        }

        selectingUnits(new ArrayList<>(Arrays.asList(rivers, polygonTerrainType, polygonTerrainFeatureType, polygonNonCombatUnit, polygonCombatUnit)), i, j);
        showingPopUp(new ArrayList<>(Arrays.asList(rivers, polygonTerrainType, polygonTerrainFeatureType, polygonNonCombatUnit, polygonCombatUnit)), i, j);
        helloselectingUnits(new ArrayList<>(Arrays.asList(rivers, polygonTerrainType, polygonTerrainFeatureType, polygonNonCombatUnit, polygonCombatUnit)), i, j);


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

                polygon.setStroke(Color.RED);
                polygon.setStrokeWidth(2);
                polygon.addEventFilter(MouseEvent.ANY, new EventHandler<>() {

                    long startTime;

                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                            startTime = System.currentTimeMillis();
                        } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                            if (System.currentTimeMillis() - startTime > 1000) {
                                stage.show();
                            }
                        } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
                            if (stage.isShowing()) {
                                stage.close();
                            }

                        }
                    }
                });



            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void helloselectingUnits(ArrayList<Polygon> polygons, int i, int j) {
        for (Polygon polygon : polygons) {
            polygon.setOnMousePressed(mouseEvent -> {
                if(DatabaseController.getInstance().getSelectedNonCombatUnit() != null || DatabaseController.getInstance().getSelectedCombatUnit() != null){
                    System.out.println(DatabaseController.getInstance().unitMovement(i,j,DatabaseController.getInstance().getDatabase().getActiveUser()));
                    DatabaseController.getInstance().movementOfAllUnits(DatabaseController.getInstance().getDatabase().getActiveUser());
             //       DatabaseController.getInstance().setTerrainsOfEachCivilization(DatabaseController.getInstance().getDatabase().getActiveUser());
                    this.databaseController.getMap().initializeMapUser(DatabaseController.getInstance().getDatabase().getActiveUser());
                    try {
                        setSelectedUnitData();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    mapForNewCoordinates();
                }
            });
        }


    }

    public void selectingUnits(ArrayList<Polygon> polygons, int i, int j) {
        for (Polygon polygon : polygons) {
            polygon.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (DatabaseController.getInstance().getMap().getTerrain()[i][j].getCombatUnit() != null) {
                            DatabaseController.getInstance().deselectAllUnits();
                            DatabaseController.getInstance().getMap().getTerrain()[i][j].getCombatUnit().setIsSelected(true);
                        } else if (DatabaseController.getInstance().getMap().getTerrain()[i][j].getNonCombatUnit() != null) {
                            DatabaseController.getInstance().deselectAllUnits();
                            DatabaseController.getInstance().getMap().getTerrain()[i][j].getNonCombatUnit().setIsSelected(true);
                        }
                        try {
                            setSelectedUnitData();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
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
        if (terrainTypeAndFeatureAddress(name)) {
            return "src/main/resources/com/example/civilization/PNG/civAsset/map/Tiles/" + name + ".png";
        }

        for (UnitTypes unitTypes : UnitTypes.values()) {
            if (unitTypes.name().equalsIgnoreCase(name)) {
                return "src/main/resources/com/example/civilization/PNG/civAsset/units/Units/" + name + ".png";
            }
        }
        return null;
    }

    public void goToChooseResearch() {
        Main.changeMenu("ChooseResearch");

    }

    public void goToCheatCode() {
        Main.changeMenu("CheatCode");
    }

    public void goToUnitActions() {
        Main.changeMenu("UnitActions");
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


