package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Units.Unit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class UnitsPanel {

    public Label result;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ArrayList<Button> units = new ArrayList<>();


    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);

    }

    public void setTexts() {
        int i = 0;
        int j = -1;
        for (Unit unit : DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getUnits()) {
            if (i % 9 == 0) {
                j++;
                i = 0;
            }
            units.add(new Button());
            units.get(i + j * 9).setFont(Font.font("Copperplate", 15));
            units.get(i + j * 9).setTextFill(Color.RED);
            units.get(i + j * 9).setPrefSize(250, 50);
            units.get(i + j * 9).setText(unit.getUnitType().name() + " X:" + unit.getX() + " Y:" + unit.getY());
            units.get(i + j * 9).setVisible(true);
            units.get(i + j * 9).setLayoutX(150 + j * 350);
            units.get(i + j * 9).setLayoutY(75 + 65 * (i + 1));
            units.get(i + j * 9).setStyle("-fx-background-radius: 100em");
            Button button = units.get(i + j * 9);
            units.get(i + j * 9).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        setAllRedColor(units);
                        button.setTextFill(Color.BLUE);
                        String[] parts = button.getText().split(" ");
                        DatabaseController.getInstance().activateUnit(DatabaseController.getInstance().getDatabase().getActiveUser(), parts[0], Integer.parseInt(parts[1].substring(2)), Integer.parseInt(parts[2].substring(2)));
                        result.setText(parts[0] + " unit activated");
                        result.setAlignment(Pos.CENTER);


                    }
                }
            });

            anchorPane.getChildren().add(units.get(i + j * 9));
            i++;
        }

    }

    private void setAllRedColor(ArrayList<Button> buttons) {
        for (Button children : buttons) {
            children.setTextFill(Color.RED);
        }

    }


    public void backToMap() {
        Main.changeMenu("gameMap");
    }

    public void goToMilitaryOverview() {
        Main.changeMenu("MilitaryOverview");
    }
}
