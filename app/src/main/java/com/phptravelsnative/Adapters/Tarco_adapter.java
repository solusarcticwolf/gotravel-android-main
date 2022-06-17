package com.phptravelsnative.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Activities.Manual_flight_detials_New;
import com.phptravelsnative.Models.Airline;
import com.phptravelsnative.Models.Tarco_Model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tarco_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<Tarco_Model> data;
    View view ;
    private LayoutInflater inflater;
    Boolean imageshow;
    public Tarco_adapter(Context context, ArrayList<Tarco_Model> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {



        view = inflater.inflate(R.layout.tarc_desgin, parent, false);
        Tarco_adapter.MyViewHolder holder = new Tarco_adapter.MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        final Tarco_adapter.MyViewHolder myViewHolder = (Tarco_adapter.MyViewHolder)holder;
        final Tarco_Model flightModel = data.get(position);

        myViewHolder.tv_price.setText(flightModel.getPrice());
        myViewHolder.tv_currency.setText(flightModel.getCurrency_code());


        String  timeeee= date_time_return(Integer.parseInt(flightModel.getDuration_time()));
        myViewHolder.tv_flight_duration_title.setText(timeeee);



        String time1=parseDateToddMMyyyy(flightModel.getDeparture_time());
        myViewHolder.tv_arrival_and_departure_time.setText(""+time1+" — "+parseDateToddMMyyyy(flightModel.getArrival_time()));

        String from = flightModel.getDeparture_code();
        String to = flightModel.getArrival_code();

        myViewHolder.tv_cities.setText(from+" — "+to);


        myViewHolder.tv_date.setText(flightModel.getDeparture_date());
        myViewHolder.tv_stops.setText("1");
      // myViewHolder.sumstp.setVisibility(View.GONE);

        final String linkimage= flightModel.getImg_code();
        Picasso.with(context).load(linkimage)
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(myViewHolder.imageView,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });



        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context,"click  me",Toast.LENGTH_LONG).show();

                Gson gson = new Gson();
                String myJson = gson.toJson(flightModel);
                Intent intent = new Intent(context, Manual_flight_detials_New.class);
                intent.putExtra("flight_Tarco", myJson);
                intent.putExtra("tourtype", "oneway");
                intent.putExtra("Model","Tarco");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                //Activity activity = (Activity) context;
                //activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        RobotoTextView tv_arrival_and_departure_time;
        RobotoTextView tv_flight_duration_title;
        RobotoTextView tv_currency;
        TextView company_name;
        ImageView imageView;
        CheckBox chkWindows;
        RobotoTextView tv_price,tv_date;
        RobotoTextView tv_cities,tv_stops;

        LinearLayout sumstp;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_cities = itemView.findViewById(R.id.tv_cities);
            tv_price = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.main_id);
            tv_currency=itemView.findViewById(R.id.tv_currency);
            tv_flight_duration_title = itemView.findViewById(R.id.tv_flight_duration_title);
            tv_date= itemView.findViewById(R.id.tv_date);
            tv_arrival_and_departure_time  = itemView.findViewById(R.id.tv_arrival_and_departure_time);
            sumstp= itemView.findViewById(R.id.sumstp);
            tv_stops =itemView.findViewById(R.id.tv_stops);
        }
    }




    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "hh:mm:aa";
        String outputPattern = "hh:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }



    public String date_time_return(int minute)
    {
        int hour = minute / 60;
        minute %= 60;
        String p = "AM";
        if (hour >= 12) {
            hour %= 12;
            p = "PM";
        }
        if (hour == 0) {
            hour = 12;
        }
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + " " + p;
    }
}

