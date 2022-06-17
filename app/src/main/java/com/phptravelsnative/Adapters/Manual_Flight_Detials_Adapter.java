package com.phptravelsnative.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Manual_Flight_Detials_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<OneWay> data;

    private LayoutInflater inflater;
    ManualFlightModel manualFlightModel;


    private int previousPosition = 0;
    int c=1;

    public Manual_Flight_Detials_Adapter(Context context, ArrayList<OneWay> data, String type,ManualFlightModel manualFlightModel) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.manualFlightModel = manualFlightModel;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.child_manualoneway_flight2, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        MyViewHolder myViewHolder = (MyViewHolder)holder;
        OneWay flightModel = data.get(position);
        /*myViewHolder.a_date.setText(flightModel.getTo_date());
        myViewHolder.d_date.setText(flightModel.getDate());
        myViewHolder.a_time.setText(flightModel.getTo_time());
        myViewHolder.d_time.setText(flightModel.getTime());
        myViewHolder.l_from.setText(flightModel.getName());
        myViewHolder.l_to.setText(flightModel.getTo_name());*/


        myViewHolder.l_from.setText(flightModel.getCode()  +" — "+ flightModel.getTo_code() );
        myViewHolder.a_time.setText((flightModel.getTime()));
        myViewHolder.d_time.setText((flightModel.getTo_time()));

        myViewHolder.d_date.setText(flightModel.getName());
        myViewHolder.a_date.setText(flightModel.getTo_name());

        myViewHolder.stops.setText("stop : "+c);
        myViewHolder.total_time.setText(manualFlightModel.getTotal_time()+" h");
        myViewHolder.flight_number.setText(manualFlightModel.getAero_name() +" - "+flightModel.getFlight_no());
        myViewHolder.vehicle_type.setText(manualFlightModel.getAero_name()+" : "+flightModel.getFlight_no());
        myViewHolder.company_name.setText(" Air LIne" +manualFlightModel.getAero_name());

      //  final String linkimage= Constant.linkimage +flightModel.getAirline()+Constant.linkimagetype;


        Picasso.with(context).load(flightModel.getImg())
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



        c=c+1;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView l_from;
        TextView l_to;
        TextView d_time;
        TextView a_time;
        TextView d_date;
        TextView a_date,flight_number,stops,total_time,vehicle_type,company_name;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            l_from = (TextView) itemView.findViewById(R.id.departure_from);
            d_time = (TextView) itemView.findViewById(R.id.departure_timefrom);
            a_time = (TextView) itemView.findViewById(R.id.departure_timeto);
            d_date = (TextView) itemView.findViewById(R.id.tv_departure_from);
            a_date = (TextView) itemView.findViewById(R.id.tv_arrival_to);
            flight_number = (TextView) itemView.findViewById(R.id.flight_number);
            stops = (TextView) itemView.findViewById(R.id.stops);
            total_time = (TextView) itemView.findViewById(R.id.total_time);
            vehicle_type = (TextView) itemView.findViewById(R.id.vehicle_type);
            company_name = (TextView) itemView.findViewById(R.id.company_name);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);
        }
    }


    private String gettime(int tripBookedTime) {


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tripBookedTime * 1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(" hh:mm");
        return sdf.format(d);
    }

    private String getdate(int tripBookedTime) {


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tripBookedTime * 1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);

    }
}
