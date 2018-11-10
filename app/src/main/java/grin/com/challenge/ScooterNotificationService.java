package grin.com.challenge;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;


public class ScooterNotificationService extends Service {

    private static final String TAG = "CHALLENGE";
    private static final String PUBNUB_SUBSCRIBE_KEY = "sub-c-2b9f3e2a-e4c1-11e8-a679-1679df73129d";
    private static final int NOTIFICATION_ID = 93845;

    public static final String EXTRA_SCOOTER = "scooter";
    public static final String ACTION_NEW_SCOOTER = "com.grin.ACTION_NEW_SCOOTER";

    public static final String EXTRA_RECONNECT = "EXTRA_RECONNECT";
    private static PubNub mPubnub;


    public static void reconnect(Context appCtx) {
        Intent intent = new Intent(appCtx, ScooterNotificationService.class);
        intent.putExtra(EXTRA_RECONNECT, true);

        appCtx.stopService(intent);
        appCtx.startService(intent);
    }


    public static void setEnabled(Context appCtx, boolean enabled) {
        Intent intent = new Intent(appCtx, ScooterNotificationService.class);
        if (enabled) appCtx.startService(intent);
        else appCtx.stopService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "Notification Service Created");

        if (mPubnub == null) {
            PNConfiguration pnConfiguration = new PNConfiguration();
            pnConfiguration.setSubscribeKey(PUBNUB_SUBSCRIBE_KEY);
            mPubnub = new PubNub(pnConfiguration);
        }
        Log.v(TAG, "Hash " + mPubnub.hashCode());
        subscribeToNotificationProvider(getApplicationContext(), "channel_1");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("Scooter Notification")
                        .setContentText("Listo para recibir notificaciones")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Listo para recibir notificaciones"));
        startForeground(NOTIFICATION_ID, builder.build());
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPubnub.unsubscribeAll();
        stopForeground(true);
    }


    public void unSuscribe() {
        if (mPubnub != null) {
            mPubnub.unsubscribeAll();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void subscribeToNotificationProvider(final Context ctx, String channel) {
            mPubnub.addListener(new SubscribeCallback() {
                @Override
                public void status(PubNub pubnub, PNStatus status) {
                    if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                        Log.v(TAG, "Connectivity Lost");
                    } else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                        Log.v(TAG, "Connected");
                    } else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                        Log.v(TAG, "Reconnected");
                    } else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {
                        Log.v(TAG, "Decryption Error");
                    }
                }

                @Override
                public void message(PubNub pubnub, PNMessageResult message) {
                    if (message.getChannel() != null) {
                        Log.v(TAG, "New Message: " + message.getMessage());
                        // Message has been received on channel group stored in
                        // message.getChannel()
                    } else {
                        // Message has been received on channel stored in
                        // message.getSubscription()
                    }
                }

                @Override
                public void presence(PubNub pubnub, PNPresenceEventResult presence) {

                }
            });
            mPubnub.subscribe().channels(Arrays.asList(channel)).execute();
    }



}
