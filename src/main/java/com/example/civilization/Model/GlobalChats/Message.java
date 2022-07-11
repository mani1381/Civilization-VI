package com.example.civilization.Model.GlobalChats;

import com.example.civilization.Model.User;

public class Message {
    private User user;
    private String ownMessage;
    private String clockTime;
    private boolean hasSeen;
    private boolean hasDelete;

    public Message(User user,String message,String clocktime){
        this.user = user;
        this.ownMessage = message;
        this.clockTime = clocktime;
        this.hasSeen = false;
        this.hasDelete = false;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOwnMessage() {
        return this.ownMessage;
    }

    public void setOwnMessage(String ownMessage) {
        this.ownMessage = ownMessage;
    }

    public String getClockTime() {
        return this.clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    public boolean isHasSeen() {
        return this.hasSeen;
    }

    public boolean getHasSeen() {
        return this.hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }

    public boolean isHasDelete() {
        return this.hasDelete;
    }

    public boolean getHasDelete() {
        return this.hasDelete;
    }

    public void setHasDelete(boolean hasDelete) {
        this.hasDelete = hasDelete;
    }

}
