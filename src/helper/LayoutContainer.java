/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/

package helper;

import controller.GameController;
import controller.MenuController;
import javafx.scene.control.ChoiceBox;
import model.Language;

import static helper.StaticContainer.*;

public class LayoutContainer {
    public static Language language;
    public static GameController gameController ;

    /**
     * Updating points
     * @param c
     */
    public static void updatePoint(GameController c){
        gameController = c;
        c.scoreLbBlue.setText(players[0].getPoints()+"");
        c.scoreLbYellow.setText(players[1].getPoints()+"");
        c.scoreLbGreen.setText(players[2].getPoints()+"");
        c.scoreLbRed.setText(players[3].getPoints()+"");
    }

    /**
     *
     * @param c
     */
    public static void changeChoiceBoxInGame(GameController c){
        gameController = c;
        setChoiceBox(c.languageBox);
    }

    /**
     *
     * @param c
     */
    public static void changeChoiceBoxInMenu(MenuController c){
        language = new Language("en", "US");
        //Referene from the game controller
        menuController = c;
        setChoiceBox(c.languageBox);
    }

    /**
     *
     * @param choiceBox
     */
    private static void setChoiceBox(ChoiceBox<String> choiceBox){
        //To set settings for the choice box
        choiceBox.setItems(availableChoices);
        if (language.getLocale().contains("en"))
            choiceBox.setValue("English");//Set face of the choice box
        else {
            choiceBox.setValue("Tiếng Việt");
        }
    }

    /**
     *
     * @param namePlayer
     * @param enemyName
     * @param playerMoveAmount
     * @param type
     */
    public static void updateStatus(String namePlayer, String enemyName, int playerMoveAmount, int type){
        addIn = "";
        switch (type){
            case 1:{
                addIn = namePlayer + " " + language.getStatusKick() +" " + enemyName;
                System.out.println("1");
                break;
            }
            case 2:{
                addIn = namePlayer + " " + language.getStatusDeployed();
                System.out.println("2");
                break;
            }
            case 3:{
                addIn = namePlayer + " " + language.getStatusMove() + " " + playerMoveAmount + " " + language.getStatusSteps();
                System.out.println("3");
                break;
            }
            case 4:{
                addIn = namePlayer + " " + language.getStatusAtHome();
                System.out.println("4");
                break;
            }
            case 5:{
                addIn = namePlayer + " " + language.getStatusMove() + " " + playerMoveAmount + " " + language.getStatusToHome();
                System.out.println("5");
                break;
            }
            default:{
                addIn = "";
                break;
            }
        }
        gameController.activityLog.setText(addIn);
    }
}
