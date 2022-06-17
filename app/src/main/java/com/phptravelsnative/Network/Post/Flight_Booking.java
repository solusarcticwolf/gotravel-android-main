package com.phptravelsnative.Network.Post;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.TravelPort;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.utality.Extra.Constant;

import java.util.HashMap;
import java.util.Map;


public class Flight_Booking extends StringRequest {

   private static final String BookingUrl= Constant.domain_name+"flights/invoice?appKey="+Constant.key;
    private Map<String,String>params;

    public Flight_Booking(String userId, String itemId, TravelPort cabin_class, String coupn_id, Response.Listener<String> listener)
    {
          super( Method.POST,BookingUrl,listener,null);
        params=new HashMap<>();

        params.put("userId",userId);
        params.put("itemid",itemId);
        params.put("children",cabin_class.getChild());
        params.put("adults",cabin_class.getAdults());
        params.put("infant",cabin_class.getInfants());
        params.put("from",cabin_class.getFrom_id());
        params.put("to",cabin_class.getTo_id());
        params.put("type",cabin_class.getTour_type());
        params.put("couponid",coupn_id);

    }
    public Flight_Booking(Hotel_data user_info, String itemId, TravelPort cabin_class, String coupn_id, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
          super( Method.POST,BookingUrl,listener,errorListener);
        params=new HashMap<>();
        params.put("firstname",user_info.getAdult());
        params.put("lastname",user_info.getChild());
        params.put("email",user_info.getLocation());
        params.put("address",user_info.getFrom());
        params.put("phone",user_info.getTo());
        params.put("couponid",coupn_id);
        params.put("itemid",itemId);
        params.put("children",cabin_class.getChild());
        params.put("adults",cabin_class.getAdults());
        params.put("infant",cabin_class.getInfants());
        params.put("from",cabin_class.getFrom_id());
        params.put("to",cabin_class.getTo_id());
        params.put("type",cabin_class.getTour_type());

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
