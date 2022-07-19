package com.example.civilization.FXMLcontrollers;

import javafx.scene.control.Label;

public class tooltipController {


    public Label result;

    public void setData(String result) {
        if (result.equalsIgnoreCase("number of players")) {
            this.result.setText("you can choose number of players here");
        } else if (result.equalsIgnoreCase("search name")) {
            this.result.setText("you can search your opponents username here");
        } else if (result.equalsIgnoreCase("new game")) {
            this.result.setText("you can start a new game with specified number of opponents");
        } else if (result.equalsIgnoreCase("load")) {
            this.result.setText("you can continue your saved games");
        }
    }
}
