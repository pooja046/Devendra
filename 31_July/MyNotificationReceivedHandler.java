package com.fcm;

import android.content.Context;
import android.content.SharedPreferences;

import com.ealpha.main.MainActivity;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.ps.utility.SessionManager;

import org.json.JSONObject;

/**
 * Created by androidbash on 12/14/2016.
 */

//This will be called when a notification is received while your app is running.
public class MyNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
    private SessionManager sessionManager;
    private Context context;

    public MyNotificationReceivedHandler(Context context) {
        this.context = context;
        sessionManager = new SessionManager(this.context);
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        try {
            String data = notification.payload.title;
            if (data != null) {
                if (data.trim().length() > 0) {
                    System.out.println("notificaiton_data..." + data.toString());
                    try {
                        int counter = sessionManager.isNotificationCount();
                        counter++;
                        sessionManager.notificationCount(counter);
                        System.out.println("notificaiton_data_counter..." + sessionManager.isNotificationCount());
                        try {
                            if (MainActivity.mainActivity != null) {
                                MainActivity.mainActivity.setBadgeOnNotification();
                            }
                        } catch (Exception e) {

                        }
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {

        }
    }
}