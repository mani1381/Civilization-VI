package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Units.Unit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class MilitaryOverview {

    public Label result;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ArrayList<Label> units = new ArrayList<>();


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
            units.add(new Label());
            units.get(i + j * 9).setFont(Font.font("Copperplate", 11));
            units.get(i + j * 9).setTextFill(Color.RED);
            units.get(i + j * 9).setPrefSize(350, 50);
            units.get(i + j * 9).setText(unit.getUnitType().name() + " X:" + unit.getX() + " Y:" + unit.getY() + " HP:" + unit.getHP() + " CS:" + unit.getUnitType().getCombatStrengh() + " combatType: " + unit.getUnitType().getCombatTypes().name());
            units.get(i + j * 9).setVisible(true);
            units.get(i + j * 9).setLayoutX(150 + j * 450);
            units.get(i + j * 9).setLayoutY(75 + 65 * (i + 1));

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

}