package com.example.civilization.Model.GlobalChats;

import com.example.civilization.Model.User;

import java.util.ArrayList;

public class Room {
    private ArrayList<User> users;
    private ArrayList<Message> messages;

    public Room(ArrayList<User> users){
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }


    public void addMessage(Message message){
        this.messages.add(message);
    }

    
}
