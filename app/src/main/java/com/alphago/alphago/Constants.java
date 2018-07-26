package com.alphago.alphago;

public class Constants {
    public static final int LANGUAGE_KOR = 0;
    public static final int LANGUAGE_ENG = 10;
    public static final int LANGUAGE_JAP = 20;
    public static final int LANGUAGE_CHI = 30;

    public static int getLanguage(String lang){
        int language = LANGUAGE_KOR;
        if(lang.equals("JAP")){
            language = LANGUAGE_JAP;
        } else if(lang.equals("CHI")){
            language = LANGUAGE_CHI;
        } else if(lang.equals("ENG")){
            language = LANGUAGE_ENG;
        }
        return language;
    }
}
