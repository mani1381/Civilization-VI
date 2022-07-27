package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Civilization;
import com.example.civilization.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinningPopupController {
    DatabaseController databaseController = DatabaseController.getInstance();
    CityController cityController = new CityController();
    private City city;
    @FXML
    private Label message;

    public void setData(City city) {
        this.city = city;
    }

    public void attachCity() {
        User user = databaseController.getDatabase().getActiveUser();
        Civilization civilization = user.getCivilization();
        cityController.attachCity(civilization, city);
        message.setVisible(true);
        message.setText("City attached successfully");

    }

    public void destroyCity() {
        User user = databaseController.getDatabase().getActiveUser();
        Civilization civilization = user.getCivilization();
        cityController.destroyCity(civilization, city.getOwner(), city);
        message.setVisible(true);
        message.setText("City destroyed successfully");

    }
}
