package com.example.civilization.View;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Enums.MenuEnums;
import com.example.civilization.Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu {
    private  DatabaseController databaseController;
    private  User user;

    public ProfileMenu(DatabaseController databaseController, User user) {
        this.databaseController = databaseController;
        this.user = user;
    }

/*    public void run(Scanner scanner) {
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine()).equals("menu exit")) {
            if ((matcher = getCommandMatcher(input, MenuEnums.CHANGE_NICKNAME.getRegex())).matches()) {
                System.out.println(this.databaseController.changeUserNickname(matcher, this.user));
            } else if ((matcher = getCommandMatcher(input, MenuEnums.CHANGE_PASSWORD.getRegex())).matches()) {
                String temp = this.databaseController.changePassword(matcher, this.user);
                if ( temp.equals("password changed successfully! Please Login again with your new password"))
                {
                    System.out.println(temp);
                    System.out.println(this.databaseController.logOut(this.user));
                    return;
                }
                else
                    System.out.println(temp);

            } else System.out.println("invalid command");
        }
        return;
    } */

    private Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
}