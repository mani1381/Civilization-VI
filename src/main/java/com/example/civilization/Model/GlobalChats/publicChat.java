package com.example.civilization.Model.GlobalChats;

import java.util.ArrayList;
import java.util.Arrays;

public class publicChat {
    private static publicChat instance;
    private ArrayList<Message> allPublicMessage = new ArrayList<>();

    public static publicChat getInstance(){
        if(instance == null){
            instance = new publicChat();
        }
        return instance;
    }

    public ArrayList<Message> getAllPublicMessage(){
        return this.allPublicMessage;
    }
    public void setPublicMessage(ArrayList<Message> newMessage){
        this.allPublicMessage = newMessage;
    }
    public void addMessage(Message message){
        this.allPublicMessage.add(message);
    }


}
