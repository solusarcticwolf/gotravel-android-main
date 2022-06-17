package com.phptravelsnative.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Adapters.AmenitiesAdapter;
import com.phptravelsnative.Adapters.ListingAdapters;
import com.phptravelsnative.Adapters.RoomDesadapter;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.HotelModel;
import com.phptravelsnative.Models.Model_Room;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hotel_travelhopedetails  extends Drawer {


    ArrayList<Model_Room> modelRoomArrayList=new ArrayList<>();
    HotelModel currentListData;
    RobotoTextView company_name,company_address,checkin,checkout,starr,priceee,descrptions,roomnmber,adultnmber,booking;
    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_images ;
    ArrayList<String> Hash_file_amenities=new ArrayList<String>();//Creating arraylist


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;



    SegmentedProgressBar segmentedProgressBar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view2;
    LayoutInflater layoutInflaterAndroid;
    RatingBar ratingbar ;
    ImageView backbuttn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //setContentView(R.layout.activity_hotel_travelhopedetails);


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_hotel_travelhopedetails);
        View inflated = stub.inflate();





        Intl();
    }
    public void Intl()
    {


        builder = new AlertDialog.Builder(Hotel_travelhopedetails.this);
        layoutInflaterAndroid = LayoutInflater.from(Hotel_travelhopedetails.this);
        view2 = layoutInflaterAndroid.inflate(R.layout.progress_dialog, null);
        builder.setView(view2);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        segmentedProgressBar = (SegmentedProgressBar) view2.findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(20000);


        Intent intent = getIntent();
        String result = intent.getStringExtra("currentdata");
        Gson gson =  new Gson();






         currentListData = gson.fromJson(result, HotelModel.class);

        findbyidall();




        Hotelslist_details();




    }


    public void findbyidall()
    {
        Hash_file_images = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.slider);



        priceee= findViewById(R.id.priceee);
        priceee.setVisibility(View.GONE);
        company_name = findViewById(R.id.company_name);
        company_address = findViewById(R.id.company_address);
        checkin = findViewById(R.id.checkin);
        checkout = findViewById(R.id.checkout);
        starr = findViewById(R.id.starr);
        ratingbar = findViewById(R.id.ratingbar);
        descrptions= findViewById(R.id.descrptions);
        roomnmber= findViewById(R.id.roomnmber);
        adultnmber= findViewById(R.id.adultnmber);
        booking= findViewById(R.id.booking);

        backbuttn =  findViewById(R.id.backbuttn);
        backbuttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


    public  void  Hotelslist_details()
    {

        String url= Constant.domain_name+"Travelhopehotels/detail?appKey="+Constant.key;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("Hotelsl_details","response/" +response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject finalresponse = jsonObject.getJSONObject("response");

                    String company_name2 = finalresponse.getString("company_name");
                    String address = finalresponse.getString("address");
                    String description = finalresponse.getString("description");
                    String checkin2 = finalresponse.getString("checkin");
                    String checkout2 = finalresponse.getString("checkout");
                    String checkInTime = finalresponse.getString("checkInTime");
                    String checkOutTime = finalresponse.getString("checkOutTime");
                    String thumb = finalresponse.getString("thumb");
                    String rating = finalresponse.getString("rating");
                    String currency_name = finalresponse.getString("currency_name");

                    String latitude = finalresponse.getString("latitude");
                    String longitude = finalresponse.getString("longitude");



                    description = description.replaceAll("\\<.*?\\>", "");

                    descrptions.setText(description);
                    company_name.setText(company_name2);
                    company_address.setText(address);
                    checkin.setText(checkin2);
                    checkout.setText(checkout2);
                    starr.setText(rating);
                    priceee.setText(currentListData.getcurrCode()+" "+currentListData.getPrice());
                    priceee.setVisibility(View.VISIBLE);
                    ratingbar.setRating(Float.parseFloat(rating));






                    Hash_file_images.put(thumb,thumb);

                    JSONArray images = finalresponse.getJSONArray("images");
                    for (int i = 0; i < images.length(); i++)
                    {
                        String dataimages = String.valueOf(images.get(i));
                        Hash_file_images.put(dataimages, dataimages);
                    }


                    JSONArray amenities = finalresponse.getJSONArray("amenities");
                    for (int i = 0; i < amenities.length(); i++)
                    {
                        JSONObject jsonObject1 = amenities.getJSONObject(i);
                        String dataimages = jsonObject1.getString("title");
                        Hash_file_amenities.add(dataimages);
                    }




                    JSONArray rooms = finalresponse.getJSONArray("rooms");
                    roomnmber.setText(rooms.length()+" Room");
                    adultnmber.setText( currentListData.getAdults()+" Adults");

                    ArrayList < String > imagec;
                    for (int i = 0; i < rooms.length(); i++)
                    {
                        JSONObject jsonObject1 = rooms.getJSONObject(i);
                        Model_Room model_room =  new Model_Room();
                         imagec = new ArrayList <String > ();


                         String  id= jsonObject1.getString("id");
                         String  price= jsonObject1.getString("price");;
                         String hotel_id= jsonObject1.getString("hotel_id");;
                         String room_descriptions= jsonObject1.getString("room_descriptions");;
                         String room_name= jsonObject1.getString("room_name");;
                         String room_slug= jsonObject1.getString("room_slug");;
                         String adults= jsonObject1.getString("adults");;
                         String childs= jsonObject1.getString("childs");;
                         String type= jsonObject1.getString("type");


                         JSONArray jsonArrayimages = jsonObject1.getJSONArray("image");
                         for (int r =0;r<jsonArrayimages.length();r++){

                             String dataiimage = String.valueOf(jsonArrayimages.get(r));
                             imagec.add(dataiimage);
                         }


                         model_room.setRate(rating);
                        model_room.setHotelname(company_name2);
                        model_room.setCehckout(checkout2);
                        model_room.setCheckin(checkin2);
                        model_room.setLongitude(longitude);
                        model_room.setLatitude(latitude);
                        model_room.setAddress(address);
                        model_room.setId(id);
                        model_room.setPrice(price);
                        model_room.setHotel_id(hotel_id);
                        model_room.setRoom_descriptions(room_descriptions);
                        model_room.setRoom_name(room_name);
                        model_room.setRoom_slug(room_slug);
                        model_room.setAdults(adults);
                        model_room.setChilds(childs);
                        model_room.setType(type);
                        model_room.setImage(imagec);
                        modelRoomArrayList.add(model_room);


                    }








                    load_silderimages();
                    load_animations();




                }
                catch (JSONException e) {
                    e.printStackTrace();

                    Log.d("HamzaError" + "-", ""+e);

                    Toast.makeText(getApplicationContext(),"error    " +e  ,Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
                alertDialog.dismiss();

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
                Log.d("HamzaError" + "-", logText, error);
                alertDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {

                JSONObject obj = new JSONObject();
                try {
                    obj.put("vendor", "3");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



                Map<String, String> params = new HashMap<String, String>();
                params.put("currceny_code","USD");
                params.put("checkin",currentListData.getCheckin());
                params.put("checkout",currentListData.getCheckout());
                params.put("hotel_id",""+currentListData.getid());
                params.put("custom_payload", String.valueOf(obj));



                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);

    }

    public void load_silderimages()
    {
        for(String name : Hash_file_images.keySet()){

            TextSliderView textSliderView = new TextSliderView(Hotel_travelhopedetails.this);
            textSliderView.description(" ").image(Hash_file_images.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }


        final Handler handler = new Handler();
        final int delay = 10000000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){

                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(2000);
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
    public void load_animations()
    {
       /* ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.animationsdesgin, R.id.label,Hash_file_amenities);

        ListView listView = (ListView) findViewById(R.id.mylist);
        listView.setAdapter(adapter);*/

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        AmenitiesAdapter mAdapter = new AmenitiesAdapter(Hash_file_amenities);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        RecyclerView recyclerViewroom = findViewById(R.id.recyclerViewroom);

        RoomDesadapter mAdapter_room = new RoomDesadapter(getApplicationContext(),modelRoomArrayList);
        LinearLayoutManager mLayoutManager2= new LinearLayoutManager(getApplicationContext());
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewroom.setLayoutManager(mLayoutManager2);
        recyclerViewroom.setItemAnimator(new DefaultItemAnimator());
        recyclerViewroom.setAdapter(mAdapter_room);




    }






}
