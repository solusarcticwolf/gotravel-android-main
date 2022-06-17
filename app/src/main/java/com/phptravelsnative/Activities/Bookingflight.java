package com.phptravelsnative.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.phptravelsnative.Adapters.Manual_Flight_Detials_Adapter;
import com.phptravelsnative.Adapters.Manual_Flight_Detials_Adapter_Travelhope;
import com.phptravelsnative.Models.Adults_model;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.Models.Taxi_model;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Network.Post.Flight_Booking;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Views.SingleTonRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import pl.kitek.timertextview.TimerTextView;

public class Bookingflight extends AppCompatActivity {



    int hour;
    int minute;
    Hotel_data hotel_data;
    ComplexPreferences sharedPreferences;
    Auto_Model searchHotel=new Auto_Model();
    Auto_Model to_travel=new Auto_Model();
    String MyPREFERENCES = "MyPrefs";
    Context context=this;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RelativeLayout stopee ;
    Gson gson;
    DataItem dataItem;
    RobotoTextView  airline,fromto,price,arrival_time,departure_time,ticketprice,arrival_date,departure_date;
    ImageView roundedImageView ,image2,imagedrop;
    Context mContext=this;
    RecyclerView recyclerView;
    ArrayList<RouteItem> onew_way = new ArrayList<>();
    Boolean showstoplist=false;
    LinearLayout timepicker, showlist,datepicker,nationalitypicker;
    RobotoTextView buytickets ;
    RobotoTextView nationalitytext ;
    DatePickerDialog picker;
    RobotoTextView expirationdate;
    RobotoTextView expirationtime;
    MaterialSpinner gender,nationalityspiner;
    MaterialSpinner child,travellers;
    FragmentManager frm;
    Taxi_model taxi_model;
    LinearLayout details_view;
    RobotoEditText usernameWrapper,phoneWrapper,emailWrapper,lastnameWrapper;
    RobotoEditText noteWrapper;
    ProgressBar progressBar2;
    String name_from,name_to;
    RobotoTextView to_city_taxi,from_city_taxi,from_city_taxi_tv,to_city_taxi_tv;
    RobotoTextView pricee_taxi ,pricee_taxi_tv,taxi_name_tv;
    Flight_Booking flight_booking_request;

    String Gender="Male";


