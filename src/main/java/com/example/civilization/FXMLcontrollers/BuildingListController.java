package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Main;
import com.example.civilization.Model.Buildings.BuildingTypes;
import com.example.civilization.Model.City.City;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BuildingListController {

    public AnchorPane anchorPane;
    @FXML
    ArrayList<Button> canBeBuiltWithTurn = new ArrayList<>();
    @FXML
    ArrayList<Button> canBeBuiltWithGold = new ArrayList<>();

    public void setData(City city) throws FileNotFoundException {
        int i = 0;
        for (BuildingTypes buildingTypes : CityController.allPossibleToCreateBuildingsWithTurn(city)) {

            canBeBuiltWithTurn.add(new Button());
            canBeBuiltWithTurn.get(i).setFont(Font.font("Copperplate", 15));
            canBeBuiltWithTurn.get(i).setStyle("-fx-background-radius: 100em");
            canBeBuiltWithTurn.get(i).setTextFill(Color.RED);
            canBeBuiltWithTurn.get(i).setPrefSize(200, 5);
            canBeBuiltWithTurn.get(i).setText(buildingTypes.name());
            canBeBuiltWithTurn.get(i).setLayoutX(250);
            canBeBuiltWithTurn.get(i).setLayoutY(35 * (i + 1) + 75);
            Button button = canBeBuiltWithTurn.get(i);

            canBeBuiltWithTurn.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        CityController.createBuildingWithTurn(canBeBuiltWithGold.get(i).getText(), city);
                    }

                }

            });
            i++;
        }

        i = 0;
        for (BuildingTypes buildingTypes : CityController.allPossibleToCreateBuildingsWithGold(city)) {

            canBeBuiltWithGold.add(new Button());
            canBeBuiltWithGold.get(i).setFont(Font.font("Copperplate", 15));
            canBeBuiltWithGold.get(i).setStyle("-fx-background-radius: 100em");
            canBeBuiltWithGold.get(i).setTextFill(Color.RED);
            canBeBuiltWithGold.get(i).setPrefSize(200, 5);
            canBeBuiltWithGold.get(i).setText(buildingTypes.name());
            canBeBuiltWithGold.get(i).setLayoutX(1000);
            canBeBuiltWithGold.get(i).setLayoutY(35 * (i + 1) + 75);
            Button button = canBeBuiltWithGold.get(i);

            canBeBuiltWithGold.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {

                        CityController.createBuildingWithGold(canBeBuiltWithGold.get(i).getText(), city);
                    }

                }

            });
            i++;
        }

        anchorPane.getChildren().addAll(canBeBuiltWithGold);
        anchorPane.getChildren().addAll(canBeBuiltWithTurn);


    }


    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }
}
