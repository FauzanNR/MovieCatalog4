package com.example.moviecatalog4.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviecatalog4.MainActivity;
import com.example.moviecatalog4.R;

import java.util.Calendar;

public class ReminderEveryday extends BroadcastReceiver {

    private void broadcastNotification(Context context, String title, String desc, int id) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(context, MainActivity.class);

        PendingIntent mPendingIntent = PendingIntent.getActivity(context, id, mIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(mPendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(uriTone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mNotificationChannel = new NotificationChannel(
                    "11001", "NOTIFICATION_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setLightColor(Color.YELLOW);
            mNotificationChannel.enableVibration(true);
            mNotificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId("11001");
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }
        mNotificationManager.notify(id, builder.build());

    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent mIntent = new Intent(context, ReminderEveryday.class);
        return PendingIntent.getBroadcast(context, 1001, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public void setAlarm(Context context) {

        cancelAlarm(context);
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, 7);
        mCalendar.set(Calendar.MINUTE, 0);
        mCalendar.set(Calendar.SECOND, 0);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,getPendingIntent(context));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(), getPendingIntent(context));
        }



        Toast.makeText(context, "Daily Reminder ON", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.cancel(getPendingIntent(context));

        Toast.makeText(context, "Daily Reminder OFF", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceive(Context context, Intent mIntent) {
        broadcastNotification(context, context.getString(R.string.reminder_everyday),
                context.getString(R.string.summary_reminder_everyday), 1001);
    }
}