    TravelPort cabin_class = new TravelPort();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        gson = new Gson();
        String Datatype = getIntent().getStringExtra("Datatype");
        if (Datatype.contentEquals("taxi_model"))
        {
            // Set taxi_model details
            setContentView(R.layout.activity_booking_taxi);

            taxi_model = gson.fromJson(getIntent().getStringExtra("taxi_model"), Taxi_model.class);
            // Toast.makeText(getApplicationContext(),"taxi",Toast.LENGTH_LONG).show();
            intfin_taxi();

        }else if(Datatype.contentEquals("travelhope"))
        {

            setContentView(R.layout.activity_bookingflight);
            dataItem = gson.fromJson(getIntent().getStringExtra("dataItem"), DataItem.class);
            cabin_class = getIntent().getExtras().getParcelable("cabin_date");
            intfin();

        }else if(Datatype.contentEquals("flight"))
        {
            setContentView(R.layout.activity_bookingflight);
            ManualFlightModel  manualFlightModel = gson.fromJson(getIntent().getStringExtra("dataItem"), ManualFlightModel.class);

           // Toast.makeText(getApplicationContext(),"flight",Toast.LENGTH_LONG).show();
            intfin_flight(manualFlightModel);


        }
        else if(Datatype.contentEquals("Tarco"))
        {

            setContentView(R.layout.activity_bookingflight);
            Toast.makeText(getApplicationContext(),"Tarco",Toast.LENGTH_LONG).show();
            ///intfin_flight(manualFlightModel);


        }







    }

    public void intfin_flight(final ManualFlightModel manualFlightModel)
    {



        roundedImageView = findViewById(R.id.image);

        recyclerView = findViewById(R.id.recyclerView);
        stopee =  findViewById(R.id.stopee);
        showlist =  findViewById(R.id.showlist);
        buytickets=  findViewById(R.id.buytickets);



        image2= findViewById(R.id.image2);
        expirationdate = findViewById(R.id.expirationdate);
        datepicker = findViewById(R.id.datepicker);

        airline = findViewById(R.id.airline);
        fromto  = findViewById(R.id.fromto);
        ticketprice = findViewById(R.id.ticketprice);
        price   = findViewById(R.id.price);
        arrival_time = findViewById(R.id.arrival_time);
        departure_time = findViewById(R.id.departure_time);
        arrival_date = findViewById(R.id.arrival_date);
        departure_date= findViewById(R.id.departure_date);
        nationalitytext= findViewById(R.id.nationalitytext);

        progressBar2 = findViewById(R.id.progressBar2);


       ticketprice.setText(manualFlightModel.getPrice());
        departure_date.setText(manualFlightModel.getModels_array().get(0).getDate());
        arrival_date.setText(manualFlightModel.getModels_array().get(0).getTo_date());
        departure_time.setText(manualFlightModel.getModels_array().get(0).getTime());
        arrival_time.setText(manualFlightModel.getModels_array().get(0).getTo_time());
        airline.setText("Flight "+manualFlightModel.getAero_name());

        fromto.setText(manualFlightModel.getModels_array().get(0).getCode()+" — " +manualFlightModel.getModels_array().get(0).getTo_code());
        price.setText(manualFlightModel.getPrice()+" "+manualFlightModel.getCurrCode());
        final String linkimage= manualFlightModel.getModels_array().get(0).getImg();


        Picasso.with(mContext).load(linkimage)
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(roundedImageView,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });





        stopee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showstoplist==true)
                {
                    showlist.setVisibility(View.GONE);
                    showstoplist=false;//downbutton
                    image2.setBackgroundResource(R.drawable.rightside);
                }else
                {
                    showlist.setVisibility(View.VISIBLE);
                    image2.setBackgroundResource(R.drawable.downbutton);
                    showstoplist=true;
                }
            }
        });





        buytickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buytickets.setVisibility(View.GONE);
                RobotoEditText usernameWrapper1 =findViewById(R.id.usernameWrapper);
                RobotoEditText phoneWrapper1 = findViewById(R.id.phoneWrapper);
                RobotoEditText  emailWrapper1 = findViewById(R.id.emailWrapper);
                RobotoEditText  lastnameWrapper1 =findViewById(R.id.lastnameWrapper);
                RobotoTextView nationalitytext1 =findViewById(R.id.nationalitytext);
                RobotoTextView  expirationdate1 =findViewById(R.id.expirationdate);
                RobotoEditText  numberPassport1 =findViewById(R.id.numberPassport);


                String usernameWrapper11 = usernameWrapper1.getText().toString();
                String phoneWrapper11 = phoneWrapper1.getText().toString();
                String emailWrapper11 = emailWrapper1.getText().toString();
                String lastnameWrapper11 = lastnameWrapper1.getText().toString();
                String nationalitytext11 = nationalitytext1.getText().toString();
                String expirationdate11 = expirationdate1.getText().toString();
                String numberPassport11 = numberPassport1.getText().toString();


                if(TextUtils.isEmpty(usernameWrapper11)) {
                    usernameWrapper1.setError("empty");
                    return;
                }
                else  if(TextUtils.isEmpty(phoneWrapper11)) {
                    phoneWrapper1.setError("empty");
                    return;
                }
                else  if(TextUtils.isEmpty(emailWrapper11)) {
                    emailWrapper1.setError("empty");
                    return;
                }


                else  if(TextUtils.isEmpty(lastnameWrapper11)) {
                    lastnameWrapper1.setError("empty");
                    return;
                }

                else  if(TextUtils.isEmpty(nationalitytext11)) {
                    nationalitytext1.setError("empty");
                    return;
                }

                else if(TextUtils.isEmpty(expirationdate11)) {
                    expirationdate1.setError("empty");
                    return;
                }
                else if(TextUtils.isEmpty(numberPassport11)) {
                    numberPassport1.setError("empty");
                    return;
                }else
                {
                    progressBar2.setVisibility(View.VISIBLE);
                    Flight_Booking(manualFlightModel);
                }

                // startActivity(new Intent(getApplicationContext(),PaymentCardDetails.class));
            }
        });


      //  buytickets.setVisibility(View.GONE);
        buytickets.setVisibility(View.VISIBLE);
        dataload_flight(manualFlightModel);
       /* new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                buytickets.setVisibility(View.VISIBLE);
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                buytickets.startAnimation(myAnim);
            }
        }, 2000);*/

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(),ManualFlightActivity.class));

            }
        }, 10* 60 * 1000);


        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();

               final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Bookingflight.this, android.R.style.Widget_Material_ActionBar, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                expirationdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                expirationdate.setTextColor(getResources().getColor(R.color.black));
                            }
                        }, year, month, day);

               // picker.s
                picker.show();
            }
        });


