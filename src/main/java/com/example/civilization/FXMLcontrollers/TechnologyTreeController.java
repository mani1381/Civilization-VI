package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.Technologies.TechnologyTypes;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TechnologyTreeController {
    @FXML
    ScrollPane scrollPane;
    @FXML
    AnchorPane anchorPane;


    @FXML
    ArrayList<Button> suggestions = new ArrayList<>();

    public static void showingPopUp(TechnologyTypes technologyTypes, StackPane stackPane) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("FXML/TechnologyPopUp.fxml"));
            Parent root = loader.load();
            TechnologyPopUpController secController = loader.getController();
            secController.setData(technologyTypes);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stackPane.addEventFilter(MouseEvent.ANY, new EventHandler<>() {

                long startTime;

                @Override
                public void handle(MouseEvent event) {
                    if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                        startTime = System.currentTimeMillis();
                    } else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                        if (System.currentTimeMillis() - startTime > 1000) {
                            stage.show();
                        }
                    } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
                        if (stage.isShowing()) {
                            stage.close();
                        }

                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() {

        Platform.runLater(this::setTechnologyButton);

    }

    public void setTechnologyButton() {
        for (Node children : anchorPane.getChildren()) {
            if (children instanceof StackPane) {
                if (((StackPane) children).getChildren().get(1) instanceof Text && ((StackPane) children).getChildren().get(0) instanceof Rectangle) {
                    ((Text) ((StackPane) children).getChildren().get(1)).setFont(Font.font("Copperplate", 18));
                    setColor((StackPane) children);

                    children.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            if (mouseEvent.getClickCount() == 2) {
                                DatabaseController.getInstance().choosingATechnologyToStudyForGraphic(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(((Text) ((StackPane) children).getChildren().get(1)).getText()));
                                setIcons();
                            }
                        }
                    });

                    showingPopUp(DatabaseController.getInstance().getTechnologyTypeByName(((Text) ((StackPane) children).getChildren().get(1)).getText()), (StackPane) children);

                }


            }


            if (children instanceof TextField) {
                ((TextField) children).setPrefSize(175, 30);
                children.setOnKeyPressed(e -> {
                    if (e.getCode().equals(KeyCode.ENTER)) {
                        anchorPane.getChildren().removeAll(suggestions);
                        suggestions.clear();
                        int i = 0;
                        for (TechnologyTypes technologyTypes : TechnologyTypes.values()) {
                            if (technologyTypes.name().startsWith(((TextField) children).getText().toUpperCase())) {
                                suggestions.add(new Button());
                                suggestions.get(i).setPrefSize(((TextField) children).getPrefWidth() - 25, ((TextField) children).getPrefHeight());
                                suggestions.get(i).setText(technologyTypes.name());
                                suggestions.get(i).setVisible(true);
                                suggestions.get(i).setLayoutX(children.getLayoutX());
                                suggestions.get(i).setLayoutY(children.getLayoutY() + ((TextField) children).getPrefHeight() * (i + 1));
                                String name = suggestions.get(i).getText();
                                suggestions.get(i).setOnMouseClicked(mouseEvent -> {
                                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                                        if (mouseEvent.getClickCount() == 2) {
                                            DatabaseController.getInstance().choosingATechnologyToStudyForGraphic(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(name));
                                            setIcons();
                                        }
                                    }
                                });
                                anchorPane.getChildren().add(suggestions.get(i));
                                i++;
                            }

                        }

                    }
                });

            }


        }

        for (Node children : anchorPane.getChildren()) {
            if (children instanceof Line) {
                ((Line) children).setStrokeWidth(3);

            }
        }


    }

    private void setColor(StackPane children) {
        if (DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(((Text) children.getChildren().get(1)).getText())) != null && DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(((Text) children.getChildren().get(1)).getText())).getIsAvailable()) {
            ((Rectangle) children.getChildren().get(0)).setFill(Color.DEEPSKYBLUE);
        } else if (DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(((Text) children.getChildren().get(1)).getText())) != null && DatabaseController.getInstance().getTechnologyByTechnologyType(DatabaseController.getInstance().getDatabase().getActiveUser(), DatabaseController.getInstance().getTechnologyTypeByName(((Text) children.getChildren().get(1)).getText())).getUnderResearch()) {
            ((Rectangle) children.getChildren().get(0)).setFill(Color.YELLOW);
        } else {
            ((Rectangle) children.getChildren().get(0)).setFill(Color.WHITE);
        }
    }

    public void setIcons() {
        for (Node children : anchorPane.getChildren()) {
            if (children instanceof StackPane) {
                if (((StackPane) children).getChildren().get(1) instanceof Text && ((StackPane) children).getChildren().get(0) instanceof Rectangle) {
                    ((Text) ((StackPane) children).getChildren().get(1)).setFont(Font.font("Copperplate", 18));

                    setColor((StackPane) children);

                }

            }
        }
    }

    public void backToChooseResearch() {
        Main.changeMenu("ChooseResearch");
    }
}
