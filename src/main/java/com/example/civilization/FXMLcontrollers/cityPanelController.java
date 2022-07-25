package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Model.City.Citizen;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Terrain;
import com.example.civilization.View.GameMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.EventListener;

public class cityPanelController {
    @FXML
    private Button buyButton;
    @FXML
    private TextField x;
    @FXML
    private TextField y;
    @FXML
    private Label buyMessage;
    @FXML
    private Label foodNumber;
    @FXML
    private Label populationNumber;
    @FXML
    private Label goldNumber;
    @FXML
    private Label numberOfUnemployed;
    private City city;
    @FXML
    private Button goButton;
    @FXML
    private TextField workersX;
    @FXML
    private TextField workersY;
    @FXML
    private Label numberOfWorkers;
    private CityController cityController = new CityController();
    private DatabaseController databaseController = DatabaseController.getInstance();
    private Terrain workingTerrain;
    public void buyTile()
    {
        int X = Integer.parseInt(x.getText());
        int Y = Integer.parseInt(y.getText());
        buyMessage.setText(cityController.buyTile(X, Y, city));
        buyMessage.setVisible(true);
        goldNumber.setText(String.valueOf(city.getGold()));

    }

    public boolean setData(Terrain terrain)
    {
        if ( terrain.getCity() == null) return false;
        else
        {
            city = terrain.getCity();
            foodNumber.setVisible(true);
            populationNumber.setVisible(true);
            goldNumber.setVisible(true);
            foodNumber.setText(String.valueOf(city.getFood()));
            populationNumber.setText(String.valueOf(city.getPopulation()));
            goldNumber.setText(String.valueOf(city.getGold()));
            numberOfUnemployed.setVisible(true);
            numberOfUnemployed.setText( String.valueOf(city.getNumberOfUnemployed()));
            return true;
        }
    }
    public boolean isClickingOnBack (MouseEvent mouseEvent)
    {
        Double mouseX = mouseEvent.getX();
        Double mouseY = mouseEvent.getX();
        if ( mouseX > 11 && mouseX < 56 && mouseY > 14 && mouseY <56) return true;
        return false;

    }

    public void go ()
    {
        int x = Integer.parseInt(workersX.getText());
        int y = Integer.parseInt(workersY.getText());
        Terrain terrain = databaseController.getTerrainByCoordinates(x, y);
        workingTerrain = terrain;
        int n = 0;
        for (Citizen citizen: city.getCitizens())
        {
            if ( citizen.getTerrain().equals(terrain)) n++;
        }
        numberOfWorkers.setText(String.valueOf(n));

    }



    public void up()
    {
        if ( workingTerrain != null)
        {
            if ( Integer.parseInt(numberOfUnemployed.getText()) > 0)
            {
                Citizen citizen = city.getTheFirstUnemployed();
                if ( citizen != null )
                {
                    citizen.assignWork(workingTerrain);
                    numberOfUnemployed.setText(String.valueOf(city.getNumberOfUnemployed()));
                    numberOfWorkers.setText( numberOfWorkers.getText() + 1);
                }
            }
        }

    }
}
