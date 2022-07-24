package com.example.civilization.Model;


import com.example.civilization.Model.GlobalChats.Room;
import com.example.civilization.Model.GlobalChats.privateChat;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private Civilization civilization;
    private int score = 0;
    private String lastWin = "00:00";
    private String lastLogin = "00:00";

    public int photoNumber;
    private String profilePicture;

    private boolean online = false;

    private ArrayList<privateChat> privatechat = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();

    private ArrayList<User> friends = new ArrayList<>();


    public User(String username, String password, String nickname, Civilization civil) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.civilization = civil;
        profilePicture = "src/main/resources/com/example/civilization/PNG/images/prof"  +(username.length() % 8 + 1)  +".png";
    }

    public Civilization getCivilization() {
        return this.civilization;
    }

    public void setCivilization(Civilization civil) {
        this.civilization = civil;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastWin() {
        return lastWin;
    }

    public void setLastWin(String lastWin) {
        this.lastWin = lastWin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getProfilePicture() {
        return profilePicture;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public ArrayList<privateChat> getPrivateChats() {
        return privatechat;
    }

    public void addPrivateChats(privateChat privateChats) {
        this.privatechat.add(privateChats);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }
}
