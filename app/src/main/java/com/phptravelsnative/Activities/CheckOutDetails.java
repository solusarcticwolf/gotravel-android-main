package com.phptravelsnative.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.phptravelsnative.Adapters.PassengerAdapter;
import com.phptravelsnative.Adapters.Tp_CheckOut_adapters;
import com.phptravelsnative.Adapters.Tp_Details_adapter;
import com.phptravelsnative.Adapters.Tp_DoubleListing_adapter;
import com.phptravelsnative.Adapters.Tp_Listing_adapter;
import com.phptravelsnative.Models.PassengerModel;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortDetails;
import com.phptravelsnative.Network.Post.Tp_CheckoutRequest;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Views.SingleTonRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckOutDetails extends Drawer {

    RelativeLayout detials_layout;
    LinearLayout show_detials;
    TravelPort cabin_class = new TravelPort();
    RelativeLayout hide_view;
    RecyclerView passenger;
    String from_key = "";
    String to_key = "";
    String tp_detials = "";
    String searchPassenger = "";
    ImageView company_icon;
    TextView company_name;
    TextView base_rate;
    TextView tax_rate;
    TextView total_rate;
    LinearLayout return_view;
    RecyclerView inbound_recycleview;
    RecyclerView outbound_recycleview;
    RelativeLayout main_layout;
    ProgressDialog dialog;

    String travelportCheckoutResp = "";
    String travelportCartResp = "";
            Button continue_booking;
    ArrayList<PassengerModel> pass = new ArrayList<>();
    ArrayList<TravelPortDetails> tp_detials_arr = new ArrayList<>();
    ArrayList<TravelPortDetails> tp_inbounds_arr = new ArrayList<>();
    ArrayList<PassengerModel> pass_book = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.checkout_detials);
        View inflated = stub.inflate();
        Intent intent = getIntent();

        passenger = (RecyclerView) inflated.findViewById(R.id.passenger);
        continue_booking = (Button) inflated.findViewById(R.id.continue_booking);
        company_icon = (ImageView)inflated.findViewById(R.id.company_icon);
        company_name = (TextView) inflated.findViewById(R.id.company_name);
        main_layout = (RelativeLayout) inflated.findViewById(R.id.main_layout);

        base_rate = (TextView) inflated.findViewById(R.id.base_rate);
        tax_rate = (TextView) inflated.findViewById(R.id.tax_rate);
        total_rate = (TextView) inflated.findViewById(R.id.total_rate);
        return_view = inflated.findViewById(R.id.return_view);

        cabin_class = intent.getExtras().getParcelable("cabin_date");
        from_key = intent.getExtras().getString("from_keys");
        to_key = intent.getExtras().getString("to_keys");
        tp_detials = intent.getExtras().getString("tp_detials");
        searchPassenger = intent.getExtras().getString("searchPassenger");

        outbound_recycleview = (RecyclerView)inflated.findViewById(R.id.departure_array);
        inbound_recycleview= (RecyclerView)inflated.findViewById(R.id.return_array);
        dialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.loading));

        passenger.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // Stop only scrolling.
                return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
            }
        });
        int ab = 0;

        ab = Integer.parseInt(cabin_class.getAdults());

        for(int i =0;i<ab;i++)
        {
            PassengerModel pm = new PassengerModel();
            pm.setTitle((i+1)+"-Adults");
            pass.add(pm);

        }

        ab = Integer.parseInt(cabin_class.getChild());
        for(int i =0;i<ab;i++)
        {
            PassengerModel pm = new PassengerModel();
            pm.setTitle((i+1)+"-Childs");
            pass.add(pm);


        }
        ab = Integer.parseInt(cabin_class.getInfants());
        for(int i =0;i<ab;i++)
        {
            PassengerModel pm = new PassengerModel();
            pm.setTitle((i+1)+"-Infants");
            pass.add(pm);


        }

        dialog.show();
        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    main_layout.setVisibility(View.VISIBLE);

                    boolean checkInbonds = false;
                    JSONObject main_json = new JSONObject(response);
                    if(main_json.getString("status").equals("success")){

                        JSONObject dataObject = main_json.getJSONObject("data");
                        JSONObject outBoundObject = dataObject.getJSONObject("outbound");

                        travelportCheckoutResp =  main_json.getJSONObject("travelportCheckoutResp").toString();
                        travelportCartResp =  main_json.getJSONObject("travelportCartResp").toString();

                        base_rate.setText(dataObject.getJSONObject("airPricingSolution").getString("BasePrice"));
                        tax_rate.setText(dataObject.getJSONObject("airPricingSolution").getString("TotalPrice"));
                        total_rate.setText(dataObject.getJSONObject("airPricingSolution").getString("Taxes"));

                        JSONArray segmentOB = outBoundObject.getJSONArray("segment");

                        TravelPortDetails travelPortDetails = new TravelPortDetails();

                        if (segmentOB.length() != 0){

                            for(int i =0;i<segmentOB.length();i++) {

                                JSONObject segmentIndex = segmentOB.getJSONObject(i);
                                travelPortDetails = new TravelPortDetails();
                                company_name.setText(segmentIndex.getJSONObject("detail").getJSONObject("carrier").getString("shortname"));

                                Picasso.with(getBaseContext())
                                        .load(segmentIndex.getJSONObject("detail").getJSONObject("carrier").getString("image_path"))
                                        .error(R.drawable.ic_no_image)
                                        .resize(120,60)
                                        .into(company_icon,  new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                            }
                                        });

                                travelPortDetails.setLocation_to(segmentIndex.getJSONObject("FlightDetails").getString("Origin"));
                                travelPortDetails.setLocation_from(segmentIndex.getJSONObject("FlightDetails").getString("Destination"));
                                travelPortDetails.setTime_from(segmentIndex.getJSONObject("FlightDetails").getString("DepartureTime"));
                                travelPortDetails.setTime_to(segmentIndex.getJSONObject("FlightDetails").getString("ArrivalTime"));
                                travelPortDetails.setFlight_type(segmentIndex.getJSONObject("detail").getJSONObject("equipment").getString("code"));
                                travelPortDetails.setDate_from(segmentIndex.getJSONObject("detail").getJSONObject("bookingInfo").getString("CabinClass"));
                                travelPortDetails.setDate_to(segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("day")+"D "
                                +segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("hour")+"H "
                                +segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("minute")+"M "
                                +segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("second")+"S ");
                                tp_detials_arr.add(travelPortDetails);
                            }


                        }

                        if (dataObject.getJSONObject("inbound").getJSONArray("segment").length()!=0) {

                            checkInbonds = true;
                            return_view.setVisibility(View.VISIBLE);

                            JSONObject inboundDataObject = dataObject.getJSONObject("inbound");
                            JSONArray segmentOBInbound = inboundDataObject.getJSONArray("segment");

                            TravelPortDetails tp_inbounds = new TravelPortDetails();

                            if (segmentOBInbound.length() != 0){

                                for(int i =0;i<segmentOBInbound.length();i++) {

                                    JSONObject segmentIndex = segmentOBInbound.getJSONObject(i);
                                    tp_inbounds = new TravelPortDetails();

                                    tp_inbounds.setLocation_to(segmentIndex.getJSONObject("FlightDetails").getString("Origin"));
                                    tp_inbounds.setLocation_from(segmentIndex.getJSONObject("FlightDetails").getString("Destination"));
                                    tp_inbounds.setTime_from(segmentIndex.getJSONObject("FlightDetails").getString("DepartureTime"));
                                    tp_inbounds.setTime_to(segmentIndex.getJSONObject("FlightDetails").getString("ArrivalTime"));
                                    tp_inbounds.setFlight_type(segmentIndex.getJSONObject("detail").getJSONObject("equipment").getString("code"));
                                    tp_inbounds.setDate_from(segmentIndex.getJSONObject("detail").getJSONObject("bookingInfo").getString("CabinClass"));
                                    tp_inbounds.setDate_to(segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("day")+"D "
                                            +segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("hour")+"H "
                                            +segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("minute")+"M "
                                            +segmentIndex.getJSONObject("detail").getJSONObject("totalDuration").getString("second")+"S ");
                                    tp_inbounds_arr.add(tp_inbounds);
                                }
                            }
                        }

                        if (checkInbonds) {
                            Tp_CheckOut_adapters adapter = new Tp_CheckOut_adapters(CheckOutDetails.this, tp_inbounds_arr);
                            inbound_recycleview.setAdapter(adapter);
                            inbound_recycleview.setLayoutManager(new LinearLayoutManager(CheckOutDetails.this));
                        }
                            Tp_CheckOut_adapters adapter = new Tp_CheckOut_adapters(CheckOutDetails.this, tp_detials_arr);
                            outbound_recycleview.setAdapter(adapter);
                            outbound_recycleview.setLayoutManager(new LinearLayoutManager(CheckOutDetails.this));

                        dialog.dismiss();


                    }else {

                        Toast.makeText(getBaseContext(), main_json.getString("message"), Toast.LENGTH_LONG).show();
                        finish();
                        dialog.dismiss();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

            }
        };


      Response.ErrorListener listener1 =new  Response.ErrorListener() {
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
                dialog.dismiss();
                finish();
            }
        };

        Tp_CheckoutRequest requestRegister=new Tp_CheckoutRequest(from_key,to_key,tp_detials,searchPassenger,listener,listener1);
        requestRegister.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = SingleTonRequest.getmInctance(this).getRequestQueue();
        requestQueue.add(requestRegister);



        PassengerAdapter adapter = new PassengerAdapter(this,pass,getSupportFragmentManager());
        passenger.setAdapter(adapter);
        passenger.setLayoutManager(new LinearLayoutManager(this));


        continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkEmpty = true;
                pass_book.clear();
                for(int i = 0;i<pass.size();i++){

                    PassengerAdapter.MyViewHolder vs = (PassengerAdapter.MyViewHolder)passenger.getChildViewHolder(passenger.getChildAt(i));

                    if((vs.mr.getText().toString().equals(""))){

                        vs.mr.setError("Please Fill It");
                        checkEmpty = false;

                    }
                    if((vs.input_email.getText().toString().equals(""))){

                        vs.input_email.setError("Please Fill It");
                        checkEmpty = false;


                    }
                    if((vs.input_name.getText().toString().equals(""))){

                        vs.input_name.setError("Please Fill It");
                        checkEmpty = false;


                    }
                    if((vs.input_phone.getText().toString().equals(""))){

                        vs.input_phone.setError("Please Fill It");
                        checkEmpty = false;


                    }
                    if((vs.first_name.getText().toString().equals(""))){

                        vs.first_name.setError("Please Fill It");
                        checkEmpty = false;


                    }
                    if((vs.nationality.getText().toString().equals(""))){

                        vs.nationality.setError("Please Fill It");
                        checkEmpty = false;
                    }
                }

                if (checkEmpty){

                    for(int i = 0;i<pass.size();i++)
                    {
                        PassengerAdapter.MyViewHolder vs = (PassengerAdapter.MyViewHolder)passenger.getChildViewHolder(passenger.getChildAt(i));

                        PassengerModel pm = new PassengerModel();
                        pm.setFirst_name(vs.first_name.getText().toString());
                        pm.setInput_name(vs.input_name.getText().toString());
                        pm.setInput_email(vs.input_email.getText().toString());
                        pm.setNationality(vs.nationality.getText().toString());
                        pm.setInput_phone(vs.input_phone.getText().toString());
                        pm.setMr(vs.mr.getText().toString());

                        if (vs.title.getText().toString().toLowerCase().contains("adult")) {

                                pm.setCdn("ADT");

                        }
                        else if (vs.title.getText().toString().toLowerCase().contains("child")) {

                                 pm.setCdn("CNN");

                        }else if (vs.title.getText().toString().toLowerCase().contains("infant")) {

                                pm.setCdn("INF");

                        }

                        pass_book.add(pm);

                        Intent card_intent = new Intent(CheckOutDetails.this,Tp_Card.class);
                        Bundle b = new Bundle();
                        b.putParcelableArrayList("passenger_data",pass_book);
                        b.putString("travelportCheckoutResp",travelportCheckoutResp);
                        b.putString("travelportCartResp",travelportCartResp);
                        card_intent.putExtras(b);
                        startActivity(card_intent);

                    }

                }




            }
        });

        detials_layout = (RelativeLayout) inflated.findViewById(R.id.detials_layout);
        show_detials = (LinearLayout) inflated.findViewById(R.id.show_detials);

        detials_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(show_detials.getVisibility() == View.GONE) {
                    show_detials.setVisibility(View.VISIBLE);
                }else {
                    show_detials.setVisibility(View.GONE);
                }


            }
        });

    }



}
