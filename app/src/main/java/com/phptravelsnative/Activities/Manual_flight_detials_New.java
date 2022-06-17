package com.phptravelsnative.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devspark.robototextview.widget.RobotoButton;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Adapters.Manual_Flight_Detials_Adapter;
import com.phptravelsnative.Adapters.Manual_Flight_Detials_Adapter_Travelhope;
import com.phptravelsnative.Adapters.Tarco_adapter;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.Models.RoutesItem;
import com.phptravelsnative.Models.Tarco_Model;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.phptravelsnative.utality.Extra.Constant.domain_name;


public class Manual_flight_detials_New extends Drawer {

    Context context=this;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RecyclerView recyclerView,recyclerView2;
    // ImageView header;
    TravelPort cabin_class = new TravelPort();
    String id = "";
    Button btn_book;
    Gson gson;
    DataItem dataItem;
    ManualFlightModel manualFlightModel;
    ProgressDialog dialog;

    ArrayList<RouteItem> single_retun = new ArrayList<>();
    ArrayList<RouteItem> single_go = new ArrayList<>();
    ArrayList<RouteItem> onew_way = new ArrayList<>();

    ArrayList<OneWay> onew_way_flight = new ArrayList<>();

    ArrayList<RoutesItem> return_way = new ArrayList<>();
    String datadetails,tourtype ;
    RobotoTextView tv_price;
    RobotoTextView tv_currency;
    RobotoTextView tv_cities,tv_cities2;
    RobotoTextView tv_duration,tv_duration2;
    RobotoTextView tv_stope,tv_stope2;
    RobotoTextView tv_citiesname;
    RobotoButton robotoButton_booking;
    LinearLayout retrun;
    String model_new ;
    Tarco_Model tarco_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        model_new = getIntent().getExtras().getString("Model" ,"travelhope");
        cabin_class = getIntent().getExtras().getParcelable("cabin_date");
        if(model_new.contentEquals("flights"))
        {
            inti_flights();
          //  Toast.makeText(getApplicationContext(),"sfdfdsfdsfdfdsf",Toast.LENGTH_LONG).show();
        }
        else if(model_new.contentEquals("travelhope"))
        {

            inti_travelhope();
        }
        else if(model_new.contentEquals("Tarco"))
        {

          //  Toast.makeText(getApplicationContext(),"Tarco",Toast.LENGTH_LONG).show();

            //inti_travelhope();
            inti_Tarco();
        }

    }




    public void inti_Tarco()
    {

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_manual_flight_detials__new);
        View inflated = stub.inflate();

        gson = new Gson();
        tourtype =getIntent().getStringExtra("tourtype");
        datadetails =getIntent().getStringExtra("flight_Tarco");
        tarco_model = gson.fromJson(datadetails, Tarco_Model.class);


        recyclerView = (RecyclerView)inflated.findViewById(R.id.recyclerView);

        retrun=  findViewById(R.id.retrun);
        tv_citiesname = (RobotoTextView) findViewById(R.id.tv_citiesname);
        tv_citiesname.setVisibility(View.VISIBLE);
        btn_book = (Button)inflated.findViewById(R.id.btn_book);
        tv_price = (RobotoTextView)inflated.findViewById(R.id.tv_price);
        tv_currency = (RobotoTextView)inflated.findViewById(R.id.tv_currency);
        tv_cities = (RobotoTextView)inflated.findViewById(R.id.tv_cities);
        tv_duration = (RobotoTextView)inflated.findViewById(R.id.tv_duration);
        tv_stope = (RobotoTextView)inflated.findViewById(R.id.tv_stope);
        tv_cities2 = (RobotoTextView)inflated.findViewById(R.id.tv_cities2);
        tv_duration2 = (RobotoTextView)inflated.findViewById(R.id.tv_duration2);
        tv_stope2 = (RobotoTextView)inflated.findViewById(R.id.tv_stope);


        // manualFlightModel
       // tv_duration2.setText(manualFlightModel.getTotal_time()+":h");




         String  timeeee= date_time_return(Integer.parseInt(tarco_model.getDuration_time()));
         tv_duration.setText(timeeee);


        //tv_cities2.setText(manualFlightModel.getModels_array().get(manualFlightModel.getModels_array().size()-1).getTo_code()+" — "+manualFlightModel.getModels_array().get(0).getCode() );

        tv_currency.setText(tarco_model.getCurrency_code());
        tv_price.setText(tarco_model.getPrice());
        tv_cities.setText(tarco_model.getDeparture_code() +" — "+tarco_model.getArrival_code());

        tv_stope.setText("stop : 1");
       // tv_stope2.setText("stop : "+(manualFlightModel.getModels_array().size()));




        dialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        recyclerView = inflated.findViewById(R.id.recyclerView);
        recyclerView2= inflated.findViewById(R.id.recyclerView2);
        robotoButton_booking = (RobotoButton) inflated.findViewById(R.id.btn_buy);


        dataload_flightTarco(tarco_model);

        // postrequest("https://www.phptravels.net/api/travelhope_flights/detail?appKey=phptravels");




        robotoButton_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String myJson = gson.toJson(manualFlightModel);
                Intent intent=new Intent(Manual_flight_detials_New.this, Bookingflight.class);
                intent.putExtra("dataItem", myJson);
                intent.putExtra("Datatype", "Tarco");
                startActivity(intent);

            }
        });


        TarcoFlight_deatils();

    }



    public void inti_travelhope()
    {

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_manual_flight_detials__new);
        View inflated = stub.inflate();

        gson = new Gson();
        tourtype =getIntent().getStringExtra("tourtype");
        datadetails =getIntent().getStringExtra("dataItem");
        dataItem = gson.fromJson(datadetails, DataItem.class);



        recyclerView = (RecyclerView)inflated.findViewById(R.id.recyclerView);

        retrun=  findViewById(R.id.retrun);
        tv_citiesname = (RobotoTextView) findViewById(R.id.tv_citiesname);
        tv_citiesname.setVisibility(View.VISIBLE);
        btn_book = (Button)inflated.findViewById(R.id.btn_book);
        tv_price = (RobotoTextView)inflated.findViewById(R.id.tv_price);
        tv_currency = (RobotoTextView)inflated.findViewById(R.id.tv_currency);
        tv_cities = (RobotoTextView)inflated.findViewById(R.id.tv_cities);
        tv_duration = (RobotoTextView)inflated.findViewById(R.id.tv_duration);
        tv_stope = (RobotoTextView)inflated.findViewById(R.id.tv_stope);

        tv_cities2 = (RobotoTextView)inflated.findViewById(R.id.tv_cities2);
        tv_duration2 = (RobotoTextView)inflated.findViewById(R.id.tv_duration2);
        tv_stope2 = (RobotoTextView)inflated.findViewById(R.id.tv_stope);

        tv_duration2.setText(dataItem.getFlight_duration());
        tv_duration.setText(dataItem.getFlight_duration());
        tv_citiesname.setText(dataItem.getFrom_code()+" — "+dataItem.getTo_code());
        tv_currency.setText(dataItem.getCurrency());
        tv_price.setText(dataItem.getFlight_price());

        tv_stope.setText("stop : "+dataItem.getStops());
        tv_cities2.setText("stop : "+dataItem.getStops());

        dialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        recyclerView = inflated.findViewById(R.id.recyclerView);
        recyclerView2= inflated.findViewById(R.id.recyclerView2);

        robotoButton_booking = (RobotoButton) inflated.findViewById(R.id.btn_buy);
        dataload(dataItem);
        Manual_flight_detials_New.this.setTitle("sdsd");
        // postrequest("https://www.phptravels.net/api/travelhope_flights/detail?appKey=phptravels");




        robotoButton_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myJson = gson.toJson(dataItem);
                Intent intent=new Intent(Manual_flight_detials_New.this, Bookingflight.class);
                intent.putExtra("dataItem", myJson);
                intent.putExtra("Datatype", "travelhope");
                intent.putExtra("cabin_date", cabin_class);
                startActivity(intent);

            }
        });
    }







    public void inti_flights()
    {

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_manual_flight_detials__new);
        View inflated = stub.inflate();

        gson = new Gson();
        tourtype =getIntent().getStringExtra("tourtype");
        datadetails =getIntent().getStringExtra("flightModel");
        manualFlightModel = gson.fromJson(datadetails, ManualFlightModel.class);



        recyclerView = (RecyclerView)inflated.findViewById(R.id.recyclerView);

        retrun=  findViewById(R.id.retrun);
        tv_citiesname = (RobotoTextView) findViewById(R.id.tv_citiesname);
        tv_citiesname.setVisibility(View.VISIBLE);
        btn_book = (Button)inflated.findViewById(R.id.btn_book);
        tv_price = (RobotoTextView)inflated.findViewById(R.id.tv_price);
        tv_currency = (RobotoTextView)inflated.findViewById(R.id.tv_currency);
        tv_cities = (RobotoTextView)inflated.findViewById(R.id.tv_cities);
        tv_duration = (RobotoTextView)inflated.findViewById(R.id.tv_duration);
        tv_stope = (RobotoTextView)inflated.findViewById(R.id.tv_stope);
        tv_cities2 = (RobotoTextView)inflated.findViewById(R.id.tv_cities2);
        tv_duration2 = (RobotoTextView)inflated.findViewById(R.id.tv_duration2);
        tv_stope2 = (RobotoTextView)inflated.findViewById(R.id.tv_stope);


       // manualFlightModel
        tv_duration2.setText(manualFlightModel.getTotal_time()+":h");
        tv_duration.setText(manualFlightModel.getTotal_time()+":h");

        tv_citiesname.setText(manualFlightModel.getModels_array().get(0).getCode() +" — "+manualFlightModel.getModels_array().get(manualFlightModel.getModels_array().size()-1).getTo_code());
        tv_cities2.setText(manualFlightModel.getModels_array().get(manualFlightModel.getModels_array().size()-1).getTo_code()+" — "+manualFlightModel.getModels_array().get(0).getCode() );

        tv_currency.setText(manualFlightModel.getCurrSymbol());
        tv_price.setText(manualFlightModel.getPrice());


        tv_stope.setText("stop : "+(manualFlightModel.getModels_array().size()));
        tv_stope2.setText("stop : "+(manualFlightModel.getModels_array().size()));




        dialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        recyclerView = inflated.findViewById(R.id.recyclerView);
        recyclerView2= inflated.findViewById(R.id.recyclerView2);

        robotoButton_booking = (RobotoButton) inflated.findViewById(R.id.btn_buy);


        dataload_flight(manualFlightModel);

        // postrequest("https://www.phptravels.net/api/travelhope_flights/detail?appKey=phptravels");




       robotoButton_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myJson = gson.toJson(manualFlightModel);
                Intent intent=new Intent(Manual_flight_detials_New.this, Bookingflight.class);
                intent.putExtra("dataItem", myJson);
                intent.putExtra("Datatype", "flight");
                intent.putExtra("cabin_date", cabin_class);
                startActivity(intent);

            }
        });
    }




    public void dataload_flightTarco(Tarco_Model tarco_model)
    {

        retrun.setVisibility(View.GONE);



      //  onew_way_flight = manualFlightModel.getModels_array();
       /* return_way=manualFlightModel.getRoute();
        tv_cities.setText(return_way.get(0).getFrom()+" — "+ return_way.get(0).getTo());
        Collections.reverse(onew_way);*/

        OneWay  oneWay_model = new OneWay();


        oneWay_model.setCode(tarco_model.getDeparture_code());
        oneWay_model.setTo_code(tarco_model.getArrival_code());
        oneWay_model.setTime(parseDateToddMMyyyy(tarco_model.getDeparture_time()));
        oneWay_model.setTo_time(parseDateToddMMyyyy(tarco_model.getArrival_time()));
        oneWay_model.setName(tarco_model.getDeparture_airport());
        oneWay_model.setTo_name(tarco_model.getArrival_airport());
        oneWay_model.setFlight_no(tarco_model.getAirline_name());
        oneWay_model.setImg(tarco_model.getImg_code());
        onew_way_flight.add(oneWay_model);




        manualFlightModel = new ManualFlightModel();
        manualFlightModel.setTotal_time(tarco_model.getDuration_time());
        manualFlightModel.setAero_name(" Tarco ");




        /*myViewHolder.stops.setText("stop : "+c);
        myViewHolder.total_time.setText(manualFlightModel.getTotal_time()+" h");
        myViewHolder.flight_number.setText(manualFlightModel.getAero_name() +" - "+flightModel.getFlight_no());
        myViewHolder.vehicle_type.setText(manualFlightModel.getAero_name()+" : "+flightModel.getFlight_no());
        myViewHolder.company_name.setText(" Air LIne" +manualFlightModel.getAero_name());*/




        Manual_Flight_Detials_Adapter adapter = new Manual_Flight_Detials_Adapter(Manual_flight_detials_New.this,onew_way_flight,"oneway",manualFlightModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));
    }



    public void dataload_flight(ManualFlightModel manualFlightModel)
    {

        if (tourtype.contentEquals("oneway"))
        {
            retrun.setVisibility(View.GONE);



            onew_way_flight = manualFlightModel.getModels_array();
            /*return_way=manualFlightModel.getRoute();
            tv_cities.setText(return_way.get(0).getFrom()+" — "+ return_way.get(0).getTo());
            Collections.reverse(onew_way);*/



            Manual_Flight_Detials_Adapter adapter = new Manual_Flight_Detials_Adapter(Manual_flight_detials_New.this,onew_way_flight,"oneway",manualFlightModel);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));



        }
        else
        {
            onew_way_flight = manualFlightModel.getModels_array();
            int size = onew_way_flight.size()/2;


            Manual_Flight_Detials_Adapter adapter = new Manual_Flight_Detials_Adapter(Manual_flight_detials_New.this,onew_way_flight,"oneway",manualFlightModel);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));

        }


    }



    public void dataload(DataItem dataItem)
    {

        if (tourtype.contentEquals("oneway"))
        {
            retrun.setVisibility(View.GONE);
            onew_way = dataItem.getRoutes();
            return_way=dataItem.getRoute();
            tv_cities.setText(return_way.get(0).getFrom()+" — "+ return_way.get(0).getTo());
            Collections.reverse(onew_way);

            Manual_Flight_Detials_Adapter_Travelhope adapter = new Manual_Flight_Detials_Adapter_Travelhope(Manual_flight_detials_New.this,onew_way,"oneway",dataItem);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));
        }
        else
        {

            onew_way = dataItem.getRoutes();

            int size = onew_way.size()/2;
            for (int i = 0;i<size;i++)
            {
                RouteItem routeItem =onew_way.get(i);
                single_go.add(routeItem);

            }


            for (int i =size ;i<onew_way.size();i++)
            {
                RouteItem routeItem =onew_way.get(i);
                single_retun.add(routeItem);

            }



            return_way=dataItem.getRoute();
            tv_cities.setText(return_way.get(0).getFrom()+" — "+ return_way.get(0).getTo());
            Manual_Flight_Detials_Adapter_Travelhope adapter = new Manual_Flight_Detials_Adapter_Travelhope(Manual_flight_detials_New.this,single_retun,"oneway",dataItem);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));


            Manual_Flight_Detials_Adapter_Travelhope adapter2 = new Manual_Flight_Detials_Adapter_Travelhope(Manual_flight_detials_New.this,single_go,"oneway",dataItem);


            tv_cities2.setText(return_way.get(1).getFrom()+" — "+ return_way.get(1).getTo());
            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));


        }


    }



    private void book() {

        Bundle bundle=new Bundle();
        bundle.putParcelable("flight_object",cabin_class);
        if(sharedPreferences.getBoolean("Check_Login",true) && !sharedPreferences.getBoolean("coupons",false))
        {
            Intent intent=new Intent(Manual_flight_detials_New.this, WebViewInvoice.class);
            intent.putExtra("check_type","flight");
            intent.putExtra("Check_Guest",false);
            Bundle b = new Bundle();
            b.putParcelable("cabin_class",cabin_class);
            intent.putExtras(b);
            intent.putExtra("id",id);
            intent.putExtras(bundle);
            startActivity(intent);

        }else
        {
            Intent intent=new Intent(Manual_flight_detials_New.this, Invoice.class);
            intent.putExtra("check_type","flights");
            Bundle b = new Bundle();
            b.putParcelable("cabin_class",cabin_class);
            intent.putExtras(b);
            intent.putExtra("id",id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }



    public void postrequest(String url)
    {
        dialog.show();

        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                //   Log.d("bordingpointtest","response/" +response);
                try {
                    OneWay oneWay = new OneWay();
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray route = data.optJSONArray("route");
                    oneWay.setName(" "+route.get(0));
                    oneWay.setTo_name(" "+route.get(1));

                    JSONArray flights = data.getJSONArray("flights");
                    for (int i =0 ; i<flights.length();i++)
                    {
                        JSONObject Object_flights = flights.getJSONObject(i);
                        String departure_time  = Object_flights.getString("departure_time");
                        String arrival_time  = Object_flights.getString("arrival_time");

                        oneWay.setDate(date_formate(arrival_time));
                        oneWay.setTo_date(date_formate(departure_time));
                        oneWay.setTime(time_formate(arrival_time));
                        oneWay.setTo_time(time_formate(departure_time));
                    }







                    // onew_way.add(oneWay);







/*
                    myViewHolder.a_date.setText(flightModel.getTo_date());
                    myViewHolder.d_date.setText(flightModel.getDate());
                    myViewHolder.a_time.setText(flightModel.getTo_time());
                    myViewHolder.d_time.setText(flightModel.getTime());
                    myViewHolder.l_from.setText(flightModel.getName());
                    myViewHolder.l_to.setText(flightModel.getTo_name());
*/



                } catch (JSONException e) {

                    Log.d("bordingpoint2", e.getMessage());
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    // finish();
                }

                dialog.dismiss();
                Manual_Flight_Detials_Adapter_Travelhope adapter = new Manual_Flight_Detials_Adapter_Travelhope(Manual_flight_detials_New.this,onew_way,"oneway",dataItem);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Manual_flight_detials_New.this));

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error == null)
                    return;
                String logText;
                if (error.networkResponse == null) {
                    logText = error.getMessage();
                } else {
                    logText = error.getMessage() + ", status "
                            + error.networkResponse.statusCode
                            + " - " + error.networkResponse.toString();
                }


                //  Log.d("boardingpoint",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                //  finish();
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {

                String booking_token= dataItem.getCustomPayload().getBookingToken();
                String visitor_uniqeid = dataItem.getCustomPayload().getVisitorUniqid();
                String flight_id = dataItem.getFlight_id();
                String adults = dataItem.getAdults();
                String children = dataItem.getChildren();
                String infants = dataItem.getInfants();

                Map<String, String> params = new HashMap<String, String>();
                params.put("flight_id",flight_id);
                params.put("booking_token",booking_token);
                params.put("visitor_uniqeid",visitor_uniqeid );
                params.put("adults", adults);
                params.put("children",  children );
                params.put("infants",infants);
                params.put("ota_id","");
                params.put("vendor","5");

                String g= gson.toJson(params);
                Log.d("bordingpointtest",g);
                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);
    }


    public String  date_formate(String convert_date)
    {
        @SuppressLint({"NewApi", "LocalSuppress"})
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyy");

        final long unixTime = Long.parseLong(convert_date);
        @SuppressLint({"NewApi", "LocalSuppress"})
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);

        return formattedDtm;
    }

    public String  time_formate(String convert_date)
    {
        @SuppressLint({"NewApi", "LocalSuppress"})
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm ");

        final long unixTime = Long.parseLong(convert_date);
        @SuppressLint({"NewApi", "LocalSuppress"})
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);

        return formattedDtm;
    }



    public String date_time_return(int minute)
    {
        int hour = minute / 60;
        minute %= 60;
        String p = "AM";
        if (hour >= 12) {
            hour %= 12;
            p = "PM";
        }
        if (hour == 0) {
            hour = 12;
        }
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + " " + p;
    }


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "hh:mm:aa";
        String outputPattern = "hh:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }





    public void TarcoFlight_deatils()
    {


        final String url = domain_name+"TarcoFlights/detail?appKey="+Constant.key+"&Offer_Ref="+tarco_model.getOffer_Ref()+"&Offer_RefItinerary="+tarco_model.getOffer_RefItinerary();
        Log.d("testingggg",url);
       // String url = domain_name+"TarcoFlights/detail?appKey="+ Constant.key+"&departure_date=2019-12-30&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+"&adults="+cabin_class.getAdults()+"&childs="+cabin_class.getChild()+"&infants="+cabin_class.getInfants();
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                 Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                // segmentedProgressBar.pause();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error == null)
                    return;
                String logText;
                if (error.networkResponse == null) {
                    logText = error.getMessage();
                } else {
                    logText = error.getMessage() + ", status "
                            + error.networkResponse.statusCode
                            + " - " + error.networkResponse.toString();
                }


                //  Log.d("boardingpoint",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(1000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);

    }

}
