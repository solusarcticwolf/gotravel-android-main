package com.phptravelsnative.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.phptravelsnative.Activities.DateActivity;
import com.phptravelsnative.Activities.Invoice;
import com.phptravelsnative.Activities.WebViewInvoice;
import com.phptravelsnative.Models.ExpediaExtra;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.OverView;
import com.phptravelsnative.Models.rooms_model;
import com.phptravelsnative.R;
import com.phptravelsnative.utality.Extra.Constant;
import com.phptravelsnative.utality.lib.Parallex.NotifyingScrollView;
import com.phptravelsnative.utality.lib.Parallex.ScrollViewFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RoomFragment extends ScrollViewFragment {

    OverView overView;
    ArrayList<rooms_model> room_list;
    Boolean b=false;
    private ProgressDialog dialog;
    ListView listView;
    Hotel_data hotel_data;
    SharedPreferences sharedPreferences;
    boolean bc;

    String date_from_api;
    String date_to_api;

    TextView date_from,date_to;

    ExpediaExtra expediaExtra;
    public  final String MyPREFERENCES = "MyPrefs" ;

    public static Fragment newInstance(int position, OverView ov, ArrayList<rooms_model> room_list, boolean b, Hotel_data hotel_data) {

        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putParcelable("ov", ov);
        args.putParcelable("hotel", hotel_data);
        args.putBoolean("check_ean", b);
        args.putParcelableArrayList("rl", room_list);
        fragment.setArguments(args);
        return fragment;
    }
    public static Fragment newInstanceExpedia(int position, OverView ov, ExpediaExtra expediaExtra, ArrayList<rooms_model> room_list, boolean b, Hotel_data hotel_data) {

        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putParcelable("ov", ov);
        args.putParcelable("hotel", hotel_data);
        args.putBoolean("check_ean", b);
        args.putParcelableArrayList("rl", room_list);
        args.putParcelableArrayList("expedia", expediaExtra);
        fragment.setArguments(args);
        return fragment;
    }

    public RoomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mPosition = getArguments().getInt(ARG_POSITION);
        overView = getArguments().getParcelable("ov");
        hotel_data = getArguments().getParcelable("hotel");
        hotel_data.setId(overView.getId());
         bc=getArguments().getBoolean("check_ean");
        dialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.loading));




        View view = inflater.inflate(R.layout.rooms_fragments, container, false);
        mScrollView = (NotifyingScrollView) view.findViewById(R.id.scrollview);


        View view_in = view.findViewById(R.id.check_in_layout);
        View  view_out = view.findViewById(R.id.check_text_out);

        date_from = (TextView) view.findViewById(R.id.date_from);
        date_to = (TextView) view.findViewById(R.id.date_to);

        TextView emptyView = (TextView) view.findViewById(R.id.emptyList);


        final Button update_btn = (Button) view.findViewById(R.id.update);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });




        if(!b) {
            room_list = getArguments().getParcelableArrayList("rl");
            date_from.setText(hotel_data.getFrom());
            date_to.setText(hotel_data.getTo());

        }else
        {
            date_from.setText(date_from_api);

            date_to.setText(date_to_api);
        }

        listView = (ListView) view.findViewById(R.id.room_list);

        view_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnClickHotelSelect();

            }
        });
        view_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnClickHotelSelect();
            }
        });

        if(bc)
        {
            expediaExtra=getArguments().getParcelable("expedia");
            RoomsAdaptersEan roomsAdapters=new RoomsAdaptersEan(getContext(), expediaExtra,room_list);
            if(roomsAdapters.getCount()==0) {
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                listView.setAdapter(roomsAdapters);
                emptyView.setVisibility(View.GONE);

            }

        }else
        {
            RoomsAdapters roomsAdapters=new RoomsAdapters(getContext(), room_list);
            if(roomsAdapters.getCount()==0) {
                emptyView.setVisibility(View.VISIBLE);
            }
            else {
                listView.setAdapter(roomsAdapters);
                emptyView.setVisibility(View.GONE);

            }
        }

        setListViewHeightBasedOnChildren(listView);

        setScrollViewOnScrollListener();

        return view;
    }

    public void update()
    {
        if(bc) {
            checkForUpdateEan(overView.getId());
        }else
        {

            checkForUpdate(overView.getId());

        }
    }

        public  void setListViewHeightBasedOnChildren(ListView listView) {


            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight=0;
            View view = null;

            for (int i = 0; i < listAdapter.getCount(); i++)
            {
                view = listAdapter.getView(i, view, listView);

                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                            ViewGroup.LayoutParams.MATCH_PARENT));

                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();

            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

            listView.setLayoutParams(params);
            listView.requestLayout();

        }

    public class RoomsAdapters extends BaseAdapter {

        ArrayList<rooms_model> myList = new ArrayList<>();
        LayoutInflater inflater;
        Context context;
        ArrayList<String> spinner=new ArrayList<>();


        public RoomsAdapters(Context context, ArrayList<rooms_model> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public rooms_model getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_room, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            final rooms_model currentListData = getItem(position);

                mViewHolder.tvTitle.setText(currentListData.getTitle());
                mViewHolder.price.setText(currentListData.getPrice() + " " + currentListData.getCurrencyCode() + " " + " For " +
                        currentListData.getStay() + " Nights");
                mViewHolder.book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(sharedPreferences.getBoolean("Check_Login",true) && !sharedPreferences.getBoolean("coupons",false))
                        {

                            Intent intent=new Intent(getContext(), WebViewInvoice.class);
                            Bundle bundle=new Bundle();
                            bundle.putParcelable("hotel_object",hotel_data);
                            intent.putExtra("check_type","hotel");
                            intent.putExtra("room_id",currentListData.getId()+"");
                            intent.putExtras(bundle);
                            intent.putExtra("numbers_rooms",(mViewHolder.spinner.getSelectedItemId()+1)+"");
                            startActivity(intent);

                        }else
                        {
                            Intent intent=new Intent(getContext(), Invoice.class);
                            Bundle bundle=new Bundle();
                            bundle.putParcelable("hotel_object",hotel_data);
                            intent.putExtra("room_id",currentListData.getId()+"");
                            intent.putExtra("check_type","hotel");
                            intent.putExtras(bundle);
                            intent.putExtra("numbers_rooms",(mViewHolder.spinner.getSelectedItemId()+1)+"");
                            startActivity(intent);
                        }
                    }
                });
                spinner = new ArrayList<>();
                for (int i = 1; i <= currentListData.getQuantity(); i++) {
                    spinner.add(i + getString(R.string.rooms));
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_layout_room, spinner);
                mViewHolder.spinner.setAdapter(dataAdapter);
                Picasso.with(context).load(currentListData.getImage()).error(R.drawable.ic_no_image).into(mViewHolder.ivIcon);

            return convertView;
        }

        private class MyViewHolder {
            TextView tvTitle;
            ImageView ivIcon;
            ImageView Spinner_Icon;
            TextView price;
            Spinner spinner;
            Button book;

            public MyViewHolder(View item) {
                tvTitle = (TextView) item.findViewById(R.id.room_title);
                price = (TextView) item.findViewById(R.id.price);
                price.setTypeface(null, Typeface.BOLD);

                ivIcon = (ImageView) item.findViewById(R.id.room_icon);
                spinner= (Spinner) item.findViewById(R.id.spinner2);
                Spinner_Icon= (ImageView) item.findViewById(R.id.spinner_icon);

                Spinner_Icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spinner.setDropDownVerticalOffset(spinner.getHeight());
                        spinner.performClick();
                    }
                });

                spinner.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        spinner.setDropDownVerticalOffset(spinner.getHeight());
                        return false;
                    }
                });
                book= (Button) item.findViewById(R.id.room_btn);

            }
        }
    }
    public class RoomsAdaptersEan extends BaseAdapter {

        ArrayList<rooms_model> myList = new ArrayList<>();
        LayoutInflater inflater;
        Context context;
        ExpediaExtra expediaExtra;
        ArrayList<String> spinner=new ArrayList<>();


        public RoomsAdaptersEan(Context context, ExpediaExtra expediaExtra,ArrayList<rooms_model> myList) {
            this.myList = myList;
            this.context = context;
            this.expediaExtra=expediaExtra;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public rooms_model getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MyViewHolder mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_room_ean, parent, false);
                mViewHolder = new MyViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder) convertView.getTag();
            }

            final rooms_model currentListData = getItem(position);

                mViewHolder.tvTitle.setText(currentListData.getTitle().trim());
                mViewHolder.price.setText( currentListData.getCurrencyCode() + " " +currentListData.getPrice()+" "+
                        currentListData.getStay());
                mViewHolder.book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        expediaExtra.setTotal_price(currentListData.getPrice());
                        Intent intent=new Intent(getContext(), Invoice.class);
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("hotel_object",hotel_data);
                        bundle.putParcelable("expedia",expediaExtra);
                        intent.putExtra("check_type","expedia");
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
                if(!currentListData.getImage().equals(""))
                Picasso.with(context).load(currentListData.getImage()).resize(100,80).error(R.drawable.ic_no_image).into(mViewHolder.ivIcon);
                else
                mViewHolder.ivIcon.setImageResource(R.drawable.ic_no_image);

            return convertView;
        }

        private class MyViewHolder {
            TextView tvTitle;
            ImageView ivIcon;
            TextView price;
            Button book;


            public MyViewHolder(View item) {
                tvTitle = (TextView) item.findViewById(R.id.room_title);
                price = (TextView) item.findViewById(R.id.price);
                price.setTypeface(null, Typeface.BOLD);

                ivIcon = (ImageView) item.findViewById(R.id.room_icon);

                book= (Button) item.findViewById(R.id.room_btn);

            }
        }
    }


    public  void checkForUpdate(int id)
    {
        Log.d("CheckData",Constant.domain_name+"hotels/hoteldetails?appKey="+Constant.key+"&id="+id+"&checkin="+date_from.getText().toString()+"&checkout="+date_to.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name+"hotels/hoteldetails?appKey="+Constant.key+"&id="+id+"&checkin="+date_from.getText().toString()+"&checkout="+date_to.getText().toString()

                , new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                dialog.dismiss();
                room_list=new ArrayList<>();
                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject main_object= parentObject.getJSONObject("response");
                    JSONArray room_array=main_object.getJSONArray("rooms");

                    rooms_model rm;
                    for(int i=0;i<room_array.length();i++)
                    {
                        JSONObject room_object=room_array.getJSONObject(i);
                        if(room_object.getInt("maxQuantity")>0) {
                            rm = new rooms_model();
                            rm.setId(room_object.getString("id"));
                            rm.setStay(room_object.getJSONObject("Info").getString("stay"));
                            rm.setQuantity(room_object.getJSONObject("Info").getInt("quantity"));
                            rm.setTitle(room_object.getString("title"));
                            rm.setCurrencyCode(room_object.getString("currCode"));
                            rm.setPrice(room_object.getString("price"));
                            rm.setImage(room_object.getString("thumbnail"));
                            room_list.add(rm);
                        }
                    }
                    b=true;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(RoomFragment.this).attach(RoomFragment.this).commit();
                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }



            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        dialog.show();
    }

    public  void checkForUpdateEan(int id)
    {
        Log.d("abcedfs", Constant.domain_name+"expedia/hoteldetails?appKey="+Constant.key+"&hotelId=" + id + "&checkIn=" + date_from.getText()
                + "&checkOut=" + date_to.getText() + "&customerSessionId=" + expediaExtra.getSessionId());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constant.domain_name+"expedia/hoteldetails?appKey="+Constant.key+"&hotelId=" + id + "&checkIn=" + date_from.getText()
                        + "&checkOut=" + date_to.getText() + "&customerSessionId=" + expediaExtra.getSessionId(), new Response.Listener() {
            @Override
            public void onResponse(Object response) {


                dialog.dismiss();
                room_list=new ArrayList<>();
                JSONObject parentObject = null;
                try {
                    parentObject = new JSONObject(response.toString());
                    JSONObject main_object = parentObject.getJSONObject("response");
                    JSONObject room_response = main_object.getJSONObject("roomsdata");
                    if(!room_response.getBoolean("hasRooms"))
                    {
                        room_list.clear();
                        Log.d("Clear","called0000");
                    }else {
                            JSONArray room_array = room_response.getJSONArray("roomsList");
                            rooms_model rm;
                            for (int i = 0; i < room_array.length(); i++) {
                                JSONObject room_object = room_array.getJSONObject(i);
                                rm = new rooms_model();
                                rm.setId(room_object.getString("id"));
                                rm.setTitle(room_object.getString("title"));
                                rm.setImage(room_object.getString("thumbnail"));
                                rm.setStay("For "+main_object.getString("totalStay")+" Nights");
                                rm.setCurrencyCode(room_object.getString("currency"));
                                rm.setPrice(room_object.getString("price"));
                                room_list.add(rm);

                            }
                    }
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(RoomFragment.this).attach(RoomFragment.this).commit();

                }
                catch(JSONException e){

                    Log.d("abcwwwwd",e.getMessage());
                }



            }
        }, null);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
        dialog.show();
    }

    public void OnClickHotelSelect()
    {
        Intent intent=new Intent(getContext(), DateActivity.class);
        startActivityForResult(intent,0);
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0) {
            if(data!=null) {
                date_from.setText(data.getStringExtra("date_from_api"));
                date_from_api = data.getStringExtra("date_from_api");
                date_to_api = data.getStringExtra("date_to_api");
                date_to.setText(data.getStringExtra("date_to_api"));
            }
        }
    }
}
