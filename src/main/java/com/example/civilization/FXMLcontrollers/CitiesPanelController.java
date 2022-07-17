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

public class CitiesPanelController {

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
            if (i % 9 == 0) {
                j++;
                i = 0;
            }
            cities.add(new Button());
            cities.get(i + j * 9).setFont(Font.font("Copperplate", 15));
            cities.get(i + j * 9).setTextFill(Color.RED);
            cities.get(i + j * 9).setPrefSize(450, 50);
            cities.get(i + j * 9).setText("Central terrain X:" + city.getCentralTerrain().getX() + " Central terrain Y:" + city.getCentralTerrain().getY());
            cities.get(i + j * 9).setVisible(true);
            cities.get(i + j * 9).setLayoutX(150 + j * 500);
            cities.get(i + j * 9).setLayoutY(75 + 65 * (i + 1));
            cities.get(i + j * 9).setStyle("-fx-background-radius: 100em");
            Button button = cities.get(i + j * 9);
            cities.get(i + j * 9).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String[] parts = button.getText().split(" ");
                        DatabaseController.getInstance().activateUnit(DatabaseController.getInstance().getDatabase().getActiveUser(), parts[0], Integer.parseInt(parts[1].substring(2)), Integer.parseInt(parts[2].substring(2)));
                        // Todo go to city panel

                    }
                }
            });

            anchorPane.getChildren().add(cities.get(i + j * 9));
            i++;
        }

    }

    public void backToMap() {
        Main.changeMenu("gameMap");
    }

    public void goToEconomicOverview() {
        Main.changeMenu("EconomicOverview");
    }
}
