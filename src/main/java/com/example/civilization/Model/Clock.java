package com.example.civilization.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Clock {
    private static DateFormat hourMinuteFormat = new SimpleDateFormat("hh:mm");

    public static String getTime(){
        long time = System.currentTimeMillis();
        return hourMinuteFormat.format(time);
    }
}
