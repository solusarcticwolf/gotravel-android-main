package com.phptravelsnative.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.google.gson.Gson;
import com.phptravelsnative.Adapters.AutoSuggestedAdapter;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Views.SingleTonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchViewActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    EditText searchName;
    Destinations adatpter;
    ImageView clear_btn;
    Intent intent;
    String MyPREFERENCES = "MyPrefs";
    Auto_Model selectedValue=new Auto_Model();
    AutoSuggestedAdapter adapter=null;

    private String COUNTRY_URL = Constant.domain_name+"hotels/suggestions?appKey="+Constant.key+"&query=";
    ComplexPreferences complexPreferences;
     ArrayList<Auto_Model> auto_models=new ArrayList<>();
    ListView listView;


    SegmentedProgressBar segmentedProgressBar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view2;
    LayoutInflater layoutInflaterAndroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        searchName=(EditText) findViewById(R.id.auto_search);
        listView=(ListView)findViewById(R.id.listview);
         complexPreferences=new ComplexPreferences(this,MyPREFERENCES,Context.MODE_PRIVATE);

        clear_btn=(ImageView)findViewById(R.id.clearButton);

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchName.setText("");
            }
        });

        intent=getIntent();
        String ModuleType=intent.getStringExtra("ModuleTyple");



        builder = new AlertDialog.Builder(SearchViewActivity.this);
        layoutInflaterAndroid = LayoutInflater.from(SearchViewActivity.this);
        view2 = layoutInflaterAndroid.inflate(R.layout.progress_dialog, null);
        builder.setView(view2);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        segmentedProgressBar = (SegmentedProgressBar) view2.findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(50000);






        if(ModuleType.equals("Bus"))
        {
            BusSearch();
        }
        else if(ModuleType.equals("DefaultHotel")){

            selectedValue=complexPreferences.getObject("Default_Hotel_Last_Search",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            DefaultHotel(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("Default_Hotel_Last_Search",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        DefaultHotel(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        else if(ModuleType.equals("ExpediaHotel")){

            selectedValue=complexPreferences.getObject("Expedia_Hotel_Last_Search",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            ExpediaSearch(selectedValue.getName());

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("Expedia_Hotel_Last_Search",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        ExpediaSearch(s.toString());
                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        }

        else if(ModuleType.equals("TravelshopeHotels")){


            selectedValue=complexPreferences.getObject("Travelshope_Hotels_Last_Search",Auto_Model.class,selectedValue);
              searchName.setText(selectedValue.getName());
           // ExpediaSearch(selectedValue.getName());*/

            TravelshopeHotels(selectedValue.getName());


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Auto_Model auto_mode2=auto_models.get(position);

                    complexPreferences.putObject("Travelshope_Hotels_Last_Search",auto_mode2);
                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if(s.length()>=2) {
                        adapter.getFilter().filter(s);

                        TravelshopeHotels(""+s);
                    }

                    if (s.length()==0)
                    {

                    }

                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        } else if(ModuleType.equals("TarcoFlight_from")){



            //  Toast.makeText(getApplicationContext(),"from",Toast.LENGTH_LONG).show();

            selectedValue=complexPreferences.getObject("TarcoFlight_from",Auto_Model.class,selectedValue);
            // searchName.setText(selectedValue.getName());
            //TravelPortSearch(selectedValue.getName());



            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    airportdatad();

                    // close this activity


                }

            }, 1*500);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TarcoFlight_from",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapter.getFilter().filter(s);
                   /* if(s.length()>=2) {

                        //TravelPortSearch(s.toString());
                        //airportdatad(s.toString());
                       *//* ArrayList<Auto_Model> autoModelArrayList=searchdataarray(auto_models,s.toString());
                        Gson gson = new Gson();
                        String data = gson.toJson(autoModelArrayList);
                        Log.d("datadatadata/"," /"+data);*//*

                    }*/
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if(ModuleType.equals("TarcoFlight_to")){

            selectedValue=complexPreferences.getObject("TarcoFlight_to",Auto_Model.class,selectedValue);
            // searchName.setText(selectedValue.getName());
            //TravelPortSearch(selectedValue.getName());


            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    airportdatad();

                    // close this activity


                }

            }, 2*1000);



            //  airportdatad();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TarcoFlight_to",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapter.getFilter().filter(s);

                  /*  if(s.length()>=0) {

                        Gson gson = new Gson();
                        Tours(s.toString());
                        String data = gson.toJson(auto_models);
                        Log.d("dataairport","");
                    }
                    */



                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else if(ModuleType.equals("TravelPort_from")){







          //  Toast.makeText(getApplicationContext(),"from",Toast.LENGTH_LONG).show();

            selectedValue=complexPreferences.getObject("TravelPort_from",Auto_Model.class,selectedValue);
           // searchName.setText(selectedValue.getName());
             //TravelPortSearch(selectedValue.getName());



            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    airportdatad();

                    // close this activity


                }

            }, 1*500);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TravelPort_from",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapter.getFilter().filter(s);
                   /* if(s.length()>=2) {

                        //TravelPortSearch(s.toString());
                        //airportdatad(s.toString());
                       *//* ArrayList<Auto_Model> autoModelArrayList=searchdataarray(auto_models,s.toString());
                        Gson gson = new Gson();
                        String data = gson.toJson(autoModelArrayList);
                        Log.d("datadatadata/"," /"+data);*//*

                    }*/
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if(ModuleType.equals("TravelPort_fromtaxi")){

            //Toast.makeText(getApplicationContext(),"fromtaxi",Toast.LENGTH_LONG).show();

            selectedValue=complexPreferences.getObject("TravelPort_fromtaxi",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            TravelPortSearch_fromtaxi(selectedValue.getName());
            //airportdatad(selectedValue.getName());



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TravelPort_fromtaxi",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        TravelPortSearch_fromtaxi(s.toString());
                        //airportdatad(s.toString());
                        ArrayList<Auto_Model> autoModelArrayList=searchdataarray(auto_models,s.toString());
                        Gson gson = new Gson();
                        String data = gson.toJson(autoModelArrayList);
                        Log.d("datadatadata/"," /"+data);

                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if(ModuleType.equals("TravelPort_totaxi")){

            //Toast.makeText(getApplicationContext(),"TravelPort_totaxi",Toast.LENGTH_LONG).show();

            selectedValue=complexPreferences.getObject("TravelPort_totaxi",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            TravelPortSearch_fromtaxi(selectedValue.getName());
            //airportdatad(selectedValue.getName());



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TravelPort_totaxi",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()>=2) {

                        TravelPortSearch_fromtaxi(s.toString());
                        //airportdatad(s.toString());
                        ArrayList<Auto_Model> autoModelArrayList=searchdataarray(auto_models,s.toString());
                        Gson gson = new Gson();
                        String data = gson.toJson(autoModelArrayList);
                        Log.d("datadatadata/"," /"+data);

                    }
                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if(ModuleType.equals("TravelPort_to")){

            selectedValue=complexPreferences.getObject("TravelPort_to",Auto_Model.class,selectedValue);
           // searchName.setText(selectedValue.getName());
           //TravelPortSearch(selectedValue.getName());


            new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

                @Override

                public void run() {

                    airportdatad();

                    // close this activity


                }

            }, 2*1000);



          //  airportdatad();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TravelPort_to",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });
            searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    adapter.getFilter().filter(s);

                  /*  if(s.length()>=0) {

                        Gson gson = new Gson();
                        Tours(s.toString());
                        String data = gson.toJson(auto_models);
                        Log.d("dataairport","");
                    }
                    */



                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }else if(ModuleType.equals("Tour")){
           // Toast.makeText(getApplicationContext(),"Tour",Toast.LENGTH_LONG).show();
            selectedValue=complexPreferences.getObject("TourObject",Auto_Model.class,selectedValue);
            searchName.setText(selectedValue.getName());
            Tours(selectedValue.getName());





            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    complexPreferences.putObject("TourObject",auto_models.get(position));

                    complexPreferences.commit();
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                }
            });



           searchName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                  /*  if(s.length()>=2) {

                        Gson gson = new Gson();
                        Tours(s.toString());
                        String data = gson.toJson(auto_models);
                        Log.d("dataairport","");
                    }
                    */



                    if(searchName.getText().length()>0)
                        clear_btn.setVisibility(View.VISIBLE);
                    else
                        clear_btn.setVisibility(View.GONE);
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });




        }else if(ModuleType.equals("CarTo")){

            CarsTo();

        }else if(ModuleType.equals("CarFrom")){

            CarsFrom();
        }
        searchName.setSelection(searchName.getText().length());

        if(searchName.getText().length()>0)
            clear_btn.setVisibility(View.VISIBLE);
        else
            clear_btn.setVisibility(View.GONE);


    }

    public  void Tours(String term)
    {
       // COUNTRY_URL = Constant.domain_name+"tours/suggestions?appKey="+Constant.key+"&query=";
        //COUNTRY_URL+term+"&lang="+Constant.default_lang
       // alertDialog.show();
        COUNTRY_URL = Constant.domain_name+"Tours/suggestionResultsmobile?appKey="+Constant.key;


        StringRequest stringRequest = new StringRequest(Request.Method.GET,COUNTRY_URL+"&lang="+Constant.default_lang, new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                JSONObject parentObject = null;
                auto_models.clear();
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("response");
                    Auto_Model auto_model;
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        if(!jo.getString("text").equals("")) {
                            auto_model = new Auto_Model();
                            auto_model.setType(jo.getString("module"));
                            auto_model.setName(jo.getString("text"));
                            auto_model.setId(jo.getInt("id"));
                            //auto_model.setCountryCode(jo.getString("countryCode"));

                            if(jo.getString("module").equals("tour"))
                            {
                                auto_model.setImage_id(R.drawable.ic_tour_icon);
                            }else
                            {
                                auto_model.setImage_id(R.drawable.location_search);

                            }
                            auto_models.add(auto_model);
                        }
                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }
               alertDialog.dismiss();
                if(adapter==null) {
                    adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                    listView.setAdapter(adapter);
                }else
                {
                    adapter.notifyDataSetChanged();
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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void BusSearch()
    {
        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(adatpter!=null)
                    adatpter.getFilter().filter(s.toString());

                if(searchName.getText().length()>0)
                    clear_btn.setVisibility(View.VISIBLE);
                else
                    clear_btn.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final int check=intent.getIntExtra("checkSearchType",-1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(check==0)
                    complexPreferences.putObject("busObjectFrom",adatpter.getItem(position));
                else if(check==1)
                    complexPreferences.putObject("busObjectTo",adatpter.getItem(position));

                complexPreferences.commit();
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        Response.Listener response_listener = new Response.Listener<String>() {

            Auto_Model auto_model;

            @Override
            public void onResponse(String response) {
                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response);
                    JSONObject mainObject=parentObject.getJSONObject("response");
                    JSONArray ciities=mainObject.getJSONArray("cities");

                    for(int i=0;i<ciities.length();i++)
                    {
                        JSONObject jsonObject=ciities.getJSONObject(i);
                        auto_model=new Auto_Model();
                        auto_model.setName(jsonObject.getString("name"));
                        auto_model.setId(jsonObject.getInt("id"));
                        auto_model.setImage_id(R.drawable.location_search);
                        auto_models.add(auto_model);
                    }
                    adatpter=   new Destinations(SearchViewActivity.this,auto_models);
                    listView.setAdapter(adatpter);
                    alertDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        StringRequest  stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"bus/cities?appKey="+Constant.key
                , response_listener, null);

        RequestQueue requestQueue = SingleTonRequest.getmInctance(this).getRequestQueue();
        requestQueue.add(stringRequest);
    }

    public  void DefaultHotel(String term)
    {
       // alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                COUNTRY_URL+term+"&lang="+Constant.default_lang
                ,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(response.toString());
                            JSONArray jsonArray= parentObject.getJSONArray("response");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                if(!jo.getString("text").equals("")) {
                                    auto_model = new Auto_Model();
                                    auto_model.setType(jo.getString("module"));
                                    auto_model.setName(jo.getString("text"));

                                    if(jo.getString("module").equals("hotel"))
                                    {
                                        auto_model.setImage_id(R.drawable.hotel_search);
                                    }else
                                    {
                                        auto_model.setImage_id(R.drawable.location_search);
                                    }
                                    auto_model.setId(jo.getInt("id"));
                                    auto_models.add(auto_model);
                                }
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }

                        alertDialog.dismiss();
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();
                        }

                       // alertDialog.dismiss();

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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public  void ExpediaSearch(String term)
    {


       // alertDialog.show();
        Log.d("CheckValue","http://yasen.hotellook.com/autocomplete?lang=en-US&limit=10&term="+term);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://yasen.hotellook.com/autocomplete?lang=en-US&limit=10&term="+term                ,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(response.toString());
                            JSONArray jsonArray= parentObject.getJSONArray("cities");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                auto_model=new Auto_Model();
                                auto_model.setName(jo.getString("fullname"));
                                int a = getResources().getIdentifier("flag_"+jo.getString("countryCode").toLowerCase(), "drawable", getApplicationContext().getPackageName());
                                auto_model.setImage_id(a);
                                auto_models.add(auto_model);
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }
                        alertDialog.dismiss();
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();

                        }
                      //  alertDialog.dismiss();
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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public  void TravelPortSearch(String term)
    {


      //  alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name+"suggession/airports?appKey="+Constant.key+"&q="+term,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(response.toString());
                            JSONArray jsonArray= parentObject.getJSONArray("response");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                auto_model=new Auto_Model();
                                auto_model.setName(jo.getString("text"));
                                auto_model.setType(jo.getString("id"));
                                auto_model.setCountryCode(jo.getString("countryCode"));
                                int a = getResources().getIdentifier("flag_"+jo.getString("countryCode").toLowerCase(), "drawable", getApplicationContext().getPackageName());
                                auto_model.setImage_id(a);
                                auto_models.add(auto_model);
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }
                        alertDialog.dismiss();
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();

                        }

                      //  alertDialog.dismiss();
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
                Log.e("HamzaError" + "-", logText, error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void CarsTo()
    {


        selectedValue=complexPreferences.getObject("carObjectTo",Auto_Model.class,selectedValue);

        searchName.setText(selectedValue.getName());

        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(adatpter!=null)
                    adatpter.getFilter().filter(s.toString());

                if(searchName.getText().length()>0)
                    clear_btn.setVisibility(View.VISIBLE);
                else
                    clear_btn.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(),"kfdfdkfd",Toast.LENGTH_LONG).show();
                    complexPreferences.putObject("carObjectTo",adatpter.getItem(position));
                    complexPreferences.putObject("carObjectFrom",adatpter.getItem(position));
                     complexPreferences.commit();
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"cars/locations?appKey="+Constant.key+"&lang="+Constant.default_lang, new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                JSONObject parentObject = null;

                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject error_object=parentObject.getJSONObject("error");

                    if(error_object.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(),error_object.getString("msg"),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject paretnObject1 = parentObject.getJSONObject("response");
                        JSONArray parentArray = paretnObject1.getJSONArray("dropoffLocations");

                        Auto_Model auto_model;
                        for(int i=0;i<parentArray.length();i++)
                        {
                            auto_model=new Auto_Model();
                            JSONObject jsonObject=parentArray.getJSONObject(i);
                            auto_model.setName(jsonObject.getString("name"));
                            auto_model.setId(jsonObject.getInt("id"));
                            auto_model.setCountryCode(" ");
                            auto_model.setImage_id(R.drawable.location_search);
                            auto_models.add(auto_model);
                        }
                    }
                    alertDialog.dismiss();
                    adatpter = new Destinations(getApplicationContext(),auto_models);
                    listView.setAdapter(adatpter);

                }
                catch(JSONException e){
                    Log.d("abcwwwwd",e.getMessage());
                }
            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    private void CarsFrom()
    {

        selectedValue=complexPreferences.getObject("carObjectFrom",Auto_Model.class,selectedValue);
        searchName.setText(selectedValue.getName());
        searchName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adatpter!=null)
                    adatpter.getFilter().filter(s.toString());

                if(searchName.getText().length()>0)
                    clear_btn.setVisibility(View.VISIBLE);
                else
                    clear_btn.setVisibility(View.GONE);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                complexPreferences.putObject("carObjectFrom",adatpter.getItem(position));
                complexPreferences.commit();
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"cars/locations?appKey="+Constant.key+"&lang="+Constant.default_lang, new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                JSONObject parentObject = null;

                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject error_object=parentObject.getJSONObject("error");

                    if(error_object.getBoolean("status"))
                    {
                        Toast.makeText(getApplicationContext(),error_object.getString("msg"),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        JSONObject paretnObject1 = parentObject.getJSONObject("response");
                        JSONArray to = paretnObject1.getJSONArray("pickupLocations");

                        Auto_Model auto_model;
                        for(int i=0;i<to.length();i++)
                        {
                            auto_model=new Auto_Model();
                            JSONObject jsonObject=to.getJSONObject(i);
                            auto_model.setName(jsonObject.getString("name"));
                            auto_model.setId(jsonObject.getInt("id"));
                            auto_model.setImage_id(R.drawable.location_search);
                            auto_model.setCountryCode(" ");
                            auto_models.add(auto_model);
                        }
                    }
                    alertDialog.dismiss();
                    adatpter = new Destinations(SearchViewActivity.this,auto_models);
                    listView.setAdapter(adatpter);
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }

            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public  void airportdatad()
    {



                        String responcae = loadJSONFromAssets();
                        JSONObject parentObject = null;
                        auto_models.clear();
                        try {
                            parentObject = new JSONObject(responcae);
                            JSONArray jsonArray= parentObject.getJSONArray("flights_airports");
                            Auto_Model auto_model;
                            for (int i = 0; i <jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                auto_model=new Auto_Model();
                                auto_model.setName(jo.getString("name"));
                                auto_model.setType(jo.getString("id"));
                                auto_model.setCountryCode(jo.getString("countryCode"));
                                auto_model.setCityCode(jo.getString("cityCode"));
                                int a = getResources().getIdentifier("flag_"+jo.getString("countryCode").toLowerCase(), "drawable", getApplicationContext().getPackageName());
                                auto_model.setImage_id(a);
                                auto_models.add(auto_model);
                            }
                        }
                        catch(JSONException e){

                            Log.d("abcwwwwd",e.getMessage());
                        }



                       alertDialog.dismiss();
                        if(adapter==null) {
                            adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                            listView.setAdapter(adapter);
                        }else
                        {
                            adapter.notifyDataSetChanged();

                        }

    }

    public String loadJSONFromAssets() {
        String json = null;
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("airports.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }


    public class Destinations extends BaseAdapter implements Filterable {

        private ArrayList<Auto_Model> originalData = null;
        private ArrayList<Auto_Model>filteredData = null;
        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();

        public Destinations(Context context, ArrayList<Auto_Model> data) {
            this.filteredData = data ;
            this.originalData = data ;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return filteredData.size();
        }

        public Auto_Model getItem(int position) {
            return filteredData.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.searching, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            final Auto_Model currentListData = getItem(position);

            mViewHolder.tvTitle.setText(currentListData.getName());
            mViewHolder.ivIcon.setImageResource(currentListData.getImage_id());

            return convertView;
        }

        private class MyViewHolder {
            TextView tvTitle;
            ImageView ivIcon;

            public MyViewHolder(View item) {
                tvTitle = (TextView) item.findViewById(R.id.city_name);
                ivIcon = (ImageView) item.findViewById(R.id.iv_icon);

            }
        }

        public Filter getFilter() {

            return mFilter;
        }

        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final ArrayList<Auto_Model> list = originalData;

                int count = list.size();
                final ArrayList<Auto_Model> nlist = new ArrayList<Auto_Model>(count);

                String filterableString ;
                String filterableStringcountrycode ;

                for (int i = 0; i < count; i++) {

                    filterableString = list.get(i).getName();
                    filterableStringcountrycode = list.get(i).getCountryCode();
                    if (filterableString.toLowerCase().contains(filterString) ) {
                        nlist.add(list.get(i));
                    }
                }
                results.values = nlist;
                results.count = nlist.size();

                return results;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<Auto_Model>) results.values;
                notifyDataSetChanged();
            }
        }
    }

    public ArrayList<Auto_Model> searchdataarray(ArrayList<Auto_Model> auto_models,String keyword)
    {

        ArrayList<Auto_Model> result = new ArrayList<Auto_Model>();
        for (Auto_Model person : auto_models ) {

            String filterableStringcountrycode = person.getCountryCode();
            if (filterableStringcountrycode.toLowerCase().contains(keyword)) {
                result.add(person);
            }
        }
        return  result;
    }


    public  void TravelPortSearch_fromtaxi(final String term)
    {
        String url=  Constant.domain_name+"/kiwitaxi/placesearch?appKey="+Constant.key;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("bordingpointtaxi","response/" +response);
                JSONObject parentObject = null;
                auto_models.clear();
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("suggestions");
                    Auto_Model auto_model;
                    for (int i = 0; i <jsonArray.length(); i++) {

                        auto_model=new Auto_Model();

                        String name = jsonArray.getString(i);
                        auto_model.setName(name);
                        auto_model.setId(0);
                        auto_model.setImage_id(R.drawable.location_search);
                        auto_model.setCountryCode("");
                        auto_models.add(auto_model);
                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }
                if(adapter==null) {
                    adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                    listView.setAdapter(adapter);
                }else
                {
                    adapter.notifyDataSetChanged();

                }




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
                Log.e("HamzaError" + "-", logText, error);

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {



                Map<String, String> params = new HashMap<String, String>();
                params.put("place_name",term);



                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);


    }

    public  void TravelshopeHotels(final String term)
    {

        //alertDialog.dismiss();
         //alertDialog.show();



        String url= Constant.domain_name+"Travelhopehotels/suggestions?appKey="+Constant.key;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("bordingpointtaxi","response/" +response);
                JSONObject parentObject = null;
                auto_models.clear();
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("response");
                    Auto_Model auto_model;
                    for (int i = 0; i <jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        auto_model=new Auto_Model();

                        String name = jsonObject.getString("text");
                        String id = jsonObject.getString("id");
                        auto_model.setName(name);
                        auto_model.setId(0);
                        auto_model.setImage_id(R.drawable.location_search);
                        auto_model.setCountryCode(id);
                        auto_models.add(auto_model);
                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }

                alertDialog.dismiss();
                if(adapter==null) {
                    adapter = new AutoSuggestedAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, auto_models);
                    listView.setAdapter(adapter);
                }else
                {
                    adapter.notifyDataSetChanged();

                }




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
                Log.e("HamzaError" + "-", logText, error);

            }
        }) {
            @Override
            protected Map<String, String> getParams()  {



                Map<String, String> params = new HashMap<String, String>();
                params.put("query",term);



                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);

    }


}

