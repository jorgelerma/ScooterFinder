package grin.com.challenge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import grin.com.challenge.adapters.ScooterListAdapter;
import grin.com.challenge.adapters.UsersListAdapter;

import grin.com.challenge.models.Scooter;
import grin.com.challenge.models.Users;


public class MainActivity extends AppCompatActivity {

    private TextView mTextWaiting;
    private RecyclerView mRecyclerScooters;
    private RecyclerView mRecyclerUsers;
    private ScooterListAdapter mAdapter;
    private UsersListAdapter mUserAdapter;

    protected BroadcastReceiver newScooterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Scooter scooter = intent.getParcelableExtra(ScooterMessagingService.EXTRA_SCOOTER);
            addScooter(scooter);


        }
    };


    protected BroadcastReceiver newUserReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Users user = intent.getParcelableExtra(UsersNotificationService.EXTRA_USER);
            addUser(user);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextWaiting = findViewById(R.id.text_waiting);
        mRecyclerScooters = findViewById(R.id.recycler_scooters);
        mRecyclerUsers = findViewById(R.id.recycler_users);

        // Subscribe to new Scooter receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsersNotificationService.ACTION_NEW_USER);
        registerReceiver(newUserReceiver, intentFilter);

        // Configure List
//        mRecyclerScooters.setHasFixedSize(true);
//        mRecyclerScooters.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerScooters.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        mAdapter = new ScooterListAdapter(this);
//        mRecyclerScooters.setAdapter(mAdapter);

        //Configure User List
        mRecyclerUsers.setHasFixedSize(true);
        mRecyclerUsers.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerUsers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mUserAdapter = new UsersListAdapter(this);
        mRecyclerUsers.setAdapter(mUserAdapter);

        getToken();

        PubnubReceiver.setAlarmState(this, true);
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

                        // Log and toast
                        String msg = "Token: " + token;
                        Log.d("CHALLENGE", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void addScooter(Scooter scooter) {
        mTextWaiting.setVisibility(View.GONE);
        mAdapter.addScooter(scooter);
        mAdapter.notifyDataSetChanged();
    }

    private void  addUser(Users user){
        mTextWaiting.setVisibility(View.GONE);
        mUserAdapter.addUser(user);
        mUserAdapter.notifyDataSetChanged();
    }
}
