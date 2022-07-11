package com.example.civilization.Enums;

public enum MenuEnums {
    ENTER("menu enter (?<menuName>Login|Main|PlayGame|Profile)"),
    SHOWCURRENT("menu show-current"),
    CREATEUSER("user create (--username|-u) (?<username>[A-Za-z0-9]+) (--nickname|-n) (?<nickname>[A-Za-z0-9]+) (--password|-p) (?<password>[A-Za-z0-9]+)" ),
    CREATEUSER2("user create (--nickname|-n) (?<nickname>[A-Za-z0-9]+) (--username|-u) (?<username>[A-Za-z0-9]+) (--password|-p) (?<password>[A-Za-z0-9]+)"),
    CREATEUSER3("user create (--nickname|-n) (?<nickname>[A-Za-z0-9]+) (--password|-p) (?<password>[A-Za-z0-9]+) (--username|-u) (?<username>[A-Za-z0-9]+)"),
    CREATEUSER4("user create (--password|-p) (?<password>[A-Za-z0-9]+) (--nickname|-n) (?<nickname>[A-Za-z0-9]+) (--username|-u) (?<username>[A-Za-z0-9]+)"),
    CREATEUSER5("user create (--password|-p) (?<password>[A-Za-z0-9]+) (--username|-u) (?<username>[A-Za-z0-9]+) (--nickname|-n) (?<nickname>[A-Za-z0-9]+)"),
    CREATEUSER6("user create (--username|-u) (?<username>[A-Za-z0-9]+) (--password|-p) (?<password>[A-Za-z0-9]+) (--nickname|-n) (?<nickname>[A-Za-z0-9]+)"),
    USERLOGIN("user login (--username|-u) (?<username>[A-Za-z0-9]+) (--password|-p) (?<password>[A-Za-z0-9]+)"),
    USERLOGIN2("user login (--password|-p) (?<password>[A-Za-z0-9]+) (--username|-u) (?<username>[A-Za-z0-9]+)"),
    USERLOGOUT("user logout"),
    PLAYGAME("play game (--player1|-p1) (?<username>[A-Za-z0-9]+).*"),
    CHANGE_NICKNAME("profile change (--nickname|-n) (?<newNickname>[A-Za-z0-9]+)"),
    CHANGE_PASSWORD("profile change (--password|-p) (--current|-c) (?<currentPassword>[A-Za-z0-9]+) (--new|-n) (?<newPassword>[A-Za-z0-9]+)"),
    CHANGE_USERNAME("profile change (--username|-u) (--current|-c) (?<currentUsername>[A-Za-z0-9]+) (--new|-n) (?<newUsername>[A-Za-z0-9]+)"),

    ;

    private String regex;
    MenuEnums (String regex)
    {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}