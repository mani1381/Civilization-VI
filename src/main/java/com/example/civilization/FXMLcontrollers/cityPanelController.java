package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.CityController;
import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.City.Citizen;
import com.example.civilization.Model.City.City;
import com.example.civilization.Model.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;

public class cityPanelController {
    @FXML
    private Button buyButton;
    @FXML
    private Label buildings;
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
            if ( !city.getBuildings().isEmpty())
            {
                buildings.setText( city.getBuildings().get(0).getBuildingType().name());
                if ( city.getBuildings().get(1) != null)
                {
                    buildings.setText(city.getBuildings().get(0).getBuildingType().name() + city.getBuildings().get(1).getBuildingType().name());
                }
            }
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

    public void goToBuilding(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/BuildingList.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BuildingListController secController = loader.getController();
        try {
            secController.setData(city);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Main.scene.setRoot(root);
    }
    public void goToUnits( )
    {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/Units.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UnitsController secController = loader.getController();
        secController.setData(city);
        Main.scene.setRoot(root);

    }

    public void goToMap(MouseEvent mouseEvent) {
        Main.changeMenu("gameMap");
    }
}
