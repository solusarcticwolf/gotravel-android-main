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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.devspark.robototextview.widget.RobotoCheckedTextView;
import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phptravelsnative.Adapters.Manual_Flight_Adapter;
import com.phptravelsnative.Adapters.Manual_Flight_Adapter2;
import com.phptravelsnative.Adapters.Tarco_adapter;
import com.phptravelsnative.Adapters.Tp_Listing_adapter;
import com.phptravelsnative.Models.Airline;
import com.phptravelsnative.Models.CustomPayload;
import com.phptravelsnative.Models.DataItem;
import com.phptravelsnative.Models.Flights2;
import com.phptravelsnative.Models.ManualFlightModel;
import com.phptravelsnative.Models.OneWay;
import com.phptravelsnative.Models.RoutesItem;
import com.phptravelsnative.Models.Tarco_Model;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.phptravelsnative.Models.RouteItem;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import static com.phptravelsnative.utality.Extra.Constant.domain_name;


public class ManualFlightActivity_New extends Drawer {

    Context context=this;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    Tp_Listing_adapter adapter;
    ArrayList<ManualFlightModel> array_main_flight = new ArrayList<>();
    ArrayList<ManualFlightModel> array_main_flight_main = new ArrayList<>();
    final ArrayList<Flights2> array_main = new ArrayList<>();
    List<DataItem> array_dataItem = new ArrayList<>();
    List<DataItem> array_dataItem2 = new ArrayList<>();
    ArrayList<TravelPortModel> tpm = new ArrayList<>();
    TravelPort cabin_class = new TravelPort();
    RecyclerView recyclerView;
    Gson gson;
    RobotoTextView tv_citiesname;
    ImageView filter ;

    ArrayList<Tarco_Model> array_main_tarco = new ArrayList<>();



