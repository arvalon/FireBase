package ru.arvalon.firewalker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static ru.arvalon.firewalker.MainActivity.TAG;

/** Created by arvalon on 07.02.2018 */

public class MessageService extends FirebaseMessagingService {

    private static final int SIMPLE_NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
    private static final String NOTIFICATION_CHANNEL_NAME = "my_notification_channel_name";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG,"Farebase Cloud Message");

        if (remoteMessage.getData().size()>0){
            Log.d(TAG,"Date: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification()!=null){

            Log.d(TAG,"Notification title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG,"Notification text: " + remoteMessage.getNotification().getBody());

            Log.d(TAG,"Map<String,String>");
            for (Map.Entry<String, String> stringStringEntry : remoteMessage.getData().entrySet()) {
                Log.d(TAG,"Key = "+stringStringEntry.getKey()+" Value = "+stringStringEntry.getValue());
            }

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel notificationChannel =
                        new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                                NOTIFICATION_CHANNEL_NAME,
                                NotificationManager.IMPORTANCE_DEFAULT);

                // Configure the notification channel.
                notificationChannel.setDescription("Channel description");
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);
                nm.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.arrow_down_float)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody());

            Notification notification = builder.build();

            nm.notify(SIMPLE_NOTIFICATION_ID,notification);
        }
    }
}