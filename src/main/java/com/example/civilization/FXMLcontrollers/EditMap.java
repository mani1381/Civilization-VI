package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.EditMapController;
import com.example.civilization.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditMap {

    public TextField iIndex;
    public TextField jIndex;
    private EditMapController editMap = EditMapController.getInstance();
    @FXML
    private MenuItem bananas;
    @FXML
    private MenuItem cattle;
    @FXML
    private MenuItem deer;
    @FXML
    private MenuItem sheep;
    @FXML
    private MenuItem wheat;
    @FXML
    private MenuItem coal;
    @FXML
    private MenuItem horse;
    @FXML
    private MenuItem iron;
    @FXML
    private MenuItem cotton;
    @FXML
    private MenuItem fur;
    @FXML
    private MenuItem dyes;
    @FXML
    private MenuItem gems;
    @FXML
    private MenuItem noResource;

    @FXML
    private MenuItem gold;
    @FXML
    private MenuItem incense;
    @FXML
    private MenuItem ivory;
    @FXML
    private MenuItem marble;
    @FXML
    private MenuItem silk;
    @FXML
    private MenuItem silver;
    @FXML
    private MenuItem sugar;
    @FXML
    private CheckBox up_up;
    @FXML
    private CheckBox up_Left;
    @FXML
    private CheckBox up_Right;
    @FXML
    private CheckBox down_Left;
    @FXML
    private CheckBox down_right;
    @FXML
    private CheckBox down_down;

    @FXML
    private MenuButton TerrainResource;
    @FXML
    private MenuItem floodplains;
    @FXML
    private MenuItem forest;
    @FXML
    private MenuItem ice;
    @FXML
    private MenuItem jungle;
    @FXML
    private MenuItem marsh;
    @FXML
    private MenuItem oasis;
    @FXML
    private MenuItem NoFeature;
    @FXML
    private MenuButton TerrainFeature;
    @FXML
    private MenuItem desert;
    @FXML
    private MenuItem grassland;
    @FXML
    private MenuItem hill;
    @FXML
    private MenuItem mountain;
    @FXML
    private MenuItem ocean;
    @FXML
    private MenuItem plains;
    @FXML
    private MenuItem snow;
    @FXML
    private MenuItem tundra;

    @FXML
    private MenuButton TerrainType;


    public void initialize() {
        MenuItem[] terrainType = {desert, grassland, hill, mountain, ocean, plains, snow, tundra};
        for (MenuItem item : terrainType) {
            item.setOnAction(actionEvent -> TerrainType.setText(item.getText()));
        }
        MenuItem[] terrainFeature = {floodplains, forest, ice, jungle, marsh, oasis, NoFeature};
        for (MenuItem item : terrainFeature) {
            item.setOnAction(actionEvent -> TerrainFeature.setText(item.getText()));
        }
        MenuItem[] terrainResource = {bananas, cattle, deer, sheep, wheat, coal, horse, iron, cotton, fur, dyes, gems, gold, incense, ivory, marble, silk, silver, sugar, noResource};
        for (MenuItem item : terrainResource) {
            item.setOnAction(actionEvent -> TerrainResource.setText(item.getText()));
        }
    }


    public void submit() {
        String iCoordinate = iIndex.getText();
        String jCoordinate = jIndex.getText();
        if (iCoordinate.length() == 0 || jCoordinate.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("empty index");
            alert.show();
        } else if (!editMap.haveDigits(iCoordinate) || !editMap.haveDigits(jCoordinate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("wrong index");
            alert.show();
        } else {
            int i = Integer.parseInt(iCoordinate);
            int j = Integer.parseInt(jCoordinate);
            if (i > 31 || j > 15) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("wrong index");
                alert.show();
            } else {
                editMap.changeCheckBoxState(up_up, up_Left, up_Right, down_down, down_Left, down_right);
                editMap.setTileInMap(i, j, TerrainType.getText(), TerrainFeature.getText(), TerrainResource.getText());
                iIndex.clear();
                jIndex.clear();
            }
        }


    }

    public void finished() {
        Main.changeMenu("GameMap");
    }

    public void back() {
        Main.changeMenu("ProfileMenu");
    }
}