    //ProgressDialog dialog;
    SegmentedProgressBar  segmentedProgressBar;
    String filterstring;
    String main_arraydata;
    String airline ;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String model_new ;
    String adults,children,inflant,tourtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.fragment_recycler_view);
        View inflated = stub.inflate();






        gson = new Gson();
        Intent intent = getIntent();
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


        filterstring = getIntent().getExtras().getString("filterdata" ,"no");
        model_new = getIntent().getExtras().getString("Model" ,"travelhope");
        recyclerView = inflated.findViewById(R.id.recyclerView);
        tv_citiesname = (RobotoTextView) findViewById(R.id.tv_citiesname);
        filter = findViewById(R.id.filter);


        segmentedProgressBar = (SegmentedProgressBar) findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        //segmentedProgressBar.setContainerColor(Color.WHITE);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(50000);
        //segmentedProgressBar.pause();
        //segmentedProgressBar.setCompletedSegments(3);

        if (cabin_class.getTour_type().contentEquals("round"))
        {
            segmentedProgressBar.playSegment(100000);
        }else
        {
            segmentedProgressBar.playSegment(50000);
        }



        if (filterstring.contains("yes"))
        {
            array_dataItem2.clear();
            segmentedProgressBar.setVisibility(View.GONE);
            tv_citiesname.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            tv_citiesname.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());
            String datadetails =getIntent().getStringExtra("dataItem");
            main_arraydata =getIntent().getStringExtra("main_arraydata");
            array_dataItem2= (ArrayList<DataItem>) fromJson(datadetails, new TypeToken<ArrayList<DataItem>>() {}.getType());
            array_dataItem= (ArrayList<DataItem>) fromJson(main_arraydata, new TypeToken<ArrayList<DataItem>>() {}.getType());
            if(array_dataItem2.size()==0)
            {
                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
            }

            Manual_Flight_Adapter2 adapter2 = new Manual_Flight_Adapter2(ManualFlightActivity_New.this, array_dataItem2,cabin_class);
            recyclerView.setAdapter(adapter2);
            recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity_New.this));
            model_new="travelhope";

        } else if (filterstring.contains("flightfliter"))
        {
            array_main_flight.clear();
            segmentedProgressBar.setVisibility(View.GONE);
            tv_citiesname.setVisibility(View.VISIBLE);
            filter.setVisibility(View.VISIBLE);
            tv_citiesname.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());
            String datadetails =getIntent().getStringExtra("dataItem");

            main_arraydata =getIntent().getStringExtra("main_arraydata");
            array_main_flight= (ArrayList<ManualFlightModel>) fromJson(datadetails, new TypeToken<ArrayList<ManualFlightModel>>() {}.getType());
            array_main_flight_main= (ArrayList<ManualFlightModel>) fromJson(main_arraydata, new TypeToken<ArrayList<ManualFlightModel>>() {}.getType());
            if(array_main_flight.size()==0)
            {
                array_main_flight.addAll(array_main_flight_main);
                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
            }

            //ManualFlightModel
            Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity_New.this, array_main_flight,cabin_class);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity_New.this));

            model_new="flights";

        }
        else if(model_new.contentEquals("flights"))
        {
            ExpediaSearch();
        }
        else if(model_new.contentEquals("TarcoFlight"))
        {
            TarcoFlight();
            Toast.makeText(getApplicationContext(),"TarcoFlight",Toast.LENGTH_LONG).show();
        }
        else if(model_new.contentEquals("travelhope"))
        {

            sharedPref = ManualFlightActivity_New.this.getPreferences(Context.MODE_PRIVATE);
            editor = sharedPref.edit();
            editor.putString("stop", null);
            editor.putString("airline", null);
            editor.commit();


            postrequest(domain_name+"/travelhope_flights/search?appKey="+Constant.key);
        }
        else
        {
            sharedPref = ManualFlightActivity_New.this.getPreferences(Context.MODE_PRIVATE);
            editor = sharedPref.edit();
            editor.putString("stop", null);
            editor.putString("airline", null);
            editor.commit();


            postrequest(domain_name+"/travelhope_flights/search?appKey="+Constant.key);
        }

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchfilterd_dialogbox();
            }
        });

    }



    public void ExpediaSearch() {

        String url = "";


        if(!cabin_class.getTour_type().equals("oneway"))
        {
            cabin_class.setTour_type("return");
        }

        url = domain_name+"flights/search?appKey="+Constant.key+"&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+
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
                                    array_main_flight.add(mainObject);
                                    array_main_flight_main.add(mainObject);
                                }

                                Manual_Flight_Adapter adapter = new Manual_Flight_Adapter(ManualFlightActivity_New.this, array_main_flight,cabin_class);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity_New.this));




                            } else {

                                Toast.makeText(getBaseContext(), parentObject.getJSONObject("error").getString("msg"), Toast.LENGTH_LONG).show();
                                finish();

                            }
                            //dialog.dismiss();
                            segmentedProgressBar.setVisibility(View.GONE);
                            tv_citiesname.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());
                            tv_citiesname.setVisibility(View.VISIBLE);
                            filter.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {

                            Log.d("abcwwwwd", e.getMessage());
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            // dialog.dismiss();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(150000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }



    public void postrequest(String url)
    {
        // dialog.show();

        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("bordingpoint2","response/" +response);

                ArrayList<RouteItem> array_routeItem ;
                ArrayList<RoutesItem> array_RoutesItem ;

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.optJSONArray("data");

                    if(data.length()==0)
                    {
                        finish();
                        Toast.makeText(getApplicationContext(),"No route avaible",Toast.LENGTH_LONG).show();
                    }

                    for (int i=0; i<data.length();i++) {

                        DataItem dataItem = new DataItem();
                        JSONObject object = data.getJSONObject(i);


                        String from_code = object.getString("from_code");
                        String to_code = object.getString("to_code");
                        String airline = object.getString("airline");
                        String currency = object.getString("currency");
                        String flight_duration = object.getString("flight_duration");
                        String flight_id = object.getString("flight_id");

                        String flight_price = "" + object.getInt("flight_price");
                        String stops = "" + object.getInt("stops");
                        String departure_time = "" + object.getInt("departure_time");
                        String arrival_time = "" + object.getInt("arrival_time");


                        array_RoutesItem = new ArrayList<RoutesItem>();
                        JSONArray routes = object.getJSONArray("routes");
                        for (int res = 0; res < routes.length(); res++) {

                            RoutesItem routesItem =  new RoutesItem();
                            JSONArray objectroutes = routes.getJSONArray(res);

                            String to = (String) objectroutes.get(0);
                            String from = (String) objectroutes.get(1);
                            routesItem.setTo(to);
                            routesItem.setFrom(from);
                            array_RoutesItem.add(routesItem);

                        }




                        JSONArray route = object.getJSONArray("route");
                        array_routeItem = new ArrayList<RouteItem>();








                        for (int r = 0; r < route.length(); r++) {

                            RouteItem routeItem = new RouteItem();
                            JSONObject objectroute = route.getJSONObject(r);

                            String flight_no = "" + objectroute.optInt("flight_no");


                            routeItem.setCityFrom(objectroute.getString("city_from"));
                            routeItem.setCityTo(objectroute.getString("city_to"));
                            routeItem.setFromCode(objectroute.getString("from_code"));
                            routeItem.setToCode(objectroute.getString("to_code"));
                            routeItem.setAirline(objectroute.getString("airline"));
                            routeItem.setLatTo(""+objectroute.getDouble("latTo"));
                            routeItem.setFlightNo(flight_no);
                            routeItem.setPrice(""+1);
                            routeItem.setOriginalReturn(""+0);
                            routeItem.setMapFrom(objectroute.getString("vehicle_type"));
                            routeItem.setAirlineType(objectroute.getString("vehicle_type"));
                            routeItem.setMapTo(objectroute.getString("vehicle_type"));
                            routeItem.setLatitudeFrom(""+objectroute.optInt("latitude_from"));
                            routeItem.setLongitudeFrom(""+objectroute.optInt("longitude_from"));
                            routeItem.setLongitudeTo(""+objectroute.optInt("longitude_to"));
                            routeItem.setLatitudeTo(""+objectroute.optInt("latitude_to"));
                            routeItem.setArrivalUtcTime(objectroute.optInt("arrival_utc_time"));
                            routeItem.setDepartureUtcTime(objectroute.optInt("departure_utc_time"));
                            routeItem.setArrivalTime(""+objectroute.optInt("arrival_time"));
                            routeItem.setDepartureTime(""+objectroute.optInt("departure_time"));
                            routeItem.setVehicleType(objectroute.getString("vehicle_type"));

                            array_routeItem.add(routeItem);

                            // Log.d("rountsize ", r+" "+flight_no);

                        }

                        JSONObject custom_payload = object.getJSONObject("custom_payload");
                        CustomPayload customPayload = new CustomPayload();
                        String visitor_uniqid = custom_payload.getString("visitor_uniqid");
                        String booking_token= custom_payload.getString("booking_token");
                        customPayload.setVisitorUniqid(visitor_uniqid);
                        customPayload.setBookingToken(booking_token);





                        dataItem.setAdults(adults);
                        dataItem.setChildren(children);
                        dataItem.setInfants(inflant);
                        dataItem.setFlight_type(tourtype);
                        dataItem.setCustomPayload(customPayload);
                        dataItem.setRoutes(array_routeItem);
                        dataItem.setRoute(array_RoutesItem);
                        dataItem.setFrom_code(from_code);
                        dataItem.setTo_code(to_code);
                        dataItem.setAirline(airline);
                        dataItem.setCurrency(currency);
                        dataItem.setFlight_price(flight_price);
                        dataItem.setFlight_duration(flight_duration);
                        dataItem.setStops(stops);
                        // String departure_timetest  =date_formate(Long.parseLong(departure_time));
                        dataItem.setDeparture_time(""+departure_time);
                        //  String arrival_timetest  =date_formate(Long.parseLong(arrival_time));
                        dataItem.setArrival_time(""+arrival_time);
                        dataItem.setFlight_id(flight_id);


                        if (cabin_class.getTour_type().contentEquals("round"))
                        {
                            int quotient = route.length()/2;
                            if(quotient*2== route.length())
                            {
                                array_dataItem.add(dataItem);
                            }
                        }else
                        {
                            array_dataItem.add(dataItem);
                        }



                    }

                    //flights2.setData(array_dataItem);
                    //array_main.add(flights2);

                   /* String g = gson.toJson(array_main);
                    Log.d("bordingpoint2", ".g"+g);*/
                    Manual_Flight_Adapter2 adapter2 = new Manual_Flight_Adapter2(ManualFlightActivity_New.this, array_dataItem,cabin_class);
                    recyclerView.setAdapter(adapter2);
                    //dialog.dismiss();
                    recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity_New.this));
