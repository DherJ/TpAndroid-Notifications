package com.jdher.tpandroid_notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

@SuppressWarnings("deprecation")
public class VoletNotificationCustomLayout extends AppCompatActivity {

    private static final int NOTIFICATION_ID_1 = 1;
    private static final int NOTIFICATION_ID_2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volet_notification_custom_layout);

        Button notifyButton = (Button) findViewById(R.id.notification);
        Button customNotifyButton = (Button) findViewById(R.id.customnotification);

        notifyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                displayNotification();
            }
        });

        customNotifyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                displayCustomLayoutNotification();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteNotifications();
    }

    public void displayCustomLayoutNotification() {

        int icon = R.drawable.notification;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, "Custom Layout Notification", when);

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout_notification);
        contentView.setTextViewText(R.id.title, getString(R.string.customnotificationtitle));
        contentView.setTextViewText(R.id.text, getString(R.string.customnotificationtext));
        contentView.setImageViewResource(R.id.image, R.drawable.bean);

        notification.contentView = contentView;

        // Si on clique sur la notification on retourne sur l'activité principale
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notification.contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // TODO changez les valeurs que vous voulez :) et testez
        notification.flags |= Notification.FLAG_NO_CLEAR; // Cant clear the notification :-/
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.category = Notification.CATEGORY_MESSAGE; // CATEGORY
        }
        notification.defaults |= Notification.DEFAULT_VIBRATE; // Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(NOTIFICATION_ID_2, notification);
    }

    protected void displayNotification() {

        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(getString(R.string.newMessage));
        mBuilder.setContentText(getString(R.string.clickMe));
        mBuilder.setTicker(getString(R.string.customnotificationticker));
        mBuilder.setSmallIcon(R.drawable.notification);

        int numMessages = 0;

        mBuilder.setNumber(++numMessages);

        /* Au clic sur la notification on est dirigé sur l'activity NotificationView */
        Intent resultIntent = new Intent(this, NotificationView.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

        /** Le taskStackBuilder permet de définir l'activity parente de la notifiction, si on appuie sue le
          * bouton back après avoir cliqué sur la notification, on retourne sur la vue VoletNotificaion
         **/
        /**
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(VoletNotification.class);
        stackBuilder.addNextIntent(resultIntent);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
         PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        **/

        PendingIntent resultPendingIntent = PendingIntent.getActivity(getBaseContext(),0,resultIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(NOTIFICATION_ID_1, mBuilder.build());
    }


    private void deleteNotifications(){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //la suppression de la notification se fait grâce à son ID
        notificationManager.cancel(NOTIFICATION_ID_1);
        notificationManager.cancel(NOTIFICATION_ID_2);
    }
}