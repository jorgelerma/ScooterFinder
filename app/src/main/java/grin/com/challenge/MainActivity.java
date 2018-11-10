package grin.com.challenge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import grin.com.challenge.models.Scooter;


public class MainActivity extends AppCompatActivity {

    private EditText mEditToken;


    protected BroadcastReceiver newScooterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Scooter scooter = intent.getParcelableExtra(ScooterMessagingService.EXTRA_SCOOTER);
            addScooter(scooter);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditToken = findViewById(R.id.edit_token);

        // Subscribe to new Scooter receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScooterMessagingService.ACTION_NEW_SCOOTER);
        registerReceiver(newScooterReceiver, intentFilter);

        getToken();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newScooterReceiver);
    }


    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("CHALLENGE", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        mEditToken.setText(token);

                        // Log and toast
                        String msg = "Token: " + token;
                        Log.d("CHALLENGE", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void addScooter(Scooter scooter) {
        Toast.makeText(this, "New Scooter " + scooter.code, Toast.LENGTH_LONG).show();
    }
}
