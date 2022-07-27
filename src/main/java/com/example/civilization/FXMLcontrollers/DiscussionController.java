package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class DiscussionController {

    static int i = 0;
    public TextField message;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ArrayList<Label> messages = new ArrayList<>();

    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);

    }

    public void setTexts() {

        message.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                int j = i;
                messages.add(new Label());
                messages.get(j).setAlignment(Pos.CENTER);
                messages.get(j).setStyle("-fx-background-radius: 100em;" + "-fx-background-color: White;");
                messages.get(j).setFont(Font.font("Copperplate", 18));
                messages.get(j).setPrefSize(400, 30);
                if (j % 2 == 0) {
                    messages.get(j).setLayoutX(325);
                } else {
                    messages.get(j).setLayoutX(700);
                }

                messages.get(j).setLayoutY(100 + 85 * (j + 1));

                messages.get(j).setText(message.getText());
                message.setText("");
                anchorPane.getChildren().add(messages.get(j));
                i++;
            }

        });


    }


    public void backToMap() {
        Main.changeMenu("DiplomacyPanel");
    }
}
