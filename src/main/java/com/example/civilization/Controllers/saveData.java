package com.example.civilization.Controllers;

import com.example.civilization.Model.Database;
import com.example.civilization.Model.User;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class saveData{

    public void saveUsers(Database database){
        try {
            FileWriter Writer = new FileWriter("src/main/resources/Users.json");
            Writer.write(new Gson().toJson(database.getUsers()));
            Writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadUsers(Database database){
        try {
            String Users = new String(Files.readAllBytes(Paths.get("src/main/resources/com/example/civilization/Users.json")));
            database.setUsers(new Gson().fromJson(Users,new TypeToken<List<User>>() {
            }.getType()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}