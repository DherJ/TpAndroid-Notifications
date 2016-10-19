package com.jdher.tpandroid_notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class VoletNotification extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volet_notification);

        Button notificationButton = (Button) findViewById(R.id.button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationVibration("Mon titre", "Mon text");
            }
        });

        Button nextActivityButton = (Button) findViewById(R.id.nextActivityButton);
        nextActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });

        Button deleteNotificationButton = (Button) findViewById(R.id.deleteNotificationButton);
        deleteNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotification();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void createNotificationVibration(String title, String body){

        // On instancie le service de notification
        final NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // On créé un intent, avec un context et une Activity
        // Lorsque nous cliquerons sur cette notification, nous retournerons à l'activity déclarée dans le 2nd paramètre
        final Intent launchNotificationIntent = new Intent(this, MainActivity.class);
        // On dclare un pending intent pour dire qu'on attend une action sur cette notification
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                REQUEST_CODE, launchNotificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        // Intent pour l'action sur un élément dans la notification
        Intent actionNotificationIntent = new Intent(this, MainActivity.class);


        // On construit notre notification
        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker(body)
                .setSmallIcon(R.drawable.notification)
                .setContentText(body)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.play,"Play", PendingIntent.getActivity(this, 0, actionNotificationIntent, 0) )
                .setVibrate(new long[] {0,200,100,200,100,200});

        nm.notify(NOTIFICATION_ID, builder.build());
    }

    private void deleteNotification(){
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //la suppression de la notification se fait grâce à son ID
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public void goToNextActivity() {
        Intent intent = new Intent(this, VoletNotificationCustomLayout.class);
        startActivity(intent);
    }
}