package com.phptravelsnative.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.phptravelsnative.Models.PassengerModel;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Views.TourSpinner;

import java.util.ArrayList;


public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<PassengerModel> data;
    FragmentManager frm;


    private LayoutInflater inflater;


    public PassengerAdapter(Context context, ArrayList<PassengerModel> data,FragmentManager frm) {

        this.context = context;
        this.data = data;
        this.frm = frm;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

            View view = inflater.inflate(R.layout.child_passenger, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        myViewHolder.title.setText(data.get(position).getTitle());



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        public TextView title;
        public TourSpinner mr;
        public EditText input_name;
        public EditText input_phone;
        public EditText input_email;
        public EditText first_name;
        public EditText nationality;


        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            input_name = (EditText)itemView.findViewById(R.id.input_name);
            input_phone = (EditText)itemView.findViewById(R.id.input_phone);
            input_email = (EditText)itemView.findViewById(R.id.input_email);
            nationality = (EditText)itemView.findViewById(R.id.nationality);
            first_name = (EditText)itemView.findViewById(R.id.first_name);
            mr = (TourSpinner)itemView.findViewById(R.id.tour_type);

            nationality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CountryPicker picker = CountryPicker.newInstance("Select Country");
                    picker.setListener(new CountryPickerListener() {

                        @Override
                        public void onSelectCountry(String name, String code) {
                            nationality.setText(name);
                            picker.dismiss();
                        }
                    });

                    picker.show(frm,"COUNTRY_PICKER");
                }
            });

            String[] name_card = new String[]{"Mr.","Miss.","Mrs"};
            String[] name_code = new String[]{"Mr.","Miss.","Mrs"};
            mr.listAdapter(name_card,name_code);



        }
    }

}
