package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;

public class ChooseResearchController {
    @FXML
    AnchorPane anchorPane;

    @FXML
    Label justCompleted = new Label();

    @FXML
    ArrayList<Button> unlockable = new ArrayList<>();


    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);


    }

    public void setTexts() {
        if (DatabaseController.getInstance().lastUnlockedTechnology() == null) {
            justCompleted.setText("You have not unlocked any technology yet!");
        } else {
            justCompleted.setText("Just Completed : " + DatabaseController.getInstance().lastUnlockedTechnology().name());
        }
        int i = 0;
        for (TechnologyTypes technologyTypes1 : DatabaseController.getInstance().unlockableTechnologies(DatabaseController.getInstance().getDatabase().getActiveUser())) {
            unlockable.add(new Button());
            unlockable.get(i).setFont(Font.font("Copperplate", 15));
            unlockable.get(i).setTextFill(Color.RED);
            unlockable.get(i).setPrefSize(justCompleted.getPrefWidth(), justCompleted.getPrefHeight());
            unlockable.get(i).setText(technologyTypes1.name());
            unlockable.get(i).setVisible(true);
            unlockable.get(i).setLayoutX(justCompleted.getLayoutX());
            unlockable.get(i).setLayoutY(justCompleted.getLayoutY() + 50 * (i + 1) + 75);
            String name = unlockable.get(i).getText();
            Button button = unlockable.get(i);
            unlockable.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        DatabaseController.getInstance().choosingATechnologyToStudyForGraphic(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(name));
                        setColor(unlockable);
                    }
                }
            });
            showingPopUp(DatabaseController.getInstance().getTechnologyTypeByName(button.getText()), button);

            anchorPane.getChildren().add(unlockable.get(i));
            i++;
        }

    }


    public void goToTechnologyTree() {
        Main.changeMenu("TechnologyTree");
    }

    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }

    private void setColor(ArrayList<Button> buttons) {
        for (Button children : buttons) {
            if (DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(children.getText())) != null && !DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(children.getText())).getUnderResearch()) {
                children.setTextFill(Color.RED);
            } else if (DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(children.getText())) != null && DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(children.getText())).getUnderResearch()) {
                children.setTextFill(Color.BLUE);
            }
        }

    }

    public void showingPopUp(TechnologyTypes technologyTypes, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/TechnologyPopUp.fxml"));
            Parent root = loader.load();
            TechnologyPopUpController secController = loader.getController();
            secController.setData(technologyTypes);
            WorkersOptionsController.pressingMouseForMoreThanTwoSeconds(button, root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
