package com.phptravelsnative.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.SearchViewActivity;
import com.phptravelsnative.Activities.SearchingCarTourOffers;
import com.phptravelsnative.Activities.WebViewInvoice;
import com.phptravelsnative.Activities.Webview;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Extra.DateSeter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ivisa_home extends Fragment {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    EditText car_to;
    EditText car_from;

    EditText first_name,last_name,email,phone,notes;
    View inflated;

    String from_code="pakistan";
    String to_code="turkey";

    String from_data="pakistan";
    String to_data="turkey";

    SharedPreferences sharedPreferences;
    public  final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;

    Button search;
    SegmentedProgressBar  segmentedProgressBar;
    LinearLayout beforedone;
    RelativeLayout donee;


    AlertDialog.Builder builder;
    LayoutInflater layoutInflaterAndroid;
    View view2;
     AlertDialog alertDialog;
    public ivisa_home() {



    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflated = inflater.inflate(R.layout.ivisa_layout, container, false);
        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        car_to = (EditText) inflated.findViewById(R.id.car_to);
        car_from = (EditText) inflated.findViewById(R.id.car_from);
        first_name = (EditText) inflated.findViewById(R.id.first_name);
        last_name = (EditText) inflated.findViewById(R.id.last_name);
        email = (EditText) inflated.findViewById(R.id.email);
        phone = (EditText) inflated.findViewById(R.id.phone);
        notes = (EditText) inflated.findViewById(R.id.notes);

        notes.setShowSoftInputOnFocus(true);

        beforedone = (LinearLayout) inflated.findViewById(R.id.beforedone);
        donee = (RelativeLayout) inflated.findViewById(R.id.donee);


        car_from.setText(sharedPreferences.getString(from_data,""));
        car_to.setText(sharedPreferences.getString(to_data,""));

        from_code =  sharedPreferences.getString(from_data,"").toLowerCase();
        to_code = sharedPreferences.getString(to_data,"").toLowerCase();




        car_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        car_from.setText(" "+name);
                        from_code = name.toLowerCase();
                        editor.putString(from_data,name);
                        editor.apply();
                        picker.dismiss();
                    }
                });

                picker.show(getChildFragmentManager(), "COUNTRY_PICKER");
            }
        });
          car_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        car_to.setText(" "+name);
                        to_code = name.toLowerCase();
                        editor.putString(to_data,name);
                        editor.apply();
                        picker.dismiss();
                    }
                });

                picker.show(getChildFragmentManager(), "COUNTRY_PICKER");
            }
        });





        search=(Button) inflated.findViewById(R.id.search_hotels);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                //segmentedProgressBar.playSegment(100000);


               if(TextUtils.isEmpty(first_name.getText().toString())){
                    first_name.setError("empty");
                }else if (TextUtils.isEmpty(last_name.getText().toString()))
                {
                    last_name.setError("empty");
                }else if (TextUtils.isEmpty(phone.getText().toString()))
                {
                    phone.setError("empty");
                }else if (TextUtils.isEmpty(email.getText().toString()))
                {
                    email.setError("empty");
                }else if (TextUtils.isEmpty(notes.getText().toString()))
                {
                    notes.setError("empty");
                }else if (TextUtils.isEmpty(car_to.getText().toString()))
                {
                    car_to.setError("empty");
                } if (TextUtils.isEmpty(car_from.getText().toString()))
                {
                    car_from.setError("empty");
                }else {
                    sendrequest();

                }




                /*Intent intent = new Intent(getContext(), WebViewInvoice.class);
                intent.putExtra("check_type","ivisa");
                intent.putExtra("from_code",from_code.toLowerCase());
                intent.putExtra("to_code",to_code.toLowerCase());
                startActivity(intent);*/

            }
        });


        return inflated;

    }

    public void OnClickHotelSelect()
    {

        Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,3);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }




    public void sendrequest()
    {

        builder = new AlertDialog.Builder(getContext());
        layoutInflaterAndroid = LayoutInflater.from(getContext());
        view2 = layoutInflaterAndroid.inflate(R.layout.progress_dialog, null);
        builder.setView(view2);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();




        segmentedProgressBar = (SegmentedProgressBar) view2.findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(50000);

       String url = Constant.domain_name+"Ivisa/booking?appKey="+Constant.key;


            mRequestQueue = Volley.newRequestQueue(getContext());



            mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    segmentedProgressBar.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);

                    try {
                        JSONObject JSONObject_main =  new JSONObject(response);
                        Boolean status=JSONObject_main.getBoolean("status");
                        if(status.equals(true))
                        {


                            beforedone.setVisibility(View.GONE);
                            donee.setVisibility(View.VISIBLE);

                           // Toast.makeText(getContext(),""+JSONObject_main.getString("msg"),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(),""+JSONObject_main.getString("msg"),Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }



                        alertDialog.dismiss();
                    }

                    catch (JSONException e) {
                        Log.d("bordingpoint2", e.getMessage());
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        // dialog.dismiss();
                       // finish();
                        alertDialog.dismiss();

                    }


                    //  Toast.makeText(getApplicationContext(),response+"",Toast.LENGTH_LONG).show();


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


                   /* Log.d("bordingpointtest",logText);
                    Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                    finish();*/
                    //This code is executed if there is an error.
                }
            }) {
                @Override
                protected java.util.Map<String, String> getParams()  {



                    //String name_from,name_to;

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("first_name",first_name.getText().toString());
                    params.put("last_name",last_name.getText().toString());
                    params.put("phone",phone.getText().toString());
                    params.put("email",email.getText().toString());
                    params.put("from_country",from_code.toLowerCase());
                    params.put("to_country",to_code.toLowerCase());
                    params.put("notes",notes.getText().toString());
                    params.put("date","");



                    return params;
                }
            };


            mRequestQueue = Volley.newRequestQueue(getContext());
            mStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            mRequestQueue.add(mStringRequest);


        }





}
