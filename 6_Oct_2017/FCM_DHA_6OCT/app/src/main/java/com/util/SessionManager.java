package com.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.main.MainActivity;

import java.util.HashMap;


public class SessionManager {
    // Shared Preferences reference
    SharedPreferences pref;
    // Editor reference for Shared preferences
    Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREFER_NAME = "DHARAMRAZ_DB";
    private static final String IS_LOGIN = "IsLogin";
    private static final String USER_DETAIL = "user_info";
    // All Shared Preferences Keys
    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY_ID = "country_id";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

//    /**
//     * Create login session
//     */
//    public void setUserDetail(UserDTO userDTO) {
//        // Storing login value as TRUE
//        Gson gson = new Gson();
//        String vUserDTO = gson.toJson(userDTO);
//        editor.putString(USER_DETAIL, vUserDTO);
//        // commit changes
//        editor.commit();
//    }
//
//    public UserDTO getUserDetail() {
//        Gson gson = new Gson();
//        String vjson = pref.getString(USER_DETAIL, "");
//        UserDTO userDTO = gson.fromJson(vjson, UserDTO.class);
//        return userDTO;
//    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    // login session...
    public void login() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setCountryDetails(String vCountry_code, String vCountry_id) {
        editor.putString(COUNTRY_CODE, vCountry_code);
        editor.putString(COUNTRY_ID, vCountry_id);
        editor.commit();
    }

    public HashMap<String, String> getCountryDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(COUNTRY_CODE, pref.getString(COUNTRY_CODE, ""));
        user.put(COUNTRY_ID,
                pref.getString(COUNTRY_ID, ""));
        return user;
    }
}
