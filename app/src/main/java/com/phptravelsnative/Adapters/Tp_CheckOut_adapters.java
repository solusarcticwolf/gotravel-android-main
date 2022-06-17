package com.phptravelsnative.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phptravelsnative.Activities.CheckOutDetails;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortDetails;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Tp_CheckOut_adapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private ArrayList<TravelPortDetails> data;

    private LayoutInflater inflater;


    private int previousPosition = 0;

    public Tp_CheckOut_adapters(Context context, ArrayList<TravelPortDetails> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        return  0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

            View view = inflater.inflate(R.layout.tp_details_child, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        TravelPortDetails travelPortDetails = data.get(position);

        MyViewHolder viewHolder  = (MyViewHolder)holder;

        viewHolder.time_from.setText(travelPortDetails.getTime_from());
        viewHolder.time_to.setText(travelPortDetails.getTime_to());
        viewHolder.location_from.setText(travelPortDetails.getLocation_from());
        viewHolder.location_from.setText(travelPortDetails.getLocation_to());
        viewHolder.total_time.setText(travelPortDetails.getDate_to());
        viewHolder.class_type.setText(travelPortDetails.getDate_from());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView time_from;
        TextView time_to;
        TextView location_from;
        TextView location_to;
        TextView class_type;
        TextView total_time;



        public MyViewHolder(View itemView) {
            super(itemView);

            time_from = (TextView) itemView.findViewById(R.id.time_from);
            time_to = (TextView) itemView.findViewById(R.id.time_to);
            location_from = (TextView) itemView.findViewById(R.id.location_from);
            location_to = (TextView) itemView.findViewById(R.id.location_to);
            class_type = (TextView) itemView.findViewById(R.id.class_type);
            total_time = (TextView) itemView.findViewById(R.id.total_time);

        }
    }
}
