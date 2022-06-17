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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar;
import com.phptravelsnative.Activities.HotelDetail;
import com.phptravelsnative.Models.Amenities_Model;
import com.phptravelsnative.Models.DetailModel;
import com.phptravelsnative.Models.HotelModel;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.OverView;
import com.phptravelsnative.Models.review_model;
import com.phptravelsnative.Models.rooms_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;

import java.util.ArrayList;

/**
 * Created by apple on 23/10/2016.
 */

public class DetailRequest implements NetworkRequest {


    Context context;
    OverView overView;

   ArrayList<review_model> review_list;
   ArrayList<HotelModel> related_list;
   ArrayList<Amenities_Model> amenities_list;
   ArrayList<rooms_model> room_list;
   ArrayList<DetailModel> arrayList;
   static  ProgressDialog dialog;
    Hotel_data hotel_data;
    int id;
    SegmentedProgressBar segmentedProgressBar;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view2;
    LayoutInflater layoutInflaterAndroid;

    public DetailRequest(Context c, int id, Hotel_data hotel) {
        context=c;
       this. hotel_data=hotel;


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

        overView=new OverView();
        review_list=new ArrayList<>();
        amenities_list=new ArrayList<>();
        room_list=new ArrayList<>();
        related_list=new ArrayList<>();
        arrayList=new ArrayList<>();
        this.id=id;

    }

    @Override
    public  void checkResult()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name+"hotels/hoteldetails?appKey="+Constant.key+"&id="+id+"&checkin="+hotel_data.getFrom()+"&checkout="+hotel_data.getTo()+"&lang="+Constant.default_lang
                        +"&child="+hotel_data.getChild()+"&adults="+hotel_data.getAdult()                ,
                new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                Default_Detail_Parser default_detail_parser=new Default_Detail_Parser(context,hotel_data);
                default_detail_parser.execute(response);



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
                Log.e("HamzaError" + "-", logText, error);
                alertDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        alertDialog.show();
    }
    public void callmethod() {

        Intent mIntent=new Intent(context,HotelDetail.class);

        Bundle mBundle = new Bundle();
        mBundle.putParcelableArrayList("arrayList", arrayList);
                mBundle.putParcelableArrayList("review", review_list);
                mBundle.putParcelableArrayList("room", room_list);
                mBundle.putParcelableArrayList("am", amenities_list);
                mBundle.putParcelableArrayList("related", related_list);
                mBundle.putParcelable("ov", overView);
                mBundle.putParcelable("hotel", hotel_data);

        mIntent.putExtras(mBundle);
        alertDialog.dismiss();
        context.startActivity(mIntent);
    }

}
