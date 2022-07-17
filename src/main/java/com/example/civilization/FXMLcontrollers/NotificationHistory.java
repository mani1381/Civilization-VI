package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NotificationHistory {

    public Label result;
    @FXML
    AnchorPane anchorPane;


    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);

    }

    public void setTexts() {
        result.setText(DatabaseController.getInstance().notificationHistory(DatabaseController.getInstance().getDatabase().getActiveUser()));
    }

    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }
}
