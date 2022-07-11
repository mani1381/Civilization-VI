package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Improvements.Improvement;
import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Technologies.Technology;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Terrain;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class WorkersOptionsController {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label workersCoordinates = new Label();

    @FXML
    ArrayList<Button> unlockable = new ArrayList<>();


    @FXML
    public void initialize() {
        Platform.runLater(this::setTexts);


    }

    public void setTexts() {
        for (TechnologyTypes technologyTypes : TechnologyTypes.values()) {
            DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getTechnologies().add(new Technology(false, 0, technologyTypes, true));
        }

        int x = DatabaseController.getInstance().getSelectedNonCombatUnit().getX();
        int y = DatabaseController.getInstance().getSelectedNonCombatUnit().getY();
        Terrain workersTerrain = DatabaseController.getInstance().getTerrainByCoordinates(x, y);


        workersCoordinates.setText("Worker's Coordinates    :   X : " + x + "   Y : " + y);
        int i = 0;
        for (ImprovementTypes improvementTypes : DatabaseController.getInstance().improvementsThatCanBeBuiltInThisTerrain()) {
            unlockable.add(new Button());
            unlockable.get(i).setFont(Font.font("Copperplate", 15));
            unlockable.get(i).setTextFill(Color.RED);
            unlockable.get(i).setPrefSize(workersCoordinates.getPrefWidth(), workersCoordinates.getPrefHeight());
            unlockable.get(i).setText("Build : " + improvementTypes.name());
            unlockable.get(i).setLayoutX(workersCoordinates.getLayoutX());
            unlockable.get(i).setLayoutY(workersCoordinates.getLayoutY() + 50 * (i + 1) + 75);
            Button button = unlockable.get(i);

            unlockable.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (workersTerrain.getTerrainImprovement() == null) {
                            for (ImprovementTypes improvementTypes1 : ImprovementTypes.values()) {
                                if (improvementTypes1.name().equalsIgnoreCase(button.getText().substring(8))) {
                                    workersTerrain.setTerrainImprovement(new Improvement(x, y, improvementTypes1));
                                }
                            }

                        } else {
                            workersTerrain.getTerrainImprovement().setBeingWorkedOn(true);
                            workersTerrain.getNonCombatUnit().setIsSelected(false);
                            workersTerrain.getNonCombatUnit().setIsFinished(true);
                            workersTerrain.setHasToBeDeleted(false);
                            Main.changeMenu("GameMap");
                        }

                    }
                }
            });


            showingPopUp(button);

            anchorPane.getChildren().add(unlockable.get(i));
            i++;
        }

        if (DatabaseController.getInstance().routsThatCanBeDeletedInThisTerrain() != null) {
            unlockable.add(new Button());
            unlockable.get(i).setFont(Font.font("Copperplate", 15));
            unlockable.get(i).setTextFill(Color.RED);
            unlockable.get(i).setPrefSize(workersCoordinates.getPrefWidth(), workersCoordinates.getPrefHeight());
            unlockable.get(i).setText("Delete Route : " + DatabaseController.getInstance().routsThatCanBeDeletedInThisTerrain().name());
            unlockable.get(i).setVisible(true);
            unlockable.get(i).setLayoutX(workersCoordinates.getLayoutX());
            unlockable.get(i).setLayoutY(workersCoordinates.getLayoutY() + 50 * (i + 1) + 75);

            Button button = unlockable.get(i);
            unlockable.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        workersTerrain.getTerrainImprovement().setHasToBeDeleted(true);
                        workersTerrain.setHasToBeDeleted(false);
                        workersTerrain.getNonCombatUnit().setIsSelected(false);
                        workersTerrain.getNonCombatUnit().setIsFinished(true);
                        Main.changeMenu("GameMap");

                    }
                }
            });

            showingPopUp(button);

            anchorPane.getChildren().add(unlockable.get(i));
            i++;
        }
        if (DatabaseController.getInstance().featuresThatCanBeDeletedInThisTerrain() != null) {
            unlockable.add(new Button());
            unlockable.get(i).setFont(Font.font("Copperplate", 15));
            unlockable.get(i).setTextFill(Color.RED);
            unlockable.get(i).setPrefSize(workersCoordinates.getPrefWidth(), workersCoordinates.getPrefHeight());
            unlockable.get(i).setText("Delete Feature : " + DatabaseController.getInstance().featuresThatCanBeDeletedInThisTerrain().name());
            unlockable.get(i).setVisible(true);
            unlockable.get(i).setLayoutX(workersCoordinates.getLayoutX());
            unlockable.get(i).setLayoutY(workersCoordinates.getLayoutY() + 50 * (i + 1) + 75);
            Button button = unlockable.get(i);
            unlockable.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        workersTerrain.setHasToBeDeleted(true);
                        workersTerrain.getTerrainImprovement().setHasToBeDeleted(false);
                        workersTerrain.getNonCombatUnit().setIsSelected(false);
                        workersTerrain.getNonCombatUnit().setIsFinished(true);
                        Main.changeMenu("GameMap");
                    }
                }
            });


            showingPopUp(button);

            anchorPane.getChildren().add(unlockable.get(i));
            i++;
        }

        if (DatabaseController.getInstance().improvementsThatCanBeRepairedInThisTerrain() != null) {
            unlockable.add(new Button());
            unlockable.get(i).setFont(Font.font("Copperplate", 15));
            unlockable.get(i).setTextFill(Color.RED);
            unlockable.get(i).setPrefSize(workersCoordinates.getPrefWidth(), workersCoordinates.getPrefHeight());
            unlockable.get(i).setText("Repair Improvement : " + DatabaseController.getInstance().improvementsThatCanBeRepairedInThisTerrain().name());
            unlockable.get(i).setVisible(true);
            unlockable.get(i).setLayoutX(workersCoordinates.getLayoutX());
            unlockable.get(i).setLayoutY(workersCoordinates.getLayoutY() + 50 * (i + 1) + 75);
            Button button = unlockable.get(i);
            unlockable.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        workersTerrain.getTerrainImprovement().setBeingRepaired(true);
                        workersTerrain.getTerrainImprovement().setPillaged(false);
                        workersTerrain.setHasToBeDeleted(false);
                        Main.changeMenu("GameMap");
                    }
                }
            });

            showingPopUp(button);

            anchorPane.getChildren().add(unlockable.get(i));
        }

    }


    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }

    public void showingPopUp(Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/WorkersOptionsPopUp.fxml"));
            Parent root = loader.load();
            WorkersOptionsPopUpController secController = loader.getController();
            secController.setData(button.getText());
            pressingMouseForMoreThanTwoSeconds(button, root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void pressingMouseForMoreThanTwoSeconds(Button button, Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        button.addEventFilter(MouseEvent.ANY, new EventHandler<>() {

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
}
