package com.jdher.tpandroid_notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 1;
    private Button addNotificationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotificationButton = (Button) findViewById(R.id.add_notification);

        addNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSimpleNotification("Ajouter une notification Toaster");
            }
        });
    }

    private void createSimpleNotification(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void callVoletNotificationActivity(View v) {
        Intent intent = new Intent(this, VoletNotification.class);
        startActivity(intent);
    }
}