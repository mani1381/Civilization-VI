package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Units.CombatUnit;
import com.example.civilization.Model.Units.NonCombatUnit;
import com.example.civilization.Model.Units.RangedCombatUnit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

public class UnitActionsController {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label result;


    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);


    }

    public void setTexts() {
        CombatUnit combatUnit = DatabaseController.getInstance().getSelectedCombatUnit();
        NonCombatUnit nonCombatUnit = DatabaseController.getInstance().getSelectedNonCombatUnit();
        for (Node children : anchorPane.getChildren()) {
            if (children instanceof Button) {
                String name = ((Button) children).getText();
                if ((name.equalsIgnoreCase("fortify") || name.equalsIgnoreCase("alert") || name.equalsIgnoreCase("fortify until heal") || name.equalsIgnoreCase("garrison")) && combatUnit == null) {
                    children.setVisible(false);
                }
                if (name.equalsIgnoreCase("setup ranged")) {
                    if (!(combatUnit instanceof RangedCombatUnit)) {
                        children.setVisible(false);
                    }
                }
                children.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            result.setText(DatabaseController.getInstance().changingTheStateOfAUnit(((Button) children).getText()));
                            Main.changeMenu("gameMap");
                        }
                    }


                });

            }
        }

    }

    public void backToMap() {
        Main.changeMenu("gameMap");
    }
}
