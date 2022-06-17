package com.phptravelsnative.Network.Post;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.utality.Extra.Constant;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TravelPort_Booking extends StringRequest {

   private static final String BookingUrl= Constant.domain_name+"travelport_android/placeorder?appKey="+Constant.key;
    private Map<String,String>params;

    public TravelPort_Booking(ArrayList<String> title,ArrayList<String> firstname, ArrayList<String> lastname,
                              ArrayList<String> phone, ArrayList<String> email,ArrayList<String> nationality,
                              ArrayList<String> code,String formsCount,String cardtype,
                              String expMonth,String expYear,String cvv,
                              String cardno,String travelportCheckoutResp,String travelportCartResp,String userId,Response.Listener<String> listener,Response.ErrorListener errorListener)
    {
          super( Method.POST,BookingUrl,listener,errorListener);
        params=new HashMap<>();
        Gson gson = new Gson();
        params.put("title[]",gson.toJson(title));
        params.put("firstname[]",new JSONArray(firstname).toString());
        params.put("lastname[]",new JSONArray(lastname).toString());
        params.put("phone[]", new JSONArray(phone).toString());
        params.put("email[]", new JSONArray(email).toString());
        params.put("nationality[]", new JSONArray(nationality).toString());
        params.put("code[]", new JSONArray(code).toString());
        params.put("formsCount",formsCount);
        params.put("cardtype",cardtype);
        params.put("expMonth",expMonth);
        params.put("expYear",expYear);
        params.put("cvv",cvv);
        params.put("travelportCheckoutResp",travelportCheckoutResp);
        params.put("travelportCartResp",travelportCartResp);
        params.put("cardno",cardno);
        params.put("userId",userId);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
