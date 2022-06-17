package com.phptravelsnative.Network.Parser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.phptravelsnative.Activities.OffersDetails;
import com.phptravelsnative.Activities.SearchingCarTourOffers;
import com.phptravelsnative.Models.DetailModel;
import com.phptravelsnative.Models.OverView;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OfffersRequest implements NetworkRequest {


    Context context;
    public  OverView overView;
    ArrayList<DetailModel> arrayList=new ArrayList<>();

    int id;

    SegmentedProgressBar segmentedProgressBar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view2;
    LayoutInflater layoutInflaterAndroid;
    public OfffersRequest(Context c, int id) {



        builder = new AlertDialog.Builder(c);
        layoutInflaterAndroid = LayoutInflater.from(c);
        view2 = layoutInflaterAndroid.inflate(R.layout.progress_dialog, null);
        builder.setView(view2);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        segmentedProgressBar = (SegmentedProgressBar) view2.findViewById(R.id.segmented_progressbar);
        segmentedProgressBar.setSegmentCount(1);
        segmentedProgressBar.setFillColor(Color.parseColor("#00B0DD"));
        segmentedProgressBar.playSegment(50000);
        context=c;

        overView=new OverView();
        this.id=id;





    }

    @Override
    public  void checkResult()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name+"offers/offerdetails?appKey="+Constant.key+"&id="+id+"&lang="+ Constant.default_lang
                ,new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject main_object= parentObject.getJSONObject("response");
                    JSONArray image_array= main_object.getJSONArray("sliderImages");

                        overView.setTitle(main_object.getString("title"));
                        overView.setId(main_object.getInt("id"));
                        overView.setDesc(main_object.getString("desc"));
                        overView.setPolicy(main_object.getString("phone"));
                        overView.setLongitude(0.0);
                        overView.setLatitude(0.0);

                        DetailModel dm;
                        for(int i=0;i<image_array.length();i++) {
                            dm = new DetailModel();
                            JSONObject child_ob = image_array.getJSONObject(i);
                            dm.setSliderImages(child_ob.getString("thumbImage"));
                            arrayList.add(dm);

                        }
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }
                callmethod();
                alertDialog.dismiss();

//Set circle indicator radius

            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        alertDialog.show();
    }
    public void callmethod() {
        Intent mIntent=new Intent(context,OffersDetails.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("ov", overView);
        mBundle.putParcelableArrayList("arrayList",arrayList);
        mIntent.putExtras(mBundle);
        context.startActivity(mIntent);
    }
}
