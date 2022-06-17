package com.phptravelsnative.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.gson.Gson;
import com.phptravelsnative.Activities.Hotel_travelhopedetails;
import com.phptravelsnative.Models.ExpediaExtra;
import com.phptravelsnative.Models.HotelModel;
import com.phptravelsnative.Models.Hotel_data;
import com.phptravelsnative.Models.Model_Tour;
import com.phptravelsnative.Models.car_model;
import com.phptravelsnative.Network.Parser.CarRequest;
import com.phptravelsnative.Network.Parser.DetailRequest;
import com.phptravelsnative.Network.Parser.ExpediaDetailRequest;
import com.phptravelsnative.Network.Parser.OfffersRequest;
import com.phptravelsnative.Network.Parser.TourRequest;
import com.phptravelsnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListingAdapters extends BaseAdapter {


    ArrayList<HotelModel> myList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    String check_ean=null;
    car_model car;
    Model_Tour tour;
    Hotel_data hotel;
   // ProgressDialog pd;
    String sessionId;

    public ListingAdapters(Context context, ArrayList<HotelModel> myList, boolean check, String sessionId, Hotel_data hotel_data, String b) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.hotel=hotel_data;
        this.sessionId=sessionId;

        check_ean=b;
        if(check)
        {
            notifyDataSetChanged();
        }
    }
    public ListingAdapters(Context c, String checkCar, car_model cm, ArrayList<HotelModel> list_models)
    {
        context=c;
        check_ean=checkCar;
        this.myList=list_models;
        inflater = LayoutInflater.from(this.context);
        this.car=cm;
    }
    public ListingAdapters(Context c, String checkCar, ArrayList<HotelModel> list_models)
    {
        context=c;
        check_ean=checkCar;
        this.myList=list_models;
        inflater = LayoutInflater.from(this.context);
    }
    public ListingAdapters(Context c, String checkCar, Model_Tour cm, ArrayList<HotelModel> list_models)
    {
        context=c;
        check_ean=checkCar;
        this.myList=list_models;
        inflater = LayoutInflater.from(this.context);
        this.tour=cm;
    }

    public ListingAdapters(Context c, ArrayList<HotelModel> list_models, Hotel_data hotel_data, String hotel_default) {
        context=c;
        check_ean=hotel_default;
        this.myList=list_models;
        inflater = LayoutInflater.from(this.context);
        this.hotel=hotel_data;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public HotelModel getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            if(check_ean.equals("offers"))
            {
                convertView=inflater.inflate(R.layout.child_offers, parent, false);
            }else {
                convertView = inflater.inflate(R.layout.child_hotels, parent, false);
            }
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        final HotelModel currentListData = getItem(position);






        if (check_ean.equals("offers"))
        {
            mViewHolder.tvTitle.setText(currentListData.getName());
        }else
        {  mViewHolder.tvLocation.setText(currentListData.getLocation());
            mViewHolder.tvTitle.setText(currentListData.getTitle());

        }


        if(check_ean.equals("offers"))
            mViewHolder.price.setText(currentListData.getcurrCode()+" "+currentListData.getcurrSymbol()+" " +currentListData.getPrice());

        if(!check_ean.equals("offers")) {

            mViewHolder.ratingbar.setNumStars(currentListData.getStarsCount());


            if(check_ean.equals("cars")) {
                mViewHolder.rating_text.setVisibility(View.GONE);
//                mViewHolder.rating_text_static.setVisibility(View.GONE);
            }
                if (check_ean.equals("ex_hotel") || check_ean.equals("travelhope_hotel")) {
                    mViewHolder.rating_text.setText(currentListData.getRateCode() + "/5");
                    mViewHolder.price.setText(" " + currentListData.getPrice());
                    mViewHolder.tv_currencynew.setText(currentListData.getcurrCode());
                   mViewHolder.tv_currencynew.setVisibility(View.VISIBLE);

                } else {
                    mViewHolder.rating_text.setText(" " + currentListData.getRating() + "/10");
                    mViewHolder.price.setText(""+ currentListData.getPrice());

                    mViewHolder.tv_currencynew.setText(currentListData.getcurrCode() );
                    mViewHolder.tv_currencynew.setVisibility(View.VISIBLE);
                }

            mViewHolder.ratingbar.setRating(currentListData.getStarsCount());
            final View finalConvertView = convertView;
            if (currentListData.getStarsCount() == 0) {
                mViewHolder.ratingbar.setVisibility(View.GONE);
            } else {
                mViewHolder.ratingbar.setVisibility(View.VISIBLE);
            }
          /*  mViewHolder.buttonClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalConvertView.callOnClick();
                }
            });*/
        }

        Picasso.with(context)
                .load(currentListData.getThumbnail())
                .error(R.drawable.ic_no_image)
                .resize(270,240)
                .into(mViewHolder.image,  new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(context,"clcik me ",Toast.LENGTH_LONG).show();

                if(check_ean.equals("ex_hotel"))
                {
                    ExpediaExtra expediaExtra =new ExpediaExtra();
                    expediaExtra.setRateKey(currentListData.getRateKey());
                    expediaExtra.setRateCode(currentListData.getRateCode());
                    expediaExtra.setRoomTypeCode(currentListData.getRoomTypeCode());
                    expediaExtra.setSessionId(sessionId+"");
                    ExpediaDetailRequest expediaDetailRequest=new ExpediaDetailRequest(context,expediaExtra,currentListData.getid(),hotel);
                    expediaDetailRequest.checkResult();

                }
                else if(check_ean.equals("travelhope_hotel"))
                {

                    Gson gson =  new Gson();
                    String currentdata = gson.toJson(currentListData);
                    Intent intent = new Intent(context, Hotel_travelhopedetails.class);
                    intent.putExtra("currentdata",currentdata);
                    context.startActivity(intent);
                    //context.startActivity(new Intent(context, Hotel_travelhopedetails.class));

                }
                else if(check_ean.equals("hotel_default"))
                {
                    DetailRequest checkDetial=new DetailRequest(context,currentListData.getid(),hotel);
                    checkDetial.checkResult();
                }else if(check_ean.equals("cars"))
                {
                    CarRequest checkDetial=new CarRequest(context,currentListData.getid(),car);
                    checkDetial.checkResult();
                }
                else if(check_ean.equals("tour"))
                {
                    TourRequest checkDetial=new TourRequest(context,currentListData.getid(),tour);
                    checkDetial.checkResult();
                } else if(check_ean.equals("offers"))
                {
                    OfffersRequest checkDetial=new OfffersRequest(context,currentListData.getid());
                    checkDetial.checkResult();
                }

            }
        });
        return convertView;
    }
    private class MyViewHolder {
        RobotoTextView tvTitle,price,rating_text_static,rating_text,tvLocation;
      //  Button buttonClick;
       // ProgressBar progressDialog;
        RatingBar ratingbar;
        ImageView image;

        RobotoTextView tv_currencynew ;

        public MyViewHolder(View item) {
            tvTitle =  item.findViewById(R.id.name);

            price = item.findViewById(R.id.price);

            image = (ImageView) item.findViewById(R.id.image);
            tv_currencynew=item.findViewById(R.id.tv_currencynew);
           // progressDialog=(ProgressBar)item.findViewById(R.id.hoteldetails_progressBar);
            if(!check_ean.equals("offers")) {
                ratingbar = (RatingBar) item.findViewById(R.id.ratingbar);
                rating_text =  item.findViewById(R.id.rating_text_number);
                 tvLocation =  item.findViewById(R.id.tvLocation);
               // buttonClick=(Button)item.findViewById(R.id.book);
            }

        }

    }
}