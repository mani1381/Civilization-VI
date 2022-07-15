package com.example.civilization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static Scene scene;
    public static Stage stage;

    //public static void main(String[] args) {
    /*
    Scanner scanner = new Scanner(System.in);
    Database database = new Database();
    DatabaseController databaseController = new DatabaseController(database);
    LoginMenu loginMenu = new LoginMenu(databaseController);
    loginMenu.run(scanner);

     */
    //  launch();
    //}

    public static Parent loadFXML(String name) {
        try {
            URL address = new URL(Main.class.getResource("/com/example/civilization/FXML/" + name + ".fxml").toString());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeMenu(String Name) {
        Parent root = loadFXML(Name);

        Main.scene.setRoot(root);

    }

    @Override
    public void start(Stage stage) {


        Parent root = loadFXML("LoginMenu");
        Scene scene = new Scene(root);
        Main.scene = scene;
        stage.setScene(scene);
        stage.show();

    }

}