package com.example.civilization.FXMLcontrollers;

import com.example.civilization.Controllers.DatabaseController;
import com.example.civilization.Main;
import com.example.civilization.Model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class DemographicPanel {

    @FXML
    AnchorPane anchorPane;
    @FXML
    ArrayList<Label> values = new ArrayList<>();

    @FXML
    public void initialize() {

        Platform.runLater(this::setTexts);

    }

    public void setTexts() {
        int i = 0;
        int j = -1;
        while (i + j * 5 < 30) {
            if (i % 5 == 0) {
                j++;
                i = 0;
            }
            values.add(new Label());
            if (j == 0) {
                if (i == 0) {
                    values.get(i + j * 5).setText(citiesRank(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 1) {
                    values.get(i + j * 5).setText(citiesValue());
                } else if (i == 2) {
                    values.get(i + j * 5).setText(citiesBest(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 3) {
                    values.get(i + j * 5).setText(citiesAverage(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 4) {
                    values.get(i + j * 5).setText(citiesWorst(DatabaseController.getInstance().getDatabase().getUsers()));
                }

            } else if (j == 1) {
                if (i == 0) {
                    values.get(i + j * 5).setText(goldRank(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 1) {
                    values.get(i + j * 5).setText(goldValue());
                } else if (i == 2) {
                    values.get(i + j * 5).setText(goldBest(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 3) {
                    values.get(i + j * 5).setText(goldAverage(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 4) {
                    values.get(i + j * 5).setText(goldWorst(DatabaseController.getInstance().getDatabase().getUsers()));
                }
            } else if (j == 2) {
                if (i == 0) {
                    values.get(i + j * 5).setText(scienceRank(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 1) {
                    values.get(i + j * 5).setText(scienceValue());
                } else if (i == 2) {
                    values.get(i + j * 5).setText(scienceBest(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 3) {
                    values.get(i + j * 5).setText(scienceAverage(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 4) {
                    values.get(i + j * 5).setText(scienceWorst(DatabaseController.getInstance().getDatabase().getUsers()));
                }
            } else if (j == 3) {
                if (i == 0) {
                    values.get(i + j * 5).setText(happinessRank(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 1) {
                    values.get(i + j * 5).setText(happinessValue());
                } else if (i == 2) {
                    values.get(i + j * 5).setText(happinessBest(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 3) {
                    values.get(i + j * 5).setText(happinessAverage(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 4) {
                    values.get(i + j * 5).setText(happinessWorst(DatabaseController.getInstance().getDatabase().getUsers()));
                }
            } else if (j == 4) {
                if (i == 0) {
                    values.get(i + j * 5).setText(technologiesRank(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 1) {
                    values.get(i + j * 5).setText(technologiesValue());
                } else if (i == 2) {
                    values.get(i + j * 5).setText(technologiesBest(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 3) {
                    values.get(i + j * 5).setText(technologiesAverage(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 4) {
                    values.get(i + j * 5).setText(technologiesWorst(DatabaseController.getInstance().getDatabase().getUsers()));
                }
            } else if (j == 5) {
                if (i == 0) {
                    values.get(i + j * 5).setText(unitsRank(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 1) {
                    values.get(i + j * 5).setText(unitsValue());
                } else if (i == 2) {
                    values.get(i + j * 5).setText(unitsBest(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 3) {
                    values.get(i + j * 5).setText(unitsAverage(DatabaseController.getInstance().getDatabase().getUsers()));
                } else if (i == 4) {
                    values.get(i + j * 5).setText(unitsWorst(DatabaseController.getInstance().getDatabase().getUsers()));
                }
            }
            values.get(i + j * 5).setAlignment(Pos.CENTER);
            values.get(i + j * 5).setFont(Font.font("Copperplate", 15));
            values.get(i + j * 5).setTextFill(Color.RED);
            values.get(i + j * 5).setPrefSize(50, 50);
            values.get(i + j * 5).setVisible(true);
            values.get(i + j * 5).setLayoutX(560 + i * 115);
            values.get(i + j * 5).setLayoutY(185 + 77 * j);


            anchorPane.getChildren().add(values.get(i + j * 5));
            i++;
        }

    }


    private String citiesRank(ArrayList<User> users) {
        int ranking = 1;
        for (User user : users) {
            if (user.getCivilization().getCities().size() > DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getCities().size()) {
                ranking++;
            }
        }
        return Integer.toString(ranking);
    }

    private String citiesValue() {
        return Integer.toString(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getCities().size());
    }

    private String citiesBest(ArrayList<User> users) {
        int max = 0;
        for (User user : users) {
            if (user.getCivilization().getCities().size() > max) {
                max = user.getCivilization().getCities().size();
            }
        }
        return Integer.toString(max);
    }

    private String citiesAverage(ArrayList<User> users) {
        double average = 0.0;
        for (User user : users) {
            average += user.getCivilization().getCities().size();
        }
        return Double.toString(average / users.size());
    }

    private String citiesWorst(ArrayList<User> users) {
        int min = 111111111;
        for (User user : users) {
            if (user.getCivilization().getCities().size() < min) {
                min = user.getCivilization().getCities().size();
            }
        }
        return Integer.toString(min);
    }

    private String goldRank(ArrayList<User> users) {
        int ranking = 1;
        for (User user : users) {
            if (user.getCivilization().getGold() > DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getGold()) {
                ranking++;
            }
        }
        return Integer.toString(ranking);
    }

    private String goldValue() {
        return Integer.toString(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getGold());
    }

    private String goldBest(ArrayList<User> users) {
        int max = 0;
        for (User user : users) {
            if (user.getCivilization().getGold() > max) {
                max = user.getCivilization().getGold();
            }
        }
        return Integer.toString(max);
    }

    private String goldAverage(ArrayList<User> users) {
        double average = 0.0;
        for (User user : users) {
            average += user.getCivilization().getGold();
        }
        return Double.toString(average / users.size());
    }

    private String goldWorst(ArrayList<User> users) {
        int min = 111111111;
        for (User user : users) {
            if (user.getCivilization().getGold() < min) {
                min = user.getCivilization().getGold();
            }
        }
        return Integer.toString(min);
    }

    private String happinessRank(ArrayList<User> users) {
        int ranking = 1;
        for (User user : users) {
            if (user.getCivilization().getHappiness() > DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getHappiness()) {
                ranking++;
            }
        }
        return Integer.toString(ranking);
    }

    private String happinessValue() {
        return Integer.toString(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getHappiness());
    }

    private String happinessBest(ArrayList<User> users) {
        int max = 0;
        for (User user : users) {
            if (user.getCivilization().getHappiness() > max) {
                max = user.getCivilization().getHappiness();
            }
        }
        return Integer.toString(max);
    }

    private String happinessAverage(ArrayList<User> users) {
        double average = 0.0;
        for (User user : users) {
            average += user.getCivilization().getHappiness();
        }
        return Double.toString(average / users.size());
    }

    private String happinessWorst(ArrayList<User> users) {
        int min = 111111111;
        for (User user : users) {
            if (user.getCivilization().getHappiness() < min) {
                min = user.getCivilization().getHappiness();
            }
        }
        return Integer.toString(min);
    }

    private String scienceRank(ArrayList<User> users) {
        int ranking = 1;
        for (User user : users) {
            if (user.getCivilization().getScience() > DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getScience()) {
                ranking++;
            }
        }
        return Integer.toString(ranking);
    }

    private String scienceValue() {
        return Integer.toString(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getScience());
    }

    private String scienceBest(ArrayList<User> users) {
        int max = 0;
        for (User user : users) {
            if (user.getCivilization().getScience() > max) {
                max = user.getCivilization().getScience();
            }
        }
        return Integer.toString(max);
    }

    private String scienceAverage(ArrayList<User> users) {
        double average = 0.0;
        for (User user : users) {
            average += user.getCivilization().getScience();
        }
        return Double.toString(average / users.size());
    }

    private String scienceWorst(ArrayList<User> users) {
        int min = 111111111;
        for (User user : users) {
            if (user.getCivilization().getScience() < min) {
                min = user.getCivilization().getScience();
            }
        }
        return Integer.toString(min);
    }

    private String technologiesRank(ArrayList<User> users) {
        int ranking = 1;
        for (User user : users) {
            if (user.getCivilization().getTechnologies().size() > DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getTechnologies().size()) {
                ranking++;
            }
        }
        return Integer.toString(ranking);
    }

    private String technologiesValue() {
        return Integer.toString(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getTechnologies().size());
    }

    private String technologiesBest(ArrayList<User> users) {
        int max = 0;
        for (User user : users) {
            if (user.getCivilization().getTechnologies().size() > max) {
                max = user.getCivilization().getTechnologies().size();
            }
        }
        return Integer.toString(max);
    }

    private String technologiesAverage(ArrayList<User> users) {
        double average = 0.0;
        for (User user : users) {
            average += user.getCivilization().getTechnologies().size();
        }
        return Double.toString(average / users.size());
    }

    private String technologiesWorst(ArrayList<User> users) {
        int min = 111111111;
        for (User user : users) {
            if (user.getCivilization().getTechnologies().size() < min) {
                min = user.getCivilization().getTechnologies().size();
            }
        }
        return Integer.toString(min);
    }

    private String unitsRank(ArrayList<User> users) {
        int ranking = 1;
        for (User user : users) {
            if (user.getCivilization().getUnits().size() > DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getUnits().size()) {
                ranking++;
            }
        }
        return Integer.toString(ranking);
    }

    private String unitsValue() {
        return Integer.toString(DatabaseController.getInstance().getDatabase().getActiveUser().getCivilization().getUnits().size());
    }

    private String unitsBest(ArrayList<User> users) {
        int max = 0;
        for (User user : users) {
            if (user.getCivilization().getUnits().size() > max) {
                max = user.getCivilization().getUnits().size();
            }
        }
        return Integer.toString(max);
    }

    private String unitsAverage(ArrayList<User> users) {
        double average = 0.0;
        for (User user : users) {
            average += user.getCivilization().getUnits().size();
        }
        return Double.toString(average / users.size());
    }

    private String unitsWorst(ArrayList<User> users) {
        int min = 111111111;
        for (User user : users) {
            if (user.getCivilization().getUnits().size() < min) {
                min = user.getCivilization().getUnits().size();
            }
        }
        return Integer.toString(min);
    }

    public void backToGameMap() {
        Main.changeMenu("GameMap");
    }
}
