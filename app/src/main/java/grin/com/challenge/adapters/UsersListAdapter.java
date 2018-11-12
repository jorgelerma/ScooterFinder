package grin.com.challenge.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import grin.com.challenge.R;
import grin.com.challenge.models.Scooter;
import grin.com.challenge.models.Users;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private List<Users> mUsersList;
    private Context mCtx;

    public UsersListAdapter(Context ctx) {
        mCtx = ctx;
        mUsersList = new ArrayList<>();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView textId;
        TextView textCode;
        TextView textBattery;
        TextView latitud, longitude, times;

        public ViewHolder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.text_id);
            textCode = itemView.findViewById(R.id.text_code);
            textBattery = itemView.findViewById(R.id.text_battery);
            latitud = itemView.findViewById(R.id.text_lat);
            longitude = itemView.findViewById(R.id.text_long);
            times = itemView.findViewById(R.id.text_time);
        }
    }

    public void addUser(Users user)
    {
        mUsersList.add(user);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new UsersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Users user = mUsersList.get(position);
        holder.textId.setText(user.id);
        holder.textCode.setText(user.code);
        holder.latitud.setText(String.valueOf(user.latitude));
        holder.latitud.setText(String.valueOf(user.longitude));
        holder.textBattery.setText(user.battery + "%");




    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Users user = mUsersList.get(position);
        holder.textId.setText(user.id);
        holder.textCode.setText(user.code);
        holder.latitud.setText(String.valueOf(user.latitude));
        holder.latitud.setText(String.valueOf(user.longitude));
        holder.textBattery.setText(user.battery + "%");
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }
}
