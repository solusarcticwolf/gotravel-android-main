package com.phptravelsnative.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phptravelsnative.Adapters.AutoSuggestedAdapter;
import com.phptravelsnative.Adapters.Tp_DoubleListing_adapter;
import com.phptravelsnative.Adapters.Tp_Listing_adapter;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.TravelPortDetails;
import com.phptravelsnative.Models.TravelPortModel;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class TravelPortActivity extends Drawer {


    RecyclerView recyclerView;
    Tp_Listing_adapter adapter;
    ArrayList<TravelPortModel> tpm = new ArrayList<>();
    TravelPort cabin_class = new TravelPort();
    String travelportResponse = "";
    String searchPassenger = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_travel_port);
        View inflated = stub.inflate();

        recyclerView = (RecyclerView) inflated.findViewById(R.id.recycleView);

        Intent intent = getIntent();

         cabin_class = intent.getExtras().getParcelable("cabin_date");

        dialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.loading));

        ExpediaSearch();

    }

    public void ExpediaSearch() {

        String url = "";
        dialog.show();
        if(cabin_class.getFrom_id().equals("") && cabin_class.getTo_id().equals(""))
        {
            url = Constant.domain_name+"travelport_android/flights?appKey="+Constant.key;


        }else {

            url = Constant.domain_name+"travelport_android/search?appKey="+Constant.key+"&origin="+cabin_class.getFrom_id()+"&destination="+cabin_class.getTo_id()+
                    "&departure="+cabin_class.getFrom_date()+"&arrival="+cabin_class.getTo_date()+"&triptype="+cabin_class.getTour_type()+
                    "&cabinclass="+cabin_class.getClass_type()+"&passenger[adult]="+cabin_class.getAdults()+"&passenger[children]="+cabin_class.getChild()
                    +"&passenger[infant]="+cabin_class.getInfants()+"&passenger[total]="+(Integer.parseInt(cabin_class.getAdults())+Integer.parseInt(cabin_class.getChild())+
                    Integer.parseInt(cabin_class.getInfants()));


        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject parentObject = null;
                        try {
                            parentObject = new JSONObject(response.toString());

                            Log.d("mainObject", parentObject.toString());

                            boolean checkInbonds = false;
                            if (parentObject.getString("status").equals("success")) {

                                JSONObject dataObject = parentObject.getJSONObject("data");

                                travelportResponse = parentObject.getJSONObject("travelportResp").toString();
                                searchPassenger = parentObject.getJSONArray("searchPassenger").toString();

                                Iterator keys = dataObject.keys();

                                while (keys.hasNext()) {

                                    JSONObject flights_objects = dataObject.getJSONObject((String) keys.next());

                                    Iterator flights_key = flights_objects.keys();

                                    while (flights_key.hasNext()) {

                                        JSONObject inner_flight_Object = flights_objects.getJSONObject((String) flights_key.next());

                                        JSONObject outBoundObject = inner_flight_Object.getJSONObject("outbound");

                                        TravelPortModel travelPortModel = new TravelPortModel();

                                        travelPortModel.setAero_plane_number("Flights " + outBoundObject.getJSONObject("aircraft").getJSONObject("equipment").getString("code"));
                                        travelPortModel.setTake_off_destination(outBoundObject.getJSONObject("origin").getJSONObject("airport").getString("code"));
                                        travelPortModel.setArrivalDestination(outBoundObject.getJSONObject("destination").getJSONObject("airport").getString("code"));

                                        travelPortModel.setTotal_time(outBoundObject.getJSONObject("totalDuration").getString("day") + "D " + outBoundObject.getJSONObject("totalDuration").getString("hour") + "H "
                                                + outBoundObject.getJSONObject("totalDuration").getString("minute") + "M");
                                        travelPortModel.setToatl_stop(outBoundObject.getJSONObject("flightItinerary").getString("totalStops") + " Stops");
                                        travelPortModel.setTakeOff_time(outBoundObject.getJSONObject("origin").getJSONObject("departure").getJSONObject("time").getString("hour") + ":"
                                                + outBoundObject.getJSONObject("origin").getJSONObject("departure").getJSONObject("time").getString("minute"));
                                        travelPortModel.setArrival_time(outBoundObject.getJSONObject("destination").getJSONObject("arrival").getJSONObject("time").getString("hour") + ":"
                                                + outBoundObject.getJSONObject("destination").getJSONObject("arrival").getJSONObject("time").getString("minute"));
                                        travelPortModel.setName_aero(outBoundObject.getJSONObject("aircraft").getJSONObject("carrier").getString("shortname"));
                                        travelPortModel.setImg_aero(outBoundObject.getJSONObject("aircraft").getJSONObject("carrier").getString("image_path"));


                                        JSONArray segmentedArr = outBoundObject.getJSONObject("flightItinerary").getJSONArray("segments");

                                        TravelPortDetails details = new TravelPortDetails();

                                        for (int i = 0; i < segmentedArr.length(); i++) {

                                            details = new TravelPortDetails();
                                            details.setCheck_header_segment("header");

                                            travelPortModel.getDetials().add(details);

                                            JSONArray inner_Segment = segmentedArr.getJSONArray(i);

                                            for (int j = 0; j < inner_Segment.length(); j++) {

                                                JSONObject segmentedObject = inner_Segment.getJSONObject(j);

                                                details = new TravelPortDetails();

                                                if (i == 0) {

                                                    details.setCheck_CheckUnChecked(true);

                                                } else {
                                                    details.setCheck_CheckUnChecked(false);

                                                }

                                                if (j == 0) {

                                                    details.setCheck_inner_segment("show_button");

                                                } else {

                                                    details.setCheck_inner_segment("hide_button");

                                                }

                                                details.setDate_from(segmentedObject.getJSONObject("departureTime").getJSONObject("date").getString("day")
                                                +"/"+segmentedObject.getJSONObject("departureTime").getJSONObject("date").getString("month")+"/"+
                                                        segmentedObject.getJSONObject("departureTime").getJSONObject("date").getString("year"));

                                                details.setDate_to(segmentedObject.getJSONObject("arrivalTime").getJSONObject("date").getString("day")
                                                        +"/"+segmentedObject.getJSONObject("arrivalTime").getJSONObject("date").getString("month")+"/"+
                                                        segmentedObject.getJSONObject("arrivalTime").getJSONObject("date").getString("year"));

                                                details.setLocation_from(segmentedObject.getJSONObject("origin").getString("code"));
                                                details.setLocation_to(segmentedObject.getJSONObject("destination").getString("code"));
                                                details.setTime_from(segmentedObject.getJSONObject("departureTime").getJSONObject("time").getString("hour")+" : "+
                                                        segmentedObject.getJSONObject("departureTime").getJSONObject("time").getString("minute"));

                                                details.setTime_to(segmentedObject.getJSONObject("arrivalTime").getJSONObject("time").getString("hour")+" : "+
                                                        segmentedObject.getJSONObject("arrivalTime").getJSONObject("time").getString("minute"));

                                                details.setKey(segmentedObject.getString("key"));
                                                details.setFlight_type(segmentedObject.getJSONObject("bookingInformation").getString("CabinClass"));

                                                travelPortModel.getDetials().add(details);



                                            }


                                        }


                                        if (inner_flight_Object.has("inbound")) {


                                            JSONObject inBoundObject = inner_flight_Object.getJSONObject("inbound");
                                            travelPortModel.setB_aero_plane_number("Flights " + inBoundObject.getJSONObject("aircraft").getJSONObject("equipment").getString("code"));
                                            travelPortModel.setB_takeOffDestination(inBoundObject.getJSONObject("origin").getJSONObject("airport").getString("code"));
                                            travelPortModel.setB_arrivalDestination(inBoundObject.getJSONObject("destination").getJSONObject("airport").getString("code"));

                                            travelPortModel.setB_toatl_time(inBoundObject.getJSONObject("totalDuration").getString("day") + "D " + inBoundObject.getJSONObject("totalDuration").getString("hour") + "H "
                                                    + inBoundObject.getJSONObject("totalDuration").getString("minute") + "M");
                                            travelPortModel.setB_total_stop(inBoundObject.getJSONObject("flightItinerary").getString("totalStops") + " Stops");

                                            travelPortModel.setB_arrival_time(inBoundObject.getJSONObject("destination").getJSONObject("arrival").getJSONObject("time").getString("hour") + ":"
                                                    + inBoundObject.getJSONObject("destination").getJSONObject("arrival").getJSONObject("time").getString("minute"));

                                            travelPortModel.setB_takeOff_time(inBoundObject.getJSONObject("origin").getJSONObject("departure").getJSONObject("time").getString("hour") + ":"
                                                    + inBoundObject.getJSONObject("origin").getJSONObject("departure").getJSONObject("time").getString("minute"));
                                            checkInbonds = true;





                                            JSONArray segmentedArr_Inbounds = inBoundObject.getJSONObject("flightItinerary").getJSONArray("segments");

                                            TravelPortDetails details_inbounds = new TravelPortDetails();

                                            for (int i = 0; i < segmentedArr_Inbounds.length(); i++) {

                                                details_inbounds = new TravelPortDetails();
                                                details_inbounds.setCheck_header_segment("header");

                                                travelPortModel.getDetials_inbounds().add(details_inbounds);

                                                JSONArray inner_Segment = segmentedArr_Inbounds.getJSONArray(i);

                                                for (int j = 0; j < inner_Segment.length(); j++) {

                                                    JSONObject segmentedObject = inner_Segment.getJSONObject(j);

                                                    details_inbounds = new TravelPortDetails();

                                                    if (i == 0) {

                                                        details_inbounds.setCheck_CheckUnChecked(true);

                                                    } else {
                                                        details_inbounds.setCheck_CheckUnChecked(false);

                                                    }

                                                    if (j == 0) {

                                                        details_inbounds.setCheck_inner_segment("show_button");

                                                    } else {

                                                        details_inbounds.setCheck_inner_segment("hide_button");

                                                    }

                                                    details_inbounds.setDate_from(segmentedObject.getJSONObject("departureTime").getJSONObject("date").getString("day")
                                                            +"/"+segmentedObject.getJSONObject("departureTime").getJSONObject("date").getString("month")+"/"+
                                                            segmentedObject.getJSONObject("departureTime").getJSONObject("date").getString("year"));

                                                    details_inbounds.setDate_to(segmentedObject.getJSONObject("arrivalTime").getJSONObject("date").getString("day")
                                                            +"/"+segmentedObject.getJSONObject("arrivalTime").getJSONObject("date").getString("month")+"/"+
                                                            segmentedObject.getJSONObject("arrivalTime").getJSONObject("date").getString("year"));

                                                    details_inbounds.setLocation_from(segmentedObject.getJSONObject("origin").getString("code"));
                                                    details_inbounds.setLocation_to(segmentedObject.getJSONObject("destination").getString("code"));
                                                    details_inbounds.setTime_from(segmentedObject.getJSONObject("departureTime").getJSONObject("time").getString("hour")+" : "+
                                                            segmentedObject.getJSONObject("departureTime").getJSONObject("time").getString("minute"));

                                                    details_inbounds.setTime_to(segmentedObject.getJSONObject("arrivalTime").getJSONObject("time").getString("hour")+" : "+
                                                            segmentedObject.getJSONObject("arrivalTime").getJSONObject("time").getString("minute"));

                                                    details_inbounds.setKey(segmentedObject.getString("key"));
                                                    details_inbounds.setFlight_type(segmentedObject.getJSONObject("bookingInformation").getString("CabinClass"));

                                                    travelPortModel.getDetials_inbounds().add(details_inbounds);



                                                }


                                            }







                                        }


                                        travelPortModel.setCheckInsert(false);

                                        travelPortModel.setPrice(inner_flight_Object.getJSONObject("price").getString("totalprice_value"));
                                        travelPortModel.setCurrCode(inner_flight_Object.getJSONObject("price").getString("totalprice_unit"));

                                        tpm.add(travelPortModel);

                                    }

                                }
                                if (checkInbonds) {
                                    Tp_DoubleListing_adapter adapter = new Tp_DoubleListing_adapter(TravelPortActivity.this, tpm,cabin_class,travelportResponse,searchPassenger);
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(TravelPortActivity.this));
                                    dialog.dismiss();

                                } else {
                                    adapter = new Tp_Listing_adapter(TravelPortActivity.this, tpm,cabin_class,travelportResponse,searchPassenger);
                                    recyclerView.setAdapter(adapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(TravelPortActivity.this));
                                    dialog.dismiss();

                                }


                            } else {

                                Toast.makeText(getBaseContext(), parentObject.getString("message"), Toast.LENGTH_LONG).show();
                                finish();

                            }


                        } catch (JSONException e) {

                            Log.d("abcwwwwd", e.getMessage());
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog.setCancelable(true);
        dialog.dismiss();
    }
}
