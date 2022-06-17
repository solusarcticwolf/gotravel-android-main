package com.phptravelsnative.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phptravelsnative.R;

import java.util.ArrayList;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {
    ArrayList<String> Hash_file_amenities2=new ArrayList<String>();//Creating arraylist
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.label);

        }
    }
    public AmenitiesAdapter( ArrayList<String> Hash_file_amenities2) {
        this.Hash_file_amenities2 = Hash_file_amenities2;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.animationsdesgin, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String name  =Hash_file_amenities2.get(position);

        holder.title.setText(name);
    }
    @Override
    public int getItemCount() {
        return Hash_file_amenities2.size();
    }
}