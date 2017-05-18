package com.p3app2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * TODO Rohan will do this activity
 */

public class ChatActivity extends AppCompatActivity {

    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Dickshouse", Context.MODE_PRIVATE);
        boolean enable = sharedPreferences.getBoolean(SettingsFragment.KEY_ANON, true);

        if (enable) {
            this.setTheme(R.style.AppTheme);
        } else {
            this.setTheme(R.style.anonTheme);
        }

        setContentView(R.layout.activity_chat);

        getSupportActionBar().setTitle("Chat");
    }
}
