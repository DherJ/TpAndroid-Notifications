package com.jdher.tpandroid_notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
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

        mNotificationManager.notify(1, notification);
    }

    protected void displaySimpleNotification3() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //Create Intent to launch this Activity again if the notification is clicked.
        Intent i = new Intent(this, NotificationView.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);

        // Sets the ticker text
        builder.setTicker(getResources().getString(R.string.customnotificationticker));

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.notification);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout_notification);

        // Set text on a TextView in the RemoteViews programmatically.
        final String text = getString(R.string.customnotificationtext);
        contentView.setTextViewText(R.id.textView, text);
        notification.contentView = contentView;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            notification.bigContentView = new RemoteViews(getPackageName(), R.layout.custom_layout_notification);
        }

        // Use the NotificationManager to show the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
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

        /** Le taskStackBuilder permet de définir l'activity parente de la notifiction, si on appuie sue le
          * bouton back après avoir cliqué sur la notification, on retourne sur la vue VoletNotificaion
         **/
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(VoletNotification.class);

        /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}