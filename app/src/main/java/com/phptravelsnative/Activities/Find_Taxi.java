package com.phptravelsnative.Activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.Hotel_data;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.phptravelsnative.Adapters.Taxi_adapterlist;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.Taxi_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Find_Taxi extends AppCompatActivity {

    Hotel_data hotel_data;
    ComplexPreferences sharedPreferences;
    Auto_Model searchHotel=new Auto_Model();
    Auto_Model to_travel=new Auto_Model();
    String MyPREFERENCES = "MyPrefs";
    ArrayList<Taxi_model> arrayList_taxi =  new ArrayList<>();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RecyclerView recyclerView;
    SegmentedProgressBar  segmentedProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__taxi);



        Inti();
    }

    public  void Inti()
    {

        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getApplicationContext(),MyPREFERENCES, Context.MODE_PRIVATE);

        searchHotel=sharedPreferences.getObject("TravelPort_fromtaxi", Auto_Model.class,searchHotel);
        to_travel=sharedPreferences.getObject("TravelPort_totaxi",Auto_Model.class,searchHotel);

        recyclerView= findViewById(R.id.recyclerView);
        segmentedProgressBar = (SegmentedProgressBar) findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        //segmentedProgressBar.setContainerColor(Color.WHITE);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(10000);

        TravelPortSearch_fromtaxi();
    }

    public  void TravelPortSearch_fromtaxi()
    {
        String url= "https://www.bedpay.com/api/kiwitaxi/getlist?appKey="+ Constant.key;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("bordingpointtaxi","response/" +response);


                try {
                    JSONArray jsonArray_main =  new JSONArray(response);
                    Taxi_model taxi_model;
                    for (int i =0; i<jsonArray_main.length();i++)
                    {

                        JSONObject jsonObject = jsonArray_main.getJSONObject(i);
                         taxi_model =  new Taxi_model();

                        String description = jsonObject.getString("description");
                        String transfer_id = jsonObject.getString("transfer_id");
                        String carname = jsonObject.getString("carname");
                        String price = jsonObject.getString("price");
                        String currencycode = jsonObject.getString("currencycode");
                        String img = jsonObject.getString("img");
                        String pax = jsonObject.getString("pax");
                        String bag = jsonObject.getString("bag");
                        String carExamples = jsonObject.getString("carExamples");


                        taxi_model.setDescription(description);
                        taxi_model.setTransfer_id(transfer_id);
                        taxi_model.setCarname(carname);
                        taxi_model.setPrice(price);
                        taxi_model.setCurrencycode(currencycode);
                        taxi_model.setImg(img);
                        taxi_model.setPax(pax);
                        taxi_model.setBag(bag);
                        taxi_model.setCarExamples(carExamples);
                        arrayList_taxi.add(taxi_model);
                    }

                    segmentedProgressBar.setVisibility(View.GONE);


                    Taxi_adapterlist taxi_adapterlist =  new Taxi_adapterlist(getApplicationContext(),arrayList_taxi);
                    recyclerView.setAdapter(taxi_adapterlist);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Find_Taxi.this));


                }
                catch (JSONException e) {
                    Log.d("bordingpoint2", e.getMessage());
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    // dialog.dismiss();
                    finish();
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
                params.put("place_from",searchHotel.getName());
                params.put("place_to",to_travel.getName());
                params.put("lang","en");
                params.put("currencycode","usd");
                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(mStringRequest);


    }



}
