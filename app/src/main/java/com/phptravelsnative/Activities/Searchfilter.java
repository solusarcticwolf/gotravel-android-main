package com.phptravelsnative.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phptravelsnative.Adapters.AirlineAdapter;
import com.phptravelsnative.Adapters.AirlineAdapter_image;
import com.phptravelsnative.Models.Airline;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Searchfilter extends AppCompatActivity {

    ArrayList<ManualFlightModel> array_main_flight = new ArrayList<>();
    ArrayList<DataItem> stopflitermArrayList =  new ArrayList<>();
    ArrayList<ManualFlightModel> stopflitermArrayList_flight =  new ArrayList<>();
    Gson gson ;
    DataItem dataItem;
    ArrayList<Airline> airlineArrayList = new ArrayList<>();
    ArrayList<Airline> stopeArrayList = new ArrayList<>();
    static ArrayList<String> airlinearyystring = new ArrayList<>();
    ArrayList<String> stoparyystring = new ArrayList<>();
    ArrayList<DataItem> dataflights;
    ManualFlightModel manualFlightModel;
    ArrayList<DataItem> searchfilter = new ArrayList<>();

    ArrayList<ManualFlightModel> searchfilter_ManualFlightModel = new ArrayList<>();
    RecyclerView recyclerView,recyclerView2;
    ImageView close ;
    TextView tvMin ,tvMax;
    CrystalRangeSeekbar rangeSeekbar ;
    Button search_fliter;
    Boolean pricefliter =true;
    Context context=this;
    TravelPort cabin_class = new TravelPort();
    String main_arraydata;
    String  airline ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    RobotoTextView reset;
    String filterdatajson ;
    String airlinefind = "no";
    String stopfind = "no";
    String model_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchfilter);



        sharedPref = Searchfilter.this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        stopeArrayList.clear();
        stoparyystring.clear();
        airlinearyystring.clear();
        model_new = getIntent().getExtras().getString("Model" ,"travelhope");
        if (model_new.contentEquals("flights"))
        {
          //  Toast.makeText(getApplicationContext(),"flights",Toast.LENGTH_LONG).show();
            inti_flights();
            dataload_flights();

        }else  if (model_new.contentEquals("travelhope"))
        {
            inti_travelshope();
            dataload_travelshope();
        }





    }

    public void inti_flights()
    {



        gson = new Gson();
        main_arraydata =getIntent().getStringExtra("array_main_flight");
        String cabin_class1 =getIntent().getStringExtra("cabin_class");
        array_main_flight= (ArrayList<ManualFlightModel>) fromJson(main_arraydata, new TypeToken<ArrayList<ManualFlightModel>>() {}.getType());
        cabin_class = gson.fromJson(cabin_class1, TravelPort.class);

        String  airlinedata=  sharedPref.getString("airline", null);
        String  stopdata=  sharedPref.getString("stop", null);

        if (airlinedata != null)
        {

            airlinearyystring = (ArrayList<String>) fromJson(airlinedata, new TypeToken<ArrayList<String>>() {}.getType());

        }

        if (stopdata != null)
        {
            stoparyystring = (ArrayList<String>) fromJson(stopdata, new TypeToken<ArrayList<String>>() {}.getType());

        }



        if (airlinearyystring.size() >0)
        {
            airlinefind="yes";
        }else
        {
            airlinefind="no";
        }
        if (stoparyystring.size() >0)
        {
            stopfind ="yes";
        }else
        {
            stopfind ="no";
        }


        reset = findViewById(R.id.reset);
        close  = findViewById(R.id.close);
        recyclerView2= findViewById(R.id.recyclerView2);
        recyclerView= findViewById(R.id.recyclerView);
        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar1);
        tvMin = (TextView) findViewById(R.id.textMin1);
        tvMax = (TextView) findViewById(R.id.textMax1);
        search_fliter= findViewById(R.id.search_fliter);
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
                pricefliter=true;
            }
        });
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                pricefliter=true;
            }
        });





        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("stop", null);
                editor.putString("airline", null);
                editor.commit();


                Intent intent = new Intent(context, ManualFlightActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("cabin_date",cabin_class);
                intent.putExtras(b);
                intent.putExtra("dataItem", main_arraydata);
                intent.putExtra("main_arraydata", main_arraydata);
                intent.putExtra("filterdata", "flightfliter");
                startActivity(intent);
                finish();
            }
        });


        search_fliter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                for (int i = 0 ; i<3;i ++)
                {
                    if (pricefliter == true)
                    {
                        searchfilter_ManualFlightModel = filterdata_price_flight();
                        pricefliter=false;
                        filterdatajson = gson.toJson(searchfilter_ManualFlightModel);
                        editor.putString("filterdatajson", filterdatajson);
                        editor.commit();
                    }
                    else if (stopfind.contentEquals("yes"))
                    {

                        searchfilter_ManualFlightModel = filterdata_stop_flight(searchfilter_ManualFlightModel);
                        filterdatajson = gson.toJson(searchfilter_ManualFlightModel);
                        stopfind="no";
                        editor.putString("filterdatajson", filterdatajson);
                        editor.commit();
                    }
                    else if (airlinefind.contentEquals("yes"))
                    {
                        searchfilter_ManualFlightModel =  filterdata_airline_flight(searchfilter_ManualFlightModel);
                        filterdatajson = gson.toJson(searchfilter_ManualFlightModel);
                        airlinefind="no";
                        editor.putString("filterdatajson", filterdatajson);
                        editor.commit();
                    }


                }






                String filterdatajson2 =  sharedPref.getString("filterdatajson", null);
                Gson gson = new Gson();
                String airline2 = gson.toJson(airlinearyystring);
                String stop = gson.toJson(stoparyystring);
                editor.putString("stop", stop);
                editor.putString("airline", airline2);
                editor.commit();
               Intent intent = new Intent(context, ManualFlightActivity_New.class);
                Bundle b = new Bundle();
                b.putParcelable("cabin_date",cabin_class);
                intent.putExtras(b);
                intent.putExtra("dataItem", filterdatajson2);
                intent.putExtra("main_arraydata", main_arraydata);
                intent.putExtra("filterdata", "flightfliter");
                context.startActivity(intent);
                finish();





                // mainfilter();
            }
        });



        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Airline airline = stopeArrayList.get(position);
                String stope =airline.getName();
                String numberOnly= stope.replaceAll("[^0-9]", "");

                boolean check = airline.isSelected();
                if (check == true)
                {
                    stoparyystring.remove(numberOnly);


                }else {
                    stoparyystring.add(numberOnly);
                }

                if (stopeArrayList.size()>0)
                {
                    stopfind ="yes";
                }else
                {
                    stopfind ="no";
                }




            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Airline airline = airlineArrayList.get(position);
                boolean check = airline.isSelected();
                Log.d("checkkkkkk" ," | "+check);
                String name =airline.getName();

                if (check == true)
                {
                    airlinearyystring.remove(name);

                }else {
                    airlinearyystring.add(name);
                }

                if (airlinearyystring.size()!=0)
                {
                    airlinefind="yes";
                }else
                {
                    airlinefind="no";
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

    }
    public void inti_travelshope()
    {



        gson = new Gson();

        main_arraydata =getIntent().getStringExtra("dataItem");
        String cabin_class1 =getIntent().getStringExtra("cabin_class");
        dataflights= (ArrayList<DataItem>) fromJson(main_arraydata, new TypeToken<ArrayList<DataItem>>() {}.getType());
        cabin_class = gson.fromJson(cabin_class1, TravelPort.class);

        String  airlinedata=  sharedPref.getString("airline", null);
        String  stopdata=  sharedPref.getString("stop", null);

       if (airlinedata != null)
        {

            airlinearyystring = (ArrayList<String>) fromJson(airlinedata, new TypeToken<ArrayList<String>>() {}.getType());

        }

       if (stopdata != null)
        {
           stoparyystring = (ArrayList<String>) fromJson(stopdata, new TypeToken<ArrayList<String>>() {}.getType());

        }



      if (airlinearyystring.size() >0)
       {
           airlinefind="yes";
       }else
       {
           airlinefind="no";
       }
       if (stoparyystring.size() >0)
        {
            stopfind ="yes";
        }else
       {
           stopfind ="no";
       }


        reset = findViewById(R.id.reset);
        close  = findViewById(R.id.close);
        recyclerView2= findViewById(R.id.recyclerView2);
        recyclerView= findViewById(R.id.recyclerView);
        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar1);
        tvMin = (TextView) findViewById(R.id.textMin1);
        tvMax = (TextView) findViewById(R.id.textMax1);
        search_fliter= findViewById(R.id.search_fliter);
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
                pricefliter=true;
            }
        });
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                pricefliter=true;
            }
        });





        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            editor.putString("stop", null);
            editor.putString("airline", null);
            editor.commit();


            Intent intent = new Intent(context, ManualFlightActivity.class);
            Bundle b = new Bundle();
            b.putParcelable("cabin_date",cabin_class);
            intent.putExtras(b);
            intent.putExtra("dataItem", main_arraydata);
            intent.putExtra("main_arraydata", main_arraydata);
            intent.putExtra("filterdata", "yes");
            startActivity(intent);
            finish();
        }
    });


        search_fliter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                for (int i = 0 ; i<3;i ++)
                {
                     if (pricefliter == true)
                    {
                        searchfilter = filterdata_price();
                        pricefliter=false;
                        filterdatajson = gson.toJson(searchfilter);
                        editor.putString("filterdatajson", filterdatajson);
                        editor.commit();
                    }
                    else if (stopfind.contentEquals("yes"))
                    {

                        searchfilter = filterdata_stop(searchfilter);
                        filterdatajson = gson.toJson(searchfilter);
                        stopfind="no";
                        editor.putString("filterdatajson", filterdatajson);
                        editor.commit();
                    }
                    else if (airlinefind.contentEquals("yes"))
                    {
                        searchfilter =  filterdata_airline(searchfilter);
                        filterdatajson = gson.toJson(searchfilter);
                        airlinefind="no";
                        editor.putString("filterdatajson", filterdatajson);
                        editor.commit();
                    }


                }






                String filterdatajson2 =  sharedPref.getString("filterdatajson", null);

                Gson gson = new Gson();
                String airline2 = gson.toJson(airlinearyystring);
                String stop = gson.toJson(stoparyystring);
                editor.putString("stop", stop);
                editor.putString("airline", airline2);
                editor.commit();
                Intent intent = new Intent(context, ManualFlightActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("cabin_date",cabin_class);
                intent.putExtras(b);
                intent.putExtra("dataItem", filterdatajson2);
                intent.putExtra("main_arraydata", main_arraydata);
                intent.putExtra("filterdata", "yes");
                context.startActivity(intent);
                finish();





                // mainfilter();
            }
        });



        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Airline airline = stopeArrayList.get(position);
                String stope =airline.getName();
                String numberOnly= stope.replaceAll("[^0-9]", "");

                boolean check = airline.isSelected();
                if (check == true)
                {
                    stoparyystring.remove(numberOnly);


                }else {
                    stoparyystring.add(numberOnly);
                }

                if (stopeArrayList.size()>0)
                {
                    stopfind ="yes";
                }else
                {
                    stopfind ="no";
                }




            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Airline airline = airlineArrayList.get(position);
                boolean check = airline.isSelected();
                Log.d("checkkkkkk" ," | "+check);
                String name =airline.getName();

                if (check == true)
                {
                    airlinearyystring.remove(name);

                }else {
                    airlinearyystring.add(name);
                }

                if (airlinearyystring.size()!=0)
                {
                    airlinefind="yes";
                }else
                {
                    airlinefind="no";
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

    }


    public void dataload_flights()
    {
        ArrayList<String> values=new ArrayList<String>();
        ArrayList<String> stopparray=new ArrayList<String>();
        ArrayList<Integer> pricearray = new ArrayList<Integer>();


        for (int i =0;i<array_main_flight.size();i++)
        {

            ManualFlightModel flightModel = array_main_flight.get(i);
            String name  = flightModel.getAero_name();
            String rountc  =""+(flightModel.getModels_array().size()-1);
            String pice  = flightModel.getPrice();



            values.add(name);
            stopparray.add(rountc);
            pricearray.add(Integer.valueOf(pice));



        }

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(values);
        values.clear();
        values.addAll(hashSet);



        HashSet<String> hashSetarline = new HashSet<String>();
        hashSetarline.addAll(airlinearyystring);
        airlinearyystring.clear();
        airlinearyystring.addAll(hashSetarline);

        for (int i =0;i<values.size();i++)
        {
            Airline airline = new Airline();

            ManualFlightModel flightModel = array_main_flight.get(i);
            String a = values.get(i);
            final String linkimage= flightModel.getModels_array().get(0).getImg();
            airline.setName(a);
            airline.setImage(linkimage);
            airline.setSelected(false);

            if (!airlinearyystring.isEmpty())
            {
                for (int k =0;k<airlinearyystring.size();k++)
                {
                    String airl = airlinearyystring.get(k);
                    if (airl.contentEquals(a))
                    {
                        airline.setSelected(true);
                    }
                }
            }


            airlineArrayList.add(airline);
        }


        HashSet<String> hashSetstop = new HashSet<String>();
        hashSetstop.addAll(stopparray);
        stopparray.clear();
        stopparray.addAll(hashSetstop);


        for (int i =0;i<stopparray.size();i++)
        {
            Airline airline = new Airline();


            String a = stopparray.get(i);
            final String linkimage= " ";
            airline.setName("stop : "+a);
            airline.setImage(linkimage);
            airline.setSelected(false);


            if (!stoparyystring.isEmpty())
            {
                for (int k =0;k<stoparyystring.size();k++)
                {
                    String airl = stoparyystring.get(k);
                    if (airl.contentEquals(a))
                    {
                        airline.setSelected(true);
                    }
                }
            }




            stopeArrayList.add(airline);
        }


        int max = Collections.max(pricearray);
        int min = Collections.min(pricearray);
        tvMin.setText(String.valueOf(min));
        tvMax.setText(String.valueOf(max));
        rangeSeekbar.setMinValue(min); rangeSeekbar.setMaxValue(max);


        AirlineAdapter stope =  new AirlineAdapter(getApplicationContext(),stopeArrayList,false);
        recyclerView2.setAdapter(stope);
        recyclerView2.setLayoutManager(new LinearLayoutManager(Searchfilter.this));

        AirlineAdapter_image airlineAdapter_image =  new AirlineAdapter_image(getApplicationContext(),airlineArrayList,true);
        recyclerView.setAdapter(airlineAdapter_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(Searchfilter.this));

    }
    public void dataload_travelshope()
    {
        ArrayList<String> values=new ArrayList<String>();
        ArrayList<String> stopparray=new ArrayList<String>();
        ArrayList<Integer> pricearray = new ArrayList<Integer>();


        for (int i =0;i<dataflights.size();i++)
        {

            DataItem dataItem = dataflights.get(i);
            String name  = dataItem.getAirline();
            String rountc  = dataItem.getStops();
            String pice  = dataItem.getFlight_price();
            values.add(name);
            stopparray.add(rountc);
            pricearray.add(Integer.valueOf(pice));

        }

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(values);
        values.clear();
        values.addAll(hashSet);



        HashSet<String> hashSetarline = new HashSet<String>();
        hashSetarline.addAll(airlinearyystring);
        airlinearyystring.clear();
        airlinearyystring.addAll(hashSetarline);

        for (int i =0;i<values.size();i++)
        {
            Airline airline = new Airline();
            String a = values.get(i);
            final String linkimage= Constant.linkimage +a+Constant.linkimagetype;
            airline.setName(a);
            airline.setImage(linkimage);
            airline.setSelected(false);

            if (!airlinearyystring.isEmpty())
            {
                for (int k =0;k<airlinearyystring.size();k++)
                {
                    String airl = airlinearyystring.get(k);
                    if (airl.contentEquals(a))
                    {
                        airline.setSelected(true);
                    }
                }
            }


            airlineArrayList.add(airline);
        }










        HashSet<String> hashSetstop = new HashSet<String>();
        hashSetstop.addAll(stopparray);
        stopparray.clear();
        stopparray.addAll(hashSetstop);


        for (int i =0;i<stopparray.size();i++)
        {
            Airline airline = new Airline();


            String a = stopparray.get(i);
            final String linkimage= " ";
            airline.setName("stop : "+a);
            airline.setImage(linkimage);
            airline.setSelected(false);


           if (!stoparyystring.isEmpty())
            {
                for (int k =0;k<stoparyystring.size();k++)
                {
                    String airl = stoparyystring.get(k);
                    if (airl.contentEquals(a))
                    {
                        airline.setSelected(true);
                    }
                }
            }




            stopeArrayList.add(airline);
        }


        int max = Collections.max(pricearray);
        int min = Collections.min(pricearray);
        tvMin.setText(String.valueOf(min));
        tvMax.setText(String.valueOf(max));
        rangeSeekbar.setMinValue(min); rangeSeekbar.setMaxValue(max);

        AirlineAdapter airlineAdapter =  new AirlineAdapter(getApplicationContext(),airlineArrayList,true);
        recyclerView.setAdapter(airlineAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Searchfilter.this));

        AirlineAdapter stope =  new AirlineAdapter(getApplicationContext(),stopeArrayList,false);
        recyclerView2.setAdapter(stope);
        recyclerView2.setLayoutManager(new LinearLayoutManager(Searchfilter.this));


    }


    public void mainfilter()
    {
        int  tvMinvalue  = Integer.parseInt(String.valueOf(tvMin.getText()));
        int tvMaxvalue  = Integer.parseInt(String.valueOf(tvMax.getText()));
        for (int i = 0; i<dataflights.size(); i++)
        {
            DataItem dataItem = dataflights.get(i);
            String price  = dataItem.getFlight_price();
            String stop  = dataItem.getStops();
            String airlinecheck  = dataItem.getAirline();


            int  startpriceint  = Integer.parseInt(price);
            int  stopint  = Integer.parseInt(stop);


            if (startpriceint>=tvMinvalue &&  startpriceint <= tvMaxvalue )
            {

                             /*   if (stopefliter ==true)
                                {
                                    for (int k =0 ; k<stoparyystring.size();k++)
                                    {
                                        String stop2 = stoparyystring.get(k);
                                        int  valuestop2  = Integer.parseInt(stop2);

                                        if ( stopint ==  valuestop2 )
                                        {

                                            if (airlinefliter==true)
                                            {

                                for (int air =0 ; air<airlinearyystring.size();air++)
                                {
                                    String airlinestring = airlinearyystring.get(air);
                                    if ( airlinecheck.contains(airlinestring))
                                    {
                                        searchfilter.add(dataItem);
                                    }


                                }


                            }else
                            {
                                searchfilter.add(dataItem);
                            }

                            //searchfilter.add(dataItem);

                        }


                    }
                }
                else
                {

                    searchfilter.add(dataItem);
                }

*/

            }

        }



    }

    public ArrayList<DataItem> filterdata_price()
    {
        ArrayList<DataItem> priceflitermArrayList =  new ArrayList<>();

        int  tvMinvalue  = Integer.parseInt(String.valueOf(tvMin.getText()));
        int tvMaxvalue  = Integer.parseInt(String.valueOf(tvMax.getText()));
        for (int i = 0; i<dataflights.size(); i++)
        {
            DataItem dataItem = dataflights.get(i);
            String price  = dataItem.getFlight_price();
            int  startprice  = Integer.parseInt(price); ;
            if(pricefliter==true)
            {
                if (startprice>=tvMinvalue &&  startprice <= tvMaxvalue )
                {
                    priceflitermArrayList.add(dataItem);
                    Log.d("priceofsearching " ,tvMinvalue +" || "+ price);
                }
            }




        }

        return  priceflitermArrayList;

    }
    public ArrayList<DataItem> filterdata_stop(ArrayList <DataItem> dataflightsarray)
    {


        for (int i = 0; i<dataflightsarray.size(); i++)
        {

            DataItem dataItem = dataflightsarray.get(i);
            String stop  = dataItem.getStops();
            int  startprice  = Integer.parseInt(stop);


            for (int k =0 ; k<stoparyystring.size();k++)
            {
                String stop2 = stoparyystring.get(k);
                int  valuestop2  = Integer.parseInt(stop2);

                if ( startprice ==  valuestop2 )
                {
                    stopflitermArrayList.add(dataItem);
                    Log.d("priceofsearching " ,startprice +" || "+ valuestop2);
                }


            }

        }


        return  stopflitermArrayList ;
    }
    public ArrayList<DataItem> filterdata_airline( ArrayList <DataItem> dataflightsarray )
    {

        ArrayList<DataItem> airlineflitermArrayList =  new ArrayList<>();

        for (int i = 0; i<dataflightsarray.size(); i++)
        {

            DataItem dataItem = dataflightsarray.get(i);
            String stop  = dataItem.getAirline();
            //int  startprice  = Integer.parseInt(stop);
            for (int k =0 ; k<airlinearyystring.size();k++)
            {
                String stop2 = airlinearyystring.get(k);
                //int  valuestop2  = Integer.parseInt(stop2);

                if ( stop.contains(stop2))
                {
                    airlineflitermArrayList.add(dataItem);
                }


            }

        }


        return  airlineflitermArrayList ;
    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }

        private OnItemClickListener mListener;

        private GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (childView != null && mListener != null) {
                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());

            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }


    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }



    public ArrayList<ManualFlightModel> filterdata_price_flight()
    {
        ArrayList<ManualFlightModel> priceflitermArrayList =  new ArrayList<ManualFlightModel>();

        int  tvMinvalue  = Integer.parseInt(String.valueOf(tvMin.getText()));
        int tvMaxvalue  = Integer.parseInt(String.valueOf(tvMax.getText()));
        for (int i = 0; i<array_main_flight.size(); i++)
        {
            ManualFlightModel dataItem = array_main_flight.get(i);
            String price  = dataItem.getPrice();
            int  startprice  = Integer.parseInt(price); ;
            if(pricefliter==true)
            {
                if (startprice>=tvMinvalue &&  startprice <= tvMaxvalue )
                {
                   priceflitermArrayList.add(dataItem);
                    Log.d("priceofsearching " ,tvMinvalue +" || "+ price);
                }
            }




        }

        return  priceflitermArrayList;

    }



    public ArrayList<ManualFlightModel> filterdata_stop_flight(ArrayList <ManualFlightModel> dataflightsarray)
    {


        for (int i = 0; i<dataflightsarray.size(); i++)
        {

            ManualFlightModel dataItem = dataflightsarray.get(i);
            String stop  =""+ dataItem.getModels_array().size();
            int  startprice  = Integer.parseInt(stop);


            for (int k =0 ; k<stoparyystring.size();k++)
            {
                String stop2 = stoparyystring.get(k);
                int  valuestop2  = Integer.parseInt(stop2);

                if ( startprice ==  valuestop2 )
                {
                    stopflitermArrayList_flight.add(dataItem);
                    Log.d("priceofsearching " ,startprice +" || "+ valuestop2);
                }


            }

        }


        return  stopflitermArrayList_flight ;
    }
    public ArrayList<ManualFlightModel> filterdata_airline_flight( ArrayList <ManualFlightModel> dataflightsarray )
    {

        ArrayList<ManualFlightModel> airlineflitermArrayList =  new ArrayList<>();

        for (int i = 0; i<dataflightsarray.size(); i++)
        {

            ManualFlightModel flightModel = dataflightsarray.get(i);
            String stop  = flightModel.getAero_name();
            //int  startprice  = Integer.parseInt(stop);
            for (int k =0 ; k<airlinearyystring.size();k++)
            {
                String stop2 = airlinearyystring.get(k);
                //int  valuestop2  = Integer.parseInt(stop2);

                if ( stop.contains(stop2))
                {
                    airlineflitermArrayList.add(flightModel);
                }


            }

        }


        return  airlineflitermArrayList ;
    }


}
