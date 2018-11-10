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

public class ScooterListAdapter extends RecyclerView.Adapter<ScooterListAdapter.ViewHolder> {

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView textId;
        TextView textCode;
        TextView textBattery;

        public ViewHolder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.text_id);
            textCode = itemView.findViewById(R.id.text_code);
            textBattery = itemView.findViewById(R.id.text_battery);
        }
    }

    private List<Scooter> mScooterList;
    private Context mCtx;


    public ScooterListAdapter(Context ctx) {
        mCtx = ctx;
        mScooterList = new ArrayList<>();
    }


    public void addScooter(Scooter scooter) {
        mScooterList.add(scooter);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scooter, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Scooter scooter = mScooterList.get(i);
        viewHolder.textId.setText(scooter.id);
        viewHolder.textCode.setText(scooter.code);
        viewHolder.textBattery.setText(scooter.battery + "%");
    }

    @Override
    public int getItemCount() {
        return mScooterList.size();
    }
}
