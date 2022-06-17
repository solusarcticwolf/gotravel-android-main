package com.phptravelsnative.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.devspark.robototextview.widget.RobotoCheckedTextView;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phptravelsnative.Adapters.Manual_Flight_Adapter;
import com.phptravelsnative.Adapters.Manual_Flight_Adapter2;
import com.phptravelsnative.Adapters.Tp_Listing_adapter;
import com.phptravelsnative.Models.Airline;
import com.phptravelsnative.Models.CustomPayload;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.Flights2;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.RoutesItem;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static com.phptravelsnative.Activities.Searchfilter.fromJson;

public class ManualFlightActivity extends Drawer {

    ArrayList<ManualFlightModel> array_main = new ArrayList<>();
    SegmentedProgressBar  segmentedProgressBar;
    Tp_Listing_adapter adapter;
    ArrayList<TravelPortModel> tpm = new ArrayList<>();
    TravelPort cabin_class = new TravelPort();
    RecyclerView recyclerView;
    RobotoTextView tv_citiesname;
    ImageView filter ;
    Context context=this;
    String adults,children,inflant,tourtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.fragment_recycler_view);
        View inflated = stub.inflate();

        Intent intent = getIntent();
        cabin_class = intent.getExtras().getParcelable("cabin_date");
        cabin_class = intent.getExtras().getParcelable("cabin_date");

        adults = cabin_class.getAdults();
        children = cabin_class.getChild();
        inflant = cabin_class.getInfants();
        tourtype = cabin_class.getTour_type();

        if (children==null)
        {
            children="0";
        }


        if (inflant==null)
        {
            inflant="0";
        }
        dialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        recyclerView = inflated.findViewById(R.id.recyclerView);



        segmentedProgressBar = (SegmentedProgressBar) findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        //segmentedProgressBar.setContainerColor(Color.WHITE);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(50000);
        tv_citiesname = (RobotoTextView) findViewById(R.id.tv_citiesname);

        filter = findViewById(R.id.filter);

      //  ExpediaSearch();
        String data =getAssetJsonData2(context);
        loading(data);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchfilterd_dialogbox();
            }
        });



    }

    public void ExpediaSearch() {

        String url = "";
       // dialog.show();
        segmentedProgressBar.setVisibility(View.VISIBLE);
        if(!cabin_class.getTour_type().equals("oneway"))
        {
            cabin_class.setTour_type("return");
        }

        url = Constant.domain_name+"flights/search?appKey="+Constant.key+"&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+
                "&departure_date="+cabin_class.getFrom_date()+"&arrival_date="+cabin_class.getTo_date()+"&type="+cabin_class.getTour_type()+
                "&cabinclass="+cabin_class.getClass_type()+"&adults="+cabin_class.getAdults()+"&childs="+cabin_class.getChild()
                +"&infants="+cabin_class.getInfants();




        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {



                        JSONObject parentObject = null;
                        try {
                        parentObject = new JSONObject(response.toString());
                        if (!parentObject.getJSONObject("error").getBoolean("status")) {

                            JSONArray main_array = parentObject.getJSONArray("response");
                            for(int i=0;i<main_array.length();i++)
                            {
                                JSONObject oneWay = main_array.getJSONObject(i).getJSONObject("oneway");
                                JSONObject  return_ = main_array.getJSONObject(i).getJSONObject("return");

                                ManualFlightModel mainObject = new ManualFlightModel();
                                mainObject.setDes(oneWay.getString("desc_flight"));
                                mainObject.setTotal_time(oneWay.getString("total_hours"));
                                mainObject.setId(oneWay.getString("id"));
                                mainObject.setPrice(oneWay.getString("price"));
                                mainObject.setCurrSymbol(oneWay.getString("currency"));
                                mainObject.setCurrCode(oneWay.getString("symbol"));
                                mainObject.setAero_name(oneWay.getString("name"));

                                for(int j =0;j<oneWay.getJSONArray("mainArray").length();j++)
                                {
                                    JSONObject jsonInnerObject = oneWay.getJSONArray("mainArray").getJSONObject(j);
                                    OneWay innerObject = new OneWay();
                                    innerObject.setCode(jsonInnerObject.getString("from_code"));
                                    innerObject.setDate(jsonInnerObject.getString("from_date"));
                                    innerObject.setName(jsonInnerObject.getString("from_label"));
                                    innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
                                    innerObject.setTime(jsonInnerObject.getString("from_time"));
                                    innerObject.setImg(jsonInnerObject.getString("aero_img"));
                                    innerObject.setTo_code(jsonInnerObject.getString("to_code"));
                                    innerObject.setTo_date(jsonInnerObject.getString("to_date"));
                                    innerObject.setTo_time(jsonInnerObject.getString("to_time"));
                                    innerObject.setTo_name(jsonInnerObject.getString("to_label"));
                                    mainObject.getModels_array().add(innerObject);
                                }
                                if(!cabin_class.getTour_type().equals("oneway")){

                                    for(int j =0;j<return_.getJSONArray("mainArray").length();j++)
                                    {
                                        JSONObject jsonInnerObject = return_.getJSONArray("mainArray").getJSONObject(j);
                                        OneWay innerObject = new OneWay();
                                        innerObject.setCode(jsonInnerObject.getString("from_code"));
                                        innerObject.setDate(jsonInnerObject.getString("from_date"));
                                        innerObject.setName(jsonInnerObject.getString("from_label"));
                                        innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
                                        innerObject.setTime(jsonInnerObject.getString("from_time"));
                                        innerObject.setImg(jsonInnerObject.getString("aero_img"));
                                        innerObject.setTo_code(jsonInnerObject.getString("to_code"));
                                        innerObject.setTo_date(jsonInnerObject.getString("to_date"));
                                        innerObject.setTo_time(jsonInnerObject.getString("to_time"));
                                        innerObject.setTo_name(jsonInnerObject.getString("to_label"));

                                        mainObject.getReturn_array().add(innerObject);
                                    }

                                }
                                array_main.add(mainObject);
                            }

                            Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity.this, array_main,cabin_class);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity.this));


                            //segmentedProgressBar.setVisibility(View.GONE);

                        } else {

                            Toast.makeText(getBaseContext(), parentObject.getJSONObject("error").getString("msg"), Toast.LENGTH_LONG).show();
                            finish();

                        }
                        //dialog.dismiss();

                        tv_citiesname.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());
                        tv_citiesname.setVisibility(View.VISIBLE);
                        segmentedProgressBar.setVisibility(View.GONE);
                        filter.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {

                        Log.d("abcwwwwd", e.getMessage());
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        //dialog.dismiss();
                        segmentedProgressBar.setVisibility(View.GONE);
                        finish();
                    }
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
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                finish();


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void searchfilterd_dialogbox() {




            //  Toast.makeText(getApplicationContext(),"flightsflights",Toast.LENGTH_LONG).show();
            Gson gson = new Gson();
            String myJson = gson.toJson(array_main);
            String cabin_class1 = gson.toJson(cabin_class);
            Intent intent = new Intent(context, Searchfilter.class);
            intent.putExtra("array_main_flight", myJson);
            intent.putExtra("cabin_class", cabin_class1);
            intent.putExtra("Model", "flights");
            startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
           // finish();




    }





    public  String getAssetJsonData2(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("city.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;

        }


        return json;
    }



    public void loading(String json)
    {
        JSONObject parentObject = null;
        try {
            parentObject = new JSONObject(json.toString());
            if (!parentObject.getJSONObject("error").getBoolean("status")) {

                JSONArray main_array = parentObject.getJSONArray("response");
                for(int i=0;i<main_array.length();i++)
                {
                    JSONObject oneWay = main_array.getJSONObject(i).getJSONObject("oneway");
                    JSONObject  return_ = main_array.getJSONObject(i).getJSONObject("return");

                    ManualFlightModel mainObject = new ManualFlightModel();
                    mainObject.setDes(oneWay.getString("desc_flight"));
                    mainObject.setTotal_time(oneWay.getString("total_hours"));
                    mainObject.setId(oneWay.getString("id"));
                    mainObject.setPrice(oneWay.getString("price"));
                    mainObject.setCurrSymbol(oneWay.getString("currency"));
                    mainObject.setCurrCode(oneWay.getString("symbol"));
                    mainObject.setAero_name(oneWay.getString("name"));



                    mainObject.setAdults(adults);
                    mainObject.setChildren(children);
                    mainObject.setInfants(inflant);
                    mainObject.setFlight_type(tourtype);

                    for(int j =0;j<oneWay.getJSONArray("mainArray").length();j++)
                    {
                        JSONObject jsonInnerObject = oneWay.getJSONArray("mainArray").getJSONObject(j);
                        OneWay innerObject = new OneWay();
                        innerObject.setCode(jsonInnerObject.getString("from_code"));
                        innerObject.setDate(jsonInnerObject.getString("from_date"));
                        innerObject.setName(jsonInnerObject.getString("from_label"));
                        innerObject.setFlight_no(jsonInnerObject.getString("flight_no"));
                        innerObject.setTime(jsonInnerObject.getString("from_time"));
                        innerObject.setImg(jsonInnerObject.getString("aero_img"));
                        innerObject.setTo_code(jsonInnerObject.getString("to_code"));
                        innerObject.setTo_date(jsonInnerObject.getString("to_date"));
                        innerObject.setTo_time(jsonInnerObject.getString("to_time"));
                        innerObject.setTo_name(jsonInnerObject.getString("to_label"));
                        mainObject.getModels_array().add(innerObject);
                    }

                    array_main.add(mainObject);
                }

                Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity.this, array_main,cabin_class);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity.this));


                //segmentedProgressBar.setVisibility(View.GONE);

            } else {

                Toast.makeText(getBaseContext(), parentObject.getJSONObject("error").getString("msg"), Toast.LENGTH_LONG).show();
                //finish();

            }
            //dialog.dismiss();

            tv_citiesname.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());
            tv_citiesname.setVisibility(View.VISIBLE);
            segmentedProgressBar.setVisibility(View.GONE);
            filter.setVisibility(View.VISIBLE);
        } catch (JSONException e) {

            Log.d("abcwwwwd", e.getMessage());
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            //dialog.dismiss();
            segmentedProgressBar.setVisibility(View.GONE);
            //finish();
        }


    }

}
