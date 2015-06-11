package com.example.ak_x64.srmclient3_v2.app.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.ak_x64.srmclient3_v2.R;
import com.example.ak_x64.srmclient3_v2.app.system.FileLocation;
import com.example.ak_x64.srmclient3_v2.app.ui.viewer.Activity_Viewer;
import com.example.ak_x64.srmclient3_v2.app.ui.wall.Activity_Wall;

/**
 * Created by AK on 23-03-2015.
 */
public class Notifications {

    public final static  String TAG="network";

public static void displayNotification(Context ctx,NotificationTypes ntypes){
    Log.d(TAG, "Notifications.displayNotification(Context,NotificationTypes); Display for " + ntypes);
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(ctx)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(ntypes.getNotificationTitle())
                    .setContentText(ntypes.getNoficationContent());
// Creates an explicit intent for an Activity in your app
    Intent i = new Intent(ctx, Activity_Viewer.class);
    i.putExtra("filepath", FileLocation.attendanceFile.getPath());
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
// Adds the back stack for the Intent (but not the Intent itself)
    stackBuilder.addParentStack(Activity_Wall.class);
// Adds the Intent that starts the Activity to the top of the stack
    stackBuilder.addNextIntent(i);
    PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
    mBuilder.setContentIntent(resultPendingIntent);

    NotificationManager mNotificationManager =
            (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
    mBuilder.setAutoCancel(true);
    mNotificationManager.notify(ntypes.getNotifID(), mBuilder.build());
}

}