//                    dialog.dismiss();
                    //  segmentedProgressBar.setCompletedSegments(3);
                    segmentedProgressBar.setVisibility(View.GONE);
                    tv_citiesname.setVisibility(View.VISIBLE);
                    filter.setVisibility(View.VISIBLE);
                    tv_citiesname.setText(cabin_class.getFrom_id()+" — "+cabin_class.getTo_id());

                } catch (JSONException e) {

                    Log.d("bordingpoint2", e.getMessage());
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    // dialog.dismiss();
                    finish();
                    //filter.setVisibility(View.VISIBLE);
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


                //  Log.d("boardingpoint",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                finish();
                //This code is executed if there is an error.
            }
        }) {
            @Override
            protected Map<String, String> getParams()  {

             /*   url = Constant.domain_name+"flights/search?appKey="+Constant.key+"&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+
                        "&departure_date="+cabin_class.getFrom_date()+"&arrival_date="+cabin_class.getTo_date()+"&type="+cabin_class.getTour_type()+
                        "&cabinclass="+cabin_class.getClass_type()+"&adults="+cabin_class.getAdults()+"&childs="+cabin_class.getChild()
                        +"&infants="+cabin_class.getInfants();*/

                String date = formatdate(cabin_class.getFrom_date());
                String type,return_from ;
                if (cabin_class.getTour_type().contentEquals("round"))
                {
                    type ="return";
                    return_from =formatdate(cabin_class.getTo_date());
                }else
                {
                    type ="oneway";
                    return_from="";
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("from_code",cabin_class.getTo_id());
                params.put("to_code",cabin_class.getFrom_id());
                params.put("date_from",date);
                params.put("return_from",return_from);
                params.put("adults",cabin_class.getAdults());
                params.put("children",cabin_class.getChild());
                params.put("infants",cabin_class.getInfants());
                params.put("flight_type",type);


                String g= gson.toJson(params);
                Log.d("bordingpoint2","ggg/"+g);
                return params;
            }
        };


        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(1000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);


    }






    public String formatdate(String fdate)
    {
        String datetime=null;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat d= new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {

        }
        return  datetime;


    }


    public String date_formate(long  convert_date)
    {



        @SuppressLint({"NewApi", "LocalSuppress"})
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" hh:mm");

        final long unixTime = convert_date;
        @SuppressLint({"NewApi", "LocalSuppress"})
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);

        return formattedDtm;


    }

    public void searchfilterd_dialogbox() {



        if(model_new.contentEquals("flights"))
        {

          //  Toast.makeText(getApplicationContext(),"flightsflights",Toast.LENGTH_LONG).show();
            Gson gson = new Gson();
            String myJson = gson.toJson(array_main_flight_main);
            String cabin_class1 = gson.toJson(cabin_class);
            Intent intent = new Intent(context, Searchfilter.class);
            intent.putExtra("array_main_flight", myJson);
            intent.putExtra("cabin_class", cabin_class1);
            intent.putExtra("Model", "flights");
            startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            //finish();

        }else if (model_new.contentEquals("travelhope"))
        {
            Gson gson = new Gson();
            String myJson = gson.toJson(array_dataItem);
            String cabin_class1 = gson.toJson(cabin_class);
            Intent intent = new Intent(context, Searchfilter.class);
            intent.putExtra("dataItem", myJson);
            intent.putExtra("cabin_class", cabin_class1);
            intent.putExtra("Model", "travelhope");
            startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            //finish();
        }


    }



    public void TarcoFlight()
    {

        String url = domain_name+"TarcoFlights/search?appKey="+Constant.key+"&departure_date=2019-12-30&from="+cabin_class.getFrom_id()+"&to="+cabin_class.getTo_id()+"&adults="+cabin_class.getAdults()+"&childs="+cabin_class.getChild()+"&infants="+cabin_class.getInfants();
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Tarco_Model tarco_model;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray response_result= jsonObject.getJSONArray("response");




                    for (int i =0; i<response_result.length();i++)
                    {


                        tarco_model =  new Tarco_Model();


                        JSONObject jsonObject1= response_result.getJSONObject(i);
                        JSONArray segments =  jsonObject1.getJSONArray("segments");


                        JSONArray one_way= segments.getJSONArray(0);

                        for(int one_way_int = 0; one_way_int<one_way.length() ; one_way_int++)
                        {
                            JSONObject oneway_object = one_way.getJSONObject(one_way_int);
                            String img_code = oneway_object.getString("img_code");

                            String departure_time = oneway_object.getString("departure_time");
                            String departure_date = oneway_object.getString("departure_date");
                            String arrival_date = oneway_object.getString("arrival_date");
                            String arrival_time = oneway_object.getString("arrival_time");
                            String departure_code = oneway_object.getString("departure_code");
                            String departure_airport = oneway_object.getString("departure_airport");
                            String arrival_code = oneway_object.getString("arrival_code");
                            String arrival_airport = oneway_object.getString("arrival_airport");
                            String duration_time = oneway_object.getString("duration_time");
                            String currency_code = oneway_object.getString("currency_code");
                            String price = oneway_object.getString("price");
                            String airline_name = oneway_object.getString("airline_name");
                            String Offer_Ref = oneway_object.getString("Offer_Ref");
                            String Offer_RefItinerary = oneway_object.getString("Offer_RefItinerary");
                            String form = oneway_object.getString("form");
                            String booking_class = oneway_object.getString("booking_class");






                            tarco_model.setDeparture_airport(departure_airport);
                            tarco_model.setDeparture_code(departure_code);
                            tarco_model.setArrival_time(arrival_time);
                            tarco_model.setArrival_date(arrival_date);
                            tarco_model.setDeparture_date(departure_date);
                            tarco_model.setDeparture_time(departure_time);
                            tarco_model.setImg_code(img_code);
                            tarco_model.setAirline_name(airline_name);
                            tarco_model.setOffer_Ref(Offer_Ref);
                            tarco_model.setOffer_RefItinerary(Offer_RefItinerary);
                            tarco_model.setForm(form);
                            tarco_model.setBooking_class(booking_class);
                            tarco_model.setPrice(price);
                            tarco_model.setCurrency_code(currency_code);
                            tarco_model.setDuration_time(duration_time);
                            tarco_model.setArrival_airport(arrival_airport);
                            tarco_model.setArrival_code(arrival_code);
                            array_main_tarco.add(tarco_model);




                        }





                    }

                    Tarco_adapter tarco_adapter =  new Tarco_adapter(getApplicationContext(),array_main_tarco);

                    recyclerView.setAdapter(tarco_adapter);
                    //dialog.dismiss();
                    recyclerView.setLayoutManager(new LinearLayoutManager(ManualFlightActivity_New.this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                // segmentedProgressBar.pause();
                segmentedProgressBar.setVisibility(View.GONE);
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


                //  Log.d("boardingpoint",logText);
                Toast.makeText(getBaseContext(),logText,Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(1000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //  mStringRequest.setRetryPolicy(new DefaultRetryPolicy(new DefaultRetryPolicy(150000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequestQueue.add(mStringRequest);

    }


}
