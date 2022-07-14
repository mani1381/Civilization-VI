package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.Buildings.BuildingTypes;
import com.example.civilization.Model.Improvements.ImprovementTypes;
import com.example.civilization.Model.Resources.ResourceTypes;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Units.UnitTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TechnologyPopUpController {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label unlockLabel;

    @FXML
    ArrayList<Label> unlocks = new ArrayList<>();


    public void setData(TechnologyTypes technologyTypes) throws FileNotFoundException {
        int i = 0;
        for (TechnologyTypes technologyTypes1 : DatabaseController.getInstance().unlockTechnologies(technologyTypes)) {
            unlocks.add(new Label());
            unlocks.get(i).setFont(Font.font("Copperplate", 15));
            unlocks.get(i).setTextFill(Color.RED);

            unlocks.get(i).setPrefSize(unlockLabel.getPrefWidth() + 100, unlockLabel.getPrefHeight());
            unlocks.get(i).setText("Technology : " + technologyTypes1.name());
            unlocks.get(i).setVisible(true);
            unlocks.get(i).setLayoutX(unlockLabel.getLayoutX());
            unlocks.get(i).setLayoutY(unlockLabel.getLayoutY() + 50 * (i + 1));
            anchorPane.getChildren().add(unlocks.get(i));
            i++;
        }

        for (Object objects : technologyTypes.getUnlocks()) {

            unlocks.add(new Label());
            unlocks.get(i).setFont(Font.font("Copperplate", 15));
            unlocks.get(i).setTextFill(Color.RED);
            unlocks.get(i).setPrefSize(unlockLabel.getPrefWidth() + 100, unlockLabel.getPrefHeight());
            if (objects instanceof ImprovementTypes) {
                unlocks.get(i).setText("Improvement : " + ((ImprovementTypes) objects).name());
            } else if (objects instanceof BuildingTypes) {
                unlocks.get(i).setText("Building : " + ((BuildingTypes) objects).name());
            } else if (objects instanceof ResourceTypes) {
                unlocks.get(i).setText("Resource : " + ((ResourceTypes) objects).name());
            } else if (objects instanceof UnitTypes) {
                unlocks.get(i).setText("Unit : " + ((UnitTypes) objects).name());
            }

            unlocks.get(i).setVisible(true);
            unlocks.get(i).setLayoutX(unlockLabel.getLayoutX());
            unlocks.get(i).setLayoutY(unlockLabel.getLayoutY() + 50 * (i + 1));
            anchorPane.getChildren().add(unlocks.get(i));
            i++;
        }
    }
}
