package com.phptravelsnative.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phptravelsnative.Models.Adults_model;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.Datafligh;
import com.phptravelsnative.Models.Flights;
import com.phptravelsnative.Models.Model_Room;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.Models.Taxi_model;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.flight_data;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Extra.Tools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.phptravelsnative.utality.Extra.Constant.domain_name;


public class PaymentCardDetails extends AppCompatActivity {




    ArrayList<Datafligh> Flight_detailsdata = new ArrayList<>();

    CardView namehotel ;
    ArrayList<RouteItem> routeItem_ArrayList =  new ArrayList<>();
    ArrayList<flight_data> flight_data_modelArrayList =  new ArrayList<>();
    ArrayList<Adults_model> adults_modelArrayList =  new ArrayList<>();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    private TextView card_number;
    private TextView card_expire;
    private TextView card_cvv;
    private TextView card_name;

    private TextInputEditText et_card_number;
    private TextInputEditText et_expire;
    private TextInputEditText et_cvv;
    private TextInputEditText et_name;


    Button continue_booking;
    private ProgressBar progress_bar;
    Gson gson;
    DataItem dataItem;
    Taxi_model taxi_model;
    String Datatype;
    Model_Room model_room;


    String strinf_adults ;



    TravelPort cabin_class = new TravelPort();
    LinearLayout roomhotelview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card_details);

        gson = new Gson();
         Datatype = getIntent().getStringExtra("Datatype");
         strinf_adults = getIntent().getStringExtra("userdata");
        if (Datatype.contentEquals("taxi_model"))
        {

            taxi_model = gson.fromJson(getIntent().getStringExtra("taxi_model"), Taxi_model.class);
            // Toast.makeText(getApplicationContext(),"taxi",Toast.LENGTH_LONG).show();
            intfin_taxi();
        }else if(Datatype.contentEquals("flight_model"))
        {
            // Toast.makeText(getApplicationContext(),"flight_model",Toast.LENGTH_LONG).show();
            cabin_class = getIntent().getExtras().getParcelable("cabin_date");
            dataItem = gson.fromJson(getIntent().getStringExtra("dataItem"), DataItem.class);
            init();
        }
        else if(Datatype.contentEquals("travelshope_hotel"))
        {

            model_room =  gson.fromJson(getIntent().getStringExtra("dataItem"), Model_Room.class);
             //Toast.makeText(getApplicationContext(),"travelshope_hotel",Toast.LENGTH_LONG).show();
            intfin_travelhope_hotel(model_room);
        }




    }
    private void intfin_travelhope_hotel(Model_Room model_room) {





        roomdataload(model_room);
        progress_bar = findViewById(R.id.progress_bar);

        continue_booking = findViewById(R.id.continue_booking);
        card_number = (TextView) findViewById(R.id.card_number);
        card_expire = (TextView) findViewById(R.id.card_expire);
        card_cvv = (TextView) findViewById(R.id.card_cvv);
        card_name = (TextView) findViewById(R.id.card_name);

        et_card_number = (TextInputEditText) findViewById(R.id.et_card_number);
        et_expire = (TextInputEditText) findViewById(R.id.et_expire);
        et_cvv = (TextInputEditText) findViewById(R.id.et_cvv);
        et_name = (TextInputEditText) findViewById(R.id.et_name);

        et_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                } else {
                    String number = Tools.insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/YY");
                } else {
                    String exp = Tools.insertPeriodically(charSequence.toString().trim(), "/", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {
                    card_cvv.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Your Name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String TravelhopehotelsBookingUrl= domain_name+"Travelhopehotels/book?appKey="+Constant.key;
                postrequest_travelhophotelbooking(TravelhopehotelsBookingUrl);

            }
        });
    }

    private void intfin_taxi() {



        progress_bar = findViewById(R.id.progress_bar);

        continue_booking = findViewById(R.id.continue_booking);
        card_number = (TextView) findViewById(R.id.card_number);
        card_expire = (TextView) findViewById(R.id.card_expire);
        card_cvv = (TextView) findViewById(R.id.card_cvv);
        card_name = (TextView) findViewById(R.id.card_name);

        et_card_number = (TextInputEditText) findViewById(R.id.et_card_number);
        et_expire = (TextInputEditText) findViewById(R.id.et_expire);
        et_cvv = (TextInputEditText) findViewById(R.id.et_cvv);
        et_name = (TextInputEditText) findViewById(R.id.et_name);

        et_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                } else {
                    String number = Tools.insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/YY");
                } else {
                    String exp = Tools.insertPeriodically(charSequence.toString().trim(), "/", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {
                    card_cvv.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Your Name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAction();
            }
        });
    }



    private void init() {


        namehotel =findViewById(R.id.namehotel);
        namehotel.setVisibility(View.GONE);

        progress_bar = findViewById(R.id.progress_bar);

        continue_booking = findViewById(R.id.continue_booking);



        card_number = (TextView) findViewById(R.id.card_number);
        card_expire = (TextView) findViewById(R.id.card_expire);
        card_cvv = (TextView) findViewById(R.id.card_cvv);
        card_name = (TextView) findViewById(R.id.card_name);

        et_card_number = (TextInputEditText) findViewById(R.id.et_card_number);
        et_expire = (TextInputEditText) findViewById(R.id.et_expire);
        et_cvv = (TextInputEditText) findViewById(R.id.et_cvv);
        et_name = (TextInputEditText) findViewById(R.id.et_name);

        et_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                } else {
                    String number = Tools.insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/YY");
                } else {
                    String exp = Tools.insertPeriodically(charSequence.toString().trim(), "/", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {
                    card_cvv.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Your Name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        continue_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                et_card_number = (TextInputEditText) findViewById(R.id.et_card_number);
                et_expire = (TextInputEditText) findViewById(R.id.et_expire);
                et_cvv = (TextInputEditText) findViewById(R.id.et_cvv);
                et_name = (TextInputEditText) findViewById(R.id.et_name);


                String  et_card_number_test = String.valueOf(et_card_number.getText());
                String  et_expire_test = String.valueOf(et_expire.getText());
                String  et_cvv_test = String.valueOf(et_cvv.getText());
                String  uet_name_test = String.valueOf(et_name.getText());





                if (et_card_number_test.isEmpty() ) {

                    Toast.makeText(getApplicationContext(),"card_number isEmpty",Toast.LENGTH_LONG).show();
                } else if (et_expire_test.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"expire isEmpty",Toast.LENGTH_LONG).show();
                }
                else if (et_cvv_test.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"cvv isEmpty",Toast.LENGTH_LONG).show();
                }
                else if (uet_name_test.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"name  isEmpty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    submitAction();

                }

                //submitAction();









            }
        });
    }


    private void submitAction() {
        progress_bar.setVisibility(View.VISIBLE);
        continue_booking.setAlpha(0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialogPaymentSuccess();
                //progress_bar.setVisibility(View.GONE);
                //continue_booking.setAlpha(1f);
            }
        }, 2000);
    }




    private void showDialogPaymentSuccess() {


        if (Datatype.contentEquals("taxi_model"))
        {

           // postrequest_taxi("https://www.phptravels.net/demo/api/travelhope_flights/booking?appKey=phptravels");
        }else if(Datatype.contentEquals("flight_model"))
        {
            travelshope_flightdeatil();
            //postrequest_flight(domain_name+"travelhope_flights/booking?appKey="+Constant.key);
        }


        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.successfullpaymentdialogbox, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        ((FloatingActionButton) dialogView.findViewById(R.id.fab_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });*/


    }




    public void postrequest_taxi(String url)
    {
        // dialog.show();

        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("bordingpointtest","response/" +response);





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


                Log.d("bordingpointtest",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                //finish();
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {



                Map<String, String> params = new HashMap<String, String>();
                params.put("transfer_id","369383");
                params.put("voucher","0");





                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);


    }

    public JSONArray adultsjson()
    {
        JSONArray list = null;
        Adults_model adults_model = new Adults_model("Mr","test","test","usama@gmail.com","1234567891","1990-01-01","2020-01-01","12345678912","AF","1","adults");
        adults_modelArrayList.add(adults_model);
        String element = gson.toJson(adults_modelArrayList, new TypeToken<ArrayList<Adults_model>>() {}.getType());
        try {
            list = new JSONArray(element);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }






    public void postrequest_travelhophotelbooking(String url)
    {
        // dialog.show();
        progress_bar.setVisibility(View.VISIBLE);
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject  jsonObject = new JSONObject(response);
                    String weblink = jsonObject.getString("response");

                    Toast.makeText(getBaseContext(),"successfull", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), Webview.class);
                    intent1.putExtra("weburl",weblink);
                    startActivity(intent1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                progress_bar.setVisibility(View.GONE);


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


                Log.d("bordingpointtest",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                //finish();
                //This code is executed if there is an error.
                progress_bar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {




                Map<String, String> params = new HashMap<String, String>();
                params.put("title","Ms");
                params.put("first_name","johnsmith");
                params.put("last_name","smith");
                params.put("email","admin@shop.com");
                params.put("mobile_code","+92");
                params.put("number","123456789");
                params.put("name_card","Visa");
                params.put("card_no","424242424242424");
                params.put("month","04");
                params.put("year","2021");
                params.put("security_code","123");
                params.put("hotel_id",model_room.getHotel_id());
                params.put("hotel_name",model_room.getHotelname());
                params.put("room_name",model_room.getRoom_name());
                params.put("rating",model_room.getRate());
                params.put("address",model_room.getAddress());
                params.put("mobile_number","0");
                params.put("longitude",model_room.getLongitude());
                params.put("latitude",model_room.getLatitude());
                params.put("checkin",model_room.getCheckin());
                params.put("checkout",model_room.getCehckout());
                params.put("adults",model_room.getAdults());
                params.put("children",model_room.getChilds());
                params.put("room_id",model_room.getId());
                params.put("price",model_room.getPrice());
                params.put("currency","USD");
                params.put("user_id","1");


                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);


    }




    public  void travelshope_flightdeatil()
    {


        String url = domain_name+"travelhope_flights/detail?appKey="+Constant.key;
        progress_bar.setVisibility(View.VISIBLE);
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(getBaseContext(),response, Toast.LENGTH_LONG).show();
                Datafligh datafligh;
                try {
                    JSONObject  jsonObject = new JSONObject(response);

                    JSONObject data = jsonObject.getJSONObject("data");
                     datafligh = new Datafligh();





                    JSONArray flights =  data.getJSONArray("flights");

                    ArrayList<Flights> flightsArrayList_data = new ArrayList<>();

                    for (int i =0;i<flights.length();i++)
                    {

                        Flights flights1 = new Flights();
                        JSONObject jsonObject1_flight = flights.getJSONObject(i);


                        flights1.setFlight_id(jsonObject1_flight.getString("flight_id"));
                        flights1.setFlight_no(jsonObject1_flight.getString("flight_no"));
                        flights1.setFrom_country(jsonObject1_flight.getString("from_country"));
                        flights1.setFrom_code(jsonObject1_flight.getString("from_code"));
                        flights1.setFrom_city(jsonObject1_flight.getString("from_city"));
                        flights1.setFrom_station(jsonObject1_flight.getString("from_station"));
                        flights1.setTo_city(jsonObject1_flight.getString("to_city"));
                        flights1.setTo_code(jsonObject1_flight.getString("to_code"));
                        flights1.setTo_country(jsonObject1_flight.getString("to_country"));
                        flights1.setTo_station(jsonObject1_flight.getString("to_station"));
                        flights1.setArrival_time(jsonObject1_flight.getString("arrival_time"));
                        flights1.setDeparture_time(jsonObject1_flight.getString("departure_time"));
                        flights1.setArrival_utc_time(jsonObject1_flight.getString("arrival_utc_time"));
                        flights1.setDeparture_utc_time(jsonObject1_flight.getString("departure_utc_time"));


                        JSONObject operating_airline = jsonObject1_flight.getJSONObject("operating_airline");
                        flights1.setIata(operating_airline.getString("iata"));
                        flights1.setName(operating_airline.getString("name"));
                        flightsArrayList_data.add(flights1);




                    }


                    datafligh.setFlightsarrayLis(flightsArrayList_data);


                    //Toast.makeText(getBaseContext(),Flight_detailsdata.size()+" = /", Toast.LENGTH_LONG).show();
                    /// set custom_payload
                    JSONObject custom_payload = data.getJSONObject("custom_payload");
                    datafligh.setBooking_token(custom_payload.getString("booking_token"));
                    datafligh.setVisitor_uniqid(custom_payload.getString("visitor_uniqid"));


                    datafligh.setCurrency(data.getString("currency"));
                    datafligh.setTotal(data.getString("total"));
                    datafligh.setBook_fee(data.getString("book_fee"));
                    datafligh.setMode(data.getString("mode"));



                    JSONArray route = data.getJSONArray("route");



                    datafligh.setFrom(route.getString(0));
                    datafligh.setTo(route.getString(1));
                    Flight_detailsdata.add(datafligh);


                    //String dddd = gson.toJson(Flight_detailsdata);
                    //Log.d("daad" ,Flight_detailsdata.size()+"/// ===  "+dddd);


                    postrequest_flight(domain_name+"travelhope_flights/booking?appKey="+Constant.key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                progress_bar.setVisibility(View.GONE);


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


                Log.d("bordingpointtest",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                //finish();
                //This code is executed if there is an error.
                progress_bar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {


                String numbearaults =   dataItem.getAdults().replaceAll("\\D+","");
                String numbehildren =   dataItem.getChildren().replaceAll("\\D+","");
                String numberaInfants =   dataItem.getInfants().replaceAll("\\D+","");

                Map<String, String> params = new HashMap<String, String>();
                params.put("flight_id",dataItem.getFlight_id());
                params.put("booking_token", dataItem.getCustomPayload().getBookingToken());
                params.put("visitor_uniqeid",dataItem.getCustomPayload().getVisitorUniqid());
                params.put("adults",numbearaults);
                params.put("children",numbehildren);
                params.put("infants",numberaInfants);
                params.put("ota_id","");
                params.put("vendor","5");



                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);




    }



    public void postrequest_flight(String url)
    {
        // dialog.show();

        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject =  new JSONObject(response);

                    String booking_id = jsonObject.getString("booking_id");
                    Toast.makeText(getApplicationContext(),booking_id+"",Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // Log.d("bordingpointtest","response/" +response);





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


                Log.d("bordingpointtest",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                //finish();
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {



                Map<String, String> params = new HashMap<String, String>();
                JSONObject custom_payload = new JSONObject();
                try {
                    custom_payload.put("booking_token", Flight_detailsdata.get(0).getBooking_token());
                    custom_payload.put("visitor_uniqid", Flight_detailsdata.get(0).getVisitor_uniqid());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }






                routeItem_ArrayList=dataItem.getRoutes();

                params.put("custom_payload",""+custom_payload);

                params.put("flight_id",dataItem.getFlight_id());

                params.put("adults", strinf_adults);
                params.put("children","{}");
                params.put("infants", "{}");
                params.put("special_request","");
                params.put("operating_airline_iata",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getIata());
                params.put("operating_airline_name",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getName());
                params.put("from_city",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getFrom_city());
                params.put("from_code",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getFrom_code());
                params.put("to_code",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getTo_code());
                params.put("departure_time",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getDeparture_time());
                params.put("to_city",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getTo_city());
                params.put("arrival_time",Flight_detailsdata.get(0).getFlightsarrayLis().get(1).getArrival_time());
                params.put("price",Flight_detailsdata.get(0).getTotal());
                params.put("flight_id",Flight_detailsdata.get(0).getFlightsarrayLis().get(1).getFlight_id());
                params.put("timestamp",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getDeparture_time());
                params.put("to_country",Flight_detailsdata.get(0).getFlightsarrayLis().get(1).getTo_country());
                params.put("from_country",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getFrom_country());
                params.put("to_station",Flight_detailsdata.get(0).getFlightsarrayLis().get(1).getFrom_station());
                params.put("from_station",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getFrom_station());
                params.put("flight_no",Flight_detailsdata.get(0).getFlightsarrayLis().get(0).getFlight_no());

                String expir =et_expire.getText().toString();
                params.put("name_card",""+et_name.getText());
                params.put("card_no",""+et_card_number.getText());
                params.put("month",expir.charAt(0) +expir.charAt(1)+"");
                params.put("year",expir.charAt(2) +expir.charAt(3)+"");
                params.put("security_code",""+et_cvv.getText());


               /* params.put("name_card","VI");
                params.put("card_no","4242424242424242");
                params.put("month","04");
                params.put("year","2023");
                params.put("security_code","123");
                params.put("name","john");
                params.put("surname","smith");
                params.put("phone","0000-000-000");
                params.put("voucher","0");

*/
                String g = gson.toJson(params);


                g=g.replaceAll("'","'");
                Log.d("ggggggg",g);
                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);


    }


    public void roomdataload( Model_Room model_room)
    {
        roomhotelview = findViewById(R.id.roomhotelview);
        roomhotelview.setVisibility(View.VISIBLE);
        TextView title;
        RobotoTextView price,type,chld,booking;
        ImageView image;
        title =  findViewById(R.id.title);
        price=   findViewById(R.id.price);
        type=    findViewById(R.id.type);
        image=   findViewById(R.id.image);
        chld=    findViewById(R.id.chld);

        String test = model_room.getRoom_name();
        String s;
        if ( test.length() >= 27)
        {
            s=test.substring(0,25);
            title.setText(s+"...");
        }else
        {
            s=test;
            title.setText(s);
        }

        price.setText("USD "+model_room.getPrice());
        type.setText(model_room.getType());
        chld.setText("Adults : "+model_room.getAdults()+ " childs : "+model_room.getChilds() );



        Picasso.with(getApplicationContext()).load(model_room.getImage().get(0))
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(image,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
    }


}


