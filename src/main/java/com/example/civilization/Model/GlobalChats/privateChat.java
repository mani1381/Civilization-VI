package com.example.civilization.Model.GlobalChats;

import com.example.civilization.Model.User;

import java.util.ArrayList;

public class privateChat {
    private User userOne;
    private User userTwo;
    private ArrayList<Message> allPrivateMessage = new ArrayList<>();
    public privateChat(User userOne,User userTwo){
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public User getUserOne() {
        return this.userOne;
    }

    public void setUserOne(User userOne) {
        this.userOne = userOne;
    }

    public User getUserTwo() {
        return this.userTwo;
    }

    public void setUserTwo(User userTwo) {
        this.userTwo = userTwo;
    }

    public ArrayList<Message> getAllPrivateMessage() {
        return this.allPrivateMessage;
    }

    public void setAllPrivateMessage(ArrayList<Message> allPrivateMessage) {
        this.allPrivateMessage = allPrivateMessage;
    }

    public void addMessage(Message mess){
        this.allPrivateMessage.add(mess);
    }




}
