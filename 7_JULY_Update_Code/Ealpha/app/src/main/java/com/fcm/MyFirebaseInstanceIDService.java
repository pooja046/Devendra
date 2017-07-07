package com.fcm;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by android_studio on 17/2/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

//    private static final String TAG = "MyFirebaseIIDService";
//    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInPref(refreshedToken);
        takeDeviceToken();
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ealpha_2017", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }

    private String takeDeviceToken() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ealpha_2017", 0);
        String regId = pref.getString("regId", "");
        if (regId == null) {
            regId = "";
        } else {
            System.out.println("Device Token..." + regId);
        }
        return regId;
    }

}
