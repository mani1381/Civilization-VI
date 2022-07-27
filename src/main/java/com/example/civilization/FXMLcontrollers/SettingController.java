package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

public class SettingController {
    @FXML
    public Button autoSave;


    @FXML
    public void initialize() {
        if (DatabaseController.getInstance().getDatabase().isAutoSaveOn()) {
            autoSave.setText("Auto save: on");
        } else {
            autoSave.setText("Auto save: off");
        }
        Platform.runLater(this::setTexts);
    }

    public void setTexts() {
        autoSave.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    if (DatabaseController.getInstance().getDatabase().isAutoSaveOn()) {
                        autoSave.setText("Auto save: off");
                        DatabaseController.getInstance().getDatabase().setAutoSaveOn(!DatabaseController.getInstance().getDatabase().isAutoSaveOn());
                    } else {
                        autoSave.setText("Auto save: on");
                        DatabaseController.getInstance().getDatabase().setAutoSaveOn(!DatabaseController.getInstance().getDatabase().isAutoSaveOn());
                    }
                }
            }
        });
    }

    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }
}
