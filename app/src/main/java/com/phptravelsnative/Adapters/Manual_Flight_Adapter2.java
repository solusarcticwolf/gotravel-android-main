package com.phptravelsnative.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Activities.Manual_flight_detials;
import com.phptravelsnative.Activities.Manual_flight_detials_New;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.RoutesItem;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.R;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.utality.Extra.Constant;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Manual_Flight_Adapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private List<DataItem> data;

    private LayoutInflater inflater;
    TravelPort cabin_class;


    private int previousPosition = 0;

    public Manual_Flight_Adapter2(Context context, List<DataItem> data, TravelPort cabin_class) {

        this.context = context;
        this.data = data;
        this.cabin_class = cabin_class;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (cabin_class.getTour_type().equals("oneway"))
        {
            return  0;
        }else{
            return  1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        if (position == 1){
            View view = inflater.inflate(R.layout.child_manualoneway_flightdual, parent, false);
            MyViewHolder2 holder = new MyViewHolder2(view);
            return holder;
           // child_manualoneway_flight3
        }else{
            View view = inflater.inflate(R.layout.child_manualoneway_flight3, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(cabin_class.getTour_type().equals("oneway"))
        {
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            int quotient = position/2;
            if(quotient*2== position){
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_itemgrey);
            }else
            {
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_item);
            }
            final DataItem dataItem = data.get(position);
            ArrayList<RouteItem> routeItems_array = new ArrayList<>();
            routeItems_array = dataItem.getRoutes();
            RouteItem routeItem = routeItems_array.get(0);
            Gson gson = new Gson();
            String g = gson.toJson(routeItems_array);
            Log.d("bordingpoint2", ".dataItem"+g);


            ArrayList<RoutesItem> RoutesItem_array = new ArrayList<>();
            RoutesItem_array = dataItem.getRoute();

            RoutesItem routesItem_model = RoutesItem_array.get(0);

            String from = routesItem_model.getFrom();
            String to = routesItem_model.getTo();

            myViewHolder.tv_cities.setText(from+" — "+to);

            String formatdate  =getdate(Integer.parseInt(dataItem.getDeparture_time()));
            Log.d("formatdatformatdatee",formatdate);
            myViewHolder.tv_date.setText(formatdate);

            myViewHolder.tv_price.setText(dataItem.getFlight_price());
            myViewHolder.tv_currency.setText(dataItem.getCurrency());
            myViewHolder.tv_flight_duration_title.setText(dataItem.getFlight_duration());
            String departure_timetest  =gettime(Integer.parseInt(dataItem.getDeparture_time()));
            String arrival_timetest  =gettime(Integer.parseInt(dataItem.getArrival_time()));
            myViewHolder.tv_arrival_and_departure_time.setText(""+ departure_timetest+" — "+arrival_timetest);
            myViewHolder.stops.setText(dataItem.getStops());
            final String linkimage= Constant.linkimage + dataItem.getAirline()+Constant.linkimagetype;
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

                    Gson gson = new Gson();
                    String myJson = gson.toJson(dataItem);
                    Intent intent = new Intent(context, Manual_flight_detials_New.class);
                    intent.putExtra("dataItem", myJson);
                    intent.putExtra("tourtype", "oneway");
                    intent.putExtra("Model","travelhope");
                    intent.putExtra("cabin_class",cabin_class);
                    context.startActivity(intent);
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    // activity.startActivity(intent);


                }
            });

        }else{

            MyViewHolder2 myViewHolder = (MyViewHolder2)holder;
            int quotient = position/2;
            if(quotient*2== position){
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_itemgrey);
            }else
            {
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_item);
            }
            final DataItem dataItem = data.get(position);
            ArrayList<RouteItem> routeItems_array = new ArrayList<>();
            routeItems_array = dataItem.getRoutes();

            Gson gson = new Gson();
            String g = gson.toJson(routeItems_array);
            Log.d("bordingpoint2", ".dataItem"+g);


            ArrayList<RoutesItem> RoutesItem_array = new ArrayList<>();
            RoutesItem_array = dataItem.getRoute();

            RoutesItem routesItem_mode0 = RoutesItem_array.get(0);
            RoutesItem routesItem_mode1 = RoutesItem_array.get(1);


            String formatdate  =getdate(Integer.parseInt(dataItem.getDeparture_time()));
            myViewHolder.tv_date.setText(formatdate);





            String from = routesItem_mode0.getFrom();
            String to = routesItem_mode0.getTo();
            myViewHolder.tv_cities.setText(from+" — "+to);
            String from1 = routesItem_mode1.getFrom();
            String to1 = routesItem_mode1.getTo();
            myViewHolder.tv_cities1.setText(" "+from1+" — "+to1);



            myViewHolder.tv_price.setText(dataItem.getFlight_price());
            myViewHolder.tv_currency.setText(dataItem.getCurrency());
            myViewHolder.tv_flight_duration_title1.setText(dataItem.getFlight_duration());
            myViewHolder.tv_flight_duration_title.setText(dataItem.getFlight_duration());
            String departure_timetest  =gettime(Integer.parseInt(dataItem.getDeparture_time()));
            String arrival_timetest  =gettime(Integer.parseInt(dataItem.getArrival_time()));
            myViewHolder.tv_arrival_and_departure_time.setText(""+ departure_timetest+" — "+arrival_timetest);
            myViewHolder.stops.setText(dataItem.getStops());
            myViewHolder.tv_stops1.setText(dataItem.getStops());

            for (int routeItems_int  = 0 ; routeItems_int <routeItems_array.size();routeItems_int++ )
            {

                RouteItem routesItem= routeItems_array.get(routeItems_int);

                String departure_timetest1  =gettime(Integer.parseInt(routesItem.getDepartureTime()));
                String arrival_timetest1  =gettime(Integer.parseInt(routesItem.getArrivalTime()));
                myViewHolder.tv_arrival_and_departure_time1.setText(" "+ departure_timetest1+" — "+arrival_timetest1);
                String date2 = getdate(Integer.parseInt(routesItem.getArrivalTime()));
                myViewHolder.tv_date2.setText(date2);
            }




            final String linkimage= Constant.linkimage + dataItem.getAirline()+Constant.linkimagetype;
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







                    Gson gson = new Gson();
                    String myJson = gson.toJson(dataItem);

                    Intent intent = new Intent(context, Manual_flight_detials_New.class);
                    intent.putExtra("dataItem", myJson);
                    intent.putExtra("tourtype", "round");
                    intent.putExtra("tourtype", "round");
                    intent.putExtra("cabin_date", cabin_class);
                    intent.putExtra("Model","travelhope");
                    context.startActivity(intent);
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    // activity.startActivity(intent);


                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder{

        RobotoTextView stops;
        ImageView imageView;
        RobotoTextView tv_arrival_and_departure_time;
        RobotoTextView tv_flight_duration_title ;
        RobotoTextView tv_price;
        RobotoTextView tv_currency;
        RelativeLayout backgroundcolor;
        RobotoTextView tv_cities;
        RobotoTextView tv_cities1;
        RobotoTextView tv_arrival_and_departure_time1;
        RobotoTextView tv_stops1;
        RobotoTextView tv_flight_duration_title1;
        RobotoTextView tv_date,tv_date2;
        @SuppressLint("WrongViewCast")
        public MyViewHolder2(View itemView) {
            super(itemView);

            tv_date2= itemView.findViewById(R.id.tv_date2);
            tv_date= itemView.findViewById(R.id.tv_date);
            tv_stops1 = (RobotoTextView) itemView.findViewById(R.id.tv_stops1);
            tv_arrival_and_departure_time1 = itemView.findViewById(R.id.tv_arrival_and_departure_time1);
            tv_cities = itemView.findViewById(R.id.tv_cities);
            tv_cities1 = itemView.findViewById(R.id.tv_cities1);
            backgroundcolor = itemView.findViewById(R.id.backgroundcolor);
            stops = (RobotoTextView) itemView.findViewById(R.id.tv_stops);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);
            tv_arrival_and_departure_time  = (RobotoTextView) itemView.findViewById(R.id.tv_arrival_and_departure_time);
            tv_flight_duration_title  = (RobotoTextView) itemView.findViewById(R.id.tv_flight_duration_title);
            tv_price = (RobotoTextView) itemView.findViewById(R.id.tv_price);
            tv_currency= (RobotoTextView) itemView.findViewById(R.id.tv_currency);
            tv_flight_duration_title1 = itemView.findViewById(R.id.tv_flight_duration_title1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


        RobotoTextView stops;
        ImageView imageView;
        RobotoTextView tv_arrival_and_departure_time;
        RobotoTextView tv_flight_duration_title ;
        RobotoTextView tv_price;
        RobotoTextView tv_currency;
        RelativeLayout backgroundcolor;
        RobotoTextView tv_cities;
        RobotoTextView tv_date;
        @SuppressLint("WrongViewCast")
        public MyViewHolder(View itemView) {
            super(itemView);


            tv_date= itemView.findViewById(R.id.tv_date);
            backgroundcolor = itemView.findViewById(R.id.backgroundcolor);
            stops = (RobotoTextView) itemView.findViewById(R.id.tv_stops);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);
            tv_cities = itemView.findViewById(R.id.tv_cities);
            tv_arrival_and_departure_time  = (RobotoTextView) itemView.findViewById(R.id.tv_arrival_and_departure_time);
            tv_flight_duration_title  = (RobotoTextView) itemView.findViewById(R.id.tv_flight_duration_title);
            tv_price = (RobotoTextView) itemView.findViewById(R.id.tv_price);
            tv_currency= (RobotoTextView) itemView.findViewById(R.id.tv_currency);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    public String getCurrencySymbol(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol();
        } catch (Exception e) {
            return currencyCode;
        }
    }

    public String formatdate2(long fdate)
    {
        @SuppressLint({"NewApi", "LocalSuppress"})
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        final long unixTime = fdate;
        @SuppressLint({"NewApi", "LocalSuppress"})
        final String formattedDtm = Instant.ofEpochSecond(unixTime).atZone(ZoneId.of("GMT-4")).format(formatter);

        return formattedDtm;


    }


    public String date_formate2(long  convert_date)
    {



        @SuppressLint({"NewApi", "LocalSuppress"})
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" hh:mm a");

        final long unixTime = convert_date;
        @SuppressLint({"NewApi", "LocalSuppress"})
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);

        return formattedDtm;


    }


    private String gettime(int tripBookedTime) {


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tripBookedTime * 1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(d);

       /* DateFormat df = new SimpleDateFormat("hh:mm a", Locale.US);
        final String time_chat_s = df.format(time);
       // String date = DateFormat.format("hh:mm a").toString();
        return time_chat_s;*/
    }

    private String getdate(int tripBookedTime) {


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tripBookedTime * 1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);

    }

}
