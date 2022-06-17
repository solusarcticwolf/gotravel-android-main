package com.phptravelsnative.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.phptravelsnative.Models.Airline;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AirlineAdapter_image extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;


    private ArrayList<Airline> data;
    View view ;
    private LayoutInflater inflater;
    Boolean imageshow;
    public AirlineAdapter_image(Context context, ArrayList<Airline> data,Boolean imageshow) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.imageshow =imageshow;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {


        if (imageshow == false)
        {
            view = inflater.inflate(R.layout.filter_search_airline_stope, parent, false);
        }else
        {
            view = inflater.inflate(R.layout.filter_search_airline, parent, false);
        }
        AirlineAdapter_image.MyViewHolder holder = new AirlineAdapter_image.MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        final AirlineAdapter_image.MyViewHolder myViewHolder = (AirlineAdapter_image.MyViewHolder)holder;
        final Airline flightModel = data.get(position);

        if (imageshow == false)
        {
            myViewHolder.imageView.setVisibility(View.GONE);
        }

        myViewHolder.company_name.setText(flightModel.getName());

        final String linkimage= flightModel.getImage();
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

        myViewHolder.chkWindows.setChecked(flightModel.isSelected());
        myViewHolder.chkWindows.setTag(data.get(position));


        myViewHolder.chkWindows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Airline fruits1 = (Airline) myViewHolder.chkWindows.getTag();

                boolean check = fruits1.isSelected();
                fruits1.setSelected(myViewHolder.chkWindows.isChecked());
                data.get(position).setSelected(myViewHolder.chkWindows.isChecked());

            }
        });


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                boolean check = flightModel.isSelected();
                if (check==false)
                {
                    myViewHolder.chkWindows.setChecked(true);
                    Airline fruits1 = (Airline) myViewHolder.chkWindows.getTag();
                    fruits1.setSelected(myViewHolder.chkWindows.isChecked());
                    data.get(position).setSelected(myViewHolder.chkWindows.isChecked());
                }else
                {
                    myViewHolder.chkWindows.setChecked(false);
                    Airline fruits1 = (Airline) myViewHolder.chkWindows.getTag();
                    fruits1.setSelected(false);
                    data.get(position).setSelected(false);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView company_name;
        ImageView imageView;
        CheckBox chkWindows;


        public MyViewHolder(View itemView) {
            super(itemView);

            company_name = (TextView) itemView.findViewById(R.id.company_name);
            imageView = (ImageView) itemView.findViewById(R.id.main_id);
            chkWindows =itemView.findViewById(R.id.chkWindows);
        }
    }



}

