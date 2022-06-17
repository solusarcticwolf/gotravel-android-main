package com.phptravelsnative.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.phptravelsnative.Adapters.AutoSuggestedAdapter;
import com.phptravelsnative.Adapters.ListingAdapters;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.HotelModel;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.ComplexPreferences;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Views.SingleTonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchingHotels extends Drawer {


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    ArrayList<HotelModel> list_models = new ArrayList<>();
    ListView listView;
    boolean userScrolled = false;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    Response.Listener<String> response_listener;
    Response.Listener<String> response_listener_expedia;
    ListingAdapters hotelsAdapters;
    private RelativeLayout bottomLayout;
    int offest = 1;
    String offset_get = "&offset=";
    int total_offest;
    Boolean moreEan = false;
    Boolean check_ean;
    String Sesstion_id;
    TextView emptyView;
    String cache_key;
    String cache_location;
    Hotel_data hotel_data;
    String location[];
    String modul ;


    SegmentedProgressBar segmentedProgressBar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view2;
    LayoutInflater layoutInflaterAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_searching);
        View inflated = stub.inflate();


        hotel_data = new Hotel_data();
        builder = new AlertDialog.Builder(SearchingHotels.this);
        layoutInflaterAndroid = LayoutInflater.from(SearchingHotels.this);
        view2 = layoutInflaterAndroid.inflate(R.layout.progress_dialog, null);
        builder.setView(view2);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        segmentedProgressBar = (SegmentedProgressBar) view2.findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(50000);


        bottomLayout = (RelativeLayout) inflated.findViewById(R.id.loadItemsLayout_listView);




        Intent intent = getIntent();
        check_ean = intent.getBooleanExtra("check_ean", true);
        if (check_ean) {
            hotel_data = intent.getParcelableExtra("hotel");
            textView.setText(hotel_data.getLocation());
            String s=hotel_data.getLocation().replaceAll("-",",");
            s=s.replaceAll(" ",",");
            location=s.split(",");




        } else {
            hotel_data = intent.getParcelableExtra("ho");
            textView.setText(hotel_data.getLocation());
        }
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSingleLine(true);
        textView.setSelected(true);


        listView = (ListView) inflated.findViewById(R.id.list_view);

        emptyView=(TextView)inflated.findViewById(R.id.emptyList);

        modul =  intent.getStringExtra("modulhotel");
        if(modul == null){
            modul = "normal";
        }
        if (modul.contentEquals("Travelhopehotels"))
        {

           TravelshopeHotelslist();
        }else
        {
            normal_olddata();
        }






        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (userScrolled
                        && firstVisibleItem + visibleItemCount == totalItemCount) {
                    userScrolled = false;
                    if (offest < total_offest) {
                        offest = offest + 1;
                        if (hotel_data.getLocation().equals("")) {
                            stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name + "hotels/list?appKey=" + Constant.key + "&checkin=" + hotel_data.getFrom() + "&checkout=" + hotel_data.getTo() + "&child=" + hotel_data.getChild() + "&adults=" + hotel_data.getAdult() + offset_get + offest + "&lang=" + sharedPreferences.getString("Language", "en"), response_listener, null);
                        }
                        else
                            stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name + "hotels/search?appKey=" + Constant.key + "&searching=" + hotel_data.getId() + "&checkin=" + hotel_data.getFrom() + "&checkout=" + hotel_data.getTo() + "&child=" + hotel_data.getChild() + "&adults=" + hotel_data.getAdult() + offset_get + offest + "&lang=" + sharedPreferences.getString("Language", "en"), response_listener, null);

                        requestQueue.add(stringRequest);
                        bottomLayout.setVisibility(View.VISIBLE);
                    }
                    if (moreEan) {
                        stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name + "expedia/listMore?appKey=&customerSessionId=" + Sesstion_id + "&cacheKey=" + cache_key + "&cacheLocation=" + cache_location+"&locale="+HashMapCompare(sharedPreferences.getString("Language", "en")), response_listener_expedia, null);
                        requestQueue.add(stringRequest);
                        bottomLayout.setVisibility(View.VISIBLE);
                    }
                }

            }
        });






    }
    public static String HashMapCompare(String s)
    {

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("en","en_US");
        hashMap.put("ar","ar_SA");
        hashMap.put("cs","cs_CZ");
        hashMap.put("da","da_DK");
        hashMap.put("de","de_DE");
        hashMap.put("el","el_GR");
        hashMap.put("es","es_ES");
        hashMap.put("et","et_EE");
        hashMap.put("fi","fi_FI");
        hashMap.put("fr","fr_FR");
        hashMap.put("hu","hu_HU");
        hashMap.put("hr","hr_HR");
        hashMap.put("id","id_ID");
        hashMap.put("is","is_IS");
        hashMap.put("it","it_IT");
        hashMap.put("ja","ja_JP");
        hashMap.put("ko","ko_KR");
        hashMap.put("ms","ms_MY");
        hashMap.put("lv","lv_LV");
        hashMap.put("lt","lt_LT");
        hashMap.put("nl","nl_NL");
        hashMap.put("no","no_NO");
        hashMap.put("pl","pl_PL");
        hashMap.put("pt","pt_BR");
        hashMap.put("ru","ru_RU");
        hashMap.put("sv","sv_SE");
        hashMap.put("sk","sk_SK");
        hashMap.put("th","th_TH");
        hashMap.put("tr","tr_TR");
        hashMap.put("uk","uk_UA");
        hashMap.put("vi","vi_VN");
        hashMap.put("zh","zh_TW");

        return  hashMap.get(s);

    }


    public  void TravelshopeHotelslist()
    {


        ComplexPreferences sharedPreferences;
        String MyPREFERENCES = "MyPrefs";

        Auto_Model searchHotel=new Auto_Model();
        sharedPreferences =new ComplexPreferences(getApplicationContext(),MyPREFERENCES, Context.MODE_PRIVATE);

        searchHotel=sharedPreferences.getObject("Travelshope_Hotels_Last_Search",Auto_Model.class,searchHotel);
        String namecityid =searchHotel.getCountryCode();

        final String[] separated = namecityid.split("/");
        Log.d("expediaUrl",              Constant.domain_name + "expedia/search?appKey=" + Constant.key + "&location=" +location[0].trim()+ "&checkIn=" + hotel_data.getFrom() + "&checkOut=" + hotel_data.getTo() + "&adults=" + hotel_data.getAdult() + "&child=" + hotel_data.getChild()+"&locale=");


        String url= Constant.domain_name+"Travelhopehotels/list?appKey="+Constant.key;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("bordingpointtaxi","response/" +response);


                try {
                    JSONObject parentObject = new JSONObject(response.toString());
                    JSONArray jsonArray= parentObject.getJSONArray("response");
                    for (int i = 0; i <jsonArray.length(); i++) {
                        HotelModel hm = new HotelModel();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String company_name = jsonObject.getString("company_name");
                        String address = jsonObject.getString("address");
                        String id = jsonObject.getString("id");
                        String description = jsonObject.getString("description");
                        String checkin = jsonObject.getString("checkin");
                        String checkout = jsonObject.getString("checkout");
                        String hotel_slug = jsonObject.getString("hotel_slug");
                        String city_name = jsonObject.getString("city_name");
                        String price = jsonObject.getString("price");
                        String image = jsonObject.getString("image");
                        String rating = jsonObject.getString("rating");

                        String test = rating;
                        char first = test.charAt(0);
                       // rating = rating.replaceAll("\\D+","");


                        String Adult = hotel_data.getAdult().replaceAll("\\D+","");

                        hm.setcurrCode("USD");
                        hm.setName(address);
                        hm.setPrice(price);
                        hm.setTitle(company_name);
                        hm.setThumbnail(image);
                        hm.setLocation(address);
                        hm.setid(Integer.parseInt(id));
                        hm.setCheckin(checkin);
                        hm.setCheckout(checkout);
                        hm.setAdults(Adult);
                        hm.setRateCode(rating);
                        hm.setStarsCount(Integer.parseInt(""+first));


                        list_models.add(hm);

                    }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                    alertDialog.dismiss();
                }


                alertDialog.dismiss();
                if (hotelsAdapters == null) {
                    if(list_models.size()<=0)
                        emptyView.setVisibility(View.VISIBLE);

                    hotelsAdapters = new ListingAdapters(SearchingHotels.this, list_models, false, Sesstion_id, hotel_data, "travelhope_hotel");
                    listView.setAdapter(hotelsAdapters);
                } else {
                    hotelsAdapters.notifyDataSetChanged();
                    bottomLayout.setVisibility(View.GONE);
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

                alertDialog.dismiss();
                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {


               /* int Child=Integer.parseInt(hotel_data.getChild().replaceAll("[\\D]",""));
                int Adult=Integer.parseInt(hotel_data.getAdult().replaceAll("[\\D]",""));*/

                String Adult = hotel_data.getAdult().replaceAll("\\D+","");
                String Child = hotel_data.getChild().replaceAll("\\D+","");

                if (Child.isEmpty())
                {
                    Child="0";
                }



                Map<String, String> params = new HashMap<String, String>();
                params.put("city",separated[0]);
                params.put("country",separated[1]);
                params.put("checkin",hotel_data.getFrom());
                params.put("checkout",hotel_data.getTo() );
                params.put("adults", Adult);
                params.put("children", Child);
                params.put("currceny_code","USD");



                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);

    }



    public void normal_olddata()
    {
        response_listener = new Response.Listener<String>() {

            HotelModel hm;

            @Override
            public void onResponse(String response) {


                alertDialog.dismiss();

                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response);

                    JSONObject error_object = parentObject.getJSONObject("error");

                    if (error_object.getBoolean("status")) {
                        Toast.makeText(getBaseContext(), error_object.getString("msg"), Toast.LENGTH_LONG).show();

                    } else {
                        if (offest == 1) {
                            total_offest = parentObject.getInt("totalPages");
                        }
                        JSONArray parentArray = parentObject.getJSONArray("response");
                        for (int i = 0; i < parentArray.length(); i++) {
                            hm = new HotelModel();
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            hm = new HotelModel();
                            hotel_data.setId(finalObject.getInt("id"));
                            hm.setName(finalObject.getString("title"));
                            hm.setPrice(finalObject.getString("price"));
                            hm.setStarsCount(finalObject.getInt("starsCount"));
                            hm.setRating(finalObject.getJSONObject("avgReviews").getString("overall"));
                            hm.setThumbnail(finalObject.getString("thumbnail"));
                            hm.setcurrCode(finalObject.getString("currCode"));

                            if(finalObject.getString("currSymbol").equals("null"))
                                hm.setcurrSymbol("");
                            else
                                hm.setcurrSymbol(finalObject.getString("currSymbol"));


                            hm.setLocation(finalObject.getString("location"));
                            hm.setid(finalObject.getInt("id"));
                            list_models.add(hm);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (offest == 1) {
                    hotelsAdapters = new ListingAdapters(SearchingHotels.this, list_models, hotel_data, "hotel_default");
                    listView.setAdapter(hotelsAdapters);
                    if (hotelsAdapters.getCount()==0)
                        emptyView.setVisibility(View.VISIBLE);
                } else {
                    hotelsAdapters.notifyDataSetChanged();
                    bottomLayout.setVisibility(View.GONE);
                }

            }
        };
        response_listener_expedia = new Response.Listener<String>() {

            HotelModel hm;

            @Override
            public void onResponse(String response) {


                alertDialog.dismiss();

                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response);
                    JSONObject main_object = parentObject.getJSONObject("HotelListResponse");

                    moreEan = main_object.getBoolean("moreResultsAvailable");
                    Sesstion_id = main_object.getString("customerSessionId");


                    if (moreEan) {
                        cache_key = main_object.getString("cacheKey");
                        cache_location = main_object.getString("cacheLocation");

                    }
                    JSONObject hotel_object = main_object.getJSONObject("HotelList");

                    JSONArray parentArray = hotel_object.getJSONArray("HotelSummary");
                    for (int i = 0; i < parentArray.length(); i++) {
                        hm = new HotelModel();
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        hm = new HotelModel();
                        hm.setName(finalObject.getString("name"));
                        hm.setPrice(finalObject.getString("highRate"));
                        hm.setStarsCount(finalObject.getInt("hotelRating"));

                        hm.setLocation(finalObject.getString("city"));


                        hm.setThumbnail("http://media.expedia.com" + finalObject.getString("thumbNailUrl").replace("_t", "_b"));
                        hm.setcurrCode(finalObject.getString("rateCurrencyCode"));
                        hm.setcurrSymbol("");
                        hm.setid(finalObject.getInt("hotelId"));

                        hm.setRateCode(finalObject.getJSONObject("RoomRateDetailsList").getJSONObject("RoomRateDetails").getString("rateCode"));

                        hm.setRoomTypeCode(finalObject.getJSONObject("RoomRateDetailsList").getJSONObject("RoomRateDetails").getString("roomTypeCode"));
                        hm.setRateKey(finalObject.getJSONObject("RoomRateDetailsList").getJSONObject("RoomRateDetails").getJSONObject("RateInfos").getJSONObject("RateInfo").getJSONObject("RoomGroup").getJSONObject("Room").getString("rateKey"));

                        list_models.add(hm);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (hotelsAdapters == null) {
                    if(list_models.size()<=0)
                        emptyView.setVisibility(View.VISIBLE);

                    hotelsAdapters = new ListingAdapters(SearchingHotels.this, list_models, false, Sesstion_id, hotel_data, "ex_hotel");
                    listView.setAdapter(hotelsAdapters);
                } else {
                    hotelsAdapters.notifyDataSetChanged();
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        };

        requestQueue = SingleTonRequest.getmInctance(this).getRequestQueue();
        if (check_ean) {
            String url;

            Log.d("expediaUrl",             url= Constant.domain_name + "expedia/search?appKey=" + Constant.key + "&location=" +location[0].trim()+ "&checkIn=" + hotel_data.getFrom() + "&checkOut=" + hotel_data.getTo() + "&adults=" + hotel_data.getAdult() + "&child=" + hotel_data.getChild()+"&locale="+HashMapCompare(sharedPreferences.getString("Language", "en")));
            if(location[0].trim().equals(""))
                url= Constant.domain_name + "expedia/list?appKey=" + Constant.key + "&checkIn=" + hotel_data.getFrom() + "&checkOut=" + hotel_data.getTo() + "&adults=" + hotel_data.getAdult() + "&child=" + hotel_data.getChild()+"&locale=" + HashMapCompare(sharedPreferences.getString("Language", "en"));
            else
                url= Constant.domain_name + "expedia/search?appKey=" + Constant.key + "&location=" +location[0].trim()+ "&checkIn=" + hotel_data.getFrom() + "&checkOut=" + hotel_data.getTo() + "&adults=" + hotel_data.getAdult() + "&child=" + hotel_data.getChild()+"&locale="+HashMapCompare(sharedPreferences.getString("Language", "en"));

            stringRequest = new StringRequest(Request.Method.GET,url, response_listener_expedia, null
            );
        } else {

            Log.d("Check_Info",Constant.domain_name + "hotels/list?appKey=" + Constant.key + "&checkin=" + hotel_data.getFrom() + "&checkout=" + hotel_data.getTo() + "&child=" + hotel_data.getChild() + "&adults=" + hotel_data.getAdult() + offset_get + offest + "&lang=" + sharedPreferences.getString("Language", "en"));
            if (hotel_data.getId() == 0) {

                String language_code = sharedPreferences.getString("Language", "en");

                String urlreq = Constant.domain_name +"hotels/list?appKey=" + Constant.key + "&checkin=" + hotel_data.getFrom()+"&checkout=" + hotel_data.getTo() + "&child=" + hotel_data.getChild() + "&adults="+hotel_data.getAdult()+offset_get+offest+"&lang="+language_code;

                stringRequest = new StringRequest(Request.Method.GET, urlreq, response_listener, null);
            } else
            {


                //https://www.phptravels.net/api/hotels/list?appKey=phptravels&checkin=16-01-2020&checkout=18-01-2020&child=&adults=1&offset=1&lang=en
                //https://www.phptravels.net/api/hotels/search?appKey=phptravels&searching=1&checkin=20-01-2020&checkout=28-01-2020&child=&adults=1&offset=1&lang=en

                String language_code1= sharedPreferences.getString("Language", "en");
                String urlreq1 = Constant.domain_name + "hotels/search?appKey=" + Constant.key + "&searching=" + hotel_data.getId() + "&checkin=" + hotel_data.getFrom() +"&checkout="+hotel_data.getTo()+"&child="+hotel_data.getChild()+"&adults="+hotel_data.getAdult() + offset_get + offest + "&lang=" +language_code1;
                stringRequest = new StringRequest(Request.Method.GET, urlreq1, response_listener, null);

            }



        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        alertDialog.show();
    }

}
