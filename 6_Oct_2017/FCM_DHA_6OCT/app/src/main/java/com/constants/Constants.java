package com.constants;

/**
 * Created by eclipse on 3/10/17.
 */

public class Constants {
    public static final String MAIN_URL = "http://www.dharamraz.com/api/api.php?";
    public static final String CATEGORY = "module=categories";
    public static final String GAMES_BY_CAT_ID_OR_COUNTRY_ID = "module=games";
    public static final String COUNTRY_LIST = "module=country";

    public static String getMethodUrl(String methodName) {
        String url = "";
        url = MAIN_URL + methodName;
        return url;
    }
}
