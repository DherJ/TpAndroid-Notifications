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

public class VoletNotificationCustomLayout extends AppCompatActivity {

    private Button notifyButton;
    private Button customNotifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volet_notification_custom_layout);

        notifyButton = (Button) findViewById(R.id.notification);

        customNotifyButton = (Button) findViewById(R.id.customnotification);

        notifyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                displaySimpleNotification();
            }
        });

        customNotifyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                displayNotification();
            }
        });
    }

    public void displaySimpleNotification() {

        int icon = R.drawable.notification;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, "Custom Notification", when);

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout_notification);
        contentView.setTextViewText(R.id.title, "Custom notification");
        contentView.setTextViewText(R.id.text, "This is a custom layout");
        contentView.setImageViewResource(R.id.image, R.drawable.bean);
        notification.contentView = contentView;

        // Si on clique sur la notification on retourne sur l'activité principale
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(1, notification);
    }

    protected void displaySimpleNotification3() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // END_INCLUDE(notificationCompat)

        // BEGIN_INCLUDE(intent)
        //Create Intent to launch this Activity again if the notification is clicked.
        Intent i = new Intent(this, NotificationView.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);
        // END_INCLUDE(intent)

        // BEGIN_INCLUDE(ticker)
        // Sets the ticker text
        builder.setTicker(getResources().getString(R.string.customnotificationticker));

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.drawable.notification);
        // END_INCLUDE(ticker)

        // BEGIN_INCLUDE(buildNotification)
        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();
        // END_INCLUDE(buildNotification)

        // BEGIN_INCLUDE(customLayout)
        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout_notification);

        // Set text on a TextView in the RemoteViews programmatically.
        final String text = getString(R.string.customnotificationtext);
        contentView.setTextViewText(R.id.textView, text);

        /* Workaround: Need to set the content view here directly on the notification.
         * NotificationCompatBuilder contains a bug that prevents this from working on platform
         * versions HoneyComb.
         * See https://code.google.com/p/android/issues/detail?id=30495
         */
        notification.contentView = contentView;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            RemoteViews expandedView =
                    new RemoteViews(getPackageName(), R.layout.custom_layout_notification);
            notification.bigContentView = expandedView;
        }
        // END_INCLUDE(customLayout)

        // START_INCLUDE(notify)
        // Use the NotificationManager to show the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
        // END_INCLUDE(notify)
    }

    protected void displaySimpleNotification2() {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, NotificationView.class);
        notificationIntent.putExtra("reminder", "rr");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        RemoteViews notificationView = new RemoteViews(
                this.getPackageName(),
                R.layout.custom_layout_notification
        );
        notificationView.setImageViewResource(
                R.id.imagenotileft,
                R.drawable.bean2);
        notificationView.setTextViewText(R.id.textView, "Hiii");//First Textview
        notificationView.setTextViewText(R.id.textView2, "Good");//Second Textview

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("HIiiiiiii")
                .setAutoCancel(false)
                .setContentIntent(contentIntent)
                .setContent(notificationView);

        Notification notification = builder.build();

        mNotificationManager.notify(2, notification);
    }


    protected void displayNotification() {

        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(getString(R.string.newMessage));
        mBuilder.setContentText(getString(R.string.clickMe));
        mBuilder.setTicker(getString(R.string.customnotificationticker));
        mBuilder.setSmallIcon(R.drawable.notification);

        int numMessages = 0;

   /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);

        // Sets a title for the Inbox style big view
        //inboxStyle.setBigContentTitle("Big Title Details:");

    /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(this, NotificationView.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);

   /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(1, mBuilder.build());
    }


    public void CustomNotification2() {
        int NOTIFICATION_ID = 1;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        int icon = R.drawable.notification;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, getString(R.string.customnotificationtext), when);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout_notification);
        contentView.setImageViewResource(R.id.image, R.drawable.bean2);
        contentView.setTextViewText(R.id.title, "My custom notification title");
        //contentView.setTextViewText(R.id.notification_text, "My custom notification text");
        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this, VoletNotificationCustomLayout.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.contentIntent = contentIntent;
        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(NOTIFICATION_ID, notification);

    }
}