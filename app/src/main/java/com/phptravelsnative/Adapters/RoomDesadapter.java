package com.phptravelsnative.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Activities.PaymentCardDetails;
import com.phptravelsnative.Models.Model_Room;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomDesadapter extends RecyclerView.Adapter<RoomDesadapter.MyViewHolder> {
    private final Context context;
    ArrayList<Model_Room> Room2=new ArrayList<Model_Room>();//Creating arraylist
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RobotoTextView price,type,chld,booking;
        ImageView image;
        LinearLayout bookingtest;




        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            price= view.findViewById(R.id.price);
            type=view.findViewById(R.id.type);
            image=view.findViewById(R.id.image);
            chld=view.findViewById(R.id.chld);
            booking=view.findViewById(R.id.booking);
            bookingtest=view.findViewById(R.id.bookingtest);
        }
    }
    public RoomDesadapter(Context context, ArrayList<Model_Room> Room2) {
        this.Room2 = Room2;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomdesgin, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        final Model_Room model_room = Room2.get(position);
        String test = model_room.getRoom_name();
        String s;
        if ( test.length() >= 27)
        {
           s=test.substring(0,25);
            holder.title.setText(s+"...");
        }else
        {
            s=test;
            holder.title.setText(s);
        }



        holder.price.setText("USD "+model_room.getPrice());
        holder.type.setText(model_room.getType());
        holder.chld.setText("Adults : "+model_room.getAdults()+ " childs : "+model_room.getChilds() );



        Picasso.with(context).load(model_room.getImage().get(0))
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(holder.image,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });



        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Toast.makeText(context,"clcik me ",Toast.LENGTH_LONG).show();
                Gson gson= new Gson();
                String data = gson.toJson(model_room);

                Intent intent = new Intent(context, PaymentCardDetails.class);
                intent.putExtra("Datatype","travelshope_hotel");
                intent.putExtra("dataItem",data);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                //Activity activity = (Activity) context;
                //overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });



    }
    @Override
    public int getItemCount() {
        return Room2.size();
    }
}