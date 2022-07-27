package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Civilization;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class DiplomacyPanelController {

    public Label result;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ArrayList<Button> civilizations = new ArrayList<>();
    @FXML
    ArrayList<Button> civilizationsDiscussion = new ArrayList<>();


    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);

    }

    public void setTexts() {
        int i = 0;
        for (Civilization civilization : DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().keySet()) {
            civilizationsDiscussion.add(new Button());
            civilizationsDiscussion.get(i).setStyle("-fx-background-radius: 100em");
            civilizationsDiscussion.get(i).setFont(Font.font("Copperplate", 13));
            civilizationsDiscussion.get(i).setTextFill(Color.RED);
            civilizationsDiscussion.get(i).setPrefSize(100, 50);
            civilizationsDiscussion.get(i).setText("Discussion");
            civilizations.add(new Button());
            civilizations.get(i).setStyle("-fx-background-radius: 100em");
            civilizations.get(i).setFont(Font.font("Copperplate", 13));
            civilizations.get(i).setTextFill(Color.RED);
            civilizations.get(i).setPrefSize(350, 50);
            if (DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().get(civilization).equals(false)) {
                civilizations.get(i).setText(civilization.getScore() + " " + civilization.getName() + " Peace");
            } else {
                civilizations.get(i).setText(civilization.getScore() + " " + civilization.getName() + " War");
            }
            Button button = civilizations.get(i);
            Button button1 = civilizationsDiscussion.get(i);
            civilizations.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String[] parts = button.getText().split(" ");
                        Civilization civilization1 = DatabaseController.getInstance().getCivilizationByName(parts[1]);
                        DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().put(civilization1, !DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().get(civilization1));
                        civilization1.getStatusWithOtherCivilizations().put(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization(), !civilization1.getStatusWithOtherCivilizations().get(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization()));
                        if (DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().get(civilization).equals(false)) {
                            button.setText(civilization.getScore() + " " + civilization.getName() + " Peace");
                        } else {
                            button.setText(civilization.getScore() + " " + civilization.getName() + " War");
                        }
                    }
                }
            });
            civilizationsDiscussion.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Main.changeMenu("Discussion");
                    }
                }
            });

            civilizationsDiscussion.get(i).setVisible(true);
            civilizationsDiscussion.get(i).setLayoutX(1000);
            civilizationsDiscussion.get(i).setLayoutY(100 + 65 * (i + 1));

            civilizations.get(i).setVisible(true);
            civilizations.get(i).setLayoutX(150 + 400);
            civilizations.get(i).setLayoutY(100 + 65 * (i + 1));
            anchorPane.getChildren().add(civilizations.get(i));
            anchorPane.getChildren().add(civilizationsDiscussion.get(i));
            i++;
        }

    }


    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }

    public void goToTradePanel() {
        Main.changeMenu("TradePanel");
    }

    public void goToDemandPanel() {
        Main.changeMenu("DemandPanel");
    }
}
