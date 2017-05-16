package com.p3app2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.p3app2.Chat_Window.ChatWindowActivity;

import static java.lang.Thread.sleep;

/**
 * Created by prashantanantharaman on 5/15/17.
 */

public class MessageReceiverService extends IntentService {

    static NotificationManager notificationManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public MessageReceiverService() {
        super("MessageReceiverService");
    }

    public MessageReceiverService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        setNotification();
    }

    void setNotification() {
        Log.d("Set notif", "Set notif");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ChatWindowActivity.class)
                .addCategory(Intent.CATEGORY_LAUNCHER).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_dartmouth_f)
                        .setContentTitle("Chat Session is on")
                        .setContentText("Ask Tabby anything!");
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
        Globals.currentnotifications = true;
    }

    public static void unsetNotification()
    {
        if(Globals.currentnotifications == true)
        {
            notificationManager.cancel(0);
            Globals.currentnotifications = false;
        }
    }
}
