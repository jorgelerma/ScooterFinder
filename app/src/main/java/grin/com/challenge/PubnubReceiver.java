package grin.com.challenge;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class PubnubReceiver extends BroadcastReceiver {
    private static final int ALARM_INTERVAL = (int) (0.1f * 60 * 1000);

    public static void setAlarmState(Context appContext, boolean state) {
        Intent alarmIntent = new Intent(appContext, PubnubReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, 0, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        long alarmTime = System.currentTimeMillis() + (state ? 0 : 1000000);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, ALARM_INTERVAL,
                pendingIntent);
        if (!state)
            manager.cancel(pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ScooterNotificationService.reconnect(context);
    }
}
