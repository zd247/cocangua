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
}
