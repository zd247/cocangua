package model;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {

    private ResourceBundle bundle;

    public Language(String language, String country){
        Locale.setDefault(new Locale(language, country));
        bundle = ResourceBundle.getBundle("MessageBundle");
    }

    public void setLanguage(String language, String country){
        Locale.setDefault(new Locale(language, country));
        bundle = ResourceBundle.getBundle("MessageBundle");
    }

    public String getTitleName(){
        return bundle.getString("titleName");
    }

    public String getStartButton(){
        return bundle.getString("startButton");
    }

    public String getAddPlayerMessage() {
        return bundle.getString("addPlayerMessage");
    }

    public String getWinScreenTitle() {
        return bundle.getString("winScreenTitle");
    }

    public String getResultStatement() { return bundle.getString("winResult"); }

    public String getEndGameLabel() { return bundle.getString("endGameLb"); }

    public String getLocale(){
        return Locale.getDefault()+"";
    }

    public String getNewGame() {
        return bundle.getString("newGame");
    }

    public String getQuit(){
        return bundle.getString("quit");
    }
}
