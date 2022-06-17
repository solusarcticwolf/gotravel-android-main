package com.phptravelsnative.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phptravelsnative.Models.TravelPortDetails;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Tp_Details_adapter extends RecyclerView.Adapter<Tp_Details_adapter.MyViewHolder> {

    private Context context;

    private ArrayList<TravelPortDetails> data;

    private LayoutInflater inflater;
    private String keys_seletec = "";



    public Tp_Details_adapter(Context context, ArrayList<TravelPortDetails> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

        for (int i=1;i<data.size();i++){

            if (!data.get(i).getCheck_header_segment().equals("header")){

                keys_seletec = keys_seletec+(data.get(i).getKey()+",");

            }else{

                break;
            }

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (data.get(position).getCheck_header_segment().equals("header"))
        {
            return  0;
        }else{
            return  1;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        if (position == 0){
            View view = inflater.inflate(R.layout.tp_header_details, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }else{
            View view = inflater.inflate(R.layout.tp_layout_details, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }

    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {


        if (!data.get(position).getCheck_header_segment().equals("header")){


            TravelPortDetails tp = data.get(position);
            myViewHolder.time_from.setText(tp.getTime_from());
            myViewHolder.time_to.setText(tp.getTime_to());
            myViewHolder.from.setText(tp.getLocation_from());
            myViewHolder.to.setText(tp.getLocation_to());
            myViewHolder.date_from.setText(tp.getDate_from());
            myViewHolder.date_to.setText(tp.getDate_to());

            if (tp.getCheck_inner_segment().equals("show_button")){

                myViewHolder.select.setVisibility(View.VISIBLE);

                if (tp.isCheck_CheckUnChecked()){

                    myViewHolder.select.setImageResource(R.drawable.fill_circle);

                }else{
                    myViewHolder.select.setImageResource(R.drawable.empty_circle);

                }

                myViewHolder.select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int i=0;i<data.size();i++){

                            if(i==position){

                                data.get(i).setCheck_CheckUnChecked(true);

                            }else{
                                data.get(i).setCheck_CheckUnChecked(false);
                            }
                        }
                        keys_seletec = "";

                        for (int i=position;i<data.size();i++){

                            if (!data.get(i).getCheck_header_segment().equals("header")){

                                keys_seletec = keys_seletec+(data.get(i).getKey()+",");

                            }else{

                                break;
                            }

                        }

                        notifyDataSetChanged();

                    }
                });


            }else{

                myViewHolder.select.setVisibility(View.GONE);

            }


        }

    }

    public String getKeys(){

        return keys_seletec;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView time_from;
        TextView time_to;
        TextView from;
        TextView to;
        TextView date_from;
        TextView date_to;
        ImageView select;


        public MyViewHolder(View itemView) {
            super(itemView);

            date_from = (TextView)itemView.findViewById(R.id.date_from);
            date_to = (TextView)itemView.findViewById(R.id.date_to);
            from = (TextView)itemView.findViewById(R.id.from);
            to = (TextView)itemView.findViewById(R.id.to);
            time_from = (TextView)itemView.findViewById(R.id.time_from);
            time_to = (TextView)itemView.findViewById(R.id.time_to);
            select = (ImageView)itemView.findViewById(R.id.check_uncheck);


        }
    }

}
