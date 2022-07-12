package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Enums.GameEnums;
import com.example.civilization.Main;
import com.example.civilization.Model.User;
import com.example.civilization.View.GameMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.regex.Matcher;

public class CheatCodeControllers {

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label resultText;


    @FXML
    public void initialize() {

        Platform.runLater(this::setCheatResult);

    }

    public void setCheatResult() {
        resultText.setPrefSize(470, 41);
        resultText.setFont(Font.font("Copperplate", 15));
        resultText.setLayoutX(450);
        resultText.setLayoutY(250);
        for (Node children : anchorPane.getChildren()) {

            if (children instanceof TextField) {
                ((TextField) children).setPrefSize(470, 41);
                children.setLayoutX(450);
                children.setLayoutY(200);
                children.setOnKeyPressed(e -> {
                    if (e.getCode().equals(KeyCode.ENTER)) {
                        String input = ((TextField) children).getText();
                        Matcher matcher;
                        User user = DatabaseController.getInstance().getDatabase().getActiveUser();
                        if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_TURN)) != null) {
                            resultText.setText(GameMenu.increaseTurnCheat(matcher));
                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_GOLD)) != null) {
                            resultText.setText(GameMenu.increaseGoldCheat(user, matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.COMBAT_UNIT_CHEAT_MOVE)) != null) {
                            resultText.setText(GameMenu.cheatMoveCombatUnit(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.NON_COMBAT_UNIT_CHEAT_MOVE)) != null) {
                            resultText.setText(GameMenu.cheatMoveNonCombatUnit(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_HAPPINESS)) != null) {
                            resultText.setText(GameMenu.increaseHappinessCheat(user, matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.INCREASE_SCIENCE)) != null) {
                            resultText.setText(GameMenu.increaseScienceCheat(user, matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.BUY_TECHNOLOGY)) != null) {
                            resultText.setText(GameMenu.buyTechnologyCheat(matcher, user));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.BUY_CHEAT_TILE)) != null) {
                            resultText.setText(GameMenu.buyCheatTile(user, matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_UNIT)) != null) {
                            resultText.setText(GameMenu.setCheatUnit(user, matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_IMPROVEMENT)) != null) {
                            resultText.setText(GameMenu.setCheatImprovement(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_RESOURCE)) != null) {
                            resultText.setText(GameMenu.setCheatResource(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_TERRAIN_FEATURE_TYPE)) != null) {
                            resultText.setText(GameMenu.setCheatTerrainFeature(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.SET_CHEAT_TERRAIN_TYPE)) != null) {
                            resultText.setText(GameMenu.setCheatTerrainType(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.DELETE_CHEAT_IMPROVEMENT)) != null) {
                            resultText.setText(GameMenu.deleteImprovementCheat(matcher));

                        } else if ((matcher = GameEnums.getMatcher(input, GameEnums.REPAIR_CHEAT_IMPROVEMENT)) != null) {
                            resultText.setText(GameMenu.repairImprovementCheat(matcher));

                        } else {
                            resultText.setText("Invalid Input");
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
