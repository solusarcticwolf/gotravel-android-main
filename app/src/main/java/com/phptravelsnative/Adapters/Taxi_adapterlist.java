package com.phptravelsnative.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.phptravelsnative.Activities.Bookingflight;
import com.phptravelsnative.Activities.Manual_flight_detials;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.Models.Taxi_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;

public class Taxi_adapterlist extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<Taxi_model> data;

    private LayoutInflater inflater;




    int c=1;
    public Taxi_adapterlist(Context context, ArrayList<Taxi_model> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.child_manualoneway_taxi, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        MyViewHolder myViewHolder = (MyViewHolder)holder;
        final Taxi_model taxi_model = data.get(position);

        myViewHolder.tv_price.setText(taxi_model.getPrice());
        myViewHolder.tv_currency.setText(taxi_model.getCurrencycode() );
        myViewHolder.tv_flight_duration_title.setText(taxi_model.getCarname());
        myViewHolder.tv_bag.setText(taxi_model.getBag());
        myViewHolder.tv_passenger.setText(" "+taxi_model.getPax());

        int quotient = position/2;
        if(quotient*2== position){
            myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_itemgrey);
        }else
        {
            myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_item);
        }
      //  final String linkimage= Constant.linkimage +flightModel.getAirline()+Constant.linkimagetype;

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String myJson = gson.toJson(taxi_model);
                Intent intent = new Intent(context, Bookingflight.class);
                intent.putExtra("taxi_model", myJson);
                intent.putExtra("Datatype", "taxi_model");
                context.startActivity(intent);
                //Activity activity = (Activity) context;
                //activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                // activity.startActivity(intent);
            }
        });

        Picasso.with(context).load(taxi_model.getImg())
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(myViewHolder.main_id,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });

        c=c+1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_price,tv_bag,tv_passenger;
        TextView tv_currency,tv_flight_duration_title;
        ImageView main_id;
        RelativeLayout backgroundcolor;

        public MyViewHolder(View itemView) {
            super(itemView);


            backgroundcolor = itemView.findViewById(R.id.backgroundcolor);
            tv_passenger  = (TextView) itemView.findViewById(R.id.tv_passenger);
            tv_flight_duration_title  = (TextView) itemView.findViewById(R.id.tv_flight_duration_title);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_currency = (TextView) itemView.findViewById(R.id.tv_currency);
            tv_bag = (TextView) itemView.findViewById(R.id.tv_bag);
            main_id = (ImageView) itemView.findViewById(R.id.main_id);

        }
    }



}
