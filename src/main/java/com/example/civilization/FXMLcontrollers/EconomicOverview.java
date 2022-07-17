package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.City.City;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class EconomicOverview{

    @FXML
    AnchorPane anchorPane;
    @FXML
    ArrayList<Button> cities = new ArrayList<>();


    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);

    }

    public void setTexts() {
        int i = 0;
        int j = -1;
        for (City city : DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getCities()) {
            if (i % 5 == 0) {
                j++;
                i = 0;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Central Terrain X:").append(city.getCentralTerrain().getX()).append(" Central Terrain Y:").append(city.getCentralTerrain().getY()).append("\n");
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

            cities.add(new Button());
            cities.get(i + j * 5).setFont(Font.font("Copperplate", 9));
            cities.get(i + j * 5).setTextFill(Color.RED);
            cities.get(i + j * 5).setPrefSize(300, 100);
            cities.get(i + j * 5).setText(stringBuilder.toString());
            cities.get(i + j * 5).setVisible(true);
            cities.get(i + j * 5).setLayoutX(150 + j * 350);
            cities.get(i + j * 5).setLayoutY(50 + 115 * (i + 1));
            cities.get(i + j * 5).setStyle("-fx-background-radius: 100em");
            Button button = cities.get(i + j * 5);
            cities.get(i + j * 5).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String[] parts = button.getText().split(" ");

                        // Todo go to city panel

                    }
                }
            });

            anchorPane.getChildren().add(cities.get(i + j * 5));
            i++;
        }

    }

    public void backToMap() {
        Main.changeMenu("gameMap");
    }

}
