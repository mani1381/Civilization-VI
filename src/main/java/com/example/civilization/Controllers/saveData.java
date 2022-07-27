package com.example.civilization.Controllers;

import com.example.civilization.Model.Database;
import com.example.civilization.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class saveData {

    private static saveData instance;
    private Database database;

    public saveData() {
        this.database = Database.getInstance();
    }

    public static saveData getInstance() {
        if (instance == null) {
            instance = new saveData();
        }
        return instance;
    }

    public void saveUsers() {
//        try {
//            FileWriter Writer = new FileWriter("src/main/resources/Users.json");
//            Writer.write(new Gson().toJson(this.database.getAllUsers()));
//            Writer.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void loadUsers() {

        String Users = null;
        try {
            Users = new String(Files.readAllBytes(Paths.get("src/main/resources/Users.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.database.setAllUsers(new Gson().fromJson(Users, new TypeToken<List<User>>() {
        }.getType()));

    }
}