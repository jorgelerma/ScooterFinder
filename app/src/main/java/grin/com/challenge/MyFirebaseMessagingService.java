package grin.com.challenge;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import grin.com.challenge.models.Scooter;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String ACTION_NEW_SCOOTER = "com.grin.ACTION_NEW_SCOOTER";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Gson gson = new Gson();
        Scooter scooter = gson.fromJson(remoteMessage.getData().get("scooter"), Scooter.class);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_NEW_SCOOTER);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
