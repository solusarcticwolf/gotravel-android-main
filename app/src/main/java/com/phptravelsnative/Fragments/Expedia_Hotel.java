package com.phptravelsnative.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingHotels;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.DateSeter;
import com.phptravelsnative.utality.Views.TourSpinner;


public class Expedia_Hotel extends Fragment {

    Hotel_data hotel_data;
    ComplexPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    TextView date_from, date_to;

    DateSeter checkDate;

    Auto_Model searchHotel=new Auto_Model();


    String date_from_api;
    String date_to_api;

    MaterialSpinner child,adult;
    View inflated;
    EditText hotel_name;


    Button search;
    public Expedia_Hotel() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);


        inflated  =  inflater.inflate(R.layout.hotels_layout, container, false);


        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        checkDate = new DateSeter(getContext());

        hotel_name= (EditText) inflated.findViewById(R.id.tour_auto_search);
        hotel_name.setHint(" "+getString(R.string.hint_hotel_ex));


        searchHotel=sharedPreferences.getObject("Expedia_Hotel_Last_Search",Auto_Model.class,searchHotel);

        hotel_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SearchViewActivity.class);
                intent.putExtra("ModuleTyple","ExpediaHotel");
                startActivityForResult(intent,1);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });




        String namefrom=searchHotel.getName();
        if (TextUtils.isEmpty(namefrom))
        {

            hotel_name.setText(getString(R.string.hint_hotel_ex));
        }else {

            hotel_name.setText(" "+searchHotel.getName());
        }





        adult= (MaterialSpinner) inflated.findViewById(R.id.adult);
        child= (MaterialSpinner) inflated.findViewById(R.id.child);

       // adult.setText(String.format("%s 2", getString(R.string.adult)));
        //child.setText(String.format("%s 0", getString(R.string.child)));

        adult.setTextColor(getResources().getColor(R.color.black));
        adult.setItems("Adult 1", "Adult 2","Adult 3", "Adult 4","Adult 5","Adult 6","Adult 7", "Adult 8","Adult 9", "Adult 10");
        adult.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                adult.setText(item);
            }
        });



        child.setTextColor(getResources().getColor(R.color.black));
        child.setItems("Child ","Child 1", "Child 2","Child 3", "Child 4","Child 5","Child 6","Child 7", "Child 8","Child 9", "Child 10");
        child.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                child.setText(item);
            }
        });



        date_from=(TextView)inflated.findViewById(R.id.date_from);
        date_to=(TextView)inflated.findViewById(R.id.date_to);

        search= (Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton();
            }
        });

        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });
        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickHotelSelect();
            }
        });


        date_from.setText(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear());

        date_from_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();

        checkDate.incrementBy();

        date_to_api=checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentDay()+"/"+checkDate.getCurrentYear();

        date_to.setText(checkDate.getCurrentDay()+"/"+checkDate.getCurrentMonthN()+"/"+checkDate.getCurrentYear());




        return inflated;

    }

    public void onClickButton() {

        Intent intent=new Intent(getContext(),SearchingHotels.class);

        Hotel_data data = new Hotel_data();
        data.setFrom(date_from_api);
        data.setTo(date_to_api);
        data.setAdult(adult.getText().charAt(adult.length()-1)+"");
        data.setChild(child.getText().charAt(child.length()-1)+"");
        intent.putExtra("check_ean",true);
        data.setLocation(hotel_name.getText().toString().replaceAll("-",",").replaceAll("-"," "));
        Bundle b=new Bundle();
        b.putParcelable("hotel",data);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void OnClickHotelSelect()
    {
        Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (data != null) {
                date_from.setText(data.getStringExtra("date_from"));
                date_from_api = data.getStringExtra("date_from_api");
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to"));
            }
        } else if (requestCode == 1) {
            searchHotel = sharedPreferences.getObject("Default_Hotel_Last_Search", Auto_Model.class, searchHotel);
            hotel_name.setText(" "+searchHotel.getName());

        }

    }
}
