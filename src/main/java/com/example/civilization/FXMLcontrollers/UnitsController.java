package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Main;
import com.example.civilization.Model.City.City;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class UnitsController {
    @FXML
    private Label currentUnit;
    @FXML
    private TextField newUnit;
    private City city;
    @FXML
    private Label message;

    public void setData(City city) {
        this.city = city;

        if (city.isIConstructingUnit()) {
            currentUnit.setText(city.getConstructionWaitList().get(0).getUnitType().name());
        }

    }

    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }

    public void createNewUnit() {
        String unitName = newUnit.getText();
        CityController cityController = new CityController();
        String mes = cityController.createUnitWithTurn(unitName, city);
        message.setText(mes);
        message.setVisible(true);

    }


}
