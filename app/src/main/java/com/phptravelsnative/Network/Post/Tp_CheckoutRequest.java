package com.phptravelsnative.Network.Post;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.utality.Extra.Constant;

import java.util.HashMap;
import java.util.Map;


public class Tp_CheckoutRequest extends StringRequest {

   private static final String BookingUrl= Constant.domain_name+"travelport_android/checkout?appKey="+Constant.key;
    private Map<String,String>params;

    public Tp_CheckoutRequest(String from_key, String to_key,String tp_detials,String searchPassenger, Response.Listener<String> listener,Response.ErrorListener errorListener)
    {
          super( Method.POST,BookingUrl,listener,errorListener);
        params=new HashMap<>();

        if(!to_key.equals("")){
            params.put("inbound",to_key);
        }
        params.put("outbound",from_key);
        params.put("travelportResp",tp_detials);
        params.put("searchPassenger",searchPassenger);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
