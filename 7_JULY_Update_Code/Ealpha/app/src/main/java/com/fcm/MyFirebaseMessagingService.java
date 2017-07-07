package com.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ealpha.R;
import com.ealpha.homeclick.ProductDetailActivityPP;
import com.ealpha.main.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by android_studio on 17/2/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private String product_link_popular = "", vMessage = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Message data payload: ");
        if (remoteMessage.getData().size() > 0) {
            vMessage = "";
            try {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                Log.e("dataChat", remoteMessage.getData().toString());
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);
                System.out.println("notification_data1..." + object.toString());
                vMessage = object.getString("message");
                try {
                    product_link_popular = object.getString("product_link");
                } catch (Exception e) {

                }
                try {
                    vMessage = object.getString("message");
                } catch (Exception e) {

                }
                if (product_link_popular == null) {
                    product_link_popular = "http://ealpha.com//mob/customers.php?get_data=product_data&product_id=10972";
                }
                if (product_link_popular != null) {
                    if (product_link_popular.trim().length() == 0) {
                        product_link_popular = "http://ealpha.com//mob/customers.php?get_data=product_data&product_id=10972";
                    }
                }
                sendNotification(vMessage);
            } catch (Exception e) {

            }
            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                sendNotification("FCM Notification");
            }
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ProductDetailActivityPP.class);
        intent.putExtra("product_link_popular", product_link_popular);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher2)
                .setContentTitle("Ealpha")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
