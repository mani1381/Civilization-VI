package com.example.civilization.Model;

import java.util.ArrayList;

public class Database {

    private static Database instance;
    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }
    private User activeUser;
    private ArrayList<User> Users;
    private ArrayList<String> civilizationsName = new ArrayList<>();

    private Map map = new Map();
    private int turn;

    public Database() {
        this.Users = new ArrayList<>();
    }

    public ArrayList<String> getCivilizationsName() {
        return this.civilizationsName;
    }

    public void setCivilizationsName(ArrayList<String> civilizationsName) {
        this.civilizationsName = civilizationsName;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Map getMap() {
        return this.map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public User getCivilizationUser(Terrain Terrain) {
        for (User users : this.Users) {
            if (users.getCivilization().getTerrains().indexOf(Terrain) != -1) {
                return users;
            }
        }
        return null;
    }

    public void addUser(User user) {
        this.Users.add(user);
    }

    public ArrayList<User> getUsers() {
        return Users;
    }

    public void setUsers(ArrayList<User> Users) {
        this.Users = Users;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        for (User user : this.Users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : this.Users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByNickname(String nickname) {
        for (User user : this.Users) {
            if (user.getNickname().equals(nickname)) {
                return user;
            }

        }
        return null;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}