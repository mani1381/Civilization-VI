package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Database;
import com.example.civilization.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginMenuController {
    private DatabaseController databaseController = DatabaseController.getInstance();
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label error;
    @FXML
    private TextField signupUsername;
    @FXML
    private TextField signupPassword;
    @FXML
    private TextField signupNickname;
    @FXML
    private Label warning;


    public void moveToLogin() {
        Main.changeMenu("Login");

    }

    public void moveToSignUp() {
        Main.changeMenu("SignUp");

    }

    public void login() {
        String u = username.getText();
        String p = password.getText();
        User user = databaseController.userLogin(u, p);
        if (user != null) {
            Database.getInstance().setActiveUser(user);
            ProfileMenuController.databaseController = DatabaseController.getInstance();
            Main.changeMenu("ProfileMenu");
            return;

        }
        error.setVisible(true);
        username.setText("");
        password.setText("");


    }

    public void signUp() {
        String u = signupUsername.getText();
        String p = signupPassword.getText();
        String n = signupNickname.getText();
        String temp = databaseController.createUser(u, p, n);
        if (temp.equals("user created successfully!")) {
            Main.changeMenu("Login");
        } else {
            warning.setText(temp);
            warning.setVisible(true);
            signupNickname.setText("");
            signupPassword.setText("");
            signupUsername.setText("");
        }

    }

    public void back() {
        Main.changeMenu("LoginMenu");
    }


}
