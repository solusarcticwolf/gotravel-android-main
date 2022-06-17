package com.phptravelsnative.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Activities.Cabin_Class;
import com.phptravelsnative.Activities.CheckOutDetails;
import com.phptravelsnative.Activities.Manual_flight_detials;
import com.phptravelsnative.Activities.Manual_flight_detials_New;
import com.phptravelsnative.Activities.Tp_Login_Booking;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Manual_Flight_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<ManualFlightModel> data;

    private LayoutInflater inflater;
    TravelPort cabin_class;


    private int previousPosition = 0;

    public Manual_Flight_Adapter(Context context, ArrayList<ManualFlightModel> data,  TravelPort cabin_class) {

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
            final ManualFlightModel flightModel = data.get(position);


            int quotient = position/2;
            if(quotient*2== position){
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_itemgrey);
            }else
            {
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_item);
            }


            myViewHolder.tv_price.setText(flightModel.getPrice());
            myViewHolder.tv_currency.setText(flightModel.getCurrSymbol());
            myViewHolder.stops.setText(""+(flightModel.getModels_array().size()));
            myViewHolder.tv_arrival_and_departure_time.setText(""+ flightModel.getModels_array().get(0).getTime()+" — "+flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time());
            myViewHolder.tv_flight_duration_title.setText(flightModel.getTotal_time()+":h");
            myViewHolder.tv_date.setText(cabin_class.getFrom_date());
            myViewHolder.tv_cities.setText(flightModel.getModels_array().get(0).getCode() +" — "+flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_code());


          /*  myViewHolder.company_name.setText(flightModel.getModels_array().get(0).getName());
            myViewHolder.departure_from.setText(flightModel.getModels_array().get(0).getCode());
            myViewHolder.departure_to.setText(flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_code());
            myViewHolder.departure_timefrom.setText(flightModel.getModels_array().get(0).getTime());
            myViewHolder.departure_timeto.setText(flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time());
            myViewHolder.stops.setText((flightModel.getModels_array().size()-1)+" Stops");
            myViewHolder.total_time.setText("Time : "+flightModel.getTotal_time());
            myViewHolder.price.setText(flightModel.getCurrSymbol()+" "+flightModel.getPrice()+" "+flightModel.getCurrCode());
            myViewHolder.flight_number.setText(flightModel.getModels_array().get(0).getFlight_no());

            Picasso.with(context)
                    .load(flightModel.getModels_array().get(0).getImg())
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

                    Bundle b = new Bundle();
                    Intent intent = new Intent(context, Manual_flight_detials.class);
                    intent.putExtra("type","oneway");
                    intent.putParcelableArrayListExtra("detials",flightModel.getModels_array());
                    b.putParcelable("cabin_class",cabin_class);
                    intent.putExtras(b);
                    intent.putExtra("id",flightModel.getId());
                    intent.putExtra("url",flightModel.getModels_array().get(0).getImg());
                    context.startActivity(intent);
                }
            });*/



            Picasso.with(context)
                    .load(flightModel.getModels_array().get(0).getImg())
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
                    String myJson = gson.toJson(flightModel);
                    Intent intent = new Intent(context, Manual_flight_detials_New.class);
                    intent.putExtra("flightModel", myJson);
                    intent.putExtra("tourtype", "oneway");
                    intent.putExtra("Model","flights");
                    context.startActivity(intent);
                    Activity activity = (Activity) context;
                    activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                    // activity.startActivity(intent);


                }
            });


        }else{

            MyViewHolder2 myViewHolder = (MyViewHolder2)holder;
            final  ManualFlightModel flightModel = data.get(position);
            int quotient = position/2;
            if(quotient*2== position){
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_itemgrey);
            }else
            {
                myViewHolder.backgroundcolor.setBackgroundResource(R.drawable.results_item);
            }


          /*  myViewHolder.company_name.setText(flightModel.getModels_array().get(0).getName());
            myViewHolder.departure_from.setText(flightModel.getModels_array().get(0).getCode());
            myViewHolder.departure_to.setText(flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_code());
            myViewHolder.departure_timefrom.setText(flightModel.getModels_array().get(0).getTime());
            myViewHolder.departure_timeto.setText(flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time());
            myViewHolder.stops.setText((flightModel.getModels_array().size()-1)+" Stops");
            myViewHolder.total_time.setText("Time : "+flightModel.getTotal_time());
            myViewHolder.price.setText(flightModel.getCurrSymbol()+" "+flightModel.getPrice()+" "+flightModel.getCurrCode());
            myViewHolder.flight_number.setText(flightModel.getModels_array().get(0).getFlight_no());

            myViewHolder.b_departure_from.setText(flightModel.getReturn_array().get(0).getCode());
            myViewHolder.b_departure_to.setText(flightModel.getModels_array().get(flightModel.getReturn_array().size()-1).getTo_code());
            myViewHolder.b_departure_timefrom.setText(flightModel.getReturn_array().get(0).getTime());
            myViewHolder.b_departure_timeto.setText(flightModel.getReturn_array().get(flightModel.getReturn_array().size()-1).getTo_time());
            myViewHolder.b_stops.setText((flightModel.getReturn_array().size()-1)+" Stops");
            myViewHolder.b_total_time.setText("Time : "+flightModel.getTotal_time());
*/
         /*   myViewHolder.tv_cities.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());
            myViewHolder.tv_cities1.setText(""+cabin_class.getTo_id()+" — "+cabin_class.getFrom_id());
            myViewHolder.tv_price.setText(flightModel.getPrice());
            myViewHolder.tv_currency.setText(flightModel.getCurrSymbol());
            myViewHolder.tv_flight_duration_title1.setText(flightModel.getTotal_time());
            myViewHolder.tv_flight_duration_title.setText(flightModel.getTotal_time());
            myViewHolder.tv_arrival_and_departure_time.setText(""+ flightModel.getModels_array().get(0).getTime()+" — "+flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time());
            myViewHolder.stops.setText((flightModel.getModels_array().size()-1)+"");
            myViewHolder.tv_stops1.setText((flightModel.getModels_array().size()-1)+" ");
            myViewHolder.tv_arrival_and_departure_time1.setText(" "+ flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time()+" — "+flightModel.getModels_array().get(0).getTime());

            myViewHolder.tv_date.setText(cabin_class.getFrom_date());
            myViewHolder.tv_date2.setText(cabin_class.getTo_date());*/

            myViewHolder.tv_price.setText(flightModel.getPrice());
            myViewHolder.tv_currency.setText(flightModel.getCurrSymbol());
            myViewHolder.tv_flight_duration_title1.setText(flightModel.getTotal_time()+" h");
            myViewHolder.tv_flight_duration_title.setText(flightModel.getTotal_time()+" h");
            myViewHolder.tv_arrival_and_departure_time.setText(""+ flightModel.getModels_array().get(0).getTime()+" — "+flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time());
            myViewHolder.stops.setText((flightModel.getModels_array().size())+"");
            myViewHolder.tv_stops1.setText((flightModel.getModels_array().size())+" ");
            myViewHolder.tv_arrival_and_departure_time1.setText(" "+ flightModel.getModels_array().get(flightModel.getModels_array().size()-1).getTo_time()+" — "+flightModel.getModels_array().get(0).getTime());

            myViewHolder.tv_date.setText(cabin_class.getFrom_date());
            myViewHolder.tv_date2.setText(cabin_class.getTo_date());

            Picasso.with(context)
                    .load(flightModel.getModels_array().get(0).getImg())
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

         /*   myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle b = new Bundle();
                    Intent intent = new Intent(context, Manual_flight_detials.class);
                    intent.putExtra("type","oneway");
                    flightModel.getModels_array().addAll(flightModel.getReturn_array());
                    intent.putParcelableArrayListExtra("detials",flightModel.getModels_array());
                    b.putParcelable("cabin_class",cabin_class);
                    intent.putExtras(b);
                    intent.putExtra("id",flightModel.getId());
                    intent.putExtra("url",flightModel.getModels_array().get(0).getImg());
                    context.startActivity(intent);
                }
            });*/


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Gson gson = new Gson();
                    String myJson = gson.toJson(flightModel);
                    Intent intent = new Intent(context, Manual_flight_detials_New.class);
                    intent.putExtra("flightModel", myJson);
                    intent.putExtra("tourtype", "round");
                    intent.putExtra("Model","flights");
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
}
