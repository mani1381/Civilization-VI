package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Model.Ruins;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import com.example.civilization.Model.Units.UnitTypes;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ruinsNotifyController {

    @FXML
    private Label gold = new Label();
    @FXML
    private Label technology = new Label();
    @FXML
    private Label nonCombatUnit = new Label();

    @FXML
    private ImageView goldImage = new ImageView();
    @FXML
    private ImageView unitImage = new ImageView();
    @FXML
    private ImageView technologyImage = new ImageView();

    public void setData(Ruins ruins) {

        if (ruins.getCity() != null) {
            gold.setText(ruins.getRuinsGold() + "gold added to " + ruins.getCivilization().getName() + " civilization and the city with following coordinates : x :" + ruins.getCity().getCentralTerrain().getX() + " y : " + ruins.getCity().getCentralTerrain().getY());
        }

        if (ruins.getTechnologyRuins() != null) {
            technology.setText(ruins.getTechnologyRuins().getTechnologyType().name().charAt(0) + ruins.getTechnologyRuins().getTechnologyType().name().substring(1).toLowerCase() + " technology added to " + ruins.getCivilization().getName() + " civilization");
        }

        if (ruins.getNonCombatUnit() != null) {
            if (ruins.getNonCombatUnit().getUnitType().equals(UnitTypes.WORKER)) {
                nonCombatUnit.setText("A worker unit added to " + ruins.getCivilization().getName() + " civilization");

            } else if (ruins.getNonCombatUnit().getUnitType().equals(UnitTypes.SETTLER)) {
                nonCombatUnit.setText("A settler unit added to " + ruins.getCivilization().getName() + " civilization");
            }

            try {
                unitImage.setImage(new Image(new FileInputStream(getImagePatternOfTiles(ruins.getNonCombatUnit().getUnitType().name()))));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String getImagePatternOfTiles(String name) throws FileNotFoundException {

        for (UnitTypes unitTypes : UnitTypes.values()) {
            if (unitTypes.name().equalsIgnoreCase(name)) {
                return "src/main/resources/com/example/civilization/PNG/civAsset/units/Units/" + name + ".png";
            }
        }

        for (TechnologyTypes technologyTypes : TechnologyTypes.values()) {
            if (technologyTypes.name().equalsIgnoreCase(name)) {
                // return " src/main/resources/com/example/civilization/PNG/civAsset/units/Units/" + name + ".png";
            }
        }

        return null;
    }


}
