package com.phptravelsnative.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.Model_Tour;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.Network.Parser.MainMenuParse;
import com.phptravelsnative.Network.Parser.ShowHtml;
import com.phptravelsnative.Network.Post.Car_Booking;
import com.phptravelsnative.Network.Post.Flight_Booking;
import com.phptravelsnative.Network.Post.Hotel_Booking;
import com.phptravelsnative.Network.Post.Tour_Booking;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.Views.SingleTonRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebViewInvoice extends Drawer {

    private WebView web_view;
    private ProgressDialog pd;
    private Hotel_data hotel_data;
    private Hotel_data guest_data;
    private String check_type;
    Boolean check_guest;
    Hotel_Booking requestRegister;
    Flight_Booking flight_booking_request;
    Model_Tour tour;
    boolean check_load=false;
    car_model car;
    TravelPort cabin_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_flight_do_hope);
        View inflated = stub.inflate();
        web_view = (WebView) inflated.findViewById(R.id.web_flight);

        hotel_data = new Hotel_data();

        pd = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        pd.setIndeterminate(true);
        pd.setCancelable(true);
        pd.setMessage(getString(R.string.loading));

        Intent intent = getIntent();
        check_type = intent.getStringExtra("check_type");

        check_guest = intent.getBooleanExtra("Check_Guest", false);
        if (check_guest) {
            guest_data = intent.getParcelableExtra("guest");

        }
        if (check_type.equals("hotel")) {
            hotel_data = intent.getParcelableExtra("hotel_object");
            Hotel_Invoice(intent.getStringExtra("numbers_rooms"), intent.getStringExtra("room_id"));
        } else if (check_type.equals("tour")) {
            tour = intent.getParcelableExtra("tour_object");
            Hotel_Invoice("", "");
        } else if (check_type.equals("car")) {
            car = intent.getParcelableExtra("car_object");
            Hotel_Invoice("", "");
        }else if (check_type.equals("flight")) {
            cabin_class = intent.getParcelableExtra("cabin_class");
            Flight_Invoice(cabin_class, intent.getStringExtra("id"));
        } else if (check_type.equals("booking")) {
            checkForUpdate(intent.getStringExtra("inv_number"), intent.getStringExtra("inv_code"));
        }else if (check_type.equals("ivisa")) {
            getIvisa(intent.getStringExtra("from_code"),intent.getStringExtra("to_code"));
        }
    }


    public void getIvisa(String from,String to)
    {

        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.domain_name+"ivisa/ivisaframe?appKey="+Constant.key+
                "&nationality_country="+from+"&destination_country="+to, new Response.Listener() {
            @Override
            public void onResponse(final Object response) {

                JSONObject main_object = null;
                try {
                    main_object = new JSONObject(response.toString());
                    if (main_object.getString("status").equals("fail")) {

                        String urlIvia = main_object.getJSONObject("error").getString("next_url").replace("//","/");

                        urlIvia = urlIvia.replace(":/","://");
                        Log.d("ivisaUrl",urlIvia+"?appKey="+Constant.key);




                        ShowUrl(urlIvia+"?appKey="+Constant.key);

                    }else if (main_object.getString("status").equals("error")) {

                        Toast.makeText(getApplicationContext(), main_object.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                        finish();
                    }else  {


                        ShowUrl(main_object.getJSONObject("data").getString("frame_url"));


                    }

                } catch (JSONException e) {

                    Log.d("abcwwwwd", e.getMessage());
                }

            }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null)
                    return;

            }
        });


        RequestQueue requestQueue = SingleTonRequest.getmInctance(getApplicationContext()).getRequestQueue();

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }




    public void checkForUpdate(String number, String code) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name + "invoice/info?appKey="+Constant.key+"&invoiceno=" + number + "&invoicecode=" + code
                , new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject main_object = parentObject.getJSONObject("response");
                    if (main_object.getString("error").equals("")) {
                        ShowUrl(main_object.getString("url"));
                    } else {
                        Toast.makeText(getApplicationContext(), main_object.getString("error"), Toast.LENGTH_LONG).show();
                        finish();

                    }

                } catch (JSONException e) {

                    Log.d("abcwwwwd", e.getMessage());
                }

            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        pd.show();
    }

    private void Hotel_Invoice(String rooms_count, String rooms_id) {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject main_json = new JSONObject(response);
                    JSONObject json_object = main_json.getJSONObject("response");
                    ShowUrl(json_object.getString("url"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String id = sharedPreferences.getString("id", "");
        if (check_type.equals("hotel")) {
            if (check_guest) {
                requestRegister = new Hotel_Booking(guest_data, hotel_data.getId() + "", rooms_count, rooms_id, hotel_data.getFrom(), hotel_data.getTo(), hotel_data.getAdult(), hotel_data.getChild(), Invoice.coupon, "hotels", listener);

            } else {
                requestRegister = new Hotel_Booking(id, hotel_data.getId() + "", rooms_count, rooms_id, hotel_data.getFrom(), hotel_data.getTo(), hotel_data.getAdult(), hotel_data.getChild(), Invoice.coupon, "hotels", listener);
            }
            pd.show();
            RequestQueue requestQueue = SingleTonRequest.getmInctance(getBaseContext()).getRequestQueue();
            requestQueue.add(requestRegister);

        } else if (check_type.equals("car")) {
            Car_Booking car_request;
            if (check_guest) {
                car_request = new Car_Booking(guest_data, car, Invoice.coupon, "cars", listener);

            } else {
                car_request = new Car_Booking(id, car, Invoice.coupon, "cars", listener);

            }
            pd.show();
            RequestQueue requestQueue = SingleTonRequest.getmInctance(getBaseContext()).getRequestQueue();
            requestQueue.add(car_request);
        } else if (check_type.equals("tour")) {


            Tour_Booking tour_request;
            if (check_guest) {
                tour_request = new Tour_Booking(guest_data, tour, Invoice.coupon, "tours", listener);
            } else {
                tour_request = new Tour_Booking(id, tour, Invoice.coupon, "tours", listener);

            }
            pd.show();
            RequestQueue requestQueue = SingleTonRequest.getmInctance(getBaseContext()).getRequestQueue();
            requestQueue.add(tour_request);
        }

    }
    private void Flight_Invoice(TravelPort cabin_class, String itemid) {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject main_json = new JSONObject(response);
                    JSONObject json_object = main_json.getJSONObject("response");
                    ShowUrl(json_object.getString("url"));


                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        String id = sharedPreferences.getString("id", "");
            if (check_guest) {
                flight_booking_request = new Flight_Booking(guest_data, itemid,cabin_class, Invoice.coupon, listener,errorListener);

            } else {
                flight_booking_request = new Flight_Booking(id,itemid ,cabin_class, Invoice.coupon, listener);
            }
            pd.show();
            RequestQueue requestQueue = SingleTonRequest.getmInctance(getBaseContext()).getRequestQueue();
            flight_booking_request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(flight_booking_request);


    }

    private void ShowUrl(String s) {
        web_view.getSettings().setLoadsImagesAutomatically(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_view.setWebViewClient(new MyWebViewClient());
        web_view.loadUrl(s);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (pd.isShowing()) {
                check_load=true;
                pd.dismiss();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url,
                                  android.graphics.Bitmap favicon) {
            if (!pd.isShowing()) {
                pd.show();
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(check_load) {
            if (!pd.isShowing()) {
                Intent intent = new Intent(getApplicationContext(), MainLayout.class);
                intent.putExtra("CheckLayout", "MainList");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}
