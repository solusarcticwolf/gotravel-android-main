package com.phptravelsnative.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phptravelsnative.Activities.CheckOutDetails;
import com.phptravelsnative.Activities.Tp_Login_Booking;
import com.phptravelsnative.Activities.TravelPortActivity;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Tp_Listing_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    String travelportResponse = "";
    String searchPassenger = "";
    SharedPreferences sharedPreferences;
    public  final String MyPREFERENCES = "MyPrefs" ;
    private ArrayList<TravelPortModel> data;

    private LayoutInflater inflater;
    Tp_Details_adapter adapter;
    TravelPort cabin_class = new TravelPort();


    private int previousPosition = 0;

    public Tp_Listing_adapter(Context context, ArrayList<TravelPortModel> data, TravelPort cabin_class, String travelportResponse,String searchPassenger) {

        this.context = context;
        this.data = data;
        this.cabin_class = cabin_class;
        this.travelportResponse = travelportResponse;
        this.searchPassenger = searchPassenger;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (data.get(position).getCheckInsert())
        {
            return  0;
        }else{
            return  1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        if (position == 1){
            View view = inflater.inflate(R.layout.tp_single_listing, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }else{
            View view = inflater.inflate(R.layout.tp_more, parent, false);
            MyViewHolder2 holder = new MyViewHolder2(view);
            return holder;

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (data.get(position).getCheckInsert()){
            TravelPortModel tpm1 = data.get(position);

            MyViewHolder2 myViewHolder = (MyViewHolder2)holder;

             adapter = new Tp_Details_adapter(context, tpm1.getDetials());
            myViewHolder.outbounds_recycleview.setAdapter(adapter);
            myViewHolder.outbounds_recycleview.setLayoutManager(new LinearLayoutManager(context));

        }else{
            MyViewHolder myViewHolder = (MyViewHolder)holder;

            TravelPortModel tpm1 = data.get(position);
            myViewHolder.departure_timefrom.setText(tpm1.getTakeOff_time());
            myViewHolder.departure_timeto.setText(tpm1.getArrival_time());
            myViewHolder.total_time.setText(tpm1.getTotal_time());
            myViewHolder.departure_from.setText(tpm1.getTake_off_destination());
            myViewHolder.departure_to.setText(tpm1.getArrivalDestination());
            myViewHolder.stops.setText(tpm1.getToatl_stop());
            myViewHolder.flight_number.setText(tpm1.getAero_plane_number());
            myViewHolder.price.setText(tpm1.getCurrCode()+" "+tpm1.getPrice());
            myViewHolder.company_name.setText(tpm1.getName_aero());



            Picasso.with(context)
                    .load(tpm1.getImg_aero())
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

                    boolean a = true;

                    for (int i = 0 ; i<data.size();i++) {
                        if (data.get(i).getCheckInsert()) {
                            removeItem(i);
                            a = false;
                            break;
                        }else{
                            a = true;
                        }
                    }

                    if ((position != previousPosition) || a) {

                        TravelPortModel tpm = new TravelPortModel();
                        tpm.setCheckInsert(true);
                        tpm.setDetials(data.get(position).getDetials());

                        addItem(position + 1, tpm);
                    }

                    previousPosition = position;

                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder{

        RecyclerView outbounds_recycleview;
        Button continue_booking;

        public MyViewHolder2(View itemView) {
            super(itemView);
            outbounds_recycleview = itemView.findViewById(R.id.outbounds_recycleview);
            continue_booking = itemView.findViewById(R.id.continue_booking);

            continue_booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String from_keys =  adapter.getKeys();

                    from_keys = from_keys.substring(0,from_keys.length()-1);

                    Intent intent = new Intent(context,CheckOutDetails.class);
                    Bundle b = new Bundle();
                    b.putParcelable("cabin_date",cabin_class);
                    b.putString("from_keys",from_keys);
                    b.putString("tp_detials",travelportResponse);
                    b.putString("searchPassenger",searchPassenger);

                    b.putString("to_keys","");
                    intent.putExtras(b);
                    context.startActivity(intent);

                }
            });


            continue_booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String from_keys =  adapter.getKeys();

                    if(sharedPreferences.getBoolean("Check_Login",true)) {


                        from_keys = from_keys.substring(0,from_keys.length()-1);

                        Intent intent = new Intent(context,CheckOutDetails.class);
                        Bundle b = new Bundle();
                        b.putParcelable("cabin_date",cabin_class);
                        b.putString("from_keys",from_keys);
                        b.putString("tp_detials",travelportResponse);
                        b.putString("searchPassenger",searchPassenger);

                        b.putString("to_keys","");
                        intent.putExtras(b);
                        context.startActivity(intent);
                    }else{


                        from_keys = from_keys.substring(0,from_keys.length()-1);

                        Intent intent = new Intent(context,Tp_Login_Booking.class);
                        Bundle b = new Bundle();
                        b.putParcelable("cabin_date",cabin_class);
                        b.putString("from_keys",from_keys);
                        b.putString("tp_detials",travelportResponse);
                        b.putString("searchPassenger",searchPassenger);

                        b.putString("to_keys","");
                        intent.putExtras(b);
                        context.startActivity(intent);
                    }




                }
            });


        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView departure_timefrom;
        TextView departure_timeto;
        TextView total_time;
        TextView departure_from;
        TextView stops;
        TextView departure_to;
        TextView flight_number;
        TextView company_name;
        TextView price;
        ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);

            departure_timefrom = (TextView) itemView.findViewById(R.id.departure_timefrom);
            departure_timeto = (TextView) itemView.findViewById(R.id.departure_timeto);
            total_time = (TextView) itemView.findViewById(R.id.total_time);
            departure_from = (TextView) itemView.findViewById(R.id.departure_from);
            stops = (TextView) itemView.findViewById(R.id.stops);
            departure_to = (TextView) itemView.findViewById(R.id.departure_to);
            flight_number = (TextView) itemView.findViewById(R.id.flight_number);
            company_name = (TextView) itemView.findViewById(R.id.company_name);
            price = (TextView) itemView.findViewById(R.id.price);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);


        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(int position) {

        TravelPortModel currPosition = data.get(position);
        data.remove(currPosition);
        notifyItemRemoved(position);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, TravelPortModel infoData) {


        if (position > data.size()){

            position = position-1;
        }
        data.add(position, infoData);
        notifyItemInserted(position);
    }
}
