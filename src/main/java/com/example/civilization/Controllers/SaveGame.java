package com.example.civilization.Controllers;

import com.example.civilization.Model.Database;
import com.example.civilization.Model.Map;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveGame {
    private static SaveGame instance;
    private Database database;
    private int turn;
    public SaveGame(int turn) {
        this.database = Database.getInstance();
        this.turn = 1;
    }

    public static SaveGame getInstance() {
        if (instance == null) {
            instance = new SaveGame(1);
        }
        return instance;
    }

    public void saveGame() throws IOException {
//        Gson gson = new Gson();
//        String gsonMap = gson.toJson(database.getMap());
//        FileWriter fileWriter = new FileWriter("src/main/resources/com/Game" + turn + ".json");
//        fileWriter.write(gsonMap);
//        fileWriter.close();
//        turn++;
//        if (turn > 5) {
//            turn = 1;
//        }
    }

    public void loadGame(int num) throws IOException {
//        String gsonMap = new String(Files.readAllBytes(Paths.get("src/main/resources/com/Game" + num + ".json")));
//        Gson gson = new Gson();
//        Map map = gson.fromJson(gsonMap, Map.class);
//        database.setMap(map);
    }
}