/*

        gender =  findViewById(R.id.gender);
        gender.setTextColor(getResources().getColor(R.color.black));
        gender.setItems("Gender","Male", "Female");
        gender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        nationalitypicker=  findViewById(R.id.nationalitypicker);
        nationalitypicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        nationalitytext.setText(name);
                        nationalitytext.setTextColor(getResources().getColor(R.color.black));
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(),"COUNTRY_PICKER");

            }
        });
        nationalitytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        nationalitytext.setText(name);
                        nationalitytext.setTextColor(getResources().getColor(R.color.black));
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(),"COUNTRY_PICKER");

            }
        });
*/





        long futureTimestamp = System.currentTimeMillis() + (10* 60 * 1000);
        TimerTextView timerText = (TimerTextView) findViewById(R.id.timerText);
        timerText.setEndTime(futureTimestamp);

    }





    public void intfin()
    {




        roundedImageView = findViewById(R.id.image);

        recyclerView = findViewById(R.id.recyclerView);
        stopee =  findViewById(R.id.stopee);
        showlist =  findViewById(R.id.showlist);
        buytickets=  findViewById(R.id.buytickets);



        image2= findViewById(R.id.image2);
        expirationdate = findViewById(R.id.expirationdate);
        datepicker = findViewById(R.id.datepicker);

        airline = findViewById(R.id.airline);
        fromto  = findViewById(R.id.fromto);
        ticketprice = findViewById(R.id.ticketprice);
        price   = findViewById(R.id.price);
        arrival_time = findViewById(R.id.arrival_time);
        departure_time = findViewById(R.id.departure_time);
        arrival_date = findViewById(R.id.arrival_date);
        departure_date= findViewById(R.id.departure_date);
        nationalitytext= findViewById(R.id.nationalitytext);
        ticketprice.setText(dataItem.getFlight_price());
        departure_date.setText(formatdate(Integer.parseInt(dataItem.getDeparture_time())));
        arrival_date.setText(formatdate(Integer.parseInt(dataItem.getArrival_time())));
        departure_time.setText(date_formate(Integer.parseInt(dataItem.getDeparture_time())));
        arrival_time.setText(date_formate(Integer.parseInt(dataItem.getArrival_time())));
        airline.setText("Flight "+dataItem.getAirline());

        fromto.setText(dataItem.getTo_code()+" — " +dataItem.getFrom_code());
        price.setText(dataItem.getFlight_price()+" "+dataItem.getCurrency());
        final String linkimage= Constant.linkimage + dataItem.getAirline()+Constant.linkimagetype;
        Picasso.with(mContext).load(linkimage)
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(roundedImageView,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });





        stopee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showstoplist==true)
                {
                    showlist.setVisibility(View.GONE);
                    showstoplist=false;//downbutton
                    image2.setBackgroundResource(R.drawable.rightside);
                }else
                {
                    showlist.setVisibility(View.VISIBLE);
                    image2.setBackgroundResource(R.drawable.downbutton);
                    showstoplist=true;
                }
            }
        });





        buytickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*RobotoEditText usernameWrapper1 =findViewById(R.id.usernameWrapper);
                RobotoEditText phoneWrapper1 = findViewById(R.id.phoneWrapper);
                RobotoEditText  emailWrapper1 = findViewById(R.id.emailWrapper);
                RobotoEditText  lastnameWrapper1 =findViewById(R.id.lastnameWrapper);
                RobotoTextView nationalitytext1 =findViewById(R.id.nationalitytext);
                RobotoTextView  expirationdate1 =findViewById(R.id.expirationdate);
                RobotoEditText  numberPassport1 =findViewById(R.id.numberPassport);


                String usernameWrapper11 = usernameWrapper1.getText().toString();
                String phoneWrapper11 = phoneWrapper1.getText().toString();
                String emailWrapper11 = emailWrapper1.getText().toString();
                String lastnameWrapper11 = lastnameWrapper1.getText().toString();
                String nationalitytext11 = nationalitytext1.getText().toString();
                String expirationdate11 = expirationdate1.getText().toString();
                String numberPassport11 = numberPassport1.getText().toString();


                if(TextUtils.isEmpty(usernameWrapper11)) {
                    usernameWrapper1.setError("empty");
                    return;
                }
                else  if(TextUtils.isEmpty(phoneWrapper11)) {
                    phoneWrapper1.setError("empty");
                    return;
                }
                else  if(TextUtils.isEmpty(emailWrapper11)) {
                    emailWrapper1.setError("empty");
                    return;
                }


                else  if(TextUtils.isEmpty(lastnameWrapper11)) {
                    lastnameWrapper1.setError("empty");
                    return;
                }



                else if(TextUtils.isEmpty(expirationdate11)) {
                    expirationdate1.setError("empty");
                    return;
                }
                else if(TextUtils.isEmpty(numberPassport11)) {
                    numberPassport1.setError("empty");
                    return;
                }else
                {
                    JSONArray adultsjso1=adultsjson();
                    String jsonArray = String.valueOf(adultsjso1);
                    jsonArray = jsonArray.substring(1, jsonArray.length() - 1);
                    String strinf_adults = jsonArray;

                    String myJson = gson.toJson(dataItem);
                    Intent intent=new Intent(Bookingflight.this, PaymentCardDetails.class);
                    intent.putExtra("dataItem", myJson);
                    intent.putExtra("Datatype", "flight_model");
                    intent.putExtra("userdata", strinf_adults);
                    startActivity(intent);
                }*/

                JSONArray adultsjso1=adultsjson();
                String jsonArray = String.valueOf(adultsjso1);
                jsonArray = jsonArray.substring(1, jsonArray.length() - 1);
                String strinf_adults = jsonArray;

                String myJson = gson.toJson(dataItem);
                Intent intent=new Intent(Bookingflight.this, PaymentCardDetails.class);
                intent.putExtra("dataItem", myJson);
                intent.putExtra("Datatype", "flight_model");
                intent.putExtra("cabin_class", cabin_class);
                intent.putExtra("userdata", strinf_adults);
                startActivity(intent);

               // startActivity(new Intent(getApplicationContext(),PaymentCardDetails.class));
            }
        });


        buytickets.setVisibility(View.GONE);

        dataload(dataItem);
        buytickets.setVisibility(View.VISIBLE);
      /*  new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                buytickets.setVisibility(View.VISIBLE);
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                buytickets.startAnimation(myAnim);
            }
        }, 2000);*/

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

           startActivity(new Intent(getApplicationContext(),ManualFlightActivity.class));

            }
        }, 10* 60 * 1000);


        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();

                /*final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Bookingflight.this, android.R.style.Widget_Material_ActionBar, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                expirationdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                expirationdate.setTextColor(getResources().getColor(R.color.black));
                            }
                        }, year, month, day);

               // picker.s
                picker.show();*/
            }
        });



        gender =  findViewById(R.id.gender);
        gender.setTextColor(getResources().getColor(R.color.black));
        gender.setItems("Gender","Male", "Female");
        gender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Gender=item;
            }
        });


        nationalitypicker=  findViewById(R.id.nationalitypicker);
        nationalitypicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        nationalitytext.setText(name);
                        nationalitytext.setTextColor(getResources().getColor(R.color.black));
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(),"COUNTRY_PICKER");

            }
        });
        nationalitytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final CountryPicker picker = CountryPicker.newInstance("Select Country");
                picker.setListener(new CountryPickerListener() {

                    @Override
                    public void onSelectCountry(String name, String code) {
                        nationalitytext.setText(name);
                        nationalitytext.setTextColor(getResources().getColor(R.color.black));
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(),"COUNTRY_PICKER");

            }
        });

        long futureTimestamp = System.currentTimeMillis() + (10* 60 * 1000);
        TimerTextView timerText = (TimerTextView) findViewById(R.id.timerText);
        timerText.setEndTime(futureTimestamp);

    }

    public void intfin_taxi()
    {
        hotel_data = new Hotel_data();
        sharedPreferences =new ComplexPreferences(getApplicationContext(),MyPREFERENCES, Context.MODE_PRIVATE);
        searchHotel=sharedPreferences.getObject("TravelPort_fromtaxi", Auto_Model.class,searchHotel);
        to_travel=sharedPreferences.getObject("TravelPort_totaxi",Auto_Model.class,searchHotel);
        name_from =searchHotel.getName();
        name_to=to_travel.getName();







        //roundedImageView
        progressBar2 =findViewById(R.id.progressBar2);
        roundedImageView = findViewById(R.id.image);
        imagedrop= findViewById(R.id.imagedrop);

        stopee =  findViewById(R.id.stopee);
        showlist =  findViewById(R.id.showlist);
        buytickets=  findViewById(R.id.buytickets);


        stopee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showstoplist==true)
                {
                    showlist.setVisibility(View.GONE);
                    showstoplist=false;//downbutton
                    image2.setBackgroundResource(R.drawable.rightside);
                }else
                {
                    showlist.setVisibility(View.VISIBLE);
                    image2.setBackgroundResource(R.drawable.downbutton);
                    showstoplist=true;
                }
            }
        });




        // inputs of data

        usernameWrapper =findViewById(R.id.usernameWrapper);
        phoneWrapper = findViewById(R.id.phoneWrapper);
        emailWrapper = findViewById(R.id.emailWrapper);
        lastnameWrapper =findViewById(R.id.lastnameWrapper);
        noteWrapper=findViewById(R.id.noteWrapper);

        image2= findViewById(R.id.image2);
        expirationdate = findViewById(R.id.expirationdate);
        datepicker = findViewById(R.id.datepicker);

        timepicker = findViewById(R.id.timepicker);
        expirationtime = findViewById(R.id.expirationtime);



        //top text view
        taxi_name_tv = findViewById(R.id.taxi_name_tv);
        airline = findViewById(R.id.airline);
        fromto  = findViewById(R.id.fromto);
        ticketprice = findViewById(R.id.ticketprice);
        price   = findViewById(R.id.price);
        arrival_time = findViewById(R.id.arrival_time);
        departure_time = findViewById(R.id.departure_time);
        arrival_date = findViewById(R.id.arrival_date);
        departure_date= findViewById(R.id.departure_date);







        //from_city_taxi_tv,to_city_taxi_tv;
        from_city_taxi_tv  = findViewById(R.id.from_city_taxi_tv);
        to_city_taxi_tv  = findViewById(R.id.to_city_taxi_tv);
        from_city_taxi_tv.setText(name_from);
        to_city_taxi_tv.setText(name_to);



        //top text view
        from_city_taxi  = findViewById(R.id.from_city_taxi);
        to_city_taxi  = findViewById(R.id.to_city_taxi);
        from_city_taxi.setText(name_from);
        to_city_taxi.setText(name_to);

        // pricee text view
        pricee_taxi  = findViewById(R.id.pricee_taxi);
        pricee_taxi_tv  = findViewById(R.id.pricee_taxi_tv);
        pricee_taxi.setText(taxi_model.getCurrencycode()+" "+taxi_model.getPrice());
        pricee_taxi_tv.setText(taxi_model.getCurrencycode()+" "+taxi_model.getPrice());


        // pricee & name text data
        ticketprice.setText(taxi_model.getPrice());
        airline.setText("Taxi "+taxi_model.getCarname());
        taxi_name_tv.setText("Taxi "+taxi_model.getCarname());
        price.setText(taxi_model.getPrice()+" "+taxi_model.getCurrencycode());


        // image load
        Picasso.with(mContext).load(taxi_model.getImg())
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(imagedrop,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });
        Picasso.with(mContext).load(taxi_model.getImg())
                .error(R.drawable.ic_no_image)
                .resize(120,60)
                .into(roundedImageView,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                    }
                });

        // clcik  button
        buytickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String  user_name = String.valueOf(usernameWrapper.getText());
                String  user_lastnameWrapper = String.valueOf(lastnameWrapper.getText());
                String  user_phoneWrapper = String.valueOf(phoneWrapper.getText());
                String  user_emailWrapper = String.valueOf(emailWrapper.getText());
                String  user_noteWrapper = String.valueOf(noteWrapper.getText());





              if (user_name.isEmpty() ) {

                  Toast.makeText(getApplicationContext(),"name isEmpty",Toast.LENGTH_LONG).show();
                } else if (user_lastnameWrapper.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"lastname isEmpty",Toast.LENGTH_LONG).show();
                }
                else if (user_phoneWrapper.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"phone isEmpty",Toast.LENGTH_LONG).show();
                }
                else if (user_emailWrapper.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"email isEmpty",Toast.LENGTH_LONG).show();
                }else if (user_noteWrapper.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"note isEmpty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    postrequest_taxi("https://www.bedpay.com/api/kiwitaxi/confirmation?appKey=phptravels");
                }
                //postrequest_taxi("https://www.bedpay.com/api/kiwitaxi/confirmation?appKey=phptravels");



            }
        });

        // buttom animations ;
      //  buytickets.setVisibility(View.GONE);
        buytickets.setVisibility(View.VISIBLE);

        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                buytickets.setVisibility(View.VISIBLE);
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);

                buytickets.startAnimation(myAnim);
            }
        }, 2000);*/

        // activty close after 10 min ;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(),ManualFlightActivity.class));

            }
        }, 10* 60 * 1000);

        // datepicker ;
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();

            }
        });



        // timepicker ;

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timepicker();


            }
        });

        expirationtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker();
            }
        });
        expirationdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();

            }
        });

        // Child drop down;
        child =  findViewById(R.id.child);
        child.setTextColor(getResources().getColor(R.color.black));
        child.setItems("Child","Child 1", "Child 2","Child 3", "Child 4","Child 5");
        child.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        // travellers drop down;
        travellers =  findViewById(R.id.travellers);
        travellers.setTextColor(getResources().getColor(R.color.black));
        travellers.setItems("Travellers"," Travellers 1", "Travellers 2","Travellers 3", " Travellers 4","Travellers 5", "Travellers 6","Travellers 7", "Travellers 8","Travellers 9", "Travellers 10","Travellers 11", "Travellers 12","Travellers 13", "Travellers 14","Travellers 15", "Travellers 16");
        travellers.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                //travellers.setText("Travellers : "+item);
            }
        });



        long futureTimestamp = System.currentTimeMillis() + (10* 60 * 1000);
        TimerTextView timerText = (TimerTextView) findViewById(R.id.timerText);
        timerText.setEndTime(futureTimestamp);

    }



    public String formatdate(int tripBookedTime)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tripBookedTime * 1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);


    }


    public String date_formate(int  tripBookedTime)
    {



        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(tripBookedTime * 1000L);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(" hh:mm");
        return sdf.format(d);



    }


    public void dataload_flight(ManualFlightModel manualFlightModel)
    {

        ArrayList<OneWay> onew_way_flight = new ArrayList<>();

        onew_way_flight = manualFlightModel.getModels_array();



        Manual_Flight_Detials_Adapter adapter = new Manual_Flight_Detials_Adapter(Bookingflight.this,onew_way_flight,"oneway",manualFlightModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Bookingflight.this));

    }
    public void dataload(DataItem dataItem)
    {
        onew_way = dataItem.getRoutes();
        RouteItem routeItem = onew_way.get(0);
        RouteItem routeItem2 = onew_way.get(onew_way.size()-1);
        fromto.setText(routeItem.getCityTo()+" — "+ routeItem2.getCityTo());
        Manual_Flight_Detials_Adapter_Travelhope adapter = new Manual_Flight_Detials_Adapter_Travelhope(Bookingflight.this,onew_way,"oneway",dataItem);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Bookingflight.this));

    }

    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

    @SuppressLint("NewApi")
    public void timepicker ()
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.timepickerrr, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        // Get current calendar date and time.
        Calendar currCalendar = Calendar.getInstance();

        // Set the timezone which you want to display time.
        currCalendar.setTimeZone(TimeZone.getTimeZone("Asia/Hong_Kong"));


        hour = currCalendar.get(Calendar.HOUR_OF_DAY);
        minute = currCalendar.get(Calendar.MINUTE);
        TimePicker picker=(TimePicker) dialogView.findViewById(R.id.datePicker1);
        picker.setHour(this.hour);
        picker.setMinute(this.minute);

        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
             expirationtime.setText(hour + ":" + minute);
            expirationtime.setTextColor(getResources().getColor(R.color.black));
        }
    });


        Button  close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button  done = dialogView.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // expirationdate.setText(day + "/" + (month + 1) + "/" + year);
                //expirationdate.setTextColor(getResources().getColor(R.color.black));
                alertDialog.dismiss();
            }
        });







       /* final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.datepickerrr);
        dialog.show();*/
    }



    public void datepicker ()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.datepickerrr, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        DatePicker picker=(DatePicker) dialogView.findViewById(R.id.datePicker1);
        Button  close = dialogView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        final int day = picker.getDayOfMonth();
        final int month = picker.getMonth() + 1;
        final int year = picker.getYear();
        Button  done = dialogView.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expirationdate.setText(day + "/" + (month + 1) + "/" + year);
                expirationdate.setTextColor(getResources().getColor(R.color.black));
                alertDialog.dismiss();
            }
        });







       /* final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.datepickerrr);
        dialog.show();*/
    }


    public void postrequest_taxi(String url)
    {


        buytickets.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar2.setVisibility(View.GONE);
                buytickets.setVisibility(View.VISIBLE);
                try {
                    JSONObject JSONObject_main =  new JSONObject(response);
                    int status=JSONObject_main.getInt("status");
                    if (status==1)
                    {


                        Toast.makeText(getBaseContext(),"successfull", Toast.LENGTH_LONG).show();
                        String weblink=JSONObject_main.getString("msg");
                        Intent intent1 = new Intent(context, Webview.class);
                        intent1.putExtra("weburl",weblink);
                        startActivity(intent1);
                    }else
                    {
                        String weblink=JSONObject_main.getString("msg");
                        Toast.makeText(getBaseContext(),weblink+"sever issue", Toast.LENGTH_LONG).show();
                    }

                }
                catch (JSONException e) {
                    Log.d("bordingpoint2", e.getMessage());
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    // dialog.dismiss();
                    finish();
                    progressBar2.setVisibility(View.GONE);
                    buytickets.setVisibility(View.VISIBLE);

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


                Log.d("bordingpointtest",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                progressBar2.setVisibility(View.GONE);
                finish();
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {



                //String name_from,name_to;

                Map<String, String> params = new HashMap<String, String>();
                params.put("transfer_id",taxi_model.getTransfer_id());
                params.put("flight_form",name_from);
                params.put("loaction",name_to);

                params.put("first_name","john");
                params.put("last_name","johnjohn");
                params.put("phone","+923066789254");
                params.put("email","john@gmail.com");
                params.put("msg","+923066789254 s");
                params.put("flight_no","SU1234");
                params.put("date","2019-10-28");
                params.put("time","23:30:00");
                params.put("pax",taxi_model.getPax());
                params.put("child","0");
                params.put("taxi_image",taxi_model.getImg());
                params.put("taxi_name",taxi_model.getCarname());
                params.put("paymenttype","partial");
                params.put("amount",taxi_model.getPrice());




                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);


    }




    private void Flight_Booking(final ManualFlightModel manualFlightModel) {


        final String BookingUrl= Constant.domain_name+"flights/invoice?appKey="+Constant.key;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, BookingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("bookingflightdata", response);

                try {
                    JSONObject JSONObject_main =  new JSONObject(response);

                    JSONObject response_object = JSONObject_main.getJSONObject("response");

                    String error = response_object.getString("error");

                    if(error.contains("no"))
                    {
                        Toast.makeText(getBaseContext(),"successfull", Toast.LENGTH_LONG).show();
                        String weblink=response_object.getString("url");
                        Intent intent1 = new Intent(context, Webview.class);
                        intent1.putExtra("weburl",weblink);
                        startActivity(intent1);
                    } else
                    {
                        String weblink=JSONObject_main.getString("msg");
                        Toast.makeText(getBaseContext(),weblink+"sever issue", Toast.LENGTH_LONG).show();
                    }
                    progressBar2.setVisibility(View.GONE);
                    buytickets.setVisibility(View.VISIBLE);
                }
                catch (JSONException e) {
                    Log.d("bordingpoint2", e.getMessage());
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    // dialog.dismiss();
                   // finish();
                    progressBar2.setVisibility(View.GONE);
                    buytickets.setVisibility(View.VISIBLE);

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


                Log.d("bordingpointtest",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                progressBar2.setVisibility(View.GONE);
                buytickets.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {



                RobotoEditText usernameWrapper1 =findViewById(R.id.usernameWrapper);
                RobotoEditText phoneWrapper1 = findViewById(R.id.phoneWrapper);
                RobotoEditText  emailWrapper1 = findViewById(R.id.emailWrapper);
                RobotoEditText  lastnameWrapper1 =findViewById(R.id.lastnameWrapper);
                RobotoTextView nationalitytext1 =findViewById(R.id.nationalitytext);
                RobotoTextView  expirationdate1 =findViewById(R.id.expirationdate);
                RobotoEditText  numberPassport1 =findViewById(R.id.numberPassport);


                String usernameWrapper11 = usernameWrapper1.getText().toString();
                String phoneWrapper11 = phoneWrapper1.getText().toString();
                String emailWrapper11 = emailWrapper1.getText().toString();
                String lastnameWrapper11 = lastnameWrapper1.getText().toString();
                String nationalitytext11 = nationalitytext1.getText().toString();
                String expirationdate11 = expirationdate1.getText().toString();
                String numberPassport11 = numberPassport1.getText().toString();
              //  fromto.setText(manualFlightModel.getModels_array().get(0).getCode()+" — " +manualFlightModel.getModels_array().get(0).getTo_code());

                //String name_from,name_to;

                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname",usernameWrapper11);
                params.put("lastname",lastnameWrapper11);
                params.put("email",emailWrapper11);
                params.put("address",nationalitytext11);
                params.put("phone",phoneWrapper11);
                params.put("couponid"," ");
                params.put("itemid",manualFlightModel.getId());
                params.put("children",manualFlightModel.getChildren());
                params.put("adults",manualFlightModel.getAdults());
                params.put("infant",manualFlightModel.getInfants());
                params.put("from",manualFlightModel.getModels_array().get(0).getCode());
                params.put("to",manualFlightModel.getModels_array().get(0).getTo_code());
                params.put("type",manualFlightModel.getFlight_type());



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


        RobotoEditText usernameWrapper1 =findViewById(R.id.usernameWrapper);
        RobotoEditText phoneWrapper1 = findViewById(R.id.phoneWrapper);
        RobotoEditText  emailWrapper1 = findViewById(R.id.emailWrapper);
        RobotoEditText  lastnameWrapper1 =findViewById(R.id.lastnameWrapper);


        RobotoTextView nationalitytext1 =findViewById(R.id.nationalitytext);
        RobotoTextView  expirationdate1 =findViewById(R.id.expirationdate);
        RobotoEditText  numberPassport1 =findViewById(R.id.numberPassport);


        String number =   dataItem.getAdults().replaceAll("\\D+","");
        int size =Integer.parseInt(number);

        ArrayList<Adults_model> adults_modelArrayList =  new ArrayList<>();
         Adults_model adults_model = null;
        for (int i=0;i<size;i++)
        {
             //adults_model = new Adults_model("Mr",""+usernameWrapper1.getText(),""+lastnameWrapper1.getText(),""+emailWrapper1.getText(),""+phoneWrapper1.getText(),"1990-01-01",""+expirationdate1.getText(),""+numberPassport1.getText(),""+nationalitytext1.getText(),"1", Gender);

            adults_model = new Adults_model("Mr","test","test","test@gmail.com","00000000000","1990-01-01","000903","0010010101001","pakis","1", Gender);
            adults_modelArrayList.add(adults_model);
        }

        JSONArray list = null;



        String element = gson.toJson(adults_modelArrayList, new TypeToken<ArrayList<Adults_model>>() {}.getType());
        try {
            list = new JSONArray(element);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
