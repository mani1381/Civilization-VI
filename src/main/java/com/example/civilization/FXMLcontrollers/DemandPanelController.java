package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Civilization;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.ArrayList;

public class DemandPanelController {

    @FXML
    public ArrayList<ChoiceBox> choiceBoxes = new ArrayList<>();
    public ArrayList<Pair<Label, Button>> civilizationsNameAndButtonRequest = new ArrayList<>();
    @FXML
    public AnchorPane anchorPane;
    @FXML
    Label resultText;
    @FXML
    ArrayList<TextField> choiceBoxesValues = new ArrayList<>();

    @FXML
    ArrayList<Pair<Button, Button>> acceptAndDecline = new ArrayList<>();

    @FXML
    ArrayList<Label> requests = new ArrayList<>();

    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);
        Platform.runLater(this::setRequestTexts);

    }

    public void setTexts() {
        int i = 0;
        for (Civilization civilization : DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().keySet()) {
            if (!DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getStatusWithOtherCivilizations().get(civilization)) {
                choiceBoxes.add(new ChoiceBox<>());
                civilizationsNameAndButtonRequest.add(new Pair<>(new Label(civilization.getName()), new Button("Send Request")));
                choiceBoxesValues.add(new TextField());
                setCivilizationNameAndButton(i, civilizationsNameAndButtonRequest);
                choiceBoxesValues.get(i).setStyle("-fx-background-radius: 100em");
                choiceBoxes.get(i).setStyle("-fx-background-radius: 100em");
                choiceBoxes.get(i).getItems().add("City");
                choiceBoxes.get(i).getItems().add("Gold");
            }


            Button button = civilizationsNameAndButtonRequest.get(i).getValue();
            int j = i;
            civilizationsNameAndButtonRequest.get(i).getValue().setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (isRequestValid(j)) {
                            resultText.setText("request sent");
                            String request = choiceBoxesValues.get(j).getText() + " " + choiceBoxes.get(j).getSelectionModel().getSelectedItem();
                            civilization.getDemandRequests().put(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization(), request);
                        } else {
                            resultText.setText("request is invalid");
                        }

                    }
                }
            });

            civilizationsNameAndButtonRequest.get(i).getKey().setLayoutX(300);
            civilizationsNameAndButtonRequest.get(i).getValue().setLayoutX(850);
            civilizationsNameAndButtonRequest.get(i).getKey().setLayoutY(100 + 65 * (i + 1));
            civilizationsNameAndButtonRequest.get(i).getValue().setLayoutY(100 + 65 * (i + 1));
            choiceBoxes.get(i).setLayoutX(150 + 400);
            choiceBoxes.get(i).setLayoutY(100 + 65 * (i + 1));
            choiceBoxesValues.get(i).setLayoutX(50 + 400);
            choiceBoxesValues.get(i).setLayoutY(100 + 65 * (i + 1));
            choiceBoxesValues.get(i).setPrefSize(75, 25);
            anchorPane.getChildren().addAll(choiceBoxes.get(i), civilizationsNameAndButtonRequest.get(i).getKey(), civilizationsNameAndButtonRequest.get(i).getValue(), choiceBoxesValues.get(i));
            i++;
        }
    }

    static void setCivilizationNameAndButton(int i, ArrayList<Pair<Label, Button>> civilizationsNameAndButtonRequest) {
        civilizationsNameAndButtonRequest.get(i).getKey().setFont(Font.font("Copperplate", 13));
        civilizationsNameAndButtonRequest.get(i).getValue().setFont(Font.font("Copperplate", 13));
        civilizationsNameAndButtonRequest.get(i).getKey().setTextFill(Color.RED);
        civilizationsNameAndButtonRequest.get(i).getValue().setTextFill(Color.RED);
        civilizationsNameAndButtonRequest.get(i).getValue().setStyle("-fx-background-radius: 100em");
    }

    public boolean isRequestValid(int i) {
        return choiceBoxes.get(i).getSelectionModel().getSelectedItem() != null && GameMenuController.isInteger(choiceBoxesValues.get(i).getText());
    }

    public void setRequestTexts() {
        int i = 0;
        for (Civilization civilization : DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getDemandRequests().keySet()) {
            requests.add(new Label(civilization.getName() + " " + DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getDemandRequests().get(civilization)));
            setRequestText(i, acceptAndDecline, requests, anchorPane);
            acceptAndDecline.get(i).getValue().setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getDemandRequests().remove(civilization);
                        Main.changeMenu("DemandPanel");

                    }
                }
            });
            acceptAndDecline.get(i).getKey().setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        demandCheck(requests.get(i).getText());
                        DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getDemandRequests().remove(civilization);
                    }
                }
            });

        }
    }

    static void setRequestText(int i, ArrayList<Pair<Button, Button>> acceptAndDecline, ArrayList<Label> requests, AnchorPane anchorPane) {
        acceptAndDecline.add(new Pair<>(new Button(), new Button()));
        acceptAndDecline.get(i).getKey().setStyle("-fx-background-color: green");
        acceptAndDecline.get(i).getValue().setStyle("-fx-background-color: red");
        requests.get(i).setLayoutX(1100);
        requests.get(i).setLayoutY(100 + 65 * (i + 1));
        requests.get(i).setTextFill(Color.RED);
        requests.get(i).setFont(Font.font("Copperplate", 11));
        acceptAndDecline.get(i).getKey().setPrefSize(30, 30);
        acceptAndDecline.get(i).getValue().setPrefSize(30, 30);
        acceptAndDecline.get(i).getKey().setLayoutX(1300);
        acceptAndDecline.get(i).getValue().setLayoutX(1350);
        acceptAndDecline.get(i).getKey().setLayoutY(100 + 65 * (i + 1));
        acceptAndDecline.get(i).getValue().setLayoutY(100 + 65 * (i + 1));
        anchorPane.getChildren().addAll(requests.get(i), acceptAndDecline.get(i).getKey(), acceptAndDecline.get(i).getValue());
    }

    public void demandCheck(String request) {
        String[] parts = request.split(" ");
        Civilization first = DatabaseController.getInstance().getCivilizationByName(parts[0]);
        int value = Integer.parseInt(parts[1]);
        Civilization second = DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization();
        if (parts[2].equalsIgnoreCase("gold")) {
            if (second.getGold() >= value) {
                second.setGold(second.getGold() - value);
                first.setGold(first.getGold() + value);
                second.getDemandRequests().remove(first);
                Main.changeMenu("DemandPanel");
            }

        } else if (parts[2].equalsIgnoreCase("city")) {
            if (second.getCities().size() >= value + 1) {
                ArrayList<City> cities = TradePanelController.getCitiesFromCivilization(second,value);
                for (City city : cities) {
                    first.addCity(city);
                    second.getCities().remove(city);
                    city.setOwner(first);
                }
                second.getDemandRequests().remove(first);
                Main.changeMenu("DemandPanel");
            }

        }
        resultText.setText("Demand is Invalid");
    }

    public void backToGameMap() {
        Main.changeMenu("DiplomacyPanel");
    }


}
