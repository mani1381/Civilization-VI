package com.example.civilization.View;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Enums.MenuEnums;
import com.example.civilization.Model.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu
{
    private final DatabaseController databaseController;
    private User user;

    public LoginMenu(DatabaseController databaseController)
    {
        this.databaseController = databaseController;

        this.user = null;
    }

/*    public void run(Scanner scanner) {
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine()).equals("menu exit")) {
            if ((matcher = getCommandMatcher(input, MenuEnums.ENTER.getRegex())).matches()) {
                if (user != null) {
                    if ( matcher.group("menuName").equals("Profile"))
                    {
                        System.out.println("Please enter main menu first");
                    }
                } else System.out.println("please login first");
            } else if ( getCommandMatcher(input, MenuEnums.SHOWCURRENT.getRegex()).matches()) {
                System.out.println("Login Menu");
            } else if ((matcher = getCommandMatcher(input, MenuEnums.CREATEUSER.getRegex())).matches() || (matcher = getCommandMatcher(input, MenuEnums.CREATEUSER2.getRegex())).matches() || (matcher = getCommandMatcher(input, MenuEnums.CREATEUSER3.getRegex())).matches() || (matcher = getCommandMatcher(input, MenuEnums.CREATEUSER4.getRegex())).matches() || (matcher = getCommandMatcher(input, MenuEnums.CREATEUSER5.getRegex())).matches() || (matcher = getCommandMatcher(input, MenuEnums.CREATEUSER6.getRegex())).matches()) {
                System.out.println(this.databaseController.createUser(matcher));
            } else if ((matcher = getCommandMatcher(input, MenuEnums.USERLOGIN.getRegex())).matches() || (matcher = getCommandMatcher(input, MenuEnums.USERLOGIN2.getRegex())).matches()) {
                this.user = this.databaseController.userLogin(matcher);
                if (this.user != null) {
                    MainMenu mainMenu = new MainMenu(this.databaseController, this.user);
                    ArrayList<User> players = mainMenu.run(scanner);
                    if (players != null && players.size() > 1) {
                        GameMenu gameMenu = new GameMenu(databaseController, players);
                        gameMenu.run(scanner);
                    }
                }
            }
            else if ( getCommandMatcher(input, MenuEnums.CHANGE_USERNAME.getRegex()).matches())
            {
                System.out.println("You cannot change your username");
            }
            else if (getCommandMatcher(input, MenuEnums.USERLOGOUT.getRegex()).matches()) {
                System.out.println(this.databaseController.logOut(this.user));
            } else System.out.println("invalid command");

        }

    } */

    private Matcher getCommandMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}