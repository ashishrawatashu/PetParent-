package com.cynoteck.petofyparents.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.activity.DashBoardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.cynoteck.petofyparents.activity.DashBoardActivity.channel_id;

public class MyService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null)
        {
            String title=remoteMessage.getNotification().getTitle();
            String text=remoteMessage.getNotification().getBody();

            showNotification(getApplicationContext(), title, text);
        }
    }

    public void showNotification(Context context,String title,String body){


        Log.d("show","running");
        Intent intent=new Intent(context, DashBoardActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder=new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(1, builder.build());


    }
}
