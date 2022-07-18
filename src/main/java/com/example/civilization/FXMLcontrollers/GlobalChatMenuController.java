package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Main;
import com.example.civilization.Model.Clock;
import com.example.civilization.Model.Database;
import com.example.civilization.Model.GlobalChats.Message;
import com.example.civilization.Model.GlobalChats.Room;
import com.example.civilization.Model.GlobalChats.privateChat;
import com.example.civilization.Model.GlobalChats.publicChat;
import com.example.civilization.Model.User;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class GlobalChatMenuController {

    private static GlobalChatMenuController instance;
    public VBox firstBox;
    public Button changeToPublicChatButton;

    public Button changeToPrivateChatButton;
    public Button changeToRoomChatButton;
    public VBox allMessagePrivate;
    public VBox VboxPublic;
    public VBox VboxPrivate;
    public HBox searchPanel;
    public TextField searchTextField;
    public ImageView searchButton;
    public VBox VboxRoom;
    public ScrollPane scrollPaneRoom;
    public VBox allMessageRoom;
    public ScrollPane scrollPanePrivate;
    public TextField searchTextFieldRoom;
    public Button roomButton;
    public HBox searchPanelRoom;
    public ImageView searchButtonRoom;

    @FXML
    private Button changeMessageButton;
    @FXML
    private TextField editTextField;
    @FXML
    private Pane editMessage;
    @FXML
    private Button deleteForMe;
    @FXML
    private Button deleteForEveryone;
    @FXML
    private Pane deleteMessage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField text;
    @FXML
    private VBox allMessages;
    private User user = new User("ehsan", null, null, null);
    private int chatNumber = 0;
    private privateChat privatechat;
    private Room room;
    private ArrayList<String> usersNames = new ArrayList<>();

    public static GlobalChatMenuController getInstance() {
        if (instance == null) {
            instance = new GlobalChatMenuController();
        }
        return instance;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void initialize() throws MalformedURLException {
        allMessages.setSpacing(5);
        allMessagePrivate.setSpacing(5);
        allMessageRoom.setSpacing(5);
        showPublicMessage();
    }

    public void showPublicMessage() throws MalformedURLException {
        for (int i = 0; i < publicChat.getInstance().getAllPublicMessage().size(); i++) {
            Message message = publicChat.getInstance().getAllPublicMessage().get(i);
            if (!(message.getUser() == user && message.getHasDelete())) sendNewMessage(message);
            if (message.getUser() != user) message.setHasSeen(true);
        }
    }


    public void enter(KeyEvent keyEvent) throws MalformedURLException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            send(null);
        }
    }

    @FXML
    public void back(MouseEvent event) throws IOException {
        Main.changeMenu("ProfileMenu");
    }


    public void send(MouseEvent mouseEvent) throws MalformedURLException {
        if (text.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("empty text");
            alert.show();
        } else if (text.getText().length() > 20) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("long message");
            alert.show();
        } else {
            Message message = new Message(user, text.getText(), Clock.getTime());
            if (chatNumber == 0) {
                publicChat.getInstance().addMessage(message);
                sendNewMessage(message);
                text.clear();
            } else if (chatNumber == 1) {
                this.privatechat.addMessage(message);
                sendNewMessage(message);
                text.clear();
            } else if (chatNumber == -1) {
                this.room.addMessage(message);
                sendNewMessage(message);
                text.clear();
            }

        }
    }

    private void sendNewMessage(Message message) throws MalformedURLException {
        Pane pane = getMessageBox(message);
        if (chatNumber == 0) {
            allMessages.getChildren().add(pane);
        } else if (chatNumber == 1) {
            allMessagePrivate.getChildren().add(pane);
        } else if (chatNumber == -1) {
            allMessageRoom.getChildren().add(pane);
        }
    }

    private Pane getMessageBox(Message message) throws MalformedURLException {
        Pane pane = new Pane();
        pane.setPrefWidth(100);
        pane.setPrefHeight(80);
        pane.setStyle("-fx-border-radius: 30 30 30 30;" + "-fx-background-radius: 30 30 30 30;" + "-fx-background-color: #927819;");
        userAvatars(pane, message.getUser());
        addText(pane, message.getOwnMessage());
        addUsersUsername(pane, message.getUser());
        addClock(pane, message.getClockTime());
        if (message.getUser() == user) {
            addSeenUnSeen(pane, message.getHasSeen());
        }
        if (message.getUser() == user) {
            addButtonToDelete(pane, message);
        }
        if (message.getUser() == user) {
            addButtonToEdit(pane, message);
        }
        return pane;
    }


    public void userAvatars(Pane pane, User user) {


    }

    public void addText(Pane pane, String message) {
        Label text = new Label(message);
        text.setPrefHeight(20);
        text.setPrefHeight(60);
        text.setLayoutX(40);
        text.setLayoutY(15);
        text.setFont(Font.font(20));
        pane.getChildren().add(text);
    }


    public void addUsersUsername(Pane pane, User user) {
        Label label = new Label("player: " + user.getUsername());
        label.setPrefHeight(20);
        label.setLayoutX(40);
        label.setLayoutY(10);
        label.setFont(Font.font(12));
        pane.getChildren().add(label);
    }

    public void addClock(Pane pane, String stringClock) {
        Label clock = new Label(stringClock);
        clock.setPrefHeight(60);
        clock.setPrefWidth(60);
        clock.setLayoutX(260);
        clock.setLayoutY(25);
        pane.getChildren().add(clock);
    }


    private void addSeenUnSeen(Pane pane, boolean isSeen) throws MalformedURLException {
        ImageView imageView;
        if (isSeen) {
            imageView = new ImageView(new Image(String.valueOf(new URL(Main.class.getResource("PNG/images/seen.png").toString()))));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            imageView.setLayoutX(305);
            imageView.setLayoutY(45);
        } else {
            imageView = new ImageView(new Image(String.valueOf(new URL(Main.class.getResource("PNG/images/unseen.png").toString()))));
            imageView.setFitHeight(10);
            imageView.setFitWidth(10);
            imageView.setLayoutX(300);
            imageView.setLayoutY(50);
        }
        pane.getChildren().add(imageView);
    }

    public void addButtonToEdit(Pane pane, Message message) throws MalformedURLException {
        ImageView imageView = new ImageView(new Image(String.valueOf(new URL(Main.class.getResource("PNG/images/edit.png").toString()))));
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setLayoutX(288);
        imageView.setLayoutY(14);
        imageView.setCursor(Cursor.HAND);
        AtomicBoolean isSelectedForEdit = new AtomicBoolean(true);
        imageView.setOnMouseClicked(mouseEvent -> {
            if (isSelectedForEdit.get()) {
                isSelectedForEdit.set(false);
                pane.getChildren().add(editMessage);
                editMessage.setLayoutX(330);
                editMessage.setLayoutY(0);
                editTextField.setText(message.getOwnMessage());
                editMessageClicked(pane, isSelectedForEdit, message);
                editMessage.setVisible(true);
                editTextField.requestFocus();
            } else {
                isSelectedForEdit.set(true);
                pane.getChildren().remove(editMessage);
            }
        });
        pane.getChildren().add(imageView);
    }


    public void editMessageClicked(Pane pane, AtomicBoolean isSelectedForEdit, Message message) {
        editTextField.setOnKeyPressed(k -> {
            if (k.getCode().equals(KeyCode.ENTER)) {
                if (editTextField.getText().length() > 20) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("long message");
                    alert.show();
                } else {
                    isSelectedForEdit.set(true);
                    if (pane.getChildren().get(1) instanceof Label) {
                        ((Label) pane.getChildren().get(1)).setText(editTextField.getText());
                    }
                    message.setOwnMessage(editTextField.getText());
                    pane.getChildren().remove(editMessage);
                }
            }
        });
        changeMessageButton.setOnMouseClicked(mouseEvent -> {
            if (editTextField.getText().length() > 20) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("long message");
                alert.show();
            } else {
                isSelectedForEdit.set(true);
                if (pane.getChildren().get(1) instanceof Label) {
                    ((Label) pane.getChildren().get(1)).setText(editTextField.getText());
                }
                message.setOwnMessage(editTextField.getText());
                pane.getChildren().remove(editMessage);
            }
        });
    }


    public void addButtonToDelete(Pane pane, Message message) throws MalformedURLException {
        ImageView imageView = new ImageView(new Image(String.valueOf(new URL(Main.class.getResource("PNG/images/blackCross.png").toString()))));
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        imageView.setLayoutX(310);
        imageView.setLayoutY(15);
        AtomicBoolean isSelectedForDelete = new AtomicBoolean(true);
        imageView.setOnMouseClicked(mouseEvent -> {
            if (isSelectedForDelete.get()) {
                deleteTRUE(pane, isSelectedForDelete, message);
            } else {
                isSelectedForDelete.set(true);
                pane.getChildren().remove(deleteMessage);
            }
        });
        pane.getChildren().add(imageView);
    }

    public void deleteTRUE(Pane pane, AtomicBoolean isSelectedForDelete, Message message) {
        isSelectedForDelete.set(false);
        pane.getChildren().add(deleteMessage);
        deleteMessage.setLayoutX(330);
        deleteMessage.setLayoutY(0);


        deleteForEveryone.setOnMouseClicked(mouseEvent -> {
            isSelectedForDelete.set(true);
            pane.getChildren().remove(deleteMessage);
            if (chatNumber == 0) {
                allMessages.getChildren().remove(pane);
                publicChat.getInstance().getAllPublicMessage().remove(message);
            } else if (chatNumber == 1) {
                allMessagePrivate.getChildren().remove(pane);
                privatechat.getAllPrivateMessage().remove(message);
            } else if (chatNumber == -1) {
                allMessageRoom.getChildren().remove(pane);
                room.getMessages().remove(message);
            }

        });
        deleteForMe.setOnMouseClicked(mouseEvent -> {
            isSelectedForDelete.set(true);
            pane.getChildren().remove(deleteMessage);
            if (chatNumber == 0) {
                allMessages.getChildren().remove(pane);
            } else if (chatNumber == 1) {
                allMessagePrivate.getChildren().remove(pane);
            } else if (chatNumber == -1) {
                allMessageRoom.getChildren().remove(pane);
            }
            message.setHasDelete(true);
        });
        deleteMessage.setVisible(true);
    }


    public void Public(MouseEvent mouseEvent) throws MalformedURLException {
        if (chatNumber != 0) {
            chatNumber = 0;
            VboxPublic.setVisible(true);
            VboxPrivate.setVisible(false);
            VboxRoom.setVisible(false);
            searchPanel.setVisible(false);
            roomButton.setVisible(false);
            searchPanelRoom.setVisible(false);
            showPublicMessage();
        }
    }

    public void Private(MouseEvent mouseEvent) throws MalformedURLException {
        if (chatNumber != 1) {
            chatNumber = 1;
            VboxPublic.setVisible(false);
            VboxRoom.setVisible(false);
            VboxPrivate.setVisible(false);
            searchPanel.setVisible(true);
            roomButton.setVisible(false);
            searchPanelRoom.setVisible(false);
            showPrivateMessage();
        }
    }

    public void Room(MouseEvent mouseEvent) throws MalformedURLException {
        if (chatNumber != -1) {
            chatNumber = -1;
            VboxPublic.setVisible(false);
            VboxPrivate.setVisible(false);
            VboxRoom.setVisible(true);
            roomButton.setVisible(true);
            searchPanel.setVisible(false);
            searchPanelRoom.setVisible(true);
            showRoomMessage();


        }
    }


    public User findUser(String username) {
        ArrayList<User> users = Database.getInstance().getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    public privateChat containSecondUser(User userSecond) {
        ArrayList<privateChat> userPrivateChats = user.getPrivateChats();
        for (int i = 0; i < userPrivateChats.size(); i++) {
            if (userPrivateChats.get(i).getUserTwo() == userSecond) {
                return userPrivateChats.get(i);
            }
        }
        return null;
    }


    public void checkSecondUserForPrivate(KeyEvent keyEvent) throws MalformedURLException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            findSecondUser(null);
        }
    }


    public void findSecondUser(MouseEvent mouseEvent) throws MalformedURLException {
        String username = searchTextField.getText();
        if (username.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("emptyUsername");
            alert.show();
        } else if (findUser(username) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("noUser");
            alert.show();
        } else {

            User userSecond = findUser(username);
            if (containSecondUser(userSecond) == null) {
                privateChat newprivateChate = new privateChat(user, userSecond);
                this.privatechat = newprivateChate;
                user.addPrivateChats(newprivateChate);
            } else {
                this.privatechat = containSecondUser(userSecond);
            }
            searchPanel.setVisible(false);
            VboxPrivate.setVisible(true);
            showPrivateMessage();
        }
    }

    public void showPrivateMessage() throws MalformedURLException {
        for (int i = 0; i < this.privatechat.getAllPrivateMessage().size(); i++) {
            Message message = this.privatechat.getAllPrivateMessage().get(i);
            if (!(message.getUser() == user && message.getHasDelete())) sendNewMessage(message);
            if (message.getUser() != user) message.setHasSeen(true);
        }
    }

    public void showRoomMessage() throws MalformedURLException {
        for (int i = 0; i < this.room.getMessages().size(); i++) {
            Message message = this.room.getMessages().get(i);
            if (!(message.getUser() == user && message.getHasDelete())) sendNewMessage(message);
            if (message.getUser() != user) message.setHasSeen(true);

        }
    }

    public void findUserForroom(MouseEvent mouseEvent) {
        String usernames = searchTextFieldRoom.getText();
        if (usernames.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("empty username");
            alert.show();
        } else {
            if (findUser(usernames) != null) {
                usersNames.add(usernames);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("we dont have this username");
                alert.show();
            }

        }
    }

    public void checkUserRoom(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            findUserForroom(null);
            keyEvent.consume();
        }

    }


    public boolean haveThisUsersInRooms(ArrayList<String> roomUsername, ArrayList<String> username) {
        for (int i = 0; i < username.size(); i++) {
            if (roomUsername.indexOf(username.get(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    public Room containsThisUsers(ArrayList<String> names) {
        ArrayList<Room> myRooms = user.getRooms();
        for (int i = 0; i < myRooms.size(); i++) {
            ArrayList<User> user = myRooms.get(i).getUsers();
            ArrayList<String> username = new ArrayList<>();
            for (int j = 0; j < user.size(); j++) {
                username.add(user.get(i).getUsername());
            }
            if (haveThisUsersInRooms(username, names) == true) {
                return myRooms.get(i);
            }
        }
        return null;

    }

    public void goToRoom(MouseEvent mouseEvent) {
        if (containsThisUsers(usersNames) != null) {
            this.room = containsThisUsers(usersNames);
        } else {
            ArrayList<User> myUser = new ArrayList<>();
            for (int i = 0; i < usersNames.size(); i++) {
                myUser.add(Database.getInstance().getUserByUsername(usersNames.get(i)));
            }
            Room room = new Room(myUser);
            this.room = room;
        }
    }
}